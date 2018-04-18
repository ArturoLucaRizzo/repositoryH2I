$(document).ready(function(){
	var urls;
	var number;
	var mail;
	
	
	$("button[name='addbutton']").click(function(event) {
		
		document.getElementById('boxModel').innerHTML ="organizzazione rimossa com successo";
		$("#boxModel").load("forUsers");

	});
	
	
	$("button[name='addUser']").click(function(event) {
		 mail = $('#us option:selected').text();
		if (window.confirm("vuoi aggiungere : "+ mail)) {
			urls="addAndEdit"
				ajaxPost2();
		}
		
		

	});
	
	
	
	

	$("button[name='rem']").click(function(event) {
		number=event.target.id;
		number=number.slice(-1);
		event.preventDefault();
		if (window.confirm("vuoi eliminare "+ document.getElementById("name"+number).textContent+" ?")) {
			urls="delete"
				ajaxPost();
		}

	});	
	$("button[name='return']").click(function(event) {
		event.preventDefault();
		$("#box").load("forElementsOrganizations");

	});	

	$("button[name='enable']").click(function(event) {
		number=event.target.id;
		number=number.slice(-1);
		event.preventDefault();
		if (window.confirm("vuoi abilitare/disabilitare "+ document.getElementById("name"+number).textContent+" ?")) {
			urls="enable";
				ajaxPost();
		}
	});	

	$("button[name='viewUser']").click(function(event) {
		number=event.target.id;
		number=number.slice(-1);
		event.preventDefault();
		urls="view";
			ajaxPost();
		
	});
	
	
	function ajaxPost2(){
		var ActiveDTO;
			ActiveDTO= {
					"success" : "true",
					"parameter" : mail


			}

		$.ajax({
			type: "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url: urls,
			data: JSON.stringify(ActiveDTO), // Note it is important
			success :function(data) {
				if(data.success=="ok"){
					if(data.parameter=="add"){
						
						$("#box").load("forElements");

					}



				}
				else{
					alert("errore generico in ajax");


				}
			}
		});
	}

	function ajaxPost(){
		var ActiveDTO;
		if(document.getElementById("email"+number)==null){
			ActiveDTO= {
					"success" : "true",
					"parameter" : document.getElementById("piva"+number).textContent
			}	
		}else{
			ActiveDTO= {
					"success" : "true",
					"parameter" : document.getElementById("email"+number).textContent


			}

		}

		$.ajax({
			type: "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url: urls,
			data: JSON.stringify(ActiveDTO), // Note it is important
			success :function(data) {
				if(data.success=="ok"){
					if(data.parameter=="user"){

						$("#box").load("forElements");

						if(urls=="delete"){
							document.getElementById('message').innerHTML ="Utente Rimosso con Successo";
							$('#myModal').modal('show');
						}
					}

					if(data.parameter=="organization"){
						$("#box").load("forElementsOrganizations");
						if(urls=="delete"){
							document.getElementById('message').innerHTML ="organizzazione rimossa com successo";
							$('#myModal').modal('show');
						}
					}
					if(data.parameter=="view"){
						$("#box").load("forElements");

					}
					if(data.parameter=="enable"){
						$("#box").load("forElements");

					}



				}
				else{
					alert("errore generico in ajax");


				}
			}
		});
	}

})

