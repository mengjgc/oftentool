package com.my.xa.core.crontask;


import com.my.xa.core.utills.*;
import com.xabkwd.credit.xa.core.utills.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
* @Description:     
* @Author:         xuluzs@foxmail.com
* @CreateDate:
* @UpdateUser:     xuluzs@foxmail.com
* @UpdateDate:      
* @UpdateRemark:    
* @Version:        1.0
*/
@Component
public class DataExportControler {
    private static final Logger LOG = LoggerFactory.getLogger( DataExportControler.class.getName() );

    @Resource
    ConfiguraDataRepository configuraDataRepository;

    @Value( "${xabk.exportData.taskName}" )
    private String taskName;

    @Value( "${xabk.exportData.taskDesc}" )
    private String taskDesc;

    @Value( "${xabk.exportData.taskSerilano}" )
    private String  taskSerilano;

    @Value( "${xabk.exportData.taskOnOrOff}" )
    private String taskOnOrOff;

    @Value( "${xabk.exportData.separator}" )
    private String separator;

    @Value( "${xabk.exportData.encode}" )
    private String encode;

    @Value( "${xabk.exportData.filePath}" )
    private  String filePath;

    @Resource
    private  JdbcTemplate jdbcTemplate;

    /**
     * 价税分离调度
     */
    @Async
    @Scheduled(cron = "${xabk.exportData.cronTime}")
    public void leviedSeparation(){
         if(!"on".equals( this.getTaskOnOrOff())){
            LOG.info(this.getTaskDesc()+"已关闭");
            return;
         }
        String businessDate = DateHelper.getCurrTime("yyyyMMdd");
        ConfiguraData configuraData = this.getConfiguraDataRepository().findOneBySerilNo(this.getTaskSerilano());
        try {
            Class encryptCalss= EncryptData.class;
            Object object = encryptCalss.newInstance();
             LOG.info(this.getTaskDesc()+"开始");
             LOG.info("当前时间。。。："+DateHelper.getCurrTime("yyyy-MM-dd HH:mm:ss"));
             // 生成数据文件
             File file = FIleExportUtil.exportFile(this.getEncode()
                     ,this.getFilePath()
                     ,configuraData
                     ,this.getJdbcTemplate()
                     ,businessDate
                     ,this.getSeparator()
                     , encryptCalss );
            //数据文件创建
             if(file.exists() ){
                 LOG.info("文件生成功。。。"+file.getAbsolutePath());
            }else{
                 LOG.error("文件导出失败");
                 //发生系统应用级错误，需要短信通知
                 throw  new RuntimeException("生成数据文件失败。。。");
             }
         }catch (Exception e){
             e.printStackTrace();
             LOG.error(e.getMessage());
         }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskDesc() {
        return taskDesc;
    }
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
    public String getTaskSerilano() {
        return taskSerilano;
    }
    public void setTaskSerilano(String taskSerilano) {
        this.taskSerilano = taskSerilano;
    }
    public String getTaskOnOrOff() {
        return taskOnOrOff;
    }
    public void setTaskOnOrOff(String taskOnOrOff) {
        this.taskOnOrOff = taskOnOrOff;
    }
    public String getSeparator() {
        return separator;
    }
    public void setSeparator(String separator) {
        this.separator = separator;
    }
    public String getEncode() {
        return encode;
    }
    public void setEncode(String encode) {
        this.encode = encode;
    }

    public ConfiguraDataRepository getConfiguraDataRepository() {
        return configuraDataRepository;
    }

    public void setConfiguraDataRepository(ConfiguraDataRepository configuraDataRepository) {
        this.configuraDataRepository = configuraDataRepository;
    }
}
