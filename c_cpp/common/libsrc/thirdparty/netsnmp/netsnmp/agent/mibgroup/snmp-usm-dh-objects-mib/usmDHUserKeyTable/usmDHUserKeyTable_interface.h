/*
 * Note: this file originally auto-generated by mib2c using
 *       version : 1.67 $ of : mfd-interface.m2c,v $
 *
 * $Id: usmDHUserKeyTable_interface.h,v 1.1.1.1 2006/04/05 02:29:38 prem Exp $
 */
/** @defgroup interface: Routines to interface to Net-SNMP
 *
 * \warning This code should not be modified, called directly,
 *          or used to interpret functionality. It is subject to
 *          change at any time.
 * 
 * @{
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
#ifndef USMDHUSERKEYTABLE_INTERFACE_H
#define USMDHUSERKEYTABLE_INTERFACE_H

#ifdef __cplusplus
extern          "C" {
#endif


#include "usmDHUserKeyTable.h"


    /*
     ********************************************************************
     * Table declarations
     */

    /*
     * PUBLIC interface initialization routine 
     */
    void           
        _usmDHUserKeyTable_initialize_interface
        (usmDHUserKeyTable_registration * user_ctx, u_long flags);
    void           
        _usmDHUserKeyTable_shutdown_interface
        (usmDHUserKeyTable_registration * user_ctx);

    usmDHUserKeyTable_registration
        *usmDHUserKeyTable_registration_get(void);

    usmDHUserKeyTable_registration
        *usmDHUserKeyTable_registration_set(usmDHUserKeyTable_registration
                                            * newreg);

    netsnmp_container *usmDHUserKeyTable_container_get(void);
    int             usmDHUserKeyTable_container_size(void);

    u_int           usmDHUserKeyTable_dirty_get(void);
    void            usmDHUserKeyTable_dirty_set(u_int status);

    usmDHUserKeyTable_rowreq_ctx
        *usmDHUserKeyTable_allocate_rowreq_ctx(usmDHUserKeyTable_data *,
                                               void *);
    void           
        usmDHUserKeyTable_release_rowreq_ctx(usmDHUserKeyTable_rowreq_ctx *
                                             rowreq_ctx);

    int             usmDHUserKeyTable_index_to_oid(netsnmp_index * oid_idx,
                                                   usmDHUserKeyTable_mib_index
                                                   * mib_idx);
    int             usmDHUserKeyTable_index_from_oid(netsnmp_index *
                                                     oid_idx,
                                                     usmDHUserKeyTable_mib_index
                                                     * mib_idx);

    /*
     * access to certain internals. use with caution!
     */
    void            usmDHUserKeyTable_valid_columns_set(netsnmp_column_info
                                                        *vc);


#ifdef __cplusplus
}
#endif
#endif                          /* USMDHUSERKEYTABLE_INTERFACE_H */
