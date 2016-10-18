/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    function doStuff(contextPath){
              
        
        
        
        
        var img = document.createElement("img");
        img.src = contextPath+"/filters/"+document.querySelector('input[name="rate"]:checked').value;

        var elem = document.getElementById("appendImage");
        elem.appendChild(img);
    }