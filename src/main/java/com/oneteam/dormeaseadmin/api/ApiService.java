package com.oneteam.dormeaseadmin.api;

import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ApiService {

    @Autowired
    IApiMapper apiMapper;

    public int countSchoolInfo(String result) {
        log.info("countSchoolInfo()");

        List<SchoolInfoDto> schoolInfoDtos = new ArrayList<>();
        SchoolInfoDto schoolInfoDto = null;
        int count = 0;

        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);

            JSONArray data = (JSONArray) jsonObj.get("schoolInfo");
            JSONObject getRow = (JSONObject) data.get(0);

            JSONArray getArray = (JSONArray) getRow.get("head");
            JSONObject getCountArray = (JSONObject) getArray.get(0);

            count = (int) ((long) getCountArray.get("list_total_count"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    //테이블의 학교 수
    public int countTable() {
        log.info("countTable()");

        return apiMapper.countTable();
    }

    //최초의 학교 데이터 입력
    public void insertSchoolInfo(String result) {
        log.info("insertSchoolInfo()");

        List<SchoolInfoDto> schoolInfoDtos = new ArrayList<>();
        SchoolInfoDto schoolInfoDto = null;

        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);

            JSONArray array = (JSONArray) jsonObj.get("schoolInfo");

            JSONObject jObj = (JSONObject) array.get(1);

            JSONArray row = (JSONArray) jObj.get("row");

            for (int i = 0; i < row.size()-1; i++) {
                JSONObject obj = (JSONObject) row.get(i);

                schoolInfoDto = new SchoolInfoDto();
                schoolInfoDto.setAtpt_code((String) obj.get("ATPT_OFCDC_SC_CODE"));
                schoolInfoDto.setAtpt_name((String) obj.get("ATPT_OFCDC_SC_NM"));
                schoolInfoDto.setZip_code(((String) obj.get("ORG_RDNZC")).trim());
                schoolInfoDto.setSchool_code((String) obj.get("SD_SCHUL_CODE"));
                schoolInfoDto.setSchool_name((String) obj.get("SCHUL_NM"));
                schoolInfoDto.setSchool_knd((String) obj.get("SCHUL_KND_SC_NM"));
                schoolInfoDto.setStatus(1);

                schoolInfoDtos.add(schoolInfoDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        apiMapper.insertSchoolData(schoolInfoDtos);
    }

    //학교 정보 스케줄 작동 시, 기본적으로 모든 학교의 상태 값을 0으로 설정
    public int updateSchoolInfoStatusFalse() {
        log.info("updateSchoolInfoStatusFalse()");

        return apiMapper.updateSchoolInfoStatusFalse();
    }

    //학교 스케줄 작동 시, 있는 데이터에 한해 상태 값 1로 재설정
    public void updateSchoolInfo(String result) {
        log.info("updateSchoolInfo()");

        List<SchoolInfoDto> schoolInfoDtos = new ArrayList<>();
        SchoolInfoDto schoolInfoDto = null;

        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);

            JSONArray array = (JSONArray) jsonObj.get("schoolInfo");

            JSONObject jObj = (JSONObject) array.get(1);

            JSONArray row = (JSONArray) jObj.get("row");

            for (int i = 0; i < row.size()-1; i++) {
                JSONObject obj = (JSONObject) row.get(i);

                schoolInfoDto = new SchoolInfoDto();
                schoolInfoDto.setAtpt_code((String) obj.get("ATPT_OFCDC_SC_CODE"));
                schoolInfoDto.setAtpt_name((String) obj.get("ATPT_OFCDC_SC_NM"));
                schoolInfoDto.setZip_code(((String) obj.get("ORG_RDNZC")).trim());
                schoolInfoDto.setSchool_code((String) obj.get("SD_SCHUL_CODE"));
                schoolInfoDto.setSchool_name((String) obj.get("SCHUL_NM"));
                schoolInfoDto.setSchool_knd((String) obj.get("SCHUL_KND_SC_NM"));
                schoolInfoDto.setStatus(1);



                schoolInfoDtos.add(schoolInfoDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        apiMapper.updateSchoolData(schoolInfoDtos);
    }
}
