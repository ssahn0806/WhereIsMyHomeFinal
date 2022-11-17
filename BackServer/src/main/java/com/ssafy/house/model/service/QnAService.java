package com.ssafy.house.model.service;

import java.util.List;

import com.ssafy.house.model.dto.Board;
import com.ssafy.house.model.dto.QnA;

public interface QnAService {

	List<QnA> qnaList();

	QnA qnaView(int article_no);

	boolean updateHit(int articleNo);

	boolean insert(QnA qna);

	boolean delete(int articleno);

	boolean modify(QnA qna);
	
	
	
}