package com.ssafy.house.model.service;

import java.util.List;

import com.ssafy.house.model.dto.Board;

public interface BoardService {

	List<Board> noticeList();

	Board noticeView(int article_no);

	boolean updateHit(int articleNo);

	boolean insert(Board board);

	boolean delete(int articleNo);

	boolean modify(Board board);

}