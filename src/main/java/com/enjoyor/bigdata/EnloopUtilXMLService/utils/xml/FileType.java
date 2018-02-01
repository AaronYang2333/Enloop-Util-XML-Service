package com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 12/29/2017 11:25 AM
 * @email aaron19940628@gmail.com
 * @date 12/29/2017 11:25 AM.
 * @description
 */
public enum FileType {

    XML(".xml", "XML_GEN_PATH"),
    XSD(".xsd", "XSD_GEN_PATH"),
    XSL(".xslt", "XSL_GEN_PATH");

    private String suffix;
    private String fileSavePath;

    FileType(String suffix, String fileSavePath) {
        this.suffix = suffix;
        this.fileSavePath = fileSavePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }
}
