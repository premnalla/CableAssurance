@BOTTOM@
/* define if you don't want debugging to be turned on */
#undef NODEBUGGING

/* define if sys/stream.h is broken */
#undef SYS_STREAM_H_IS_BROKEN

/* des_ks_struct.weak_key */
#undef STRUCT_DES_KS_STRUCT_HAS_WEAK_KEY

#ifdef HAVE_AES_CFB128_ENCRYPT
#define HAVE_AES
#endif

/* define if you have dbm_open */
#undef HAVE_DBM_OPEN
