$(function () {
    $('#appointment_time').change(function () {
        var ss = $('#appointment_time').val();
        if ($.trim(ss) === '') {
            $('#cancel_appointment').css('display','none');
        }else {
            $('#cancel_appointment').css('display','inline-block');
        }
    });

    $('#cancel_appointment').click(function () {
        $('#appointment_time').val('');
        $('#cancel_appointment').css('display','none');
    });
});