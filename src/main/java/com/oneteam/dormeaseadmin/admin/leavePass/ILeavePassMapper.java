package com.oneteam.dormeaseadmin.admin.leavePass;

import com.oneteam.dormeaseadmin.utils.pagination.PageMakerDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ILeavePassMapper{
    void updateApproveLeavePass(int no);
    LeavePassDto selectLeavePassByNo(int no);

    int updateLeavePass(LeavePassDto leavePassDto);

    List<LeavePassDto> selectLeavePassBySchoolNo(String schoolNo);

    int selectLeavePasses(String schoolNo);

    List<LeavePassDto> selectLeavePassList(PageMakerDto pageMakerDto);
}
