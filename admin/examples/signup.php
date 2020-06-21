<?php

include('connection.php');

if(isset($_POST['submit'])){
	$username = $_POST['username'];
	//$password = base64_encode (mysqli_real_escape_string($con, trim($_POST['pass'])));
	$cpass = base64_encode($_POST['cpass']);
	if(!empty($username) && !empty($cpass)){
		$query = mysqli_query($con, "select * from login where email = '$username'");
		if(mysqli_num_rows($query) == '0'){
			$query1 = mysqli_query($con, "INSERT INTO `login` (`email`,`password`,`status`) VALUES ('$username','$cpass','1')");
			echo "INSERT INTO `login` (`email`,`password`,`status`) VALUES ('$username','$cpass','1')";
			
			
		}
		else{
			echo '<p>An account already exists for this username. Please use a different address.</p>';
			$username = "";
		}
	}
	else{
		echo '<p>You must enter all of the sign-up data, including the desired password twice.</p>';
	}
}

?>
  <html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/css/materialize.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>index</title>
  </head>
  <style>

  body {
    background-color: #e0f2f1;
  }

  form p {
    font-size: 120%;
  }

  .ctrlqHeaderMast {
    height: 278px;
    background: #8bc348;
  }

  .ctrlqFormContent {
    color: #8bc348;
    padding: 20px 35px
  }

  .ctrlqFormContentWrapper {
    display: -webkit-box;
    display: -moz-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-orient: vertical;
    box-orient: vertical;
    -webkit-flex-direction: column;
    flex-direction: column
  }

  .ctrlqAccent {
    background-color: #8bc348;
    height: 8px;
  }
  .ctrlqCenteredContent {
    margin: auto;
    width: 600px; 
  }
  .ctrlqFormCard {
    background-color: #fff;
    margin-bottom: 48px;
    -webkit-box-shadow: 0 1px 4px 0 #8bc348;
    box-shadow: 0 1px 4px 0 #8bc348;
    word-wrap: break-word
  }

  .ctrlqFormCard:first-of-type {
    margin-top: -100px
  }

  .ctrlqHeaderTitle {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    font-size: 34px;
    line-height: 135%;
    max-width: 100%;
    min-width: 0%;
    margin-bottom: 22px
  }

  @media (max-width: 660px) {
    .ctrlqHeaderMast {
      height: 122px;
    }
    .ctrlqFormCard:first-of-type {
      margin-top: -50px;
    }

    .ctrlqCenteredContent {
      width: 90%;
    }
  }

  div.error {
    position: relative;
    top: -1rem;
    left: 0rem;
    font-size: 0.8rem;
    color: #FF4081;
    -webkit-transform: translateY(0%);
    -ms-transform: translateY(0%);
    -o-transform: translateY(0%);
    transform: translateY(0%);
  }

</style>
<body>
  <div class="ctrlqFormContentWrapper">
    <div class="ctrlqHeaderMast"></div>
    <div class="ctrlqCenteredContent">
      <div class="ctrlqFormCard">
        <div class="ctrlqAccent"></div>
        <div class="ctrlqFormContent">

          <form action="" method="post">  

            <div class="row">
              <div class="input-field col s12">
                <h4>Sign Up</h4>
                <p>All fields are required</p>
              </div>
            </div>

            <div class="row">
              <div class="input-field col s12">
                <input id="name" name="username" type="text" class="validate" required>
                <label for="name">username</label>
                <div id="e1"></div>
              </div>
            </div>

            <div class="row">
              <div class="input-field col s12">
                <input id="password" name="pass" type="password" class="validate" required>
                <label for="password">Password</label>
                <div id="e2"></div>
              </div>
            </div>

			<div class="row">
              <div class="input-field col s12">
                <input id="password" name="cpass" type="password" class="validate" required>
                <label for="password">Confirm Password</label>
                <div id="e2"></div>
              </div>
            </div>



            <div class="row">
              <div class="input-field">
				<button type="submit" name="submit" class="waves-effect waves-light btn-large" style="background: #8bc348;"><a href="signup.php" style="color: #fff;">Sign Up</a></button>
              </div>
            </div>
          </form>

        </div>
      </div>
    </div>
  </div>



  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.15.0/jquery.validate.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.15.0/additional-methods.min.js"></script>





</body>

</html>