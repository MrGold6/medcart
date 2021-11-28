function validDate(){
    var today = new Date().toISOString().split('T')[0];
    document.getElementById("valid_date").setAttribute('min', today);
}

function validDateBeforeCurrent(){
    var today = new Date().toISOString().split('T')[0];
    document.getElementById("valid_date").setAttribute('max', today);
}