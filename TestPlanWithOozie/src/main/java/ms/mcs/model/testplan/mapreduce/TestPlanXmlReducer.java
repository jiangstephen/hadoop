package ms.mcs.model.testplan.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.mapreduce.Reducer;

import ms.mcs.model.testplan.mapreduce.writable.TestCaseValue;
import ms.mcs.model.testplan.mapreduce.writable.TestSuiteKey;

public class TestPlanXmlReducer extends Reducer<TestSuiteKey, TestCaseValue, TestSuiteKey, BooleanWritable> {


	@Override
	public void reduce(TestSuiteKey testSuiteKey, Iterable<TestCaseValue> testCaseValues, Context context) throws IOException, InterruptedException {
		boolean passed = true;
		for (TestCaseValue testCaseValue : testCaseValues) {
			if(!testCaseValue.isPassed()){
				passed = false;
				break;
			}
		}
		context.write(testSuiteKey, new BooleanWritable(passed));
	}


}
