let mediSearch = {
    init : function () {
        let _this = this;

        // 검색값 유지
        let urlParams = decodeURI(decodeURIComponent(window.location.search));
        let param = new URLSearchParams(urlParams);
        console.log(urlParams);

        if (param.has('name'))
            document.getElementById("autoSizingInput3").value = param.get('name');
        if (param.has('companyName'))
            document.getElementById("autoSizingInput4").value = param.get('companyName');
        if (param.has('mark'))
            document.getElementById("autoSizingInput1").value = param.get('mark');

        let formulation = param.get('formulation')
        if (param.has('formulation'))
            $(":radio[name='btnradio'][value='" + formulation + "']").attr('checked', true);
        let line = param.get('line')
        if (param.has('line'))
            $(":radio[name='btnradio3'][value='" + line + "']").attr('checked', true);
        let shape = param.get('shape')
        if (param.has('shape'))
            $(":radio[name='btnradio1'][value='" + shape + "']").attr('checked', true);
        let color = param.get('color')
        if (param.has('color'))
            $(":radio[name='btnradio2'][value='" + color + "']").attr('checked', true);

        // 다시 입력
        $('#rebtn').on('click', function () {
            document.getElementById("autoSizingInput3").value = "";
            document.getElementById("autoSizingInput4").value = "";
            document.getElementById("autoSizingInput1").value = "";

            $("#btnradio6").prop('checked', true);
            $("#btnradio___6").prop('checked', true);
            $("#btnradio_12").prop('checked', true);
            $("#btnradio__17").prop('checked', true);
        });



        // 알약 검색
        $('#searchBtn').on('click', function () {
            let url = '/medi/searchDetail?'

            let name = document.getElementById('autoSizingInput3').value;
            if(name !== "")
                url = url + 'name=' + name + '&';

            let companyName = document.getElementById('autoSizingInput4').value;
            if(companyName !== "")
                url = url + 'companyName=' + companyName + '&';

            let mark = document.getElementById('autoSizingInput1').value;
            if(mark !== "")
                url = url + 'mark=' + mark + '&';


            let formulation = $('input[name="btnradio"]:checked').val();
            if(formulation !== "전체")
                url = url + 'formulation=' + formulation + '&';

            let line = $('input[name="btnradio3"]:checked').val();
            if(line !== "전체")
                url = url + 'line=' + line + '&';

            let shape = $('input[name="btnradio1"]:checked').val();
            if(shape !== "전체")
                url = url + 'shape=' + shape + '&';

            let color = $('input[name="btnradio2"]:checked').val();
            if(color !== "전체")
                url = url + 'color=' + color + '&';

            location.href=url;
        });
    },
};
mediSearch.init();