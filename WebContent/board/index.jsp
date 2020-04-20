<%@page import="VO.NewsVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	ArrayList<String> keywordList = (ArrayList<String>) request.getAttribute("keywordList");
	ArrayList<NewsVO> newsPreviewList = (ArrayList<NewsVO>) request.getAttribute("newsList");
	
	int keyListSize = keywordList.size();
	int npSize = newsPreviewList.size();
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>외부망 언론 정보 시스템</title>
	
	<link rel="stylesheet" href="board/css/bootstrap.min.css" type="text/css">
	
	<style>
		body {
			margin: 24px;
			padding: 0;
		}	
		.header {
			display: inline-block;
			margin: 20px 0 20px 0;
		}		
		#main_title {
			cursor: pointer;
		}
		.service_list {
			margin: 0 0 16px 0;
		}
		.service_label {
			font-size: 20px;
		}
		.btn {
			float:right;
		}
		.keyword_item {
			margin: 0 0 8px 0;
		}
		.title_list_group {
			margin: 12px 0 0 0;
		}
	</style>
</head>
<body>
	<div class="header">
		<h1 class="font-weight-bold" onclick="title_refresh();" id="main_title">외부망 언론 정보 수집 시스템</h1>
	</div>
	
	<div class="service_list">
		<ul class="list-group">
			<li class="list-group-item">
				<span class="service_label">1. 데이터 수집</span>
				<button class="btn btn-primary data_collect_btn font-weight-bold" name="data_collect" onClick="data_collect_btn_onClick(event);">실행</button>
			</li>
			<li class="list-group-item">
				<span class="service_label">2. 데이터 취합</span>
				<button class="btn btn-primary data_export_btn font-weight-bold" name="data_export" onClick="data_export_btn_onClick();">실행</button>
			</li>
			<li class="list-group-item">
				<span class="service_label">3. 데이터 간략 보기</span>
				<button class="btn btn-primary data_preview_btn font-weight-bold" id="data_preview_btn" name="data_preview" onClick="data_preview_btn_onClick();">보기</button>
			</li>
			<li class="list-group-item">
				<span class="service_label">4. 키워드 보기</span>
				<button class="btn btn-primary keyword_preview_btn font-weight-bold" id="keyword_preview_btn" name="keyword_preview" onClick="keyword_preview_btn_onClick();">보기</button>
			</li>
		</ul>
	</div>
	
	<div class="keyword_preview_screen" id="keyword_view" style="display: none;">
		<h5>>> 현재 keyword.json의 키워드 목록</h5>
		<ul>
			<%
				for (int i = 0; i < keyListSize; i++) {
					String keyword = keywordList.get(i);
			%>
				<li class="keyword_item"><%= keyword %></li>
			<%
				}
			%>
		</ul>
	</div>
	
	<div class="data_preview_screen" id="data_view" style="display: none;">
		<h5>>> 현재 데이터 타이틀 보기</h5>
		<ul class="list-group title_list_group">
			<%
				for (int i = 0; i < npSize; i++) {
					String title = newsPreviewList.get(i).getTitle();
			%>
				<li class="list-group-item"><%= title %></li>
			<%
				}
			%>
		</ul>
	</div>	
<script>
	function title_refresh() {
		window.location.href = "/ExternalService/main";
	}
	
	// 1. 데이터 수집 실행 버튼
	function data_collect_btn_onClick() {
		const insertNotice = confirm("자료를 수집을 시작하시겠습니까?");
		
		if (insertNotice == true) {
			window.location.href = "/ExternalService/crawling";
			
		} else {
			return;
		}
	}
	
	// 2. 데이터 news.json 으로 내보내기
	function data_export_btn_onClick() {
		const exportNotice = confirm("자료를 내보내겠습니까?");
		
		if (exportNotice == true) {
			window.location.href = "/ExternalService/export";
		} else {
			return;
		}
	}
	
	// 3. 데이터 간략 보기
	function data_preview_btn_onClick() {
		const dataPreview = document.getElementById("data_view");
		const dataPreviewBtn = document.getElementById("data_preview_btn");
		
		if (dataPreview.style.display == "none") {
			dataPreviewBtn.innerText = "접기";
			dataPreview.style.display = "block";
		} else {
			dataPreviewBtn.innerText = "보기";
			dataPreview.style.display = "none";
		}
	}
	
	// 4. 키워드 보기 실행 버튼
	function keyword_preview_btn_onClick() {
		const keyPreview = document.getElementById("keyword_view");
		const previewBtn = document.getElementById("keyword_preview_btn");
		
		if (keyPreview.style.display == "none") {
			previewBtn.innerText = "접기";
			keyPreview.style.display = "block";
		} else {
			previewBtn.innerText = "보기";
			keyPreview.style.display = "none";
		}
	}
	
	
</script>
<!-- BootStrap4 -->
<script src="board/js/jquery-3.4.1.slim.min.js"></script>
<script src="board/js/popper.min.js"></script>
<script src="board/js/bootstrap.min.js"></script>
</body>
</html>