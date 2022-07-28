let main = {
    init: function () {
        let _this = this;
        let linkId;

        // 연동 수정(아이디 값 가져오기)
        $('.update').on('click', function () {
            linkId = $(this).attr("value");
            _this.linkPermit(linkId);
        });

        // 연동 거부
        $('.refuse').on('click', function () {
            linkId = $(this).attr("value");
            if (confirm("정말 거부 하시겠습니까?") == true) {
                _this.linkRefuse(linkId);
            } else {
                alert("취소되었습니다.")
            }
        });
    },
    // 연동 수정
    linkPermit: function (id) {
        let update = {
            permit: true
        };
        console.log(update);

        let email = $('#hidden' + id).val();
        let nickname = email.split('@');

        let linkData = {
            userEmail: email,
            permit: true,
            nickname: nickname[0]
        };
        console.log(linkData);

        $.ajax({
            type: 'POST',
            url: '/api/link/permit/update/' + id,
            dataType: "json",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(update),
        }).done(function () {
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
        $.ajax({
            type: 'POST',
            url: '/api/link/add',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(linkData)
        }).done(function () {
            alert('연동 되었습니다.');
            window.location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    // 연동 거부
    linkRefuse: function (id) {
        $.ajax({
            type: 'DELETE',
            url: '/api/link/delete/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('연동이 거부되었습니다.');
            window.location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};
main.init();