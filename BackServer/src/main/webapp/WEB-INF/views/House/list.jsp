<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/nav.jsp"%>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=88d45ac53ce90e79d08d138faff11149"></script>
	<!-- content -->
	<div class="container h-80 flex-row"
		style="height: 83.6vh; overflow: scroll">
		<br/>
		<div class="d-flex flex-row justify-content-around">
			<div class="dropdown">
				<select class="form-select" id="sido"
					aria-label="Default select example">
					<option name="province" selected>광역시/도</option>
					<c:forEach var="sidoCode" items="${sidoCodes}">
						<option name="province" value="${sidoCode.sidoCode}">${sidoCode.sidoName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="dropdown">
				<select class="form-select" id="gugun"
					aria-label="Default select example">
					<option name="city" selected>군/구</option>
				</select>
			</div>
			<div class="dropdown">
				<select class="form-select" id="dong"
					aria-label="Default select example">
					<option name="viliage" selected>동</option>
				</select>
			</div>
			<div>
				<button type="button" class="btn btn-success" id="searchHouse">
					조회</button>
				<button type="button" class="btn btn-warning" id="registfavorite">
					관심지역 등록</button>
			</div>
		</div>
		<br/>
		<div class="d-flex flex-row justify-content-end" style="margin:0 100px;">
			<input type="text" placeholder="아파트명을 입력해주세요." id="aptName" />
			<button type="button" class="btn btn-primary" id="searchName">
				조회</button>
		</div>
		<br />
		<div class="container h-80 flex-row"
			style="height: 83.6vh; overflow: scroll">
			<br /> <br />
			<div class="row" style="width: 100%; height: 100%">
				<div class="col-4">
					<h3 class="text-center text">주택 매매 거래정보</h3>
					<button type="button" class="btn btn-danger" id="sortPrice">
					금액 낮은 순</button>					
					<div style="width: 100%; height: 650px; overflow: auto">
						<table class="table" style="width: 100%">
							<thead></thead>
							<tbody id="apartList" style="overflow: scroll">
								<tr>
									<td class="text-center" colspan="4">지역을 선택해주세요!</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-8">
					<div id="map" style="width: 100%; height: 80%;"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="../resources/apartment.js"> </script>
	<script>
	var apartList;
	document.querySelector("#registfavorite").addEventListener("click",function(){
		let sidoSel = document.querySelector("#sido");
		if(sidoSel.options[sidoSel.selectedIndex].text == "광역시/도"){
			alert("시도를 선택하세요");
			return;
		}
		let gugunSel = document.querySelector("#gugun");
		if(gugunSel.options[gugunSel.selectedIndex].text == "군/구"){
			alert("구/군을 선택하세요");
			return;
		}
		let dongSel = document.querySelector("#dong");
		if(dongSel.options[dongSel.selectedIndex].text == "동"){
			alert("동을 선택하세요");
			return;
		}
		
		let dongCode = dongSel.options[dongSel.selectedIndex].value;
		console.log(dongCode,typeof(dongCode));
		location.href="${root}/user/favorite.do?dongCode="+dongCode;
		
	})
	document.querySelector("#searchName").addEventListener("click",function(){
		let aptName = document.querySelector("#aptName").value;
		if(aptName === "") return;
		fetch("${root}/house/rest/searchname.do?aptName="+aptName)
		.then((response)=>response.json())
		.then((data)=> addList(data));
	});
    //조회 버튼을 누르면 행정동코드를 이용해서 리스트를 받아와서 갱신하기
    document.querySelector("#searchHouse").addEventListener("click", function() {
    	let dongsel = document.querySelector("#dong");
    	let dongCode = dongsel.options[dongsel.selectedIndex].value;
    	fetch("${root}/house/rest/searchcode.do?dongCode=" + dongCode)
    		.then((response) => response.json())
    		.then((data) => addList(data));
    });
    // 시도가 바뀌면 구군정보 얻기.
    document.querySelector("#sido").addEventListener("change", function() {
    	if (this[this.selectedIndex].value) {
    		let regcode = this[this.selectedIndex].value;
    		sendRequest("sidoCode", "gugun", regcode);
    	} else {
    		initOption("gugun");
    		initOption("dong");
    	}
    });

    document.querySelector("#gugun").addEventListener("change", function() {
    	if (this[this.selectedIndex].value) {
    		let regcode = this[this.selectedIndex].value;
    		sendRequest("gugunCode", "dong", regcode);
    	} else {
    		initOption("dong");
    	}
    });

    
    document.querySelector("#sortPrice").addEventListener("click",function(){
    	if(apartList != undefined)
    		sortList();
    })
    function sortList(){
    	console.log(typeof(apartList[0].dealAmount),typeof(apartList[0].dealAmount.trim()), typeof(parseInt(apartList[0].dealAmount.trim())),apartList[0].dealAmount.trim(),parseFloat(apartList[0].dealAmount.trim()));
    	apartList.sort((a,b)=>{
    		let adealArr = a.dealAmount.split(",");
    		let adealAmount = '';
    		for(let i=0;i<adealArr.length;i++) adealAmount+=adealArr[i];
    		let bdealArr = b.dealAmount.split(",");
    		let bdealAmount = '';
    		for(let i=0;i<bdealArr.length;i++) bdealAmount+=bdealArr[i];
    		if(adealAmount > bdealAmount){
    			return 1;
    		}
    		
    		if(adealAmount < bdealAmount){
    			return -1;
    		}
   			return 0;
    	});
    	updateList();
    }
    
    function updateList(){
    	let tbody = document.querySelector("#apartList");
    	tbody.innerHTML = '';
    	for(let i=0;i<apartList.length;i++){
    		console.log(apartList[i]);
			let tr = document.createElement("tr");
			let td = document.createElement("td");
			let aptName = document.createElement("h6");
			aptName.textContent = apartList[i].aptName;
			let aptSize = document.createElement("p");
			aptSize.textContent = `전용면적 : ` + apartList[i].area;
			let aptPrice = document.createElement("p");
			aptPrice.textContent = `거래금액 : ` + apartList[i].dealAmount.trim() + `(천원)`;
			let transDate = document.createElement("p");
			transDate.textContent = `거래일자 : ` + apartList[i].dealYear + `/` + apartList[i].dealMonth + `/` + apartList[i].dealDay;
			td.appendChild(aptName);
			td.appendChild(aptSize);
			td.appendChild(aptPrice);
			td.appendChild(transDate);
			tr.appendChild(td);
	    	tbody.appendChild(tr);
    	}
    }

    function addList(data) {
    	apartList = data;
    	var map = new kakao.maps.Map(container, options);
    	let tbody = document.querySelector("#apartList");
    	tbody.innerHTML = "";

    	if (data.length == 0) {
    		let tr = document.createElement("tr");
    		let td = document.createElement("td");
    		td.setAttribute("class","text-center");
    		td.textContent = "조회된 데이터가 없습니다.";
    		tr.appendChild(td);
    		tbody.appendChild(tr);
    	}
    	else {
    		let setArr = new Set();
    		for (let i = 0; i < data.length; i++) {
    			let item = data[i];
    			let tr = document.createElement("tr");
    			fetch("${root}/house/rest/searchinfo.do?aptName=" + item.aptCode)
    				.then((response) => response.json())
    				.then((info) => {
    					if (i == 0) {
    						var moveLatLon = new kakao.maps.LatLng(info.lat, info.lng);
    						map.setLevel(7);
    						map.setCenter(moveLatLon);
    					}
    					let td = document.createElement("td");
    					let aptName = document.createElement("h6");
    					aptName.textContent = info.aptName;
    					apartList[i].aptName = info.aptName;
    					let aptSize = document.createElement("p");
    					aptSize.textContent = `전용면적 : ` + item.area;
    					let aptPrice = document.createElement("p");
    					aptPrice.textContent = `거래금액 : ` + item.dealAmount + `(천원)`;
    					let transDate = document.createElement("p");
    					transDate.textContent = `거래일자 : ` + item.dealYear + `/` + item.dealMonth + `/` + item.dealDay;
    					td.appendChild(aptName);
    					td.appendChild(aptSize);
    					td.appendChild(aptPrice);
    					td.appendChild(transDate);
    					tr.appendChild(td);

    					/* tr.addEventListener("click", function() {
    						location.href = "${root}/house/detail.do?no=" + item.no + "&aptCode=" + item.aptCode;
    					}); */

    					var markerPosition = new kakao.maps.LatLng(info.lat, info.lng);
						setArr.add(JSON.stringify(markerPosition));
		    			if(i==data.length-1){
				    		for (let value of setArr){
				    			var latlng = JSON.parse(value);
				    			console.log(latlng);
		    					var markerPosition = new kakao.maps.LatLng(latlng.Ma,latlng.La);
		    					var marker = new kakao.maps.Marker({
		    					    position: markerPosition
		    					});

		    					// 마커가 지도 위에 표시되도록 설정합니다
		    					marker.setMap(map);
				    		}
		    				
		    			}
    				})
    			tbody.appendChild(tr);

    		}
    		

    	}
    }
    function sendRequest(reqcode, rescode, regcode) {
    	const url = "${root}/house/rest/search" + rescode + ".do";
    	let params = reqcode + "=" + regcode;
    	fetch(url + "?" + params)
    		.then((response) => response.json())
    		.then((data) => addOption(rescode, data));
    }

    function addOption(selid, data) {
    	let opt = ``;
    	initOption(selid);
    	switch (selid) {
    		case "gugun":
    			opt += `<option value="">구군선택</option>`;
    			for (let i = 0; i < data.length; i++) {
    				opt += `<option value=` + data[i].gugunCode + `>` + data[i].gugunName + `</option>`
    			}
    			break;
    		case "dong":
    			opt += `<option value="">동선택</option>`;
    			for (let i = 0; i < data.length; i++) {
    				opt += `<option value=` + data[i].dongCode + `>` + data[i].dongName + `</option>`
    			}
    			break;
    	}
    	document.querySelector("#" + selid).innerHTML = opt;
    }

    function initOption(selid) {
    	let options = document.querySelector("#" + selid);
    	options.length = 0;
    	// let len = options.length;
    	// for (let i = len - 1; i >= 0; i--) {
    	//   options.remove(i);
    	// }
    }
    </script>
	<%@ include file="/WEB-INF/views/common/footer.jsp"%>