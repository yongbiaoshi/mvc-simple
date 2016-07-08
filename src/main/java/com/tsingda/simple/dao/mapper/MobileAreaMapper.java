package com.tsingda.simple.dao.mapper;

import com.tsingda.simple.model.MobileArea;

public interface MobileAreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MobileArea record);

    int insertSelective(MobileArea record);

    MobileArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MobileArea record);

    int updateByPrimaryKey(MobileArea record);
}