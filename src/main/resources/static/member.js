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

        //비밀번호 변경
        var _this = this;
        $('#btn-update-password').on('click', function () {
            _this.userPassWordUpdate();
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

    userPassWordUpdate : function () {
        var data = {
            nowPassword: $('#nowPass').val(),
            newPassword: $('#newPass').val(),
         };

        // 편하게 하기 위해서 작성
        var Password = $('#nowPass').val();
        var newPassword = $('#newPass').val();
        var rePassword = $('#rePass').val();

        //아래 로직은 Null값이 못들어가게 선언
         if(Password.length < 1){
             alert("현재 비밀번호를 입력해주세요");
             $("#nowPass").focus();
             return;
         }
         if(newPassword.length < 1){
             alert("변경할 비밀번호를 입력해주세요");
             $("#newPass").focus();
             return;
         }
         if(rePassword.length < 1){
             alert("재확인 비밀번호를 입력해주세요");
             $("#rePass").focus();
             return;
         }

        // 변경할 패스워드와 재확인 비밀번호 일치 검사 로직
        if(newPassword !== rePassword){
            alert("신규 비밀번호와 재확인 비밀번호가 일치하지 않습니다.");
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/api/member/password',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('정보가 수정되었습니다.');
        }).fail(function (error) {
            alert('비밀번호를 확인해주세요.');
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

