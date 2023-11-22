package com.oneteam.dormeaseadmin.board.reply;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IReplyMapper {

    int insertReply(ReplyDto replyDto);

    List<ReplyDto> selectReplies(int no);
}
