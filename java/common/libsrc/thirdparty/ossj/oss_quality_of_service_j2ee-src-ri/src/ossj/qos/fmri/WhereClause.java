package ossj.qos.fmri;

import javax.oss.ManagedEntityValue;

/**
 * WhereClause
 *
 * @author  Audrey Ward
 * @version 1.0
 *
 * © Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public interface WhereClause {
    
    public String buildPrepStatementClause();
    public String[] getClauseMEVAttributeNames();
    public ManagedEntityValue buildManagedEntityTemplate();
}
