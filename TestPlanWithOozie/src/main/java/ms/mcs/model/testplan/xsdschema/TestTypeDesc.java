//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.16 at 10:12:27 PM EDT 
//


package ms.mcs.model.testplan.xsdschema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestTypeDesc.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TestTypeDesc"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="desc1"/&gt;
 *     &lt;enumeration value="desc2"/&gt;
 *     &lt;enumeration value="desc3"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TestTypeDesc")
@XmlEnum
public enum TestTypeDesc {

    @XmlEnumValue("desc1")
    DESC_1("desc1"),
    @XmlEnumValue("desc2")
    DESC_2("desc2"),
    @XmlEnumValue("desc3")
    DESC_3("desc3");
    private final String value;

    TestTypeDesc(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TestTypeDesc fromValue(String v) {
        for (TestTypeDesc c: TestTypeDesc.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
