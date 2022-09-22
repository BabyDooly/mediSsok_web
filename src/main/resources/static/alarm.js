function getTime(){
    const alarmContainer = document.querySelector('.mediBellDiv2');

    $.ajax({
        type: 'GET',
        url: '/api/alarm/get',
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
    }).done(function(json) {
        const alarmDate = json.alarmDatetime;

        const dateNow = new Date();
        const year = dateNow.getFullYear();
        const month = dateNow.getMonth() + 1;
        const day = dateNow.getDate();
        const hours = dateNow.getHours();
        const minutes = dateNow.getMinutes();
        const current = `${year}-${month < 10 ? `0${month}` : month}-${day < 10 ? `0${day}` : day}T${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:00`;

        console.log("now: " + current + " alarm: " + alarmDate);

        if(current === alarmDate)
            alarmContainer.classList.add('alarmOn');

        else
            alarmContainer.classList.remove('alarmOn');
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function init(){
    getTime();
    setInterval(getTime, 1000);
}
