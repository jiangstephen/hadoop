package ms.mcs.model.testplan.mapreduce.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.hadoop.io.Writable;

public class TestCaseValue implements Writable {

	private boolean passed;
	
	private String xmlContent;
	
	public TestCaseValue(){
		
	}
	
	public TestCaseValue(boolean passed,  String xmlContent){
		this.passed = passed;
		this.xmlContent = xmlContent;
	}
	public void readFields(DataInput dataInput) throws IOException {
		passed = dataInput.readBoolean();
		xmlContent = dataInput.readUTF();
	}

	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeBoolean(passed);
		dataOutput.writeUTF(xmlContent);
	}
	public boolean isPassed() {
		return passed;
	}
	public String getXmlContent() {
		return xmlContent;
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object object){
		return EqualsBuilder.reflectionEquals(this,  object);
	}
	
	@Override
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
}
