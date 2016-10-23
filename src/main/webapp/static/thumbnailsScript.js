/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function follow(targetUser){
    

    $.ajax({
                url:  "/Instagrim/FollowUnfollow/follow",
                type: "POST",
                data: {"targetUser":targetUser},
                success: function () {
                  
                    $("#followDiv button").attr("onclick","unfollow('"+targetUser+"')");
                    $("#followDiv button").text('Unfollow');
                    
                    var currValue = parseInt($("#followersContent span").eq(1).text());
                    $("#followersContent span").eq(1).text(currValue+1);
                },
                error: function (jqXHR, textStatus, errorMessage) {

                    if (jqXHR.status === 401) {
                        window.location = "/Instagrim/Login";
                    }
                    else if (jqXHR.status === 400){//e.g. user modified something and tried to follow non-existing user
                        //if several tabls, and on 1 pressed unfollow again, reload page to get correct value 
                        window.location = "/Instagrim/Images/"+targetUser;
                    }
                }
            });
    
}

function unfollow(targetUser){
      $.ajax({
                url:  "/Instagrim/FollowUnfollow/unfollow",
                type: "POST",
                data: {"targetUser":targetUser},
                success: function () {
                  
                    $("#followDiv button").attr("onclick","follow('"+targetUser+"')");
                    $("#followDiv button").text('Follow');
                    
                    var currValue = parseInt($("#followersContent span").eq(1).text());
                    $("#followersContent span").eq(1).text(currValue-1);
                },
                error: function (jqXHR, textStatus, errorMessage) {

                    if (jqXHR.status === 401) {
                        window.location = "/Instagrim/Login";
                    }
                    else if (jqXHR.status === 400){//e.g. user modified something and tried to follow non-existing user
                        //if several tabls, and on 1 pressed unfollow again, reload page to get correct value
                        window.location = "/Instagrim/Images/"+targetUser;
                    }
                }
            });
    
    
    
    
}