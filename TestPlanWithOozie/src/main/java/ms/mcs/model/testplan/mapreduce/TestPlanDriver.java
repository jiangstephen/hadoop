package ms.mcs.model.testplan.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class TestPlanDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		conf.set("xmlinput.start", "<testSuites");
		conf.set("xmlinput.end", "</testSuites>");
		Job job = new Job(conf);
		job.setJarByClass(TestPlanDriver.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(TestPlanXmlMapper.class);
		job.setReducerClass(TestPlanXmlReducer.class);

		job.setInputFormatClass(XmlInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
	}

}
