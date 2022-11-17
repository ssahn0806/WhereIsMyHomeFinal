package com.ssafy.house.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.model.dao.HousedealDAO;
import com.ssafy.house.model.dao.HouseinfoDAO;
import com.ssafy.house.model.dto.HouseDeal;
import com.ssafy.house.model.dto.HouseInfo;

@Service
public class HouseServiceImpl implements HouseService {
	
	@Autowired
	private HousedealDAO housedealDao;
	@Autowired
	private HouseinfoDAO houseinfoDao;
	
	//houseDeal
	//0.거래내역 No로 거래 상세내역 조회
	@Override
	public HouseDeal selectHouseDealByNo(int no) {
		return housedealDao.selectHouseDealByNo(no);
	}
	//1.동코드로부터 거래내역 조회
	@Override
	public List<HouseDeal> selectHouseDealsByDongCode(String dongCode) {
		return housedealDao.selectHouseDealsByDongCode(dongCode);
	}
	//2.아파트명으로부터 거래내역 조회
	@Override
	public List<HouseDeal> selectHouseDealsByAptName(String aptName) {
		return housedealDao.selectHouseDealsByAptName(aptName);
	}
	//houseInfo
	//1.아파트 코드명으로부터 아파트 정보 조회
	@Override
	public HouseInfo selectHouse(int aptCode) {
		return houseinfoDao.selectHouse(aptCode);
	}
	
	// 거래내역 리스트를 Merge Sort
    @Override
	public List<HouseDeal> mergeSortHouseDeals(List<HouseDeal> houseDeals){
        mergeSort(houseDeals, 0, houseDeals.size()-1);
        return houseDeals;
    }
    
    public static void mergeSort(List<HouseDeal> a, int from, int to) {
        if (from == to) {
            return;
        }
        int mid = (from + to) / 2;
        mergeSort(a, from, mid);
        mergeSort(a, mid + 1, to);
        merge(a, from, mid, to);
    }

    public static void merge(List<HouseDeal> a, int from, int mid, int to) {
        int n = to - from + 1;
        String[] b = new String[n];
                int i1 = from;
        int i2 = mid + 1;
        int j = 0;

        while (i1 <= mid && i2 <= to) {
            if (a.get(i1).getDealAmount().compareTo(a.get(i2).getDealAmount()) < 0) {
                b[j] = a.get(i1).getDealAmount();
                i1++;
            } else {
                b[j] = a.get(i2).getDealAmount();
                i2++;
            }
            j++;
        }

        while (i1 <= mid) {
            b[j] = a.get(i1).getDealAmount();
            i1++;
            j++;
        }

        while (i2 <= to) {
            b[j] = a.get(i2).getDealAmount();
            i2++;
            j++;
        }

        for (j = 0; j < n; j++) {
            HouseDeal aHouseDeal = a.get(j);
            aHouseDeal.setDealAmount(b[j]);
            a.set(from + j, aHouseDeal);
        }
    }
}
