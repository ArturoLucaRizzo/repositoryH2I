$(document).ready(function(){
	

	var number;
	var piva;

	var idField = $('#id-field'),
	organizationField = $('#organization-field'),
	pivaField = $('#piva-field'),
	editBtn = $('#edit-btn').hide(),
	editListBtn = $("button[name='editListbtn']"),
	backListBtn = $("button[name='backListbtn']").hide(),
	addbutton= $('#add-organization');





	$("button[name='editListbtn']").click(function(event) {
		editListBtn.show();
		backListBtn.hide(),
		number=event.target.id;
		number=number.split('-')[1];
		editListBtns = $('#edit-'+number).hide();
		backListBtns = $('#back-'+number).show();
		piva=document.getElementById("piva-"+number).textContent;
		pivaField.val(piva);
		organizationField.val(document.getElementById("name-"+number).textContent);
		organizationField = $('#organization-field').show();
		pivaField = $('#piva-field').show();
        idField.val(document.getElementById("idorganizations-"+number).textContent);
		editBtn.show();
		addbutton.hide();


	});
	$("button[name='backListbtn']").click(function(event) {
		number=event.target.id;
		number=number.split('-')[1];
		
		editListBtns = $('#edit-'+number).show();
		backListBtns = $('#back-'+number).hide();
		pivaField.val('');
		organizationField.val('');
		editBtn.hide();
		addbutton.show();


	});
	


	

})


