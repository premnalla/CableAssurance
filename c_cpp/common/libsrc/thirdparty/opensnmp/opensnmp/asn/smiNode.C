#include "config.h"
#include "smiNode.H"
#include "opensnmpUtilities.H"

smiNode::smiNode() {
}

smiNode::smiNode(unsigned int *init, int initlen) : OID(init, initlen) {
}

smiNode::smiNode(const char *in) : OID(in) {
}

smiNode::smiNode(SmiNode *sn) : OID(sn) {
}
    
smiNode::smiNode(const OID &thecopy, char *append) 
    : OID(thecopy, append)
{
}

smiNode::smiNode(std::list<unsigned int> fromlist) : OID(fromlist) {
}

smiNode::~smiNode() {
}

SmiNode *
smiNode::get_sminode() {
    return smiGetNodeByOID(size(), array());
}

string
smiNode::description() {
    SmiNode *sn = get_sminode();
    if (sn && sn->description)
        return string(sn->description);
    return string("");
}

SmiAccess
smiNode::get_smiAccess(void) {
    SmiNode *sn = get_sminode();
    if (sn) {
        return sn->access;
    }
    return SMI_ACCESS_UNKNOWN;
}

SmiStatus
smiNode::get_smiStatus(void) {
    SmiNode *sn = get_sminode();
    if (sn) {
        return sn->status;
    }
    return SMI_STATUS_UNKNOWN;
}

SmiBasetype
smiNode::get_smiBasetype(void) {
    SmiNode *sn = get_sminode();
    if (sn) {
      SmiType *theType = smiGetNodeType(sn);
      if (theType)
        return theType->basetype;
    }
    return SMI_BASETYPE_UNKNOWN;
}

SmiDecl
smiNode::get_smiDecl(void) {
    SmiNode *sn = get_sminode();
    if (sn) {
      SmiType *theType = smiGetNodeType(sn);
      if (theType)
        return theType->decl;
    }
    return SMI_DECL_UNKNOWN;
}

int
smiNode::get_smiImplied(void) {
    SmiNode *sn = get_sminode();
    if (sn) {
      return sn->implied;
    }
    return int(0);
}

SmiNodekind
smiNode::get_smiNodekind(void) {
    SmiNode *sn = get_sminode();
    if (sn) {
        return sn->nodekind;
    }
    return SMI_NODEKIND_UNKNOWN;
}

SmiIndexkind
smiNode::get_smiIndexkind(void) {
    SmiNode *sn = get_sminode();
    if (sn) {
        return sn->indexkind;
    }
    return SMI_INDEX_UNKNOWN;
}

SmiElement *
smiNode::get_smiElement(void) {
  SmiNode *sn = get_sminode();
  if (sn) {
    return smiGetFirstElement(sn);
  }
  return NULL;
}


bool
smiNode::is_scalar(void) {
  SmiNodekind kind = get_smiNodekind();
  
  if (kind & SMI_NODEKIND_SCALAR) return true;
  else return false;
}

bool
smiNode::is_row(void) {
  SmiNodekind kind = get_smiNodekind();
  
  if (kind & SMI_NODEKIND_ROW) return true;
  else return false;
}

bool
smiNode::is_table(void) {
  SmiNodekind kind = get_smiNodekind();
  
  if (kind & SMI_NODEKIND_TABLE) return true;
  else return false;
}

bool
smiNode::is_column(void) {
  SmiNodekind kind = get_smiNodekind();
  
  if (kind & SMI_NODEKIND_COLUMN) return true;
  else return false;
}

bool
smiNode::is_access_readcreate_readwrite(void) {
  SmiAccess acc = get_smiAccess();
  
  if ( acc == SMI_ACCESS_READ_WRITE ) 
    return true;
  else return false;
}

bool
smiNode::is_implied(void) {
  int imp = get_smiImplied();

  if (imp != 0) return true;
  else return false;
}

string
smiNode::implied(void) {
  if (this->is_implied()) return string("Implied");
  else return string("not implied");
}

string
smiNode::nodekind(void) {
    string result;
    SmiNodekind kind = get_smiNodekind();

    if (kind == SMI_NODEKIND_NODE)
        return string("unknown");
    
    if ((kind & SMI_NODEKIND_NODE) == SMI_NODEKIND_NODE)
        result += "node" ;
    if ((kind & SMI_NODEKIND_SCALAR) == SMI_NODEKIND_SCALAR)
        result += "scalar";
    if ((kind & SMI_NODEKIND_TABLE) == SMI_NODEKIND_TABLE)
        result += "table";
    if ((kind & SMI_NODEKIND_ROW) == SMI_NODEKIND_ROW)
        result += "row";
    if ((kind & SMI_NODEKIND_COLUMN) == SMI_NODEKIND_COLUMN)
        result += "column";
    if ((kind & SMI_NODEKIND_NOTIFICATION) == SMI_NODEKIND_NOTIFICATION)
        result += "notification";
    if ((kind & SMI_NODEKIND_GROUP) == SMI_NODEKIND_GROUP)
        result += "group";
    if ((kind & SMI_NODEKIND_COMPLIANCE) == SMI_NODEKIND_COMPLIANCE)
        result += "compliance";
    if ((kind & SMI_NODEKIND_CAPABILITIES) == SMI_NODEKIND_CAPABILITIES)
        result += "capabilities";
    if ((kind & SMI_NODEKIND_ANY) == SMI_NODEKIND_ANY)
        result += "any";
    return result;
}


// STATIC
// This static function will convert the returned string from a
// 'string basetype' call back to a SmiBasetype.

SmiBasetype
smiNode::stringToBasetype(string bTypeStr) {

  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "UNKNOWN") )
    return SMI_BASETYPE_UNKNOWN;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "INTEGER32") )
    return SMI_BASETYPE_INTEGER32;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "OCTETSTRING") )
    return SMI_BASETYPE_OCTETSTRING;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "OBJECTIDENTIFIER") )
    return SMI_BASETYPE_OBJECTIDENTIFIER;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "UNSIGNED32") )
    return SMI_BASETYPE_UNSIGNED32;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "INTEGER64") )
    return SMI_BASETYPE_INTEGER64;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "UNSIGNED64") )
    return SMI_BASETYPE_UNSIGNED64;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "FLOAT32") )
    return SMI_BASETYPE_FLOAT32;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "FLOAT64") )
    return SMI_BASETYPE_FLOAT64;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "FLOAT128") )
    return SMI_BASETYPE_FLOAT128;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "ENUM") )
    return SMI_BASETYPE_ENUM;
  if ( OPENSNMP_UTILITIES::compareIgnoreCase(bTypeStr, "BITS") )
    return SMI_BASETYPE_BITS;

  return SMI_BASETYPE_UNKNOWN;
}  // stringToBasetype

string
smiNode::basetype(void) {
    SmiBasetype kind = get_smiBasetype();

    if ( kind == SMI_BASETYPE_UNKNOWN )
      return string("UNKNOWN");
    if ( kind == SMI_BASETYPE_INTEGER32 )
      return "INTEGER32";
    if ( kind == SMI_BASETYPE_OCTETSTRING )
      return "OCTETSTRING";
    if ( kind == SMI_BASETYPE_OBJECTIDENTIFIER )
      return "OBJECTIDENTIFIER";
    if ( kind == SMI_BASETYPE_UNSIGNED32 )
      return "UNSIGNED32";
    if ( kind == SMI_BASETYPE_INTEGER64 )
      return "INTEGER64";
    if ( kind == SMI_BASETYPE_UNSIGNED64 )
      return "UNSIGNED64";
    if ( kind == SMI_BASETYPE_FLOAT32 )
      return "FLOAT32";
    if ( kind == SMI_BASETYPE_FLOAT64 )
      return "FLOAT64";
    if ( kind == SMI_BASETYPE_FLOAT128 )
      return "FLOAT128";
    if ( kind == SMI_BASETYPE_ENUM )
      return "ENUM";
    if ( kind == SMI_BASETYPE_BITS )
      return "BITS";

    return string("unknown");
}

string
smiNode::smitypeid(void) {
  SmiNode *sn = get_sminode();
  if (sn) {
    SmiType *theType = smiGetNodeType(sn);
    if ((theType) && (theType->name))
      return string(theType->name);
  }
  return string("unknown");
}



string
smiNode::decl(void) {
  SmiDecl kind = get_smiDecl();

  if ( kind == SMI_DECL_UNKNOWN )
    return string("UNKNOWN");
  /* SMIv1/v2 ASN.1 statements and macros */
  if ( kind == SMI_DECL_IMPLICIT_TYPE )
    return string("IMPLICIT_TYPE");
  if ( kind == SMI_DECL_TYPEASSIGNMENT )
    return string("TYPEASSIGNMENT");
  if ( kind == SMI_DECL_IMPL_SEQUENCEOF )
    return string("IMPL_SEQUENCEOF");
  if ( kind == SMI_DECL_VALUEASSIGNMENT )
    return string("VALUEASSIGNMENT");
  if ( kind == SMI_DECL_OBJECTTYPE )
    return string("OBJECTTYPE");
  if ( kind == SMI_DECL_OBJECTIDENTITY )
    return string("OBJECTIDENTITY");
  if ( kind == SMI_DECL_MODULEIDENTITY )
    return string("MODULEIDENTITY");
  if ( kind == SMI_DECL_NOTIFICATIONTYPE )
    return string("NOTIFICATIONTYPE");
  if ( kind == SMI_DECL_TRAPTYPE )
    return string("TRAPTYPE");
  if ( kind == SMI_DECL_OBJECTGROUP )
    return string("OBJECTGROUP");
  if ( kind == SMI_DECL_NOTIFICATIONGROUP )
    return string("NOTIFICATIONGROUP");
  if ( kind == SMI_DECL_MODULECOMPLIANCE )
    return string("MODULECOMPLIANCE");
  if ( kind == SMI_DECL_AGENTCAPABILITIES )
    return string("AGENTCAPABILITIES");
  if ( kind == SMI_DECL_TEXTUALCONVENTION )
    return string("TEXTUALCONVENTION");
  /* SMIng statements */
  if ( kind == SMI_DECL_MODULE )
    return string("MODULE");
  if ( kind == SMI_DECL_EXTENSION )
    return string("EXTENSION");
  if ( kind == SMI_DECL_TYPEDEF )
    return string("TYPEDEF");
  if ( kind == SMI_DECL_NODE )
    return string("NODE");
  if ( kind == SMI_DECL_SCALAR )
    return string("SCALAR");
  if ( kind == SMI_DECL_TABLE )
    return string("TABLE");
  if ( kind == SMI_DECL_ROW )
    return string("ROW");
  if ( kind == SMI_DECL_COLUMN )
    return string("COLUMN");
  if ( kind == SMI_DECL_NOTIFICATION )
    return string("NOTIFICATION");
  if ( kind == SMI_DECL_GROUP )
    return string("GROUP");
  if ( kind == SMI_DECL_COMPLIANCE )
    return string("COMPLIANCE");

  return string("unknown");
}

string
smiNode::indexkind(void) {
    string result;
    SmiIndexkind kind = get_smiIndexkind();

    if (kind == SMI_INDEX_UNKNOWN)
        result = "unknown";
    if (kind == SMI_INDEX_INDEX)
        result = "index" ;
    if (kind == SMI_INDEX_AUGMENT)
        result = "augment";
    if (kind == SMI_INDEX_REORDER)
        result = "reorder";
    if (kind == SMI_INDEX_SPARSE)
        result = "sparse";
    if (kind == SMI_INDEX_EXPAND)
        result = "expand";

    return result;
} // indexkind

std::list<string>
smiNode::indexes(void) {
  std::list<string> result;
  SmiIndexkind  kind = get_smiIndexkind();

  if ( (SMI_INDEX_INDEX   == kind) ||
       (SMI_INDEX_AUGMENT == kind) ){
    SmiModule  *smiModule = NULL;
    SmiNode    *smiNode = NULL, *sn = NULL, *iNode = NULL;
    SmiElement *smiElement = NULL;

    // for augment must retrieve 'related' nodes, hmm, like in-laws?
    if (SMI_INDEX_AUGMENT == kind)  {
      // make sure it is all good, non-zero, values.
      if ( (sn    = get_sminode()) &&
	   (iNode = smiGetRelatedNode(sn)) ) {
	smiElement = smiGetFirstElement(iNode);
      }
    }
    else {
      smiElement = get_smiElement();
    }

    for (; smiElement; smiElement = smiGetNextElement(smiElement)) {
      smiNode = smiGetElementNode(smiElement);
      smiModule = smiGetNodeModule(smiNode);
      result.push_back(string(smiNode->name));
    }
  } 
  else {
    result.push_back(string("unknown"));
  }
    
  return result;
}  // indexes

string
smiNode::access(void) {
    SmiAccess a = get_smiAccess();
    switch (a) {
        case SMI_ACCESS_NOT_ACCESSIBLE:
            return string("not-accessible");
        case SMI_ACCESS_NOTIFY:
            return string("accessible-for-notify");
        case SMI_ACCESS_READ_ONLY:
            return string("read-only");
        case SMI_ACCESS_READ_WRITE:
            return string("read-write");
        default:
            return string("(unknown)");
    }
    return string("");
}

string
smiNode::status(void) {
    SmiStatus a = get_smiStatus();
    switch (a) {
        case SMI_STATUS_CURRENT:
            return string("current");
        case SMI_STATUS_DEPRECATED:
            return string("deprecated");
        case SMI_STATUS_MANDATORY:
            return string("mandatory");
        case SMI_STATUS_OPTIONAL:
            return string("optional");
        case SMI_STATUS_OBSOLETE:
            return string("obsolete");
        default:
            return string("(unknown)");
    }
    return string("");
}

string
smiNode::label(void) {
    SmiNode *sn = get_sminode();
    if (sn) {
        return string(sn->name);
    }
    return string("");
}

std::list<smiNode *>
smiNode::get_children(void) {
    std::list<smiNode *> nodes;
    SmiNode *sn = get_sminode();
    if (sn) {
        for(sn = smiGetFirstChildNode(sn);
            sn != NULL;
            sn = smiGetNextChildNode(sn)) {
            nodes.push_back(new smiNode(sn));
        }
    }
    return nodes;
}
    
std::list<smiNode *>
smiNode::get_indexes(void) {
  std::list<smiNode *> rnodes;
  SmiIndexkind  theKind = get_smiIndexkind();

  if ( (SMI_INDEX_INDEX   == theKind) ||
       (SMI_INDEX_AUGMENT == theKind) )  {
    SmiNode    *sNode = NULL, *sn = NULL, *iNode = NULL;
    SmiElement *smiElement = NULL;
    
    // for augment must retrieve 'related' nodes, hmm, like in-laws?
    if (SMI_INDEX_AUGMENT == theKind)  {
      // make sure it is all good, non-zero, values.
      if ( (sn    = get_sminode()) &&
	   (iNode = smiGetRelatedNode(sn)) ) {
	smiElement = smiGetFirstElement(iNode);
      }
    }
    else {
      smiElement = get_smiElement();
    }

    // add all the index nodes to the list
    for (; smiElement; smiElement = smiGetNextElement(smiElement)) {
      sNode = smiGetElementNode(smiElement);
      rnodes.push_back(new smiNode(sNode));
    }
  } 
  return rnodes;
} // get_indexes


string
smiNode::format(void) {
    SmiNode *sn = get_sminode();
    if (sn && sn->format) {
        return string(sn->format);
    }
    return string("");
}

string
smiNode::units(void) {
    SmiNode *sn = get_sminode();
    if (sn && sn->units) {
        return string(sn->units);
    }
    return string("");
}

