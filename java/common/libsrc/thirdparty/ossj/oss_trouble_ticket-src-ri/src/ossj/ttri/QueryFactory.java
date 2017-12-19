package ossj.ttri;

import javax.oss.IllegalArgumentException;
import javax.oss.QueryValue;
import java.util.Properties;

/**
 * Query Factory Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class QueryFactory {

    static private QueryFactory singleton = new QueryFactory();

    static public final QueryFactory getInstance() {
        return singleton;
    }


    // creates new QueryFactory
    protected QueryFactory() {
    }


    //-------------------------------------------------------------------
    // provide constructors that will provide all functionality as defined
    // in the TT JVT Session Bean bulk methods
    //-------------------------------------------------------------------
    public Query create(QueryValue queryValue,
                        String[] attrNames,
                        Properties[] props)
            throws IllegalArgumentException {

        Query query = (Query) queryValue;
        query.setSelectedAttributeNames(attrNames);
        query.executeQuery();
        return query;

    }

}
