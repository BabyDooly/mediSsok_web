let main = {
    init: function () {
        let _this = this;
        let linkId;

        // 회원 정보 수정
        $('#btn-update').on('click', function () {
            _this.userUpdate();
        });

        // 알람 업데이트
        $('#liveToastBtn').on('click', function () {
            _this.alarmUpdate();
        });

        //비밀번호 변경
        $('#btn-update-password').on('click', function () {
            _this.userPassWordUpdate();
        });

        //비밀번호 찾기
        $('#checkEmail').on('click', function () {
            _this.findPassword();
        });

        //회원 연동
        $('#emailSubmit').on('click', function () {
            _this.link();
        });

        //연동 정보 가져오기
        $('.link').on('click', function () {
            linkId = $(this).attr("value");
            _this.linkGet(linkId);
        });

        // 연동 정보 수정
        $('#editaddbtn').on('click', function () {
            _this.linkUpdate(linkId);
        });

        // 연동 제거
        $('#deletebtn').on('click', function () {
            _this.linkDelete(linkId);
        });
    },

    // 유저 정보 수정
    userUpdate: function () {
        let pic = $('input[name=profile-check]:checked').val();
        console.log(pic);
        console.log($("#myProfile").attr("value"))

        if (pic == undefined)
            pic = $("#myProfile").attr("value");

        let data = {
            nickname: $('#nameText').val(),
            phone: $('#callText').val(),
            picture: pic
        };

        $.ajax({
            type: 'POST',
            url: '/api/member/user',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('정보가 수정되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    
    // 비밀번호 변경
    userPassWordUpdate: function () {
        let data = {
            nowPassword: $('#nowPass').val(),
            newPassword: $('#newPass').val(),
        };

        // 편하게 하기 위해서 작성
        let Password = $('#nowPass').val();
        let newPassword = $('#newPass').val();
        let rePassword = $('#rePass').val();

        //아래 로직은 Null값이 못들어가게 선언
        if (Password.length < 1) {
            alert("현재 비밀번호를 입력해주세요");
            $("#nowPass").focus();
            return;
        }
        if (newPassword.length < 1) {
            alert("변경할 비밀번호를 입력해주세요");
            $("#newPass").focus();
            return;
        }
        if (rePassword.length < 1) {
            alert("재확인 비밀번호를 입력해주세요");
            $("#rePass").focus();
            return;
        }

        // 변경할 패스워드와 재확인 비밀번호 일치 검사 로직
        if (newPassword !== rePassword) {
            alert("신규 비밀번호와 재확인 비밀번호가 일치하지 않습니다.");
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/api/member/password',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('정보가 수정되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert('비밀번호를 확인해주세요.');
        });
    },

    // 알람 업데이트
    alarmUpdate : function () {
        let Check1 = $('#s4_1').is(":checked");
        let Check2 = $('#s4_2').is(":checked");
        let workAlarms;

        console.log("check1 : " + Check1);
        console.log("check2 : " + Check2);
        console.log($('#workTime').val())

        if($('#workTime').val() != null)
            workAlarms = $('#workTime').val();

        console.log("값: " + workAlarms);

        if (Check1 == false && Check2 == false)
            workAlarms = null;

        console.log("값: " + workAlarms);
        
        let data = {
            vibration: $('#vibrationCheckbox').is(':checked'),
            pushAlarms: $('#pushAlarmCheckbox').is(':checked'),
            locationAlarms: $('#locationAlarmsCheckbox').is(':checked'),
            replenishAlarms: $('#replenishAlarmsCheckbox').is(':checked'),
            workAlarms: workAlarms
        };

        console.log(data);

        $.ajax({
            type: 'POST',
            url: '/api/member/alarm',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            let toastLiveExample = document.getElementById('liveToast')
            let toast = new bootstrap.Toast(toastLiveExample)

            toast.show()
            setTimeout(function(){window.location.href="/";}, 2000);
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 비밀번호 찾기
    findPassword: function () {
        let userEmail = $("#userEmail").val()

        let data = {
            memberEmail : $("#userEmail").val()
        };

        //아래 로직은 Null값이 못들어가게 선언
        if(userEmail.length < 1){
            alert("이메일을 입력해주세요");
            $("#userEmail").focus();
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/api/member/findEmail',
            dataType: "json",
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success : function (res) {
                if (res == true) {
                    $.ajax({
                        type: 'POST',
                        url: '/api/member/sendEmail',
                        dataType: "json",
                        contentType: 'application/json; charset=utf-8',
                        data: JSON.stringify(data),
                    }).fail(function (error) {
                        alert(JSON.stringify(error));
                    })
                    alert('임시비밀번호를 전송 했습니다.');
                    window.location.href = "/user/login";
                } else {
                    alert('유효한 이메일이 아닙니다.');
                }
            }
        })
    },

    // 비밀번호 찾기
    findPassword: function () {
        let userEmail = $("#userEmail").val()

        let data = {
            memberEmail : userEmail
        };

        //아래 로직은 Null값이 못들어가게 선언
        if(userEmail.length < 1){
            alert("이메일을 입력해주세요");
            $("#userEmail").focus();
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/api/member/findEmail',
            dataType: "json",
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success : function (res) {
                if (res == true) {
                    $.ajax({
                        type: 'POST',
                        url: '/api/member/sendEmail',
                        dataType: "json",
                        contentType: 'application/json; charset=utf-8',
                        data: JSON.stringify(data),
                    }).fail(function (error) {
                        alert(JSON.stringify(error));
                    })
                    alert('임시비밀번호를 전송 했습니다.');
                    window.location.href = "/user/login";
                } else {
                    alert('유효한 이메일이 아닙니다.');
                }
            }
        })
    },

    // 회원 연동
    link: function () {
        let userEmail = $("#connectEmail").val()
        let userNickName = $("#connectName").val()

        if (userEmail.length < 1) {
            alert("이메일을 입력해주세요");
            $("#connectEmail").focus();
            return;
        }

        if (userNickName.length < 1) {
            alert("닉네임을 입력해주세요");
            $("#connectName").focus();
            return;
        }

        let emailData = {
            memberEmail: userEmail
        };

        let linkData = {
            userEmail: userEmail,
            nickname: userNickName
        };
        console.log(linkData)

        $.ajax({
            type: 'POST',
            url: '/api/member/findEmail',
            dataType: "json",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(emailData),
            success: function (res) {
                if (res == true) {
                    $.ajax({
                        type: 'POST',
                        url: '/api/link/add',
                        dataType: "json",
                        contentType: 'application/json; charset=utf-8',
                        data: JSON.stringify(linkData),
                        success: function (suc) {
                            console.log(suc)

                            if (suc == 1)
                                alert('이미 연동 신청한 이메일입니다.');
                            else if (suc == 2)
                                alert('자기 자신 입니다.');
                            else {
                                alert('연동을 신청 했습니다.');
                                window.location.href = "/user/mypage";
                            }
                        }
                    }).fail(function (error) {
                        alert(JSON.stringify(error));
                    })
                } else {
                    alert('유효한 이메일이 아닙니다.');
                }
            }
        })
    },

    // 링크 정보 조회
    linkGet : function (id) {
        let data = {
            linkId: id,
        };

        $.ajax({
            type: 'POST',
            url: '/api/link/get',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data) {
            console.log(data);
            $('#profile-email').val(data['userEmail'])
            $('#profile-nickname').val(data['nickname'])

            if (data['permit'] == true)
                $('#profile-text').hide();

        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    // 링크 정보 수정
    linkUpdate : function (id) {
        let data = {
            nickname : $('#profile-nickname').val()
        }

        console.log(data)

        $.ajax({
            type: 'POST',
            url: '/api/link/update/' + id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('연동이 수정되었습니다.');
            window.location.href = "/user/mypage";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 연동 삭제
    linkDelete : function (id) {
        $.ajax({
            type: 'DELETE',
            url: '/api/link/delete/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('연동이 제거되었습니다.');
            window.location.href = "/user/mypage";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};
main.init();

