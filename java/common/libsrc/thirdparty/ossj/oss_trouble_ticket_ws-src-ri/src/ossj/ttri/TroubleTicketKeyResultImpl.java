package ossj.ttri;

import javax.oss.ManagedEntityKey;
import javax.oss.trouble.TroubleTicketKey;
import javax.oss.trouble.TroubleTicketKeyResult;

/**
 * TroubleTicketKeyResult Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TroubleTicketKeyResultImpl implements TroubleTicketKeyResult,
        java.io.Serializable {

    private TroubleTicketKey ttKey;
    private boolean succeeded;
    private Exception exception;

    public TroubleTicketKeyResultImpl() {
    }

    public TroubleTicketKeyResultImpl(TroubleTicketKey ttKey,
                                      boolean succeeded,
                                      Exception exception) {
        this.ttKey = ttKey;
        this.succeeded = succeeded;
        this.exception = exception;
    }

    public ManagedEntityKey getManagedEntityKey() {
        return ttKey;
    }

    public TroubleTicketKey getTroubleTicketKey() {
        return ttKey;
    }

    public boolean isSuccess() {
        return succeeded;
    }

    public Exception getException() {
        return exception;
    }

    public void setManagedEntityKey(ManagedEntityKey key) {
        this.ttKey = (TroubleTicketKey) key;
    }

    public void setTroubleTicketKey(TroubleTicketKey key) {
        this.ttKey = key;
    }

    public void setSuccess(boolean is_success) {
        this.succeeded = is_success;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
