/*
 * Note: this file originally auto-generated by mib2c using
 *       version : 1.17 $ of : mfd-data-access.m2c,v $ 
 *
 * $Id: ipv6InterfaceTable_data_access.c,v 1.1.1.1 2006/04/05 02:29:38 prem Exp $
 */
/*
 * standard Net-SNMP includes 
 */
#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>
#include <net-snmp/agent/net-snmp-agent-includes.h>

/*
 * include our parent header 
 */
#include "ipv6InterfaceTable.h"


#include "ipv6InterfaceTable_data_access.h"

/** @defgroup data_access data_access: Routines to access data
 *
 * These routines are used to locate the data used to satisfy
 * requests.
 * 
 * @{
 */
/**********************************************************************
 **********************************************************************
 ***
 *** Table ipv6InterfaceTable
 ***
 **********************************************************************
 **********************************************************************/
/*
 * IP-MIB::ipv6InterfaceTable is subid 30 of ip.
 * Its status is Current.
 * OID: .1.3.6.1.2.1.4.30, length: 8
 */

/**
 * initialization for ipv6InterfaceTable data access
 *
 * This function is called during startup to allow you to
 * allocate any resources you need for the data table.
 *
 * @param ipv6InterfaceTable_reg
 *        Pointer to ipv6InterfaceTable_registration
 *
 * @retval MFD_SUCCESS : success.
 * @retval MFD_ERROR   : unrecoverable error.
 */
int
ipv6InterfaceTable_init_data(ipv6InterfaceTable_registration *
                             ipv6InterfaceTable_reg)
{
    DEBUGMSGTL(("verbose:ipv6InterfaceTable:ipv6InterfaceTable_init_data",
                "called\n"));

    /*
     * TODO:303:o: Initialize ipv6InterfaceTable data.
     */

    return MFD_SUCCESS;
}                               /* ipv6InterfaceTable_init_data */

/**
 * container overview
 *
 */

/**
 * container initialization
 *
 * @param container_ptr_ptr A pointer to a container pointer. If you
 *        create a custom container, use this parameter to return it
 *        to the MFD helper. If set to NULL, the MFD helper will
 *        allocate a container for you.
 *
 *  This function is called at startup to allow you to customize certain
 *  aspects of the access method. For the most part, it is for advanced
 *  users. The default code should suffice for most cases. If no custom
 *  container is allocated, the MFD code will create one for your.
 *
 * @remark
 *  This would also be a good place to do any initialization needed
 *  for you data source. For example, opening a connection to another
 *  process that will supply the data, opening a database, etc.
 */
void
ipv6InterfaceTable_container_init(netsnmp_container **container_ptr_ptr)
{
    DEBUGMSGTL(("verbose:ipv6InterfaceTable:ipv6InterfaceTable_container_init", "called\n"));

    if (NULL == container_ptr_ptr) {
        snmp_log(LOG_ERR,
                 "bad container param to ipv6InterfaceTable_container_init\n");
        return;
    }

    /*
     * For advanced users, you can use a custom container. If you
     * do not create one, one will be created for you.
     */
    *container_ptr_ptr = NULL;

}                               /* ipv6InterfaceTable_container_init */

/**
 * @internal
 * determine if we want a ifTable row in our container
 */
void
ipv6InterfaceTable_check_entry_for_updates(const ifTable_rowreq_ctx *
                                           ift_rrc,
                                           netsnmp_interface_entry *entry)
{
    netsnmp_container *c = ipv6InterfaceTable_container_get();
    ifTable_rowreq_ctx *ip6if_rrc;
    int             changed = 0;

    DEBUGMSGTL(("verbose:ipv6InterfaceTable:check_entry_for_updates",
                "called\n"));

    /*
     * do we have a corresponding row?
     */
    ip6if_rrc = CONTAINER_FIND(c, ift_rrc);
    if (NULL == ip6if_rrc) {
        /*
         * no corresponding row. should we have one?
         */
        if ((NULL != entry) &&
            (entry->ns_flags & NETSNMP_INTERFACE_FLAGS_HAS_IPV6)) {
            /*
             * yes.
             */
            DEBUGMSGTL(("ipv6InterfaceTable:check_entry_for_updates",
                        "inserted row for index %d\n", entry->index));
            CONTAINER_INSERT(c, ift_rrc);
            changed = 1;
        }
    } else {
        /*
         * found corresponding row. is it still applicable?
         */
        if ((NULL == entry) ||
            (0 == (entry->ns_flags & NETSNMP_INTERFACE_FLAGS_HAS_IPV6))) {
            /*
             * no
             */
            DEBUGMSGTL(("ipv6InterfaceTable:check_entry_for_updates",
                        "removed  row for index %d\n",
                        ift_rrc->data.ifentry->index));
            CONTAINER_REMOVE(c, ift_rrc);
            changed = 1;
        } else {
            /*
             * still applicable. anything changed?
             */
            if (/** retransmit */
                   ((entry->
                     ns_flags & NETSNMP_INTERFACE_FLAGS_HAS_V6_RETRANSMIT)
                    && (entry->retransmit_v6 !=
                        ift_rrc->data.ifentry->retransmit_v6)) ||
                /** reasm */
                   ((entry->
                     ns_flags & NETSNMP_INTERFACE_FLAGS_HAS_V6_REASMMAX)
                    && (entry->reasm_max !=
                        ift_rrc->data.ifentry->reasm_max)) ||
                /** reachable time */
                   ((entry->
                     ns_flags & NETSNMP_INTERFACE_FLAGS_HAS_V6_REACHABLE)
                    && (entry->reachable_time !=
                        ift_rrc->data.ifentry->reachable_time)) ||
                /** if id */
                   ((entry->ns_flags & NETSNMP_INTERFACE_FLAGS_HAS_V6_IFID)
                    &&
                    ((entry->v6_if_id_len !=
                      ift_rrc->data.ifentry->v6_if_id_len)
                     || (0 !=
                         memcmp(entry->v6_if_id,
                                ift_rrc->data.ifentry->v6_if_id,
                                entry->v6_if_id_len)))) ||
                /** forwarding */
                   ((entry->
                     ns_flags & NETSNMP_INTERFACE_FLAGS_HAS_V6_FORWARDING)
                    && (entry->forwarding_v6 !=
                        ift_rrc->data.ifentry->forwarding_v6))) {
                DEBUGMSGTL(("ipv6InterfaceTable:check_entry_for_updates",
                            "row changed for index %d\n",
                            ift_rrc->data.ifentry->index));
                changed = 1;
            }
        }
    }

    /*
     * if something changed, update table last changed
     */
    if (changed)
        ipv6InterfaceTable_lastChange_set(netsnmp_get_agent_uptime());
}


/**
 * container shutdown
 *
 * @param container_ptr_ptr A pointer to the container.
 *
 *  This function is called at shutdown to allow you to customize certain
 *  aspects of the access method. For the most part, it is for advanced
 *  users. The default code should suffice for most cases.
 *
 *  This function is called before ipv6InterfaceTable_container_free().
 *
 * @remark
 *  This would also be a good place to do any cleanup needed
 *  for you data source. For example, closing a connection to another
 *  process that supplied the data, closing a database, etc.
 */
void
ipv6InterfaceTable_container_shutdown(netsnmp_container *container_ptr)
{
    DEBUGMSGTL(("verbose:ipv6InterfaceTable:ipv6InterfaceTable_container_shutdown", "called\n"));

    if (NULL == container_ptr) {
        snmp_log(LOG_ERR,
                 "bad params to ipv6InterfaceTable_container_shutdown\n");
        return;
    }

}                               /* ipv6InterfaceTable_container_shutdown */

/**
 * load initial data
 *
 * TODO:350:M: Implement ipv6InterfaceTable data load
 *
 * @param container container to which items should be inserted
 *
 * @retval MFD_SUCCESS              : success.
 * @retval MFD_RESOURCE_UNAVAILABLE : Can't access data source
 * @retval MFD_ERROR                : other error.
 *
 *  This function is called to load the index(es) (and data, optionally)
 *  for the every row in the data set.
 *
 * @remark
 *  While loading the data, the only important thing is the indexes.
 *  If access to your data is cheap/fast (e.g. you have a pointer to a
 *  structure in memory), it would make sense to update the data here.
 *  If, however, the accessing the data invovles more work (e.g. parsing
 *  some other existing data, or peforming calculations to derive the data),
 *  then you can limit yourself to setting the indexes and saving any
 *  information you will need later. Then use the saved information in
 *  ipv6InterfaceTable_row_prep() for populating data.
 *
 * @note
 *  If you need consistency between rows (like you want statistics
 *  for each row to be from the same time frame), you should set all
 *  data here.
 *
 */
int
ipv6InterfaceTable_container_load(netsnmp_container *container)
{
    ipv6InterfaceTable_rowreq_ctx *rowreq_ctx;
    size_t          count = 0;

    /*
     * temporary storage for index values
     */
    /*
     * ipv6InterfaceIfIndex(1)/InterfaceIndex/ASN_INTEGER/long(long)//l/a/w/e/R/d/H
     */
    long            ipv6InterfaceIfIndex;


    DEBUGMSGTL(("verbose:ipv6InterfaceTable:ipv6InterfaceTable_container_load", "called\n"));

    /*
     * TODO:351:M: |-> Load/update data in the ipv6InterfaceTable container.
     * loop over your ipv6InterfaceTable data, allocate a rowreq context,
     * set the index(es) [and data, optionally] and insert into
     * the container.
     */
    while (1) {
        /*
         * check for end of data; bail out if there is no more data
         */
        if (1)
            break;

        /*
         * TODO:352:M: |   |-> set indexes in new ipv6InterfaceTable rowreq context.
         * data context will be set from the param (unless NULL,
         *      in which case a new data context will be allocated)
         */
        rowreq_ctx = ipv6InterfaceTable_allocate_rowreq_ctx(NULL);
        if (NULL == rowreq_ctx) {
            snmp_log(LOG_ERR, "memory allocation failed\n");
            return MFD_RESOURCE_UNAVAILABLE;
        }
        if (MFD_SUCCESS !=
            ipv6InterfaceTable_indexes_set(rowreq_ctx,
                                           ipv6InterfaceIfIndex)) {
            snmp_log(LOG_ERR,
                     "error setting index while loading "
                     "ipv6InterfaceTable data.\n");
            ipv6InterfaceTable_release_rowreq_ctx(rowreq_ctx);
            continue;
        }

        /*
         * TODO:352:r: |   |-> populate ipv6InterfaceTable data context.
         * Populate data context here. (optionally, delay until row prep)
         */
        /*
         * non-TRANSIENT data: no need to copy. set pointer to data 
         */

        /*
         * insert into table container
         */
        CONTAINER_INSERT(container, rowreq_ctx);
        ++count;
    }

    DEBUGMSGT(("verbose:ipv6InterfaceTable:ipv6InterfaceTable_container_load", "inserted %d records\n", count));

    return MFD_SUCCESS;
}                               /* ipv6InterfaceTable_container_load */

/**
 * container clean up
 *
 * @param container container with all current items
 *
 *  This optional callback is called prior to all
 *  item's being removed from the container. If you
 *  need to do any processing before that, do it here.
 *
 * @note
 *  The MFD helper will take care of releasing all the row contexts.
 *
 */
void
ipv6InterfaceTable_container_free(netsnmp_container *container)
{
    DEBUGMSGTL(("verbose:ipv6InterfaceTable:ipv6InterfaceTable_container_free", "called\n"));

    /*
     * TODO:380:M: Free ipv6InterfaceTable container data.
     */
}                               /* ipv6InterfaceTable_container_free */

/**
 * prepare row for processing.
 *
 *  When the agent has located the row for a request, this function is
 *  called to prepare the row for processing. If you fully populated
 *  the data context during the index setup phase, you may not need to
 *  do anything.
 *
 * @param rowreq_ctx pointer to a context.
 *
 * @retval MFD_SUCCESS     : success.
 * @retval MFD_ERROR       : other error.
 */
int
ipv6InterfaceTable_row_prep(ipv6InterfaceTable_rowreq_ctx * rowreq_ctx)
{
    DEBUGMSGTL(("verbose:ipv6InterfaceTable:ipv6InterfaceTable_row_prep",
                "called\n"));

    netsnmp_assert(NULL != rowreq_ctx);

    /*
     * TODO:390:o: Prepare row for request.
     * If populating row data was delayed, this is the place to
     * fill in the row for this request.
     */

    return MFD_SUCCESS;
}                               /* ipv6InterfaceTable_row_prep */

/** @} */
