<!DOCTYPE html>
<%@include file="../taglibs.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>ATM System Options: Account Details</title>
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
				<h1 class="demo-section-title text-uppercase text-center">Account
					Details</h1>
				<h6 class="text-center">${ accountNumber }</h6>
				<br />
				<div class="row">
					<!-- 3/4 -->
					<!-- Balance -->
					<div class="col-xs-9">
						<div class="col-xs-12">
							<div class="tile">
								<h4 class="text-uppercase balance">
									<span>Balance</span>$${ balance }
								</h4>
							</div>
						</div>
						<!-- Options -->
						<div class="col-xs-4">
							<a href="${pageContext.request.contextPath}/withdraw">
								<div class="tile">
									<img src="img/quick_cash.png" alt="Withdraw Funds"
										class="tile-image">
									<h3 class="tile-title">Withdraw</h3>
									<p>Withdraw funds from account.</p>
								</div>
							</a>
						</div>
						<div class="col-xs-4">
							<a href="${pageContext.request.contextPath}/transferDestination">
								<div class="tile">
									<img src="img/transfer_money.png"
										alt="Transfer Funds to Another Account" class="tile-image">
									<h3 class="tile-title">Transfer</h3>
									<p>Transfer funds to another account.</p>
								</div>
							</a>
						</div>
						<div class="col-xs-4">
							<a href="${pageContext.request.contextPath}/viewTransaction">
								<div class="tile">
									<img src="img/deposit_vault.png" alt="View Last ${ nTransaction } Transaction"
										class="tile-image big-illustration">
									<h3 class="tile-title">View Last ${ nTransaction } Transaction</h3>
									<p>View Last ${ nTransaction } Transaction.</p>
								</div>
							</a>
						</div>
						<!-- // END OPTIONS -->
					</div>
					<!-- 1/4 -->
					<div class="col-xs-3">
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
