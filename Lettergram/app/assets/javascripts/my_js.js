function check_empty(){
if(document.getElementById('name').value == "" 
|| document.getElementById('email').value == "" 
||document.getElementById('msg').value == "" ){
alert ("Fill All Fields !");
}
	else {  
	document.getElementById('form').submit();  
	alert ("Form submitted successfully...");
	}
}

//function to display Popup
function div_show(){ 
document.getElementById('abc').style.display = "block";
}

//function to display Popup
function div_show2(){ 
document.getElementById('abc').style.display = "block";
}

//function to check target element
function check(e){ 
var target = (e && e.target) || (event && event.srcElement); 

var obj = document.getElementById('abc'); 
var obj2 = document.getElementById('img_box'); 

checkParent(target)?obj.style.display='none':null; 
target==obj2?obj.style.display='block':null; 
} 

//function to check parent node and return result accordingly
function checkParent(t){ 
	while(t.parentNode){ 
		if(t==document.getElementById('abc'))
			{ 
				return false 
			}
		else if(t==document.getElementById('close'))
			{
				return true
			} 
		t=t.parentNode 
	} 
	return true 
} 
