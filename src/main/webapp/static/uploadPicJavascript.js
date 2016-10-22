/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//when uploading from file
    function getFileInputAsBlob(){
              
        $("#messageDiv").html('');
        var blobFile = document.getElementById("exampleInputFile").files[0];
       
        if (blobFile===undefined){
           
            $("#messageDiv").html('<div class="alert alert-danger" id="errorDiv" ><strong>Error!</strong>Select file</div>');
            return;
        }
        uploadPicture(blobFile);
    }
    
//when uploading returned pic    
    function getDataURIAsBlob(){
      $("#messageDiv").html('');
      var blobFile = dataURItoBlob($("#appendImage img").attr('src'));
      uploadPicture(blobFile);
    }
    
    //http://stackoverflow.com/questions/4998908/convert-data-uri-to-file-then-append-to-formdata
    function dataURItoBlob(dataURI) {
        var byteString = atob(dataURI.split(',')[1]);
        var ab = new ArrayBuffer(byteString.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < byteString.length; i++) {
            ia[i] = byteString.charCodeAt(i);
        }
        return new Blob([ab], { type: dataURI.split(',')[0].split(':')[1].split(';')[0] });
    }
    
    
    function uploadPicture(blobFile) {
            var formData = new FormData();
            formData.append("fileToUpload", blobFile);

            $.ajax({
                url:  "/Instagrim/Image",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function () {
                    $("#messageDiv").html('<div class="alert alert-success" id="errorDiv" ><strong>Hooray!</strong> Uploaded successfully</div>');

                },
                error: function (jqXHR, textStatus, errorMessage) {

                    if (jqXHR.status === 401) {
                        window.location = "/Instagrim/Login";
                    } else {
                        $("#messageDiv").html('<div class="alert alert-danger" id="errorDiv" ><strong>Error!</strong>' + jqXHR.responseText + '</div>');
                    }


                }
            });


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
               success: function(data) {
                   
                   $("#appendImage").html('');
                   if ($('#2 button').length<2){
                       $('#2 button').after('<button type="button" class="btn btn-primary" onclick="getDataURIAsBlob()" >Save</button>');
                   }
                   
                    var img = document.createElement("img");
                    img.src = data;
                    img.className += "img-responsive";
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
    
  