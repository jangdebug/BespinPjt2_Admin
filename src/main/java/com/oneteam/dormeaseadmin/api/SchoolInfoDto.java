package com.oneteam.dormeaseadmin.api;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.Data;

@Data
public class SchoolInfoDto {
    private @SQLInjectionSafe String atpt_code;           //시도교육청코드
    private @SQLInjectionSafe String atpt_name;           //시도교육청명
    private @SQLInjectionSafe String zip_code;            //학교우편번호
    private @SQLInjectionSafe String school_code;         //표준학교코드
    private @SQLInjectionSafe String school_name;         //학교 명
    private @SQLInjectionSafe String school_knd;          //학교 종류
    private @SQLInjectionSafe int status;              //학교 상태
}
