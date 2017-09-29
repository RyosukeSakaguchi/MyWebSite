<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.UserBeans"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html class="no-js">

	<head>
		<!-- 共通header.jsp読み込み-->
		<jsp:include page="header.jsp" />
		<title>ユーザー情報消去</title>
		<!-- オリジナルCSS読み込み -->
		<link href="/AttendanceRecord/jsp/css/original/common.css" rel="stylesheet">
	</head>

	<body>
		<section id="fh5co-hero" class="no-js-fullheight" style="background-image: url(/AttendanceRecord/jsp/images/full_image_2.jpg);" data-next="yes">
			<div class="container">
				<div class="fh5co-intro no-js-fullheight">
					<div class="fh5co-intro-text">
						<div class="fh5co-center-position">
							<%
								UserBeans u = (UserBeans) request.getAttribute("userInfo");
								UserBeans userList[] = (UserBeans[])request.getAttribute("userList[]");
								if(u != null){
							%>
							<h2 class="animate-box">
								<font size="5">本当にログインID&nbsp;:&nbsp;<font color="red"><%=u.getLoginId()%></font>を削除してもよろしいでしょうか。</font>
							</h2>
							<%
								}else if(request.getAttribute("userList[]") != null){
							%>
									<h2 class="animate-box">
									<font size="5">本当にログインID&nbsp;:&nbsp;
							<%
									for(int i = 0; i < userList.length; i++){
										if(i == userList.length - 1){
							%>
										<font color="red"><%=userList[i].getLoginId()%></font>
							<% 			}else{%>
										<font color="red"><%=userList[i].getLoginId()%></font>,
							<%
										}
									}
							%>
								 を削除してもよろしいでしょうか。</font></h2>
							<%
								}else{
							%>
									<h2 class="animate-box">
									<font size="5">本当に<font color="red">全ユーザー</font>を削除してもよろしいでしょうか。</font>
									</h2>
							<%
								}
							%>

							<br><br>
							<p class="animate-box">
								<div class="wrapper">
									<div>
										<a href="UserList" class="btn btn-outline "><i class="icon-play2"></i> Cancel</a>
									</div>
									<div>
										<form action="UserDelete" method="post">
												<%
													if(u != null){
												%>
												<input type="hidden" value="<%=u.getId()%>" name="id">
												<%
													}else if(request.getAttribute("userList[]") != null){
														for(int i = 0; i < userList.length; i++){
												%>
												<input type="hidden" value="<%=userList[i].getId()%>" name="idList[]">
												<%
														}
													}
												%>

											<button  class="btn btn-outline" type="submit"><i class="icon-play2"></i> OK</button>
										</form>
									</div>
								</div>
							</p>
						</div>
					</div>
				</div>
			</div>
		</section>

		<!-- 共通footer.jsp読み込み-->
		<jsp:include page="footer.jsp" />

	</body>

</html>

