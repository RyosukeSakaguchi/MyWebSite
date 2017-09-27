<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.UserBeans"%>
<%@ page import="beans.WorkSituationBeans"%>
<!DOCTYPE html>
<html class="no-js">

	<head>
		<!-- 共通header.jsp読み込み-->
		<jsp:include page="header.jsp" />
		<title>日別勤務状況</title>
		<!-- オリジナルCSS読み込み -->
		<link href="/AttendanceRecord/jsp/css/original/common.css" rel="stylesheet">
	</head>

	<body>
		<%
			UserBeans userInfo = (UserBeans) request.getAttribute("userInfo");
		%>
		<div id="fh5co-page" style="width:95%">
			<ul class="pull-left left-menu">
				<br>　　　　　<a class="btn btn-default" style="border: 2px solid black;" href="UserDetail?id=<%=userInfo.getId()%>">戻る</a>
			</ul>
			<ul class="pull-right center-right">
				<br><a class="btn btn-default" style="border: 2px solid black;" href="Logout">ログアウト</a>
			</ul>
		</div>

		<section id="fh5co-projects">
			<div class="fh5co-overlay"></div><br>
			<div class="container" style="width: 100%;">
				<%
					String name = (String)request.getAttribute("name");
					int year = (int)request.getAttribute("year");
					int month = (int)request.getAttribute("month");
					int date = (int)request.getAttribute("date");
				%>
				<div style="text-align: center;"><%=year%>年 <%=month%>月<%=date%>日 <%=name%></div><br>
				<div align="center">
					<br><font size="5" color="red">${errMsg}</font><br><br>
					<font size="5" color="red">${scsMsg}</font>
				</div>
				<div class="fh5co-intro js-fullheight">
					<div class="fh5co-intro-text">
						<div class="fh5co-center-position">
							<div class="container" style="width: 90%;">
								<div class="row">
									<div>
										<div class="wrapper">
											<div>
												<ul class="pull-left left-menu">
													<a class="btn btn-danger" href="WorkSituationDelete?id=<%=userInfo.getId()%>&year=<%=year%>&month=<%=month%>&date=<%=date%>">Delete</a>
													<a class="btn btn-success" href="WorkSituationEdit?id=<%=userInfo.getId()%>&year=<%=year%>&month=<%=month%>&date=<%=date%>">Edit</a>
												</ul>
											</div>
											<div>
												<form action="OutputCSV" method="post">
													<button class="btn btn-warning" type="submit">csvに出力</button>
													<input type="hidden" value="<%=userInfo.getId()%>" name="id">
													<input type="hidden" value="<%=year%>" name="year">
													<input type="hidden" value="<%=month%>" name="month">
													<input type="hidden" value="<%=date%>" name="date">
												</form>
											</div>
										</div>
										<br>
										<table class="table table-striped">
											<thead>
												<tr>
													<th style="text-align: center;">勤務状況</th>
													<th style="text-align: center;">勤務開始時間</th>
													<th style="text-align: center;">勤務終了時間</th>
													<th style="text-align: center;">休憩時間</th>
													<th style="text-align: center;">勤務時間</th>
													<th style="text-align: center;">残業時間</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<%
														List<WorkSituationBeans> workSituationList = (List<WorkSituationBeans>) request.getAttribute("workSituationList");
														for(WorkSituationBeans workSituation : workSituationList){
													%>

													<td style="text-align: center;">
														<%=workSituation.getWorkSitu()%>
													</td>
													<td style="text-align: center;">
														<%=workSituation.getWorkStart()%>
													</td>
													<td style="text-align: center;">
														<%=workSituation.getWorkEnd()%>
													</td>
													<td style="text-align: center;">
														<%=workSituation.getBreakTime()%>
													</td>
													<td style="text-align: center;">
														<%=workSituation.getWorkTime()%>
													</td>
													<td style="text-align: center;">
														<%=workSituation.getOvertime()%>
													</td>
													<%
														}
													%>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		<!-- 共通footer.jsp読み込み-->
		<jsp:include page="footer.jsp" />

	</body>

</html>

