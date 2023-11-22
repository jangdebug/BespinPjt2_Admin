package com.oneteam.dormeaseadmin.product;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.Data;

@Data
public class ProductRegistDto {
    private @SQLInjectionSafe int no;                     // COMMENT '상품 등록 번호'
    private @SQLInjectionSafe String img;                 // COMMENT '상품 이미지'
    private @SQLInjectionSafe String product_name;        // COMMENT '상품 이름'
    private @SQLInjectionSafe int product_price;          // COMMENT '상품 가격'
    private @SQLInjectionSafe String zip_code;            // COMMENT '등록한 학교 우편 번호'
    private @SQLInjectionSafe String school_name;         // COMMENT '등록한 학교 명'
    private @SQLInjectionSafe String admin_id;            // COMMENT '등록한 관리자 아이디'
    private @SQLInjectionSafe String admin_name;          // COMMENT '등록한 관리자 이름'
    private @SQLInjectionSafe int status;                 // COMMENT '상품 등록 상태'
    private @SQLInjectionSafe String reg_date;            // COMMENT '상품 등록 날짜'
    private @SQLInjectionSafe String mod_date;            // COMMENT '상품 수정 날짜'

    //데이터 조인을 위한 추가 데이터
    private @SQLInjectionSafe String school_no;           // COMMENT '등록한 학교 고유 번호'
    private @SQLInjectionSafe String admin_grade;         // COMMENT '등록한 관리자 등급'
}
