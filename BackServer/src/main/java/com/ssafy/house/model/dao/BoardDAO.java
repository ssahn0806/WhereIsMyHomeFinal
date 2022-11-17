package com.ssafy.house.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.Board;

@Mapper
public interface BoardDAO {

	List<Board> noticeList();

	Board noticeView(int article_no);

	int updateHit(int articleNo);

	int insert(Board board);

	int delete(int articleno);

	int modify(Board board);

}