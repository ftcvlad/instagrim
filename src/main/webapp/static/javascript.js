$(function(){
   
});


function logoutF(){
    
    $.post("/Instagrim/Logout",function(){
        window.location  = "/Instagrim";
    });
    
    
}