<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en"></html>
  <head>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
  </head>
  <body>
    <%@ include file="/WEB-INF/views/common/nav.jsp" %>
    <!-- content -->
    <div class="container" style="height: 81.8vh">
      <div>
        <table class="table w-100 mt-5">
          <thead class="thead-dark bg-secondary text-white">
            <tr>
              <td class="w-10">번호</td>
              <td class="w-50">제목</td>
              <td class="w-10">조회수</td>
              <td class="w-10">등록시간</td>
              <td></td>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="notice" items="${notices}">
            	<tr>
	              <th scope="row">${notice.articleNo}</th>
	              <td><a href="${root}/board/noticeview.do?articleNo=${notice.articleNo}">${notice.subject}</a></td>
	              <td>${notice.hit}</td>
	              <td>${notice.registerTime}</td>
	              <c:if test="${userlevel eq 'admin'}">
		              <td>
		                <a href="${root}/board/modifyform.do?articleNo=${notice.articleNo}&subject=${notice.subject}&content=${notice.content}"><button>수정</button></a>
		                <a href="${root}/board/delete.do?articleno=${notice.articleNo}"><button>삭제</button></a>
		              </td>
	              </c:if>
	            </tr>
            </c:forEach>
          </tbody>
        </table>
        <c:if test="${userlevel eq 'admin'}">
        <div class="mt-5"><a href="${root}/board/insertForm.do"><button>등록</button></a></div>
        </c:if>
      </div>
    </div>
    <!-- footer -->
    <div class="d-flex p-3 bg-dark text-white">
      <div class="col-sm-2">
        <a href="guide.html" class="text-decoration-none text-white">웹사이트 안내</a>
      </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

