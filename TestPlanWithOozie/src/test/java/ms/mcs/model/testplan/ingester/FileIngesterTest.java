package ms.mcs.model.testplan.ingester;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class FileIngesterTest {
	
	private FileIngester testee;
	
	private File testFolder = new File("target/test");
	
	@Before
	public void setUp() throws IOException{
		testee = new FileIngester();
		FileUtils.forceMkdir(testFolder);
	}
	
	@After
	public void tearDown() throws IOException{
		FileUtils.forceDelete(testFolder);
	}
	
	@Test
	public void test_copyToHDFS() throws IOException, ParseException{
		FileIngesterContext fileIngesterContext = new FileIngesterContext();
		fileIngesterContext.setDateDir(testFolder.getAbsolutePath());
		fileIngesterContext.setHadoopCoreSiteXml(testFolder.getAbsolutePath()+"/hadoopCoreSite.xml");
		fileIngesterContext.setHadoopDir("hadoop");
		fileIngesterContext.setHadoopHdfsSiteXml(testFolder.getAbsolutePath()+"/hadoopHdfsSite.xml");
		fileIngesterContext.setSourceDirectory(testFolder.getAbsolutePath());
		testee.copyToHDFS(fileIngesterContext);
	}
	
	@Test
	public void test_findNewFile() throws IOException, InterruptedException{
		for(int i=0; i<10; i++){
			FileUtils.touch(new File(testFolder, "testsuites" + i +".xml"));
			FileUtils.touch(new File(testFolder, "shouldnotinclude" + i +".xml"));
		}
		Date now = new Date();
		Thread.sleep(100);
		for(int i=10; i<20; i++){
			FileUtils.touch(new File(testFolder, "testsuites" + i +".xml"));
			FileUtils.touch(new File(testFolder, "shouldnotinclude" + i +".xml"));
			
		}
		Collection<File> files = testee.findNewFiles(testFolder, now);
		Assert.assertEquals(10, files.size());
		Iterator<File>  fileIterator = files.iterator();
		int i = 10;
		while(fileIterator.hasNext()){
			String fileName = "testsuites" + (i++) +".xml";
			Assert.assertEquals(fileName,   FilenameUtils.getName(fileIterator.next().getAbsolutePath()));
		}
	}
	
	

}
