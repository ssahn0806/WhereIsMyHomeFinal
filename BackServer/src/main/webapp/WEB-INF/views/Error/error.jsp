<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<div class="container">
		<div class="row text-center">
			<h3>처리 중 문제가 발생하였습니다. - ${errorMsg}</h3>
		</div>
	</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>