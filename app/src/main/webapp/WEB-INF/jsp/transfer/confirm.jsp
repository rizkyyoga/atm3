<!DOCTYPE html>
<%@include file="../taglibs.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>ATM System: Transfer Funds</title>
<!-- Loading Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Loading Flat UI -->
<link href="css/flat-ui.css" rel="stylesheet">
<link href="css/demo.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">

<link rel="shortcut icon" href="img/favicon.ico">

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
				<h1 class="demo-section-title text-uppercase text-center">Transfer
					Funds</h1>

				<div class="row">

					<!-- 3/4 -->
					<!-- Balance -->
					<div class="col-xs-9">
						<form class="form-horizontal" method="POST"
							action="${pageContext.request.contextPath}/transfer">
							<input name="destination" type="hidden" value="${ destination }">
							<input name="amount" type="hidden" value="${ amount }"> <input
								name="reference" type="hidden" value="${ reference }">
							<div class="col-xs-12">
								<div class="tile">
									<h4 class="text-uppercase balance">
										<span>Reference</span>${ reference }
									</h4>
								</div>
							</div>
							<!-- Options -->
							<div class="col-xs-6">
								<div class="tile">
									<h5>From:</h5>
									<button type="button" class="btn btn-block btn-lg btn-inverse">
										<h6>${ accountNumber }</h6>
									</button>
								</div>
							</div>

							<div class="col-xs-6">
								<div class="tile">
									<h5>To:</h5>
									<button type="button" class="btn btn-block btn-lg btn-inverse">
										<h6>${ destination }</h6>
									</button>
								</div>
							</div>
							<!-- // END OPTIONS -->
							<div class="col-xs-12">
								<div class="tile">
									<h4 class="text-uppercase balance">
										<span>Amount</span>$${ amount }
									</h4>
								</div>
							</div>
							<div class="col-xs-12" style="margin-bottom: 2rem;">
								<button type="submit" class="btn btn-block btn-lg btn-success">
									<h6>
										<span class="fui-check"></span> Confirm Transfer
									</h6>
								</button>
							</div>
						</form>
					</div>
					<!-- 1/4 -->
					<div class="col-xs-3">
						<div class="tile">
							<a id="back"
								href="${pageContext.request.contextPath}/transaction"
								class="btn btn-lg btn-inverse text-uppercase btn-padding"><span
								class="fui-arrow-left"></span> Back</a>
						</div>
						<div class="tile">
							<a id="cancelTransactin"
								href="${pageContext.request.contextPath}/logout"
								class="btn btn-lg btn-danger text-uppercase btn-padding"><span
								class="fui-cross"></span> Exit</a>
						</div>
					</div>
				</div>
				<!-- // END OPTIONS-->
			</div>
			<!--  // END column-->
		</div>
		<!-- // END row-->
	</div>
	<!-- /container -->
	<script src="js/jquery.min.js"></script>
	<script src="js/video.js"></script>
	<script src="js/flat-ui.min.js"></script>
	<script src="js/application.js"></script>
	<script>
		videojs.options.flash.swf = "js/video-js.swf"
	</script>
</body>
</html>
