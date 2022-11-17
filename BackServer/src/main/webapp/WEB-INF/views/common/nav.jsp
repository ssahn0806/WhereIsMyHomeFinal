<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
  <div class="container-fluid bg-primary text-white text-end">
    <div class="d-flex flex-row-reverse row">
      <c:choose>
        <c:when test="${not empty userid}">
          <div>
            <span class="text-white text"
              ><a href="${root}/user/detail.do"
                ><span class="text-decoration-underline text-white text">${userName}</span></a
              >님 반갑습니다.</span
            >
            <a href="${root}/user/logout.do"><span class="text-white">로그아웃 </span></a>
          </div>
        </c:when>
        <c:otherwise>
          <div>
            <a href="${root}/user/registerform.do"><span class="text-white">회원가입 </span></a>
            <a href="${root}/user/loginform.do"><span class="text-white">로그인</span></a>
          </div>
        </c:otherwise>
      </c:choose>
      <div class="container-fluid text-start">
        <a href="${root}/"><img src="${root}/img/logo.png" alt="" width="10%" /></a>
      </div>
    </div>
  </div>
</header>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <c:choose>
    <c:when test="${not empty userid}">
      <div class="col">
        <div class="d-flex p-3 flex-row-reverse bg-dark text-white">
          <!-- <div class="col-sm-2"><a href="javascript:alert('준비중입니다.')"class="text-decoration-none text-white">관심지역 둘러보기</a></div> -->
          <div class="col-sm-2">
            <a href="${root}/house/favorite.do" class="text-decoration-none text-white">관심지역 둘러보기</a>
          </div>
          <!-- 			      <div class="col-sm-2"><a href="fav_loc.html" class="text-decoration-none text-white">관심지역 설정</a></div> -->
          <div class="col-sm-2">
            <a href="${root}/house/list.do" class="text-decoration-none text-white">시세조회</a>
          </div>
          <div class="col-sm-2">
            <a href="${root}/board/noticeList.do" class="text-decoration-none text-white">공지사항</a>
          </div>
          <div class="col-sm-2">
            <a href="${root}/board/guide.do" class="text-decoration-none text-white">이용방법</a>
          </div>
        </div>
      </div>
    </c:when>
    <c:otherwise>
      <div class="col">
        <div class="d-flex p-3 flex-row-reverse bg-dark">
          <div class="col-sm-2">
            <a href="${root}/board/noticeList.do" class="text-decoration-none text-white">공지사항</a>
          </div>
          <div class="col-sm-2">
            <a href="${root}/board/guide.do" class="text-decoration-none text-white">이용방법</a>
          </div>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
</nav>
