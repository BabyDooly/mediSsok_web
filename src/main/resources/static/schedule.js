let mediBox = {
    init: function () {
        let _this = this;
        let id;


        // 일정 추가
        $('#addbtn').on('click', function () {
            let today = new Date();
            let year = today.getFullYear();
            let month = today.getMonth() + 1;
            let day = today.getDate();

            today = new Date(year + "-" + month + "-" + day + " 0:0:0")

            let start = new Date($('#startday').val() + " 0:0:0");

            console.log(today);
            console.log(start);
            console.log(start < today);


            if ($('#medicase').val() == "0")
                alert("약통 선택은 필수입니다.");
            else if ($('#startday').val() == "")
                alert("시작일 선택은 필수입니다.");
            else if ($('#addTime').val() == "")
                alert("시간 선택은 필수입니다.");
            else if ($('#selectdayBtn').val() == "0")
                alert("주기 선택은 필수입니다.");
            else if ($('#selectdayBtn').val() == "2" && !($('#addMon').is(':checked') || $('#addTue').is(':checked')
            || $('#addWed').is(':checked') || $('#addThu').is(':checked') || $('#addFri').is(':checked')
            || $('#addSat').is(':checked') || $('#addSun').is(':checked')))
                alert("요일 선택은 필수입니다.");
            else if(start < today)
                alert("오늘 이후의 날짜를 선택하세요.");
            else{
                _this.add();
            }
        });

        // 알림 아이디 받아보기 (조회)
        $('.alarm-getID').on('click', function () {
            id = $(this).attr("value");
            console.log(id);
            _this.alarmGet(id);
        })
        
        // 알림 수정
        $('.btn-edit').on('click', function () {
            _this.alarmUpdate(id);
        })

        // 알람 여부
        $('.alarmBtn').on('click', function () {
            id = $(this).attr("value");
            console.log(id);
            let img1 = document.getElementById("img" + id);
            console.log(img1);
            if (img1.classList.contains('fas')) {
                _this.alarmCheck(id, false);
            } else {
                _this.alarmCheck(id, true);
            }
        })

        // 복용 여부
        $('.eatBtn').on('click', function () {
            id = $(this).attr("value");
            console.log(id);

            let clickCheck = $(this).attr("id");

            console.log(clickCheck)

            let on = "on-" + id;
            let off = "off-" + id;
            let onCheck = $('#' + on).is(":checked")
            let offCheck = $('#' + off).is(":checked")

            console.log(onCheck)
            console.log(offCheck)

            if (offCheck) {
                _this.eatCheck(id, true);
            } else if(onCheck) {
                _this.eatCheck(id, false);
            }
        })

        // 알림 삭제
        $('.btn-delete').on('click', function () {
            id = $(this).attr("value");
            console.log(id);
            if(confirm("정말 삭제 하시겠습니까?") == true) {
                _this.alarmDelete(id);
            }else{
                alert("취소되었습니다.")
            }
        });
    },

    // 일정 추가
    add: function () {
        let day = $('#startday').val() + " " + $('#addTime').val();

        let cycle_result
        let week_result = 0;

        // 주기 검사,계산
        if ($('#selectdayBtn').val() == "1")
            cycle_result = 1;
        else if ($('#selectdayBtn').val() == "2"){
            cycle_result = 0;
            if ($('#addMon').is(':checked'))
                week_result = week_result + 1;
            if ($('#addTue').is(':checked'))
                week_result = week_result + 2;
            if ($('#addWed').is(':checked'))
                week_result = week_result + 4;
            if ($('#addThu').is(':checked'))
                week_result = week_result + 8;
            if ($('#addFri').is(':checked'))
                week_result = week_result + 16;
            if ($('#addSat').is(':checked'))
                week_result = week_result + 32;
            if ($('#addSun').is(':checked'))
                week_result = week_result + 64;

        }
        else if ($('#selectdayBtn').val() == "3"){
            if ($('#sel2').val() == "0")
                cycle_result = $('#stepper').val();
            else if ($('#sel2').val() == "1")
                cycle_result = $('#stepper').val() * 7;
            else if ($('#sel2').val() == "2")
                cycle_result = $('#stepper').val() * 30;


        }

        let data = {
             startday : day,
             cycle: cycle_result,
             week: week_result,
             medicineBoxId: $('#medicase').val(),
        }

        console.log(data)

        $.ajax({
            type: 'POST',
            url: '/api/medi/schedule/add',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('스케줄이 등록되었습니다.');
            location.reload(); // 스케줄 등록 성공시 리로드
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 알람 조회
    alarmGet : function (id) {
        let data = {
            dateInfoId: id,
        };

        $.ajax({
            type: 'POST',
            url: '/api/medi/alarm/get',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data) {
            console.log(data['medicineBoxName']);
            let alarmDatetime = data['alarmDatetime'];
            let time = alarmDatetime.substring(11,16)

            $('#editaddTime').val(time)
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    // 알림 수정
    alarmUpdate : function (id) {
        let year
        let month
        let day

        if (window.location.search == '') {
            date = new Date();
            year = date.getFullYear();
            month = date.getMonth() + 1;
            day = date.getDate();

            month = month >= 10 ? month : '0' + month;
            day = day >= 10 ? day : '0' + day;
        } else {
            let query = window.location.search;
            let param = new URLSearchParams(query);

            year = param.get('year');
            month = param.get('month');
            day = param.get('day');

            month = month >= 10 ? month : '0' + month;
            day = day >= 10 ? day : '0' + day;
        }

        let today = year + '-' + month + '-' + day + ' ' + $('#editaddTime').val();



        let data = {
            alarmDatetime : today
        }

        console.log(data);

        $.ajax({
            type: 'POST',
            url: '/api/medi/alarm/update/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('알림이 수정되었습니다.');
            location.reload(); // 알림 수정 성공시 리로드
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 알림 삭제
    alarmDelete : function (id) {
        $.ajax({
            type: 'DELETE',
            url: '/api/medi/alarm/delete/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('알림이 삭제되었습니다.');
            location.reload(); // 알림 삭제 성공시 리로드
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 알람 여부
    alarmCheck : function (id, check) {
        let data = {
            alarmCheck : check
        }

        $.ajax({
            type: 'POST',
            url: '/api/medi/alarm/check/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            location.reload(); // 알람 여부 성공시 리로드
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 복용 여부
    eatCheck : function (id, check) {
        let data = {
            eatCheck : check
        }

        let dateId = {
            dateInfoId: id,
        };


        $.ajax({
            type: 'POST',
            url: '/api/medi/eat/check/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            // location.reload(); // 복용 여부 수정 성공시 리로드

            $.ajax({
                type: 'POST',
                url: '/api/medi/alarm/get',
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(dateId)
            }).done(function(data) {
                console.log(data);

                if (data['replenishAlarms'] == true) {
                    let medicineBoxName =  data['medicineBoxName'];
                    document.getElementById("liveTitle").innerHTML = medicineBoxName;

                    let medCount =  data['medCount'];
                    document.getElementById("medCountText").innerHTML = medCount + "개 남았습니다";

                    let toastLive = document.getElementById('liveCount')
                    let toast1 = new bootstrap.Toast(toastLive)

                    toast1.show()
                    setTimeout(function(){}, 4000);
                }
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });



    },
}
mediBox.init();

