package com.my.xa.core.utills;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ConfiguraDataRepository extends PagingAndSortingRepository<ConfiguraData, String > {
    public  ConfiguraData findOneBySerilNo(@Param("serilNo") String serilNo);
    Iterable<ConfiguraData> findAll();//查询所有的对象
}
