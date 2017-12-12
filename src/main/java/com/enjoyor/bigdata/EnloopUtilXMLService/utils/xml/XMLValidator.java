package com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.ValidateResult;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/12/5 15:20
 * @email aaron19940628@gmail.com
 * @date 2017/12/5 15:20.
 * @description
 */
public class XMLValidator {

    private static final Logger LOGGER = Logger.getLogger(XMLValidator.class);

    public static ValidateResult validateWithSingleSchema(File xsd, File xml) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsd);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return ValidateResult.ok();
        } catch (Exception e) {
            if (e instanceof SAXParseException) {
                return ValidateResult.build((SAXParseException) e);
            } else {
                return ValidateResult.build(e.toString());
            }
        }
    }

    public static ValidateResult validateWithSingleSchema(MultipartFile xsdFile, MultipartFile xmlFile) {
        File xsd = FileUtil.convert2IOFile(xsdFile);
        File xml = FileUtil.convert2IOFile(xmlFile);
        return validateWithSingleSchema(xsd, xml);
    }

    public static ValidateResult validateWithMultiSchemas(InputStream xml, List<File> schemas) {
        try {
            Schema schema = createSchema(schemas);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return ValidateResult.ok();
        } catch (Exception e) {
            return ValidateResult.build(e.getMessage());
        }
    }

    /**
     * Create Schema object from the schemas file.
     *
     * @param schemas
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private static Schema createSchema(List<File> schemas) throws ParserConfigurationException, SAXException, IOException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        SchemaResourceResolver resourceResolver = new SchemaResourceResolver();
        sf.setResourceResolver(resourceResolver);

        Source[] sources = new Source[schemas.size()];

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setValidating(false);
        docFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        for (int i = 0; i < schemas.size(); i++) {
            org.w3c.dom.Document doc = docBuilder.parse(schemas.get(i));
            DOMSource stream = new DOMSource(doc, schemas.get(i).getAbsolutePath());
            sources[i] = stream;
        }

        return sf.newSchema(sources);
    }
}
