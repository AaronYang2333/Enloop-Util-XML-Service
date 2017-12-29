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

    XML(".xml"),
    XSD(".xsd"),
    XSLT(".xslt");

    private String suffix;

    FileType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
