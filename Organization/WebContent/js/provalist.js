
var options = {
  valueNames: [ 'id', 'name', 'surname', 'mail' ]
};

// Init list
var contactList = new List('user', options);

var idField = $('#id-field'),
    nameField = $('#name-field').hide(),
    surnameField = $('#surname-field').hide(),
    mailField = $('#mail-field'),
    addBtn = $('#add-btn'),
    editBtn = $('#edit-btn').hide(),
    removeBtns = $('.remove-item-btn'),
    editBtns = $('.edit-item-btn');

// Sets callbacks to the buttons in the list
refreshCallbacks();

addBtn.click(function() {

  nameField.hide('');
  surnameField.hide('');
  refreshCallbacks();
});

editBtn.click(function() {
  var item = contactList.get('id', idField.val())[0];

  nameField.hide('');
  surnameField.hide('');
  editBtn.hide();
  addBtn.show();

});

function refreshCallbacks() {
  // Needed to add new buttons to jQuery-extended object
  removeBtns = $(removeBtns.selector);
  editBtns = $(editBtns.selector);
  
  removeBtns.click(function() {
    var itemId = $(this).closest('tr').find('.id').text();
    contactList.remove('id', itemId);
    editBtn.hide();
    addBtn.show();
  });
  
  editBtns.click(function() {
    var itemId = $(this).closest('tr').find('.id').text();
    var itemValues = contactList.get('id', itemId)[0].values();
    idField.val(itemValues.id);
    nameField.val(itemValues.name);
    surnameField.val(itemValues.surname);
    mailField.val(itemValues.mail);
    nameField.show('');
    surnameField.show('');
    editBtn.show();
    addBtn.hide();
  });
}


function clearFields() {
  nameField.val('');
  surnameField.val('');
  mailField.val('');

}