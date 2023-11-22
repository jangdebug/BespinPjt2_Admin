package com.oneteam.dormeaseadmin.api.scheduler;

import com.oneteam.dormeaseadmin.api.ApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Log4j2
@Component
//@Controller
public class SchoolInfoScheduler {

    @Autowired
    ApiService apiService;

    //총 학교의 수
    @Scheduled(cron = "0 0 0 1 * *")
    //RequestMapping(value = {"", "/"})
    public String countSchoolInfoScheduler(){
        log.info("countSchoolInfoScheduler()");

        StringBuilder result = new StringBuilder();

        String str = "%EA%B3%A0%EB%93%B1%ED%95%99%EA%B5%90";        //고등학교 아스키코드

        try {
            String apiUrl = "https://open.neis.go.kr/hub/schoolInfo?" +
                    "KEY=419ec70ec0d54de6bf489ffa5afe25cf" +
                    "&Type=json" +
                    "&pIndex=1" +
                    "&pSize=1" +
                    "&SCHUL_KND_SC_NM=" + str;

            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;

            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine);

            }
            urlConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        int schoolTableCnt = apiService.countTable();
        int schoolCnt = apiService.countSchoolInfo(result.toString());      //학교 수

        if(schoolTableCnt > 0){
            int statusUpdate = updateSchoolInfoStatusFalse();

            if(statusUpdate > 0){
                updateSchoolInfo(schoolCnt);
            }
        }
        else{
            insertSchoolInfo(schoolCnt);
        }

        return "home";
    }

    private void insertSchoolInfo(int schoolCnt) {
        log.info("insertSchoolInfo()");
        StringBuilder result = null;

        String str = "%EA%B3%A0%EB%93%B1%ED%95%99%EA%B5%90";        //고등학교 아스키코드

        int pIndex = 1;
        int pSize = 500;
        int for_count = schoolCnt / pSize + 1;      //5+1 = 6

        for(int i=0; i<for_count; i++, pIndex++){
            result = new StringBuilder();
            try {

                if(i == for_count-1) pSize = schoolCnt % 500;

                String apiUrl = "https://open.neis.go.kr/hub/schoolInfo?" +
                        "KEY=419ec70ec0d54de6bf489ffa5afe25cf" +
                        "&Type=json" +
                        "&pIndex=" + pIndex +
                        "&pSize=" + pSize +
                        "&SCHUL_KND_SC_NM=" + str;

                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader br;

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String returnLine;

                while ((returnLine = br.readLine()) != null) {
                    result.append(returnLine);

                }
                urlConnection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            apiService.insertSchoolInfo(result.toString());
        }
    }

    private int updateSchoolInfoStatusFalse() {
        log.info("updateSchoolInfoStatusFalse()");

        return apiService.updateSchoolInfoStatusFalse();
    }

    private void updateSchoolInfo(int schoolCnt) {
        log.info("updateSchoolInfo()");

        StringBuilder result = null;

        String str = "%EA%B3%A0%EB%93%B1%ED%95%99%EA%B5%90";        //고등학교 아스키코드

        int pIndex = 1;
        int pSize = 500;
        int for_count = schoolCnt / pSize + 1;      //5+1 = 6

        for(int i=0; i<for_count; i++, pIndex++){
            result = new StringBuilder();
            try {

                if(i == for_count-1) pSize = schoolCnt % 500;

                String apiUrl = "https://open.neis.go.kr/hub/schoolInfo?" +
                        "KEY=419ec70ec0d54de6bf489ffa5afe25cf" +
                        "&Type=json" +
                        "&pIndex=" + pIndex +
                        "&pSize=" + pSize +
                        "&SCHUL_KND_SC_NM=" + str;

                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader br;

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String returnLine;

                while ((returnLine = br.readLine()) != null) {
                    result.append(returnLine);

                }
                urlConnection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            apiService.updateSchoolInfo(result.toString());
        }
    }

}
