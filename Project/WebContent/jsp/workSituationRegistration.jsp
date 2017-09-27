<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.UserBeans"%>
<%@ page import="dao.DaoUtil"%>
<%@ page import="java.sql.Time"%>
<!DOCTYPE html>
<html class="no-js">

	<head>
		<!-- 共通header.jsp読み込み-->
		<jsp:include page="header.jsp" />
		<title>勤務状況登録</title>
	</head>

	<body>
		<div class="fh5co-loader"></div>
		<div id="fh5co-page">
			<section id="fh5co-header">
				<div class="container">
					<nav role="navigation">
						<h1 id="fh5co-logo"></h1>
							<%
								UserBeans loginUser= (UserBeans)session.getAttribute("loginUser");
							%>
						<ul class="pull-left left-menu">
							<li class="fh5co-cta-btn"><a href="UserDetail?id=<%=loginUser.getId()%>">あなたの情報</a></li>
							<li class="fh5co-cta-btn"><a href="UserUpdate?id=<%=loginUser.getId()%>">情報更新</a></li>
						</ul>
						<ul class="pull-right right-menu">
							<li class="fh5co-cta-btn"><a href="Logout">ログアウト</a></li>
						</ul>
					</nav>
				</div>
			</section>

			<section id="fh5co-hero" class="js-fullheight" style="background-image: url(/AttendanceRecord/jsp/images/hero_bg.jpg);" data-next="yes">
				<div class="fh5co-overlay"></div>
				<div class="container">
					<div class="fh5co-intro js-fullheight">
						<div class="fh5co-intro-text">
							<div class="fh5co-center-position">
								<h2 class="animate-box">Work Situation Registration</h2>
								<div align="center">
									<font size="5" color="red">${errMsg}</font>
								</div><br>
								<div align="center">
									<font size="5" color="#99FF33">${confMsg}</font>
								</div>
								<p class="animate-box"></p>
								<%
									boolean result1= (boolean)request.getAttribute("result1");
									boolean result2= (boolean)request.getAttribute("result2");
									boolean result3= (boolean)request.getAttribute("result3");
									if(result1){
								%>

								<div align="center">
									<br><font size="5" color="red">${overStartTimeMsg}</font><br><br>
								</div>
								<form action="WorkSituationRegistration" method="post">
									<input type="hidden" name="id" value="<%=loginUser.getId()%>">
									<input type="hidden" name="situation" value="start">
									<button class="btn btn-outline" type="submit" style="color: red;"><i class="icon-play2" style="color: white;"></i> Start</button>
								</form>
								<%
									}else if(result2){
								%>
										<div align="center">
											<br><font size="5" color="red">${overEndTimeMsg}</font>
										</div>
										<form action="WorkSituationRegistration?id=<%=loginUser.getId()%>" method="post">
										<br>
										<div class="form-group" style="margin-left: 200px;">
											<%
												Time breakTimeMaster = DaoUtil.getTime(1, "break_time");
											%>
											<label for="inputName" class="col-sm-2 control-label" style="width: 100px;">休憩時間</label>
											<div class="col-sm-3">
												<input type="time" value="<%=breakTimeMaster%>"  class="form-control" id="inputName" name="breakTime" onblur="isEmpty(this)" style="background: white; height: 35px; width: 200px;">
											</div>
										</div>
										<br><br>
											<input type="hidden" name="situation" value="end" >
											<button name="situation" value="end" type="submit"  class="btn btn-outline" style="color: red;"><i class="icon-play2" style="color: white;"></i> End</button>

										</form>

								<%
									}else if(result3){
								%>
											<div align="center">
												<br><font size="5" color="white">お疲れ様でした。</font>
											</div>

								<%

									}
								%>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>

		<!-- 共通footer.jsp読み込み-->
		<jsp:include page="footer.jsp" />

	</body>

</html>

