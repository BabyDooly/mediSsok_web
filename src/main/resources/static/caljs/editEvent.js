/* ****************
 *  일정 편집
 * ************** */
var editEvent = function (event, element, view) {

    $('#deleteEvent').data('id', event._id); //클릭한 이벤트 ID

    $('.popover.fade.top').remove();
    $(element).popover("hide");

    if (event.allDay === true) {
        editAllDay.prop('checked', true);
    } else {
        editAllDay.prop('checked', false);
    }

    if (event.end === null) {
        event.end = event.start;
    }

    if (event.allDay === true && event.end !== event.start) {
        editEnd.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD HH:mm'))
    } else {
        editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
    }

    modalTitle.html('일정 수정');
    editTitle.val(event.title);
    editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
    editType.val(event.type);
    editDesc.val(event.description);
    editColor.val(event.backgroundColor);
    addBtnContainer.hide();
    modifyBtnContainer.show();
    // eventModal.modal('show');

    let query = window.location.search;         // http://localhost:8080/notice?id=1&name=하나
    let param = new URLSearchParams(query);     // ?id=1&name=하나


    if (param.get("memberId") != null)
        eventModal.modal('hide');

    else
        eventModal.modal('show');


    element2.value=editColor.val();
    element2.style.backgroundColor=editColor.val();
    if(element2.value=='#D25565'){
        element2.innerText='안먹었어요';
    }else{
        element2.innerText='먹었어요';
    }

    //업데이트 버튼 클릭시
    $('#updateEvent').unbind();
    $('#updateEvent').on('click', function () {

        if (editStart.val() > editEnd.val()) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (editTitle.val() === '') {
            alert('약통 이름은 필수입니다.')
            return false;
        }

        var statusAllDay;
        var startDate;
        var endDate;
        var displayDate;

        if (editAllDay.is(':checked')) {
            statusAllDay = true;
            startDate = moment(editStart.val()).format('YYYY-MM-DD');
            endDate = moment(editEnd.val()).format('YYYY-MM-DD');
            displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD');
        } else {
            statusAllDay = false;
            startDate = editStart.val();
            endDate = editEnd.val();
            displayDate = endDate;
        }

        eventModal.modal('hide');

        event.allDay = statusAllDay;
        event.title = editTitle.val();
        event.start = startDate;
        event.end = displayDate;
        event.type = editType.val();
        event.backgroundColor = editColor.val();
        event.description = editDesc.val();

        $("#calendar").fullCalendar('updateEvent', event);


        let editEat
        if(element2.value=='#D25565'){
            editEat = false;
        }else{
            editEat = true;
        }

        let data = {
            startday: editStart.val(),
            eatCheck: editEat
        };

        console.log(data);

        //일정 업데이트
        $.ajax({
            type: 'POST',
            url: '/api/status/update/' + event._id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (response) {
                // location.reload();
            }
        });

    });
};

// 삭제버튼
$('#deleteEvent').on('click', function () {
    
    $('#deleteEvent').unbind();
    $("#calendar").fullCalendar('removeEvents', $(this).data('id'));
    eventModal.modal('hide');

    //삭제시
    $.ajax({
        type: 'DELETE',
        url: '/api/medi/alarm/delete/'+ $(this).data('id'),
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        success: function (response) {
            alert('일정이 삭제되었습니다.')
            location.reload();
        }
    });
});