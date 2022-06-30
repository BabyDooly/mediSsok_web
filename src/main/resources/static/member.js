var main = {
    init : function () {
        var _this = this;
        $('#btn-update').on('click', function () {
            _this.userUpdate();
        });

        var _this = this;
        $('#liveToastBtn').on('click', function () {
            _this.alarmUpdate();
        });


    },

    userUpdate : function () {
        var data = {
            nickname: $('#nameText').val(),
            phone: $('#callText').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/member/user',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('정보가 수정되었습니다.');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    alarmUpdate : function () {
        var data = {
            vibration: $('#vibrationCheckbox').is(':checked'),
            pushAlarms: $('#pushAlarmCheckbox').is(':checked'),
            locationAlarms: $('#locationAlarmsCheckbox').is(':checked'),
            replenishAlarms: $('#replenishAlarmsCheckbox').is(':checked')
        };

        $.ajax({
            type: 'POST',
            url: '/api/member/alarm',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            var toastTrigger = document.getElementById('liveToastBtn')
            var toastLiveExample = document.getElementById('liveToast')
            if (toastTrigger) {
                toastTrigger.addEventListener('click', function () {
                    var toast = new bootstrap.Toast(toastLiveExample)

                    toast.show()
                    setTimeout(function(){window.location.href="/";}, 3000);
                })
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};
main.init();

