<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/nav.jsp"%>
	<!-- 	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=88d45ac53ce90e79d08d138faff11149"></script> -->
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=88d45ac53ce90e79d08d138faff11149&libraries=services"></script>
	<!-- content -->
	<div class="container h-80 flex-row"
		style="height: 83.6vh; overflow: scroll">
		<br />
		<div class="container h-80 flex-row"
			style="height: 83.6vh; overflow: scroll">
			<h3 class="text-center text" id="favoriteStreet">나의 관심 지역</h3>
			<br />
			<div class="row" style="width: 100%; height: 100%">
				<div class="col-4">
					<div>
						<canvas id="chart" width="300" height="200"></canvas>
					</div>
					<h3 class="text-center text">관심 지역 부가 정보</h3>
					<button id="getHospital" class="btn btn-danger">병원</button>
					<button id="getBank" class="btn btn-success">은행</button>
					<button id="getTheater" class="btn btn-primary">극장</button>
					<button id="getCafe" class="btn btn-dark">카페</button>
					<div style="width: 100%; height: 650px; overflow: auto">
						<table class="table" style="width: 100%">
							<thead></thead>
							<tbody id="dataList" style="overflow: scroll">
								<tr>
									<td class="text-center" colspan="4">검색 대상을 선택해주세요!</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-8">
					<div id="map" style="width: 100%; height: 100%;"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
		var container = document.getElementById("map");
		var options = {
				center: new kakao.maps.LatLng(37.498545, 127.062223),
				level: 5,
			};
		var map = new kakao.maps.Map(container, options);
		var markerLst = [];
		var searchList;
		var ps = new kakao.maps.services.Places(map); 
	</script>
	<script>
	window.onload = function(){
		
		fetch("${root}/house/rest/fullname.do?dongCode="+"${userfavDong}")
		.then((response)=>response.json())
		.then((data)=>{
			let favSt = document.querySelector("#favoriteStreet");
			favSt.innerText = "나의 관심 지역 : " + data.fullname; 
		});
		
		fetch("${root}/house/rest/coords.do?dongCode="+"${userfavDong}")
		.then((response)=>response.json())
		.then((data)=>{
			var container = document.getElementById("map");
			
			var moveLatLng = new kakao.maps.LatLng(data.lat,data.lng);
			var options = {
					center: moveLatLng,
					level: 5,
				};
			map.setCenter(moveLatLng);
		})
		
		fetch("${root}/place/rest/count.do?dongCode="+"${userfavDong}")
		.then((response)=>response.json())
		.then((data)=>{
			searchList = data;
			var ps = new kakao.maps.services.Places(map);
			ps.categorySearch('HP8',countAppendHospital,{bounds: map.getBounds(), location : new kakao.maps.LatLng(map.getCenter())});
			
		})
	}
	
	
	document.querySelector("#getHospital").addEventListener("click",function(){
		var ps = new kakao.maps.services.Places(map); 
		ps.categorySearch('HP8',placeSearchCB,{
			bounds : map.getBounds(),
			location : new kakao.maps.LatLng(map.getCenter())
		});
	})
	
	document.querySelector("#getBank").addEventListener("click",function(){
		var ps = new kakao.maps.services.Places(map); 
		ps.categorySearch('BK9',placeSearchCB,{
			bounds : map.getBounds(),
			location : new kakao.maps.LatLng(map.getCenter())
		});
	})
	
	document.querySelector("#getCafe").addEventListener("click",function(){
		fetch("${root}/place/rest/list.do?dongCode="+"${userfavDong}&categoryCode=Q12")
		.then((response)=>response.json())
		.then((data)=>{
			replaceList(data);
		});
	})
	
	document.querySelector("#getTheater").addEventListener("click",function(){
		fetch("${root}/place/rest/list.do?dongCode="+"${userfavDong}&categoryCode=N03")
		.then((response)=>response.json())
		.then((data)=>{
			replaceList(data);
		});
	})
	
	function drawChart(){
		new Chart(document.getElementById("chart"), {
		    type: 'bar',
		    data: {
		      labels: ["병원","은행","극장","카페"],
		      datasets: [
		        {
		          label: "조회된 개수",
		          backgroundColor: ["#dc3545", "#198754","#0d6efd","#212529"],
		          data: [searchList.hospital,searchList.bank,searchList.theater,searchList.cafe]
		        }
		      ]
		    },
		    options: {
		      responsive : false,
		      legend: { display: false },
		      titie : {
		    	  display : true,
		    	  text : '관심 지역 내 부가 정보 조회 결과'
		      }
		    }
		});
	}
	function countAppendBank(data,status,pagination){
		if(status == kakao.maps.services.Status.OK){
			searchList['bank'] = data.length;
			console.log(searchList);
			drawChart();
		}
	}
	
	function countAppendHospital(data,status,pagination){
		if(status == kakao.maps.services.Status.OK){
			searchList['hospital'] = data.length;
			ps.categorySearch('BK9',countAppendBank,{bounds: map.getBounds(), location : new kakao.maps.LatLng(map.getCenter())});
		}
	}
	
	 function replaceList(data) {
    	let tbody = document.querySelector("#dataList");
    	tbody.innerHTML = "";

   		if(markerLst.length>0){
			for(let i=0;i<markerLst.length;i++){
				markerLst[i].setMap(null);
			}
		}
   		
    	if (data.length == 0) {
    		let tr = document.createElement("tr");
    		let td = document.createElement("td");
    		td.setAttribute("class","text-center");
    		td.textContent = "조회된 데이터가 없습니다.";
    		tr.appendChild(td);
    		tbody.appendChild(tr);
    	}
    	else {
    		let category = data[0].categorycode == "Q12" ? "카페" : "극장";
    		
    		for (let i = 0; i < data.length; i++) {
    			let item = data[i];
    			let tr = document.createElement("tr");
 
				let td = document.createElement("td");
				let dataName = document.createElement("h6");
				dataName.textContent = category+`명 :`+item.name+item.subname;
				let dataAddr = document.createElement("p");
				dataAddr.textContent = category+`주소 : ` + item.address;
				let dataLink = document.createElement("a");
				dataLink.setAttribute("target","_blank");
				dataLink.setAttribute("href","https://map.kakao.com/link/to/"+item.name+item.subname+","+item.lat+","+item.lng);
				dataLink.textContent = "길찾기";
				td.appendChild(dataName);
				td.appendChild(dataAddr);
				td.appendChild(dataLink);
				tr.appendChild(td);

    			tbody.appendChild(tr);
    			
    			data[i].lat = data[i].lat.substr(0,data[i].lat.length-2);
    			displayMarker(data[i]);

    		}

    	}
    }
	
	function placeSearchCB(data,status,pagination){
		if(status == kakao.maps.services.Status.OK){
 			addList(data);
 			console.log(markerLst);
 			if(markerLst.length>0){
 				for(let i=0;i<markerLst.length;i++){
 					markerLst[i].setMap(null);
 				}
 			}
			for(var i=0;i<data.length;i++){
				displayMarker(data[i]);
			}
		}
	}

	
	function displayMarker(place){
		// 마커를 생성하고 지도에 표시합니다
		console.log(place);
		var imageSrc = "../img/bank_marker.png";
		
		if(place.categorycode !=null){
			place.y = place.lat;
			place.x = place.lng;
			if(place.categorycode == "Q12"){
				imageSrc = "../img/cafe_marker.png";
			}
			else{
				imageSrc = "../img/theater_marker.png";
			}
		}
		else{
			if(place.category_group_code == "HP8"){
				imageSrc = "../img/hospital_marker.png";
			}
			
		}
		
	    var imageSize = new kakao.maps.Size(34, 39);// 마커이미지의 크기입니다
	    var imageOption = {offset: new kakao.maps.Point(27, 69)};
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
	    var marker = new kakao.maps.Marker({
	    	map : map,
	        position: new kakao.maps.LatLng(place.y, place.x),
	        image : markerImage
	    });
	    markerLst.push(marker);
		
	}
    function addList(data) {
    	let tbody = document.querySelector("#dataList");
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
    		let category = data[0].category_group_code == "HP8" ? "병원" : "은행";
    		for (let i = 0; i < data.length; i++) {
    			let item = data[i];
    			let tr = document.createElement("tr");
 
				let td = document.createElement("td");
				let dataName = document.createElement("h6");
				dataName.textContent = category+`명 :`+item.place_name;
				let dataAddr = document.createElement("p");
				dataAddr.textContent = category+`주소 : ` + item.road_address_name;
				let dataPhone = document.createElement("p");
				dataPhone.textContent = `전화번호 : ` + item.phone;
				td.appendChild(dataName);
				td.appendChild(dataAddr);
				td.appendChild(dataPhone);
				tr.appendChild(td);

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