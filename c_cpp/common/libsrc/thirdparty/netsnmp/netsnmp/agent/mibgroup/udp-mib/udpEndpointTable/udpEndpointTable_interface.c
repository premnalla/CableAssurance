/*
 * Note: this file originally auto-generated by mib2c using
 *       version : 1.67 $ of : mfd-interface.m2c,v $ 
 *
 * $Id: udpEndpointTable_interface.c,v 1.1.1.1 2006/04/05 02:29:39 prem Exp $
 */
/*
 * *********************************************************************
 * *********************************************************************
 * *********************************************************************
 * ***                                                               ***
 * ***  NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE  ***
 * ***                                                               ***
 * ***                                                               ***
 * ***       THIS FILE DOES NOT CONTAIN ANY USER EDITABLE CODE.      ***
 * ***                                                               ***
 * ***                                                               ***
 * ***       THE GENERATED CODE IS INTERNAL IMPLEMENTATION, AND      ***
 * ***                                                               ***
 * ***                                                               ***
 * ***    IS SUBJECT TO CHANGE WITHOUT WARNING IN FUTURE RELEASES.   ***
 * ***                                                               ***
 * ***                                                               ***
 * *********************************************************************
 * *********************************************************************
 * *********************************************************************
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
#include "udpEndpointTable.h"


#include <net-snmp/agent/table_container.h>
#include <net-snmp/library/container.h>

#include "udpEndpointTable_interface.h"

#include <ctype.h>

/**********************************************************************
 **********************************************************************
 ***
 *** Table udpEndpointTable
 ***
 **********************************************************************
 **********************************************************************/
/*
 * UDP-MIB::udpEndpointTable is subid 7 of udp.
 * Its status is Current.
 * OID: .1.3.6.1.2.1.7.7, length: 8
 */
typedef struct udpEndpointTable_interface_ctx_s {

    netsnmp_container *container;
    netsnmp_cache  *cache;

    udpEndpointTable_registration *user_ctx;

    netsnmp_table_registration_info tbl_info;

    netsnmp_baby_steps_access_methods access_multiplexer;

} udpEndpointTable_interface_ctx;

static udpEndpointTable_interface_ctx udpEndpointTable_if_ctx;

static void
                _udpEndpointTable_container_init(udpEndpointTable_interface_ctx * if_ctx);
static void
                _udpEndpointTable_container_shutdown(udpEndpointTable_interface_ctx *
                                                     if_ctx);


netsnmp_container *
udpEndpointTable_container_get(void)
{
    return udpEndpointTable_if_ctx.container;
}

udpEndpointTable_registration *
udpEndpointTable_registration_get(void)
{
    return udpEndpointTable_if_ctx.user_ctx;
}

udpEndpointTable_registration *
udpEndpointTable_registration_set(udpEndpointTable_registration * newreg)
{
    udpEndpointTable_registration *old = udpEndpointTable_if_ctx.user_ctx;
    udpEndpointTable_if_ctx.user_ctx = newreg;
    return old;
}

int
udpEndpointTable_container_size(void)
{
    return CONTAINER_SIZE(udpEndpointTable_if_ctx.container);
}

/*
 * mfd multiplexer modes
 */
static Netsnmp_Node_Handler _mfd_udpEndpointTable_pre_request;
static Netsnmp_Node_Handler _mfd_udpEndpointTable_post_request;
static Netsnmp_Node_Handler _mfd_udpEndpointTable_object_lookup;
static Netsnmp_Node_Handler _mfd_udpEndpointTable_get_values;
/**
 * @internal
 * Initialize the table udpEndpointTable 
 *    (Define its contents and how it's structured)
 */
void
_udpEndpointTable_initialize_interface(udpEndpointTable_registration *
                                       reg_ptr, u_long flags)
{
    netsnmp_baby_steps_access_methods *access_multiplexer =
        &udpEndpointTable_if_ctx.access_multiplexer;
    netsnmp_table_registration_info *tbl_info =
        &udpEndpointTable_if_ctx.tbl_info;
    netsnmp_handler_registration *reginfo;
    netsnmp_mib_handler *handler;
    int             mfd_modes = 0;

    DEBUGMSGTL(("internal:udpEndpointTable:_udpEndpointTable_initialize_interface", "called\n"));


    /*************************************************
     *
     * save interface context for udpEndpointTable
     */
    /*
     * Setting up the table's definition
     */
    netsnmp_table_helper_add_indexes(tbl_info, ASN_INTEGER,
                                               /** index: udpEndpointLocalAddressType */
                                     ASN_OCTET_STR,
                                                 /** index: udpEndpointLocalAddress */
                                     ASN_UNSIGNED,
                                                /** index: udpEndpointLocalPort */
                                     ASN_INTEGER,
                                               /** index: udpEndpointRemoteAddressType */
                                     ASN_OCTET_STR,
                                                 /** index: udpEndpointRemoteAddress */
                                     ASN_UNSIGNED,
                                                /** index: udpEndpointRemotePort */
                                     ASN_UNSIGNED,
                                                /** index: udpEndpointInstance */
                                     0);

    /*
     * Define the minimum and maximum accessible columns.  This
     * optimizes retrival. 
     */
    tbl_info->min_column = UDPENDPOINTTABLE_MIN_COL;
    tbl_info->max_column = UDPENDPOINTTABLE_MAX_COL;

    /*
     * save users context
     */
    udpEndpointTable_if_ctx.user_ctx = reg_ptr;

    /*
     * call data access initialization code
     */
    udpEndpointTable_init_data(reg_ptr);

    /*
     * set up the container
     */
    _udpEndpointTable_container_init(&udpEndpointTable_if_ctx);
    if (NULL == udpEndpointTable_if_ctx.container) {
        snmp_log(LOG_ERR,
                 "could not initialize container for udpEndpointTable\n");
        return;
    }

    /*
     * access_multiplexer: REQUIRED wrapper for get request handling
     */
    access_multiplexer->object_lookup =
        _mfd_udpEndpointTable_object_lookup;
    access_multiplexer->get_values = _mfd_udpEndpointTable_get_values;

    /*
     * no wrappers yet
     */
    access_multiplexer->pre_request = _mfd_udpEndpointTable_pre_request;
    access_multiplexer->post_request = _mfd_udpEndpointTable_post_request;


    /*************************************************
     *
     * Create a registration, save our reg data, register table.
     */
    DEBUGMSGTL(("udpEndpointTable:init_udpEndpointTable",
                "Registering udpEndpointTable as a mibs-for-dummies table.\n"));
    handler =
        netsnmp_baby_steps_access_multiplexer_get(access_multiplexer);
    reginfo =
        netsnmp_handler_registration_create("udpEndpointTable", handler,
                                            udpEndpointTable_oid,
                                            udpEndpointTable_oid_size,
                                            HANDLER_CAN_BABY_STEP |
                                            HANDLER_CAN_RONLY);
    if (NULL == reginfo) {
        snmp_log(LOG_ERR, "error registering table udpEndpointTable\n");
        return;
    }
    reginfo->my_reg_void = &udpEndpointTable_if_ctx;

    /*************************************************
     *
     * set up baby steps handler, create it and inject it
     */
    if (access_multiplexer->object_lookup)
        mfd_modes |= BABY_STEP_OBJECT_LOOKUP;
    if (access_multiplexer->set_values)
        mfd_modes |= BABY_STEP_SET_VALUES;
    if (access_multiplexer->irreversible_commit)
        mfd_modes |= BABY_STEP_IRREVERSIBLE_COMMIT;
    if (access_multiplexer->object_syntax_checks)
        mfd_modes |= BABY_STEP_CHECK_OBJECT;

    if (access_multiplexer->pre_request)
        mfd_modes |= BABY_STEP_PRE_REQUEST;
    if (access_multiplexer->post_request)
        mfd_modes |= BABY_STEP_POST_REQUEST;

    if (access_multiplexer->undo_setup)
        mfd_modes |= BABY_STEP_UNDO_SETUP;
    if (access_multiplexer->undo_cleanup)
        mfd_modes |= BABY_STEP_UNDO_CLEANUP;
    if (access_multiplexer->undo_sets)
        mfd_modes |= BABY_STEP_UNDO_SETS;

    if (access_multiplexer->row_creation)
        mfd_modes |= BABY_STEP_ROW_CREATE;
    if (access_multiplexer->consistency_checks)
        mfd_modes |= BABY_STEP_CHECK_CONSISTENCY;
    if (access_multiplexer->commit)
        mfd_modes |= BABY_STEP_COMMIT;
    if (access_multiplexer->undo_commit)
        mfd_modes |= BABY_STEP_UNDO_COMMIT;

    handler = netsnmp_baby_steps_handler_get(mfd_modes);
    netsnmp_inject_handler(reginfo, handler);

    /*************************************************
     *
     * inject row_merge helper with prefix rootoid_len + 2 (entry.col)
     */
    handler = netsnmp_get_row_merge_handler(reginfo->rootoid_len + 2);
    netsnmp_inject_handler(reginfo, handler);

    /*************************************************
     *
     * inject container_table helper
     */
    handler =
        netsnmp_container_table_handler_get(tbl_info,
                                            udpEndpointTable_if_ctx.
                                            container,
                                            TABLE_CONTAINER_KEY_NETSNMP_INDEX);
    netsnmp_inject_handler(reginfo, handler);

    /*************************************************
     *
     * inject cache helper
     */
    if (NULL != udpEndpointTable_if_ctx.cache) {
        handler = netsnmp_cache_handler_get(udpEndpointTable_if_ctx.cache);
        netsnmp_inject_handler(reginfo, handler);
    }

    /*
     * register table
     */
    netsnmp_register_table(reginfo, tbl_info);

}                               /* _udpEndpointTable_initialize_interface */

/**
 * @internal
 * Shutdown the table udpEndpointTable
 */
void
_udpEndpointTable_shutdown_interface(udpEndpointTable_registration *
                                     reg_ptr)
{
    /*
     * shutdown the container
     */
    _udpEndpointTable_container_shutdown(&udpEndpointTable_if_ctx);
}

void
udpEndpointTable_valid_columns_set(netsnmp_column_info *vc)
{
    udpEndpointTable_if_ctx.tbl_info.valid_columns = vc;
}                               /* udpEndpointTable_valid_columns_set */

/**
 * @internal
 * convert the index component stored in the context to an oid
 */
int
udpEndpointTable_index_to_oid(netsnmp_index * oid_idx,
                              udpEndpointTable_mib_index * mib_idx)
{
    int             err = SNMP_ERR_NOERROR;

    /*
     * temp storage for parsing indexes
     */
    /*
     * udpEndpointLocalAddressType(1)/InetAddressType/ASN_INTEGER/long(u_long)//l/a/w/E/r/d/h
     */
    netsnmp_variable_list var_udpEndpointLocalAddressType;
    /*
     * udpEndpointLocalAddress(2)/InetAddress/ASN_OCTET_STR/char(char)//L/a/w/e/R/d/h
     */
    netsnmp_variable_list var_udpEndpointLocalAddress;
    /*
     * udpEndpointLocalPort(3)/InetPortNumber/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/H
     */
    netsnmp_variable_list var_udpEndpointLocalPort;
    /*
     * udpEndpointRemoteAddressType(4)/InetAddressType/ASN_INTEGER/long(u_long)//l/a/w/E/r/d/h
     */
    netsnmp_variable_list var_udpEndpointRemoteAddressType;
    /*
     * udpEndpointRemoteAddress(5)/InetAddress/ASN_OCTET_STR/char(char)//L/a/w/e/R/d/h
     */
    netsnmp_variable_list var_udpEndpointRemoteAddress;
    /*
     * udpEndpointRemotePort(6)/InetPortNumber/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/H
     */
    netsnmp_variable_list var_udpEndpointRemotePort;
    /*
     * udpEndpointInstance(7)/UNSIGNED32/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/h
     */
    netsnmp_variable_list var_udpEndpointInstance;

    /*
     * set up varbinds
     */
    memset(&var_udpEndpointLocalAddressType, 0x00,
           sizeof(var_udpEndpointLocalAddressType));
    var_udpEndpointLocalAddressType.type = ASN_INTEGER;
    memset(&var_udpEndpointLocalAddress, 0x00,
           sizeof(var_udpEndpointLocalAddress));
    var_udpEndpointLocalAddress.type = ASN_OCTET_STR;
    memset(&var_udpEndpointLocalPort, 0x00,
           sizeof(var_udpEndpointLocalPort));
    var_udpEndpointLocalPort.type = ASN_UNSIGNED;
    memset(&var_udpEndpointRemoteAddressType, 0x00,
           sizeof(var_udpEndpointRemoteAddressType));
    var_udpEndpointRemoteAddressType.type = ASN_INTEGER;
    memset(&var_udpEndpointRemoteAddress, 0x00,
           sizeof(var_udpEndpointRemoteAddress));
    var_udpEndpointRemoteAddress.type = ASN_OCTET_STR;
    memset(&var_udpEndpointRemotePort, 0x00,
           sizeof(var_udpEndpointRemotePort));
    var_udpEndpointRemotePort.type = ASN_UNSIGNED;
    memset(&var_udpEndpointInstance, 0x00,
           sizeof(var_udpEndpointInstance));
    var_udpEndpointInstance.type = ASN_UNSIGNED;

    /*
     * chain temp index varbinds together
     */
    var_udpEndpointLocalAddressType.next_variable =
        &var_udpEndpointLocalAddress;
    var_udpEndpointLocalAddress.next_variable = &var_udpEndpointLocalPort;
    var_udpEndpointLocalPort.next_variable =
        &var_udpEndpointRemoteAddressType;
    var_udpEndpointRemoteAddressType.next_variable =
        &var_udpEndpointRemoteAddress;
    var_udpEndpointRemoteAddress.next_variable =
        &var_udpEndpointRemotePort;
    var_udpEndpointRemotePort.next_variable = &var_udpEndpointInstance;
    var_udpEndpointInstance.next_variable = NULL;


    DEBUGMSGTL(("verbose:udpEndpointTable:udpEndpointTable_index_to_oid",
                "called\n"));

    /*
     * udpEndpointLocalAddressType(1)/InetAddressType/ASN_INTEGER/long(u_long)//l/a/w/E/r/d/h 
     */
    snmp_set_var_value(&var_udpEndpointLocalAddressType,
                       (u_char *) & mib_idx->udpEndpointLocalAddressType,
                       sizeof(mib_idx->udpEndpointLocalAddressType));

    /*
     * udpEndpointLocalAddress(2)/InetAddress/ASN_OCTET_STR/char(char)//L/a/w/e/R/d/h 
     */
    snmp_set_var_value(&var_udpEndpointLocalAddress,
                       (u_char *) & mib_idx->udpEndpointLocalAddress,
                       mib_idx->udpEndpointLocalAddress_len *
                       sizeof(mib_idx->udpEndpointLocalAddress[0]));

    /*
     * udpEndpointLocalPort(3)/InetPortNumber/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/H 
     */
    snmp_set_var_value(&var_udpEndpointLocalPort,
                       (u_char *) & mib_idx->udpEndpointLocalPort,
                       sizeof(mib_idx->udpEndpointLocalPort));

    /*
     * udpEndpointRemoteAddressType(4)/InetAddressType/ASN_INTEGER/long(u_long)//l/a/w/E/r/d/h 
     */
    snmp_set_var_value(&var_udpEndpointRemoteAddressType,
                       (u_char *) & mib_idx->udpEndpointRemoteAddressType,
                       sizeof(mib_idx->udpEndpointRemoteAddressType));

    /*
     * udpEndpointRemoteAddress(5)/InetAddress/ASN_OCTET_STR/char(char)//L/a/w/e/R/d/h 
     */
    snmp_set_var_value(&var_udpEndpointRemoteAddress,
                       (u_char *) & mib_idx->udpEndpointRemoteAddress,
                       mib_idx->udpEndpointRemoteAddress_len *
                       sizeof(mib_idx->udpEndpointRemoteAddress[0]));

    /*
     * udpEndpointRemotePort(6)/InetPortNumber/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/H 
     */
    snmp_set_var_value(&var_udpEndpointRemotePort,
                       (u_char *) & mib_idx->udpEndpointRemotePort,
                       sizeof(mib_idx->udpEndpointRemotePort));

    /*
     * udpEndpointInstance(7)/UNSIGNED32/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/h 
     */
    snmp_set_var_value(&var_udpEndpointInstance,
                       (u_char *) & mib_idx->udpEndpointInstance,
                       sizeof(mib_idx->udpEndpointInstance));


    err = build_oid_noalloc(oid_idx->oids, oid_idx->len, &oid_idx->len,
                            NULL, 0, &var_udpEndpointLocalAddressType);
    if (err)
        snmp_log(LOG_ERR, "error %d converting index to oid\n", err);

    /*
     * parsing may have allocated memory. free it.
     */
    snmp_reset_var_buffers(&var_udpEndpointLocalAddressType);

    return err;
}                               /* udpEndpointTable_index_to_oid */

/**
 * extract udpEndpointTable indexes from a netsnmp_index
 *
 * @retval SNMP_ERR_NOERROR  : no error
 * @retval SNMP_ERR_GENERR   : error
 */
int
udpEndpointTable_index_from_oid(netsnmp_index * oid_idx,
                                udpEndpointTable_mib_index * mib_idx)
{
    int             err = SNMP_ERR_NOERROR;

    /*
     * temp storage for parsing indexes
     */
    /*
     * udpEndpointLocalAddressType(1)/InetAddressType/ASN_INTEGER/long(u_long)//l/a/w/E/r/d/h
     */
    netsnmp_variable_list var_udpEndpointLocalAddressType;
    /*
     * udpEndpointLocalAddress(2)/InetAddress/ASN_OCTET_STR/char(char)//L/a/w/e/R/d/h
     */
    netsnmp_variable_list var_udpEndpointLocalAddress;
    /*
     * udpEndpointLocalPort(3)/InetPortNumber/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/H
     */
    netsnmp_variable_list var_udpEndpointLocalPort;
    /*
     * udpEndpointRemoteAddressType(4)/InetAddressType/ASN_INTEGER/long(u_long)//l/a/w/E/r/d/h
     */
    netsnmp_variable_list var_udpEndpointRemoteAddressType;
    /*
     * udpEndpointRemoteAddress(5)/InetAddress/ASN_OCTET_STR/char(char)//L/a/w/e/R/d/h
     */
    netsnmp_variable_list var_udpEndpointRemoteAddress;
    /*
     * udpEndpointRemotePort(6)/InetPortNumber/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/H
     */
    netsnmp_variable_list var_udpEndpointRemotePort;
    /*
     * udpEndpointInstance(7)/UNSIGNED32/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/h
     */
    netsnmp_variable_list var_udpEndpointInstance;

    /*
     * set up varbinds
     */
    memset(&var_udpEndpointLocalAddressType, 0x00,
           sizeof(var_udpEndpointLocalAddressType));
    var_udpEndpointLocalAddressType.type = ASN_INTEGER;
    memset(&var_udpEndpointLocalAddress, 0x00,
           sizeof(var_udpEndpointLocalAddress));
    var_udpEndpointLocalAddress.type = ASN_OCTET_STR;
    memset(&var_udpEndpointLocalPort, 0x00,
           sizeof(var_udpEndpointLocalPort));
    var_udpEndpointLocalPort.type = ASN_UNSIGNED;
    memset(&var_udpEndpointRemoteAddressType, 0x00,
           sizeof(var_udpEndpointRemoteAddressType));
    var_udpEndpointRemoteAddressType.type = ASN_INTEGER;
    memset(&var_udpEndpointRemoteAddress, 0x00,
           sizeof(var_udpEndpointRemoteAddress));
    var_udpEndpointRemoteAddress.type = ASN_OCTET_STR;
    memset(&var_udpEndpointRemotePort, 0x00,
           sizeof(var_udpEndpointRemotePort));
    var_udpEndpointRemotePort.type = ASN_UNSIGNED;
    memset(&var_udpEndpointInstance, 0x00,
           sizeof(var_udpEndpointInstance));
    var_udpEndpointInstance.type = ASN_UNSIGNED;

    /*
     * chain temp index varbinds together
     */
    var_udpEndpointLocalAddressType.next_variable =
        &var_udpEndpointLocalAddress;
    var_udpEndpointLocalAddress.next_variable = &var_udpEndpointLocalPort;
    var_udpEndpointLocalPort.next_variable =
        &var_udpEndpointRemoteAddressType;
    var_udpEndpointRemoteAddressType.next_variable =
        &var_udpEndpointRemoteAddress;
    var_udpEndpointRemoteAddress.next_variable =
        &var_udpEndpointRemotePort;
    var_udpEndpointRemotePort.next_variable = &var_udpEndpointInstance;
    var_udpEndpointInstance.next_variable = NULL;


    DEBUGMSGTL(("verbose:udpEndpointTable:udpEndpointTable_index_from_oid",
                "called\n"));

    /*
     * parse the oid into the individual index components
     */
    err = parse_oid_indexes(oid_idx->oids, oid_idx->len,
                            &var_udpEndpointLocalAddressType);
    if (err == SNMP_ERR_NOERROR) {
        /*
         * copy out values
         */
        mib_idx->udpEndpointLocalAddressType =
            *((u_long *) var_udpEndpointLocalAddressType.val.string);
        /*
         * NOTE: val_len is in bytes, udpEndpointLocalAddress_len might not be
         */
        if (var_udpEndpointLocalAddress.val_len >
            sizeof(mib_idx->udpEndpointLocalAddress))
            err = SNMP_ERR_GENERR;
        else {
            memcpy(mib_idx->udpEndpointLocalAddress,
                   var_udpEndpointLocalAddress.val.string,
                   var_udpEndpointLocalAddress.val_len);
            mib_idx->udpEndpointLocalAddress_len =
                var_udpEndpointLocalAddress.val_len /
                sizeof(mib_idx->udpEndpointLocalAddress[0]);
        }
        mib_idx->udpEndpointLocalPort =
            *((u_long *) var_udpEndpointLocalPort.val.string);
        mib_idx->udpEndpointRemoteAddressType =
            *((u_long *) var_udpEndpointRemoteAddressType.val.string);
        /*
         * NOTE: val_len is in bytes, udpEndpointRemoteAddress_len might not be
         */
        if (var_udpEndpointRemoteAddress.val_len >
            sizeof(mib_idx->udpEndpointRemoteAddress))
            err = SNMP_ERR_GENERR;
        else {
            memcpy(mib_idx->udpEndpointRemoteAddress,
                   var_udpEndpointRemoteAddress.val.string,
                   var_udpEndpointRemoteAddress.val_len);
            mib_idx->udpEndpointRemoteAddress_len =
                var_udpEndpointRemoteAddress.val_len /
                sizeof(mib_idx->udpEndpointRemoteAddress[0]);
        }
        mib_idx->udpEndpointRemotePort =
            *((u_long *) var_udpEndpointRemotePort.val.string);
        mib_idx->udpEndpointInstance =
            *((u_long *) var_udpEndpointInstance.val.string);


    }

    /*
     * parsing may have allocated memory. free it.
     */
    snmp_reset_var_buffers(&var_udpEndpointLocalAddressType);

    return err;
}                               /* udpEndpointTable_index_from_oid */


/*
 *********************************************************************
 * @internal
 * allocate resources for a udpEndpointTable_rowreq_ctx
 */
udpEndpointTable_rowreq_ctx *
udpEndpointTable_allocate_rowreq_ctx(void)
{
    udpEndpointTable_rowreq_ctx *rowreq_ctx =
        SNMP_MALLOC_TYPEDEF(udpEndpointTable_rowreq_ctx);

    DEBUGMSGTL(("internal:udpEndpointTable:udpEndpointTable_allocate_rowreq_ctx", "called\n"));

    if (NULL == rowreq_ctx) {
        snmp_log(LOG_ERR, "Couldn't allocate memory for a "
                 "udpEndpointTable_rowreq_ctx.\n");
    }

    rowreq_ctx->oid_idx.oids = rowreq_ctx->oid_tmp;

    rowreq_ctx->udpEndpointTable_data_list = NULL;


    return rowreq_ctx;
}                               /* udpEndpointTable_allocate_rowreq_ctx */

/*
 * @internal
 * release resources for a udpEndpointTable_rowreq_ctx
 */
void
udpEndpointTable_release_rowreq_ctx(udpEndpointTable_rowreq_ctx *
                                    rowreq_ctx)
{
    DEBUGMSGTL(("internal:udpEndpointTable:udpEndpointTable_release_rowreq_ctx", "called\n"));

    netsnmp_assert(NULL != rowreq_ctx);


    /*
     * free index oid pointer
     */
    if (rowreq_ctx->oid_idx.oids != rowreq_ctx->oid_tmp)
        free(rowreq_ctx->oid_idx.oids);

    SNMP_FREE(rowreq_ctx);
}                               /* udpEndpointTable_release_rowreq_ctx */

/**
 * @internal
 * wrapper
 */
static int
_mfd_udpEndpointTable_pre_request(netsnmp_mib_handler *handler,
                                  netsnmp_handler_registration *reginfo,
                                  netsnmp_agent_request_info *agtreq_info,
                                  netsnmp_request_info *requests)
{
    int             rc;

    DEBUGMSGTL(("internal:udpEndpointTable:_mfd_udpEndpointTable_pre_request", "called\n"));

    if (1 != netsnmp_row_merge_status_first(reginfo, agtreq_info)) {
        DEBUGMSGTL(("internal:udpEndpointTable",
                    "skipping additional pre_request\n"));
        return SNMP_ERR_NOERROR;
    }

    rc = udpEndpointTable_pre_request(udpEndpointTable_if_ctx.user_ctx);
    if (MFD_SUCCESS != rc) {
        /*
         * nothing we can do about it but log it
         */
        DEBUGMSGTL(("udpEndpointTable", "error %d from "
                    "udpEndpointTable_pre_request\n", rc));
        netsnmp_request_set_error_all(requests, SNMP_VALIDATE_ERR(rc));
    }

    return SNMP_ERR_NOERROR;
}                               /* _mfd_udpEndpointTable_pre_request */

/**
 * @internal
 * wrapper
 */
static int
_mfd_udpEndpointTable_post_request(netsnmp_mib_handler *handler,
                                   netsnmp_handler_registration *reginfo,
                                   netsnmp_agent_request_info *agtreq_info,
                                   netsnmp_request_info *requests)
{
    udpEndpointTable_rowreq_ctx *rowreq_ctx =
        netsnmp_container_table_row_extract(requests);
    int             rc, packet_rc;

    DEBUGMSGTL(("internal:udpEndpointTable:_mfd_udpEndpointTable_post_request", "called\n"));

    /*
     * release row context, if deleted
     */
    if (rowreq_ctx && (rowreq_ctx->rowreq_flags & MFD_ROW_DELETED))
        udpEndpointTable_release_rowreq_ctx(rowreq_ctx);

    /*
     * wait for last call before calling user
     */
    if (1 != netsnmp_row_merge_status_last(reginfo, agtreq_info)) {
        DEBUGMSGTL(("internal:udpEndpointTable",
                    "waiting for last post_request\n"));
        return SNMP_ERR_NOERROR;
    }

    packet_rc = netsnmp_check_all_requests_error(agtreq_info->asp, 0);
    rc = udpEndpointTable_post_request(udpEndpointTable_if_ctx.user_ctx,
                                       packet_rc);
    if (MFD_SUCCESS != rc) {
        /*
         * nothing we can do about it but log it
         */
        DEBUGMSGTL(("udpEndpointTable", "error %d from "
                    "udpEndpointTable_post_request\n", rc));
    }

    return SNMP_ERR_NOERROR;
}                               /* _mfd_udpEndpointTable_post_request */

/**
 * @internal
 * wrapper
 */
static int
_mfd_udpEndpointTable_object_lookup(netsnmp_mib_handler *handler,
                                    netsnmp_handler_registration *reginfo,
                                    netsnmp_agent_request_info
                                    *agtreq_info,
                                    netsnmp_request_info *requests)
{
    int             rc = SNMP_ERR_NOERROR;
    udpEndpointTable_rowreq_ctx *rowreq_ctx =
        netsnmp_container_table_row_extract(requests);

    DEBUGMSGTL(("internal:udpEndpointTable:_mfd_udpEndpointTable_object_lookup", "called\n"));

    /*
     * get our context from mfd
     * udpEndpointTable_interface_ctx *if_ctx =
     *             (udpEndpointTable_interface_ctx *)reginfo->my_reg_void;
     */

    if (NULL == rowreq_ctx) {
        rc = SNMP_ERR_NOCREATION;
    }

    if (MFD_SUCCESS != rc)
        netsnmp_request_set_error_all(requests, rc);
    else
        udpEndpointTable_row_prep(rowreq_ctx);

    return SNMP_VALIDATE_ERR(rc);
}                               /* _mfd_udpEndpointTable_object_lookup */

/***********************************************************************
 *
 * GET processing
 *
 ***********************************************************************/
/*
 * @internal
 * Retrieve the value for a particular column
 */
NETSNMP_STATIC_INLINE int
_udpEndpointTable_get_column(udpEndpointTable_rowreq_ctx * rowreq_ctx,
                             netsnmp_variable_list * var, int column)
{
    int             rc = SNMPERR_SUCCESS;

    DEBUGMSGTL(("internal:udpEndpointTable:_mfd_udpEndpointTable_get_column", "called for %d\n", column));


    netsnmp_assert(NULL != rowreq_ctx);

    switch (column) {

        /*
         * udpEndpointProcess(8)/UNSIGNED32/ASN_UNSIGNED/u_long(u_long)//l/A/w/e/r/d/h 
         */
    case COLUMN_UDPENDPOINTPROCESS:
        var->val_len = sizeof(u_long);
        var->type = ASN_UNSIGNED;
        rc = udpEndpointProcess_get(rowreq_ctx,
                                    (u_long *) var->val.string);
        break;

    default:
        snmp_log(LOG_ERR,
                 "unknown column %d in _udpEndpointTable_get_column\n",
                 column);
        break;
    }

    return rc;
}                               /* _udpEndpointTable_get_column */

int
_mfd_udpEndpointTable_get_values(netsnmp_mib_handler *handler,
                                 netsnmp_handler_registration *reginfo,
                                 netsnmp_agent_request_info *agtreq_info,
                                 netsnmp_request_info *requests)
{
    udpEndpointTable_rowreq_ctx *rowreq_ctx =
        netsnmp_container_table_row_extract(requests);
    netsnmp_table_request_info *tri;
    u_char         *old_string;
    void            (*dataFreeHook) (void *);
    int             rc;

    DEBUGMSGTL(("internal:udpEndpointTable:_mfd_udpEndpointTable_get_values", "called\n"));

    netsnmp_assert(NULL != rowreq_ctx);

    for (; requests; requests = requests->next) {
        /*
         * save old pointer, so we can free it if replaced
         */
        old_string = requests->requestvb->val.string;
        dataFreeHook = requests->requestvb->dataFreeHook;
        if (NULL == requests->requestvb->val.string) {
            requests->requestvb->val.string = requests->requestvb->buf;
            requests->requestvb->val_len =
                sizeof(requests->requestvb->buf);
        } else if (requests->requestvb->buf ==
                   requests->requestvb->val.string) {
            if (requests->requestvb->val_len !=
                sizeof(requests->requestvb->buf))
                requests->requestvb->val_len =
                    sizeof(requests->requestvb->buf);
        }

        /*
         * get column data
         */
        tri = netsnmp_extract_table_info(requests);
        if (NULL == tri)
            continue;

        rc = _udpEndpointTable_get_column(rowreq_ctx, requests->requestvb,
                                          tri->colnum);
        if (rc) {
            if (MFD_SKIP == rc) {
                requests->requestvb->type = SNMP_NOSUCHINSTANCE;
                rc = SNMP_ERR_NOERROR;
            }
        } else if (NULL == requests->requestvb->val.string) {
            snmp_log(LOG_ERR, "NULL varbind data pointer!\n");
            rc = SNMP_ERR_GENERR;
        }
        if (rc)
            netsnmp_request_set_error(requests, SNMP_VALIDATE_ERR(rc));

        /*
         * if the buffer wasn't used previously for the old data (i.e. it
         * was allcoated memory)  and the get routine replaced the pointer,
         * we need to free the previous pointer.
         */
        if (old_string && (old_string != requests->requestvb->buf) &&
            (requests->requestvb->val.string != old_string)) {
            if (dataFreeHook)
                (*dataFreeHook) (old_string);
            else
                free(old_string);
        }
    }                           /* for results */

    return SNMP_ERR_NOERROR;
}                               /* _mfd_udpEndpointTable_get_values */


/***********************************************************************
 *
 * SET processing
 *
 ***********************************************************************/

/*
 * SET PROCESSING NOT APPLICABLE (per MIB or user setting)
 */
/***********************************************************************
 *
 * DATA ACCESS
 *
 ***********************************************************************/
static void     _container_free(netsnmp_container *container);

/**
 * @internal
 */
static int
_cache_load(netsnmp_cache * cache, void *vmagic)
{
    DEBUGMSGTL(("internal:udpEndpointTable:_cache_load", "called\n"));

    if ((NULL == cache) || (NULL == cache->magic)) {
        snmp_log(LOG_ERR,
                 "invalid cache for udpEndpointTable_cache_load\n");
        return -1;
    }

    /** should only be called for an invalid or expired cache */
    netsnmp_assert((0 == cache->valid) || (1 == cache->expired));

    /*
     * call user code
     */
    return udpEndpointTable_container_load((netsnmp_container *) cache->
                                           magic);
}                               /* _cache_load */

/**
 * @internal
 */
static void
_cache_free(netsnmp_cache * cache, void *magic)
{
    netsnmp_container *container;

    DEBUGMSGTL(("internal:udpEndpointTable:_cache_free", "called\n"));

    if ((NULL == cache) || (NULL == cache->magic)) {
        snmp_log(LOG_ERR,
                 "invalid cache in udpEndpointTable_cache_free\n");
        return;
    }

    container = (netsnmp_container *) cache->magic;

    _container_free(container);
}                               /* _cache_free */

/**
 * @internal
 */
static void
_container_item_free(udpEndpointTable_rowreq_ctx * rowreq_ctx,
                     void *context)
{
    DEBUGMSGTL(("internal:udpEndpointTable:_container_item_free",
                "called\n"));

    if (NULL == rowreq_ctx)
        return;

    udpEndpointTable_release_rowreq_ctx(rowreq_ctx);
}                               /* _container_item_free */

/**
 * @internal
 */
static void
_container_free(netsnmp_container *container)
{
    DEBUGMSGTL(("internal:udpEndpointTable:_container_free", "called\n"));

    if (NULL == container) {
        snmp_log(LOG_ERR,
                 "invalid container in udpEndpointTable_container_free\n");
        return;
    }

    /*
     * call user code
     */
    udpEndpointTable_container_free(container);

    /*
     * free all items. inefficient, but easy.
     */
    CONTAINER_CLEAR(container,
                    (netsnmp_container_obj_func *) _container_item_free,
                    NULL);
}                               /* _container_free */

/**
 * @internal
 * initialize the container with functions or wrappers
 */
void
_udpEndpointTable_container_init(udpEndpointTable_interface_ctx * if_ctx)
{
    DEBUGMSGTL(("internal:udpEndpointTable:_udpEndpointTable_container_init", "called\n"));

    /*
     * cache init
     */
    if_ctx->cache = netsnmp_cache_create(30,    /* timeout in seconds */
                                         _cache_load, _cache_free,
                                         udpEndpointTable_oid,
                                         udpEndpointTable_oid_size);

    if (NULL == if_ctx->cache) {
        snmp_log(LOG_ERR, "error creating cache for udpEndpointTable\n");
        return;
    }

    if_ctx->cache->flags = NETSNMP_CACHE_DONT_INVALIDATE_ON_SET;

    udpEndpointTable_container_init(&if_ctx->container, if_ctx->cache);
    if (NULL == if_ctx->container)
        if_ctx->container =
            netsnmp_container_find("udpEndpointTable:table_container");
    if (NULL == if_ctx->container) {
        snmp_log(LOG_ERR, "error creating container in "
                 "udpEndpointTable_container_init\n");
        return;
    }

    if (NULL != if_ctx->cache)
        if_ctx->cache->magic = (void *) if_ctx->container;
}                               /* _udpEndpointTable_container_init */

/**
 * @internal
 * shutdown the container with functions or wrappers
 */
void
_udpEndpointTable_container_shutdown(udpEndpointTable_interface_ctx *
                                     if_ctx)
{
    DEBUGMSGTL(("internal:udpEndpointTable:_udpEndpointTable_container_shutdown", "called\n"));

    udpEndpointTable_container_shutdown(if_ctx->container);

    _container_free(if_ctx->container);

}                               /* _udpEndpointTable_container_shutdown */


udpEndpointTable_rowreq_ctx *
udpEndpointTable_row_find_by_mib_index(udpEndpointTable_mib_index *
                                       mib_idx)
{
    udpEndpointTable_rowreq_ctx *rowreq_ctx;
    oid             oid_tmp[MAX_OID_LEN];
    netsnmp_index   oid_idx;
    int             rc;

    /*
     * set up storage for OID
     */
    oid_idx.oids = oid_tmp;
    oid_idx.len = sizeof(oid_tmp) / sizeof(oid);

    /*
     * convert
     */
    rc = udpEndpointTable_index_to_oid(&oid_idx, mib_idx);
    if (MFD_SUCCESS != rc)
        return NULL;

    rowreq_ctx =
        CONTAINER_FIND(udpEndpointTable_if_ctx.container, &oid_idx);

    return rowreq_ctx;
}
