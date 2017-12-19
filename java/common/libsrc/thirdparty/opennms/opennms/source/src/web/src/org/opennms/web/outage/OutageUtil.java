//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2003 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//

package org.opennms.web.outage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.opennms.web.Util;
import org.opennms.web.outage.filter.Filter;
import org.opennms.web.outage.filter.InterfaceFilter;
import org.opennms.web.outage.filter.LostServiceDateAfterFilter;
import org.opennms.web.outage.filter.LostServiceDateBeforeFilter;
import org.opennms.web.outage.filter.NegativeInterfaceFilter;
import org.opennms.web.outage.filter.NegativeNodeFilter;
import org.opennms.web.outage.filter.NegativeServiceFilter;
import org.opennms.web.outage.filter.NodeFilter;
import org.opennms.web.outage.filter.OutageFilter;
import org.opennms.web.outage.filter.RegainedServiceDateAfterFilter;
import org.opennms.web.outage.filter.RegainedServiceDateBeforeFilter;
import org.opennms.web.outage.filter.ServiceFilter;

public abstract class OutageUtil extends Object {
    protected static final HashMap sortStyles;

    protected static final HashMap outTypes;

    protected static final String DOWN_COLOR = "red";

    public static final String FILTER_SERVLET_URL_BASE = "outage/list";

    static {
        sortStyles = new java.util.HashMap();

        // integer -> style mappings
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._NODE), OutageFactory.SortStyle.NODE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._INTERFACE), OutageFactory.SortStyle.INTERFACE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._SERVICE), OutageFactory.SortStyle.SERVICE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._IFLOSTSERVICE), OutageFactory.SortStyle.IFLOSTSERVICE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._IFREGAINEDSERVICE), OutageFactory.SortStyle.IFREGAINEDSERVICE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._ID), OutageFactory.SortStyle.ID);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._REVERSE_NODE), OutageFactory.SortStyle.REVERSE_NODE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._REVERSE_INTERFACE), OutageFactory.SortStyle.REVERSE_INTERFACE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._REVERSE_SERVICE), OutageFactory.SortStyle.REVERSE_SERVICE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._REVERSE_IFLOSTSERVICE), OutageFactory.SortStyle.REVERSE_IFLOSTSERVICE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._REVERSE_IFREGAINEDSERVICE), OutageFactory.SortStyle.REVERSE_IFREGAINEDSERVICE);
        sortStyles.put(Integer.toString(OutageFactory.SortStyle._REVERSE_ID), OutageFactory.SortStyle.REVERSE_ID);

        // style -> integer mappings
        sortStyles.put(OutageFactory.SortStyle.NODE, Integer.toString(OutageFactory.SortStyle._NODE));
        sortStyles.put(OutageFactory.SortStyle.INTERFACE, Integer.toString(OutageFactory.SortStyle._INTERFACE));
        sortStyles.put(OutageFactory.SortStyle.SERVICE, Integer.toString(OutageFactory.SortStyle._SERVICE));
        sortStyles.put(OutageFactory.SortStyle.IFLOSTSERVICE, Integer.toString(OutageFactory.SortStyle._IFLOSTSERVICE));
        sortStyles.put(OutageFactory.SortStyle.IFREGAINEDSERVICE, Integer.toString(OutageFactory.SortStyle._IFREGAINEDSERVICE));
        sortStyles.put(OutageFactory.SortStyle.ID, Integer.toString(OutageFactory.SortStyle._ID));
        sortStyles.put(OutageFactory.SortStyle.REVERSE_NODE, Integer.toString(OutageFactory.SortStyle._REVERSE_NODE));
        sortStyles.put(OutageFactory.SortStyle.REVERSE_INTERFACE, Integer.toString(OutageFactory.SortStyle._REVERSE_INTERFACE));
        sortStyles.put(OutageFactory.SortStyle.REVERSE_SERVICE, Integer.toString(OutageFactory.SortStyle._REVERSE_SERVICE));
        sortStyles.put(OutageFactory.SortStyle.REVERSE_IFLOSTSERVICE, Integer.toString(OutageFactory.SortStyle._REVERSE_IFLOSTSERVICE));
        sortStyles.put(OutageFactory.SortStyle.REVERSE_IFREGAINEDSERVICE, Integer.toString(OutageFactory.SortStyle._REVERSE_IFREGAINEDSERVICE));
        sortStyles.put(OutageFactory.SortStyle.REVERSE_ID, Integer.toString(OutageFactory.SortStyle._REVERSE_ID));

        outTypes = new java.util.HashMap();

        // integer -> outage type mappings
        outTypes.put(Integer.toString(OutageFactory.OutageType._CURRENT), OutageFactory.OutageType.CURRENT);
        outTypes.put(Integer.toString(OutageFactory.OutageType._RESOLVED), OutageFactory.OutageType.RESOLVED);
        outTypes.put(Integer.toString(OutageFactory.OutageType._BOTH), OutageFactory.OutageType.BOTH);

        // outage type -> integer mappings
        outTypes.put(OutageFactory.OutageType.CURRENT, Integer.toString(OutageFactory.OutageType._CURRENT));
        outTypes.put(OutageFactory.OutageType.RESOLVED, Integer.toString(OutageFactory.OutageType._RESOLVED));
        outTypes.put(OutageFactory.OutageType.BOTH, Integer.toString(OutageFactory.OutageType._BOTH));
    }

    public static OutageFactory.SortStyle getSortStyle(String sortStyleString) {
        if (sortStyleString == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return (OutageFactory.SortStyle) sortStyles.get(sortStyleString.toLowerCase());
    }

    public static String getSortStyleString(OutageFactory.SortStyle sortStyle) {
        if (sortStyle == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return (String) sortStyles.get(sortStyle);
    }

    public static OutageFactory.OutageType getOutageType(String outTypeString) {
        if (outTypeString == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return (OutageFactory.OutageType) outTypes.get(outTypeString.toLowerCase());
    }

    public static String getOutageTypeString(OutageFactory.OutageType outType) {
        if (outType == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return (String) outTypes.get(outType);
    }

    public static Filter getFilter(String filterString) {
        if (filterString == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        Filter filter = null;

        StringTokenizer tokens = new StringTokenizer(filterString, "=");
        String type = tokens.nextToken();
        String value = tokens.nextToken();

        if (type.equals(NodeFilter.TYPE)) {
            filter = new NodeFilter(Integer.parseInt(value));
        } else if (type.equals(InterfaceFilter.TYPE)) {
            filter = new InterfaceFilter(value);
        } else if (type.equals(ServiceFilter.TYPE)) {
            filter = new ServiceFilter(Integer.parseInt(value));
        } else if (type.equals(OutageFilter.TYPE)) {
            filter = new OutageFilter(Integer.parseInt(value));
        } else if (type.equals(NegativeNodeFilter.TYPE)) {
            filter = new NegativeNodeFilter(Integer.parseInt(value));
        } else if (type.equals(NegativeInterfaceFilter.TYPE)) {
            filter = new NegativeInterfaceFilter(value);
        } else if (type.equals(NegativeServiceFilter.TYPE)) {
            filter = new NegativeServiceFilter(Integer.parseInt(value));
        } else if (type.equals(LostServiceDateBeforeFilter.TYPE)) {
            filter = new LostServiceDateBeforeFilter(Long.parseLong(value));
        } else if (type.equals(LostServiceDateAfterFilter.TYPE)) {
            filter = new LostServiceDateAfterFilter(Long.parseLong(value));
        } else if (type.equals(RegainedServiceDateBeforeFilter.TYPE)) {
            filter = new RegainedServiceDateBeforeFilter(Long.parseLong(value));
        } else if (type.equals(RegainedServiceDateAfterFilter.TYPE)) {
            filter = new RegainedServiceDateAfterFilter(Long.parseLong(value));
        }

        return (filter);
    }

    public static String getFilterString(Filter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return (filter.getDescription());
    }

    /** Returns the color to use for an outage, if no color then it returns null. */
    public static String getStatusColor(Outage outage) {
        if (outage == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        String color = null;

        if (outage.getRegainedServiceTime() == null) {
            color = DOWN_COLOR;
        }

        return color;
    }

    /** Returns the icon to use for an outage, if no icon then it returns null. */
    public static String getStatusLabel(Outage outage) {
        if (outage == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        String label = null;

        if (outage.getRegainedServiceTime() == null) {
            label = "DOWN";
        }

        return label;
    }

    protected static final String[] LINK_IGNORES = new String[] { "sortby", "outtype", "limit", "multiple", "filter" };

    public static String makeLink(HttpServletRequest request, OutageFactory.SortStyle sortStyle, OutageFactory.OutageType outageType, ArrayList filters, int limit) {
        if (request == null || sortStyle == null || outageType == null || filters == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Cannot take a zero or negative limit value.");
        }

        HashMap additions = new HashMap();
        additions.put("sortby", OutageUtil.getSortStyleString(sortStyle));
        additions.put("outtype", OutageUtil.getOutageTypeString(outageType));
        additions.put("limit", Integer.toString(limit));

        if (filters != null) {
            String[] filterStrings = new String[filters.size()];

            for (int i = 0; i < filters.size(); i++) {
                filterStrings[i] = OutageUtil.getFilterString((Filter) filters.get(i));
            }

            additions.put("filter", filterStrings);
        }

        return FILTER_SERVLET_URL_BASE + "?" + Util.makeQueryString(request, additions, LINK_IGNORES, Util.IgnoreType.REQUEST_ONLY);
    }

    public static String makeLink(HttpServletRequest request, OutageQueryParms parms) {
        if (request == null || parms == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return makeLink(request, parms.sortStyle, parms.outageType, parms.filters, parms.limit);
    }

    public static String makeLink(HttpServletRequest request, OutageQueryParms parms, OutageFactory.SortStyle sortStyle) {
        if (request == null || parms == null || sortStyle == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return makeLink(request, sortStyle, parms.outageType, parms.filters, parms.limit);
    }

    public static String makeLink(HttpServletRequest request, OutageQueryParms parms, OutageFactory.OutageType outageType) {
        if (request == null || parms == null || outageType == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return makeLink(request, parms.sortStyle, outageType, parms.filters, parms.limit);
    }

    public static String makeLink(HttpServletRequest request, OutageQueryParms parms, ArrayList filters) {
        if (request == null || parms == null || filters == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return makeLink(request, parms.sortStyle, parms.outageType, filters, parms.limit);
    }

    public static String makeLink(HttpServletRequest request, OutageQueryParms parms, Filter filter, boolean add) {
        if (request == null || parms == null || filter == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        ArrayList newList = new ArrayList(parms.filters);

        if (add) {
            newList.add(filter);
        } else {
            newList.remove(filter);
        }

        return makeLink(request, parms.sortStyle, parms.outageType, newList, parms.limit);
    }

    public static String makeHiddenTags(HttpServletRequest request, OutageFactory.SortStyle sortStyle, OutageFactory.OutageType outageType, ArrayList filters, int limit) {
        if (request == null || sortStyle == null || outageType == null || filters == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Cannot take a zero or negative limit value.");
        }

        HashMap additions = new HashMap();
        additions.put("sortby", OutageUtil.getSortStyleString(sortStyle));
        additions.put("outtype", OutageUtil.getOutageTypeString(outageType));
        additions.put("limit", Integer.toString(limit));

        if (filters != null) {
            String[] filterStrings = new String[filters.size()];

            for (int i = 0; i < filters.size(); i++) {
                filterStrings[i] = OutageUtil.getFilterString((Filter) filters.get(i));
            }

            additions.put("filter", filterStrings);
        }

        return Util.makeHiddenTags(request, additions, LINK_IGNORES, Util.IgnoreType.REQUEST_ONLY);
    }

    public static String makeHiddenTags(HttpServletRequest request, OutageQueryParms parms) {
        if (request == null || parms == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return makeHiddenTags(request, parms.sortStyle, parms.outageType, parms.filters, parms.limit);
    }

    public static String makeHiddenTags(HttpServletRequest request, OutageQueryParms parms, OutageFactory.SortStyle sortStyle) {
        if (request == null || parms == null || sortStyle == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return makeHiddenTags(request, sortStyle, parms.outageType, parms.filters, parms.limit);
    }

    public static String makeHiddenTags(HttpServletRequest request, OutageQueryParms parms, OutageFactory.OutageType outageType) {
        if (request == null || parms == null || outageType == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return makeHiddenTags(request, parms.sortStyle, outageType, parms.filters, parms.limit);
    }

    public static String makeHiddenTags(HttpServletRequest request, OutageQueryParms parms, ArrayList filters) {
        if (request == null || parms == null || filters == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        return makeHiddenTags(request, parms.sortStyle, parms.outageType, filters, parms.limit);
    }

    public static String makeHiddenTags(HttpServletRequest request, OutageQueryParms parms, Filter filter, boolean add) {
        if (request == null || parms == null || filter == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        ArrayList newList = new ArrayList(parms.filters);

        if (add) {
            newList.add(filter);
        } else {
            newList.remove(filter);
        }

        return makeHiddenTags(request, parms.sortStyle, parms.outageType, newList, parms.limit);
    }

}
