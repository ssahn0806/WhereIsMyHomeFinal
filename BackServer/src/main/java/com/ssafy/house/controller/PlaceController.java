package com.ssafy.house.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.model.dto.Place;
import com.ssafy.house.model.service.PlaceService;

@RequestMapping("/api/places")
@RestController
public class PlaceController {

	@Autowired
	private PlaceService placeService;

	@GetMapping("/{dongCode}/lists/{categoryCode}")
	public ResponseEntity<?> doSelectPlaces(@PathVariable String dongCode, @PathVariable String categoryCode, @RequestParam(defaultValue ="-1") int limit) {

		Map<String, Object> conditions = new HashMap<>();
		conditions.put("dongCode", dongCode);
		conditions.put("categoryCode", categoryCode);
		if(limit!=-1) {
			conditions.put("limit", limit);			
		}
		System.out.println("limit ---->"+conditions.containsKey("limit"));
		List<Place> places = placeService.selectPlaces(conditions);
		if (places != null) {
			return ResponseEntity.ok(places);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{dongCode}/{categoryCode}")
	public ResponseEntity<?>  doCountPlaces(@PathVariable String dongCode, @PathVariable String categoryCode) {

		Map<String, Object> conditions = new HashMap<>();
		conditions.put("dongCode", dongCode);
		conditions.put("categoryCode", categoryCode);
		HashMap<String, Integer> placeMap = new HashMap<>();
		
		int cnt = placeService.countPlaces(conditions);
		
		switch(categoryCode) {
		case "Q12":
			placeMap.put("cafe",cnt);
			break;		
		case "N03":
			placeMap.put("theater",cnt);
			break;
		}

		return ResponseEntity.ok(placeMap);
	}

}
