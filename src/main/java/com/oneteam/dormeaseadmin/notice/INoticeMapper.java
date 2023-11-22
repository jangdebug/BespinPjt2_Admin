package com.oneteam.dormeaseadmin.notice;

import com.oneteam.dormeaseadmin.utils.UploadFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface INoticeMapper {
    List<NoticeDto> selectAllNotice(Map<String, Object> map);

    int selectCountOfNotice(Map<String, Object> map);

    int selectCountOfFixedNotice();

    int updateNoticeHit(int no);

    NoticeDto selectDetailNotice(int no);

    List<UploadFileDto> selectUploadedFiles(int no);

    int selectCurrentNoticeNo();

    void insertNewFile(List<UploadFileDto> uploadedFileDtos);

    int insertNewNotice(Map<String, Object> noticeDtoMap);

    int deleteFilesForModify(int noticeNoForModify);

    int updateNotice(NoticeDto noticeDto);

    int updateNoticeForDelete(int no);
}
