let mediBox = {
    init : function () {
        let _this = this;
        let id = $('#btn-edit').attr('value');
        let scheduleId;


        // 약통 추가
        $('#btn-add').on('click', function () {
            if (document.getElementById("medicase").value == "")
                alert("약통 이름은 필수입니다.");
            else
                _this.add();
        });

        // 약통 수정
        $('#btn-edit').on('click', function () {
            _this.update(id);
        })

        // 약통 삭제
        $('#btn-delete').on('click', function () {
            _this.delete(id);
        });

        // 스케줄 수정(아이디 값 가져오기)
        $('.scheduleUpdate').on('click', function () {
            scheduleId = $(this).attr("value");
            _this.scheduleGet(scheduleId);
        });

        // 스케줄 추가
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


            if ($('#medicasemedicase').val() === "0")
                alert("약통 선택은 필수입니다.");
            else if ($('#startday').val() === "")
                alert("시작일 선택은 필수입니다.");
            else if ($('#addBellTime').val() === "")
                alert("시간 선택은 필수입니다.");
            else if ($('#selectdayBtn').val() === "0")
                alert("주기 선택은 필수입니다.");
            else if ($('#selectdayBtn').val() === "2" && !($('#addMon').is(':checked') || $('#addTue').is(':checked')
                || $('#addWed').is(':checked') || $('#addThu').is(':checked') || $('#addFri').is(':checked')
                || $('#addSat').is(':checked') || $('#addSun').is(':checked')))
                alert("요일 선택은 필수입니다.");
            else if(start < today)
                alert("오늘 이후의 날짜를 선택하세요.");
            else{
                _this.scheduleAdd(id);
            }
        });

        // 스케줄 수정
        $('#editaddbtn').on('click', function () {
            console.log($('#editstartday').val());
            console.log($('#editaddTime').val());

            if ($('#editstartday').val() === "")
                alert("시작일 선택은 필수입니다.");
            else if ($('#editaddTime').val() === "")
                alert("시간 선택은 필수입니다.");
            else if ($('#editselectdayBtn2').val() === "0")
                alert("주기 선택은 필수입니다.");
            else if ($('#editselectdayBtn2').val() === "2" && !($('#editMon').is(':checked') || $('#editTue').is(':checked')
                || $('#editWed').is(':checked') || $('#editThu').is(':checked') || $('#editFri').is(':checked')
                || $('#editSat').is(':checked') || $('#editSun').is(':checked')))
                alert("요일 선택은 필수입니다.");
            else{
                _this.scheduleUpdate(scheduleId , id);
            }
        });

        // 스케줄 수정 취소
        $('.editcanclebtn').on('click', function () {
            document.getElementById("editMon").checked = false;
            document.getElementById("editTue").checked = false;
            document.getElementById("editWed").checked = false;
            document.getElementById("editThu").checked = false;
            document.getElementById("editFri").checked = false;
            document.getElementById("editSat").checked = false;
            document.getElementById("editSun").checked = false;
        });

        // 스케줄 삭제
        $('.scheduleDelete').on('click', function () {
            scheduleId = $(this).attr("value");
            _this.scheduleDelete(scheduleId, id);
        });

    },

    // 약통 추가
    add : function () {
        var ajsonArray = new Array();
        var ajson = new Object();

        for (let i = 0; i <= 7; i++) {
            if ($('#medi' + i).val() != "" && document.getElementById('medi' + i) != undefined){
                console.log($('#medi' + i).val());
                ajson = $('#medi' + i).val();
                ajsonArray.push(ajson);
            }
        }

        console.log(ajsonArray)

        let data = {
            name: $('#medicase').val(),
            memo: $('#memo').val(),
            color: $('#caseCol').val(),
            count: $('#count').text(),
            medicineLists: ajsonArray
        }

        console.log(data)

        $.ajax({
            type: 'POST',
            url: '/api/medi/add',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('약통이 등록되었습니다.');
            window.location.href = '/medi/medibox'; // 약통 등록 성공시 리로드
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 약통 수정
    update : function (id) {
        let data = {
            name: $('#editMediName').val(),
            memo: $('#editMediMemo').val(),
            color: $('#editcaseColor').val(),
            count: $('#editCount').text(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/medi/update/' + id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('약통이 수정되었습니다.');
            window.location.href = '/medi/box/' + id; // 약통 등록 성공시 페이지 리로드
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 약통 삭제
    delete : function (id) {
        $.ajax({
            type: 'DELETE',
            url: '/api/medi/delete/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('약통이 삭제되었습니다.');
            window.location.href = '/medi/medibox'; // 약통 삭제 성공시 페이지 리로드
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },


    // 스케줄 조회
    scheduleGet : function (id) {
        let data = {
            scheduleId: id,
        };

        $.ajax({
            type: 'POST',
            url: '/api/medi/schedule/get',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data) {
            console.log(data['medicineBoxName']);
            let dateTime = data['dateTime'];
            let date = dateTime.substring(0,10)
            let time = dateTime.substring(11,16)

            $('#editmedicase').text(data['medicineBoxName'])
            $('#editstartday').val(date)
            $('#editaddTime').val(time)

            if (data['cycle'] == 1){
                $('#editselectdayBtn2').val(1);
                document.all["editBel1"].style.visibility = "hidden";
                document.all["editBel2"].style.visibility = "hidden";
                document.all["editBel3"].style.visibility = "hidden";
            }
            else if (data['cycle'] == 0){
                $('#editselectdayBtn2').val(2);
                document.all["editBel1"].style.visibility = "hidden";
                document.all["editBel2"].style.visibility = "hidden";
                document.all["editBel3"].style.visibility = "visible";

                let week = data['week'];

                if (((week>>0)&1) == 1)
                    document.getElementById("editMon").checked = true;
                if (((week>>1)&1) == 1)
                    document.getElementById("editTue").checked = true;
                if (((week>>2)&1) == 1)
                    document.getElementById("editWed").checked = true;
                if (((week>>3)&1) == 1)
                    document.getElementById("editThu").checked = true;
                if (((week>>4)&1) == 1)
                    document.getElementById("editFri").checked = true;
                if (((week>>5)&1) == 1)
                    document.getElementById("editSat").checked = true;
                if (((week>>6)&1) == 1)
                    document.getElementById("editSun").checked = true;
            }
            else{
                $('#editselectdayBtn2').val(3);
                document.all["editBel1"].style.visibility = "visible";
                document.all["editBel2"].style.visibility = "visible";
                document.all["editBel3"].style.visibility = "hidden";
                $('#editstepper2').val(data['cycle']);
            }

        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    // 스케줄 추가
    scheduleAdd: function (id) {
        let day = $('#startday').val() + " " + $('#addBellTime').val();

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
                cycle_result = $('#bellstepper').val();
            else if ($('#sel2').val() == "1")
                cycle_result = $('#bellstepper').val() * 7;
            else if ($('#sel2').val() == "2")
                cycle_result = $('#bellstepper').val() * 30;
        }

        let data = {
            startday : day,
            cycle: cycle_result,
            week: week_result,
            medicineBoxId: $('#mediBellcase').val(),
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
            window.location.href = '/medi/box/' + id; // 스케줄 수정 성공 메인페이지 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 스케줄 수정
    scheduleUpdate : function (scheduleId, id) {
        let day = $('#editstartday').val() + " " + $('#editaddTime').val();

        let cycle_result
        let week_result = 0;

        // 주기 검사,계산
        if ($('#editselectdayBtn2').val() == "1")
            cycle_result = 1;
        else if ($('#editselectdayBtn2').val() == "2"){
            cycle_result = 0;
            if ($('#editMon').is(':checked'))
                week_result = week_result + 1;
            if ($('#editTue').is(':checked'))
                week_result = week_result + 2;
            if ($('#editWed').is(':checked'))
                week_result = week_result + 4;
            if ($('#editThu').is(':checked'))
                week_result = week_result + 8;
            if ($('#editFri').is(':checked'))
                week_result = week_result + 16;
            if ($('#editSat').is(':checked'))
                week_result = week_result + 32;
            if ($('#editSun').is(':checked'))
                week_result = week_result + 64;

        }
        else if ($('#editselectdayBtn2').val() == "3"){
            if ($('#editBel2').val() == "0")
                cycle_result = $('#editstepper2').val();
            else if ($('#editBel2').val() == "1")
                cycle_result = $('#editstepper2').val() * 7;
            else if ($('#editBel2').val() == "2")
                cycle_result = $('#editstepper2').val() * 30;


        }

        let data = {
            startday : day,
            cycle: cycle_result,
            week: week_result,
        }

        $.ajax({
            type: 'POST',
            url: '/api/medi/schedule/update/' + scheduleId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('스케줄이 수정되었습니다.');
            window.location.href = '/medi/box/' + id; // 스케줄 수정 성공 메인페이지 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 스케줄 삭제
    scheduleDelete : function (scheduleId, id) {
        $.ajax({
            type: 'DELETE',
            url: '/api/medi/schedule/delete/'+scheduleId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('스케줄이 삭제되었습니다.');
            window.location.href = '/medi/box/' + id; // 스케줄 삭제 성공시 메인페이지 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};
mediBox.init();