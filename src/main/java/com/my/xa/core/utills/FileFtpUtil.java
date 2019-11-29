package com.my.xa.core.utills;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ftp 传送文件
 */
public class FileFtpUtil {
    private static final Logger LOG= LoggerFactory.getLogger(FileFtpUtil.class.getName());
    private String ftp_ip;
    private int ftp_port;
    private String ftp_user;
    private String ftp_pwd;
    private File sourceFile;
    private String targetFile;
    private String okFlag;
    private String okFilePath;
    private String encode;
    private String businessDate;

    public   boolean ftpSendFile(){
         LOG.info("FTP 文件传送开始");
         boolean success = false;
         FTPClient ftp = new FTPClient();
         ftp.setControlEncoding(encode);
         FileInputStream input = null;
         BufferedInputStream inputBuffer =  null;
         String   lastBusinessDate = null;
         try {
             lastBusinessDate = DateHelper.getRelativeDate(businessDate, DateHelper.TERM_UNIT_MONTH, -1, "yyyyMMdd");
             targetFile =targetFile.replace("@{businessDate}",businessDate);
             targetFile = targetFile.replace("@{lastBusinessDate}",lastBusinessDate);
             int reply;
             ftp.connect(ftp_ip, ftp_port);// 连接FTP服务器
             ftp.login(ftp_user, ftp_pwd);// 登录
             reply = ftp.getReplyCode();
             if (!FTPReply.isPositiveCompletion(reply)) {
                 ftp.disconnect();
                 LOG.error("ftp 服务器连接失败");
                 return success;
             }
             ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
             File[] files = sourceFile.listFiles();
             LOG.info("ftp 服务器路径："+targetFile);
             ftp.makeDirectory(targetFile);
             ftp.changeWorkingDirectory(targetFile);
             File okFile = null;
             for (int i = 0; i < files.length ; i++) {
                 if(files[i].getName().contains(okFlag)){
                     okFile = files[i];
                     continue;
                 }
                 input = new FileInputStream(files[i]);
                 inputBuffer = new BufferedInputStream(input);
                 ftp.storeFile(files[i].getName(),inputBuffer );
             }
             // 最后传送 OK 文件
             LOG.info("传送 OK 文件");
             if(okFile!=null) {
                 input = new FileInputStream(okFile);
                 inputBuffer = new BufferedInputStream(input);
                 ftp.storeFile(okFile.getName(), inputBuffer);
             }
             ftp.logout();
             success = true;
             LOG.info("FTP 文件传送结束");
         } catch (Exception e) {
             e.printStackTrace();
             LOG.error(e.getMessage());
             return false;
         } finally {
                 try {
                     if (ftp.isConnected())    ftp.disconnect();
                     if(input!=null)input.close();
                     if(inputBuffer!=null)inputBuffer.close();
                 } catch (IOException ioe) {
                 }
         }
        return success;
 }

    public String getFtp_ip() {
        return ftp_ip;
    }

    public void setFtp_ip(String ftp_ip) {
        this.ftp_ip = ftp_ip;
    }

    public int getFtp_port() {
        return ftp_port;
    }

    public void setFtp_port(int ftp_port) {
        this.ftp_port = ftp_port;
    }

    public String getFtp_user() {
        return ftp_user;
    }

    public void setFtp_user(String ftp_user) {
        this.ftp_user = ftp_user;
    }

    public String getFtp_pwd() {
        return ftp_pwd;
    }

    public void setFtp_pwd(String ftp_pwd) {
        this.ftp_pwd = ftp_pwd;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getOkFlag() {
        return okFlag;
    }

    public void setOkFlag(String okFlag) {
        this.okFlag = okFlag;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }
}
