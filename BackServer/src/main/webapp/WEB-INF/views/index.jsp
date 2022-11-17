<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
  </head>
  <body>
    <%@ include file="/WEB-INF/views/common/nav.jsp" %>
    <!-- content -->
    <div class="container p-0 m-5 m d-flex flex-row" style="height: 70vh">
      <div class="w-75"><img src="${root}/img/banner.jpg" alt="" width="80%"/></div>

      <div class="w-25">
        <div>
          <h3>주요 뉴스</h3>
          <ul>
            <li class="pb-2 pt-2">
              <a
                href="https://www.fntimes.com/html/view.php?ud=20220906163036701146fd2a8c7d_18"
                class="text-black"
                >캠코, 부동산원과 ‘금융·부동산 정보 공유 협력’ 약속</a
              >
            </li>
            <li class="pb-2">
              <a
                href="https://biz.chosun.com/real_estate/real_estate_general/2022/09/07/WEVHLA2JMZAPNKICU3XMNS2KEU/"
                class="text-black"
                >부동산 거래 한파에 위기 맞은 중개 플랫폼… 신사업에 ‘안간힘’</a
              >
            </li>
            <li class="pb-2">
              <a
                href="https://www.hani.co.kr/arti/economy/economy_general/1057444.html"
                class="text-black"
                >수도권 15억 넘는 아파트, 주택담보대출 허용 검토
              </a>
            </li>
            <li class="pb-2">
              <a href="https://www.yna.co.kr/view/AKR20220906068900003" class="text-black"
                >부동산R114 "신축·재건축 아파트는 깡통전세 우려 작아"</a
              >
            </li>
            <li class="pb-2">
              <a
                href="https://www.msn.com/ko-kr/news/world/%EB%B8%94%EB%A3%B8%EB%B2%84%EA%B7%B8-%EB%B6%80%EB%8F%99%EC%82%B0-%ED%95%98%EB%9D%BD%EA%B8%B0%EC%97%90%EB%8A%94-%EA%B0%95%EB%82%A8-%EC%95%84%ED%8C%8C%ED%8A%B8-%EA%B0%80%EA%B2%A9-%EB%8D%94-%EB%96%A8%EC%96%B4%EC%A0%B8/ar-AA11uSHc"
                class="text-black"
                >블룸버그 "부동산 하락기에는 강남 아파트 가격 더 떨어져"</a
              >
            </li>
          </ul>
        </div>
      </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
