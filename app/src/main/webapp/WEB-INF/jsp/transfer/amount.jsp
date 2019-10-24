<!DOCTYPE html>
<%@include file="../taglibs.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>ATM System Login: Input Pin</title>
<!-- Loading Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Loading Flat UI -->
<link href="css/flat-ui.css" rel="stylesheet">
<link href="css/demo.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">

<link rel="shortcut icon" href="img/favicon.ico">
<style>
#confirmPin {
	display: block;
	float: left;
	margin-top: 1rem;
	margin-left: 0px;
	width: 100%;
}

#confirmPin a {
	padding: 0px;
}
</style>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
      <script src="dist/js/vendor/html5shiv.js"></script>
      <script src="dist/js/vendor/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">
		<div class="demo-headline" style="padding: 0px;">
			<h1 class="demo-logo">
				ATM <small>24 Hour Syariah Saving</small>
			</h1>
		</div>
		<!-- /demo-headline -->
		<div class="row">
			<div class="col-xs-12" id="screenView">
				<!-- Place <h1></h1> below -->

				<!-- Replace Video with Animated Gif or Animated SVG -->
				<div class="row">
					<div class="col-xs-8 text-center"
						style="border-right: solid 3px #CCC">
						<h1 class="demo-section-title text-uppercase text-center">Input
							Amount Transfer</h1>
						<input id="destination" type="hidden" value="${ destination }">
						<input type="text" id="userPinInput" class="form-control"
							maxlength="4" style="text-align: center;" required="true" />
						<div id="confirmPin">
							<a href="#"
								class="btn btn-block btn-lg btn-success text-uppercase">Confirm</a>
						</div>
						<div id="cancelTransaction">
							<a href="${pageContext.request.contextPath}/transaction"
								class="btn btn-block btn-lg btn-danger text-uppercase"><span
								class="fui-cross"></span> Cancel</a>
						</div>
						<span style="color: red;">${ message }</span>
						<!-- // Pin Pad -->
					</div>
					<div class="col-xs-4">
						<div style="margin-top: 8rem;">
							<img src="img/CardInsertedGreenLight.png" style="width: 16em;" />
						</div>
					</div>
					<!-- /.col-xs-4 -->
				</div>
				<!-- // END login screen  -->
			</div>
		</div>
		<!-- // END row-->
	</div>
	<!-- /container -->
	<script src="js/jquery.min.js"></script>
	<script src="js/video.js"></script>
	<script src="js/flat-ui.min.js"></script>
	<script src="js/application.js"></script>
	<script>
		videojs.options.flash.swf = "js/video-js.swf";
		$(document)
				.ready(
						function() {
							$("#confirmPin")
									.click(
											function() {
												window.location
														.replace("${pageContext.request.contextPath}/transferConfirm?destination="
																+ $(
																		"#destination")
																		.val()
																+ "&amount="
																+ $(
																		"#userPinInput")
																		.val());
											});
						});
	</script>
</body>
</html>