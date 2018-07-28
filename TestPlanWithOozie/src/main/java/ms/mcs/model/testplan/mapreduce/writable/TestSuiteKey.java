package ms.mcs.model.testplan.mapreduce.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.hadoop.io.WritableComparable;

public class TestSuiteKey implements WritableComparable<TestSuiteKey> {

	private String modelName;
	
	private Long executionDate;
	
	private String pricingLib;
	
	public TestSuiteKey(){
		
	}
	
	public TestSuiteKey(String modelName, Long executionDate, String pricingLib){
		this.modelName = modelName;
		this.executionDate = executionDate;
		this.pricingLib = pricingLib;
	}
	
	public void readFields(DataInput dataInput) throws IOException {
		modelName = dataInput.readUTF();
		executionDate = dataInput.readLong();
		pricingLib = dataInput.readUTF();
	}

	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeUTF(modelName);
		dataOutput.writeLong(executionDate);
		dataOutput.writeUTF(pricingLib);
	}

	public int compareTo(TestSuiteKey testCaseKey) {
		return CompareToBuilder.reflectionCompare(this, testCaseKey);
	}

	public String getModelName() {
		return modelName;
	}

	public Long getExecutionDate() {
		return executionDate;
	}

	public String getPricingLib() {
		return pricingLib;
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

