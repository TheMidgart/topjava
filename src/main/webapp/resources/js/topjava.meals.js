const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data) {
                        return moment(data).format('YYYY-MM-DD HH:mm');
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-meal-excess", data.excess);

            }
        })
    );
});

jQuery('#timepicker_start').datetimepicker({
    datepicker: false,
    format: 'H:i'
})

jQuery('#timepicker_end').datetimepicker({
    datepicker: false,
    format: 'H:i'
});

jQuery('#datepicker_start').datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
})

jQuery('#datepicker_end').datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
});