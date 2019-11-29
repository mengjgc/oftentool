package com.my.xa.core.utills;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Vector;

/**
 * SFTP 文件上传下载
 */
public class SFTPTools {
    private static final Logger LOG = LoggerFactory.getLogger( SFTPTools.class.getName() );
    private ChannelSftp sftp = null;
    private SFTPUtil su = null;

    private String host ;
    private String remote;
    private String username;
    private  String password ;
    private String dirName;
    private String local;
    private int  port = 0;
    public SFTPTools(){

    }
    public SFTPTools(String host, String remote, String username, String password, String dirName, String local, int port) {
        this.host = host;
        this.remote = remote;
        this.username = username;
        this.password = password;
        this.dirName = dirName;
        this.local = local;
        this.port = port;
    }

    /**
     *  下载文件 递归下载文件及目录
     * @return
     * @throws Exception
     */
    public  boolean download() throws Exception {
        port =  port==0 ? 22 : this.port;
        try{
            su = new SFTPUtil(host,username,password,port);
            su.connect();
            sftp =  su.getSftp();
            //LOG.info(remote+"------"+dirName+"------"+local);
            downloadSftpFile(remote,dirName,local);
            LOG.info("结束。。。");
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            su.disconnect();
        }
    }

    /**
     *  上传文件 递归上传文件及目录
     * @return
     * @throws Exception
     */
    public boolean upload() throws Exception {
        port =  port==0 ? 22 : this.port;
        try{
            su = new SFTPUtil(host,username,password,port);
            su.connect();
            sftp =  su.getSftp();
            uploadSftpFile(remote,local);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        } finally{
            su.disconnect();
        }
    }

    /**
     * @param remote	远程文件路径
     * @param downloadFile 远程文件名
     * @param saveFile 本地文件绝对路径文件
     * @throws Exception
     */
    private void downloadSftpFile(String remote,String downloadFile,String saveFile) throws Exception{
        try {
            SftpATTRS sftpAttr =   sftp.lstat(remote+"/"+downloadFile);
            if(sftpAttr.isDir()){
                sftp.cd(remote+"/"+downloadFile);
                File file = new File(saveFile);
                if(!file.exists()){
                    file.mkdir();
                }else{
                    if(!file.isDirectory())file.mkdir();
                }
                Vector<ChannelSftp.LsEntry> vec = sftp.ls(remote+"/"+downloadFile);
                for (ChannelSftp.LsEntry lsEntry : vec) {
                    if(lsEntry.getFilename().equals("..")||lsEntry.getFilename().equals("."))continue;
                    downloadSftpFile(remote+"/"+downloadFile, lsEntry.getFilename(),saveFile+"/"+lsEntry.getFilename());
                }
            }else{
                su.download(remote,downloadFile,saveFile);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void uploadSftpFile(String remote,String localFile) throws Exception{
        try {
            File file = new File(localFile);
            if(file.isDirectory()){
                su.mkdir(remote+"/"+file.getName());
                File listFile[] = file.listFiles();
                for (File files : listFile) {
                    uploadSftpFile(remote+"/"+file.getName(), files.getAbsolutePath());
                }
            }else if(file.isFile()){
                su.upload(remote, localFile);
            }else{
                throw new Exception("本地["+localFile+"]目录不存在");
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
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

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}