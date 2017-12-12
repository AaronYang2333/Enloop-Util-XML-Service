package com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml;

import lombok.Data;
import org.apache.log4j.Logger;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/12/5 15:31
 * @email aaron19940628@gmail.com
 * @date 2017/12/5 15:31.
 * @description resolve multi xsd file
 */
public class SchemaResourceResolver implements LSResourceResolver {

    private static final Logger LOGGER = Logger.getLogger(SchemaResourceResolver.class);

    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        LOGGER.info("/n>> Resolving " + "/n"
                + "TYPE: " + type + "/n"
                + "NAMESPACE_URI: " + namespaceURI + "/n"
                + "PUBLIC_ID: " + publicId + "/n"
                + "SYSTEM_ID: " + systemId + "/n"
                + "BASE_URI: " + baseURI + "/n");

        String schemaLocation = baseURI.substring(0, baseURI.lastIndexOf("/") + 1);
        if (systemId.indexOf("http://") < 0) {
            systemId = schemaLocation + systemId;
        }
        LSInput lsInput = new LSInputImpl();
        URI uri = null;
        try {
            uri = new URI(systemId);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File file = new File(uri);
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lsInput.setSystemId(systemId);
        lsInput.setByteStream(is);

        return lsInput;
    }


}

@Data
class LSInputImpl implements LSInput {

    private String publicId;
    private String systemId;
    private String baseURI;
    private InputStream byteStream;
    private Reader charStream;
    private String stringData;
    private String encoding;
    private boolean certifiedText;

    @Override
    public Reader getCharacterStream() {
        return null;
    }

    @Override
    public void setCharacterStream(Reader characterStream) {

    }

    @Override
    public InputStream getByteStream() {
        return null;
    }

    @Override
    public void setByteStream(InputStream byteStream) {

    }

    @Override
    public String getStringData() {
        return null;
    }

    @Override
    public void setStringData(String stringData) {

    }

    @Override
    public String getSystemId() {
        return null;
    }

    @Override
    public void setSystemId(String systemId) {

    }

    @Override
    public String getPublicId() {
        return null;
    }

    @Override
    public void setPublicId(String publicId) {

    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public void setBaseURI(String baseURI) {

    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public void setEncoding(String encoding) {

    }

    @Override
    public boolean getCertifiedText() {
        return false;
    }

    @Override
    public void setCertifiedText(boolean certifiedText) {

    }
}
