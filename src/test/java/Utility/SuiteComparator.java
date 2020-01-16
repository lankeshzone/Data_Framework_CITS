package Utility;

	import org.testng.ISuite;
	import org.testng.xml.XmlSuite;

	import java.util.ArrayList;
	import java.util.Comparator;
	import java.util.List;

	public class SuiteComparator implements Comparator<ISuite> {

	    public List<String> xmlNames;

	    public SuiteComparator(List<XmlSuite> parentXmlSuites) {
	        for (XmlSuite parentXmlSuite : parentXmlSuites) {
	            List<XmlSuite> childXmlSuites = parentXmlSuite.getChildSuites();
	            xmlNames = new ArrayList<String>();
	            xmlNames.add(parentXmlSuite.getFileName());
	            for (XmlSuite xmlsuite : childXmlSuites) {
	                xmlNames.add(xmlsuite.getFileName());
	            }
	        }
	    }

	    public int compare(ISuite suite1, ISuite suite2) {
	        String suite1Name = suite1.getXmlSuite().getFileName();
	        String suite2Name = suite2.getXmlSuite().getFileName();
	        return xmlNames.indexOf(suite1Name) - xmlNames.indexOf(suite2Name);
	    }
	}

