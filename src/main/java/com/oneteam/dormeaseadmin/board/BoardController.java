package com.oneteam.dormeaseadmin.board;

import com.oneteam.dormeaseadmin.admin.member.MemberDto;
import com.oneteam.dormeaseadmin.utils.UploadFileDto;
import com.oneteam.dormeaseadmin.utils.UploadFileService;
import com.oneteam.dormeaseadmin.utils.pagination.PageDefine;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/board")
@Tag(name = "Board", description = "자유게시판 API")
public class BoardController {
    private final BoardService boardService;
    private final UploadFileService uploadFileService;
    public BoardController(BoardService boardService, UploadFileService uploadFileService) {
        this.boardService = boardService;
        this.uploadFileService = uploadFileService;
    }

    /**
     * [VIEW] 자유 게시판 리스트
     *
     * @param keyWord 키워드
     * @param search 키워드
     * @param pageNum 페이지 넘버
     * @param amount 한 페이지 당 개수
     * @return 페이지
     */

    /*
     * 자유 게시판 게시글 리스트 페이지
     */
    @GetMapping("/freeBoardListForm")
    @Operation(summary = "자유 게시판 게시글 리스트 페이지", description = "자유 게시판 게시글 리스트 페이지", tags = {"View"})
    public String freeBoardListForm(HttpSession session, Model model,
                                    @RequestParam(value="keyWord", required = false) String keyWord,
                                    @RequestParam(value="search", required = false) String search,
                                    @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_USER_PAGE_NUMBER) int pageNum,
                                    @RequestParam(value="amount", required = false, defaultValue = PageDefine.DEFAULT_USER_AMOUNT) int amount) {
        log.info("freeBoardListForm()");
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        String schoolNo = null;
        if (loginedMemberDto != null) {
            schoolNo = loginedMemberDto.getSchool_no();
        }
        Map<String, Object> resultMap = boardService.getAllFreeBoardContent(schoolNo,keyWord, search, pageNum, amount);
        String nextPage = "board/freeBoardListForm";
        model.addAttribute("boardDtos", resultMap.get("boardDtos"));
        model.addAttribute("pageMakerDto", resultMap.get("pageMakerDto"));
        model.addAttribute("search", resultMap.get("search"));
        model.addAttribute("keyWord", resultMap.get("keyWord"));

        return nextPage;
    }

    /*
     * 게시글 디테일 페이지
     */
    @GetMapping("/detailContentForm")
    public String detailContentForm(@RequestParam("no") int no, Model model, HttpSession session) {
        log.info("detailContentForm()");
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        String nextPage = "board/detailContentForm";
        Map<String, Object> boardAndReplyMap = boardService.getDetailContent(no);
        model.addAttribute("boardAndReplyMap", boardAndReplyMap);

        return nextPage;
    }

    /*
     * 게시글 작성 페이지
     */
    @GetMapping("/writeContentForm")
    public String writeContentForm(HttpSession session, Model model) {
        log.info("writeContentForm()");
        String nextpage = "board/writeContentForm";

        return nextpage;
    }

    /*
     * 게시글 작성 컨펌
     */
    @PostMapping("/writeContentConfirm")
    @Operation(summary = "게시글 작성 확인", description = "게시글 작성 확인")
    public String writeContentConfirm(@RequestParam(value = "files", required = false)  List<MultipartFile> files,
                                      HttpSession session,
                                      Model model,
                                      BoardDto boardDto) {
        log.info("writeContentConfirm()");
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
            result = boardService.writeContentConfirm(loginedMemberDto, boardDto, uploadedFileDtos);
        } catch(Exception e) {
            e.printStackTrace();
        }
        // 업로드한 파일들을 처리
        String nextpage = "board/writeContentResult";
        model.addAttribute("result", result);

        return nextpage;
    }

    /*
     * 게시글 수정 페이지
     */
    @PostMapping("/modifyContentForm")
    public String modifyContentForm(@RequestParam(value = "board_no", required = false) int no, Model model) {
        log.info("modifyContentForm()");
        String nextPage = "board/modifyContentForm";
        Map<String, Object> boardAndReplyMap = boardService.getdetailContentForModify(no);
        model.addAttribute("boardAndReplyMap", boardAndReplyMap);

        return nextPage;
    }

    /*
     * 게시글 수정 컨펌
     */
    @PostMapping("/modifyContentConfirm")
    public String modifyContentConfirm(@RequestParam(value = "files", required = false)  List<MultipartFile> files,
                                       @RequestParam(value = "board_attach_file", required = false) List<String> board_attach_file,
                                       HttpSession session,
                                       BoardDto boardDto,
                                       Model model) {
        log.info("modifyContentConfirm()");
        MemberDto loginedMemberDto = (MemberDto) session.getAttribute("loginedMemberDto");
        String nextPage = "board/modifyContentResult";
        int result = -1;
        if (loginedMemberDto.getNo() == boardDto.getStudent_no()) {
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
                result = boardService.modifyContentConfirm(boardDto, uploadedFileDtos, board_attach_file);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("result", result);

        return nextPage;
    }

    /*
     * 게시글 삭제 컨펌
     */
    @GetMapping("/deleteContentConfirm")
    public String deleteContentConfirm(@RequestParam(value = "board_no", required = false) int no, Model model) {
        log.info("deleteContentConfirm()");
        String nextPage = "board/deleteContentResult";
        int result = boardService.deleteContentConfirm(no);
        model.addAttribute("result", result);

        return nextPage;
    }
}
