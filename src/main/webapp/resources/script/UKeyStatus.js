
function ukeyok(){
	if (document.getElementById('IID_SecureWeb_I_SerialNumber').value!=''){
		//document.getElementById('WebacResult').innerHTML="UKey已插入！";
	}
	if  (document.getElementById('IID_SecureWeb_I_SerialNumber').value==''){
		//document.getElementById('WebacResult').innerHTML="请插入UKey！";
		window.opener = null;
		window.open(' ', '_self', ' '); 
		window.close();
	}
}