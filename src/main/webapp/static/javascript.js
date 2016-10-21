$(function(){
   
   
    $('input[name=searchUsername]').on('keyup', function (e) {
        if (e.keyCode === 13) {
            window.location = "/Instagrim/Images/"+$('input[name=searchUsername]').val();
        }
    });
   
   
});


function logoutF(){
    
    $.post("/Instagrim/Logout",function(){
        window.location  = "/Instagrim";
    });
    
    
}


function showUser(context){
   
    
    window.location = context+"/Images/"+$('input[name=searchUsername]').val();
}