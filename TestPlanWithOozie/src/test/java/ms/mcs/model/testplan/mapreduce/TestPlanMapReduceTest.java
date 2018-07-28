package ms.mcs.model.testplan.mapreduce;

import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import ms.mcs.model.testplan.mapreduce.writable.TestSuiteKey;
import ms.mcs.model.testplan.mapreduce.writable.TestCaseValue;

public class TestPlanMapReduceTest {
	  MapDriver<LongWritable, Text, TestSuiteKey, TestCaseValue> mapDriver;
	  ReduceDriver<TestSuiteKey, TestCaseValue, TestSuiteKey, BooleanWritable> reduceDriver;
	  MapReduceDriver<LongWritable, Text, TestSuiteKey, TestCaseValue, TestSuiteKey, BooleanWritable> mapReduceDriver;
	 
	  @Before
	  public void setUp() {
	    TestPlanXmlMapper mapper = new TestPlanXmlMapper();
	    TestPlanXmlReducer reducer = new TestPlanXmlReducer();
	    mapDriver = MapDriver.newMapDriver(mapper);
	    reduceDriver = ReduceDriver.newReduceDriver(reducer);
	    mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
	  }
	 
	  @Test
	  public void testMapper() throws IOException {
	    mapDriver
	    .withInput(new LongWritable(),new Text(IOUtils.toString(new ClassPathResource("testplan.xml").getInputStream())));

	    mapDriver
	    .withOutput(new TestSuiteKey("model1",  1514826000000L, "pricingLibs"), new TestCaseValue(true, "xmlContent"))
	    .withOutput(new TestSuiteKey("model2",  1514826000000L, "pricingLibs"), new TestCaseValue(true, "xmlContent"));
	    mapDriver.runTest();
	  }
	 
	  @Test
	  public void testReducer() throws IOException {
	    reduceDriver.withInput(new TestSuiteKey("model1",  1514826000000L, "pricingLibs"), Arrays.asList(new TestCaseValue(true, "value1"), new TestCaseValue(false, "value2")));
	    reduceDriver.withOutput(new TestSuiteKey("model1",  1514826000000L, "pricingLibs"), new BooleanWritable(false));
	    reduceDriver.runTest();
	  }
	  
	  @Test
	  public void testMapReduce() throws IOException {
	    mapReduceDriver
	    .withInput(new LongWritable(),new Text(IOUtils.toString(new ClassPathResource("testplan.xml").getInputStream())));

	    mapReduceDriver
	    .withOutput(new TestSuiteKey("model1",  1514826000000L, "pricingLibs"), new BooleanWritable(true))
	    .withOutput(new TestSuiteKey("model2",  1514826000000L, "pricingLibs"), new BooleanWritable(true));

	    mapReduceDriver.runTest();
	  }
}
