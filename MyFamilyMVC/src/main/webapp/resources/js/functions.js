function getAllData(){ 

//console.log(name);
$.ajax({
        type : "GET",
        contentType : "application/json",
        url : "http://localhost:8080/FamilyTree/rest/getWholeTree",
        dataType : 'text',
        timeout : 100000,
        success : function(data) {
            // now you have "data" which is in json format-same data that is displayed on browser.
            displayData(data);      
        },
        error : function(e) {
            //do something
        	console.log("error");
        },
        done : function(e) {
            //do something
        }
    });
}




function getPersonData(){ 
var name = 	document.getElementById("getPerson").value;
//console.log(name);
$.ajax({
        type : "GET",
        contentType : "application/json",
        url : "http://localhost:8080/FamilyTree/rest/getPerson/"+name,
        dataType : 'text',
        timeout : 100000,
        success : function(data) {
            // now you have "data" which is in json format-same data that is displayed on browser.
            displayData(data);      
        },
        error : function(e) {
            //do something
        	console.log("error");
        },
        done : function(e) {
            //do something
        }
    });
}

//var $loading = $('#loadingDiv').hide();
$(document)
  .ajaxStart(function () {
    $("#loadingDiv").css('display', 'inline-block');
  })
  .ajaxStop(function () {
	$("#loadingDiv").css('display', 'none');
  });

function displayData(data){
    //your codes to parse and display json data  in html table in your page.
	if (data !=null || data!=undefined){
	      var myDiv = document.getElementById("myTree");
	        var newDiv;
	        var parentNode = myDiv.parentNode;
	        if(parentNode) {
	        parentNode.removeChild(myDiv);
	        newDiv = document.createElement('div');
	        newDiv.setAttribute('id','myTree');
	        newDiv.setAttribute("class", "col-md-6 col-md-offset-3");
	        newDiv.setAttribute("style", "background-color:#FFFFFF");
	        parentNode.appendChild(newDiv);
	        }
	        else {
	        newDiv = myDiv;
	        }
	       
	        newDiv.innerHTML = data;
	        makeTree();
	        console.log(data);
	}
	else{
		console.log("No such data!");
	}
}

var activated=0;

