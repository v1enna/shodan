$("#game-page").html("");

$(document).ready(
	() => {
		$.ajax(
			{
				type: "GET",
				url: "GameServlet",
				data: {
					action: "game",
					endpoint: "View/Game.jsp",
					game_id: new URLSearchParams(window.location.search).get("game")
				},
				beforeSend: () => {
					$("#game-page").html("<div class=\"loader loader-lowered\">");
				},
				success: (data) => {
					setTimeout(
						() => {
							$(".content").replaceWith(data.substring(0, data.lastIndexOf("\n")))
						}, 150)
				}
			}
		);
	}
);

$(document).off().on("click", "#add-to-cart", () => {
	console.log(0);
	
	$.ajax(
		{
			type: "POST",
			url: "CartServlet",
			data: {
				action: "addGame",
				cookie: navigator.cookieEnabled,
				game_id: $(".game-info-container").parent().attr("data-game-id"),
				jsession: window.location.href.substring(
					window.location.href.lastIndexOf("=") + 1
				),
				total: $(".last-row-total").text().split(" ")[1]
			},
			success: () => {
				$("#add-to-cart").html("Aggiunto <strong>" + name +"</strong> al carrello!");
				updateCart();
			},
			error: () => {
				$("#add-to-cart").html("Non è stato possibile aggiungere il gioco al carrello!");	
			}	
		}
	);
});