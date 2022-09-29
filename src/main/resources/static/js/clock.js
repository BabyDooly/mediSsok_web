function setClock() {
    var dateInfo = new Date();
    var hour = modifyNumber(dateInfo.getHours());
    var min = modifyNumber(dateInfo.getMinutes());
    var sec = modifyNumber(dateInfo.getSeconds());
    var year = dateInfo.getFullYear();
    var month = dateInfo.getMonth() + 1;
    var date = dateInfo.getDate();
    var audio = document.getElementById("alarmaudio");
    var alarm = document.getElementById("alarmClock");
    document.getElementById("clocktime").innerHTML = hour + ":" + min + ":" + sec;
    document.getElementById("clockdate").innerHTML = year + "년" + month + "월" + date + "일";

    console.log(hour + ":" + min + ":" + sec)

        if (alarm.hasAttribute('aria-hidden') == true) {
            audio.pause();
        } else {
            audio.play();
        }

}

function modifyNumber(time) {
    if (parseInt(time) < 10) {
        return "0" + time;
    } else
        return time;
}

$(document).ready(function(){
    setClock();
    setInterval(setClock, 1000);
});