package com.oneteam.dormeaseadmin.admin.member;

import com.oneteam.dormeaseadmin.admin.history.AdminHistoryDto;
import com.oneteam.dormeaseadmin.admin.leavePass.ILeavePassMapper;
import com.oneteam.dormeaseadmin.admin.leavePass.LeavePassDto;
import com.oneteam.dormeaseadmin.utils.pagination.Criteria;
import com.oneteam.dormeaseadmin.utils.pagination.PageMakerDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonUser {
    private static IMemberMapper memberMapper;

    public CommonUser(IMemberMapper memberMapper){
        this.memberMapper = memberMapper;
    }

    public Map<String, Object> commonClass(String keyWord, int pageNum, int amount, String userType){
        Map<String, Object> map = new HashMap<>();
        Criteria criteria = new Criteria(pageNum, amount);
        int total = memberMapper.selectCount(keyWord,userType);
        PageMakerDto pageMakerDto = new PageMakerDto(keyWord,criteria, total);
        map.put("pageMakerDto", pageMakerDto);
        if(userType.equals("student")){
            List<StudentDto> studentDtos = memberMapper.selectStudents(pageMakerDto);
            map.put("studentDtos", studentDtos);
        } else if(userType.equals("parents")){
            List<ParentsDto> parentsDtos = memberMapper.selectParents(pageMakerDto);
            map.put("parentsDtos", parentsDtos);
        } else if(userType.equals("admin")){
            List<MemberDto> memberDtos = memberMapper.selectAdmins(pageMakerDto);
            map.put("memberDtos", memberDtos);
        }
        return map;
    }


}
