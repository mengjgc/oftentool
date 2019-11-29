package com.my.xa.core.utills;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@RestController
public class FIleExportUtil {


    private static final Logger LOG= LoggerFactory.getLogger(FIleExportUtil.class.getName());

    /**
     * 导出文件
     * @param encode
     * @param sourceFilePath
     * @param sqlSource
     * @param jdbcTemplate
     * @param businessDate
     * @param separator
     * @return
     * @throws Exception
     */
    public static File exportFile(String encode, String sourceFilePath, String sqlSource,JdbcTemplate jdbcTemplate,String businessDate,String separator)throws  Exception
    {
        String lastBusinessDate =  null;
        final String encodeFile  = encode;
        FileOutputStream fileOut = null;
        File inSouceFile = null;
        if(encode==null)encode="utf-8"; //默认编码
        lastBusinessDate = DateHelper.getRelativeDate(businessDate, DateHelper.TERM_UNIT_DAY, -1, "yyyyMMdd");
        sourceFilePath = sourceFilePath.replace("@{businessDate}",businessDate);
        sourceFilePath = sourceFilePath.replace("@{lastBusinessDate}",lastBusinessDate);

        sqlSource = sqlSource.replace("@{businessDate}",businessDate);
        sqlSource = sqlSource.replace("@{lastBusinessDate}",lastBusinessDate);

         LOG.info("执行SQL："+sqlSource);
        inSouceFile = new File(sourceFilePath);
        if(!inSouceFile.getParentFile().exists())inSouceFile.getParentFile().mkdirs();
        if(!inSouceFile.exists())inSouceFile.createNewFile();
        fileOut = new FileOutputStream(inSouceFile);
        final OutputStream out =new BufferedOutputStream(fileOut);
        try {
            jdbcTemplate.query(sqlSource, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    ResultSetMetaData md= rs.getMetaData();
                    String strLine = "";
                    try {
                        for (int i = 1; i < md.getColumnCount() + 1; i++) {
                            String st = rs.getString(i)==null?"":rs.getString(i);
                            strLine += i == 1 ? st : (separator + st);
                        }
                        strLine += "\r\n";
                        out.write(strLine.getBytes(encodeFile));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            out.flush();
            return inSouceFile;
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage());
            return null;
        }finally{
            try {
                if(out!=null)out.close();
                if(fileOut!=null)fileOut.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                LOG.error(e2.getMessage());
            }
        }
    }

    public static File exportFile(String encode, String sourceFilePath,ConfiguraData configuraData, JdbcTemplate jdbcTemplate, String businessDate, String separator,Class encryptCalss)throws  Exception
    {
        String lastBusinessDate =  null;
        final String encodeFile  = encode;
        FileOutputStream fileOut = null;
        File inSouceFile = null;
        if(encode==null)encode="utf-8"; //默认编码
        String sqlSource = configuraData.getSqlContent();
        lastBusinessDate = DateHelper.getRelativeDate(businessDate, DateHelper.TERM_UNIT_DAY, -1, "yyyyMMdd");
        sourceFilePath = sourceFilePath.replace("@{businessDate}",businessDate);
        sourceFilePath = sourceFilePath.replace("@{lastBusinessDate}",lastBusinessDate);

        sqlSource = sqlSource.replace("@{businessDate}",businessDate);
        sqlSource = sqlSource.replace("@{lastBusinessDate}",lastBusinessDate);
        LOG.info("执行SQL："+sqlSource);
        inSouceFile = new File(sourceFilePath);
        if(!inSouceFile.getParentFile().exists())inSouceFile.getParentFile().mkdirs();
        if(!inSouceFile.exists())inSouceFile.createNewFile();
        fileOut = new FileOutputStream(inSouceFile);
        final OutputStream out =new BufferedOutputStream(fileOut);
        try {
            jdbcTemplate.query(sqlSource, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    ResultSetMetaData md= rs.getMetaData();
                    String strLine = "";
                    try {
                        for (int i = 1; i < md.getColumnCount() + 1; i++) {
                            String st = rs.getString(i)==null?"":rs.getString(i);
                            if(md.getColumnName(i).startsWith("JAVA")){
                                String enType = md.getColumnName(i).split("\\$")[1];
                                Method method = encryptCalss.getMethod(enType,String.class);
                                st = (String) method.invoke(enType,st);
                            }
                            strLine += i == 1 ? st : (separator + st);
                        }
                        strLine += "\r\n";
                        out.write(strLine.getBytes(encodeFile));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            out.flush();
            return inSouceFile;
        }catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage());
            return null;
        }finally{
            try {
                if(out!=null)out.close();
                if(fileOut!=null)fileOut.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                LOG.error(e2.getMessage());
            }
        }
    }
}
