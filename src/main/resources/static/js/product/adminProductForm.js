function adminProductConfirm(){
    console.log("adminProductConfirm()");

    let form = document.adminProductForm;

    if(form.name.value == ''){
        alert('상품의 이름을 입력해주세요.');
        form.name.focus();
    } else if(form.price.value == ''){
        alert("상품의 가격을 입력해주세요.");
        form.price.focus();
    } else if(isNaN(form.price.value)){
        alert("상품의 가격을 숫자로 입력해주세요.");
        form.price.focus();
    } else{
        ajax_admin_already_regist(form.name.value, function(countExist) {
            if (countExist > 0) {
                alert('이미 개시 중인 상품입니다.');
            }
            else{
                alert('상품을 등록합니다.');
                form.submit();
            }
        })
    }
}

function ajax_admin_already_regist(name, callback){
    console.log('ajax_admin_already_regist()');

    let msgDto = {
        name : name
    }

    $.ajax({
        url: '/product/adminAlreadyRegist',
        method: 'POST',
        data: JSON.stringify(msgDto),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function(data) {
            console.log('AJAX SUCCESS - adminAlreadyRegist()');

            callback(data.countExist); // 결과를 콜백 함수에 전달

        },
        error: function(data) {
            console.log('AJAX ERROR - ajax_addProduct()');

            alert('등록 중 문제가 발생하였습니다.');
        }
    });
}
