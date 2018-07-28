package hadoop.tutorial.xml;

import java.io.IOException;
import java.util.Arrays;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;
 
public class XmlMapReduceTest {
  MapDriver<LongWritable, Text, Text, Text> mapDriver;
  ReduceDriver<Text, Text, Text, Text> reduceDriver;
  MapReduceDriver<LongWritable, Text, Text, Text, Text, Text> mapReduceDriver;
 
  @Before
  public void setUp() {
    XmlMapper mapper = new XmlMapper();
    XmlReducer reducer = new XmlReducer();
    mapDriver = MapDriver.newMapDriver(mapper);
    reduceDriver = ReduceDriver.newReduceDriver(reducer);
    mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
  }
 
  @Test
  public void testMapper() throws IOException {
    mapDriver
    .withInput(new LongWritable(),new Text("<configuration><property><name>name1</name><value>value1</value></property></configuration>"))
    .withInput(new LongWritable(),new Text("<configuration><property><name>name2</name><value>value2</value></property></configuration>"));
    
    mapDriver
    .withOutput(new Text("name1"), new Text("value1"))
    .withOutput(new Text("name2"), new Text("value2"));
    
    mapDriver.runTest();
  }
 
  @Test
  public void testReducer() throws IOException {
    reduceDriver.withInput(new Text("name1"), Arrays.asList(new Text("value1"), new Text("value2")));
    reduceDriver
    .withOutput(new Text("<configuration>"), new Text())
    .withOutput(new Text("<property><name>name1</name><value>value1</value></property>"), new Text())
    .withOutput(new Text("<property><name>name1</name><value>value2</value></property>"), new Text())
    .withOutput(new Text("</configuration>"), new Text());
    reduceDriver.runTest();
  }
  
  @Test
  public void testMapReduce() throws IOException {
    mapReduceDriver
    .withInput(new LongWritable(),new Text("<configuration><property><name>name1</name><value>value1</value></property></configuration>"))
    .withInput(new LongWritable(),new Text("<configuration><property><name>name2</name><value>value2</value></property></configuration>"));
    
    mapReduceDriver
    .withOutput(new Text("<configuration>"), new Text())
    .withOutput(new Text("<property><name>name1</name><value>value1</value></property>"), new Text())
    .withOutput(new Text("<property><name>name2</name><value>value2</value></property>"), new Text())
    .withOutput(new Text("</configuration>"), new Text());
    mapReduceDriver.runTest();
  }
  
}