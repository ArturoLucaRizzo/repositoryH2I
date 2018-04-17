$(document).ready(function(){
	var urls;
	

	$("button[name='rem']").click(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		if (window.confirm("vuoi eliminare "+ document.getElementById("name").textContent+" ?")) {
			urls="delete"
			ajaxPost();
        }

	});	
	
	$("button[name='enable']").click(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		if (window.confirm("vuoi abilitare/disabilitare "+ document.getElementById("name").textContent+" ?")) {
			urls="enable"
			ajaxPost();
        }
	});	
	
	
	function ajaxPost(){

		var removeDTO= {
				"success" : "true",
				"parameter" : document.getElementById("email").textContent
		}

		$.ajax({
			type: "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url: urls,
			data: JSON.stringify(removeDTO), // Note it is important
			success :function(data) {
			if(data.success=="ok"){
				
		        $("#box").load("forElements");
			}else{
				alert("errore generico in ajax")
			}
			

			}

		});


	}



})
