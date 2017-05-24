<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<spring:url value="/resources/css/bootstrap.min.css" var="mainCss" />
<spring:url value="/resources/js/jquery-3.2.1.min.js" var="jqueryJs" />
<spring:url value="/resources/js/main.js" var="mainJs" />

<link href="${mainCss}" rel="stylesheet" />
<script src="${jqueryJs}"></script>
<script src="${mainJs}"></script>

<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title><spring:message code="lbl.MainPage" /></title>
</head>
<!-- Static navbar -->
<nav class="navbar navbar-default navbar-static-top">
<div class="container">
	<div id="navbar" class="navbar-collapse collapse">
		<ul class="nav navbar-nav">
			<li class="active"><a href="#">${userName} <spring:message
						code="lbl.Hello" /> ${nowLocal}
			</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="?loginName=${userName}&locale=en">English</a></li>
			<li><a href="?loginName=${userName}&locale=zh_TW">Chinese</a></li>
		</ul>
	</div>
	<!--/.nav-collapse -->
</div>
</nav>
<body>

	<p>
	<div class="container" style="min-height: 500px">

		<!-- 使用Ajax直接更新之結果區塊 -->
		<div id="feedback"></div>

		<br>

		<div class="starter-template">

			<!--使用ajax傳遞資料，資料類型為application/json-->
			<form class="form-horizontal" id="search-form">
				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label"><spring:message
							code="lbl.Name" /></label>
					<div class="col-sm-10">
						<input type=text class="form-control" id="name">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" id="bth-search"
							class="btn btn-primary btn-lg">Json</button>
					</div>
				</div>
			</form>


			<!--使用ajax傳遞資料，資料類型為application/x-www-form-urlencoded-->
			<form class="form-horizontal" id="search-form-x">
				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label"><spring:message
							code="lbl.Name" /></label>
					<div class="col-sm-10">
						<input type=text class="form-control" id="nameX">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" id="bth-search"
							class="btn btn-primary btn-lg">Json X</button>
					</div>
				</div>
			</form>

			<!--使用ajax上傳檔案，資料類型為multipart/form-data-->
			<form method="POST" enctype="multipart/form-data" id="fileUploadForm"
				class="form-horizontal">

				<div class="form-group form-group-lg">
					<label class="col-sm-2 control-label"><spring:message
							code="lbl.File" /></label>
					<div class="col-sm-10">
						<input type=file class="form-control" name="file" id="updateFile">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button id="btnUpload" class="btn btn-primary btn-lg"
							type="button">
							<spring:message code="lbl.Upload" />
						</button>
					</div>
				</div>

			</form>

			<!--直接下載其他檔案-->
			<form class="form-horizontal">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button id='btnDownloadFileJqueryFile'
							class="btn btn-primary btn-lg" type="button">
							<spring:message code="lbl.Download" />
						</button>
					</div>
				</div>
			</form>
		</div>

	</div>

</body>
</html>