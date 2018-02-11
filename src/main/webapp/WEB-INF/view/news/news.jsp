<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Custom and plugin javascript -->
<%-- <script src="${pageContext.request.contextPath}/js/inspinia.js"></script>
<script src="${pageContext.request.contextPath}/js/plugins/pace/pace.min.js"></script>

<script src="${pageContext.request.contextPath}/js/plugins/masonary/masonry.pkgd.min.js"></script>
 --%>
<style>
.grid .ibox {
	margin-bottom: 0;
}

.grid-item {
	margin-bottom: 25px;
	width: 300px;
}
</style>

<!-- CH JS -->
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chcss/news/news.css"/>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/chjs/news.js"></script> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NEWS</title>
</head>
<body>
<input id="addurl" type="hidden" value="${pageContext.request.contextPath}">
<input id="userid" type="hidden" value="${userid}">
	<div class="gray-bg"  id="news">
		<div class="wrapper wrapper-content animated fadeInRight" id="new_boxs">
			<div class="row">
				<div class="col-lg-12">
					<div class="ibox">
						<div class="ibox-content">
							<h2>
								<font style="vertical-align: inherit;"><font
									style="vertical-align: inherit;"> 石工 </font></font>
							</h2>
							<p>
								<font style="vertical-align: inherit;"><font
									style="vertical-align: inherit;"> 砌体是一个JavaScript网格布局库。</font><font
									style="vertical-align: inherit;">它的工作原理是根据可用的垂直空间将元素放置在最佳位置，有点像石匠在墙上镶石头。</font><font
									style="vertical-align: inherit;">你可能已经看到它在互联网上使用。 </font></font><a
									href="http://masonry.desandro.com/" target="_blank"><font
									style="vertical-align: inherit;"><font
										style="vertical-align: inherit;">http://masonry.desandro.com/</font></font></a>
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="ibox">
						<div class="ibox-content">
							<h2>
								<font style="vertical-align: inherit;"><font
									style="vertical-align: inherit;"> 石工 </font></font>
							</h2>
							<p>
								<font style="vertical-align: inherit;"><font
									style="vertical-align: inherit;"> 砌体是一个JavaScript网格布局库。</font><font
									style="vertical-align: inherit;">它的工作原理是根据可用的垂直空间将元素放置在最佳位置，有点像石匠在墙上镶石头。</font><font
									style="vertical-align: inherit;">你可能已经看到它在互联网上使用。 </font></font><a
									href="http://masonry.desandro.com/" target="_blank"><font
									style="vertical-align: inherit;"><font
										style="vertical-align: inherit;">http://masonry.desandro.com/</font></font></a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>