package com.my.xa.core.utills;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "WD_CONFIGURA_DATA" )
public class ConfiguraData implements Serializable{

    @Id
    @GenericGenerator( name="cid", strategy="assigned")
    @GeneratedValue( generator="cid" )
    @Column( name = "serilNo", length = 32 )
    String serilNo ;
    @Lob
    @Column( name = "sqlContent")
    String sqlContent;

    @Column( name = "encrrptType", length = 10 )
    String encrrptType;

    @Column( name = "updateTime", length = 10 )
    String updateTime;

    public String getSerilNo() {
        return serilNo;
    }

    public void setSerilNo(String serilNo) {
        this.serilNo = serilNo;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public String getEncrrptType() {
        return encrrptType;
    }

    public void setEncrrptType(String encrrptType) {
        this.encrrptType = encrrptType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
