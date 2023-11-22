package com.oneteam.dormeaseadmin.admin.member;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.Data;

@Data
public class MemberDto {
    private @SQLInjectionSafe int no;                 // COMMENT '관리자 번호'
    private @SQLInjectionSafe String school_no;       // COMMENT '학교 번호'
    private @SQLInjectionSafe String id;              // COMMENT '관리자 아이디'
    private @SQLInjectionSafe String password;        // COMMENT '관리자 비밀번호'
    private @SQLInjectionSafe String name;            // COMMENT '관리자 이름'
    private @SQLInjectionSafe int gender;             // COMMENT '관리자 성별'
    private @SQLInjectionSafe int fail_count;         // COMMENT '로그인 실패 횟수'
    private @SQLInjectionSafe String grade;           // COMMENT '관리자 등급'
    private @SQLInjectionSafe String position;        // COMMENT '관리자 부서'
    private @SQLInjectionSafe String phone;           // COMMENT '관리자 전화 번호'
    private @SQLInjectionSafe String zip_code;        // COMMENT '관리자 우편번호'
    private @SQLInjectionSafe String address;         // COMMENT '관리자 주소'
    private @SQLInjectionSafe String address_detail1; // COMMENT '관리자 상세 주소1'
    private @SQLInjectionSafe String address_detail2; // COMMENT '관리자 상세 주소2'
    private @SQLInjectionSafe String mail;            // COMMENT '관리자 메일'
    private @SQLInjectionSafe String reg_date;        // COMMENT '관리자 등 날짜'
    private @SQLInjectionSafe String mod_date;        // COMMENT '관리자 수정 날짜'
}
