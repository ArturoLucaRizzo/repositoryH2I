$(document).ready(function(){
	var urls;
	var number;
	var type;
	var mail,name,surname;
	var piva,id,organization;

	$("button[name='addbutton']").click(function(event) {

		number=event.target.id;


		if(number=="add-organization"){

			organization	=	document.getElementById("organization-field").value;
			piva	=	document.getElementById("piva-field").value;	

			if((piva.length != 11)  ||  !(/^[0-9]+$/.test( piva ))  ){
				alert("Partita Iva non valida");
			}

			else if((organization.length==0)  ){

				alert("Inserisci un nome valido");

			}else	if (window.confirm("Vuoi aggiungere : "+ organization)) {
				urls="addAndEdit"
					ajaxPost3();
			}

		} else {
			if(number=="add-btn"){
				$("#boxModel").load("forUsers");
				$("button[name='addUser']").click(function() {
					mail = $('#us option:selected').text();
					if (window.confirm("Vuoi aggiungere : "+ mail)) {
						urls="addAndEdit"
							ajaxPost2();
					}	


				});


			}




		}


	});

	$("button[name='editbuttonuser']").click(function(event) {
		mail = document.getElementById("mail-field").value;
		name = document.getElementById("name-field").value;
		surname = document.getElementById("surname-field").value;
		if (window.confirm("vuoi davvero editare : "+ mail+ "?")) {
			urls="edit";
			type="users";
			ajaxPost4();
		}

	});



	$("button[name='editbuttonorganization']").click(function(event) {
		organization = document.getElementById("organization-field").value;
		id = document.getElementById("id-field").value;
		piva = document.getElementById("piva-field").value;
		if((piva.length != 11)  ||  !(/^[0-9]+$/.test( piva ))  ){
			alert("Partita Iva non valida");
		}

		else if((organization.length==0)  ){

			alert("Inserisci un nome valido");

		}else if (window.confirm("Vuoi procedere con l'aggiornamento di :" +id+ " ?")) {
			urls="edit";
			type="organizations";
			ajaxPost4();
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
		document.getElementById('title').innerHTML ="Organizations"

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
		name=document.getElementById("name"+number).textContent;
		urls="view";
		ajaxPost();

	});




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
							$('#myModalRemove').modal('show');
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
						document.getElementById('title').innerHTML ="Organization "+name;

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

	function ajaxPost3(){
		var AddDTO;
		AddDTO= {
				"success"         : "true",
				"first_parameter" : organization,
				"second_parameter": piva,
				"third_parameter" : "organization",
				"four_parameter"  : "false"
		}

		$.ajax({
			type: "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url: urls,
			data: JSON.stringify(AddDTO),
			success :function(data) {
				if(data.success=="ok"){
					if(data.third_parameter=="add"){
						$("#box").load("forElementsOrganizations");
					}
				}else if(data.second_parameter=="presente"){
					alert("Organizzazione già presente");
				}
				else{
					alert("errore generico in ajax");


				}
			}
		});
	}
	function ajaxPost2(){
		var AddDTO;
		AddDTO= {
				"success" : "true",
				"first_parameter" : mail,
				"third_parameter" :"user",
				"second_paramter" :"false",
				"four_paramter"   :"false"
		}

		$.ajax({
			type: "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url: urls,
			data: JSON.stringify(AddDTO),
			success :function(data) {
				if(data.success=="ok"){
					if(data.first_parameter=="add"){
						$("#box").load("forElements");
					}
				}else{
					alert("errore generico in ajax");
				}
			}
		});
	}

	function ajaxPost4(){
		var ActiveDTO;
		if(type=="organizations"){
			ActiveDTO= {
					"success" : "true",
					"first_parameter" : id,
					"second_parameter" : organization,
					"third_parameter" : piva,
					"four_parameter" : type
			}	
		}
		if(type=="users"){
			ActiveDTO= {
					"success" : "true",
					"first_parameter" : name,
					"second_parameter" : surname,
					"third_parameter" : mail,
					"four_parameter" : type
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
					if(data.first_parameter=="users"){
						if(urls=="edit"){
							document.getElementById('message').innerHTML =data.second_parameter;
							$('#myModalRemove').modal('show');
						}
						$("#box").load("forElements");
					}

					if(data.first_parameter=="organizations"){

						if(urls=="edit"){
							document.getElementById('message').innerHTML =data.second_parameter;
							$('#myModalRemove').modal('show');
						}
						$("#box").load("forElementsOrganizations");

					}


				}
				else{
					document.getElementById('message').innerHTML =data.second_parameter;
					$('#myModalRemove').modal('show');
				}
			}
		});
	}


})




