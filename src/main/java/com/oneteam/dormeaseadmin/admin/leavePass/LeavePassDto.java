package com.oneteam.dormeaseadmin.admin.leavePass;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.Data;

@Data
public class LeavePassDto {
    private @SQLInjectionSafe int no;                     // COMMENT '외출증 번호'
    private @SQLInjectionSafe String school_no;              // COMMENT '학교 번호'
    private @SQLInjectionSafe String school_name;         // COMMENT '학교 이름'
    private @SQLInjectionSafe String parent_phone;        // COMMENT '학부모 전화번호'
    private @SQLInjectionSafe String dormitory;           // COMMENT '기숙사 호실'
    private @SQLInjectionSafe int student_no;             // COMMENT '학생 번호'
    private @SQLInjectionSafe String student_id;             // COMMENT '학생 아이디'
    private @SQLInjectionSafe String student_name;        // COMMENT '학생 이름'
    private @SQLInjectionSafe int student_grade;          // COMMENT '학생 학년'
    private @SQLInjectionSafe String content;             // COMMENT '외출 사유'
    private @SQLInjectionSafe int admin_approval;         // COMMENT '관리자 승인'
    private @SQLInjectionSafe String start_date;          // COMMENT '출발 날짜'
    private @SQLInjectionSafe String end_date;            // COMMENT '예상 복귀 날짜'
    private @SQLInjectionSafe String comeback_date;            // COMMENT '실제 복귀 날짜'
    private @SQLInjectionSafe String reg_date;            // COMMENT '외출증 등록 날짜'
    private @SQLInjectionSafe String mod_date;            // COMMENT '외출증 수정 날짜'
}