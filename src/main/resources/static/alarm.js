let timer;

// 알람 시간 가져와서 체크
function getTime(){
    let alarmDate;

    if ($(".collapse").hasClass("nav-have")){
        $.ajax({
            type: 'GET',
            url: '/api/alarm/get',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function(json) {
            if (json.pushAlarms){
                alarmDate = json.alarmDatetime;
                document.getElementById("alarm-box").innerHTML = json.medicineBoxName;
                $('#eat-check').val(json.id);

                timer = setInterval(function() {
                    const now = nowTime();
                    timeCheck(alarmDate, now);
                }, 1000);
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

// 현재 시간
function nowTime(){
    const dateNow = new Date();
    const year = dateNow.getFullYear();
    const month = dateNow.getMonth() + 1;
    const day = dateNow.getDate();
    const hours = dateNow.getHours();
    const minutes = dateNow.getMinutes();
    const current = `${year}-${month < 10 ? `0${month}` : month}-${day < 10 ? `0${day}` : day}T${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:00`;

    return current;
}

// 알람시간, 현재 시간 비교
function timeCheck(alarm, now){
    console.log("알람시간: " + alarm + " 현재시간: " + now);

    if(alarm === now){
        $("#alarmClock").modal('show');
        clearInterval(timer);

        $('#alarmClock').on('hidden.bs.modal', function (e) {
             getTime();
        })
    }
}

// 복용 여부
function eatCheck(){
    const idValue = document.getElementById('eat-check').value;

    let data = {
        eatCheck : true
    }

    $.ajax({
        type: 'POST',
        url: '/api/medi/eat/check/'+idValue,
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function() {
        location.reload(); // 복용 여부 수정 성공시 리로드
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

getTime();