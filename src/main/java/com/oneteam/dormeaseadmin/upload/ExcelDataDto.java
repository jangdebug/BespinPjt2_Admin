package com.oneteam.dormeaseadmin.upload;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.Data;

@Data
public class ExcelDataDto {
    private @SQLInjectionSafe String name;                //학교 명
    private @SQLInjectionSafe String zip_code;            //학교 우편번호
    private @SQLInjectionSafe String address;             //학교 주소

}
