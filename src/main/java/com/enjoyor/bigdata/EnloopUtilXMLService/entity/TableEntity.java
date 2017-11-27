package com.enjoyor.bigdata.EnloopUtilXMLService.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 11:41
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 11:41.
 * @description
 */
@Data
@ApiModel(description = "二维表实体对象")
public class TableEntity {

    private Short column1;

    private Integer column2;

    private Float column3;

    private Long column4;

    private Double column5;

    private Date column6;

    private POJO column7;

    private String column8;

    private List column9;

    private Map column10;
}
