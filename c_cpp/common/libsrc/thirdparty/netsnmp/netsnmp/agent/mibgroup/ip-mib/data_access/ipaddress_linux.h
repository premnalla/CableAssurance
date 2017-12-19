/*
 * ipaddress data access header
 *
 * $Id: ipaddress_linux.h,v 1.1.1.1 2006/04/05 02:29:38 prem Exp $
 */
/**---------------------------------------------------------------------*/
/*
 * configure required files
 *
 * Notes:
 *
 * 1) prefer functionality over platform, where possible. If a method
 *    is available for multiple platforms, test that first. That way
 *    when a new platform is ported, it won't need a new test here.
 *
 * 2) don't do detail requirements here. If, for example,
 *    HPUX11 had different reuirements than other HPUX, that should
 *    be handled in the *_hpux.h header file.
 */
config_require(ip-mib/data_access/ipaddress_linux)
config_require(ip-mib/data_access/ipaddress_ioctl)
