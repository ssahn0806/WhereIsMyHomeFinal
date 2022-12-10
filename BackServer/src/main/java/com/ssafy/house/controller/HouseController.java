package com.ssafy.house.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.house.model.dto.BaseAddress;
import com.ssafy.house.model.dto.DataInfo;
import com.ssafy.house.model.dto.DongCode;
import com.ssafy.house.model.dto.GugunCode;
import com.ssafy.house.model.dto.HouseDeal;
import com.ssafy.house.model.dto.HouseInfo;
import com.ssafy.house.model.dto.PageInfo;
import com.ssafy.house.model.dto.SidoCode;
import com.ssafy.house.model.service.AddressService;
import com.ssafy.house.model.service.HouseService;

@RequestMapping("/api/houses")
@RestController
public class HouseController{
	
	@Autowired
	private HouseService houseService;
	@Autowired
	private AddressService addressService;

	@GetMapping("/list")
	public ResponseEntity<List<SidoCode>> doListInit() {
		List<SidoCode> sidoCodes = addressService.selectSidoCodes();
		if(sidoCodes != null) {
			return ResponseEntity.ok(sidoCodes);
		} else
			return ResponseEntity.internalServerError().build();
		
	}
	
//	public PageInfo doFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		//서버 내부적으로 카카오 API를 비동기로 호출해서 그 데이터를 request담아서 favorite으로 보낸다.
//		/house/favorite.do
	//return new PageInfo(true,"/House/favorite.jsp");
//	}

	
	@GetMapping("/{aptCode}")
	public ResponseEntity<?> doHouseDetail(@PathVariable int aptCode) {
		// no -> 거래내역 상세 조회
		//HouseDeal dealInfo = houseService.selectHouseDealByNo(Integer.parseInt(request.getParameter("no")));
		// aptCode -> 아파트 정보 상세 조회
//		HouseInfo houseInfo = houseService.selectHouse(Integer.parseInt(request.getParameter("aptCode")));
//		request.setAttribute("dealInfo", dealInfo);
//		request.setAttribute("houseInfo", houseInfo);
//		return new PageInfo(true,"/House/detail.jsp");
		HouseInfo houseInfo = houseService.selectHouse(aptCode);
		if(houseInfo != null) {
			return ResponseEntity.ok(houseInfo);	
		} else
			return ResponseEntity.internalServerError().build();
		
	}
	

	@GetMapping("/gugun/{sidoCode}")
	public ResponseEntity<List<GugunCode>> doSearchGuguns(@PathVariable String sidoCode){
		List<GugunCode> gugunCodes = addressService.selectGugunCodes(sidoCode);
		if(gugunCodes != null) {
			return ResponseEntity.ok(gugunCodes);
		} else
			return ResponseEntity.notFound().build();
		
		
	}
	
	//선택된 구군명으로부터 동 데이터 목록 추출 및 전송
	@GetMapping("/dong/{gugunCode}")
	public ResponseEntity<List<DongCode>> doSearchDongs(@PathVariable String gugunCode){
		List<DongCode> dongCodes = addressService.selectDongCodes(gugunCode);
		if(dongCodes != null) {
			return ResponseEntity.ok(dongCodes);
		} else
			return ResponseEntity.notFound().build();
				
	}

	//동코드로부터 일치하는 거래내역 정보 전송
	@GetMapping("/housedeals/dong/{dongCode}")
	public ResponseEntity<List<HouseDeal>> searchHousesBycode(@PathVariable String dongCode){
		List<HouseDeal> houseDeals = houseService.selectHouseDealsByDongCode(dongCode);
		if(houseDeals != null) {
			return ResponseEntity.ok(houseDeals);
		}
			return ResponseEntity.notFound().build();
	}
	
	//아파트명이 포함된 거래내역 정보 전송
	@GetMapping("/housedeals/name/{aptName}")
	public ResponseEntity<List<HouseDeal>> searchHousesByname(@PathVariable String aptName) {
		List<HouseDeal> houseDeals = houseService.selectHouseDealsByAptName(aptName);
		if(houseDeals != null) {
			return ResponseEntity.ok(houseDeals);
		}
			return ResponseEntity.notFound().build();
	}
	
	//아파트코드로 아파트 상세 정보 전송
	@GetMapping("/houseinfos/name/{aptCode}")
	public ResponseEntity<?> searchHouseInfoByname(@PathVariable int aptCode) {
		HouseInfo houseInfos = houseService.selectHouse(aptCode);
		if(houseInfos != null) {
			return ResponseEntity.ok(houseInfos);
		} else
			return ResponseEntity.notFound().build();
		
	}
	@GetMapping("/fullname/{dongCode}")
	public ResponseEntity<?> doFullName(@PathVariable String dongCode) {
		BaseAddress fullName = addressService.selectFullName(dongCode);
		if(fullName != null) {
			return ResponseEntity.ok(fullName);
		} else
			return ResponseEntity.notFound().build();
		
	
	}
	@GetMapping("/coordsMap/{dongCode}")
	public ResponseEntity<Map<String,String>> doCoords(@PathVariable String dongCode){
		Map<String,String> coordsMap = addressService.selectLatLng(dongCode);
		if(coordsMap != null) {
			return ResponseEntity.ok(coordsMap);
		} else
			return ResponseEntity.notFound().build();
	}
	
}
