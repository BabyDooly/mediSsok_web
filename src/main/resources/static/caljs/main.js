var draggedEventIsAllDay;
var activeInactiveWeekends = true;

var calendar = $('#calendar').fullCalendar({
    /** ******************
     *  OPTIONS
     * *******************/
    locale: 'ko',
    timezone: "local",
    nextDayThreshold: "09:00:00",
    allDaySlot: true,
    displayEventTime: true,
    displayEventEnd: true,
    firstDay: 0, //월요일이 먼저 오게 하려면 1
    weekNumbers: false,
    selectable: true, // 날짜 드래그 다중선택
    weekNumberCalculation: "ISO",
    defaultView:'month',
    eventLimit: 2, // 뒤에 숫자보다 셀이 많으면 +추가로 뜸
    views: {
        month: {
            eventLimit: 2,
        },
    },
    eventLimitClick: 'popover', //+된거 눌렀을때 popover
    eventLimitText:'개',
    navLinks: false, // 달력상의 날짜를 클릭할 수 있는지

    timeFormat: 'HH:mm',
    defaultTimedEventDuration: '01:00:00',
    editable: false, // 이벤트 드래그, 리사이징 등의 편집 여부 결정
    minTime: '00:00:00',
    maxTime: '24:00:00',
    slotLabelFormat: 'HH:mm',
    weekends: true,
    nowIndicator: true,
    dayPopoverFormat: 'MM/DD dddd',
    longPressDelay: 0,
    eventLongPressDelay: 0,
    selectLongPressDelay: 0,

    header: {
        left: 'today, prevYear, nextYear',
        center: 'prev, title, next',
        right: 'month, listWeek'
    },
    views: {
        month: {
            columnFormat: 'dddd'
        },
        listWeek: {
            columnFormat: ''
        }
    },

    /* ****************
     *  일정 받아옴
     * ************** */
    events: function (start, end, timezone, callback) {
        let query = window.location.search;         // http://localhost:8080/notice?id=1&name=하나
        let param = new URLSearchParams(query);     // ?id=1&name=하나

        let idUrl;

        if (param.get("memberId") != null)
            idUrl = "/api/status/list/" + param.get('memberId')

        else
            idUrl = "/api/status/list";

        console.log(idUrl)

        $.ajax({
            type: "get",
            url: idUrl,
            data: {
                // 화면이 바뀌면 Date 객체인 start, end 가 들어옴
                //startDate : moment(start).format('YYYY-MM-DD'),
                //endDate   : moment(end).format('YYYY-MM-DD')
            },
            success: function (response) {
                console.log(response);

                var fixedDate = response.map(function (array) {
                    if (array.allDay && array.start !== array.end) {
                        array.end = moment(array.end).add(1, 'days'); // 이틀 이상 AllDay 일정인 경우 달력에 표기시 하루를 더해야 정상출력
                    }
                    return array;
                });
                callback(fixedDate);
            }
        });
    },

    eventAfterAllRender: function (view) {
        if (view.name == "month") $(".fc-content").css('height', 'auto');
    },

    //일정 리사이즈
    eventResize: function (event, delta, revertFunc, jsEvent, ui, view) {
        $('.popover.fade.top').remove();

        /** 리사이즈시 수정된 날짜반영
         * 하루를 빼야 정상적으로 반영됨. */
        var newDates = calDateWhenResize(event);

        //리사이즈한 일정 업데이트
        $.ajax({
            type: "get",
            url: "",
            data: {
                //id: event._id,
                //....
            },
            success: function (response) {
                alert('수정: ' + newDates.startDate + ' ~ ' + newDates.endDate);
            }
        });

    },

    eventDragStart: function (event, jsEvent, ui, view) {
        draggedEventIsAllDay = event.allDay;
    },

    //일정 드래그앤드롭
    eventDrop: function (event, delta, revertFunc, jsEvent, ui, view) {
        $('.popover.fade.top').remove();

        //주,일 view일때 종일 <-> 시간 변경불가
        if (view.type === 'agendaWeek' || view.type === 'agendaDay') {
            if (draggedEventIsAllDay !== event.allDay) {
                alert('드래그앤드롭으로 종일<->시간 변경은 불가합니다.');
                location.reload();
                return false;
            }
        }

        // 드랍시 수정된 날짜반영
        var newDates = calDateWhenDragnDrop(event);

        //드롭한 일정 업데이트
        $.ajax({
            type: "get",
            url: "",
            data: {
                //...
            },
        });

    },
    //월 클릭했을때
    select: function (startDate, endDate, jsEvent, view) {

        $(".fc-body").unbind('click');
        $(".fc-body").on('click', 'td', function (e) {

            $("#contextMenu")
                .addClass("contextOpened")
                .css({
                    display: "block",
                    left: e.pageX,
                    top: e.pageY
                });
            return false;
        });

        var today = moment();

        if (view.name == "month") {
            startDate.set({
                hours: today.hours(),
                minute: today.minutes()
            });
            startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
            endDate = moment(endDate).subtract(1, 'days');

            endDate.set({
                hours: today.hours() + 1,
                minute: today.minutes()
            });
            endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
        } else {
            startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
            endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
        }

        //날짜 클릭시 카테고리 선택메뉴
        var $contextMenu = $("#contextMenu");
        $contextMenu.on("click", "a", function (e) {
            e.preventDefault();

            //닫기 버튼이 아닐때
            if ($(this).data().role !== 'close') {
                newEvent(startDate, endDate, $(this).html());
            }

            $contextMenu.removeClass("contextOpened");
            $contextMenu.hide();
        });

        $('body').on('click', function () {
            $contextMenu.removeClass("contextOpened");
            $contextMenu.hide();
        });

    },

    //이벤트 클릭시 수정이벤트
    eventClick: function (event, jsEvent, view) {
        editEvent(event);
    }

});

function getDisplayEventDate(event) {

    var displayEventDate;

    if (event.allDay == false) {
        var startTimeEventInfo = moment(event.start).format('HH:mm');
        var endTimeEventInfo = moment(event.end).format('HH:mm');
        displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo;
    } else {
        displayEventDate = "하루종일";
    }

    return displayEventDate;
}

function calDateWhenResize(event) {

    var newDates = {
        startDate: '',
        endDate: ''
    };

    if (event.allDay) {
        newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
        newDates.endDate = moment(event.end._d).subtract(1, 'days').format('YYYY-MM-DD');
    } else {
        newDates.startDate = moment(event.start._d).format('YYYY-MM-DD HH:mm');
        newDates.endDate = moment(event.end._d).format('YYYY-MM-DD HH:mm');
    }

    return newDates;
}

function calDateWhenDragnDrop(event) {
    // 드랍시 수정된 날짜반영
    var newDates = {
        startDate: '',
        endDate: ''
    }

    // 날짜 & 시간이 모두 같은 경우
    if (!event.end) {
        event.end = event.start;
    }

    //하루짜리 all day
    if (event.allDay && event.end === event.start) {
        console.log('1111')
        newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
        newDates.endDate = newDates.startDate;
    }

    //2일이상 all day
    else if (event.allDay && event.end !== null) {
        newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
        newDates.endDate = moment(event.end._d).subtract(1, 'days').format('YYYY-MM-DD');
    }

    //all day가 아님
    else if (!event.allDay) {
        newDates.startDate = moment(event.start._d).format('YYYY-MM-DD HH:mm');
        newDates.endDate = moment(event.end._d).format('YYYY-MM-DD HH:mm');
    }

    return newDates;
}