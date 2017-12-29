package com.enjoyor.bigdata.EnloopUtilXMLService.utils.common;

import com.enjoyor.bigdata.EnloopUtilXMLService.exception.IORuntimeException;
import com.enjoyor.bigdata.EnloopUtilXMLService.utils.xml.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.enjoyor.bigdata.EnloopUtilXMLService.utils.common.FileUtil.genFileName;

/**
 * @author Aaron Yang (yb)
 * @version 1.0
 * @modified Aaron 2017/10/24 10:03
 * @email aaron19940628@gmail.com
 * @date 2017/10/24 10:03.
 * @description
 */
public class CmdUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmdUtil.class);

    public static String XSD_GEN_PATH = "XSD_GEN_PATH";

    private static final String EXTERNAL_JAR_PATH = "extjar/";

    private static final String XSD_SAVE_PATH = "xsd/";
    

    /**
     * 使用trang命令生成了 XSD文件
     *
     * @param xmlFileFullPath
     * @return 返回执行命令的结果和生成的XSD文件的文件名
     */
    public static Map<String, String> trang(String xmlFileFullPath) {
        HashMap<String, String> result = new HashMap<>();
        String localWorkEnv = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
        //命令执行需要的工作空间
        File basic = new File(localWorkEnv + EXTERNAL_JAR_PATH);
        String xsdFileName = genFileName(FileType.XSD);
        String fileSavePath = localWorkEnv + XSD_SAVE_PATH;
        String xsdFileFullPath = fileSavePath + xsdFileName;
        result.put(XSD_GEN_PATH, xsdFileFullPath.substring(1));
        //创建XSD文件
        new File(fileSavePath, xsdFileName);
        String command = "java -jar trang.jar " + xmlFileFullPath + " " + xsdFileFullPath;

        BufferedReader br = null;
        try {
            Process process = Runtime.getRuntime().exec(command, null, basic);
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            LOGGER.info("execute trang.jar result : " + sb.toString());
        } catch (Exception e) {
            throw new IORuntimeException(Process.class, "执行trang命令时发生错误！Exception : " + e.getLocalizedMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    throw new IORuntimeException(StringBuilder.class, "关闭流操作时发生错误！Exception : " + e.getLocalizedMessage());
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        trang("G:/Git_WorkSpace/Enloop-Util-XML-Service/target/classes/xml/TEMP_FILE_1514525696855.xml");
    }

}
