<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.UserBeans"%>
<%@ page import="dao.DaoUtil"%>
<%@ page import="java.sql.Time"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>勤務状況登録</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
<meta name="author" content="FREEHTML5.CO" />

<!--
	//////////////////////////////////////////////////////

	FREE HTML5 TEMPLATE
	DESIGNED & DEVELOPED by FREEHTML5.CO

	Website: 		http://freehtml5.co/
	Email: 			info@freehtml5.co
	Twitter: 		http://twitter.com/fh5co
	Facebook: 		https://www.facebook.com/fh5co

	//////////////////////////////////////////////////////
	 -->

<!-- Facebook and Twitter integration -->
<meta property="og:title" content="" />
<meta property="og:image" content="" />
<meta property="og:url" content="" />
<meta property="og:site_name" content="" />
<meta property="og:description" content="" />
<meta name="twitter:title" content="" />
<meta name="twitter:image" content="" />
<meta name="twitter:url" content="" />
<meta name="twitter:card" content="" />

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
<link rel="shortcut icon" href="favicon.ico">

<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,600,400italic,700' rel='stylesheet' type='text/css'>

<!-- Animate.css -->
<link rel="stylesheet" href="/AttendanceRecord/jsp/css/animate.css">
<!-- Flexslider -->
<link rel="stylesheet" href="/AttendanceRecord/jsp/css/flexslider.css">
<!-- Icomoon Icon Fonts-->
<link rel="stylesheet" href="/AttendanceRecord/jsp/css/icomoon.css">
<!-- Magnific Popup -->
<link rel="stylesheet" href="/AttendanceRecord/jsp/css/magnific-popup.css">
<!-- Bootstrap  -->
<link rel="stylesheet" href="/AttendanceRecord/jsp/css/bootstrap.css">


<!--
	Default Theme Style
	You can change the style.css (default color purple) to one of these styles



	-->
<link rel="stylesheet" href="/AttendanceRecord/jsp/css/style.css">

<!-- Styleswitcher ( This style is for demo purposes only, you may delete this anytime. ) -->
<link rel="stylesheet" id="theme-switch" href="/AttendanceRecord/jsp/css/style.css">
<!-- End demo purposes only -->


<style>
/* For demo purpose only */

/*
	GREEN
	8dc63f
	RED
	FA5555
	TURQUOISE
	27E1CE
	BLUE
	2772DB
	ORANGE
	FF7844
	YELLOW
	FCDA05
	PINK
	F64662
	PURPLE
	7045FF

	*/

/* For Demo Purposes Only ( You can delete this anytime :-) */
#colour-variations {
	padding: 10px;
	-webkit-transition: 0.5s;
	-o-transition: 0.5s;
	transition: 0.5s;
	width: 140px;
	position: fixed;
	left: 0;
	top: 100px;
	z-index: 999999;
	background: #fff;
	/*border-radius: 4px;*/
	border-top-right-radius: 4px;
	border-bottom-right-radius: 4px;
	-webkit-box-shadow: 0 0 9px 0 rgba(0, 0, 0, .1);
	-moz-box-shadow: 0 0 9px 0 rgba(0, 0, 0, .1);
	-ms-box-shadow: 0 0 9px 0 rgba(0, 0, 0, .1);
	box-shadow: 0 0 9px 0 rgba(0, 0, 0, .1);
}

#colour-variations.sleep {
	margin-left: -140px;
}

#colour-variations h3 {
	text-align: center;;
	font-size: 11px;
	letter-spacing: 2px;
	text-transform: uppercase;
	color: #777;
	margin: 0 0 10px 0;
	padding: 0;;
}

#colour-variations ul, #colour-variations ul li {
	padding: 0;
	margin: 0;
}

#colour-variations ul {
	margin-bottom: 20px;
	float: left;
}

#colour-variations li {
	list-style: none;
	display: inline;
}

#colour-variations li a {
	width: 20px;
	height: 20px;
	position: relative;
	float: left;
	margin: 5px;
}

#colour-variations li a[data-theme="style"] {
	background: #8dc63f;
}

#colour-variations li a[data-theme="red"] {
	background: #FA5555;
}

#colour-variations li a[data-theme="turquoise"] {
	background: #27E1CE;
}

#colour-variations li a[data-theme="blue"] {
	background: #2772DB;
}

#colour-variations li a[data-theme="orange"] {
	background: #FF7844;
}

#colour-variations li a[data-theme="yellow"] {
	background: #FCDA05;
}

#colour-variations li a[data-theme="pink"] {
	background: #F64662;
}

#colour-variations li a[data-theme="purple"] {
	background: #7045FF;
}

#colour-variations a[data-layout="boxed"], #colour-variations a[data-layout="wide"]
	{
	padding: 2px 0;
	width: 48%;
	border: 1px solid #ededed;
	text-align: center;
	color: #777;
	display: block;
}

#colour-variations a[data-layout="boxed"]:hover, #colour-variations a[data-layout="wide"]:hover
	{
	border: 1px solid #777;
}

#colour-variations a[data-layout="boxed"] {
	float: left;
}

#colour-variations a[data-layout="wide"] {
	float: right;
}

.option-toggle {
	position: absolute;
	right: 0;
	top: 0;
	margin-top: 5px;
	margin-right: -30px;
	width: 30px;
	height: 30px;
	background: #8dc63f;
	text-align: center;
	border-top-right-radius: 4px;
	border-bottom-right-radius: 4px;
	color: #fff;
	cursor: pointer;
	-webkit-box-shadow: 0 0 9px 0 rgba(0, 0, 0, .1);
	-moz-box-shadow: 0 0 9px 0 rgba(0, 0, 0, .1);
	-ms-box-shadow: 0 0 9px 0 rgba(0, 0, 0, .1);
	box-shadow: 0 0 9px 0 rgba(0, 0, 0, .1);
}

.option-toggle i {
	top: 2px;
	position: relative;
}

.option-toggle:hover, .option-toggle:focus, .option-toggle:active {
	color: #fff;
	text-decoration: none;
	outline: none;
}
</style>
<!-- End demo purposes only -->


<!-- Modernizr JS -->
<script src="/AttendanceRecord/jsp/js/modernizr-2.6.2.min.js"></script>
<!-- FOR IE9 below -->
<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

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

	<!-- jQuery -->
	<script src="/AttendanceRecord/jsp/js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="/AttendanceRecord/jsp/js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="/AttendanceRecord/jsp/js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="/AttendanceRecord/jsp/js/jquery.waypoints.min.js"></script>
	<!-- Flexslider -->
	<script src="/AttendanceRecord/jsp/js/jquery.flexslider-min.js"></script>
	<!-- Magnific Popup -->
	<script src="/AttendanceRecord/jsp/js/jquery.magnific-popup.min.js"></script>
	<script src="/AttendanceRecord/jsp/js/magnific-popup-options.js"></script>

	<!-- For demo purposes only styleswitcher ( You may delete this anytime ) -->
	<script src="/AttendanceRecord/jsp/js/jquery.style.switcher.js"></script>
	<script>
		$(function() {
			$('#colour-variations ul').styleSwitcher({
				defaultThemeId : 'theme-switch',
				hasPreview : false,
				cookie : {
					expires : 30,
					isManagingLoad : true
				}
			});
			$('.option-toggle').click(function() {
				$('#colour-variations').toggleClass('sleep');
			});
		});
	</script>
	<!-- End demo purposes only -->

	<!-- Main JS (Do not remove) -->
	<script src="/AttendanceRecord/jsp/js/main.js"></script>

	<!--
	INFO:
	jQuery Cookie for Demo Purposes Only.
	The code below is to toggle the layout to boxed or wide
	-->
	<script src="/AttendanceRecord/jsp/js/jquery.cookie.js"></script>
	<script>
		$(function() {

			if ($.cookie('layoutCookie') != '') {
				$('body').addClass($.cookie('layoutCookie'));
			}

			$('a[data-layout="boxed"]').click(function(event) {
				event.preventDefault();
				$.cookie('layoutCookie', 'boxed', {
					expires : 7,
					path : '/'
				});
				$('body').addClass($.cookie('layoutCookie')); // the value is boxed.
			});

			$('a[data-layout="wide"]').click(function(event) {
				event.preventDefault();
				$('body').removeClass($.cookie('layoutCookie')); // the value is boxed.
				$.removeCookie('layoutCookie', {
					path : '/'
				}); // remove the value of our cookie 'layoutCookie'
			});
		});
	</script>

</body>
</html>

