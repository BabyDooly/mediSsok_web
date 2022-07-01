let mediBox = {
    init : function () {
        let _this = this;
        $('#btn-add').on('click', function () {
            _this.add();
        });
    },
    add : function () {
        var data = {
            name: $('#medicase').val(),
            memo: $('#memo').val(),
            color: $('#caseCol').val(),
            count: $('#mediCount').val(),
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

};
mediBox.init();