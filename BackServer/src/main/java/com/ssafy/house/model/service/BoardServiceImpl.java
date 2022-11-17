package com.ssafy.house.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.model.dao.BoardDAO;
import com.ssafy.house.model.dto.Board;

@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardDAO boardDao;
	
	@Autowired
	public BoardServiceImpl(BoardDAO boardDao) {
		super();
		this.boardDao = boardDao;
	}
	@Override
	public List<Board> noticeList() {
		return boardDao.noticeList();
	}

	@Override
	public Board noticeView(int article_no) {
		return boardDao.noticeView(article_no);
	}
	
	@Override
	public boolean updateHit(int articleNo) {
		return boardDao.updateHit(articleNo) > 0;
	}
	
	@Override
	public boolean insert(Board board) {
		return boardDao.insert(board) > 0 ;
	}

	@Override
	public boolean delete(int articleNo)  {
		return boardDao.delete(articleNo) > 0; 
	}

	@Override
	public boolean modify(Board board) {
		return boardDao.modify(board) > 0;
	}
	
}
