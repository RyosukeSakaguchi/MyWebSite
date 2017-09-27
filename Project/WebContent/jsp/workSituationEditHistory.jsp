<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.UserBeans"%>
<%@ page import="beans.WorkSituationBeans"%>
<%@ page import="beans.WorkSituationEditBeans"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html class="no-js">

	<head>
		<!-- 共通header.jsp読み込み-->
		<jsp:include page="header.jsp" />
		<title>勤務状況編集履歴</title>
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
			<div class="fh5co-overlay"></div>
			<div class="container" style="width: 100%;">
				<%
					String name = (String)request.getAttribute("name");
				%>
				<div style="text-align: center;"><%=name%>の編集履歴${dispMsg}</div><br><br>
				<div class="fh5co-intro js-fullheight">
					<div class="fh5co-intro-text">
						<div class="fh5co-center-position">
							<div class="container" style="width: 90%;">
								<div class="row">
									<div>
										<table class="table table-striped">
											<thead>
												<tr>
													<th style="text-align: center;">編集日時</th>
													<th style="text-align: center;">編集内容</th>
												</tr>
											</thead>
											<tbody>
												<%
													boolean result = (boolean)request.getAttribute("result");
													int count;
													List<WorkSituationEditBeans> workSituationEditList = (List<WorkSituationEditBeans>) request.getAttribute("workSituationEditList");
													if(result){
														count = workSituationEditList.size()-20;
													}else{
														count = workSituationEditList.size()-5;
													}
													for(int i = workSituationEditList.size()-1;i >= count ;i--){
												%>
												<tr>
													<td style="text-align: center;"><%=workSituationEditList.get(i).getEditDate()%></td>
													<td style="text-align: center;"><%=workSituationEditList.get(i).getEditContent()%></td>
												</tr>
												<%
														if(i == 0){
															break;
														}
													}
												%>
											</tbody>
										</table><br><br>
										<div class=button_wrapper >
											<%
												if(result){
											%>
												<a class="btn btn-primary" href="WorkSituationEditHistory?id=<%=userInfo.getId()%>" >最新5件を表示</a><br>
											<%
												}else{
											%>
												<a class="btn btn-primary" href="WorkSituationEditHistory?id=<%=userInfo.getId()%>&disp=20" >最新20件を表示</a><br>

											<%
												}
											%>
										</div>
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

