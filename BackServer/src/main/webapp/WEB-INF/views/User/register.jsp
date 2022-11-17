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
			<h2 class="bg-primary text-light text-center">회원 가입</h2>
		</div>
		
		<c:if test="${errorMsg != null}">
			<div class="row mt-3">
				<h4 class="bg-warning text-light text-center">${errorMsg}</h4>
			</div>
		
		</c:if>
		
		<form method="post" action="${root}/user/register.do" class="row" onsubmit="return check();">
			<input type="hidden" name="userlevel" id="userlevel" value="user"/>
			<table class="table">
				<tbody>
					<tr>
						<th><label for="userid">아이디</label></th>
						<td><input type="text" name="userid" id="userid"/></td>
					</tr>
					<tr>
						<th><label for="userpw">비밀번호</label></th>
						<td><input type="password" name="userpw" id="userpw"/></td>
					</tr>
					<tr>
						<th><label for="username">이름</label></th>
						<td><input type="text" name="username" id="username"/></td>
					</tr>
					<!-- <tr>
						<th><label for="userlevel">등급</label></th>
						<td><input type="hidden" name="userlevel" id="userlevel" /></td>
					</tr>
					<tr>
						<th><label for="userfavdongcode">선호 지역</label></th>
						<td><input type="text" name="userfavdongcode" id="userfavdongcode"/></td>
					</tr> -->
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2">
							<input type="submit" value="회원가입"/>
							<input type="reset" value="취소"/>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</div>
	<script>
		function check(){
			if (!document.querySelector("#userid").value) {
		          alert("아이디 입력!!");
		          return false;
		    } if (!document.querySelector("#userpw").value) {
		          alert("비밀번호 입력!!");
		          return false;
	        } if (!document.querySelector("#username").value) {
		          alert("이름 입력!!");
		          return false;
		    } 
			return true;
		}
	</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>