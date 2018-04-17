$(document).ready(function(){

	$("button[name='rem']").click(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxPost();
	});	
	function ajaxPost(){

		var removeRespone= {
				"success" : "true",
				"parameter" : document.getElementById("email").textContent
		}

		$.ajax({
			type: "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url: "delete",
			data: JSON.stringify(removeRespone), // Note it is important
			success :function(data) {
			if(data.success=="ok"){
		        $("#box").load("forElements");
			}else{
				alert("errore generico nella remove")
			}
			

			}

		});


	}



})
