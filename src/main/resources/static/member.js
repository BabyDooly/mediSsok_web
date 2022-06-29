var main = {
    init : function () {
        var _this = this;
        $('#btn-update').on('click', function () {
            _this.update();
        });
    },

    update : function () {
        var data = {
            nickname: $('#nameText').val(),
            phoneNum: $('#callText').val()
        };

        var id = 1;

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('정보가 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};
main.init();