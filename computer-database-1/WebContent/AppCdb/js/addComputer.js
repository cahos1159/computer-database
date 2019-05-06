
$(function() {
	    $('#btnSubmit').bind('click', function(){
	        var txtVal =  $('#introduced').val();
	        if(!isDate(txtVal))
	            alert('Invalid Introduced Date');
	    });
	});

$(function() {
    $('#btnSubmit').bind('click', function(){
        var txtVal =  $('#discontinued').val();
        if(!isDate(txtVal))
            alert('Invalid Discontinued Date');
    });
});

function isDate(inputDate){
	var regexDatePattern = /^\d{4}\-\d{1-2}\-\d{1-2}\s\d{1-2}\:\d{1-2}\:\d{1-2}\.\d{1}$/;
	var regexOtherDate = /([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))/;
	if(regexDatePattern.test(inputDate)){
		return true;
	}
	
	else if(regexOtherDate.test(inputDate)){
	
		if($("#introduced").val().localeCompare(inputDate)){
			alert($("#introduced").val());
			return true;
		}
		else{
			alert($("#discontinued").val());
			return true
		}
	}
	else {
		return false;
	}
	
}