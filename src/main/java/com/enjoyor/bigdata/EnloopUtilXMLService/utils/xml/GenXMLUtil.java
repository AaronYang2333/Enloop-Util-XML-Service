package com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml;

import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.HttpClientUtil;
import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.xerces.xs.XSModel;
import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/27 14:59
 * @email aaron19940628@gmail.com
 * @date 2017/11/27 14:59.
 * @description
 */
public class GenXMLUtil {

    private static final Logger LOOGER = LoggerFactory.getLogger(GenXMLUtil.class);

    private static final String XSD_SAVE_PATH = "xsd/";

    /**
     * 在本地使用XSModel进行转换
     * @param xsdFile
     * @return
     * @throws Exception
     */
    public static String genXMLInLocal(MultipartFile xsdFile) throws Exception {
        Map<String, Object> saveFileResult = FileUtil.saveFile(XSD_SAVE_PATH, String.valueOf(FileUtil.readFile(xsdFile)), FileType.XSD);
        String fileFullPath = saveFileResult.get(FileType.XSD.getFileSavePath()).toString();
        File xsdIOFile = new File(fileFullPath);
        String absolutePath = xsdIOFile.getAbsolutePath();

        List<QName> globalElements = getGlobalElements(xsdIOFile);
        if (globalElements.isEmpty()) {
            throw new RuntimeException("XSD File's Content is empty");
        }
        QName rootElement = globalElements.iterator().next();
        XSModel xsModel = new XSParser().parse(absolutePath);
        XSInstance xsInstance = new XSInstance();
        xsInstance.generateAllChoices = Boolean.TRUE;
        xsInstance.generateOptionalElements = Boolean.TRUE;
        xsInstance.generateOptionalAttributes = Boolean.TRUE;
        xsInstance.generateFixedAttributes = Boolean.TRUE;
        xsInstance.generateDefaultAttributes = Boolean.TRUE;

        StringWriter outWriter = new StringWriter();
        XMLDocument sampleXml = new XMLDocument(new StreamResult(outWriter), true, 4, "UTF-8");
        xsInstance.generate(xsModel, rootElement, sampleXml);

        String xmlString = outWriter.getBuffer().toString();
        return XmlObject.Factory.parse(xmlString).toString();
    }

    /**
     * 远程访问接口，进行 XSD-> XML 的转换
     * @param xsdDataFromFile
     * @return
     */
    public static String genXMLByHTTP(StringBuffer xsdDataFromFile) {
        String url = "http://xsd2xml.com/GetXmlData";
        Map<String, String> params = new HashMap<String, String>();
        params.put("XsdData", xsdDataFromFile.toString());
        params.put("Options.ChoiceOption", "GenerateFirst");
        params.put("Options.OccurrenceOption", "AtLeastOne");
        params.put("Options.CodeAnyUri", "https://github.com/AaronYang2333/Enloop-Util-XML-Service");
        params.put("Options.CodeBase64Binary", "AAAAZg==");
        params.put("Options.CodeBoolean", "true");
        params.put("Options.CodeByte", "1");
        params.put("Options.CodeDate", "2017-10-13");
        params.put("Options.CodeDateTime", "2017-12-13T12:12:12");
        params.put("Options.CodeDecimal", "123.45");
        params.put("Options.CodeDouble", "3.1415926535");
        params.put("Options.CodeDuration", "P5Y2M10D");
        params.put("Options.CodeFloat", "3.14159");
        params.put("Options.CodeGDay", "23");
        params.put("Options.CodeGMonth", "5");
        params.put("Options.CodeGMonthDay", "05-13");
        params.put("Options.CodeGYear", "1999");
        params.put("Options.CodeGYearMonth", "1999-05");
        params.put("Options.CodeHexBinary", "0FB7");
        params.put("Options.CodeInt", "123");
        params.put("Options.CodeInteger", "1234");
        params.put("Options.CodeLong", "12345");
        params.put("Options.CodeNegativeInteger", "-311");
        params.put("Options.CodeNonNegativeInteger", "33");
        params.put("Options.CodeNonPositiveInteger", "-21");
        params.put("Options.CodeNormalizedString", "str111");
        params.put("Options.CodePositiveInteger", "745");
        params.put("Options.CodeShort", "12");
        params.put("Options.CodeString", "str1234");
        params.put("Options.CodeTime", "12:12:12");
        params.put("Options.CodeUnsignedByte", "5");
        params.put("Options.CodeUnsignedInt", "766");
        params.put("Options.CodeUnsignedLong", "432");
        params.put("Options.CodeUnsignedShort", "44");
        String response = HttpClientUtil.doPost(url, params);
        return StringEscapeUtils.unescapeJava(response.substring(7, response.length() - 2));
    }

    /**
     * 获取全部的Qname
     * @param xsdFile
     * @return
     * @throws Exception
     */
    private static List<QName> getGlobalElements(File xsdFile) throws Exception {
        SchemaTypeSystem sts = XmlBeans.compileXsd(
                new XmlObject[]{
                        XmlObject.Factory.parse(xsdFile)
                },
                XmlBeans.getBuiltinTypeSystem(), null);

        List<QName> qnames = new ArrayList();
        for (SchemaGlobalElement el : sts.globalElements()) {
            qnames.add(el.getName());
        }

        return qnames;
    }
}
