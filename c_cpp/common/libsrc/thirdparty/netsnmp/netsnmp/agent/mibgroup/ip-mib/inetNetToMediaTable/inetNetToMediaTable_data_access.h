/*
 * Note: this file originally auto-generated by mib2c using
 *       version : 1.17 $ of : mfd-data-access.m2c,v $
 *
 * $Id: inetNetToMediaTable_data_access.h,v 1.1.1.1 2006/04/05 02:29:38 prem Exp $
 */
#ifndef INETNETTOMEDIATABLE_DATA_ACCESS_H
#define INETNETTOMEDIATABLE_DATA_ACCESS_H

#ifdef __cplusplus
extern          "C" {
#endif


    /*
     *********************************************************************
     * function declarations
     */

    /*
     *********************************************************************
     * Table declarations
     */
/**********************************************************************
 **********************************************************************
 ***
 *** Table inetNetToMediaTable
 ***
 **********************************************************************
 **********************************************************************/
    /*
     * IP-MIB::inetNetToMediaTable is subid 35 of ip.
     * Its status is Current.
     * OID: .1.3.6.1.2.1.4.35, length: 8
     */


    int
        inetNetToMediaTable_init_data(inetNetToMediaTable_registration *
                                      inetNetToMediaTable_reg);


    /*
     * TODO:180:o: Review inetNetToMediaTable cache timeout.
     * The number of seconds before the cache times out
     */
#define INETNETTOMEDIATABLE_CACHE_TIMEOUT   60

    void            inetNetToMediaTable_container_init(netsnmp_container
                                                       **container_ptr_ptr,
                                                       netsnmp_cache *
                                                       cache);
    void
                    inetNetToMediaTable_container_shutdown(netsnmp_container
                                                           *container_ptr);

    int             inetNetToMediaTable_container_load(netsnmp_container
                                                       *container);
    void            inetNetToMediaTable_container_free(netsnmp_container
                                                       *container);

    int             inetNetToMediaTable_cache_load(netsnmp_container
                                                   *container);
    void            inetNetToMediaTable_cache_free(netsnmp_container
                                                   *container);

    int
        inetNetToMediaTable_row_prep(inetNetToMediaTable_rowreq_ctx *
                                     rowreq_ctx);

    int
        inetNetToMediaTable_validate_index(inetNetToMediaTable_registration
                                           * inetNetToMediaTable_reg,
                                           inetNetToMediaTable_rowreq_ctx *
                                           rowreq_ctx);
    int             inetNetToMediaIfIndex_check_index(inetNetToMediaTable_rowreq_ctx * rowreq_ctx);     /* internal */
    int             inetNetToMediaNetAddressType_check_index(inetNetToMediaTable_rowreq_ctx * rowreq_ctx);      /* internal */
    int             inetNetToMediaNetAddress_check_index(inetNetToMediaTable_rowreq_ctx * rowreq_ctx);  /* internal */


#ifdef __cplusplus
}
#endif
#endif                          /* INETNETTOMEDIATABLE_DATA_ACCESS_H */
