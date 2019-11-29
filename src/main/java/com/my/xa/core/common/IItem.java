/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.xa.core.common;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author jack2
 */
public interface IItem extends Serializable {

    public String getUuid();
    public void setUuid(String uuid);
    public Calendar getCreateTime();
    public void setCreateTime(Calendar createTime);
    public Calendar getUpdateTime();
    public void setUpdateTime(Calendar updateTime);
    public long getVersion();
    public void setVersion(long version);
    
}
