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

import com.ssafy.house.model.dto.Board;
import com.ssafy.house.model.service.BoardService;

@RequestMapping("/api/boards")
@RestController
public class BoardController{
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping()
	private ResponseEntity<List<Board>> noticeList(){
		List<Board> list = boardService.noticeList();
		if(list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
		
		
	}
	
	
	@GetMapping("/{articleNo}")
	private ResponseEntity<Board> noticeView(@PathVariable int articleNo){
		Board board = boardService.noticeView(articleNo);
		if(board != null) {
			return ResponseEntity.ok(board);
		} else {
			return ResponseEntity.notFound().build();
		}
		
		
	}

	
	@PostMapping
	private ResponseEntity<?> insert(@RequestBody Board board){
		
		boolean res = boardService.insert(board);
		if(res) {
			return ResponseEntity.created(URI.create("/api/boards/"+board.getArticleNo())).build();
			
		} else
			return ResponseEntity.internalServerError().build();
		
	}
	@DeleteMapping("/{articleNo}")
	private ResponseEntity<?> delete(@PathVariable int articleNo){
		Board board2 = boardService.noticeView(articleNo);
		
		if(board2 !=null) {
			boolean res = boardService.delete(articleNo);
			if(res) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.internalServerError().build();
			}
			
		} else
			return ResponseEntity.notFound().build();
		
	}

	@PutMapping("/{articleNo}")
	private ResponseEntity<?> modify(@RequestBody Board board){
		Board board2 = boardService.noticeView(board.getArticleNo());
		if(board2 != null) {
			boolean res = boardService.modify(board);
			if(res) {
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.internalServerError().build();
			} 
		} else
			return ResponseEntity.notFound().build();
	}
	
}

