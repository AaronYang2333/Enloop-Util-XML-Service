package com.enjoyor.bigdata.EnloopUtilXMLService.controller.download;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.validator.ParamAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 1/18/2018 9:09 AM
 * @email aaron19940628@gmail.com
 * @date 1/18/2018 9:09 AM.
 * @description
 */
@Controller
@Api(tags = "Gen && Download", description = "(Download Service)")
@RequestMapping("/download")
public class GenFileAndDownloadAPI {

    private static final Integer BUFFER_SIZE = 1024;

    @ApiOperation(value = "下载接口", notes = "生成文件并下载")
    @RequestMapping(method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rowData", value = "需要下载的文件的内容", paramType = "query"),
            @ApiImplicitParam(name = "suffix", value = "需要下载的文件的后缀", paramType = "query")
    })
    public void downloadFileItem(HttpServletRequest request, HttpServletResponse response, String rowData, String suffix) {
        //包装内容，并生成一个文件
        ParamAssert.isBlank(rowData, "无内容，请检查！");
        try {
            File generatedFile = generateFile(rowData.substring(8), suffix);
            //获取要下载文件的文件流
            InputStream fis = new FileInputStream(generatedFile);
            Long fileSize = generatedFile.length();
            request.setCharacterEncoding("UTF-8");
            // 设置response的Header
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(generatedFile.getName().getBytes(), "UTF-8"));
            response.addHeader("Content-Length", fileSize.toString());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream;charset=utf-8");

            // 以流的形式下载文件。
            byte[] buffer = new byte[BUFFER_SIZE];
            int length = 0;
            while ((length = fis.read(buffer)) != -1) {
                toClient.write(buffer, 0, length);
            }
            toClient.flush();
            fis.close();
        } catch (IOException e) {
            throw new IORuntimeException(OutputStream.class, "下载文件时发生错误");
        } catch (IndexOutOfBoundsException e) {
            throw new IORuntimeException(String.class, "文件内容似乎不正确，请检查！");
        }
    }

    private File generateFile(String rowData, String suffix) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String fileSavePath = loader.getResource(".").getPath() + "/temp";
        String xmlFileName = FileUtil.genFileName(suffix);
        return FileUtil.sinkContentIntoFile(new File(fileSavePath, xmlFileName), rowData);
    }
}
