package ms.mcs.model.testplan.ingester;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class FileProducerTest {
	
	private FileProducer testee;
	
	private File folder = new File("target/test");
	
	@Before
	public void setUp() throws IOException{
		this.testee = new FileProducer();
		FileUtils.forceMkdir(folder);
	}
	
	@After
	public void tearDown() throws IOException {
		FileUtils.forceDelete(folder);
	}
	
	@Test
	public void test() throws IOException{
		testee.generateXmlFiles("target/test", 10);
		Assert.assertEquals(10, FileUtils.listFiles(folder, new String[]{"xml"}, false).size());
	}
	

}
