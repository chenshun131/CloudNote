package com.chenshun.utils.excel;

import com.chenshun.utils.ServletUtil;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/6/19 10:13  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class JxlsTemplate {

    private static final Logger logger = LoggerFactory.getLogger(JxlsTemplate.class);

    //-- 默认常量 --//
    private static final String DEFAULT_RESULT_MAP_NAME = "model";// 默认返回集合变量名

    /**
     * 根据自定义模板,输出模板流文件
     */
    public static void report(HttpServletRequest request, HttpServletResponse response, String templateFilePath,
                              Object result, String resultName) throws FileNotFoundException {
        ServletUtil.initResponseHeader(response, ServletUtil.EXCEL_TYPE);
        InputStream is = getFileInputStream(request, templateFilePath);
        transfrom(response, is, resultName, result);
    }

    public static void report(HttpServletRequest request, HttpServletResponse response, String templateFilePath,
                              Object result) throws FileNotFoundException {
        report(request, response, templateFilePath, result, DEFAULT_RESULT_MAP_NAME);
    }

    /**
     * 利用XLSTransformer进行读写流,完成excel文件输出
     */
    private static void transfrom(HttpServletResponse response, InputStream is, String resultName, Object result) {
        /* 流操作 */
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Workbook workbook;
        try {
            XLSTransformer transformer = new XLSTransformer();
            Map<String, Object> beanMap = new HashMap<>();
            beanMap.put(resultName, result);
            workbook = transformer.transformXLS(is, beanMap);
            workbook.write(os);
            InputStream inputStream = new ByteArrayInputStream(os.toByteArray(), 0, os.toByteArray().length);
            // 读写操作
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.info("Write data into HSSFWorkbook occur exception,caused by:{}", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                    os.close();
                } catch (IOException e) {
                    logger.info("The file can't close fileStream.,caused by:{}", e);
                }
            }
        }
    }

    /**
     * 根据模板路径得到EXCEL模板文件输入流
     */
    private static InputStream getFileInputStream(HttpServletRequest request, String templatePath) throws
            FileNotFoundException {
        // 获取web容器上下文中EXCEL模板文件路径
        String realPath = ServletUtil.getRealPath(request);

        StringBuilder filePath = new StringBuilder();
        filePath.append(realPath);
        filePath.append(templatePath);

        logger.debug("The excel template path is :{}", realPath);
        return new FileInputStream(filePath.toString());
    }

}
