package com.chenshun.component.service;

import com.chenshun.utils.web.ftp.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/17 14:02  <p />
 * Version: V1.0  <p />
 * Description: 读取文件流 <p />
 */
public class FileLoadService {

    private static Logger log = LoggerFactory.getLogger(FileLoadService.class);

    private static Map<String, String> mime = new HashMap<>();

    static {
        mime.put("png", "image/png");
        mime.put("pdf", "application/pdf");
        mime.put("jpg", "image/jpeg");
    }

    private FtpUtil ftpUtil;

    public boolean read(String fileName, String policyCode, HttpServletResponse response) {
        log.info("read file :" + fileName);
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        InputStream inputStream = ftpUtil.loadFile(fileName);

        byte[] arr = new byte[1024];
        int k = 0;
        ServletOutputStream out = null;
        response.setHeader("Content-Type", mime.get(fileType));
//        response.setHeader("Content-Disposition", "attachment; filename=\" "+ policyCode +".pdf\"");
//        response.setHeader("Content-Disposition", "inline; filename=\" "+ policyCode +"."+fileType+"\"");

        try {
            out = response.getOutputStream();
            while ((k = inputStream.read(arr)) != -1) {
                out.write(arr, 0, k);
            }
        } catch (IOException e) {
            log.error("----- > load file error : " + e);
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                log.error("----- > close file error : " + e);
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("----- > close file error : " + e);
            }
            ftpUtil.closeFTPClient();
        }

        //写入流失败这种情况应该返回true，防止出现再次转发response
//        即response既回写数据，又进行redirect
        return true;
    }

    public FtpUtil getFtpUtil() {
        return ftpUtil;
    }

    public void setFtpUtil(FtpUtil ftpUtil) {
        this.ftpUtil = ftpUtil;
    }

}
