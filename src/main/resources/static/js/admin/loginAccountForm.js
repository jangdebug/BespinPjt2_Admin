function loginAccountConfirm() {
    console.log("loginAccountConfirm() CALLED!!!");

    let form = document.loginAccountForm;

    if(form.id.value == ''){
        alert("아이디를 입력해주세요!");
        form.id.focus();
    } else if(form.password.value == ''){
        alert("비밀번호를 입력해주세요!");
        form.password.focus();
    }
    else {

        product_registmit();
    }

}