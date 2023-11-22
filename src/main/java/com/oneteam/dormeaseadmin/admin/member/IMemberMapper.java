package com.oneteam.dormeaseadmin.admin.member;

import com.oneteam.dormeaseadmin.admin.school.SchoolDto;
import com.oneteam.dormeaseadmin.utils.pagination.PageMakerDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMemberMapper {

    boolean selectDuplicateByID(String id);
    int createAccountConfirm(MemberDto adminDto);
    void insertAdminLoginHistory(MemberDto loginedMemberDto);

    MemberDto selectMemberByID(String id);

    void updateFailCount(MemberDto memberDto);

    int selectMaxNoFromLoginHistory(String id);

    void updateAdminLoginHistory(int maxNo);

    List<SchoolDto> selectSchoolsByName(String name);

    int selectCount(String schoolNo, String userType);

    List<StudentDto> selectStudents(PageMakerDto pageMakerDto);

    List<ParentsDto> selectParents(PageMakerDto pageMakerDto);

    void updateApproval(int no, String userType);

    StudentDto selectStudent(int no);
    ParentsDto selectParents(int no);

    int updateParent(ParentsDto parentsDto);

    int updateStudent(StudentDto studentDto);

    List<MemberDto> selectAdmins(PageMakerDto pageMakerDto);

    void updateAdminApproval(int no, String grade);
}
