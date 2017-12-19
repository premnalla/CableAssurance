#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>

#define NETSNMP_DS_APP_DONT_FIX_PDUS 0

/*
 * a list of hosts to query
 */
#define NEW_HOST 0
#define PROBING  1
#define GETTING  2

#define MAX_HOSTS 100
struct host_t {
  struct snmp_session *sess;		/* SNMP session data */
  char *name;
  int state;
} hosts[MAX_HOSTS];
int active_hosts = 0;			/* hosts that we have not completed */
int host_count = 0;
int oid_count = 0;
FILE  *fp = 0;
int no_more_hosts = 0;
netsnmp_session def_session;
char            *oids[128];
struct snmp_session *last_sess = NULL;		/* SNMP session data */
int probe_hosts = 0;

void
usage(void)
{
    fprintf(stderr, "USAGE: snmpfget ");
    snmp_parse_args_usage(stderr);
    fprintf(stderr, " OID [OID]...\n\n");
    snmp_parse_args_descriptions(stderr);
    fprintf(stderr,
            "  -C APPOPTS\t\tSet various application specific behaviours:\n");
    fprintf(stderr,
            "\t\t\t  f:  do not fix errors and retry the request\n");
}
static void
optProc(int argc, char *const *argv, int opt)
{
    switch (opt) {
    case 'C':
        while (*optarg) {
            switch (*optarg++) {
            case 'f':
                netsnmp_ds_toggle_boolean(NETSNMP_DS_APPLICATION_ID, 
					  NETSNMP_DS_APP_DONT_FIX_PDUS);
                break;
            default:
                fprintf(stderr, "Unknown flag passed to -C: %c\n",
                        optarg[-1]);
                exit(1);
            }
        }
        break;
    }
}

int
snmpv3_build_probe_pdu(netsnmp_pdu **pdu)
{
    struct usmUser *user;

    /*
     * create the pdu 
     */
    if (!pdu)
        return -1;
    *pdu = snmp_pdu_create(SNMP_MSG_GET);
    if (!(*pdu))
        return -1;
    (*pdu)->version = SNMP_VERSION_3;
    (*pdu)->securityName = strdup("");
    (*pdu)->securityNameLen = strlen((*pdu)->securityName);
    (*pdu)->securityLevel = SNMP_SEC_LEVEL_NOAUTH;
    (*pdu)->securityModel = SNMP_SEC_MODEL_USM;

    /*
     * create the empty user 
     */
    user = usm_get_user(NULL, 0, (*pdu)->securityName);
    if (user == NULL) {
        user = (struct usmUser *) calloc(1, sizeof(struct usmUser));
        if (user == NULL) {
            snmp_free_pdu(*pdu);
            *pdu = (netsnmp_pdu *) NULL;
            return -1;
        }
        user->name = strdup((*pdu)->securityName);
        user->secName = strdup((*pdu)->securityName);
        user->authProtocolLen = sizeof(usmNoAuthProtocol) / sizeof(oid);
        user->authProtocol =
            snmp_duplicate_objid(usmNoAuthProtocol, user->authProtocolLen);
        user->privProtocolLen = sizeof(usmNoPrivProtocol) / sizeof(oid);
        user->privProtocol =
            snmp_duplicate_objid(usmNoPrivProtocol, user->privProtocolLen);
        usm_add_user(user);
    }
    return 0;
}

/*
 * simple printing of returned data
 */
int print_result (int status, struct snmp_session *sp, struct snmp_pdu *pdu)
{
  char buf[1024];
  struct variable_list *vp;
  int ix;

  switch (status) {
  case STAT_SUCCESS:
    vp = pdu->variables;
    if (pdu->errstat == SNMP_ERR_NOERROR) {
      fprintf(stdout, "%s|OK", sp->peername);
      while (vp) {
        snprint_variable(buf, sizeof(buf), vp->name, vp->name_length, vp);
        fprintf(stdout, "|%s", buf);
	vp = vp->next_variable;
      }
      fprintf(stdout, "\n");
    }
    else {
      for (ix = 1; vp && ix != pdu->errindex; vp = vp->next_variable, ix++)
        ;
      if (vp) snprint_objid(buf, sizeof(buf), vp->name, vp->name_length);
      else strcpy(buf, "(none)");
      fprintf(stdout, "%s|ERROR : %s : %s",
      	sp->peername, buf, snmp_errstring(pdu->errstat));
      for (ix = 0; ix < oid_count; ix++) fprintf(stdout, "|");
      fprintf(stdout, "\n");
    }
    return 1;
  case STAT_TIMEOUT:
    fprintf(stdout, "%s|TIMEOUT", sp->peername);
    for (ix = 0; ix < oid_count; ix++) fprintf(stdout, "|");
    fprintf(stdout, "\n");
    return 0;
  case STAT_ERROR:
    snmp_perror(sp->peername);
    return 0;
  }
  return 0;
}

int process_probe_response(struct snmp_session *sp, struct snmp_pdu *pdu) {

	  /*
	   * Handle engineID discovery.  
	   */
	  if (!sp->securityEngineIDLen && pdu->securityEngineIDLen) {
	    sp->securityEngineID =
	      (u_char *) malloc(pdu->securityEngineIDLen);
	    if (sp->securityEngineID == NULL) {
	      /*
	       * TODO FIX: recover after message callback *?
	       * return -1;
	       */
	      return 1;
	    }
	    memcpy(sp->securityEngineID, pdu->securityEngineID,
		   pdu->securityEngineIDLen);
	    sp->securityEngineIDLen = pdu->securityEngineIDLen;
	    if (!sp->contextEngineIDLen) {
	      sp->contextEngineID =
		(u_char *) malloc(pdu->
				  securityEngineIDLen);
	      if (sp->contextEngineID == NULL) {
		/*
		 * TODO FIX: recover after message callback *?
		 * return -1;
		 */
		return 1;
	      }
	      memcpy(sp->contextEngineID,
		     pdu->securityEngineID,
		     pdu->securityEngineIDLen);
	      sp->contextEngineIDLen =
		pdu->securityEngineIDLen;
	    }
	  }

	  /*
	   * If boot/time supplied, set it for this engine id
	   */
	  if (sp->engineBoots || sp->engineTime) {
	    set_enginetime(sp->securityEngineID,
			   sp->securityEngineIDLen,
			   sp->engineBoots,
			   sp->engineTime,
			   TRUE);
	  }

	  if (create_user_from_session(sp) != SNMPERR_SUCCESS) {
	    return 1;
	  }
	  return 0;
}

int start_host(struct host_t *host);

/*****************************************************************************/

/*
 * response handler
 */
int asynch_response(int operation, struct snmp_session *sp, int reqid,
		    struct snmp_pdu *pdu, void *magic)
{
  struct host_t *host  = (struct host_t *)magic;
  if (last_sess) {
    snmp_close(last_sess);
    last_sess = NULL;
  }

  if (host->state == PROBING) {

    if (operation == NETSNMP_CALLBACK_OP_RECEIVED_MESSAGE) {
      if (process_probe_response(sp, pdu)) {
	print_result(STAT_ERROR, host->sess, pdu);
	host->state = NEW_HOST;
      }
    }
    else {
      print_result(STAT_TIMEOUT, host->sess, pdu);
      host->state = NEW_HOST;
    }
  }
  else {
    last_sess = sp;

    if (operation == NETSNMP_CALLBACK_OP_RECEIVED_MESSAGE) {
      if (print_result(STAT_SUCCESS, host->sess, pdu)) {
      }
    }
    else
      print_result(STAT_TIMEOUT, host->sess, pdu);

  }

  /* something went wrong (or end of variables) 
   * this host not active any more
   */
  active_hosts--;

  start_host(host);

  return 1;
}


int start_host(struct host_t *host) 
{
    int count;
    oid             name[MAX_OID_LEN];
    size_t          name_length;
    netsnmp_session sess;
    int failures = 0;
    int ix;
    char host_name[81];
    char *p;
    netsnmp_pdu *pdu;

    if ((host->state == NEW_HOST) || (host->state == GETTING)) {

      if (!no_more_hosts) {

        if (fgets(host_name, 81, fp)) {
	    host_name[80] = '\0';
	    p = host_name;
	    while ((*p != '\t') && (*p != ' ') && (*p != '\n') && (*p != '\r') && (*p != '\0')) p++;
	    *p = '\0';
	    if (strlen(host_name) > 0) {

	        host->name = strdup(host_name);
		bcopy(def_session, &sess, sizeof(sess));
    
		sess.peername = host->name;
		sess.callback = asynch_response;		/* default callback */
		sess.callback_magic = host;

		if (probe_hosts) 
		  sess.flags |= SNMP_FLAGS_DONT_PROBE;

		if (!(host->sess = snmp_open(&sess))) {
		  printf("%s|TIMEOUT", host->name);
		  for (ix = 0; ix < oid_count; ix++) fprintf(stdout, "|");
		  fprintf(stdout, "\n");
		}
		else {
		  
		  if (probe_hosts) {
		    if (snmpv3_build_probe_pdu(&pdu) != 0) {
		      printf("Error building probe pdu\n");
		      SOCK_CLEANUP;
		      exit(1);		      
		    }

		    host->state = PROBING;
		  }

		  else {

		    /*
		     * Create PDU for GET request and add object names to request.
		     */
		    pdu = snmp_pdu_create(SNMP_MSG_GET);
		    for (count = 0; count < oid_count; count++) {
		      name_length = MAX_OID_LEN;
		      if (!snmp_parse_oid(oids[count], name, &name_length)) {
			snmp_perror(oids[count]);
			failures++;
		      } else
			snmp_add_null_var(pdu, name, name_length);
		    }
		    if (failures) {
		      printf("Error creating pdu\n");
		      SOCK_CLEANUP;
		      exit(1);
		    }
		    host->state = GETTING;

		  }

		  if (snmp_send(host->sess, pdu)) {
	            active_hosts++;
		    return 1;
		  }
		  else {
		    printf("%s|ERROR", host->name);
		    for (ix = 0; ix < oid_count; ix++) fprintf(stdout, "|");
		    fprintf(stdout, "\n");
		  }
		}
	    }
	}
	else {
           no_more_hosts = 1;
        }
      }
    }
    else if (host->state == PROBING) {

      /*
       * Create PDU for GET request and add object names to request.
       */
      pdu = snmp_pdu_create(SNMP_MSG_GET);
      for (count = 0; count < oid_count; count++) {
	name_length = MAX_OID_LEN;
	if (!snmp_parse_oid(oids[count], name, &name_length)) {
	  snmp_perror(oids[count]);
	  failures++;
	} else
	  snmp_add_null_var(pdu, name, name_length);
      }
      if (failures) {
	printf("Error creating pdu\n");
	SOCK_CLEANUP;
	exit(1);
      }
      host->state = GETTING;
     
      if (snmp_send(host->sess, pdu)) {
	active_hosts++;
	return 1;
      }
      else {
	printf("%s|ERROR", host->name);
	for (ix = 0; ix < oid_count; ix++) fprintf(stdout, "|");
	fprintf(stdout, "\n");
      }

    }

    return 0;
}

/*****************************************************************************/

int main (int argc, char **argv)
{
    int hi;
    int             arg;

    /*
     * Initialize SNMP library
     */
    init_snmp("snmpfget");

    /*
     * Get the common command line arguments 
     */
    switch (arg = snmp_parse_args(argc, argv, &def_session, "C:", optProc)) {
    case -2:
        exit(0);
    case -1:
        usage();
        exit(1);
    default:
        break;
    }
    if (arg >= argc) {
        fprintf(stderr, "Missing object name\n");
        usage();
        exit(1);
    }

    /*
     * Get the object names 
     */
    for (; arg < argc; arg++)
        oids[oid_count++] = argv[arg];

    /*
     * Get the host names
     */
    if (!(fp = fopen(def_session.peername, "r"))) {
        fprintf(stderr, "Invalid host filename: %s\n", def_session.peername);
        exit(1);
    }
    SOCK_STARTUP;


    /* 
     * Start initial set of hosts
     */
    probe_hosts = ((def_session.version == SNMP_VERSION_3) &&
                   (def_session.securityEngineIDLen == 0));

    while ((host_count < MAX_HOSTS) && (!no_more_hosts)) {
      hosts[host_count].state = NEW_HOST;
        if (start_host(&hosts[host_count]))
	  host_count++;
    }

  /* loop while any active hosts */

  while (active_hosts) {
    int fds = 0, block = 1;
    fd_set fdset;
    struct timeval timeout;

    FD_ZERO(&fdset);
    snmp_select_info(&fds, &fdset, &timeout, &block);
    fds = select(fds, &fdset, NULL, NULL, block ? NULL : &timeout);
    if (fds < 0) {
        perror("select failed");
        exit(1);
    }
    if (fds)
        snmp_read(&fdset);
    else
        snmp_timeout();
  }

  fclose(fp);
  /* cleanup */
  for (hi = 0; hi < host_count; hi++) {
    if (hosts[hi].sess) snmp_close(hosts[hi].sess);
  }

    exit(0);
}
