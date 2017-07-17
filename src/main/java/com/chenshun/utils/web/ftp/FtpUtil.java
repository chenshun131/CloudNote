package com.chenshun.utils.web.ftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

/**
 * User: mew <p />
 * Time: 17/7/11 14:55  <p />
 * Version: V1.0  <p />
 * Description: FTP Utils,支持多线程环境 <p />
 */
public final class FtpUtil {

    public static final Log ftpLog = LogFactory.getLog("FTP");

    private final ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<>();

    /** FTP 登录用户名 */
    private String userName;

    /** FTP 登录密码 */
    private String password;

    /** FTP 服务器IP地址 */
    private String ip;

    /** FTP 端口 */
    private int port;

    /** 上传路径 */
    private String uploadUrl = "";

    private FtpUtil() {
        super();
        // 做调试用的日志
        ftpLog.info("FtpUtil instance created:" + this);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    /**
     * 获取FTPClient对象
     *
     * @return
     */
    private FTPClient getFtpClient(String... middleDir) {
        if (this.ftpClientThreadLocal.get() != null && this.ftpClientThreadLocal.get().isConnected()) {
            return this.ftpClientThreadLocal.get();
        } else {
            // 为NULL或未连接
            try {
                FTPClient ftpClient = null;
                connect(ftpClient); //连接

                if (this.ftpClientThreadLocal.get() == null) {
                    throw new RuntimeException("ftp server login failed or refused connection");
                }

                //this.ftpClient = client;

                String dir = middleDir.length > 0 ? uploadUrl + "/" + middleDir[0] : uploadUrl;
                if (!this.ftpClientThreadLocal.get().changeWorkingDirectory(dir)) {
                    if (middleDir.length > 0) {
                        String middleDirTmp = middleDir[0].endsWith("/") ? removeEnd(middleDir[0], "/") : middleDir[0];
                        String[] middleDirList = middleDirTmp.split("/");

                        String levelDir = uploadUrl;
                        for (String subDir : middleDirList) {
                            levelDir = levelDir + "/" + subDir;
                            if (!this.ftpClientThreadLocal.get().changeWorkingDirectory(levelDir)) {
                                if (this.ftpClientThreadLocal.get().makeDirectory(levelDir)) {
                                    this.ftpClientThreadLocal.get().changeWorkingDirectory(levelDir);
                                }
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                ftpLog.error("登录FTP SERVER[" + ip + "]失败,连接被拒绝", e);
            } catch (IOException e) {
                ftpLog.error("访问FTP SERVER[" + ip + "]失败,目录操作失败", e);
            } catch (Exception e) {
                ftpLog.error("未知错误", e);
            }
        }
        return this.ftpClientThreadLocal.get();
    }

    /**
     * 连接
     *
     * @param client
     * @return
     * @throws SocketException
     * @throws IOException
     */
    private FTPClient connect(FTPClient client) throws IOException {
        if (client == null) {
            client = new FTPClient();
            ftpLog.info("FTPUtils对象中的连接方法中创建ftp client对象引用成功[FtpUtil inst addr:" + this + "; ftp client inst addr:"
                    + client + "]");
        }

        client.configure(getFtpConfig());
        client.connect(this.ip, this.port);
        if (!client.login(this.userName, this.password)) {
            ftpLog.warn("登录失败,用户名或密码不匹配");
            client.logout();
            client = null;
        } else {
            // 文件类型,默认是ASCII
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.setControlEncoding("UTF-8");

            // 设置被动模式
            client.enterLocalPassiveMode();
            //client.setConnectTimeout(60000);
            client.setBufferSize(1024);

            client.setDefaultPort(port);

            client.getReplyString();

            // 响应信息
            int replyCode = client.getReplyCode();

            client.setConnectTimeout(60000);
            client.setDataTimeout(120000);

            //ftpLog.info("FTPUtils对象[" + this + "]中,当前的ftpClient对象:" + client);

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                client = null;  //释放空间
                closeFTPClient();
                ftpLog.warn("FTP SERVER拒绝连接");
                throw new SocketException("FTP server refused connection");
            }
        }
        //放入ThreadLocal
        this.ftpClientThreadLocal.set(client);
        return client;
    }

    /**
     * 上传单个文件,并重命名
     *
     * @param fileName
     *         --本地文件命
     * @param middleDir
     *         --扩展中间路径
     * @return true:上传成功;false:上传失败
     */
    public boolean uploadFile(String fileName, InputStream input, String... middleDir) {
        boolean flag = true;
        ftpLog.info("上传文件");
        FTPClient client = null;
        try {
            client = getFtpClient(middleDir);
            //client.setFileType(FTP.BINARY_FILE_TYPE);
            //client.enterLocalPassiveMode();
            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            flag = client.storeFile(fileName, input);
        } catch (Exception e) {
            flag = false;
            ftpLog.error("上传文件失败", e);
        } finally {
            closeFTPClient();
            close(input);
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param remoteFileName
     *         -- 服务器上的文件名
     * @param localFileName
     *         -- 本地文件名
     * @return true:下载成功;false:下载失败
     */
    public boolean loadFile(String remoteFileName, String localFileName) {
        boolean flag = true;
        FTPClient client = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            client = getFtpClient();
            fos = new FileOutputStream(localFileName);
            bos = new BufferedOutputStream(fos);
            flag = client.retrieveFile(remoteFileName, bos);
        } catch (Exception e) {
            flag = false;
            ftpLog.error("文件下载失败", e);
        } finally {
            close(bos);
            close(fos);
            closeFTPClient();
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param remoteFileName
     *         -- 服务器上的文件名
     * @return
     */
    public InputStream loadFile(String remoteFileName) {
        FTPClient client = null;
        try {
            client = getFtpClient();
            return client.retrieveFileStream(remoteFileName);
        } catch (Exception e) {
            ftpLog.error("获取下载文件流失败", e);
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @param middleDir
     * @return
     */
    public boolean deleteFile(String fileName, String... middleDir) {
        boolean flag = true;
        FTPClient client = null;
        try {
            client = getFtpClient(middleDir);
            flag = client.deleteFile(fileName);
            if (flag) {
                ftpLog.info("删除文件成功[" + fileName + "]");
            } else {
                ftpLog.info("删除文件失败");
            }
        } catch (Exception e) {
            flag = false;
            ftpLog.error("删除文件失败", e);
        } finally {
            closeFTPClient();
        }
        return flag;
    }

    /**
     * 删除目录
     *
     * @param pathName
     */
    public void deleteDirectory(String pathName) {
        FTPClient client = null;
        try {
            client = getFtpClient();
            File file = new File(pathName);
            if (file.isDirectory()) {
                file.listFiles();
            } else {
                deleteFile(pathName);
            }
            client.removeDirectory(pathName);
        } catch (Exception e) {
            ftpLog.error("删除目录失败", e);
        } finally {
            closeFTPClient();
        }
    }

    /**
     * 删除空目录
     *
     * @param pathName
     */
    public void deleteEmptyDirectory(String pathName) {
        FTPClient client = null;
        try {
            client = getFtpClient();
            client.removeDirectory(pathName);
        } catch (Exception e) {
            ftpLog.error("删除空目录失败", e);
        } finally {
            closeFTPClient();
        }
    }

    /**
     * 列出服务器上文件和目录
     *
     * @param regStr
     *         -- 匹配的正则表达式
     */
    public String[] listRemoteFiles(String regStr) {
        String files[] = null;
        FTPClient client = null;
        try {
            client = getFtpClient();
            files = client.listNames(regStr);
            if (files == null || files.length == 0) {
                ftpLog.info("没有任何文件");
            } else {
                for (String file : files) {
                    ftpLog.info(file);
                }
            }
        } catch (Exception e) {
            ftpLog.error("列出文件名失败", e);
        } finally {
            closeFTPClient();
        }
        return files;
    }

    /**
     * 列出FTP服务器上的所有文件和目录
     */
    public String[] listRemoteAllFiles() {
        String names[] = null;
        FTPClient client = null;
        try {
            client = getFtpClient();
            names = client.listNames();
            for (String name : names) {
                ftpLog.info(name);
            }
        } catch (Exception e) {
            ftpLog.error("列出文件和目录失败", e);
        } finally {
            closeFTPClient();
        }
        return names;
    }

    /**
     * 进入到服务器的某个目录下
     *
     * @param directory
     */
    public void changeWorkingDirectory(String directory) {
        FTPClient client = getFtpClient();
        try {
            client.changeWorkingDirectory(directory);
        } catch (Exception e) {
            ftpLog.error("进入指定目录失败", e);
        }
    }

    /**
     * 返回到上一层目录
     */
    public void changeToParentDirectory() {
        FTPClient client = getFtpClient();
        try {
            client.changeToParentDirectory();
        } catch (Exception e) {
            ftpLog.error("返回上一层目录失败", e);
        }
    }

    /**
     * 重命名文件
     *
     * @param oldFileName
     *         --原文件名
     * @param newFileName
     *         --新文件名
     */
    public void renameFile(String oldFileName, String newFileName) {
        FTPClient client = getFtpClient();
        try {
            boolean flag = client.rename(oldFileName, newFileName);
            if (flag) {
                ftpLog.info("重命名文件成功[原文件名称:" + oldFileName + ",新文件名称:" + newFileName + "]");
            } else {
                ftpLog.info("重命名文件失败");
            }
        } catch (Exception e) {
            ftpLog.error("重命名文件失败", e);
        } finally {
            closeFTPClient();
        }
    }

    /**
     * 设置FTP客户端的配置 -- 一般可以不设置
     *
     * @return ftpConfig
     */
    private FTPClientConfig getFtpConfig() {
        FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
        return ftpConfig;
    }

    /**
     * 在服务器上创建一个文件夹
     *
     * @param dir
     *         文件夹名称,不能含有特殊字符.如 \ 、/ 、: 、* 、?、 "、 <、>...
     */
    public boolean makeDirectory(String dir) {
        FTPClient client = getFtpClient();
        boolean flag = true;
        try {
            flag = client.makeDirectory(dir);
            if (flag) {
                ftpLog.info("创建文件夹[" + dir + "]成功");
            } else {
                ftpLog.info("创建文件夹失败");
            }
        } catch (Exception e) {
            flag = false;
            ftpLog.error("创建文件夹失败", e);
        } finally {
            closeFTPClient();
        }
        return flag;
    }

    /**
     * CLOSE FTP CLIENT
     */
    public void closeFTPClient() {
        try {
            FTPClient ftpClient = this.ftpClientThreadLocal.get();
            if (ftpClient == null) {
                return;
            }
            ftpClient.logout(); // 登出
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
                this.ftpClientThreadLocal.set(null); // 释放
                ftpLog.info("断开FTPUtils对象中处于连接状态的ftp client对象引用[FtpUtil inst addr:" + this + "; ftp client inst " +
                        "addr:" + ftpClient + "]");
                ftpClient = null;
            } else {
                ftpLog.info("FTPUtils对象中的ftp client对象引用未处于连接状态[FtpUtil inst addr:" + this + "; ftp client inst " +
                        "addr:" + ftpClient + "]");
            }
        } catch (Exception e) {
            ftpLog.error("关闭FTP CLIENT失败", e);
            throw new RuntimeException("关闭FTP CLIENT失败", e);
        }
    }

    /**
     * 关闭IO
     *
     * @param closeableInst
     */
    private void close(Closeable closeableInst) {
        if (closeableInst != null) {
            try {
                closeableInst.close();
            } catch (IOException ex) {
                if (closeableInst != null) {
                    try {
                        closeableInst.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean isEmpty(String value) {
        return null == value || value.trim().length() == 0;
    }

    private String removeEnd(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

}
