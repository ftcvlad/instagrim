/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    function doStuff(contextPath){
              
        
        
        
        
       
    }
    
    function sendTofilter(contextPath) {
        $("#errorDiv").hide();
        var blobFile = document.getElementById("exampleInputFile").files[0];
       
       if (blobFile===undefined){
           $("#errorDiv").html("<strong>Error!</strong> Select file")
           $("#errorDiv").show();
           
       }
       else{
            var formData = new FormData();
            formData.append("fileToUpload", blobFile);

            $.ajax({
               url: contextPath+"/filters/"+document.querySelector('input[name="filterName"]:checked').value,
               type: "POST",
               data: formData,
               processData: false,
               contentType: false,
               success: function() {
                    var img = document.createElement("img");
                    img.src = contextPath+"/filters/getimage";

                    var elem = document.getElementById("appendImage");
                    elem.appendChild(img);
               },
               error: function(jqXHR, textStatus, errorMessage) {
                   $("#errorDiv").html("<strong>Error!</strong>"+jqXHR.responseText)
                   $("#errorDiv").show();
               }
            });
       }
       
    }