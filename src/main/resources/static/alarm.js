let timer;

// 알람 시간 가져와서 체크
function getTime(){
    let alarmDate;
    let workAlarms;

    if ($(".collapse").hasClass("nav-have")){
        $.ajax({
            type: 'GET',
            url: '/api/alarm/get',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function(json) {
            if (json.pushAlarms){
                alarmDate = json.alarmDatetime;
                workAlarms = json.workAlarms;

                document.getElementById("alarm-box").innerHTML = json.medicineBoxName;
                $('#eat-check').val(json.id);

                timer = setInterval(function() {
                    const date = nowDate();
                    const time = nowTime()
                    timeCheck(alarmDate, workAlarms, date, time);
                }, 1000);
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

// 현재 일정 + 시간
function nowDate(){
    const dateNow = new Date();
    const year = dateNow.getFullYear();
    const month = dateNow.getMonth() + 1;
    const day = dateNow.getDate();
    const hours = dateNow.getHours();
    const minutes = dateNow.getMinutes();
    const current = `${year}-${month < 10 ? `0${month}` : month}-${day < 10 ? `0${day}` : day}T${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:00`;

    return current;
}

// 현재 시간
function nowTime(){
    const dateNow = new Date();
    const hours = dateNow.getHours();
    const minutes = dateNow.getMinutes();
    const current = `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:00`;

    return current;
}

// 알람시간, 출근시간, 현재 시간 비교
function timeCheck(alarmDate, workAlarms, date, time){
    console.log("알람시간: " + alarmDate + " 출근시간: " + workAlarms + " 현재일정: " + date + " 현재시간: " + time);

    // 알람시간
    if(alarmDate === date){
        $("#alarm-box").show();
        $("#eat-check").show();
        $("#work-box").hide();

        $("#alarmClock").modal('show');
        clearInterval(timer);

        $('#alarmClock').on('hidden.bs.modal', function (e) {
             getTime();
        })
    }

    // 출근시간
    if(workAlarms === time){
        $("#alarm-box").hide();
        $("#eat-check").hide();
        $("#work-box").show();

        $("#alarmClock").modal('show');
        clearInterval(timer);

        $('#alarmClock').on('hidden.bs.modal', function (e) {
            setTimeout(function() {
                getTime();
            }, 60000);
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