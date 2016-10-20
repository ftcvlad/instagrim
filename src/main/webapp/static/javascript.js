$(function(){
   
   
  
   
   
});


function logoutF(){
    
    $.post("/Instagrim/Logout",function(){
        window.location  = "/Instagrim";
    });
    
    
}

function showUser(context){
   
    
    window.location = context+"/Images/"+$('input[name=searchUsername]').val();
}