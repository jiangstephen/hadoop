package hadoop.tutorial.xml;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class XmlReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		context.write(new Text("<configuration>"), new Text());
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		context.write(new Text("</configuration>"), new Text());
	}

	private Text outputKey = new Text();

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		for (Text value : values) {
			outputKey.set(constructPropertyXml(key, value));

			context.write(outputKey, new Text());
		}
	}

	public static String constructPropertyXml(Text name, Text value) {
		StringBuilder sb = new StringBuilder();
		sb.append("<property><name>").append(name).append("</name><value>").append(value)
				.append("</value></property>");
		return sb.toString();
	}
}
