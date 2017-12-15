/* config.h.  Generated automatically by configure.  */
/* config.h.in.  Generated automatically from configure.in by autoheader.  */
#ifndef CONFIG_H
#define CONFIG_H



/* VERSION (from configure.in) */
#define VERSION "2.4b2_to"



/* Define if you have the `nsl' library (-lnsl). */
/* #undef HAVE_LIBNSL */

/* Define if you have the `socket' library (-lsocket). */
/* #undef HAVE_LIBSOCKET */

/* Define if you have the <stdlib.h> header file. */
#define HAVE_STDLIB_H 1

/* Define if you have the <sys/file.h> header file. */
#define HAVE_SYS_FILE_H 1

/* Define if you have the <sys/select.h> header file. */
#define HAVE_SYS_SELECT_H 1

/* Define if you have the <unistd.h> header file. */
#define HAVE_UNISTD_H 1

/* Name of package */
#define PACKAGE "fping"

/* Version number of package */
#define VERSION "2.4b2_to"

/* some OSes do not define this ... lets take a wild guess */

#ifndef INADDR_NONE
#  define INADDR_NONE 0xffffffffU
#endif

#endif /* CONFIG_H */
