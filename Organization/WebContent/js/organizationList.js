var options = {
  valueNames: [ 'id', 'organization', 'piva' ]
};

// Init list
var contactList = new List('organizations', options);

var idField = $('#id-field'),
    organizationField = $('#organization-field'),
    pivaField = $('#piva-field'),
    addBtn = $('#add-btn'),
    editBtn = $('#edit-btn').hide(),
    removeBtns = $('.remove-item-btn'),
    editBtns = $('.edit-item-btn');

// Sets callbacks to the buttons in the list
refreshCallbacks();

addBtn.click(function() {

});

editBtn.click(function() {


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
    organizationField.val(itemValues.organization);
    pivaField.val(itemValues.piva);
    editBtn.show();
    addBtn.hide();
  });
}


function clearFields() {
  organizationField.val('');
  pivaField.val('');

}