package com.ssafy.house.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.model.dao.BaseaddressDAO;
import com.ssafy.house.model.dao.DongCodeDAO;
import com.ssafy.house.model.dao.GugunCodeDAO;
import com.ssafy.house.model.dao.SidoCodeDAO;
import com.ssafy.house.model.dto.BaseAddress;
import com.ssafy.house.model.dto.DongCode;
import com.ssafy.house.model.dto.GugunCode;
import com.ssafy.house.model.dto.SidoCode;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private SidoCodeDAO sidoDao;
	@Autowired
	private GugunCodeDAO gugunDao;
	@Autowired
	private DongCodeDAO dongDao;
	@Autowired
	private BaseaddressDAO addressDao;
	
	//1.시도 코드로 부터 시도지역명 조회
	@Override
	public SidoCode selectSidoCode(String code) {
		return sidoDao.selectSidoCode(code);
	}
	//2.모든 시도 내역 조회
	@Override
	public List<SidoCode> selectSidoCodes() {
		return sidoDao.selectSidoCodes();
	}
	
	//1.시도 코드로부터 일치하는 구군내역 조회
	@Override
	public List<GugunCode> selectGugunCodes(String sidoCode) {
		return gugunDao.selectGugunCodes(sidoCode);
	}
	
	//1.군구 코드로 부터 동코드 목록 조회
	@Override
	public List<DongCode> selectDongCodes(String gugunCode){
		return dongDao.selectDongCodes(gugunCode);
	}
	
	//1.시도명,구군명,동명으로 동코드 정보 반환
	@Override
	public BaseAddress selectBaseaddress(Map<String, Object> codes) {
		return addressDao.selectBaseaddress(codes);
	}
	
	//2.동코드로부터 시군구명 풀네임 반환
	@Override
	public BaseAddress selectFullName(String dongCode) {
		return addressDao.selectFullName(dongCode);
	}
	
	//3.동코드로부터 lat,lng 반환
	@Override
	public Map<String,String> selectLatLng(String dongCode){
		return addressDao.selectLatLng(dongCode);
	}
	
}
