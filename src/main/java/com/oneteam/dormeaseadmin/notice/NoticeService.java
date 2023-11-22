package com.oneteam.dormeaseadmin.notice;

import com.oneteam.dormeaseadmin.admin.member.MemberDto;
import com.oneteam.dormeaseadmin.utils.UploadFileDto;
import com.oneteam.dormeaseadmin.utils.pagination.Criteria;
import com.oneteam.dormeaseadmin.utils.pagination.PageMakerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class NoticeService {
    private final INoticeMapper noticeMapper;
    public NoticeService(INoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    // 공지사항 게시글 리스트 페이지
    public Map<String, Object> getAllNoticeContent(String schoolNo, String keyWord, String search, int pageNum, int amount) {
        log.info("getAllNoticeContent()");
        Criteria criteria = new Criteria(pageNum, amount);
        Map<String, Object> map = new HashMap<>();
        map.put("keyWord", keyWord);
        map.put("search", search);
        map.put("schoolNo", schoolNo);
        int totalCnt = noticeMapper.selectCountOfNotice(map);
        PageMakerDto pageMakerDto = new PageMakerDto(schoolNo, criteria, totalCnt);
        map.put("pageMakerDto", pageMakerDto);
        List<NoticeDto> noticeDtos = noticeMapper.selectAllNotice(map);
        map.remove("schoolNo");
        map.put("noticeDtos", noticeDtos);

        return map;
    }

    // 공지사항 디테일 페이지
    public Map<String, Object> getDetailNotice(int no) {
        log.info("getDetailNotice()");

        NoticeDto noticeDto = new NoticeDto();
        List<UploadFileDto> uploadedFiles = new ArrayList<>();
        Map<String, Object> noticeAndFilesMap = new HashMap();

        int result = noticeMapper.updateNoticeHit(no);

        if (result > 0) {
            noticeDto = noticeMapper.selectDetailNotice(no);
            uploadedFiles = noticeMapper.selectUploadedFiles(no);
        }
        noticeAndFilesMap.put("noticeDto", noticeDto);
        noticeAndFilesMap.put("uploadedFiles", uploadedFiles);

        return noticeAndFilesMap;
    }

    // 공지사항 작성 컨펌
    public int writeNoticeConfirm(MemberDto loginedMemberDto, NoticeDto noticeDto, List<UploadFileDto> uploadedFileDtos) {
        log.info("writeNoticeConfirm()");
        int result = -1;
        Map<String, Object> noticeDtoMap = new HashMap<>();
        noticeDtoMap.put("loginedMemberDto", loginedMemberDto);
        noticeDtoMap.put("noticeDto", noticeDto);

        if (uploadedFileDtos != null && !uploadedFileDtos.isEmpty()) {
            int currentNoticeNo = noticeMapper.selectCurrentNoticeNo();
            for (int i = 0 ; i < uploadedFileDtos.size() ; i++) {
                uploadedFileDtos.get(i).setNotice_no(currentNoticeNo + 1);
            }
            noticeMapper.insertNewFile(uploadedFileDtos);
        }
        result = noticeMapper.insertNewNotice(noticeDtoMap);

        return result;
    }

    // 상단 고정 게시물 가능여부 체크
    public int checkFixedNotice() {
        log.info("checkFixedNotice()");

        int result = noticeMapper.selectCountOfFixedNotice();

        return result;
    }

    // 공지사항 수정 페이지
    public Map<String, Object> getdetailNoticeForModify(int no) {
        log.info("getdetailNoticeForModify()");
        Map<String, Object> noticeAndFilesMap = new HashMap<>();
        NoticeDto  noticeDto = noticeMapper.selectDetailNotice(no);
        List<UploadFileDto> uploadedFiles = noticeMapper.selectUploadedFiles(no);
        noticeAndFilesMap.put("noticeDto", noticeDto);
        noticeAndFilesMap.put("uploadedFiles", uploadedFiles);

        return null;
    }

    // 공지사항 수정 컨펌
    public int modifyNoticeConfirm(NoticeDto noticeDto, List<UploadFileDto> uploadedFileDtos, List<String> noticeAttachFile) {
        log.info("modifyNoticeConfirm()");
        int result = -1;
        int deleteResult = -1;
        int noticeNoForModify = noticeDto.getNo();
        List<UploadFileDto> uploadedFileCheck = new ArrayList<>();
        uploadedFileCheck = noticeMapper.selectUploadedFiles(noticeNoForModify);

        // 사용자가 수정시 파일을 첨부한 경우
        if (uploadedFileDtos != null && !uploadedFileDtos.isEmpty()) {
            // 기존에 첨부된 파일이 있는 경우
            if (uploadedFileCheck != null && !uploadedFileCheck.isEmpty()) {
                deleteResult = noticeMapper.deleteFilesForModify(noticeNoForModify);
                // 새로 등록된 첨부파일 DB에 등록
                if (deleteResult > 0) {
                    for (int i = 0 ; i < uploadedFileDtos.size() ; i++) {
                        uploadedFileDtos.get(i).setNotice_no(noticeNoForModify);
                    }
                    noticeMapper.insertNewFile(uploadedFileDtos);
                }
                // 기존에 첨부된 파일이 없는 경우 ==> 새로 등록된 첨부파일 DB에 등록
            } else {
                for (int i = 0 ; i < uploadedFileDtos.size() ; i++) {
                    uploadedFileDtos.get(i).setNotice_no(noticeNoForModify);
                }
                noticeMapper.insertNewFile(uploadedFileDtos);
            }

            // 사용자가 수정시 파일을 첨부하지 않은 경우
        } else {
            // 사용자가 기존의 첨부파일을 삭제한 경우
            if (noticeAttachFile == null) {
                noticeMapper.deleteFilesForModify(noticeNoForModify);
            }
        }
        result = noticeMapper.updateNotice(noticeDto);

        return result;
    }

    // 게시글 삭제 컴펌
    public int deleteNoticeConfirm(int no) {
        log.info("deleteNoticeConfirm()");

        return noticeMapper.updateNoticeForDelete(no);
    }

}
