<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
$(document).ready(function(){ // html페이지를 다 로드시키고 매개변수함수를 실행
	
	$('#addFileupload').click(function(){
        let flag = true;
		// 추가된 noticefileList안에 파일이 첨부되지 않았다면 새로운 noticefileList 추가 X
		/* javascript 기본api
		let noticefileList = $('.noticefileList');
		console.log(noticefileList.length);
		for(let i=0; i<noticefileList.length; i++) {
			console.log(noticefileList[i].value);
			if(noticefileList[i].value == '') {
				flag = false;
				break;
			}
		}
		*/
		
		// jquery api 사용
		$('.noticefileList').each(function(){ // each함수를 이용한 반복
			if($(this).val() == '') {
				flag = false;
			}
		});
		
		if(flag) {
			$('#fileSection').append("<div><input class='noticefileList' type='file' name='noticefileList'><div>");
		} else {
			alert('파일이 첨부되지 않은 noticefileList가 존재합니다');
		}
	});
	
	$('#addNotice').click(function(){
        let flag = true;
		if($('#noticeTitle').val() == '') {
			alert('noticeTitle 입력하세요');
		} else if($('#noticeContent').val() == '') {
			alert('noticeContent 입력하세요');
		} else {
			$('.noticefileList').each(function(){ // each함수를 이용한 반복
				if($(this).val() == '') {
					flag = false;
				}
			});
			if(flag) {
				$('#addForm').submit();
			} else {
				alert('파일이 첨부되지 않은 noticefileList가 존재합니다');
			}
		}
	});
	
});	
</script>
</head>
<body>
	<form id="addForm" method="post" action="${pageContext.request.contextPath}/addNotice" enctype="multipart/form-data">
		<div>
			제목 : <input type="text" name="noticeTitle" id="noticeTitle">
		</div>
		<div>
			내용 : <input type="text" name="noticeContent" id="noticeContent">
		</div>
		<div>
			<button type="button" id="addFileupload">파일업로드 추가</button>
			<div id="fileSection">
				<!-- 파일업로드 input 태그가 추가될 영역 -->
			</div>
		</div>
		<div>
			<button type="button" id="addNotice">입력</button>
		</div>
	</form>
</body>
</html>