<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<head>
	<script type="text/javascript" src="${contextPath}/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.paging.min.js"></script>
	<style>
	
	
	
	
	.cont_box2{position: relative; width:99%;height:90%; margin:0 auto; box-sizing: border-box; background:#fff; padding: 0px 50px 0px 50px; border:1px solid #a1a1a1; border-radius: 8px; box-shadow: 2px 2px 3px 0 rgba(0,0,0,.1);}
	
	.cont_bt{width:100%; margin-top:20px;}
	.pagination {position:absolute; left:50%;transform:translateX(-50%); display: flex; justify-content: center; align-items: center; width:400px;  }
	.pagination a{display:inline-block; width:36px; height:36px; font-size:14px; line-height:36px; color:#c5c5c5;font-weight:600; text-align: center;}
	.pagination a:hover{color:#1b1b1b;}
	.pagination a.on{color:#1b1b1b;}
	</style>
</head>

<div class="cont_box2">
	<div  style="min-height:100px; max-height:657px; overflow: auto;">
		<table class="basic">					
			<thead>	
				<tr>
					<th>No</th>
					<th>공장</th>
					<th>등급</th>
					<th>ID</th>
					<th>PWD</th>
					<th>POS Talk 등록 사용자</th>
					<th colspan="2">생성자</th>
					<th>생성일</th>
					<th colspan="2">수정자</th>
					<th>수정일</th>
				</tr>						
			</thead>
			<tbody id="userListTb">	
			</tbody>
		</table>
	</div>
	<div class="cont_bt">
		<div class="pagination">  				        
	    </div>
	</div>
</div>
	
	
<script type="text/javascript">
$(document).ready(function(){
	 selectUserList(1);
});

function selectUserList(pageNum){
	let perNum = 10;
	$.ajax({
		type : "POST",
		url : "${contextPath}/test/selectPageList",
		async : true,
		dataType: 'json',
		data : {
			perNum:perNum
			,pageNum:pageNum
		},
		success : function(res){
			let data = res.data;
			console.log(data);
			//totalCnt: 100, startNum: 0, perNum: 10, list: Array(1), pageNum: 1
			/*
			res.data.list.forEach (function (data, index) {
				var trHtml = '<tr>'
					+'    <td>'+data.udtDatetime+'</td>'
					+'</tr>';
				userListTb.append(trHtml);	
				var popTag = $('<a href="javascript:void(0);" class="showMask" style="color:blue;">'+data.id+'</a>');
				popTag.click(function (e) {
		            modifyPop(data.fNm,data.levelNm,data.id,data.pwd,data.etc);
		     	});
				$('#detailPop_'+data.id).html(popTag);
			});
			*/
			
			paging(".pagination", data.totalCnt,data.perNum,data.pageNum,selectUserList,"${contextPath}");
		},
		error: function(xhr){
			console.log(xhr);
		}
	});	
	
	
	//let totalCnt = 100;
	//let perNum2 = 10;
	//let pageNum2 = 1;
	
	//paging(".pagination", totalCnt, perNum2, pageNum2,selectUserList,"${contextPath}");
}



 function paging(className, totalCount, perNum, pageNum,clickFun){
	if(totalCount > 0){
		$(className).paging(totalCount, {
			format: '[< ncnnn! >]', 
			perpage: perNum,
			lapping: 0,
			page: pageNum,
			onClick : function(page){
				var pageVal = $(this).data('page');
				$('#pageNum').val(pageVal);
				clickFun(pageVal);
			},
			onFormat: function (type) {
				switch (type) {
					case 'block':
						if (!this.active){
                            return '';
						} else { 
							if(this.page == this.value){
								return '<a href="javascript:void(0);" class="on">' + this.value + '</a>'
							} else {
								return '<a href="javascript:void(0);">' + this.value + '</a>';
							}
						}
					case 'next' : return '<a href="javascript:void(0);"><img src="/images/pagination-arrow-next.png" alt="next"></a>';
					case 'prev' : return '<a href="javascript:void(0);"><img src="/images/pagination-arrow-pre.png" alt="preview"></a>';
					case 'first': return '<a href="javascript:void(0);"><img src="/images/pagination-arrow-first.png" alt="first"></a>';
					case 'last' : return '<a href="javascript:void(0);"><img src="/images/pagination-arrow-end.png" alt="end"></a>';
				}
			}
		});
	}
}
 

</script>
