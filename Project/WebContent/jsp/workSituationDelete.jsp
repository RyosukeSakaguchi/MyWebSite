<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.UserBeans"%>
<%@ page import="beans.WorkSituationBeans"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html class="no-js">

	<head>
		<!-- 共通header.jsp読み込み-->
		<jsp:include page="header.jsp" />
		<title>勤務情報消去</title>
		<!-- オリジナルCSS読み込み -->
		<link href="/AttendanceRecord/jsp/css/original/common.css" rel="stylesheet">
	</head>

	<body>
		<div class="fh5co-loader"></div>
		<section id="fh5co-hero" class="no-js-fullheight" style="background-image: url(/AttendanceRecord/jsp/images/full_image_2.jpg);" data-next="yes">
			<div class="fh5co-overlay"></div>
			<div class="container">
				<div class="fh5co-intro no-js-fullheight">
					<div class="fh5co-intro-text">
						<div class="fh5co-center-position">
							<h2 class="animate-box">
								<font size="5">${confMsg1}<font color="red">${confMsg2}</font>${confMsg3}</font>
							</h2><br><br>
							<%
								List<WorkSituationBeans> workSituationList = (List<WorkSituationBeans>) request.getAttribute("workSituationList");
								UserBeans userInfo = (UserBeans) request.getAttribute("userInfo");
								int id = (int)request.getAttribute("id");
								int year = (int)request.getAttribute("year");
								int month = (int)request.getAttribute("month");
								int date = (int)request.getAttribute("date");
							%>
							<p class="animate-box">
							<div class="wrapper">
								<div>
								<%
								if(request.getAttribute("date") == null){
								%>
									<a href="MonthlyWorkCheck?id=<%=userInfo.getId()%>&year=<%=year%>&month=<%=month%>" class="btn btn-outline "><i class="icon-play2"></i> Cancel</a>
								<%
								}else{
								%>
									<a href="DailyWorkCheck?id=<%=userInfo.getId()%>&year=<%=year%>&month=<%=month%>&date=<%=date%>" class="btn btn-outline "><i class="icon-play2"></i> Cancel</a>
								<%
								}
								%>
								</div>
								<div>
								<form action="WorkSituationDelete" method="post">
									<input type="hidden" name="id" value="<%=userInfo.getId()%>" >
									<input type="hidden" name="year" value="<%=year%>" >
									<input type="hidden" name="month" value="<%=month%>" >
									<input type="hidden" name="date" value="<%=date%>" >
									<%
									for(WorkSituationBeans workSituation : workSituationList){
									%>
									<input type="hidden" name="workSituationIdList[]" value="<%=workSituation.getId()%>" >
									<%
									}
									%>
									<button class="btn btn-outline"><i class="icon-play2"></i> OK</button>
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

