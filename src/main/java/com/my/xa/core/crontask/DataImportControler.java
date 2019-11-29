package com.my.xa.core.crontask;


import com.my.xa.core.utills.ConfiguraData;
import com.my.xa.core.utills.ConfiguraDataRepository;
import com.my.xa.core.utills.DateHelper;
import com.xabkwd.credit.xa.core.utills.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
public class DataImportControler {
    private static final Logger LOG = LoggerFactory.getLogger( DataImportControler.class.getName() );

    @Resource
    ConfiguraDataRepository configuraDataRepository;

    @Value( "${xabk.importData.taskName}" )
    private String taskName;


    @Value( "${xabk.importData.taskDesc}" )
    private String taskDesc;

    @Value( "${xabk.importData.taskSerilano}" )
    private String  taskSerilano;

    @Value( "${xabk.importData.taskOnOrOff}" )
    private String taskOnOrOff;

    @Value( "${xabk.importData.separator}" )
    private String separator;

    @Value( "${xabk.importData.encode}" )
    private String encode;

    @Value( "${xabk.importData.filePath}" )
    private  String filePath;

    @Resource
    private  JdbcTemplate jdbcTemplate;

    /**
     * 数据导入功能
     */
    @Async
    @Scheduled(cron = "${xabk.importData.cronTime}")
    public void leviedSeparation(){
        // 调度是否关闭
         if(!"on".equals( this.getTaskOnOrOff())){
            LOG.info(this.getTaskDesc()+"已关闭");
            return;
         }
        String businessDate = DateHelper.getCurrTime("yyyyMMdd");
         // 数据库配置信息
        ConfiguraData configuraData = this.getConfiguraDataRepository().findOneBySerilNo(this.getTaskSerilano());

        try {
            File inFile = new File(this.getFilePath());
            FileInputStream inputPutStream  = new FileInputStream(inFile);
            InputStreamReader inputRead = new InputStreamReader(inputPutStream,this.encode);
            BufferedReader  buffReader = new BufferedReader(inputRead);
            this.getJdbcTemplate().execute(new ConnectionCallback<List>(){
                 @Override
                 public List doInConnection(Connection con) throws SQLException, DataAccessException {
                     int inserSum = 0;
                     try{
                         PreparedStatement insertPstm = con.prepareStatement(configuraData.getSqlContent());
                         LOG.info("sql:"+configuraData.getSqlContent());
                         String str = buffReader.readLine();
                         int comitSum = 10000;
                         while(null != str&&str.length()>0){
                             inserSum ++;
                            String [] values = str.split(getSeparator());
                             for (String value:values) {
                                 int index = 1;
                                 value = value.replace("\"","");
                                 insertPstm.setString(index,value);
                                 index ++;
                             }
                             str = buffReader.readLine();
                             insertPstm.addBatch();
                             if(inserSum%comitSum==0){
                                 insertPstm.executeBatch();
                                 insertPstm.clearBatch();
                             }
                         }
                         if(inserSum>0){
                             insertPstm.executeBatch();
                             insertPstm.clearBatch();
                         }
                         LOG.info("数据导入成功，共"+inserSum);
                     }catch (Exception e){
                         LOG.error("数据行：" +inserSum+"异常");
                         e.printStackTrace();
                     }
                     return null;
                 }
                }
            );
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
