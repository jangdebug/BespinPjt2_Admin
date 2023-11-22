function findProduct(){
    console.log('findProduct()');

    let findProductName = $("#registProductForm input[type='text']").val();

    if(findProductName == ''){
        alert('Input Please Product');
        findProductName.focus();
    }
    else {
        ajax_addProduct(findProductName);
    }
}

function allfindProduct(){
    console.log('allfindProduct()');

    let findProductName = "";
    ajax_addProduct(findProductName);

    $("#registProductForm input[type='text']").val("");
}

function ajax_addProduct(name){
    console.log('ajax_addProduct()');

    let msgDto = {
        name : name
    }

    $.ajax({
        url: '/product/selectProduct',
        method: 'POST',
        data: JSON.stringify(msgDto),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function(data) {
            console.log('AJAX SUCCESS - ajax_addProduct()');

            if(data.productDtos.length == 0)
                alert('상품을 찾을 수 없습니다.');

            else{
                $("#selectProduct table").children().remove();

                for (let i = 0; i < data.productDtos.length; i += 7) {
                    let appendTag = "<tr>";

                    for (let j = i; j < i + 7 && j < data.productDtos.length; j++) {
                        appendTag += "<td>";
                        appendTag += "<a href='#none' class='add_product' data-product-name='" + data.productDtos[j].name;
                        appendTag += "' data-product-price='" + data.productDtos[j].price;
                        if(data.productDtos[j].img == ''){
                            appendTag += "' data-img='default/default.jpg'>";
                            appendTag += '<img width="100" height="100" src="/dormEaseUploadImg/admin/product/default/default.jpg">';
                        }
                        else {
                            appendTag += "' data-img='" + data.productDtos[j].img + "'>";
                            appendTag += '<img width="100" height="100" src="/dormEaseUploadImg/admin/product/' + data.productDtos[j].img + '">';
                        }
                        appendTag += '<span class="name">' + data.productDtos[j].name + '</span>'
                        appendTag += '<span class="price">' + data.productDtos[j].price + '</span>'
                        appendTag += "</a>";
                        appendTag += "</td>";
                    }

                    appendTag += "</tr>";
                    $("#selectProduct table").append(appendTag);
                }
                if(name != ''){     //전체 보기 일땐 alert (x)
                    alert('상품 검색이 완료되었습니다.');
                }
            }

        },
        error: function(data) {
            console.log('AJAX ERROR - ajax_addProduct()');

            alert('상품을 찾을 수 없습니다.');
        }
    });
}