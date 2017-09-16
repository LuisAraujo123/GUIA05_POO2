$(document).ready(function() {
    $.fn.initBootTable = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consLibr([{name : 'codiLibr', value : row.id.trim()}]);
        });
        return false;
    };
    
    $('#modaFormLibr').on('show.bs.modal', function() {
        INIT_OBJE_MODA_LIBR();
    });
    
    INIT_OBJE_LIBR();
});

function INIT_OBJE_LIBR()
{
    $("#tablLibr").initBootTable();
}

function INIT_OBJE_MODA_LIBR()
{
    $("#formTest\\:btonElim").confirmation({container: '#formTest'});
}