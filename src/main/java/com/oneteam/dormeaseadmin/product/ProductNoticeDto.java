package com.oneteam.dormeaseadmin.product;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.Data;

@Data
public class ProductNoticeDto {

    private @SQLInjectionSafe int no;               // COMMENT '공지 번호'
    private @SQLInjectionSafe String img;           // COMMENT '상품 이미지',
    private @SQLInjectionSafe String name;          // COMMENT '상품 명',
    private @SQLInjectionSafe String price;         // COMMENT '상품 가격',
    private @SQLInjectionSafe int status;           // COMMENT '등록 상태',
    private @SQLInjectionSafe String reg_date;      // COMMENT '등록 날짜',

}
