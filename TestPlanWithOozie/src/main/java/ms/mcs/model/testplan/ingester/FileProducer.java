package ms.mcs.model.testplan.ingester;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.Validate;

import ms.mcs.model.testplan.util.JaxbUtil;
import ms.mcs.model.testplan.xsdschema.Execution;
import ms.mcs.model.testplan.xsdschema.TestCase;
import ms.mcs.model.testplan.xsdschema.TestSuite;
import ms.mcs.model.testplan.xsdschema.TestSuites;
import ms.mcs.model.testplan.xsdschema.TestTypeDesc;

public class FileProducer {

	private TestSuites generateRandomTestSuites() {
		TestSuites testSuites = new TestSuites();
		Execution execution = new Execution();
		execution.setExecutionTime(getXMLGregorianCalendarNow());
		execution.setPricingLibs("pricingLib");
		testSuites.setExec(execution);
		for (int i = 0; i < Math.random() * 10; i++) {
			TestSuite testSuite = new TestSuite();
			testSuite.setName(RandomStringUtils.random(20, "ABCDEFGHI"));
			for (int t = 0; t < Math.random() * 5; t++) {
				TestCase testCase = new TestCase();
				testCase.setPassed(Math.random() > 0.5 ? true : false);
				testCase.setTestType(RandomStringUtils.random(10, "ABCDEFG"));
				for (int j = 0; j < Math.random() * 10; j++) {
					testCase.getDescs().add(TestTypeDesc.DESC_1);
				}
				testSuite.getTestCases().add(testCase);
			}
			testSuites.getTestSuites().add(testSuite);
		}
		return testSuites;
	}

	private String getFileName() {
		return new StringBuilder("testsuites_").append(new SimpleDateFormat("yyyy_MM_dd_HHmmss_SSS").format(new Date()))
				.append(".xml").toString();
	}

	public void generateXmlFiles(String path, int cnt) throws IOException {
		for (int i = 0; i < cnt; i++) {
			FileUtils.writeStringToFile(new File(path, getFileName()),
					JaxbUtil.serialize(TestSuites.class, generateRandomTestSuites()), "UTF-8");
		}
	}

	public static void main(String[] args) throws IOException {
		Validate.notNull(args, "need 2 parameters,  directory and file count");
		String path = args[0];
		int cnt = new Integer(args[1]);
		new FileProducer().generateXmlFiles(path, cnt);
	}

	private XMLGregorianCalendar getXMLGregorianCalendarNow() {
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
			return now;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
