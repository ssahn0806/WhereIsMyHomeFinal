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
    <div class="container">
      <h3 class="pb-3"><a href="${root}/index.do" class="text-black" target="_blank">메인 페이지 및 메뉴 구성</a></h3>
      <p>회원 가입 및 로그인 : 우측 상단의 메뉴를 통해 이용할 수 있습니다.</p>
      <p>로그인 전 메뉴 확인 가능 : 네비게이션 바를 통해 이동할 수 있습니다.</p>
      <div><img src="${root}/img/main_page.png" alt="" width="90%" /></div>
      <p>회원 정보 및 로그아웃 : 우측 상단의 메뉴를 통해 이용할 수 있습니다.</p>
      <p>로그인 후 메뉴 확인 가능 : 네비게이션 바를 통해 이동할 수 있습니다.</p>
      <div><img src="${root}/img/main_page2.png" alt="" width="90%" /></div>

      <h3 class="pb-3">회원 관리 페이지</h3>
      <p><a href="${root}/user/registerform.do" class="text-black" target="_blank">회원 가입 페이지</a></p>
      <div><img src="${root}/img/user_register.png" alt="" /></div>
      <p><a href="${root}/user/modify.do" class="text-black" target="_blank">회원 정보 조회 및 수정</a></p>
      <div><img src="${root}/img/user_edit.png" alt="" /></div>
      <p><a href="${root}/user/modify.do" class="text-black" target="_blank">회원 정보 삭제(탈퇴)</a></p>
      <div><img src="${root}/img/user_delete.png" alt="" /></div>

      <h3 class="pb-3">
        <a href="${root}/house/list.do" class="text-black" target="_blank">실거래가 조회, 결과 페이지</a>
      </h3>
      <p>등록되어 있는 모든 아파트의 정보를 list로 가져옵니다.</p>
      <p>시,군,구 동을 선택할 수 있는 select UI 구성</p>
      <p>선택된 동을 Map에 다각형으로 표현하고, 검색된 아파트를 클릭하면 마커를 표시함.</p>
      <div><img src="${root}/img/apart_all.png" alt="" width="90%" /></div>
      <div><img src="${root}/img/apart_search.png" alt="" width="90%" /></div>
      <div><img src="${root}/img/apart_detail.png" alt="" width="90%" /></div>

      <h3 class="pb-3"><a href="find_pw.html" class="text-black" target="_blank">비밀번호 찾기 페이지</a></h3>
      <p>메인 화면에서 로그인 하지 않았을 때 비밀번호를 찾을 수 있는 UI 구성</p>
      <p>회원가입시 등록했던 내용을 토대로 비밀번호를 찾을 수 있는 UI 구성</p>
      <div><img src="${root}/img/password_find.png" alt="" width="90%" /></div>
      <div><img src="${root}/img/password_find2.png" alt="" width="90%" /></div>

      <h3 class="pb-3">
        <a href="javascript:alert('준비중입니다.')" class="text-black"  target="_blank"
          >관심지역 등록/수정/삭제 환경 관련 정보 제공</a
        >
      </h3>
      <p>현재 등록된 관심 지역을 조회하고, 수정 삭제 할 수 있는 UI 구성</p>
      <p>관심지역내에 존재하는 오염물 폐기 시설의 정보를 Map위에 나타냄</p>
      <div><img src="${root}/img/favorite_edit.png" alt="" width="90%" /></div>
      <div><img src="${root}/img/favorite_location.png" alt="" width="90%" /></div>

      <h3 class="pb-3">
        <a href="${root}/board/notice.do" class="text-black">공지사항 페이지</a>
      </h3>
      <p>등록되어 있는 공지사항 수정,삭제하고 새롭게 등록하는 UI 구성</p>
      <div><img src="${root}/img/noticeList.png" alt="" width="90%" /></div>
    </div>
    <!-- footer -->
    <div class="d-flex p-3 bg-dark text-white">
      <div class="col-sm-2">
        <a href="${root}/board/guide.do" class="text-decoration-none text-white"  target="_blank">웹사이트 안내</a>
      </div>
    </div>
   </body>
  </html>
