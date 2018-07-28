package ms.mcs.model.testplan.ingester;

public class FileIngesterContext {
	
	private String sourceDirectory;
	
	private String hadoopCoreSiteXml;
	
	private String hadoopHdfsSiteXml;
	
	private String hadoopDir;
	
	private String dateDir;
	
	public String getSourceDirectory() {
		return sourceDirectory;
	}

	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}

	public String getHadoopCoreSiteXml() {
		return hadoopCoreSiteXml;
	}

	public void setHadoopCoreSiteXml(String hadoopCoreSiteXml) {
		this.hadoopCoreSiteXml = hadoopCoreSiteXml;
	}

	public String getHadoopHdfsSiteXml() {
		return hadoopHdfsSiteXml;
	}

	public void setHadoopHdfsSiteXml(String hadoopHdfsSiteXml) {
		this.hadoopHdfsSiteXml = hadoopHdfsSiteXml;
	}

	public String getHadoopDir() {
		return hadoopDir;
	}

	public void setHadoopDir(String hadoopDir) {
		this.hadoopDir = hadoopDir;
	}

	public String getDateDir() {
		return dateDir;
	}

	public void setDateDir(String dateDir) {
		this.dateDir = dateDir;
	}

}
