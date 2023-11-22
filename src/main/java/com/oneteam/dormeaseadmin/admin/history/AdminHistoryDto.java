package com.oneteam.dormeaseadmin.admin.history;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.Data;

@Data
public class AdminHistoryDto {
    private @SQLInjectionSafe int no;                     //COMMENT '관리자 히스토리 번호'
    private @SQLInjectionSafe String admin_id;            //COMMENT '접속한 관리자 아이디'
    private @SQLInjectionSafe String admin_name;          //COMMENT '접속한 관리자 이름'
    private @SQLInjectionSafe String admin_grade;         //COMMENT '접속한 관리자 등급'
    private @SQLInjectionSafe String admin_mail;          //COMMENT '접속한 관리자 이름'
    private @SQLInjectionSafe String login_ip;            //COMMENT '접속한 관리자 아이피'
    private @SQLInjectionSafe String login_date;          //COMMENT '접속 날짜'
    private @SQLInjectionSafe String logout_date;         //COMMENT '접속 시간'
}
