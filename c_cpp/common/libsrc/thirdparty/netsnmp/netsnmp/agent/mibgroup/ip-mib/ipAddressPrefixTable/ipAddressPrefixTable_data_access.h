/*
 * Note: this file originally auto-generated by mib2c using
 *       version : 1.17 $ of : mfd-data-access.m2c,v $
 *
 * $Id: ipAddressPrefixTable_data_access.h,v 1.1.1.1 2006/04/05 02:29:38 prem Exp $
 */
#ifndef IPADDRESSPREFIXTABLE_DATA_ACCESS_H
#define IPADDRESSPREFIXTABLE_DATA_ACCESS_H

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
 *** Table ipAddressPrefixTable
 ***
 **********************************************************************
 **********************************************************************/
    /*
     * IP-MIB::ipAddressPrefixTable is subid 32 of ip.
     * Its status is Current.
     * OID: .1.3.6.1.2.1.4.32, length: 8
     */


    int
        ipAddressPrefixTable_init_data(ipAddressPrefixTable_registration *
                                       ipAddressPrefixTable_reg);


    /*
     * TODO:180:o: Review ipAddressPrefixTable cache timeout.
     * The number of seconds before the cache times out
     */
#define IPADDRESSPREFIXTABLE_CACHE_TIMEOUT   60

    void            ipAddressPrefixTable_container_init(netsnmp_container
                                                        **container_ptr_ptr,
                                                        netsnmp_cache *
                                                        cache);
    void
        ipAddressPrefixTable_container_shutdown(netsnmp_container
                                                *container_ptr);

    int             ipAddressPrefixTable_container_load(netsnmp_container
                                                        *container);
    void            ipAddressPrefixTable_container_free(netsnmp_container
                                                        *container);

    int             ipAddressPrefixTable_cache_load(netsnmp_container
                                                    *container);
    void            ipAddressPrefixTable_cache_free(netsnmp_container
                                                    *container);

    int
        ipAddressPrefixTable_row_prep(ipAddressPrefixTable_rowreq_ctx *
                                      rowreq_ctx);



#ifdef __cplusplus
}
#endif
#endif                          /* IPADDRESSPREFIXTABLE_DATA_ACCESS_H */
