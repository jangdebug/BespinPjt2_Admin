package com.oneteam.dormeaseadmin.product;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.Data;

@Data
public class ProductDto {

    private @SQLInjectionSafe int no;                // COMMENT '상품 등록 번호'
    private @SQLInjectionSafe String img;           // COMMENT '상품 이미지'
    private @SQLInjectionSafe String name;           // COMMENT '상품 명'
    private @SQLInjectionSafe int price;             // COMMENT '상품 가격'
    private @SQLInjectionSafe String admin_id;       // '등록 관리자 아이디';
    private @SQLInjectionSafe String admin_name;     // '등록 관리자 명';
    private @SQLInjectionSafe int status;            // '상품 상태';
    private @SQLInjectionSafe String reg_date;       // '등록 날짜';
    private @SQLInjectionSafe String mod_date;       // '수정 날짜';

}
