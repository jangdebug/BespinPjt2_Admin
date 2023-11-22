package com.oneteam.dormeaseadmin.utils.pagination;

import lombok.Data;

@Data
public class PageMakerDto {

    private int startPage;          // 시작 페이지
    private int endPage;            // 끝 페이지
    private boolean prev, next;     // 이전 페이지 또는 다음 페이지 출력 유/무
    private int total;              // 전체 게시물 개수
    private int totalPage;          // 전체 페이지 수
    private Criteria criteria;      // 현재 페이지, 페이지당 게시물 정보
    private String keyWord;         // 키워드

    public PageMakerDto(Criteria criteria, int total) {

        this.criteria = criteria;
        this.total = total;

        this.endPage = (int) (Math.ceil(criteria.getPageNum() / (double)criteria.getAmount())) * criteria.getAmount();
        this.startPage = this.endPage - criteria.getAmount() + 1;

        int realEnd = (int) (Math.ceil(total * 1.0 / criteria.getAmount()));

        if (realEnd < this.endPage)
            this.endPage = realEnd;

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;

        this.totalPage = total / criteria.getAmount();
        if (total % criteria.getAmount() != 0)
            totalPage += 1;
    }

    public PageMakerDto(String keyWord, Criteria criteria, int total) {

        this.criteria = criteria;
        this.total = total;
        this.keyWord = keyWord;

        this.endPage = (int) (Math.ceil(criteria.getPageNum() / (double)criteria.getAmount())) * criteria.getAmount();
        this.startPage = this.endPage - criteria.getAmount() + 1;

        int realEnd = (int) (Math.ceil(total * 1.0 / criteria.getAmount()));

        if (realEnd < this.endPage)
            this.endPage = realEnd;

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;

        this.totalPage = total / criteria.getAmount();
        if (total % criteria.getAmount() != 0)
            totalPage += 1;
    }

    @Override
    public String toString() {
        return "PageMakerDto{" +
                "startPage=" + startPage +
                ", endPage=" + endPage +
                ", prev=" + prev +
                ", next=" + next +
                ", total=" + total +
                ", totalPage=" + totalPage +
                ", criteria=" + criteria +
                ", keyWord=" + keyWord +
                "}";
    }
}
