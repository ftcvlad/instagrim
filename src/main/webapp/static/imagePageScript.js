


function addComment(contextPath, username){
    
    
   
    
    var commentText = $("#enterComment textarea").val();
    var targetSUUID = $("#enterComment button").data("imagesuuid");
    
   
    $.post(contextPath+"/Comment/"+targetSUUID ,{comment:commentText} , function(){
            // if not logged in user tries to comment he was redirected to login
             $("#commentList ul").append('<li><strong><a href="/Instagrim/Images/'+username+'">'+username+'</a></strong> '+commentText+'</li>');
             
                            
        
        $("#enterComment textarea").val('');
       
        
    }).fail(function(jqXHR, textStatus,errorMessage){
        if (jqXHR.status === 401) {//session expired
            window.location = contextPath + "/Login";
        }
    });
}