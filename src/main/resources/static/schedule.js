let mediBox = {
    init: function () {
        let _this = this;

        // 일정 추가
        $('#addbtn').on('click', function () {
            _this.add();
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
            else{

            }

        });
    },

    // 약통 추가
    add: function () {
        var day = $('#startday').val() + " " + $('#addTime').val();

        var cycle_result
        var week_result = 0;

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
            url: '/api/medi/bell/add',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('스케줄이 등록되었습니다.');
            window.location.href = '/medi/bell'; // 스케줄 등록 성공시 메인페이지 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}
mediBox.init();

