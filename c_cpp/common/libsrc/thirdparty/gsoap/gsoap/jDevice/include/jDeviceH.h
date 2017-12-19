/* jDeviceH.h
   Generated by gSOAP 2.7.9c from include/jDevice.h
   Copyright(C) 2000-2006, Robert van Engelen, Genivia Inc. All Rights Reserved.
   This part of the software is released under one of the following licenses:
   GPL, the gSOAP public license, or Genivia's license for commercial use.
*/

#ifndef jDeviceH_H
#define jDeviceH_H
#include "jDeviceStub.h"
#ifndef WITH_NOIDREF

#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_markelement(struct soap*, const void*, int);
SOAP_FMAC3 int SOAP_FMAC4 soap_putelement(struct soap*, const void*, const char*, int, int);
SOAP_FMAC3 void *SOAP_FMAC4 soap_getelement(struct soap*, int*);

#ifdef __cplusplus
}
#endif
SOAP_FMAC3 int SOAP_FMAC4 soap_putindependent(struct soap*);
SOAP_FMAC3 int SOAP_FMAC4 soap_getindependent(struct soap*);
#endif
SOAP_FMAC3 int SOAP_FMAC4 soap_ignore_element(struct soap*);

SOAP_FMAC3 void * SOAP_FMAC4 soap_instantiate(struct soap*, int, const char*, const char*, size_t*);
SOAP_FMAC3 int SOAP_FMAC4 soap_fdelete(struct soap_clist*);
SOAP_FMAC3 void* SOAP_FMAC4 soap_class_id_enter(struct soap*, const char*, void*, int, size_t, const char*, const char*);

SOAP_FMAC3 void* SOAP_FMAC4 soap_container_id_forward(struct soap*, const char*, void*, size_t, int, int, size_t, unsigned int);

SOAP_FMAC3 void SOAP_FMAC4 soap_container_insert(struct soap*, int, int, void*, size_t, const void*, size_t);

#ifndef SOAP_TYPE_byte
#define SOAP_TYPE_byte (2)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_byte(struct soap*, char *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_byte(struct soap*, const char *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_byte(struct soap*, const char*, int, const char *, const char*);
SOAP_FMAC3 char * SOAP_FMAC4 soap_get_byte(struct soap*, char *, const char*, const char*);
SOAP_FMAC3 char * SOAP_FMAC4 soap_in_byte(struct soap*, const char*, char *, const char*);

#ifndef SOAP_TYPE_int
#define SOAP_TYPE_int (1)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_int(struct soap*, int *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_int(struct soap*, const int *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_int(struct soap*, const char*, int, const int *, const char*);
SOAP_FMAC3 int * SOAP_FMAC4 soap_get_int(struct soap*, int *, const char*, const char*);
SOAP_FMAC3 int * SOAP_FMAC4 soap_in_int(struct soap*, const char*, int *, const char*);

#ifndef SOAP_TYPE_ns1__EndUserDeviceTypeT
#define SOAP_TYPE_ns1__EndUserDeviceTypeT (8)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_ns1__EndUserDeviceTypeT(struct soap*, enum ns1__EndUserDeviceTypeT *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_ns1__EndUserDeviceTypeT(struct soap*, const enum ns1__EndUserDeviceTypeT *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_ns1__EndUserDeviceTypeT(struct soap*, const char*, int, const enum ns1__EndUserDeviceTypeT *, const char*);

SOAP_FMAC3S const char* SOAP_FMAC4S soap_ns1__EndUserDeviceTypeT2s(struct soap*, enum ns1__EndUserDeviceTypeT);
SOAP_FMAC3 enum ns1__EndUserDeviceTypeT * SOAP_FMAC4 soap_get_ns1__EndUserDeviceTypeT(struct soap*, enum ns1__EndUserDeviceTypeT *, const char*, const char*);
SOAP_FMAC3 enum ns1__EndUserDeviceTypeT * SOAP_FMAC4 soap_in_ns1__EndUserDeviceTypeT(struct soap*, const char*, enum ns1__EndUserDeviceTypeT *, const char*);

SOAP_FMAC3S int SOAP_FMAC4S soap_s2ns1__EndUserDeviceTypeT(struct soap*, const char*, enum ns1__EndUserDeviceTypeT *);

#ifndef SOAP_TYPE_std__string
#define SOAP_TYPE_std__string (12)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_std__string(struct soap*, std::string *);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_std__string(struct soap*, const std::string *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_std__string(struct soap*, const std::string *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_std__string(struct soap*, const char*, int, const std::string*, const char*);
SOAP_FMAC3 std::string * SOAP_FMAC4 soap_get_std__string(struct soap*, std::string *, const char*, const char*);
SOAP_FMAC3 std::string * SOAP_FMAC4 soap_in_std__string(struct soap*, const char*, std::string*, const char*);
SOAP_FMAC5 std::string * SOAP_FMAC6 soap_new_std__string(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_std__string(struct soap*, std::string*);
SOAP_FMAC3 std::string * SOAP_FMAC4 soap_instantiate_std__string(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_std__string(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#ifndef SOAP_TYPE_ns1__DeviceDetailsT
#define SOAP_TYPE_ns1__DeviceDetailsT (7)
#endif

SOAP_FMAC3 int SOAP_FMAC4 soap_out_ns1__DeviceDetailsT(struct soap*, const char*, int, const ns1__DeviceDetailsT *, const char*);
SOAP_FMAC3 ns1__DeviceDetailsT * SOAP_FMAC4 soap_get_ns1__DeviceDetailsT(struct soap*, ns1__DeviceDetailsT *, const char*, const char*);
SOAP_FMAC3 ns1__DeviceDetailsT * SOAP_FMAC4 soap_in_ns1__DeviceDetailsT(struct soap*, const char*, ns1__DeviceDetailsT *, const char*);
SOAP_FMAC5 ns1__DeviceDetailsT * SOAP_FMAC6 soap_new_ns1__DeviceDetailsT(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_ns1__DeviceDetailsT(struct soap*, ns1__DeviceDetailsT*);
SOAP_FMAC3 ns1__DeviceDetailsT * SOAP_FMAC4 soap_instantiate_ns1__DeviceDetailsT(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_ns1__DeviceDetailsT(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#ifndef WITH_NOGLOBAL

#ifndef SOAP_TYPE_SOAP_ENV__Fault
#define SOAP_TYPE_SOAP_ENV__Fault (23)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_SOAP_ENV__Fault(struct soap*, struct SOAP_ENV__Fault *);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_SOAP_ENV__Fault(struct soap*, const struct SOAP_ENV__Fault *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_SOAP_ENV__Fault(struct soap*, const struct SOAP_ENV__Fault *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_SOAP_ENV__Fault(struct soap*, const char*, int, const struct SOAP_ENV__Fault *, const char*);
SOAP_FMAC3 struct SOAP_ENV__Fault * SOAP_FMAC4 soap_get_SOAP_ENV__Fault(struct soap*, struct SOAP_ENV__Fault *, const char*, const char*);
SOAP_FMAC3 struct SOAP_ENV__Fault * SOAP_FMAC4 soap_in_SOAP_ENV__Fault(struct soap*, const char*, struct SOAP_ENV__Fault *, const char*);
SOAP_FMAC5 struct SOAP_ENV__Fault * SOAP_FMAC6 soap_new_SOAP_ENV__Fault(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_SOAP_ENV__Fault(struct soap*, struct SOAP_ENV__Fault*);
SOAP_FMAC3 struct SOAP_ENV__Fault * SOAP_FMAC4 soap_instantiate_SOAP_ENV__Fault(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_SOAP_ENV__Fault(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#endif

#ifndef WITH_NOGLOBAL

#ifndef SOAP_TYPE_SOAP_ENV__Reason
#define SOAP_TYPE_SOAP_ENV__Reason (22)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_SOAP_ENV__Reason(struct soap*, struct SOAP_ENV__Reason *);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_SOAP_ENV__Reason(struct soap*, const struct SOAP_ENV__Reason *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_SOAP_ENV__Reason(struct soap*, const struct SOAP_ENV__Reason *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_SOAP_ENV__Reason(struct soap*, const char*, int, const struct SOAP_ENV__Reason *, const char*);
SOAP_FMAC3 struct SOAP_ENV__Reason * SOAP_FMAC4 soap_get_SOAP_ENV__Reason(struct soap*, struct SOAP_ENV__Reason *, const char*, const char*);
SOAP_FMAC3 struct SOAP_ENV__Reason * SOAP_FMAC4 soap_in_SOAP_ENV__Reason(struct soap*, const char*, struct SOAP_ENV__Reason *, const char*);
SOAP_FMAC5 struct SOAP_ENV__Reason * SOAP_FMAC6 soap_new_SOAP_ENV__Reason(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_SOAP_ENV__Reason(struct soap*, struct SOAP_ENV__Reason*);
SOAP_FMAC3 struct SOAP_ENV__Reason * SOAP_FMAC4 soap_instantiate_SOAP_ENV__Reason(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_SOAP_ENV__Reason(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#endif

#ifndef WITH_NOGLOBAL

#ifndef SOAP_TYPE_SOAP_ENV__Detail
#define SOAP_TYPE_SOAP_ENV__Detail (21)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_SOAP_ENV__Detail(struct soap*, struct SOAP_ENV__Detail *);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_SOAP_ENV__Detail(struct soap*, const struct SOAP_ENV__Detail *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_SOAP_ENV__Detail(struct soap*, const struct SOAP_ENV__Detail *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_SOAP_ENV__Detail(struct soap*, const char*, int, const struct SOAP_ENV__Detail *, const char*);
SOAP_FMAC3 struct SOAP_ENV__Detail * SOAP_FMAC4 soap_get_SOAP_ENV__Detail(struct soap*, struct SOAP_ENV__Detail *, const char*, const char*);
SOAP_FMAC3 struct SOAP_ENV__Detail * SOAP_FMAC4 soap_in_SOAP_ENV__Detail(struct soap*, const char*, struct SOAP_ENV__Detail *, const char*);
SOAP_FMAC5 struct SOAP_ENV__Detail * SOAP_FMAC6 soap_new_SOAP_ENV__Detail(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_SOAP_ENV__Detail(struct soap*, struct SOAP_ENV__Detail*);
SOAP_FMAC3 struct SOAP_ENV__Detail * SOAP_FMAC4 soap_instantiate_SOAP_ENV__Detail(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_SOAP_ENV__Detail(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#endif

#ifndef WITH_NOGLOBAL

#ifndef SOAP_TYPE_SOAP_ENV__Code
#define SOAP_TYPE_SOAP_ENV__Code (19)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_SOAP_ENV__Code(struct soap*, struct SOAP_ENV__Code *);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_SOAP_ENV__Code(struct soap*, const struct SOAP_ENV__Code *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_SOAP_ENV__Code(struct soap*, const struct SOAP_ENV__Code *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_SOAP_ENV__Code(struct soap*, const char*, int, const struct SOAP_ENV__Code *, const char*);
SOAP_FMAC3 struct SOAP_ENV__Code * SOAP_FMAC4 soap_get_SOAP_ENV__Code(struct soap*, struct SOAP_ENV__Code *, const char*, const char*);
SOAP_FMAC3 struct SOAP_ENV__Code * SOAP_FMAC4 soap_in_SOAP_ENV__Code(struct soap*, const char*, struct SOAP_ENV__Code *, const char*);
SOAP_FMAC5 struct SOAP_ENV__Code * SOAP_FMAC6 soap_new_SOAP_ENV__Code(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_SOAP_ENV__Code(struct soap*, struct SOAP_ENV__Code*);
SOAP_FMAC3 struct SOAP_ENV__Code * SOAP_FMAC4 soap_instantiate_SOAP_ENV__Code(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_SOAP_ENV__Code(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#endif

#ifndef WITH_NOGLOBAL

#ifndef SOAP_TYPE_SOAP_ENV__Header
#define SOAP_TYPE_SOAP_ENV__Header (18)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_SOAP_ENV__Header(struct soap*, struct SOAP_ENV__Header *);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_SOAP_ENV__Header(struct soap*, const struct SOAP_ENV__Header *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_SOAP_ENV__Header(struct soap*, const struct SOAP_ENV__Header *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_SOAP_ENV__Header(struct soap*, const char*, int, const struct SOAP_ENV__Header *, const char*);
SOAP_FMAC3 struct SOAP_ENV__Header * SOAP_FMAC4 soap_get_SOAP_ENV__Header(struct soap*, struct SOAP_ENV__Header *, const char*, const char*);
SOAP_FMAC3 struct SOAP_ENV__Header * SOAP_FMAC4 soap_in_SOAP_ENV__Header(struct soap*, const char*, struct SOAP_ENV__Header *, const char*);
SOAP_FMAC5 struct SOAP_ENV__Header * SOAP_FMAC6 soap_new_SOAP_ENV__Header(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_SOAP_ENV__Header(struct soap*, struct SOAP_ENV__Header*);
SOAP_FMAC3 struct SOAP_ENV__Header * SOAP_FMAC4 soap_instantiate_SOAP_ENV__Header(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_SOAP_ENV__Header(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#endif

#ifndef SOAP_TYPE_jDevice__getDeviceDetails
#define SOAP_TYPE_jDevice__getDeviceDetails (15)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_jDevice__getDeviceDetails(struct soap*, struct jDevice__getDeviceDetails *);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_jDevice__getDeviceDetails(struct soap*, const struct jDevice__getDeviceDetails *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_jDevice__getDeviceDetails(struct soap*, const struct jDevice__getDeviceDetails *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_jDevice__getDeviceDetails(struct soap*, const char*, int, const struct jDevice__getDeviceDetails *, const char*);
SOAP_FMAC3 struct jDevice__getDeviceDetails * SOAP_FMAC4 soap_get_jDevice__getDeviceDetails(struct soap*, struct jDevice__getDeviceDetails *, const char*, const char*);
SOAP_FMAC3 struct jDevice__getDeviceDetails * SOAP_FMAC4 soap_in_jDevice__getDeviceDetails(struct soap*, const char*, struct jDevice__getDeviceDetails *, const char*);
SOAP_FMAC5 struct jDevice__getDeviceDetails * SOAP_FMAC6 soap_new_jDevice__getDeviceDetails(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_jDevice__getDeviceDetails(struct soap*, struct jDevice__getDeviceDetails*);
SOAP_FMAC3 struct jDevice__getDeviceDetails * SOAP_FMAC4 soap_instantiate_jDevice__getDeviceDetails(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_jDevice__getDeviceDetails(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#ifndef SOAP_TYPE_jDevice__getDeviceDetailsResponse
#define SOAP_TYPE_jDevice__getDeviceDetailsResponse (11)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_jDevice__getDeviceDetailsResponse(struct soap*, struct jDevice__getDeviceDetailsResponse *);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_jDevice__getDeviceDetailsResponse(struct soap*, const struct jDevice__getDeviceDetailsResponse *);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_jDevice__getDeviceDetailsResponse(struct soap*, const struct jDevice__getDeviceDetailsResponse *, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_jDevice__getDeviceDetailsResponse(struct soap*, const char*, int, const struct jDevice__getDeviceDetailsResponse *, const char*);
SOAP_FMAC3 struct jDevice__getDeviceDetailsResponse * SOAP_FMAC4 soap_get_jDevice__getDeviceDetailsResponse(struct soap*, struct jDevice__getDeviceDetailsResponse *, const char*, const char*);
SOAP_FMAC3 struct jDevice__getDeviceDetailsResponse * SOAP_FMAC4 soap_in_jDevice__getDeviceDetailsResponse(struct soap*, const char*, struct jDevice__getDeviceDetailsResponse *, const char*);
SOAP_FMAC5 struct jDevice__getDeviceDetailsResponse * SOAP_FMAC6 soap_new_jDevice__getDeviceDetailsResponse(struct soap*, int);
SOAP_FMAC5 void SOAP_FMAC6 soap_delete_jDevice__getDeviceDetailsResponse(struct soap*, struct jDevice__getDeviceDetailsResponse*);
SOAP_FMAC3 struct jDevice__getDeviceDetailsResponse * SOAP_FMAC4 soap_instantiate_jDevice__getDeviceDetailsResponse(struct soap*, int, const char*, const char*, size_t*);
#ifdef __cplusplus
extern "C" {
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_copy_jDevice__getDeviceDetailsResponse(struct soap*, int, int, void*, size_t, const void*, size_t);
#ifdef __cplusplus
}
#endif

#ifndef WITH_NOGLOBAL

#ifndef SOAP_TYPE_PointerToSOAP_ENV__Reason
#define SOAP_TYPE_PointerToSOAP_ENV__Reason (25)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_PointerToSOAP_ENV__Reason(struct soap*, struct SOAP_ENV__Reason *const*);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_PointerToSOAP_ENV__Reason(struct soap*, struct SOAP_ENV__Reason *const*, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_PointerToSOAP_ENV__Reason(struct soap*, const char *, int, struct SOAP_ENV__Reason *const*, const char *);
SOAP_FMAC3 struct SOAP_ENV__Reason ** SOAP_FMAC4 soap_get_PointerToSOAP_ENV__Reason(struct soap*, struct SOAP_ENV__Reason **, const char*, const char*);
SOAP_FMAC3 struct SOAP_ENV__Reason ** SOAP_FMAC4 soap_in_PointerToSOAP_ENV__Reason(struct soap*, const char*, struct SOAP_ENV__Reason **, const char*);

#endif

#ifndef WITH_NOGLOBAL

#ifndef SOAP_TYPE_PointerToSOAP_ENV__Detail
#define SOAP_TYPE_PointerToSOAP_ENV__Detail (24)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_PointerToSOAP_ENV__Detail(struct soap*, struct SOAP_ENV__Detail *const*);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_PointerToSOAP_ENV__Detail(struct soap*, struct SOAP_ENV__Detail *const*, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_PointerToSOAP_ENV__Detail(struct soap*, const char *, int, struct SOAP_ENV__Detail *const*, const char *);
SOAP_FMAC3 struct SOAP_ENV__Detail ** SOAP_FMAC4 soap_get_PointerToSOAP_ENV__Detail(struct soap*, struct SOAP_ENV__Detail **, const char*, const char*);
SOAP_FMAC3 struct SOAP_ENV__Detail ** SOAP_FMAC4 soap_in_PointerToSOAP_ENV__Detail(struct soap*, const char*, struct SOAP_ENV__Detail **, const char*);

#endif

#ifndef WITH_NOGLOBAL

#ifndef SOAP_TYPE_PointerToSOAP_ENV__Code
#define SOAP_TYPE_PointerToSOAP_ENV__Code (20)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_PointerToSOAP_ENV__Code(struct soap*, struct SOAP_ENV__Code *const*);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_PointerToSOAP_ENV__Code(struct soap*, struct SOAP_ENV__Code *const*, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_PointerToSOAP_ENV__Code(struct soap*, const char *, int, struct SOAP_ENV__Code *const*, const char *);
SOAP_FMAC3 struct SOAP_ENV__Code ** SOAP_FMAC4 soap_get_PointerToSOAP_ENV__Code(struct soap*, struct SOAP_ENV__Code **, const char*, const char*);
SOAP_FMAC3 struct SOAP_ENV__Code ** SOAP_FMAC4 soap_in_PointerToSOAP_ENV__Code(struct soap*, const char*, struct SOAP_ENV__Code **, const char*);

#endif

#ifndef SOAP_TYPE__QName
#define SOAP_TYPE__QName (5)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default__QName(struct soap*, char **);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize__QName(struct soap*, char *const*);
SOAP_FMAC3 int SOAP_FMAC4 soap_put__QName(struct soap*, char *const*, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out__QName(struct soap*, const char*, int, char*const*, const char*);
SOAP_FMAC3 char ** SOAP_FMAC4 soap_get__QName(struct soap*, char **, const char*, const char*);
SOAP_FMAC3 char * * SOAP_FMAC4 soap_in__QName(struct soap*, const char*, char **, const char*);

#ifndef SOAP_TYPE_string
#define SOAP_TYPE_string (3)
#endif
SOAP_FMAC3 void SOAP_FMAC4 soap_default_string(struct soap*, char **);
SOAP_FMAC3 void SOAP_FMAC4 soap_serialize_string(struct soap*, char *const*);
SOAP_FMAC3 int SOAP_FMAC4 soap_put_string(struct soap*, char *const*, const char*, const char*);
SOAP_FMAC3 int SOAP_FMAC4 soap_out_string(struct soap*, const char*, int, char*const*, const char*);
SOAP_FMAC3 char ** SOAP_FMAC4 soap_get_string(struct soap*, char **, const char*, const char*);
SOAP_FMAC3 char * * SOAP_FMAC4 soap_in_string(struct soap*, const char*, char **, const char*);

#endif

/* End of jDeviceH.h */
