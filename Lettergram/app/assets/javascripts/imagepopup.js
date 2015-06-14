function pop(cardtype, cardimgsrc){ 
bootbox.dialog({
                title: "Preview of the " + cardtype + " card.",
                message: 
                    '<table><tr><td>'+
                    '<img src="/assets/'+cardimgsrc+'" class="letterimagepreview"/>'+
                    '</td><td>'+
                    '<div class="row">  ' +
                    '<div class="col-md-12"> ' +
                    '<form class="form-horizontal"> ' +
                    '<div class="form-group"> ' +
                    '<label class="container-fluid" for="name">Quantity</label> ' +
                    '<div class="container-fluid"> ' +
                    '<input id="name" name="name" type="text" placeholder="#" class="form-control input-md container-fluid"> ' +
                    '<span class="help-block">How many would you like to purchase?</span> </div> ' +
                    '</div> ' +
                    '<div class="form-group"> ' +
                    '<label class="col-md-12 control-label" for="cart">Add to Cart?</label> ' +
                    '<div class="col-md-12"> <div class="checkbox"> <label for="cart-0"> ' +
                    '<input type="checkbox" name="cart" id="cart-0" value="Add"> ' +
                    'Add </label>' +
                    '</div>'+
                    '</div> </div>' +
                    '</form> </div>  </div>' +
                    '</td></tr></table>',
                buttons: {
                    success: {
                        label: "Save",
                        className: "btn-success"/*,
                        callback: function () {
                          This part isn't working
                            var name = $('#name').val();
                            var answer = $("input[name='cart']:checked").val();
                            Example.show("Hello " + name + ". You've chosen <b>" + answer + "</b>");
                        }*/
                    }
                }
            }
        ).find("div.modal-dialog").addClass("modal-lg");
}