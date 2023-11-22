package com.oneteam.dormeaseadmin.api;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IApiMapper {

    public int countTable();
    public void insertSchoolData(List<SchoolInfoDto> schoolInfoDtos);
    public int updateSchoolInfoStatusFalse();
    public void updateSchoolData(List<SchoolInfoDto> schoolInfoDtos);
}


