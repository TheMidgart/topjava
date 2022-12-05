const userAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#mealDatatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function updateFilter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: $('#formFilter').serialize(),
        success: updateTableByData
    });

}

function resetFilter(){
    let inputs = $('#formFilter input');
    for (let i=0; i<inputs.length; i++){
        inputs[i].value = null;
    }
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl,
        success: updateTableByData
    });

}