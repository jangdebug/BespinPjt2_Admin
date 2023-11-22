package com.oneteam.dormeaseadmin.admin.leavePass;

import com.oneteam.dormeaseadmin.admin.member.MemberDto;
import com.oneteam.dormeaseadmin.utils.pagination.PageDefine;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/leavePass")
public class LeavePassController {

    private final LeavePassService leavePassService;

    public LeavePassController(LeavePassService leavePassService) {
        this.leavePassService = leavePassService;
    }

    @GetMapping("/leavePassList")
    public String leavePassList(Model model, HttpSession session,
                                             @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_PAGE_NUMBER) int pageNum,
                                             @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_AMOUNT) int amount){
        log.info("leavePassList()");
        String nextPage = "admin/leavePassList";
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        Map<String, Object> map = leavePassService.leaveOutList(loginedMemberDto.getSchool_no(), pageNum, amount);
        model.addAttribute("pageMakerDto", map.get("pageMakerDto"));
        model.addAttribute("leavePassDtos", map.get("leavePassDtos"));

        return nextPage;
    }

    @GetMapping("/approveLeavePass")
    @ResponseBody
    public Object approveLeavePass(HttpSession session, @RequestParam int no,
                                   @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_PAGE_NUMBER) int pageNum,
                                   @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_AMOUNT) int amount){
        log.info("approveLeavePass()");
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");

        return leavePassService.approveLeavePass(no, loginedMemberDto.getSchool_no(), pageNum, amount);
    }

    @GetMapping("/modifyLeavePassForm")
    public String modifyLeavePassForm(@RequestParam int no, Model model){
        log.info("modifyLeavePassForm()");
        String nextPage = "leavePass/modifyLeavePassForm";
        LeavePassDto leavePassDto = leavePassService.modifyLeavePassForm(no);
        model.addAttribute("leavePassDto", leavePassDto);

        return nextPage;
    }
    @PostMapping("/modifyLeavePassConfirm")
    public String modifyLeavePassConfirm(LeavePassDto leavePassDto){
        log.info("modifyLeavePassConfirm()");
        String nextPage = "redirect:/leavePass/leaveOutList";
        int result = leavePassService.modifyLeavePassConfirm(leavePassDto);

        return nextPage;
    }
    @GetMapping("/modifyComebackDateConfirm")
    @ResponseBody
    public Object modifyComebackDateConfirm(LeavePassDto leavePassDto,
                                            @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_PAGE_NUMBER) int pageNum,
                                            @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_AMOUNT) int amount){
        log.info("modifyComebackDateConfirm()");

        Map<String, Object> map = leavePassService.modifyComebackDateConfirm(leavePassDto, pageNum, amount);

        return map;
    }
}
