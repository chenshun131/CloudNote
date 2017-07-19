package com.chenshun.component.service;

import com.chenshun.utils.web.ftp.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

/**
 * User: mew <p />
 * Time: 17/7/19 17:21  <p />
 * Version: V1.0  <p />
 * Description: 文件上传工具 <p />
 */
public class FileUploadService {

    private static Logger log = LoggerFactory.getLogger(FileUploadService.class);

    private FtpUtil ftpUtil;

    /**
     * 上传图片
     *
     * @param inputStream
     * @param pathPrefix
     *         图片存放路径前缀，如 会员的身份证：private/hyb/member
     * @return
     * @throws Exception
     */
    public String saveImageToFtp(InputStream inputStream, String pathPrefix) {
        String middleDir = "";
        UUID fileName = UUID.randomUUID();
        try {
            Calendar c = Calendar.getInstance();// 获得系统当前日期
            int m = c.get(Calendar.MONTH) + 1;
            int d = c.get(Calendar.DATE);
            String month = ((m < 10) ? "0" : "") + m;
            String day = ((d < 10) ? "0" : "") + d;
            middleDir = pathPrefix + "/" + c.get(Calendar.YEAR) + "/" + month + "/" + day + "/";
            boolean succeed = ftpUtil.uploadFile(fileName + ".jpg", inputStream, middleDir);
            if (!succeed) {
                log.error("FTP upload failure!");
                throw new RuntimeException("FTP upload failure!");
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return middleDir + fileName + ".jpg";
    }

    public FtpUtil getFtpUtil() {
        return ftpUtil;
    }

    public void setFtpUtil(FtpUtil ftpUtil) {
        this.ftpUtil = ftpUtil;
    }

}
