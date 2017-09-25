<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.UserBeans"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<title>ユーザー情報参照</title>
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
<!-- オリジナルCSS読み込み -->
<link href="/AttendanceRecord/jsp/css/original/common.css" rel="stylesheet">
<!-- カレンダー  -->
<link rel="stylesheet" type="text/css" href="/AttendanceRecord/jsp/css2/calendar.css">
<link rel="stylesheet" type="text/css" href="/AttendanceRecord/jsp/css2/custom_2.css">
<link rel="stylesheet" type="text/css" href="/AttendanceRecord/jsp/css2/demo.css">

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
	<!-- Loader -->
	<div id="fh5co-page">
		<section id="fh5co-header">
			<div class="container">
				<nav role="navigation">
					<ul class="pull-left left-menu">
						<%
							UserBeans loginUser= (UserBeans)session.getAttribute("loginUser");
							UserBeans u = (UserBeans) request.getAttribute("userInfo");
						%>
						<li class="fh5co-cta-btn">
							<c:choose>
								<c:when test="${loginUser.getId() == 1}"><a href="UserList">戻る</a></c:when>
								<c:otherwise><a href="WorkSituationRegistration">戻る</a></c:otherwise>
							</c:choose>
							　　<a href="UserUpdate?id=<%=u.getId()%>">更新</a>
						</li>

					</ul>
				</nav>
			</div>
		</section>

		<section id="fh5co-hero" class="no-js-fullheight" style="background-image: url(/AttendanceRecord/jsp/images/full_image_2.jpg);" data-next="yes">
			<div class="container">
				<div class="fh5co-intro no-js-fullheight">
					<div class="fh5co-intro-text">
						<div class="wrapper2">
							<div class="fh5co-left-position"><br><br>
								<h2 class="animate-box">User Detail</h2>
							</div>
							<div class="animate-box" style="color:white">
								ログイン : <%=u.getLoginId()%> <br>
								<br> 名前 : <%=u.getName()%> <br>
								<br> 役職 : <%=u.getPosition()%> <br>
								<br> 生年月日 : <%=u.getFormatBirthDate()%> <br>
								<br> 登録日時 : <%=u.getFormatCreateDate()%> <br>
								<br> 更新日時 : <%=u.getFormatUpdateDate()%> <br>
								<br>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<section id="fh5co-features">
			<div class="container">
				<form name="form1" action="WorkSituationEditHistory" method="get">
				<input type="hidden" name="id" value="<%=u.getId()%>">
				<div class="button_wrapper" style="text-align:left;">
					<button class="btn btn-default" style="border: 2px solid black;">勤務状況編集履歴</button>
				</div>
				</form>
				<div class="fh5co-center-position">
					<div class="fh5co-lead animate-box fadeInUp animated" aline="center">Calendar</div><br>
				</div>
				<div class="custom-calendar-wrap">
					<div id="custom-inner" class="custom-inner">
					<form action="DailyWorkCheck" method="get">
					<input type="hidden" name="id" value=<%=u.getId()%>>
						<div class="custom-header clearfix">
							<nav>
								<span id="custom-prev" class="custom-prev"></span>
								<span id="custom-next" class="custom-next"></span>
							</nav>
							<h2 id="custom-month" class="custom-month"></h2>
							<h3 id="custom-year" class="custom-year"></h3>
						</div>
						<div id="calendar" class="fc-calendar-container"></div>
						</form>
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
		<script type="text/javascript" src="/AttendanceRecord/jsp/js/jquery.calendario.js"></script>
		<script type="text/javascript" src="/AttendanceRecord/jsp/js/data.js"></script>
		<script type="text/javascript">
			$(function() {
				var id = '<%=u.getId()%>';

				var transEndEventNames = {
					'WebkitTransition' : 'webkitTransitionEnd',
					'MozTransition' : 'transitionend',
					'OTransition' : 'oTransitionEnd',
					'msTransition' : 'MSTransitionEnd',
					'transition' : 'transitionend'
				}, transEndEventName = transEndEventNames[Modernizr
						.prefixed('transition')], $wrapper = $('#custom-inner'), $calendar = $('#calendar'), cal = $calendar
						.calendario({
							onDayClick : function($el, $contentEl,
									dateProperties) {

								if ($contentEl.length > 0) {
									showEvents($contentEl, dateProperties);

								}

							},
							caldata : codropsEvents,
							displayWeekAbbr : true
						}),
				$month = $('#custom-month').html('<input type="hidden" name="month" value=' + cal.getMonth() +'>' + cal.getMonthName()),
				$year = $('#custom-year').html('<input type="hidden" name="year" value=' + cal.getYear() +'>' + cal.getYear());

				$('#custom-next').on('click', function() {
					cal.gotoNextMonth(updateMonthYear);
					$month = $('#custom-month').html('<input type="hidden" name="month" value=' + cal.getMonth() +'>' + cal.getMonthName());
					$year = $('#custom-year').html('<input type="hidden" name="year" value=' + cal.getYear() +'>' + cal.getYear());
				});
				$('#custom-prev').on('click', function() {
					cal.gotoPreviousMonth(updateMonthYear);
					$month = $('#custom-month').html('<input type="hidden" name="month" value=' + cal.getMonth() +'>' + cal.getMonthName());
					$year = $('#custom-year').html('<input type="hidden" name="year" value=' + cal.getYear() +'>' + cal.getYear());
				});
				$('#custom-month').on('click', function() {
					location.href = 'MonthlyWorkCheck?id=' + id + '&year=' + cal.getYear() + '&month=' + cal.getMonth();
				});


				function updateMonthYear() {
					$month.html(cal.getMonthName());
					$year.html(cal.getYear());
				}

				// just an example..
				function showEvents($contentEl, dateProperties) {

					hideEvents();

					var $events = $('<div id="custom-content-reveal" class="custom-content-reveal"><h4>Events for '
							+ dateProperties.monthname
							+ ' '
							+ dateProperties.day
							+ ', '
							+ dateProperties.year
							+ '</h4></div>'), $close = $(
							'<span class="custom-content-close"></span>').on(
							'click', hideEvents);

					$events.append($contentEl.html(), $close).insertAfter(
							$wrapper);

					setTimeout(function() {
						$events.css('top', '0%');
					}, 25);

				}
				function hideEvents() {

					var $events = $('#custom-content-reveal');
					if ($events.length > 0) {

						$events.css('top', '100%');
						Modernizr.csstransitions ? $events.on(
								transEndEventName, function() {
									$(this).remove();
								}) : $events.remove();

					}

				}

			});
		</script>
</body>
</html>

