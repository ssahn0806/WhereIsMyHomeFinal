package com.ssafy.house.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.QnA;

@Mapper
public interface QnADAO {

	List<QnA> qnaList();

	QnA qnaView(int article_no);

	int updateHit(int articleNo);

	int insert(QnA qna);

	int delete(int articleno);

	int modify(QnA qna);

}