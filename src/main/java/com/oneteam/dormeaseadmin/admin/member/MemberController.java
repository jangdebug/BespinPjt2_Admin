package com.oneteam.dormeaseadmin.admin.member;

import com.oneteam.dormeaseadmin.utils.pagination.PageDefine;
import com.oneteam.dormeaseadmin.utils.pagination.PageMakerDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/admin/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*
     * 관리자 계정생성 폼
     */
    @GetMapping("/createAccountForm")
    public String createAccountForm() {
        log.info("createAccountForm()");

        String nextPage = "admin/createAccountForm";

        return nextPage;
    }

    /*
     * 아이디 중복 여부 체크
     */

    @GetMapping("/checkIdDuplication")
    @ResponseBody
    public Object checkIdDuplication(@RequestParam String id) {
        log.info("checkIdDuplication()");

        Map<String, Object> map = memberService.checkIdDuplication(id);

        return map;
    }

    /*
     * 학교 이름 검색
     */

    @GetMapping("/searchSchoolName")
    @ResponseBody
    public Object searchSchoolName(@RequestParam String name) {
        log.info("searchSchoolName()");

        return memberService.searchSchoolName(name);

    }


    /*
     * 관리자 계정생성 확인
     */
    @PostMapping("/createAccountConfirm")
    public String createAccountConfirm(MemberDto adminDto, Model model) {
        log.info("createAccountConfirm()");

        String nextPage = "admin/createAccountResult";

        int result = memberService.createAccountConfirm(adminDto);
        model.addAttribute("result", result);

        return nextPage;
    }

    /*
     * 관리자 로그인 폼
     */
    @GetMapping("/loginForm")
    public String loginForm() {
        log.info("loginForm()");

        String nextPage = "admin/loginForm";

        return nextPage;
    }

    /*
     * 관리자 로그인 확인
     */

    @PostMapping("/loginConfirm")
    @ResponseBody
    public Object loginConfirm(MemberDto memberDto, HttpSession session) {
        log.info("loginConfirm()");

        Map<String, Object> map = memberService.loginConfirm(memberDto);
        MemberDto loginedMemberDto = (MemberDto) map.get("loginedMemberDto");
        if (loginedMemberDto != null) {
            session.setAttribute("loginedMemberDto", loginedMemberDto);
            session.setMaxInactiveInterval(30 * 60);
        }
        return map;

    }

    /*
     * 관리자 로그아웃 확인
     */
    @GetMapping("/logoutConfirm")
    public String logoutConfirm(HttpSession session) {
        log.info("logoutConfirm()");

        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        memberService.logoutConfirm(loginedMemberDto);
        session.removeAttribute("loginedMemberDto");

        return "redirect:/";
    }

    /*
     * 학생 사용자 리스트
     */
    @GetMapping("/studentApprovalList")
    public String studentApprovalList(HttpSession session, Model model,
                                      @RequestParam(required = false, defaultValue = "0") int no,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_PAGE_NUMBER) int pageNum,
                                      @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_AMOUNT) int amount) {
        log.info("studentApprovalList()");
        String nextPage = "admin/studentApprovalList";
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        Map<String, Object> map = memberService.studentApprovalList(no, loginedMemberDto.getSchool_no(), pageNum, amount);
        model.addAttribute("studentDtos", map.get("studentDtos"));
        model.addAttribute("pageMakerDto", map.get("pageMakerDto"));

        return nextPage;
    }

    /*
     * 학부모 사용자 리스트
     */
    @GetMapping("/parentsApprovalList")
    public String parentsApprovalList(HttpSession session, Model model,
                                      @RequestParam(required = false, defaultValue = "0") int no,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_USER_PAGE_NUMBER) int pageNum,
                                      @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_USER_AMOUNT) int amount) {
        log.info("parentsApprovalList()");
        String nextPage = "admin/parentsApprovalList";
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        Map<String, Object> map = memberService.parentsApprovalList(no, loginedMemberDto.getSchool_no(), pageNum, amount);
        model.addAttribute("parentsDtos", map.get("parentsDtos"));
        model.addAttribute("pageMakerDto", map.get("pageMakerDto"));

        return nextPage;
    }

    @GetMapping("/modifyStudentForm")
    public String modifyStudentForm(@RequestParam int no, Model model) {
        log.info("modifyStudentForm()");

        String nextPage = "admin/modifyStudentForm";
        StudentDto studentDto = memberService.modifyStudentForm(no);
        model.addAttribute("studentDto", studentDto);

        return nextPage;

    }
    @GetMapping("/modifyParentsForm")
    public String modifyParentsForm(@RequestParam int no, Model model) {
        log.info("modifyParentsForm()");

        String nextPage = "admin/modifyParentsForm";
        ParentsDto parentsDto = memberService.modifyParentsForm(no);
        model.addAttribute("parentsDto", parentsDto);

        return nextPage;

    }
    @PostMapping("/modifyStudentConfirm")
    public String modifyStudentConfirm(StudentDto studentDto, Model model) {
        log.info("modifyStudentConfirm()");

        String nextPage = "admin/modifyStudentResult";
        int result = memberService.modifyStudentConfirm(studentDto);
        model.addAttribute("result", result);

        return nextPage;

    }
    @PostMapping("/modifyParentsConfirm")
    public String modifyParentsConfirm(ParentsDto parentsDto, Model model) {
        log.info("modifyParentsConfirm()");

        String nextPage = "admin/modifyParentsResult";
        int result = memberService.modifyParentsConfirm(parentsDto);
        model.addAttribute("result", result);

        return nextPage;

    }

    /*
     * 교직원 승인 부여 리스트(최고 교직원용 - admin)
     */
    @GetMapping("/admin002ApprovalList")
    public String admin002ApprovalList(HttpSession session, Model model,
                                       @RequestParam(required = false, defaultValue = "0") int no,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_PAGE_NUMBER) int pageNum,
                                       @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_AMOUNT) int amount) {
        log.info("admin002ApprovalList()");
        String nextPage = "admin/admin002ApprovalList";
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        Map<String, Object> map = memberService.admin002ApprovalList(no, loginedMemberDto.getSchool_no(), pageNum, amount);
        model.addAttribute("memberDtos", map.get("memberDtos"));
        model.addAttribute("pageMakerDto", map.get("pageMakerDto"));

        return nextPage;
    }
    /*
     * 최고 교직원 승인 부여 리스트(최고 관리자용 - super_admin)
     */
    @GetMapping("/admin001ApprovalList")
    public String admin001ApprovalList(HttpSession session, Model model,
                                       @RequestParam(required = false, defaultValue = "0") int no,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_PAGE_NUMBER) int pageNum,
                                       @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_AMOUNT) int amount) {
        log.info("admin001ApprovalList()");
        String nextPage = "admin/admin001ApprovalList";
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        Map<String, Object> map = memberService.admin001ApprovalList(no, pageNum, amount);
        model.addAttribute("memberDtos", map.get("memberDtos"));
        model.addAttribute("pageMakerDto", map.get("pageMakerDto"));

        return nextPage;
    }



}
