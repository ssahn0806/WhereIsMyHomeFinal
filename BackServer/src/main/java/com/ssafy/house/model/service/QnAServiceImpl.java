package com.ssafy.house.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.model.dao.QnADAO;
import com.ssafy.house.model.dto.QnA;

@Service
public class QnAServiceImpl implements QnAService {
	
	private QnADAO qnaDao;
	
	@Autowired
	public QnAServiceImpl(QnADAO qnaDao) {
		super();
		this.qnaDao = qnaDao;
	}
	@Override
	public List<QnA> qnaList() {
		return qnaDao.qnaList();
	}

	@Override
	public QnA qnaView(int article_no) {
		return qnaDao.qnaView(article_no);
	}
	
	@Override
	public boolean updateHit(int articleNo) {
		return qnaDao.updateHit(articleNo) > 0;
	}
	
	@Override
	public boolean insert(QnA qna) {
		return qnaDao.insert(qna) > 0 ;
	}

	@Override
	public boolean delete(int articleNo)  {
		return qnaDao.delete(articleNo) > 0; 
	}

	@Override
	public boolean modify(QnA qna) {
		return qnaDao.modify(qna) > 0;
	}
	
}
