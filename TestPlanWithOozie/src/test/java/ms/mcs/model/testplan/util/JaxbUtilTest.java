package ms.mcs.model.testplan.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import ms.mcs.model.testplan.xsdschema.TestSuite;
import ms.mcs.model.testplan.xsdschema.TestSuites;

public class JaxbUtilTest {
	
	@Test
	public void test_serialize_deSerialize() throws IOException{
		
		final String testPlanContent =  IOUtils.toString(new ClassPathResource("testplan.xml").getInputStream());
	    TestSuites  testSuites = JaxbUtil.deserialize(TestSuites.class,  testPlanContent);
		Assert.assertNotNull(testSuites);
		Assert.assertEquals("pricingLibs", testSuites.getExec().getPricingLibs());
		Assert.assertNotNull(JaxbUtil.serialize(TestSuite.class, testSuites.getTestSuites().get(0)));
	}
	
	
	

}
