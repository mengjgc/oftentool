/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.xa.core.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

/**
 *
 * @author jack2
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate( true )
@DynamicInsert( true )
public class Item implements IItem {

    private static final long serialVersionUID = 5287847281451148917L;
    
    @Column( length = 50, nullable = false, updatable = false )
    private String uuid;

    @CreatedDate
    @Column( updatable = false )
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8" )
    private Calendar createTime;
    @LastModifiedDate
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
    private Calendar updateTime;
    @Version
    private long version;
    
    public Item() {
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Calendar getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    @Override
    public Calendar getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
    }
    
}
