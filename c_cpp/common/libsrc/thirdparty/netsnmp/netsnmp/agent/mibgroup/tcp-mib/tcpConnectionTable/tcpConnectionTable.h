/*
 * Note: this file originally auto-generated by mib2c using
 *       version : 1.48 $ of : mfd-top.m2c,v $
 *
 * $Id: tcpConnectionTable.h,v 1.1.1.1 2006/04/05 02:29:38 prem Exp $
 */
#ifndef TCPCONNECTIONTABLE_H
#define TCPCONNECTIONTABLE_H

#ifdef __cplusplus
extern          "C" {
#endif


/** @defgroup misc misc: Miscelaneous routines
 *
 * @{
 */
#include <net-snmp/library/asn1.h>
#include <net-snmp/data_access/tcpConn.h>

    /*
     * other required module components 
     */
    /* *INDENT-OFF*  */
config_require(tcp-mib/data_access/tcpConn)
config_require(tcp-mib/tcpConnectionTable/tcpConnectionTable_interface)
config_require(tcp-mib/tcpConnectionTable/tcpConnectionTable_data_access)
    /* *INDENT-ON*  */

    /*
     * OID, column number and enum definions for tcpConnectionTable 
     */
#include "tcpConnectionTable_constants.h"

    /*
     *********************************************************************
     * function declarations
     */
    void            init_tcpConnectionTable(void);
    void            shutdown_tcpConnectionTable(void);

    /*
     *********************************************************************
     * Table declarations
     */
/**********************************************************************
 **********************************************************************
 ***
 *** Table tcpConnectionTable
 ***
 **********************************************************************
 **********************************************************************/
    /*
     * TCP-MIB::tcpConnectionTable is subid 19 of tcp.
     * Its status is Current.
     * OID: .1.3.6.1.2.1.6.19, length: 8
     */
    /*
     *********************************************************************
     * When you register your mib, you get to provide a generic
     * pointer that will be passed back to you for most of the
     * functions calls.
     *
     * TODO:100:r: Review all context structures
     */
    /*
     * TODO:101:o: |-> Review tcpConnectionTable registration context.
     */
    typedef struct tConnectionT_user_ctx {

        void           *dummy;

    } tcpConnectionTable_registration;

/**********************************************************************/
    /*
     * TODO:110:r: |-> Review tcpConnectionTable data context structure.
     * This structure is used to represent the data for tcpConnectionTable.
     */
    typedef netsnmp_tcpconn_entry tcpConnectionTable_data;


    /*
     *********************************************************************
     * TODO:115:o: |-> Review tcpConnectionTable undo context.
     * We're just going to use the same data structure for our
     * undo_context. If you want to do something more efficent,
     * define your typedef here.
     */
    typedef tcpConnectionTable_data tcpConnectionTable_undo_data;

    /*
     * TODO:120:r: |-> Review tcpConnectionTable mib index.
     * This structure is used to represent the index for tcpConnectionTable.
     */
    typedef struct tcpConnectionTable_mib_index_s {

        /*
         * tcpConnectionLocalAddressType(1)/InetAddressType/ASN_INTEGER/long(u_long)//l/a/w/E/r/d/h
         */
        u_long          tcpConnectionLocalAddressType;

        /*
         * tcpConnectionLocalAddress(2)/InetAddress/ASN_OCTET_STR/char(char)//L/a/w/e/R/d/h
         */
        /** 128 - 5(other indexes) - oid length(10) = 112 */
        /** but 20 = size of ipv6z address, longest supported type */
        char            tcpConnectionLocalAddress[20];
        size_t          tcpConnectionLocalAddress_len;

        /*
         * tcpConnectionLocalPort(3)/InetPortNumber/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/H
         */
        u_long          tcpConnectionLocalPort;

        /*
         * tcpConnectionRemAddressType(4)/InetAddressType/ASN_INTEGER/long(u_long)//l/a/w/E/r/d/h
         */
        u_long          tcpConnectionRemAddressType;

        /*
         * tcpConnectionRemAddress(5)/InetAddress/ASN_OCTET_STR/char(char)//L/a/w/e/R/d/h
         */
        /** 128 - 5(other indexes) - oid length(10) = 112 */
        /** but 20 = size of ipv6z address, longest supported type */
        char            tcpConnectionRemAddress[20];
        size_t          tcpConnectionRemAddress_len;

        /*
         * tcpConnectionRemPort(6)/InetPortNumber/ASN_UNSIGNED/u_long(u_long)//l/a/w/e/R/d/H
         */
        u_long          tcpConnectionRemPort;


    } tcpConnectionTable_mib_index;

    /*
     * TODO:121:r: |   |-> Review tcpConnectionTable max index length.
     * If you KNOW that your indexes will never exceed a certain
     * length, update this macro to that length.
     *
     * BE VERY CAREFUL TO TAKE INTO ACCOUNT THE MAXIMUM
     * POSSIBLE LENGHT FOR EVERY VARIABLE LENGTH INDEX!
     * Guessing 128 - col/entry(2)  - oid len(8)
     */
#define MAX_tcpConnectionTable_IDX_LEN     44


    /*
     *********************************************************************
     * TODO:130:o: |-> Review tcpConnectionTable Row request (rowreq) context.
     * When your functions are called, you will be passed a
     * tcpConnectionTable_rowreq_ctx pointer.
     */
    typedef struct tcpConnectionTable_rowreq_ctx_s {

    /** this must be first for container compare to work */
        netsnmp_index   oid_idx;
        oid             oid_tmp[MAX_tcpConnectionTable_IDX_LEN];

        tcpConnectionTable_mib_index tbl_idx;

        tcpConnectionTable_data *data;
        tcpConnectionTable_undo_data *undo;
        unsigned int    column_set_flags;       /* flags for set columns */


        /*
         * flags per row. Currently, the first (lower) 8 bits are reserved
         * for the user. See mfd.h for other flags.
         */
        u_int           rowreq_flags;

        /*
         * TODO:131:o: |   |-> Add useful data to tcpConnectionTable rowreq context.
         */

        /*
         * storage for future expansion
         */
        netsnmp_data_list *tcpConnectionTable_data_list;

    } tcpConnectionTable_rowreq_ctx;

    typedef struct tcpConnectionTable_ref_rowreq_ctx_s {
        tcpConnectionTable_rowreq_ctx *rowreq_ctx;
    } tcpConnectionTable_ref_rowreq_ctx;

    /*
     *********************************************************************
     * function prototypes
     */
    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_pre_request(tcpConnectionTable_registration *
                                       user_context);
    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_post_request(tcpConnectionTable_registration *
                                        user_context, int rc);

    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_rowreq_ctx_init(tcpConnectionTable_rowreq_ctx *
                                           rowreq_ctx,
                                           void *user_init_ctx);
    void
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_rowreq_ctx_cleanup(tcpConnectionTable_rowreq_ctx
                                              * rowreq_ctx);

    tcpConnectionTable_data *tcpConnectionTable_allocate_data(void);
    void            tcpConnectionTable_release_data(tcpConnectionTable_data
                                                    * data);

    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_check_dependencies(tcpConnectionTable_rowreq_ctx
                                              * rowreq_ctx);
    int             tcpConnectionTable_commit(tcpConnectionTable_rowreq_ctx
                                              * rowreq_ctx);

         
         
         
         
         
         
        tcpConnectionTable_rowreq_ctx
        * tcpConnectionTable_row_find_by_mib_index
        (tcpConnectionTable_mib_index * mib_idx);

    extern oid      tcpConnectionTable_oid[];
    extern int      tcpConnectionTable_oid_size;


#include "tcpConnectionTable_interface.h"
#include "tcpConnectionTable_data_access.h"
    /*
     *********************************************************************
     * GET function declarations
     */

    /*
     *********************************************************************
     * GET Table declarations
     */
/**********************************************************************
 **********************************************************************
 ***
 *** Table tcpConnectionTable
 ***
 **********************************************************************
 **********************************************************************/
    /*
     * TCP-MIB::tcpConnectionTable is subid 19 of tcp.
     * Its status is Current.
     * OID: .1.3.6.1.2.1.6.19, length: 8
     */
    /*
     * indexes
     */

    int             tcpConnectionState_get(tcpConnectionTable_rowreq_ctx *
                                           rowreq_ctx,
                                           u_long *
                                           tcpConnectionState_val_ptr);
    int             tcpConnectionProcess_get(tcpConnectionTable_rowreq_ctx
                                             * rowreq_ctx,
                                             u_long *
                                             tcpConnectionProcess_val_ptr);


    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_indexes_set_tbl_idx(tcpConnectionTable_mib_index
                                               * tbl_idx,
                                               u_long
                                               tcpConnectionLocalAddressType_val,
                                               char
                                               *tcpConnectionLocalAddress_val_ptr,
                                               size_t
                                               tcpConnectionLocalAddress_val_ptr_len,
                                               u_long
                                               tcpConnectionLocalPort_val,
                                               u_long
                                               tcpConnectionRemAddressType_val,
                                               char
                                               *tcpConnectionRemAddress_val_ptr,
                                               size_t
                                               tcpConnectionRemAddress_val_ptr_len,
                                               u_long
                                               tcpConnectionRemPort_val);
    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_indexes_set(tcpConnectionTable_rowreq_ctx *
                                       rowreq_ctx,
                                       u_long
                                       tcpConnectionLocalAddressType_val,
                                       char
                                       *tcpConnectionLocalAddress_val_ptr,
                                       size_t
                                       tcpConnectionLocalAddress_val_ptr_len,
                                       u_long tcpConnectionLocalPort_val,
                                       u_long
                                       tcpConnectionRemAddressType_val,
                                       char
                                       *tcpConnectionRemAddress_val_ptr,
                                       size_t
                                       tcpConnectionRemAddress_val_ptr_len,
                                       u_long tcpConnectionRemPort_val);



    /*
     *********************************************************************
     * SET function declarations
     */

    /*
     *********************************************************************
     * SET Table declarations
     */
/**********************************************************************
 **********************************************************************
 ***
 *** Table tcpConnectionTable
 ***
 **********************************************************************
 **********************************************************************/
    /*
     * TCP-MIB::tcpConnectionTable is subid 19 of tcp.
     * Its status is Current.
     * OID: .1.3.6.1.2.1.6.19, length: 8
     */


    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_undo_setup(tcpConnectionTable_rowreq_ctx *
                                      rowreq_ctx);
    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_undo_cleanup(tcpConnectionTable_rowreq_ctx *
                                        rowreq_ctx);
    int             tcpConnectionTable_undo(tcpConnectionTable_rowreq_ctx *
                                            rowreq_ctx);
    int             tcpConnectionTable_commit(tcpConnectionTable_rowreq_ctx
                                              * rowreq_ctx);
    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_undo_commit(tcpConnectionTable_rowreq_ctx *
                                       rowreq_ctx);


    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionState_check_value(tcpConnectionTable_rowreq_ctx *
                                       rowreq_ctx,
                                       u_long tcpConnectionState_val);
    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionState_undo_setup(tcpConnectionTable_rowreq_ctx *
                                      rowreq_ctx);
    int             tcpConnectionState_set(tcpConnectionTable_rowreq_ctx *
                                           rowreq_ctx,
                                           u_long tcpConnectionState_val);
    int             tcpConnectionState_undo(tcpConnectionTable_rowreq_ctx *
                                            rowreq_ctx);

    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionProcess_check_value(tcpConnectionTable_rowreq_ctx *
                                         rowreq_ctx,
                                         u_long tcpConnectionProcess_val);
    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionProcess_undo_setup(tcpConnectionTable_rowreq_ctx *
                                        rowreq_ctx);
    int             tcpConnectionProcess_set(tcpConnectionTable_rowreq_ctx
                                             * rowreq_ctx,
                                             u_long
                                             tcpConnectionProcess_val);
    int             tcpConnectionProcess_undo(tcpConnectionTable_rowreq_ctx
                                              * rowreq_ctx);


    int
     
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        tcpConnectionTable_check_dependencies(tcpConnectionTable_rowreq_ctx
                                              * ctx);


    /*
     * DUMMY markers, ignore
     *
     * TODO:099:x: *************************************************************
     * TODO:199:x: *************************************************************
     * TODO:299:x: *************************************************************
     * TODO:399:x: *************************************************************
     * TODO:499:x: *************************************************************
     */

#ifdef __cplusplus
}
#endif
#endif                          /* TCPCONNECTIONTABLE_H */
