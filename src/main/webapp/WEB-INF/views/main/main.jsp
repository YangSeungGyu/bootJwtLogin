<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script type="text/javascript" src="${contextPath}/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
<title>Insert title here</title>
</head>
<body>
main
<br/>
<a href="/auth/loginView"><button type="button" id="loginView">로그인</button></a>
</body>
<br/>
<input type="hidden" id="token"></input>
<button type="button" onclick="testApi()">testApi</button>

<script type="text/javascript">
$(document).ready(function(){
	//실행될 코드
	let token = "${param.token}";
	$("#token").val(token);
});

function testApi(){
	let token = $("#token").val();
	alert(token);
	$.ajax({
		url :'/api/testApi1',
		type : "POST",
		dataType: 'json',
		beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json");
            xhr.setRequestHeader("Authorization","Bearer " + token);
        },
		success: function(result){
			console.log(result.resultCode +" / " +result.resultMsg);
			alert(result.data.test);
		},
		error: function(xhr){
			alert("faild");
		}
	});
}

</script>

</html>

