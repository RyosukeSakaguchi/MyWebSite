<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="beans.UserBeans"%>
<%@ page import="beans.PositionBeans"%>
<%@ page import="dao.DaoUtil"%>
<%@ page import="dao.WorkSituationDao"%>
<%@ page import="java.util.List "%>
<%@ page import="common.UtilLogic"%>
<!DOCTYPE html>
<html class="no-js">

	<head>
		<!-- 共通header.jsp読み込み-->
		<jsp:include page="header.jsp" />
		<title>ユーザー一覧</title>
		<!-- オリジナルCSS読み込み -->
		<link href="/AttendanceRecord/jsp/css/original/common.css" rel="stylesheet">
		<script src="/AttendanceRecord/jsp/js/modernizr.custom.63321.js"></script>
	</head>

	<body>
		<div class="fh5co-loader"></div>
		<div id="fh5co-page">

			<section id="fh5co-header">
				<div class="container">
					<nav role="navigation">
						<h1 id="fh5co-logo"></h1>
						<ul class="pull-right right-menu">
							<li class="fh5co-cta-btn"><a href="SignUp">新規登録</a></li>
							<li class="fh5co-cta-btn"><a href="Logout">ログアウト</a></li>
						</ul>
					</nav>
				</div>
			</section>

			<section id="fh5co-hero" style="background-image: url(/AttendanceRecord/jsp/images/hero_bg.jpg); height: 600px;" data-next="yes">
				<div class="fh5co-overlay"></div>
				<div class="container">
					<div class="fh5co-intro" style="height: 700px;">
						<div class="fh5co-intro-text">
							<div class="wrapper2">
								<div class="fh5co-left-position"><br> <br>
									<h2 class="animate-box">User List</h2>
									<font size="5" color="white">${errMsg}</font>
								</div>
								<div class="fh5co-right-position">
									<form action="UserList" method="POST">
										<div class="form-group">
											<label for="inputName" class="col-sm-2 control-label" style="width: 200px;">ログインID</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" name="login_id" value="${login_id}" onblur="isEmpty(this)" style="background: white; height: 35px; width: 200px;">
											</div>
										</div><br><br>
										<div class="form-group">
											<label for="inputName" class="col-sm-2 control-label" style="width: 200px;">ユーザー名</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" name="name" value="${name}" onblur="isEmpty(this)" style="background: white; height: 35px; width: 200px;">
											</div>
										</div><br><br>
										<div class="form-group">
											<label for="inputName" class="col-sm-2 control-label" style="width: 200px;">役職</label>
											<div class="col-sm-3">
												<select class="form-control" name="position" style="background: white; height: 35px; width: 200px;">
													<%
														String position = (String)request.getAttribute("position");
														String workSituation = (String)request.getAttribute("workSituation");
														List<PositionBeans> positonList = DaoUtil.findAllPosition();
														request.setAttribute("positonList",positonList);
													%>
													<c:choose>
														<c:when test="${position == null}">
															<option value="" ></option>
															<c:forEach var="obj" items="${positonList}">
																<option value="${obj.position}" >${obj.position}</option>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<option value="" ></option>
															<%
															 for(PositionBeans p : positonList){
																 if(p.getPosition().equals(position)){
															%>

															<option value="<%=p.getPosition()%>" selected ><%=p.getPosition()%></option>

															<%
																 }else{

															%>

															<option value="<%=p.getPosition()%>"><%=p.getPosition()%></option>

															<%
																 }
															 }

															%>
														</c:otherwise>
													</c:choose>
												</select>
											</div>
										</div><br><br>
										<div class="form-group" style="width:800px;">
											<label for="inputName" class="col-sm-2 control-label" style="width: 200px;">生年月日</label>
											<div class="col-sm-3">
												<input type="date" class="form-control"  name="birth_date_from" value="${birth_date_from}" onblur="isEmpty(this)" style="background: white; height: 35px; width: 200px;">
											</div>
											<label for="inputName" class="col-sm-2 control-label" style="width: 50px;">　〜</label>
											<div class="col-sm-3">
												<input type="date" class="form-control"  name="birth_date_to" value="${birth_date_to}" onblur="isEmpty(this)" style="background: white; height: 35px; width: 200px;">
											</div>
										</div><br><br>
										<div class="form-group">
											<label for="inputName" class="col-sm-2 control-label" style="width: 200px;">勤務状況</label>
											<div class="col-sm-3">
												<select class="form-control" name="workSituation" style="background: white; height: 35px; width: 200px;">
												<%if(workSituation == null){ %>
													<option value=""  selected></option>
													<option value="勤務中">勤務中</option>
													<option value="帰宅" >帰宅</option>
												<%}else{%>
													<option value="" <% if(workSituation.equals("")){%> selected<% } %> ></option>
													<option value="勤務中"<% if(workSituation.equals("勤務中")){%> selected<% } %> >勤務中</option>
													<option value="帰宅" <% if(workSituation.equals("帰宅")){%> selected<% } %> >帰宅</option>
												<%} %>
												</select>
											</div>
										</div>
										<br> <br> <br>
										<div class="form-group">
											<div class="button_wrapper">
												<button class="btn btn-info" type="submit" >検索</button>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>

			<section id="fh5co-projects">
				<div class="container">
					<font size="5" color="red">${noCheckMsg}</font><br><br>
					<form name="form1" action="UserDelete" method="get">
							<input type="button" class="btn btn-primary" value="全て選択" onClick="BoxChecked(true);">
							<input type="button" class="btn btn-warning" value="全て未選択" onClick="BoxChecked(false);" >
							　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
							<button class="btn btn-danger" type="submit">選択したユーザーを削除</button><br><br>

						<div class="row">

							<a  role="button" style ="color : #85919d" >
							<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							</a>
							5/8
							<a  role="button" style ="color : #85919d">
							<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
							</a>

							<div class="table-responsive">
								<table class="table table-striped">
									<thead>
										<tr>
											<th></th>
											<th></th>
											<th>Login ID</th>
											<th>User Name</th>
											<th>Birth Day</th>
											<th></th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<%
											List<UserBeans> u = (List<UserBeans>) request.getAttribute("userList");
											UserBeans loginUser= (UserBeans)session.getAttribute("loginUser");
											Date now = new Date();
											SimpleDateFormat y = new SimpleDateFormat("yyyy");
											SimpleDateFormat m = new SimpleDateFormat("MM");
											int year = Integer.parseInt(y.format(now));
											int month = Integer.parseInt(m.format(now));

											for (int i = 0; i < u.size(); i++) {
												if(u.get(i).getId() != 1){
													boolean result = WorkSituationDao.isWorking(u.get(i).getLoginId());
													String titalOvertime = UtilLogic.totalOvertime(WorkSituationDao.findAll(u.get(i).getLoginId(), year, month));
													int titalOvertimeInt = UtilLogic.stringTimeToInt(titalOvertime);
										%>
										<tr>
											<td></td>
											<td><input type="checkbox" name="delListId[]" value="<%=u.get(i).getId()%>" onClick="DisChecked();"></td>
											<td><%=u.get(i).getLoginId()%></td>
											<td><%=u.get(i).getName()%></td>
											<td><%=u.get(i).getFormatBirthDate()%></td>
											<%
												if(!result){
											%>
												<td>帰宅
											<%
												}else{
											%>
											<td>勤務中
											<%
												}
												if(titalOvertimeInt >= 500000) {
											%>
											<font size="3" color="red">　残業時間超過</font>
											<%
												}
											%>
											</td>
											<td>
												<a class="btn btn-primary" href="UserDetail?id=<%=u.get(i).getId()%>">詳細</a>
												<a class="btn btn-success" href="UserUpdate?id=<%=u.get(i).getId()%>">更新</a>
												<a class="btn btn-danger" href="UserDelete?id=<%=u.get(i).getId()%>">削除</a>
											</td>
										</tr>
										<%		}
											}
										%>
									</tbody>
								</table>
							</div>
						</div>
					</form>
					<font size="3" color="red">${salaryErrMsg}</font><br>
					<form action="OutputCSV" method="post">
					<div class="col-sm-3" style="width: 220px">
						<input type="month" class="form-control" name="yearAndMonth" style="background: white; height: 35px; width: 200px;">
					</div>
					の全ユーザーの月給をcsvファイルに
					<button class="btn btn-warning" type="submit">出力</button>
					</form>
				</div>
			</section>
		</div>

		<!-- 共通footer.jsp読み込み-->
		<jsp:include page="footer.jsp" />

		<script type="text/javascript" src="/AttendanceRecord/jsp/js/delListGet.js"></script>
	</body>

</html>

