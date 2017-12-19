// snmpTable.C: implementation for the snmpTable mib.

#include "config.h"
#include "snmpIndexes.H"
#include "snmpTable.H"
#include <typeinfo>
#include "debug.H"
#include "snmpRowManager.H"

#include <assert.h>

#ifndef NO_PERSISTENCE
#include "snmpDatabaseObj.H"
#include "snmpRow.H"
#endif

//
// snmpTable
//

snmpTable::snmpTable(const snmpIndexes & indexes, unsigned int maxcolnum, 
                     unsigned int mincolnum,
                     snmpDataTable::dataStorageType dSType,
                     snmpRowManager *rowManager) {
    datatable = new snmpDataTable(indexes);
    this->maxcolnum = maxcolnum;
    this->mincolnum = mincolnum;
    this->dSType = dSType;
    this->manager = rowManager;
}

snmpTable::snmpTable(snmpDataTable *table, unsigned int maxcolnum, 
                     unsigned int mincolnum,
                     snmpDataTable::dataStorageType dSType,
                     snmpRowManager *rowManager) {
    this->datatable = table;
    this->maxcolnum = maxcolnum;
    this->mincolnum = mincolnum;
    this->dSType = dSType;
    this->manager = rowManager;
}

snmpTable::~snmpTable() {
    // XXX
}

// init() is called after the table is registered by the cr.
void
snmpTable::init( const OID & registeredAt )
{
  DEBUGINIT(debugObj_1, "snmpTable:init");
  DEBUG9(debugObj_1, "  snmpTable registered for "
	 << registeredAt.get_numericStr() << endl);

#ifndef NO_PERSISTENCE
  DEBUGINIT(debugObj, "persist:init");

  // get pointer to database
  snmpDatabaseObj * pDb = snmpDatabaseObj::getSnmpDatabaseObj();
  if( pDb == NULL ) {
    DEBUG0( debugObj, "No database to read persistent values from!" << endl );
    return;
  }
  
  // get saved values from the database
  map<snmpIndexes, Sequence*> values;
  pDb->get_storedValues( registeredAt, datatable->get_snmpIndexes(), values );

  // iterate through saved rows and re-create them
  snmpRow *pRow;
  int newRows = 0;
  map<snmpIndexes, Sequence*>::iterator it;
  for( it = values.begin(); it != values.end(); ++it ) {
    ++newRows;
    DEBUG9(debugObj, "----->" << it->first << ':' << *it->second );
    pRow = manager->allocator->allocateWithoutDefaults();
    // iterate through saved column and set them in row
    Sequence *pSeq = dynamic_cast<Sequence*>(it->second);
    Sequence::iterator col_it = pSeq->begin();
    while( col_it != pSeq->end() ) {
      Integer32 *pInt = dynamic_cast<Integer32*>(*col_it++);
      assert( col_it != pSeq->end() ); // should always have pairs
      asnDataType *pData = *col_it++;
      DEBUG1(debugObj, "----->** col " << *pInt << "=|" << *pData << '|' );
      pRow->create_column( *pInt, *pData );
    }
    // use rowManager to provide default values for any columns that
    // are not already set
    manager->allocator->allocateMissingDefaults( *pRow );

    // add row to table
    add_row( it->first, pRow );
  }

  DEBUG5(debugObj,  " added " << newRows << " new row(s)." );
#endif
}

//  void
//  snmpTable::set_setableFields(map<unsigned int, bool> &setable_cols) {
//      this->setable_cols = new map<unsigned int, bool>(setable_cols);
//  }

snmpProtoErr *
snmpTable::snmp_get(MIBRegistration *reg, SearchRangeList *thevars) {
  DEBUGINIT(debugObj, "snmpTable");
  DEBUG9(debugObj, "        snmpTable::get\n");

  OID *regoid = reg->get_reg_oid();
  SearchRangeList::iterator i;

  for(i = thevars->begin(); i != thevars->end(); i++) {
    SearchRange *sr = *i;
    OID *retrievethis = sr->get_start_oid();
    DEBUG9(debugObj, "    **** snmpTable::get retrieving: " << retrievethis->toString() << "\n");
    if (retrievethis->length() < regoid->length()+3) {
        DEBUG9(debugObj, "    **** snmpTable::get length too short: " \
             << retrievethis->toString() \
             << " < " \
             << regoid->toString() \
             << " + 3\n");
        continue; // length is too short, nothing after the column.
    }
    OID *index = retrievethis->clone();
    index->crop(regoid->length()+3, MAX_OID_LEN+1); // crop down to just indexes
    snmpIndexes key = datatable->get_snmpIndexes();
    if (!key.set_fromOID(*index)) {
        // illegal request, we can't parse the oid into the index values.
        delete index;
        continue;
    }
    map <snmpIndexes, snmpRow *>::iterator ri = datatable->find(key);

    // no entry found?
    if (ri == datatable->end()) {
          DEBUG9(debugObj, "    **** snmpTable::get no row found at " << index->toString() << "\n");
        delete index;
        continue;
    }
    delete index;

    // get the column number from the request oid.
    unsigned int colnum = (*retrievethis)[regoid->length()+1];

    // get the data at that column, and put it in the varbind (if !NULL).
    asnDataType *ret = ri->second->get_column(colnum);
    if (ret != 0) {
        sr->get_varbind()->set_value(ret->clone());
    } else {
      // XXX: error
    }
    DEBUG9(debugObj, "        snmpTable::get " << sr->get_varbind()->toString() << "\n");
  }
  return 0;
}

snmpProtoErr *
snmpTable::snmp_getNext(MIBRegistration *reg, SearchRangeList *thevars) {
  DEBUGINIT(debugObj, "snmpTable");
  DEBUG9(debugObj, "        snmpTable::getNext\n");

  OID *regoid = reg->get_reg_oid();
  SearchRangeList::iterator i;
  map <snmpIndexes, snmpRow *>::iterator ri;
  
  for(i = thevars->begin(); i != thevars->end(); i++) {
    SearchRange *sr = *i;
    OID *retrievethis = sr->get_start_oid();
    DEBUG9(debugObj, "    **** snmpTable::getNext retrieving: " << retrievethis->toString() << "\n");
    if (*retrievethis < *regoid) {
        DEBUG9(debugObj, "    **** snmpTable::getNext start range is before us: " << retrievethis->toString() << " < " << regoid->toString() << "\n");

        // return the first node
        *retrievethis = *regoid;
        ri = datatable->begin();
    } else if (retrievethis->length() < regoid->length()+3) {
        DEBUG9(debugObj, "    **** snmpTable::getNext length too short for indexing: " << retrievethis->toString() << " < " << regoid->toString() << " + 3\n");

        // check to see if the entry node pointer is illegal and beyond us (>1)
        if (retrievethis->length() > regoid->length()+1 &&
            (*retrievethis)[regoid->length()] > 1) {
            DEBUG9(debugObj, "       * snmpTable::getNext illegal entry node\n" );
            continue;
        }
        
        ri = datatable->begin();
    } else {
        OID *index = retrievethis->clone();
         // crop down to just indexes
        index->crop(regoid->length()+3, MAX_OID_LEN+1);
        snmpIndexes key = datatable->get_snmpIndexes();
        if (!key.set_fromOID(*index) ||
            ((ri = datatable->find(key)) == datatable->end())) {
            // illegal request, we can't parse the oid into the index values.
            DEBUG9(debugObj, "    **** snmpTable::getNext unparsable key or nothing found at key:" << (string) key << " -> " << index->toString() << "\n");

            // gotta iterate through data to find the right node.
            // find first > node.
            for (ri = datatable->begin(); ri != datatable->end(); ri++) {
                DEBUG9(debugObj, "    **** snmpTable::getNext searching at " << (string) ri->first.get_OID() << "\n");
                if (*index < ri->first.get_OID())
                    break;
            }
            delete index;

            if (ri == datatable->end())
                continue;  // XXX: did we miss deleting something?
        } else {
            delete index;
            // ok, we have the current pointer, so get the next.
            ri++;
        }
    }
    
    unsigned int colnum;
    if (retrievethis->length() > regoid->length()+1) {
        colnum = (*retrievethis)[regoid->length()+1];
        if (colnum < mincolnum)
            colnum = mincolnum;
    } else
        colnum = mincolnum;

    
    if (ri == datatable->end()) {
        // gotta go to next column...
        colnum++;
        if (colnum > maxcolnum)
            continue; // out of our range.
        // ...and start at the beginning of the data range.
        ri = datatable->begin();
        DEBUG9(debugObj, "      ** snmpTable::getNext going to next column.\n");
    }

    if (ri == datatable->end()) {
        DEBUG9(debugObj, "no data to return\n");
        continue;
    }

    // get the data at that column, and put it in the varbind (if !NULL).
    // if NULL, then continue til we either run beyond our table range
    asnDataType *ret;
    ret = ri->second->get_column(colnum);
    while(ret == NULL) {
        ri++;
        if (ri == datatable->end()) {
            colnum++;
            ri = datatable->begin();
            if (colnum > maxcolnum)
                break; // out of our range.
        }
        ret = ri->second->get_column(colnum);
    }

    if (ret != NULL) {
        sr->get_varbind()->set_value(ret->clone());
        sr->get_varbind()->set_OID(datatable->createOID(regoid, colnum, &ri));
    } else {
      // XXX: error? (no, not really).
    }
    DEBUG9(debugObj, "        snmpTable::getNext " << sr->get_varbind()->toString() << "\n");
  }
  return 0;
}

snmpProtoErr *
snmpTable::snmp_set(MIBRegistration *reg, set_actions action, 
                    SearchRangeList *thevars) {
  DEBUGINIT(debugObj, "snmpTable");
  DEBUG9(debugObj, "        set, action = " << (int) action << "\n");
  DEBUG9(debugObj, "        set\n");
  OID *regoid = reg->get_reg_oid();

  // nothing is writable, return immediately.
  if (dSType == snmpDataTable::READ_ONLY) {
      return new snmpProtoErr(PROTOERR_NOTWRITABLE,
                             (*(thevars->begin()))->get_varbind());
  }
  
  map <snmpRowKey, map<int,VarBind*>* > changed; // map rows to changed columns
  SearchRangeList::iterator i;
  for(i = thevars->begin(); i != thevars->end(); i++) {
      SearchRange *sr = *i;
      OID *retrievethis = sr->get_start_oid();

      // find the row to be acted upon.
      if (retrievethis->length() < regoid->length()+3) {
          DEBUG9(debugObj, "    **** snmpTable::get length too short: " << retrievethis->toString() << " < " << regoid->toString() << " + 3\n");
          // length is too short, nothing after the column.
          return new snmpProtoErr(PROTOERR_NOSUCHOBJECT, // XXX: correct?
                                  sr->get_varbind());
      }

      // get the column number from the request oid.
      unsigned int colnum = (*retrievethis)[regoid->length()+1];

      // are we allowed to write to the column in question at all?
      if (dSType == snmpDataTable::SOMEWRITABLE) {
          map<int, bool>::const_iterator i;
          if (manager->setable_cols == NULL ||
              (i = manager->setable_cols->find(colnum)) == manager->setable_cols->end() ||
              i->second == false) {
                   return new snmpProtoErr(PROTOERR_NOTWRITABLE, 
                                           sr->get_varbind());
          }
      }
      

      // find the actual data (maybe) in our storage.
      OID *index = retrievethis->clone();
      // crop down to just indexes
      index->crop(regoid->length()+3, MAX_OID_LEN+1);
      snmpIndexes key = datatable->get_snmpIndexes();
      if (!key.set_fromOID(*index)) {
          // illegal request, we can't parse the oid into the index values.
          delete index;
          continue;
      }
      map <snmpIndexes, snmpRow *>::iterator ri = datatable->find(key);

      // no entry found?
      if (ri == datatable->end()) {
          // can we create one using the allocator given to us?
          if (!manager->allocator) {
              delete index;
              return new snmpProtoErr(PROTOERR_NOCREATION, sr->get_varbind());
          }
          snmpRow *newrow = manager->allocator->allocate();
          datatable->add_row(key, newrow);
          newRows.insert(key);

          ri = datatable->find(key);
          // XXX: remember to delete later if hit UNDO
      }

      // setup undo key, which may be needed later.
      undoKey undoinfo(colnum, key);

      // save changed row
      snmpRowKey rowKey( key, ri->second );
      if( changed.count( rowKey ) == 0 )
	changed[ rowKey ] = new map<int,VarBind*>();
      // xxx-rks: we could check for multiple identical column
      // sets here pretty easily.
      map<int,VarBind*>* cols = changed[ rowKey ];
      (*cols)[ colnum ] = sr->get_varbind();

      // examine or use with the actual request data.
      asnDataType *tmpadtp;
      switch(action) {
          case AGENTX_TESTSET:
              // attempt to check all known possible problems now.  If
              // there is one, we try to return an error now, rather
              // than later, if at all possible.
              // XXX: check against default value too.
              if (sr->get_varbind()->get_value(false) == NULL)
                  return new snmpProtoErr(PROTOERR_WRONGTYPE, 
                                          sr->get_varbind());
              tmpadtp = ri->second->get_column(colnum, false);
              if (tmpadtp != NULL) {
                  asnDataType::valueErrors err;
                  err = tmpadtp->check_new_value(*(sr->get_varbind()->get_value(false)));
                  if (err)
                      return new snmpProtoErr(err, sr->get_varbind());
              }
              break;
                  
          case AGENTX_COMMIT:
              // save old state for possible "UNDO" later on.
              // XXX: deal with multiple identical variables in set request
              tmpadtp = ri->second->get_column(colnum);
              if (tmpadtp)
                  undoInformation[undoinfo] = tmpadtp->clone();
              else
                  undoInformation[undoinfo] = NULL;

              // update the column with the new information.
              try {
                  ri->second->set_column(colnum, *sr->get_varbind()->get_value(false));
              } catch (bad_cast) {
                  DEBUG9(debugObj, "set ERROR: " 
                         << sr->get_varbind()->toString() 
                         << " bad_cast (wrongType)\n");
                  return new snmpProtoErr(PROTOERR_WRONGTYPE, 
                                          sr->get_varbind());
              } catch (snmpProtoErr &err) {
                  DEBUG9(debugObj, "set ERROR: object "
                         << sr->get_varbind()->toString() 
                         << " returned error: " << err.toString() << "\n");
                  return new snmpProtoErr(err.get_error(), 
                                          sr->get_varbind());
              } catch (...) {
                  // what the heck happened?
                  return new snmpProtoErr(PROTOERR_GENERR, 
                                          sr->get_varbind());
              }
              break;

          case AGENTX_UNDO:
              // uncommit the changes above
              // XXX: find an iterater to make sure an entry exists.
              //      (or else die a miserably horrible death
              //      proclaiming that we've been cheated out of our
              //      life's goals and expectations by someone else).
              // XXX: deal with multiple identical variables in set request
              // XXX: deal with row creation/deletion.
              ri->second->set_columnForced(colnum, 
                                           undoInformation[undoinfo]);
              undoInformation.erase(undoinfo);

          case AGENTX_CLEANUP:
              // destroy no longer needed undo information.
              if (undoInformation[undoinfo] != 0) {
                  delete undoInformation[undoinfo];
              }
              undoInformation.erase(undoinfo);

          default:
              break;
      }
      DEBUG9(debugObj, "        set " << sr->get_varbind()->toString() << "\n");
  }


  // final row processing
  switch (action) {
      case AGENTX_CLEANUP:
          newRows.clear();
          datatable->delete_marked_rows();
          break;
      case AGENTX_UNDO:
          for(set <snmpIndexes>::iterator newRowIter = newRows.begin();
              newRowIter != newRows.end(); newRowIter++) {
              datatable->delete_row(*newRowIter);
          }
          newRows.clear();
          datatable->clear_delete_marks();
          break;
  case AGENTX_COMMIT: {
	    map <snmpRowKey, map<int,VarBind*>* >::iterator j;
	    for( j = changed.begin(); j != changed.end(); ++j ) {
	      manager->row_changed( regoid, j->first, j->second );
	    }
          }
	  break;
      default:
          break;
  }


  return 0;
}

bool snmpTable::add_row(const snmpIndexes &indexValue, snmpRow *row) {
    return datatable->add_row(indexValue, row);
}
