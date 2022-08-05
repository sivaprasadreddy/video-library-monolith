jQuery(document).ready(function($){
    updateCartItemCount();
});

function updateCartItemCount()
{
    $.ajax ({
        url: '/cart/items/count',
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        complete: function(responseData, status, xhttp){
            $('#cart-item-count').text('('+responseData.responseJSON.count+')');
        }
    });
}

function addItemToCart(uuid)
{
    $.ajax ({
        url: '/cart/items',
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data : '{"uuid":"'+ uuid +'"}"',
        complete: function(responseData, status, xhttp){
            updateCartItemCount();
        }
    });
}

function updateCartItemQuantity(uuid, quantity)
{
    $.ajax ({
        url: '/cart/items',
        type: "PUT",
        dataType: "json",
        contentType: "application/json",
        data : '{ "product" :{ "uuid":"'+ uuid +'"},"quantity":"'+quantity+'"}',
        complete: function(responseData, status, xhttp){
            updateCartItemCount();
            location.href = '/cart'
        }
    });
}

function removeItemFromCart(uuid) {
    $.ajax ({
        url: '/cart/items/'+uuid,
        type: "DELETE",
        dataType: "json",
        contentType: "application/json",
        complete: function(responseData, status, xhttp){
            updateCartItemCount();
            location.href = '/cart'
        }
    });
}
