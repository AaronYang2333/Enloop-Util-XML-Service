package com.enjoyor.bigdata.EnloopUtilXMLService.utils.common;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    public static String XML_GEN_PATH = "XML_GEN_PATH";

    private static final Integer BUFFER_SIZE = 1024;

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
        byte[] buffer = new byte[BUFFER_SIZE];
        int hasRead = 0;
        try {
            xsdInputStream = getInputStream(file);
            while ((hasRead = xsdInputStream.read(buffer)) > 0) {
                String temp = new String(buffer, 0, hasRead);
                xsdData.append(temp);
            }
            return xsdData;
        } catch (IOException e) {
            throw new IORuntimeException(InputStream.class, "从输入流中读取内容时发生错误！Exception : " + e.getLocalizedMessage());
        } finally {
            closeAllStream(xsdInputStream);
        }
    }


    /**
     * 读取本地File类型文件的内容
     *
     * @param fileFullPath 本地文件存储的全路径 e.g. G:/Git_WorkSpace/Enloop-Util-XML-Service/target/classes/xml/TEMP_FILE_1514525696855.xml
     * @return 文件的内容
     */
    public static StringBuffer readFile(String fileFullPath) {
        try {
            StringBuffer stringBuffer = new StringBuffer();

            FileInputStream fileInputStream = new FileInputStream(new File(fileFullPath));
            byte[] buffer = new byte[BUFFER_SIZE];
            int hasRead = 0;
            while ((hasRead = fileInputStream.read(buffer)) > 0) {
                stringBuffer.append(new String(buffer, 0, hasRead));
            }
            return stringBuffer;
        } catch (FileNotFoundException e) {
            throw new IORuntimeException(File.class, "读取临时文件" + fileFullPath + "时发生错误！");
        } catch (IOException e) {
            throw new IORuntimeException(InputStream.class, "从输入流中读取内容时发生错误！Exception : " + e.getLocalizedMessage());
        }
    }

    /**
     * org.springframework.web.multipart.MultipartFile -> java.io.File
     *
     * @param file (org.springframework.web.multipart.MultipartFile)
     * @return file (java.io.File)
     */
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
     * 在指定目录中生成一个文件，并将内容写入
     *
     * @param dir
     * @param fileContent
     * @return
     */
    public static Map<String, Object> saveFile(String dir, String fileContent) {
        Map<String, Object> result = new HashMap<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String fileSavePath = loader.getResource(".").getPath() + dir;
        String xmlFileName = genFileName(FileType.XML);
        result.put(XML_GEN_PATH, (fileSavePath + xmlFileName).substring(1));
        File xmlFile = new File(fileSavePath, xmlFileName);
        try {
            FileWriter xmlfileWriter = new FileWriter(xmlFile);
            xmlfileWriter.write(fileContent);
            xmlfileWriter.flush();
            xmlfileWriter.close();
        } catch (FileNotFoundException e) {
            throw new IORuntimeException(File.class, "读取临时文件" + xmlFileName + "时发生错误！");
        } catch (IOException e) {
            throw new IORuntimeException(FileWriter.class, "写入临时文件" + xmlFileName + "时发生错误！");
        }
        return result;
    }


    /**
     * 关闭连接&&释放资源
     *
     * @param streams
     */
    public static void closeAllStream(Closeable... streams) {
        for (Closeable stream : streams) {
            try {
                stream.close();
            } catch (IOException e) {
                throw new IORuntimeException(InputStream.class, "执行关闭流操作时发生错误！");
            }
        }
    }

    public static String genFileName(FileType fileType) {
        return "TEMP_FILE_" + new Date().getTime() + fileType.getSuffix();
    }

}
