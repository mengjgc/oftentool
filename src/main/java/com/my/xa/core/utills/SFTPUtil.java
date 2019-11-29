package com.my.xa.core.utills;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;
import java.util.Properties;  

import com.jcraft.jsch.Channel;  
import com.jcraft.jsch.ChannelSftp;  
import com.jcraft.jsch.JSch;  
import com.jcraft.jsch.Session;  
 
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SFTPUtil {
    private static final Logger LOG = LoggerFactory.getLogger( SFTPUtil.class.getName() );
    private String host;  
    private String username;  
    private String password;  
    private int port = 22;  
    private ChannelSftp sftp = null;  
    
    /**
     * 指定端口
     * @param host
     * @param username
     * @param password
     * @param port
     */
    public SFTPUtil(String host,String username,String password,int port){
    	this.host = host;
    	this.username = username;
    	this.password = password;
    	this.port = port;
    }
    
    /**
     * 默认端口
     * @param host
     * @param username
     * @param password
     */
    public SFTPUtil(String host,String username,String password){
    	this.host = host;
    	this.username = username;
    	this.password = password;
    }
    
    
    /** 
     * connect server via sftp 
     */  
    public void connect() throws Exception{  
        JSch jsch = new JSch();  
        Session sshSession = jsch.getSession(username, host, port);  
        LOG.info("Session created.");
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");  
        sshSession.setConfig(sshConfig);  
        sshSession.connect();  
        LOG.info("Session connected.");  
        LOG.info("Opening Channel.");  
        Channel channel = sshSession.openChannel("sftp");  
        channel.connect();  
        sftp = (ChannelSftp) channel;  
        //SftpATTRS d = sftp.lstat("");
        
        LOG.info("Connected to " + host + ".");  
    }  
    /** 
     * Disconnect with server 
     */  
    public void disconnect() {  
        if(this.sftp != null){  
            if(this.sftp.isConnected()){  
                this.sftp.disconnect();  
            }else if(this.sftp.isClosed()){  
            	LOG.info("sftp is closed already");  
            }  
        }  
    }  
    
    /**
     * @param remote	远程文件路径
     * @param downloadFile 远程文件名
     * @throws Exception
     */
    public void download(String remote, String downloadFile,String localFile) throws Exception{  
        sftp.cd(remote);
        File file = new File(localFile);  
        FileOutputStream os = new FileOutputStream(file);
        sftp.get(downloadFile, os);
        os.close();
    }  
      
    /**
     *  
     * @param remote 远程文件路径
     * @param localFile 本地文件绝对路径文件
     * @throws Exception
     */
    public void upload(String remote,String localFile) throws Exception{
    	mkdir(remote);
    	sftp.cd(remote);
    	File rfile = new File(localFile);
    	String filename = rfile.getName();
    	
    	FileInputStream is = new FileInputStream(localFile);
    	this.sftp.put(is, filename);
    	is.close();
    }  
    /** 
     * create remote 
     * @param filepath
     */  
    public void mkdir(String filepath) throws Exception{  
        File file = new File(filepath);  
        String ppath = file.getParent();  
        if(ppath==null||ppath==File.separator||ppath=="\\"){
            return ;
        }
        ppath=ppath.replace("\\", "/");   
        try {
            this.sftp.cd(filepath);
        } catch (SftpException e1) { 
        	mkdir(ppath);
        	this.sftp.cd(ppath);
        	this.sftp.mkdir(filepath);
        	this.sftp.cd(filepath);
        }  
    }

	public ChannelSftp getSftp() {
		return sftp;
	}

	public void setSftp(ChannelSftp sftp) {
		this.sftp = sftp;
	}
}  