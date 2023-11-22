package com.oneteam.dormeaseadmin.admin.member;

import com.oneteam.dormeaseadmin.admin.school.SchoolDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class MemberService {

    private final IMemberMapper memberMapper;
    private final CommonUser commonUser;
    private final PasswordEncoder passwordEncoder;

    public MemberService(IMemberMapper memberMapper, PasswordEncoder passwordEncoder, CommonUser commonUser) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.commonUser = commonUser;
    }

    //관리자 계정생성 중복 확인
    public Map<String, Object> checkIdDuplication(String id) {
        log.info("loginAccountConfirm()");

        boolean isDuplicateID = memberMapper.selectDuplicateByID(id);

        Map<String, Object> map = new HashMap<>();
        map.put("isDuplicateID", isDuplicateID);
        return map;
    }

    //관리자 계정생성 확인
    public int createAccountConfirm(MemberDto memberDto) {
        log.info("createAccountConfirm()");

        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return memberMapper.createAccountConfirm(memberDto);
    }

    //관리자 로그인 확인
    public Map<String, Object> loginConfirm(MemberDto memberDto) {
        log.info("loginConfirm()");
        MemberDto loginedMemberDto = memberMapper.selectMemberByID(memberDto.getId());
        Map<String, Object> map = new HashMap<>();
        if (loginedMemberDto != null) {
            if (!passwordEncoder.matches(memberDto.getPassword(), loginedMemberDto.getPassword())) {
                memberMapper.updateFailCount(memberDto);
                map.put("fail_count", loginedMemberDto.getFail_count() + 1);
                map.put("result", false);
                loginedMemberDto = null;
            } else {
                if (loginedMemberDto.getFail_count() >= 5) {
                    map.put("fail_count", loginedMemberDto.getFail_count());
                    loginedMemberDto = null;
                    map.put("result", false);
                } else {
                    loginedMemberDto.setPassword(null);
                    memberMapper.updateFailCount(loginedMemberDto);
                    memberMapper.insertAdminLoginHistory(loginedMemberDto);
                    loginedMemberDto.setFail_count(0);
                    map.put("fail_count", 0);
                    map.put("result", true);
                }
            }
        }
        map.put("loginedMemberDto", loginedMemberDto);
        return map;
    }

    public void logoutConfirm(MemberDto loginedMemberDto) {
        log.info("loginConfirm()");

        int maxNo = memberMapper.selectMaxNoFromLoginHistory(loginedMemberDto.getId());
        log.info("maxNo{}", maxNo);
        memberMapper.updateAdminLoginHistory(maxNo);
    }

    public Object searchSchoolName(String name) {
        log.info("searchSchoolName()");
        Map<String, Object> map = new HashMap<>();
        List<SchoolDto> schoolDtos =  memberMapper.selectSchoolsByName(name);
        map.put("schoolDtos", schoolDtos);

        return map;
    }

    public Map<String, Object> parentsApprovalList(int no, String schoolNo, int pageNum, int amount) {
        log.info("parentsApprovalList()");
        if(no > 0){
            String userType = "parents";
            memberMapper.updateApproval(no, userType);
        }

        return commonUser.commonClass(schoolNo, pageNum, amount, "parents");
    }

    public Map<String, Object> studentApprovalList(int no, String schoolNo, int pageNum, int amount) {
        log.info("studentApprovalList()");
        if(no > 0){
            String userType = "student";
            memberMapper.updateApproval(no, userType);
        }

        return commonUser.commonClass(schoolNo, pageNum, amount, "student");
    }

    public StudentDto modifyStudentForm(int no) {
        log.info("modifyStudentForm");
        StudentDto studentDto = memberMapper.selectStudent(no);

        return studentDto;
    }

    public ParentsDto modifyParentsForm(int no) {
        log.info("modifyParentsForm");
        ParentsDto parentsDto = memberMapper.selectParents(no);

        return parentsDto;
    }

    public int modifyParentsConfirm(ParentsDto parentsDto) {
        log.info("modifyParentsConfirm");

        int result = memberMapper.updateParent(parentsDto);

        return result;
    }

    public int modifyStudentConfirm(StudentDto studentDto) {
        log.info("modifyStudentConfirm");

        int result = memberMapper.updateStudent(studentDto);

        return result;
    }

    public Map<String, Object> admin002ApprovalList(int no, String schoolNo, int pageNum, int amount) {
        log.info("admin002ApprovalList()");
        if(no > 0){
            String grade = "admin_002";
            memberMapper.updateAdminApproval(no, grade);
        }

        return commonUser.commonClass(schoolNo, pageNum, amount, "admin");
    }

    public Map<String, Object> admin001ApprovalList(int no, int pageNum, int amount) {
        log.info("admin002ApprovalList()");
        if(no > 0){
            String grade = "admin_001";
            memberMapper.updateAdminApproval(no, grade);
        }

        return commonUser.commonClass(null, pageNum, amount, "admin");
    }
}
