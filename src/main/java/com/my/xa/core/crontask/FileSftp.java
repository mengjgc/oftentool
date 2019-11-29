package com.my.xa.core.crontask;


import com.my.xa.core.utills.DateHelper;
import com.my.xa.core.utills.SFTPTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
* @Description:     sftp 上传与下载
* @Author:         xuluzs@foxmail.com
* @CreateDate:
* @UpdateUser:     xuluzs@foxmail.com
* @UpdateDate:      
* @UpdateRemark:    
* @Version:        1.0
*/
@Component
public class FileSftp {
    private static final Logger LOG = LoggerFactory.getLogger( FileSftp.class.getName() );

    @Resource
    SFTPTools sFTPTools;

    @Value( "${xabk.SFTP.taskName}" )
    private String taskName;


    @Value( "${xabk.SFTP.sendType}" )
    private String sendType;

    @Value( "${xabk.SFTP.localPath}" )
    private String  localPath;

    @Value( "${xabk.SFTP.taskOnOrOff}" )
    private String taskOnOrOff;

    @Value( "${xabk.SFTP.remotePath}" )
    private String remotePath;

    @Value( "${xabk.SFTP.IP}" )
    private String IP;

    @Value( "${xabk.SFTP.port}" )
    private  String port;

    @Value( "${xabk.SFTP.username}" )
    private String username;

    @Value( "${xabk.SFTP.password}" )
    private String password;

    @Value( "${xabk.SFTP.dirName}" )
    private  String dirName;


    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public SFTPTools getsFTPTools() {
        return sFTPTools;
    }

    public void setsFTPTools(SFTPTools sFTPTools) {
        this.sFTPTools = sFTPTools;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getTaskOnOrOff() {
        return taskOnOrOff;
    }

    public void setTaskOnOrOff(String taskOnOrOff) {
        this.taskOnOrOff = taskOnOrOff;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 文件上传与下载
     */
    @Async
    @Scheduled(cron = "${xabk.SFTP.cronTime}")
    public void dataImport(){
        // 调度是否关闭
         if(!"on".equals( this.getTaskOnOrOff())){
            LOG.info(this.getTaskName()+"-->已关闭");
            return;
         }
         if(this.getPort()==null||"".equals(this.getPassword()))this.setPort("22");
        try{
            String businessDate = DateHelper.getCurrTime("yyyyMMdd");
            String nextBusinessDate = DateHelper.getRelativeDate(businessDate,DateHelper.TERM_UNIT_DAY,1,"yyyyMMdd");
            sFTPTools.setDirName(this.getDirName());
            sFTPTools.setHost(this.getIP());
            sFTPTools.setPort(Integer.parseInt(this.getPort()));
            sFTPTools.setUsername(this.getUsername());
            sFTPTools.setPassword(this.getPassword());
            sFTPTools.setLocal(this.getLocalPath());
            sFTPTools.setRemote(this.getRemotePath());
            if("download".equals(this.getSendType())){//下载
                sFTPTools.download();
            }else if("upload".equals(this.getSendType())){
                sFTPTools.upload();
            }else{
                LOG.error( this.getSendType()+"传输类型有误,标准为[upload,download]");
            }

        }catch (Exception e){
             e.printStackTrace();
             LOG.error(e.getMessage());
         }
    }



}
