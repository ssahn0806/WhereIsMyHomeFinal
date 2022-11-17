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
			<h2 class="bg-primary text-light text-center">회원 정보</h2>
		</div>
		
		<c:if test="${errorMsg != null}">
			<div class="row mt-3">
				<h4 class="bg-warning text-light text-center">${errorMsg}</h4>
			</div>
		
		</c:if>
		
		<form method="post" action="${root}/user/modify.do" class="row" onsubmit="return check();">
			<input type="hidden" name="userlevel" id="userlevel" value="user"/>
			<table class="table">
				<tbody>
					<tr>
						<th><label for="userid">아이디</label></th>
						<td><input type="text" name="userid" id="userid" value="${userid}" readonly/></td>
					</tr>
					<tr>
						<th><label for="userpw">비밀번호</label></th>
						<td><input type="text" name="userpw" id="userpw" value="${userpw}" /></td>
					</tr>
					<tr>
						<th><label for="username">이름</label></th>
						<td><input type="text" name="username" id="username" value="${userName}" /></td>
					</tr>
                    <tr>
                        <th><label for="userfavDong">관심지역</label></th>
                        <td id="favorite"><input type="text" name="userfavDong" id="userfavDong" value="${userfavDong}" /></td>
                    </tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2">
							<input type="submit" value="수정"/>
							<input type="reset" value="취소"/>
							<input type="submit" value="탈퇴" formaction="${root}/user/delete.do" formmethod="post"/>
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
		
		window.onload = function(){
			let favorite = document.querySelector("#favorite");
			let userfavDong = document.querySelector("#userfavDong");
			if(userfavDong.value !=""){
				fetch("${root}/house/rest/fullname.do?dongCode="+userfavDong.value)
				.then((response)=>response.json())
				.then((data)=>{
					console.log(data);
					userfavDong.value = data.fullname;
/* 					userfavDong.setAttribute("style","width:220px"); */
				})
			}
			else{
/* 				let aTag = document.createElement("a");
				aTag.setAttribute("href","${root}/house/list.do"); */
				favorite.innerHTML = `<a href="${root}/house/list.do">등록하러가기</a>`;
				console.log(favorite);
			}
		}
	</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>