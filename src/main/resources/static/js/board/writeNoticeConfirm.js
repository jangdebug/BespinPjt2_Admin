function writeNoticeConfirm() {
    console.log('writeContent() CALLED!!');

    let form = document.writeNoticeForm;
    if (form.title.value == '') {
        alert('제목은 필수입력 사항입니다.\n제목을 입력해주세요.');
        form.title.focus();

    } else if (form.start_date.value == "" && form.end_date.value !="") {
        alert('시작날짜를 설정해주시기 바랍니다.');

    } else if (form.start_date.value != "" && form.end_date.value =="") {
        alert('마감날짜를 설정해주시기 바랍니다.');

    } else if (form.start_date.value > form.end_date.value) {
        alert('입력하신 날짜를 확인해주시기 바랍니다. \n마감날짜는 시작날짜보다 뒤로 설정하셔야 합니다.');

    } else {
        form.submit();

    }

}