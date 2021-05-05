if(document.cookie){		
	window.location.replace("app.jsp");
}
		
$(document).ready(
	() => {

		$.ajax(
			{
				method: "GET",
				url: "GameServlet",
				data: {
					action: "shop",
					limit: 5
				},
				beforeSend: () => {
					$(".left").html("<div class='loader-container'><div class=\"loader\"></div>");
				},
				success: () => {
					setTimeout(() => {
						$(".left").load("index.jsp .left");
					}, 400)
				}
			}
		);
		
		$.ajax(
			{
				method: "GET",
				url: "GameServlet",
				data: {
					action: "shop",
					limit: 5,
					order: "DESC"
				},
				beforeSend: () => {
					$(".right").html("<div class='loader-container'><div class=\"loader\"></div>");
				},
				success: () => {
					setTimeout(() => {
						$(".right").load("index.jsp .right");
					}, 400)
				}
			}
		);
						
	}
);

function tryLogin(){
	$.ajax(
		{
			method: "POST",
			url: "LoginServlet",
			data: {
				username: $("#login-username").val(),
				password: $("#login-password").val()
			},
			success: () => {
				console.log("Login effettuato con successo");
				window.location.replace("app.jsp");
			},
			error: () => {
				console.log("Errore nel login");
				$("#login-fail").show();
			}	
		}
	);
}

function trySignIn(){
	$.ajax(
		{
			method: "POST",
			url: "SignInServlet",
			data: {
				username: $("#signin-username").val(),
				password: $("#signin-password").val(),
				password2: $("#signin-password-again").val(),
				email: $("#signin-email").val()
			},
			success: (data) => {
				console.log("Registrazione avvenuta con successo: " + data);
				$("#signin-message").html(data);
				$("#signin-message").css("color", "green");
				$("#signin-message").show();
			},
			error: (data) => {
				console.log("Fallimento nella registrazione: " + data.responseText);
				$("#signin-message").html(data.responseText);
				$("#signin-message").css("color", "red");
				$("#signin-message").show();
			}
		}
	);
}

