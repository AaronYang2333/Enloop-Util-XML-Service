package com.enjoyor.bigdata.EnloopUtilXMLService.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/25 15:36
 * @email aaron19940628@gmail.com
 * @date 2017/11/25 15:36.
 * @description
 */
@Getter
@Setter
public class POJO {

    private String pojo_column1;

    private Integer pojo_column2;

    private List<?> pojo_column3;
}
