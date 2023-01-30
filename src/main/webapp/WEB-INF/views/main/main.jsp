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
<button type="button" onclick="testApi()">testApi(토큰 버전)</button>
<button type="button" onclick="testApi2()">testApi2(토큰 미입력)</button>
<button type="button" onclick="testApi3()">testApi2(토큰 미입력)</button>
<br/>
<button type="button" onclick="location.href='/test/pageTest'">testPage</button>



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
            xhr.setRequestHeader("Authorization",token);
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


function testApi2(){
	$.ajax({
		url :'/api/testApi2',
		type : "POST",
		dataType: 'json',
		success: function(result){
			console.log(result.resultCode +" / " +result.resultMsg);
			alert(result.data.test);
		},
		error: function(xhr){
			alert("faild");
		}
	});
}



function testApi3(){
	//변경된 토큰 확인
	let token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3Mzk0NDI0NiwiZXhwIjoxNjc0NTQ5MDQ2LCJ1c2VySWQiOiJhZG1pbiJ9.wuOESG2hyw0cEcN1LQ0scyXlafsRikyFYqznQxXKo_kfyxFG53Rmym8ZOdDCuVCTue6ZZSATZwSQkPaNjWx_HQ";
	$.ajax({
		url :'/api/testApi1',
		type : "POST",
		dataType: 'json',
		beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json");
            xhr.setRequestHeader("Authorization",token);
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

