package ms.mcs.model.testplan.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//import org.apache.hadoop.mapred.Mapper;

import ms.mcs.model.testplan.mapreduce.writable.TestCaseValue;
import ms.mcs.model.testplan.mapreduce.writable.TestSuiteKey;
import ms.mcs.model.testplan.util.JaxbUtil;
import ms.mcs.model.testplan.xsdschema.TestCase;
import ms.mcs.model.testplan.xsdschema.TestSuite;
import ms.mcs.model.testplan.xsdschema.TestSuites;

public class TestPlanXmlMapper extends Mapper<LongWritable, Text, TestSuiteKey, TestCaseValue> {
	
		@Override
		protected void map(LongWritable key, Text value, Mapper.Context context)
				throws IOException, InterruptedException {
			String testSuitesXml = value.toString();
			try {
				TestSuites testSuites = JaxbUtil.deserialize(TestSuites.class, testSuitesXml);
				for(TestSuite testSuite : testSuites.getTestSuites()){
					for(TestCase testCase: testSuite.getTestCases()){
						TestSuiteKey testCaseKey = new TestSuiteKey(testSuite.getName(), 
								testSuites.getExec().getExecutionTime().toGregorianCalendar().getTimeInMillis(),  
								testSuites.getExec().getPricingLibs());
						TestCaseValue testCaseValue = new TestCaseValue(testCase.isPassed(), "xmlContent");
						context.write(testCaseKey, testCaseValue);
					}
				}
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
	}