<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
 
<html>

<head>

<meta charset="UTF-8">

<title>FireBaseTest</title>

</head>

<body>

	<form onsubmit="return handle(event)">

		<h1>Login</h1>

		<label>Username:</label><br> <input type="text" id="search"
			name="search">

		<button type="submit" value="Submit">login</button>

	</form>


	<hr />

	<h2>Search for weather</h2>

	<form onsubmit="return handleSubmit(event)">

		<label for="username">Username:</label><br> <input type="text"
			id="username" name="username"><br> <label for="history">City:</label><br>

		<input type="text" id="history" name="history"><br> <input
			type="submit" value="Submit" id="submit">

	</form>

	<!-- The core Firebase JS SDK is always required and must be listed first -->

	<script src="https://www.gstatic.com/firebasejs/7.13.1/firebase-app.js"></script>



	<script src="https://www.gstatic.com/firebasejs/7.6.1/firebase-auth.js"></script>

	<script
		src="https://www.gstatic.com/firebasejs/7.6.1/firebase-database.js"></script>



	<!-- TODO: Add SDKs for Firebase products that you want to use

     https://firebase.google.com/docs/web/setup#available-libraries -->

	<script
		src="https://www.gstatic.com/firebasejs/7.13.1/firebase-analytics.js"></script>



	<script>
		// Your web app's Firebase configuration

		var firebaseConfig = {

			apiKey : "AIzaSyB1OjAkutr0z7bNn0NjorgD4Gbe2uZWaXw",

			authDomain : "cs-310-project-2.firebaseapp.com",

			databaseURL : "https://cs-310-project-2.firebaseio.com",

			projectId : "cs-310-project-2",

			storageBucket : "cs-310-project-2.appspot.com",

			messagingSenderId : "214844068069",

			appId : "1:214844068069:web:03f56921d09a7db939440c",

			measurementId : "G-R018PH3DDR"

		};

		// Initialize Firebase

		firebase.initializeApp(firebaseConfig);

		firebase.analytics();

		function handleSubmit(event) {

			event.preventDefault();

			const uName = document.getElementById('username').value;

			const city = document.getElementById('history').value;

			writeUserData(uName, city);

			return false;

		}

		function writeUserData(userName, cityName) {

			// push the city

			firebase.database().ref('users/' + userName + '/search_history')

			.push(cityName);

		}

		function handle(event) {

			event.preventDefault();

			const userName = document.getElementById('search').value;

			// TODO: remove

			document.querySelector('h1').innerText = userName;

			var historyRef = firebase.database().ref(

			'users/' + userName + '/search_history');

			historyRef.on('child_added', function(data) {

				// do something with data (console.log)

				console.log(data);

			});

		};
	</script>

</body>

</html>