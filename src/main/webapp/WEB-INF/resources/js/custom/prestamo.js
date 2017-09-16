$(document).ready(function() {
    $.fn.initBootTable = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consPres([{name : 'codiPres', value : row.id.trim()}]);
        });
        return false;
    };
    
    $('#modaFormPres').on('show.bs.modal', function() {
        INIT_OBJE_MODA_PRES();
    });
    
    INIT_OBJE_PRES();
});

function INIT_OBJE_PRES()
{
    $("#tablPres").initBootTable();
}

function INIT_OBJE_MODA_PRES()
{
    $("#formTest\\:btonElim").confirmation({container: '#formTest'});
}