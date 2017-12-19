/*
 * Note: this file originally auto-generated by mib2c using
 *       version : 1.17 $ of : mfd-data-access.m2c,v $
 *
 * $Id: tcpListenerTable_data_access.h,v 1.1.1.1 2006/04/05 02:29:38 prem Exp $
 */
#ifndef TCPLISTENERTABLE_DATA_ACCESS_H
#define TCPLISTENERTABLE_DATA_ACCESS_H

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
 *** Table tcpListenerTable
 ***
 **********************************************************************
 **********************************************************************/
    /*
     * TCP-MIB::tcpListenerTable is subid 20 of tcp.
     * Its status is Current.
     * OID: .1.3.6.1.2.1.6.20, length: 8
     */


    int
        tcpListenerTable_init_data(tcpListenerTable_registration *
                                   tcpListenerTable_reg);


    /*
     * TODO:180:o: Review tcpListenerTable cache timeout.
     * The number of seconds before the cache times out
     */
#define TCPLISTENERTABLE_CACHE_TIMEOUT   60

    void            tcpListenerTable_container_init(netsnmp_container
                                                    **container_ptr_ptr,
                                                    netsnmp_cache * cache);
    void            tcpListenerTable_container_shutdown(netsnmp_container
                                                        *container_ptr);

    int             tcpListenerTable_container_load(netsnmp_container
                                                    *container);
    void            tcpListenerTable_container_free(netsnmp_container
                                                    *container);

    int             tcpListenerTable_cache_load(netsnmp_container
                                                *container);
    void            tcpListenerTable_cache_free(netsnmp_container
                                                *container);

    int             tcpListenerTable_row_prep(tcpListenerTable_rowreq_ctx *
                                              rowreq_ctx);



#ifdef __cplusplus
}
#endif
#endif                          /* TCPLISTENERTABLE_DATA_ACCESS_H */
