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
// Modifications:
//
// 2003 Jan 31: Cleaned up some unused imports.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
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
// Tab Size = 8
//

package org.opennms.netmgt.eventd.datablock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;

/**
 * <pre>
 * The information read from the eventconf.xml is stored here. It maintains
 *  a map,  keyed with 'EventKey's. 
 * 
 *  It also has an UEI to 'EventKey'list map - this mapping fastens the lookup 
 *  for OpenNMS internal events when different masks are configured for the
 *  same UEI.
 * 
 *  When a lookup is to be done for an 'Event',  
 *  - its 'key' is used to get a lookup,
 *  - if no match is found for the key, UEI is used to lookup the keys that got added for that UEI
 *    and the first best fit in the event map for any of the UEI keys are used
 *  - if there is still no match at this point, all keys in the eventconf are iterated through to
 *    find a match
 *  
 * </pre>
 * 
 * @author <A HREF="mailto:sowmya@opennms.org">Sowmya Nataraj </A>
 * @author <A HREF="http://www.opennms.org">OpenNMS.org </A>
 */
public class EventConfData extends Object {
    /**
     * The map keyed with 'EventKey's
     */
    private LinkedHashMap m_eventMap;

    /**
     * The map of UEI to 'EventKey's list - used mainly to find matches for the
     * OpenNMS internal events faster(in cases where there are multiple masks
     * for the same UEI)
     */
    // private Hashtable m_ueiToKeyListMap;
    private LinkedHashMap m_ueiToKeyListMap;

    /**
     * Purely used for debugging
     */
    private void dumpEventMap() {
        Category log = ThreadCategory.getInstance(EventConfData.class);
        log.debug("Size of the map: " + m_eventMap.size());

        Iterator entryIterator = m_eventMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIterator.next();
            EventKey key = (EventKey) entry.getKey();
            Object value = entry.getValue();

            if (log.isDebugEnabled()) {
                log.debug("Eventkey: " + key.toString() + " looks up: " + value);
                log.debug("Eventkey: " + key.toString() + " looks up: " + m_eventMap.get(key));
            }
        }
    }

    /**
     * See if there is a match for the event from the list of event keys
     * 
     * @return the eventconf entry if a match is found
     */
    private org.opennms.netmgt.xml.eventconf.Event getMatchInKeyList(List keylist, org.opennms.netmgt.xml.event.Event event) {
        // dumpEventMap();
        Iterator keysIter = keylist.iterator();
        while (keysIter.hasNext()) {
            EventKey eventKey = (EventKey) keysIter.next();

            boolean keyMatchFound = eventMatchesKey(eventKey, event);

            // if a match was found, return the config
            if (keyMatchFound) {
                org.opennms.netmgt.xml.eventconf.Event matchedEvent = (org.opennms.netmgt.xml.eventconf.Event) m_eventMap.get(eventKey);
                if (matchedEvent != null) {
                    Category log = ThreadCategory.getInstance(EventConfData.class);
                    if (log.isDebugEnabled())
                        log.debug("Match found using key: " + eventKey.toString());
                }

                return matchedEvent;
            }
        }

        return null;
    }

    /**
     * Check whether the event matches the passed key
     * 
     * @return true if the event matches the passed key
     */
    private boolean eventMatchesKey(EventKey eventKey, org.opennms.netmgt.xml.event.Event event) {
        // go through the key elements and see if this event
        // will match
        boolean maskMatch = true;

        Iterator keysetIter = eventKey.keySet().iterator();
        while (keysetIter.hasNext() && maskMatch) {
            String key = (String) keysetIter.next();

            List maskValues = (List) eventKey.get(key);

            // get the event value for this key
            String eventvalue = EventKey.getMaskElementValue(event, key);
            maskMatch = eventValuePassesMaskValue(eventvalue, maskValues);
            if (!maskMatch) {
                return maskMatch;
            }
        }

        return maskMatch;
    }

    /**
     * Check whether the eventvalue passes any of the mask values Mask values
     * ending with a '%' only need to be a substring of the eventvalue for the
     * eventvalue to pass the mask
     * 
     * @return true if the values passes the mask
     */
    private boolean eventValuePassesMaskValue(String eventvalue, List maskValues) {
        boolean maskMatch = false;

        Iterator valiter = maskValues.iterator();
        while (valiter.hasNext() && !maskMatch) {
            String keyvalue = (String) valiter.next();
            if (keyvalue != null && eventvalue != null) {
                int len = keyvalue.length();
                if (keyvalue.equals(eventvalue)) {
                    maskMatch = true;
                } else if (keyvalue.charAt(len - 1) == '%') {
                    if (eventvalue.startsWith(keyvalue.substring(0, len - 1)))
                        maskMatch = true;
                }
            }
        }

        return maskMatch;
    }

    /**
     * Update the uei to keylist map
     */
    private void updateUeiToKeyListMap(EventKey eventKey, org.opennms.netmgt.xml.eventconf.Event event) {
        String eventUei = event.getUei();
        List keylist = (List) m_ueiToKeyListMap.get(eventUei);
        if (keylist == null) {
            keylist = new ArrayList();
            keylist.add(eventKey);

            m_ueiToKeyListMap.put(eventUei, keylist);
        } else {
            if (!keylist.contains(eventKey))
                keylist.add(eventKey);
        }
    }

    /**
     * Default constructor - allocate the maps
     */
    public EventConfData() {
        m_eventMap = new LinkedHashMap();

        m_ueiToKeyListMap = new LinkedHashMap();
    }

    /**
     * Add an event - add to the 'EventKey' map using the event mask by default.
     * If the event has snmp information, add using the snmp EID
     * 
     * @param event
     *            the org.opennms.netmgt.xml.eventconf.Event
     */
    public synchronized void put(org.opennms.netmgt.xml.eventconf.Event event) {

        // the event key
        EventKey eventKey = new EventKey(event);

        // add to the configevent map first
        m_eventMap.put(eventKey, event);

        // add to the uei to key list map
        updateUeiToKeyListMap(eventKey, event);

        // if event has snmp information, add to the snmp map
        org.opennms.netmgt.xml.eventconf.Snmp eventSnmp = event.getSnmp();
        if (eventSnmp != null) {
            String eventEID = eventSnmp.getId();
            if (eventEID != null) {
                EventKey snmpKey = new EventKey();
                snmpKey.put(EventKey.TAG_SNMP_EID, new EventMaskValueList(eventEID));

                m_eventMap.put(snmpKey, event);

                // add to the uei to key list map
                updateUeiToKeyListMap(snmpKey, event);
            }
        }
    }

    /**
     * Add an event with the specified key
     * 
     * @param key
     *            the EventKey for this event
     * @param event
     *            the org.opennms.netmgt.xml.eventconf.Event
     */
    public synchronized void put(EventKey key, org.opennms.netmgt.xml.eventconf.Event event) {
        m_eventMap.put(key, event);

        // add to the uei to key list map
        updateUeiToKeyListMap(key, event);
    }

    /**
     * <pre>
     * Get the right configuration for the event - the eventkey is used first.
     *  If no match is found, the event's uei to keylist is iterated through, and these keys
     *  used to lookup the event map. if still no match is found, all eventconf
     *  keys are iterated through to find a match. The first successful match is returned.
     * 
     *  
     * <EM>
     * NOTE:
     * </EM>
     * The first right config event that the event matches is returned.
     *  The ordering of the configurations is the responsibility of the user
     * </pre>
     * 
     * @param event
     *            the event which is to be looked up
     */
    public synchronized org.opennms.netmgt.xml.eventconf.Event getEvent(org.opennms.netmgt.xml.event.Event event) {
        org.opennms.netmgt.xml.eventconf.Event matchedEvent = null;

        //
        // use the eventkey and see if there is a match
        //
        EventKey key = new EventKey(event);
        matchedEvent = (org.opennms.netmgt.xml.eventconf.Event) m_eventMap.get(key);
        if (matchedEvent != null) {
            Category log = ThreadCategory.getInstance(EventConfData.class);
            if (log.isDebugEnabled())
                log.debug("Match found using key: " + key.toString());

            return matchedEvent;
        }

        //
        // get the UEI and see if the UEI keys get a match - this step is here
        // to make the matching process faster in case of usual internal events
        // of OpenNMS, using the UEI shortens the search as against going
        // through
        // the entire eventconf for each event
        //
        String uei = event.getUei();
        if (uei != null) {
            // Go through the uei to keylist map
            List keylist = (List) m_ueiToKeyListMap.get(uei);
            if (keylist != null) {
                // check the event keys known for this uei
                matchedEvent = getMatchInKeyList(keylist, event);
            }
        }

        //
        // if still no match, no option but to go through all known keys
        //
        if (matchedEvent == null) {
            Iterator entryIterator = m_eventMap.entrySet().iterator();
            while (entryIterator.hasNext() && (matchedEvent == null)) {
                Map.Entry entry = (Map.Entry) entryIterator.next();
                EventKey iterKey = (EventKey) entry.getKey();

                boolean keyMatchFound = eventMatchesKey(iterKey, event);

                // if a match was found, return the config
                if (keyMatchFound) {
                    Category log = ThreadCategory.getInstance(EventConfData.class);
                    if (log.isDebugEnabled())
                        log.debug("Match found using key: " + iterKey.toString());

                    matchedEvent = (org.opennms.netmgt.xml.eventconf.Event) entry.getValue();
                }
            }
        }

        return matchedEvent;
    }

    /**
     * Get the event with the specified snmp key
     * 
     * @param eid
     *            the snmp eid
     */
    public synchronized org.opennms.netmgt.xml.eventconf.Event getEventBySnmp(String eid) {
        EventKey key = new EventKey();
        key.put(EventKey.TAG_SNMP_EID, new EventMaskValueList(eid));

        return (org.opennms.netmgt.xml.eventconf.Event) m_eventMap.get(key);
    }

    /**
     * Get the event with the specified uei
     * 
     * @param uei
     *            the uei
     */
    public synchronized org.opennms.netmgt.xml.eventconf.Event getEventByUEI(String uei) {
        EventKey key = new EventKey();
        key.put(EventKey.TAG_UEI, new EventMaskValueList(uei));

        return (org.opennms.netmgt.xml.eventconf.Event) m_eventMap.get(key);
    }

    /**
     * Clear out the data
     */
    public synchronized void clear() {
        m_eventMap.clear();
        m_ueiToKeyListMap.clear();
    }

}
