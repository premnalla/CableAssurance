package com.nec.oss.ipb.producer.ri.xml;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import com.ilog.ossj.xml.binding.Registry;
import com.ilog.ossj.xml.mdb.XmlMessageDrivenBean;
import com.ilog.ossj.xml.mdb.JVTSessionXmlDelegate;
import com.nec.oss.ipb.producer.ri.xml.IPBJVTSessionXmlDelegate;

/**
 * Extension of the XML Tooling XmlMessageDrivenBean to handle
 * specifics of the IP Billing API. Basically, this class is needed
 * to specify the use of IPBJVTSessionXmlDelegate instead of 
 * JVTSessionXmlDelegate 
 */
public class IPBRIXmlMessageDrivenBean extends XmlMessageDrivenBean 
{
	/**
	 * Constructor  - calls the parent constructor
	 */
	public IPBRIXmlMessageDrivenBean() 
	{
		super(getIPBRIRegistry());
	}

	/**
	 * Returns the IPBRIConfiguration registry.
	 * Initializes it if necessary.
	 * @return Registry for the IP Billing API
	 */
	private static Registry getIPBRIRegistry() 
	{
		return IPBRIConfiguration.configureXmlBinding();
	}

	/**
	 * EJB Creation - calls the parent ejbCreate
	 */
	public void ejbCreate() 
	{
		super.ejbCreate();
	}

	/**
	 * EJB Removal - calls the parent ejbRemove
	 */
	public void ejbRemove() 
	{
		super.ejbRemove();
	}

	/**
	 * Create the JVTSessionXmlDelegate object. This is where we
     * specify the use of IPBJVTSessionXmlDelegate 
	 * 
	 * @see
	 */
	protected void createJVTSessionXmlDelegate() 
	{
		setDelegate(new IPBJVTSessionXmlDelegate(this));
	}
}
