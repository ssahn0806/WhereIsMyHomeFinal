package com.ssafy.house.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.model.dto.QnA;
import com.ssafy.house.model.service.QnAService;

@RequestMapping("/api/qnas")
@RestController
public class QnAController{
	
	@Autowired
	private QnAService qnaService;
	@GetMapping()
	private ResponseEntity<List<QnA>> qnaList(){
		List<QnA> list = qnaService.qnaList();
		if(list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
		
		
	}
	
	
	@GetMapping("/{articleNo}")
	private ResponseEntity<QnA> qnaView(@PathVariable int articleNo){
		QnA qna = qnaService.qnaView(articleNo);
		if(qna != null) {
			return ResponseEntity.ok(qna);
		} else {
			return ResponseEntity.notFound().build();
		}
		
		
	}

	
	@PostMapping
	private ResponseEntity<?> insert(@RequestBody QnA qna){
		
		boolean res = qnaService.insert(qna);
		if(res) {
			return ResponseEntity.created(URI.create("/api/qnas/"+qna.getArticleNo())).build();
			
		} else
			return ResponseEntity.internalServerError().build();
		
	}
	@DeleteMapping("/{articleNo}")
	private ResponseEntity<?> delete(@PathVariable int articleNo){
		QnA qna2 = qnaService.qnaView(articleNo);
		
		if(qna2 !=null) {
			boolean res = qnaService.delete(articleNo);
			if(res) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.internalServerError().build();
			}
			
		} else
			return ResponseEntity.notFound().build();
		
	}

	@PutMapping("/{articleNo}")
	private ResponseEntity<?> modify(@RequestBody QnA qna){
		QnA qna2 = qnaService.qnaView(qna.getArticleNo());
		if(qna2 != null) {
			boolean res = qnaService.modify(qna);
			if(res) {
				return ResponseEntity.ok(qna);
			} else {
				return ResponseEntity.internalServerError().build();
			} 
		} else
			return ResponseEntity.notFound().build();
	}
	
}

