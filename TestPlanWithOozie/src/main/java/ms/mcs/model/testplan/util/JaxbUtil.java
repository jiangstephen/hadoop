package ms.mcs.model.testplan.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> clazz,  String xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller m = context.createUnmarshaller();
			return (T)m.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to unmarshal the " + xml +" to class " + clazz, e);
		} 
	}
	
	public static <T> String serialize(Class<T> clazz, T object) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Marshaller m = context.createMarshaller();
			StringWriter sw = new StringWriter();
			m.marshal(object, sw);
			return sw.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to marshal the "+object, e);
		} 
	}


}
