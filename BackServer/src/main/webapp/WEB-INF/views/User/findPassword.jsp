<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<div class="container">
		<div class="row mt-3">
			<h2 class="bg-primary text-light text-center">비밀번호 찾기</h2>
		</div>
		
		<c:if test="${errorMsg != null}">
			<div class="row mt-3">
				<h4 class="bg-warning text-light text-center">${errorMsg}</h4>
			</div>
		
		</c:if>
		
		<form method="post" action="${root}/user/findPassword.do" class="row">
			<table class="table">
				<tbody>
					<tr>
						<th><label for="userid">아이디</label></th>
						<td><input type="text" name="userid" id="userid"/></td>
					</tr>
					<tr>
						<th><label for="username">이름</label></th>
						<td><input type="text" name="username" id="username"/></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2">
							<input type="submit" value="찾기"/>
							<input type="reset" value="취소"/>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>