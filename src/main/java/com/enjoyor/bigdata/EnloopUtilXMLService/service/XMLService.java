package com.enjoyor.bigdata.EnloopUtilXMLService.service;

import com.enjoyor.bigdata.EnloopUtilXMLService.entity.TableEntity;
import com.enjoyor.bigdata.EnloopUtilXMLService.exception.Dom4jException;

import java.io.IOException;
import java.util.List;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 11:41
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 11:41.
 * @description
 */
public interface XMLService {

    /**
     * 将列表内容转换成XML
     *
     * @param entityList
     * @return
     */
    String table2Xml(List<?> entityList);

}
