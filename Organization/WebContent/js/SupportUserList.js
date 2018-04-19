$(document).ready(function(){
	

	var number;
	var piva;

	var idField = $('#id-field'),
	nameField = $('#name-field').hide(),
	surnameField = $('#surname-field').hide(),
	mailField = $('#mail-field').hide(),
	editBtn = $("#edit-btn"),
	editListBtn = $("button[name='editUserListbtn']"),
	backListBtn = $("button[name='backUserListbtn']"),
	addbutton= $('#add-btn');





	$("button[name='editUserListbtn']").click(function(event) {
		editListBtn.show();
		backListBtn.hide(),
		number=event.target.id;
		number=number.slice(-1);
		surnameField.show();
		nameField.show();
		editListBtns = $('#edit'+number).hide();
		backListBtns = $('#back'+number).show();
		nameField.val(document.getElementById("name"+number).textContent);
		surnameField.val(document.getElementById("surname"+number).textContent);
		mailField.val(document.getElementById("email"+number).textContent);
		editBtn.show();
		addbutton.hide();


	});
	$("button[name='backUserListbtn']").click(function(event) {
		editListBtn.show();
		backListBtn.hide(),
		number=event.target.id;
		number=number.slice(-1);
		editListBtns = $('#edit'+number).show();
		backListBtns = $('#back'+number).hide();
		nameField.val('');
		surnameField.val('');
		mailField.val('');
		mailField.hide();
		nameField.hide();
		surnameField.hide();
		editBtn.hide();
		addbutton.show();


	});
	


	

})
