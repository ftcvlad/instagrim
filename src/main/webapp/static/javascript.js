$(function(){
   
});


function logoutF(){
    
    $.post("Logout",function(){
        window.location  = "/Instagrim";
    });
    
    
}