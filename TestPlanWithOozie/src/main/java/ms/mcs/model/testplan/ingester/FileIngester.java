package ms.mcs.model.testplan.ingester;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIngester {
	
	private static final String FILE_NAME_LAST_VISITED_DATE = "last_visited_timestamp.dat";
	private static final String DATE_FORMAT = "yyyy-MM-dd HHmmssSSS";
	private static final Logger LOG = LoggerFactory.getLogger(FileIngester.class);
	
	Collection<File> findNewFiles(File directory, Date date){
		Validate.notNull(directory, "directory not be null");
		Validate.isTrue(directory.isDirectory(), directory.getAbsolutePath()+" must be a directory");
		AndFileFilter andFileFilter = new AndFileFilter();
		andFileFilter.addFileFilter(FileFilterUtils.ageFileFilter(date, false));
		andFileFilter.addFileFilter(FileFilterUtils.suffixFileFilter(".xml"));
		andFileFilter.addFileFilter(FileFilterUtils.prefixFileFilter("testsuites"));
		return FileUtils.listFiles(directory, andFileFilter, TrueFileFilter.INSTANCE);
	}
	
	
	public void copyToHDFS(FileIngesterContext fileIngesterContext) throws IOException, ParseException {
		
		File dateDir = new File(fileIngesterContext.getDateDir());
		if(!dateDir.isDirectory()){
			throw new IllegalArgumentException("the date dir " + fileIngesterContext.getDateDir()+" is not a directory");
		}
		Configuration conf = new Configuration();
		conf.addResource(new Path(fileIngesterContext.getHadoopCoreSiteXml()));
		conf.addResource(new Path(fileIngesterContext.getHadoopHdfsSiteXml()));
		FileSystem fs = FileSystem.get(conf);
		File dateFile = new File(dateDir,  FILE_NAME_LAST_VISITED_DATE);
		Date date = null;
		if(dateFile.exists()){
			date = new SimpleDateFormat(DATE_FORMAT).parse(FileUtils.readFileToString(dateFile));
		} else {
			LOG.info("Date file doesn't exists,  set up the date to be -10,000 days ago");
			date = DateUtils.addDays(new Date(), -10000);
		}
		Date newDate = new Date();
		Path hadoopOutputPath = new Path(fileIngesterContext.getHadoopDir());
		fs.delete(hadoopOutputPath, true);
		fs.mkdirs(hadoopOutputPath);
		LOG.info("delete the hadoop input directory {}",  hadoopOutputPath);
		LOG.info("The cut off date is {}", date);
		for(File file : findNewFiles(new File(fileIngesterContext.getSourceDirectory()), date)){
			fs.copyFromLocalFile(new Path(file.getAbsolutePath()), hadoopOutputPath);
			LOG.info("copy file from local [{}] into hdfs [{}]",  file.getAbsolutePath(), hadoopOutputPath);
		}
		FileUtils.writeStringToFile(dateFile, new SimpleDateFormat(DATE_FORMAT).format(newDate));
	}
	
	public static void main(String[] args) throws IOException, ParseException{
		Validate.notNull(args, "argument should not be null");
		Validate.isTrue(args.length == 5, "5 parameters needed");
		String hadoopCoreSiteXml = args[0];
		Validate.isTrue(new File(hadoopCoreSiteXml).isFile(), hadoopCoreSiteXml+" is not file");
		String hadoopHdfsSiteXml = args[1];
		Validate.isTrue(new File(hadoopHdfsSiteXml).isFile(), hadoopHdfsSiteXml+" is not file");
		String hadoopDir = args[2];
		String sourceDirectory = args[3];
		String dateDir = args[4];
		
		Validate.isTrue(new File(sourceDirectory).isDirectory(), sourceDirectory+" is not directory");
		FileIngesterContext fileIngesterContext = new FileIngesterContext();
		fileIngesterContext.setDateDir(dateDir);
		fileIngesterContext.setHadoopCoreSiteXml(hadoopCoreSiteXml);
		fileIngesterContext.setHadoopDir(hadoopDir);
		fileIngesterContext.setHadoopHdfsSiteXml(hadoopHdfsSiteXml);
		fileIngesterContext.setSourceDirectory(sourceDirectory);
		new FileIngester().copyToHDFS(fileIngesterContext);
	}
	
}
