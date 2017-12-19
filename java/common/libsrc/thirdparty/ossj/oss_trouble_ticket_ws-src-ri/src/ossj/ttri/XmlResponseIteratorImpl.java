/*
 * Class.java
 *
 * Created on September 9, 2003, 1:53 PM
 */

package ossj.ttri;

/**
 *
 * @author  vperrot
 */
public class XmlResponseIteratorImpl implements XmlResponseIterator {

    protected XmlResponseIteratorIF xmlRespIter;

    /** Creates a new instance of Class */
    public XmlResponseIteratorImpl(XmlResponseIteratorIF xmlRespIter) {
        this.xmlRespIter = xmlRespIter;
    }

    public String getNext(int howMany)
            throws java.rmi.RemoteException {
        return (xmlRespIter.getNext(howMany));
    }

    public void remove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
        xmlRespIter.remove();
    }

}
