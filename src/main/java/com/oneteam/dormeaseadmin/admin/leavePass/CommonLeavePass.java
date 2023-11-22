package com.oneteam.dormeaseadmin.admin.leavePass;

import com.oneteam.dormeaseadmin.utils.pagination.Criteria;
import com.oneteam.dormeaseadmin.utils.pagination.PageMakerDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonLeavePass {
    private static ILeavePassMapper leavePassMapper;

    public CommonLeavePass(ILeavePassMapper leavePassMapper){
        this.leavePassMapper = leavePassMapper;
    }

    public Map<String, Object> commonClass(String schoolNo, int pageNum, int amount){
        Map<String, Object> map = new HashMap<>();
        Criteria criteria = new Criteria(pageNum, amount);
        int total = leavePassMapper.selectLeavePasses(schoolNo);
        PageMakerDto pageMakerDto = new PageMakerDto(schoolNo,criteria, total);
        map.put("pageMakerDto", pageMakerDto);
        List<LeavePassDto> leavePassDtos = leavePassMapper.selectLeavePassList(pageMakerDto);
        map.put("leavePassDtos", leavePassDtos);

        return map;
    }


}
