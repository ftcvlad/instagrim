/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    function uploadImage(contextPath){
              
        $("#messageDiv").html('');
        var blobFile = document.getElementById("exampleInputFile").files[0];
       
        if (blobFile===undefined){
           
            $("#messageDiv").html('<div class="alert alert-danger" id="errorDiv" ><strong>Error!</strong>Select file</div>');

        }
        else{
            var formData = new FormData();
            formData.append("fileToUpload", blobFile);

            $.ajax({
               url: contextPath+"/Image",
               type: "POST",
               data: formData,
               processData: false,
               contentType: false,
               success: function() {
                   $("#messageDiv").html('<div class="alert alert-success" id="errorDiv" ><strong>Hooray!</strong> Uploaded successfully</div>');
                  
               },
               error: function(jqXHR, textStatus, errorMessage) {
                   
                   if (jqXHR.status===401){
                       window.location = contextPath+"/Login";
                   }
                   else{
                       $("#messageDiv").html('<div class="alert alert-danger" id="errorDiv" ><strong>Error!</strong>'+jqXHR.responseText+'</div>');
                   }
                   
                  
               }
            });
       }
        
        
       
    }
    
    function sendTofilter(contextPath) {
        $("#messageDiv").html('');
        var blobFile = document.getElementById("exampleInputFile").files[0];
       
       if (blobFile===undefined){
         $("#messageDiv").html('<div class="alert alert-danger" id="errorDiv" ><strong>Error!</strong>Select file</div>');  
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
                     if (jqXHR.status===401){
                       window.location = contextPath+"/Login";
                   }
                   else{
                       $("#messageDiv").html('<div class="alert alert-danger" id="errorDiv" ><strong>Error!</strong>'+jqXHR.responseText+'</div>');
                   }
               }
            });
       }
       
    }