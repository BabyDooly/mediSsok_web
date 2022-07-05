let mediBox = {
    init : function () {
        let _this = this;
        let id;

        // 약통 추가
        $('#btn-add').on('click', function () {
            if (document.getElementById("medicase").value == "")
                alert("약통 이름은 필수입니다.");
            else
                _this.add();
        });

        // 약통 조회
        $('.mediBox').on('click', function () {
            id = $(this).attr('id');
            _this.get(id);
        })

        // 약통 수정
        $('#btn-edit').on('click', function () {
            _this.update(id);
        })

        // 약통 삭제
        $('#btn-delete').on('click', function () {
            _this.delete(id);
        });
    },

    // 약통 추가
    add : function () {
        let data = {
            name: $('#medicase').val(),
            memo: $('#memo').val(),
            color: $('#caseCol').val(),
            count: $('#count').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/medi/add',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('약통이 등록되었습니다.');
            window.location.href = '/medi/medibox'; // 글등록 성공 메인페이지 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 약통 조회
    get : function (id) {
        let data = {
            medicineBoxId: id,
        };

        $.ajax({
            type: 'POST',
            url: '/api/medi/get',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data) {
            $('#mediName').text(data['name'])
            $('#mediMemo').text(data['memo'])
            $('#mediCounts').text(data['count'])
            $('#mediImg2').css('backgroundColor', data['color'])

            $('#editMediName').val(data['name'])
            $('#editMediMemo').val(data['memo'])
            $('#editMediCounts').val(data['count'])
            $('#editcaseColor').val(data['color'])

            $('#portfolioModal1').modal('show');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    // 약통 수정
    update : function (id) {
        let data = {
            name: $('#editMediName').val(),
            memo: $('#editMediMemo').val(),
            color: $('#editcaseColor').val(),
            count: $('#editMediCounts').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/medi/update/' + id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('약통이 수정되었습니다.');
            window.location.href = '/medi/medibox'; // 글수정 성공 메인페이지 이동
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
            window.location.href = '/medi/medibox'; // 약통 삭제 성공시 메인페이지 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};
mediBox.init();

