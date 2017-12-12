package com.enjoyor.bigdata.EnloopUtilXMLService.utils.common;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Random;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/11/27 14:55
 * @email aaron19940628@gmail.com
 * @date 2017/11/27 14:55.
 * @description
 */
public class FileUtil {

    /**
     * 获得MultipartFile类型文件的输入流
     *
     * @param file
     * @return
     */
    public static InputStream getInputStream(MultipartFile file) {
        InputStream inputStream = null;
        //获取MultipartFile对象的输入流
        try {
            inputStream = new BufferedInputStream(file.getInputStream());
        } catch (IOException e) {
            throw new IORuntimeException(InputStream.class, "获取xsdFile输入流对象时出现错误！");
        }
        return inputStream;
    }

    /**
     * 读取MultipartFile类型文件的内容
     *
     * @param file
     * @return
     */
    public static StringBuffer readFile(MultipartFile file) {
        InputStream xsdInputStream = null;
        StringBuffer xsdData = new StringBuffer();
        byte[] buffer = new byte[1024];
        int hasRead = 0;
        try {
            xsdInputStream = getInputStream(file);
            while ((hasRead = xsdInputStream.read(buffer)) > 0) {
                String temp = new String(buffer, 0, hasRead);
                xsdData.append(temp);
            }
            return xsdData;
        } catch (IOException e) {
            throw new IORuntimeException(InputStream.class, "从输入流中读取内容时发生错误！");
        } finally {
            closeAllStream(xsdInputStream);
        }
    }

    public static File convert2IOFile(MultipartFile file) {
        int suffix = new Random().nextInt(99);
        try {
            File tempFile = File.createTempFile("tempFile" + suffix, ".xml");
            file.transferTo(tempFile);
            tempFile.deleteOnExit();
            return tempFile;
        } catch (IOException e) {
            throw new IORuntimeException(MultipartFile.class, "MultipartFile.transferTo(File) 转换发生错误！");
        }
    }


    /**
     * 关闭连接&&释放资源
     *
     * @param streams
     */
    public static void closeAllStream(Closeable... streams) {
        for (int i = 0; i < streams.length; i++) {
            try {
                streams[i].close();
            } catch (IOException e) {
                throw new IORuntimeException(InputStream.class, "执行关闭流操作时发生错误！");
            }
        }
    }
}
