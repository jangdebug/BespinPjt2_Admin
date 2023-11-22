package com.oneteam.dormeaseadmin.notice;

import com.oneteam.dormeaseadmin.admin.member.MemberDto;
import com.oneteam.dormeaseadmin.utils.UploadFileDto;
import com.oneteam.dormeaseadmin.utils.UploadFileService;
import com.oneteam.dormeaseadmin.utils.pagination.PageDefine;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final UploadFileService uploadFileService;
    public NoticeController (NoticeService noticeService, UploadFileService uploadFileService) {
        this.noticeService = noticeService;
        this.uploadFileService = uploadFileService;
    }

    /*
     * 공지사항 게시글 리스트 페이지
     */
    @GetMapping("/noticeListForm")
    public String noticeListForm(HttpSession session, Model model,
                                 @RequestParam(value="keyWord", required = false) String keyWord,
                                 @RequestParam(value="search", required = false) String search,
                                 @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_PAGE_NUMBER) int pageNum,
                                 @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_LEAVEPASS_AMOUNT) int amount) {
        log.info("noticeListForm()");
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        String schoolNo = null;
        if (loginedMemberDto != null) {
            schoolNo = loginedMemberDto.getSchool_no();
        }
        Map<String, Object> resultMap = noticeService.getAllNoticeContent(schoolNo,keyWord, search, pageNum, amount);

        String nextPage = "notice/noticeListForm";
        model.addAttribute("pageMakerDto", resultMap.get("pageMakerDto"));
        model.addAttribute("search", resultMap.get("search"));
        model.addAttribute("keyWord", resultMap.get("keyWord"));
        model.addAttribute("noticeDtos", resultMap.get("noticeDtos"));

        return nextPage;
    }

    /*
     * 공지사항 디테일 페이지
     */
    @GetMapping("/detailNoticeForm")
    public String detailNoticeForm(@RequestParam("no") int no, Model model) {
        log.info("detailNoticeForm()");
        String nextPage = "notice/detailNoticeForm";
        Map<String, Object> noticeAndFilesMap = noticeService.getDetailNotice(no);
        model.addAttribute("noticeAndFilesMap", noticeAndFilesMap);

        return nextPage;
    }

    /*
     * 공지사항 작성 페이지
     */
    @GetMapping("/writeNoticeForm")
    public String writeNoticeForm(HttpSession session, Model model) {
        log.info("writeNoticeForm()");
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        String nextPage = "notice/writeNoticeForm";

        return nextPage;
    }

    /*
     * 상단 고정 게시물 가능여부 체크
     */
    @GetMapping("/checkFixedNotice")
    @ResponseBody
    public Object checkFixedNotice() {
        log.info("checkFixedNotice()");

        int fixedNotice = noticeService.checkFixedNotice();

        return fixedNotice >= 5 ? false : true;
    }

    /*
     * 공지사항 작성 컨펌
     */
    @PostMapping("/writeNoticeConfirm")
    public String writeNoticeConfirm(@RequestParam(value = "files", required = false)List<MultipartFile> files,
                                     HttpSession session,
                                     Model model,
                                     NoticeDto noticeDto) {
        log.info("writeNoticeConfirm()");
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        int result = -1;

        try {
            List<UploadFileDto> uploadedFileDtos = new ArrayList<>();
            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    String fileOriName = file.getOriginalFilename();
                    // SAVE FILES
                    if (fileOriName != "") {
                        UploadFileDto uploadedFileDto = uploadFileService.upload(loginedMemberDto.getId(), file);
                        uploadedFileDto.setBoard_attach_file(uploadedFileDto.getBoard_attach_file());
                        if (uploadedFileDto != null) {
                            // 업로드된 파일 정보를 리스트에 추가
                            uploadedFileDtos.add(uploadedFileDto);
                        }
                    }
                }
            }
            result = noticeService.writeNoticeConfirm(loginedMemberDto, noticeDto, uploadedFileDtos);
        } catch(Exception e) {
            e.printStackTrace();
        }
        String nextPage = "notice/writeContentResult";
        model.addAttribute("result", result);

        return nextPage;
    }

    /*
     * 공지사항 수정 페이지
     */
    @PostMapping("/modifyNoticeForm")
    public String modifyNoticeForm(@RequestParam(value = "notice_no", required = false) int no, Model model) {
        log.info("modifyNoticeForm()");
        String nextPage = "notice/modifyNoticeForm";
        Map<String, Object> noticeAndFilesMap = noticeService.getdetailNoticeForModify(no);
        model.addAttribute("noticeAndFilesMap", noticeAndFilesMap);

        return nextPage;
    }

    /*
     * 공지사항 수정 컨펌
     */
    @PostMapping("/modifyNoticeConfirm")
    public String modifyNoticeConfirm(@RequestParam(value = "files", required = false) List<MultipartFile> files,
                                      @RequestParam(value = "notice_attach_file", required = false) List<String> notice_attach_file,
                                      HttpSession session,
                                      NoticeDto noticeDto,
                                      Model model) {
        log.info("modifyNoticeConfirm()");
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        String nextPage = "notice/modifyNoticeResult";
        int result = -1;
        if (loginedMemberDto.getNo() == noticeDto.getAdmin_no()) {
            try {
                List<UploadFileDto> uploadFileDtos = new ArrayList<>();
                if (files != null && !files.isEmpty()) {
                    for (MultipartFile file : files) {
                        String fileOriName = file.getOriginalFilename();
                        // SAVE FILES IN LOCAL
                        if (fileOriName != "") {
                            UploadFileDto uploadFileDto = uploadFileService.upload(loginedMemberDto.getId(), file);
                            uploadFileDto.setNotice_attach_file(uploadFileDto.getNotice_attach_file());
                            if (uploadFileDto != null) {
                                // 업로드된 파일 정보를 리스트에 담기
                                uploadFileDtos.add(uploadFileDto);
                            }
                        }
                    }
                }
                result = noticeService.modifyNoticeConfirm(noticeDto, uploadFileDtos, notice_attach_file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("result", result);

        return nextPage;
    }

    /*
     * 게시글 삭제 컴펌
     */
    @GetMapping("/deleteNoticeConfirm")
    public String deleteNoticeConfirm(@RequestParam(value = "notice_no", required = false) int no, Model model) {
        log.info("deleteNoticeConfirm()");
        String nextPage = "notice/deleteNoticeResult";
        int result = noticeService.deleteNoticeConfirm(no);
        model.addAttribute("result", result);

        return nextPage;
    }
}
