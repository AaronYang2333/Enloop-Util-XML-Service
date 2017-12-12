package com.enjoyor.bigdata.EnloopUtilXMLService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xml.sax.SAXParseException;

import java.util.List;
import java.util.Map;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/12/5 15:47
 * @email aaron19940628@gmail.com
 * @date 2017/12/5 15:47.
 * @description
 */
@Data
@AllArgsConstructor
public class ValidateResult {

    private Integer lineNumber;

    private String errorMsg;

    private String errorDetail;

    public static ValidateResult ok(String message){
        return new ValidateResult(null,message,null);
    }

    public static ValidateResult ok(){
        return new ValidateResult(null,"验证通过！",null);
    }

    public static ValidateResult build(String errorMsg){
        return new ValidateResult(0,"验证不通过！",errorMsg);
    }

    public static ValidateResult build(SAXParseException exception){
        return new ValidateResult(exception.getLineNumber(),"验证不通过！",exception.getLocalizedMessage());
    }

}
