package com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml;

import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.HttpClientUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator.ParamAssert;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.HashMap;
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

    private static XmlSchemaCollection coll = new XmlSchemaCollection();
    private static XmlGenOptions options = new XmlGenOptions();
    private static SchemaTypeXmlGenerator generator = new SchemaTypeXmlGenerator(coll, options);
    private static XmlSchema schema;

    public static String genXMLInLocal(MultipartFile xsdFile, String localPart) {
        InputStream xsdInputStream = FileUtil.getInputStream(xsdFile);
        ParamAssert.isNull(xsdInputStream, "获取的文件的输入流为空！");
        LOOGER.info("成功读取到文件数据，开始进行转换 XSD->XML");
        coll.setBaseUri("https://github.com/AaronYang2333/Enloop-Util-XML-Service");
        StreamSource source = new StreamSource(xsdInputStream);
        schema = coll.read(source);

        //是否添加注释
        options.setGenCommentsForParticles(false);
        options.setGenChoiceOptionsAsComments(false);
        options.setMaxRecursiveDepth(1);
        options.setMaxRepeatingElements(2);
        options.setDefVals(DefaultValues.DEFAULT);

        QName elName = new QName("", localPart);

        return generator.generateXml(elName, true);
    }

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
}
