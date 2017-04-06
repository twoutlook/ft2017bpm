
var txt_sg = document.getElementById("txt_sg");
var txt_sgzg = document.getElementById("txt_sgzg");
var txt_cz = document.getElementById("txt_cz");
var kdcrq = document.getElementById("kdcrq_txt");

document.write("<style>");
if ( typeof (s_detail5Obj) != 'undefined') {
	document.write("#" + s_detail5Obj.getId() + " .aw-column-0 {text-align: right;}");
}
if ( typeof (s_detail1Obj) != 'undefined') {
	document.write("#" + s_detail1Obj.getId() + " .aw-column-0 {text-align: right;}");
}
if ( typeof (s_detail6Obj) != 'undefined') {
	document.write("#" + s_detail6Obj.getId() + " .aw-column-0 {text-align: right;}");
	document.write("#" + s_detail6Obj.getId() + " .aw-column-6 {text-align: right;}");
}
document.write("</style>");

function formCreate(){
    return true;
}
function formOpen(){
	if (formMode != "isPrintForm") {
		/********Activity Reload Grid Script********/
		var s_detail1Value = document.getElementById("s_detail1").value;
		if(s_detail1Value != ""){
			var s_detail1Data = eval(s_detail1Value);
			s_detail1Obj.reload(s_detail1Data);
		}
		var s_detail5Value = document.getElementById("s_detail5").value;
		if(s_detail5Value != ""){
			var s_detail5Data = eval(s_detail5Value);
			s_detail5Obj.reload(s_detail5Data);
		}
		var s_detail6Value = document.getElementById("s_detail6").value;
		if(s_detail6Value != ""){
			var s_detail6Data = eval(s_detail6Value);
			s_detail6Obj.reload(s_detail6Data);
		}

		/********Set Grid Width Script********/
		s_detail5Obj.setColumnWidth(70, 0);
		s_detail5Obj.setColumnWidth(100, 1);
		s_detail5Obj.setColumnWidth(130, 2);
		s_detail5Obj.setColumnWidth(150, 3);
		s_detail5Obj.setColumnWidth(200, 4);
		s_detail5Obj.setColumnWidth(200, 5);
		s_detail5Obj.setColumnWidth(100, 6);
		s_detail5Obj.setColumnWidth(100, 7);
		s_detail5Obj.setColumnWidth(200, 8);
		s_detail5Obj.setColumnWidth(200, 9);
		s_detail5Obj.setColumnWidth(200, 10);
		s_detail1Obj.setColumnWidth(50, 0);
		s_detail1Obj.setColumnWidth(100, 1);
		s_detail1Obj.setColumnWidth(110, 2);
		s_detail1Obj.setColumnWidth(100, 3);
		s_detail1Obj.setColumnWidth(100, 4);
		s_detail1Obj.setColumnWidth(100, 5);
		s_detail1Obj.setColumnWidth(100, 6);
		s_detail1Obj.setColumnWidth(190, 7);
		s_detail1Obj.setColumnWidth(140, 8);
		s_detail1Obj.setColumnWidth(140, 9);
		s_detail1Obj.setColumnWidth(200, 10);
		s_detail1Obj.setColumnWidth(200, 11);
		s_detail1Obj.setColumnWidth(200, 12);
		s_detail1Obj.setColumnWidth(200, 13);
		s_detail1Obj.setColumnWidth(100, 14);
		s_detail1Obj.setColumnWidth(100, 15);
		s_detail1Obj.setColumnWidth(100, 16);
		s_detail1Obj.setColumnWidth(300, 17);
		s_detail6Obj.setColumnWidth(70, 0);
		s_detail6Obj.setColumnWidth(110, 1);
		s_detail6Obj.setColumnWidth(100, 2);
		s_detail6Obj.setColumnWidth(100, 3);
		s_detail6Obj.setColumnWidth(100, 4);
		s_detail6Obj.setColumnWidth(100, 5);
		s_detail6Obj.setColumnWidth(140, 6);
		s_detail6Obj.setColumnWidth(200, 7);
		s_detail6Obj.setColumnWidth(100, 8);
		s_detail6Obj.setColumnWidth(100, 9);
		s_detail6Obj.setColumnWidth(80, 10);
	}

	/********NoBorder TextBox Script********/
	document.getElementById("xmdadocno_desc").style.border = "none";
	document.getElementById("xmda004_desc").style.border = "none";
	document.getElementById("xmda002_desc").style.border = "none";
	document.getElementById("xmda003_desc").style.border = "none";
	document.getElementById("xmda027_desc").style.border = "none";
	document.getElementById("xmda009_desc").style.border = "none";
	document.getElementById("xmda010_desc").style.border = "none";
	document.getElementById("xmda011_desc").style.border = "none";
	document.getElementById("xmda035_desc").style.border = "none";
	document.getElementById("xmda015_desc").style.border = "none";
	document.getElementById("xmda017_desc").style.border = "none";
	document.getElementById("xmda018_desc").style.border = "none";
	document.getElementById("xmda023_desc").style.border = "none";

	return true;
}
function formSave(){
if(checkNull()){
	return true;}
return false;
}
function formClose(){
	return true;
}

//获取单选框值
function rb_sg_onclick(){    //radiobutton控件取值
	var rb_sg = document.getElementsByName("rb_sg");
	for(var i = 0 ;i<rb_sg.length;i++){
			 if(rb_sg[i].checked){
				document.getElementById("hdn_sg").value=rb_sg[i].value;//实际值
					}      
		}
}
function rb_sgzg_onclick(){    //radiobutton控件取值
	var rb_sgzg = document.getElementsByName("rb_sgzg");
	for(var i = 0 ;i<rb_sgzg.length;i++){
			 if(rb_sgzg[i].checked){
				document.getElementById("hdn_sgzg").value=rb_sgzg[i].value;//实际值
					}      
		}
}
function rb_cz_onclick(){    //radiobutton控件取值
	var rb_cz = document.getElementsByName("rb_cz");
	for(var i = 0 ;i<rb_cz.length;i++){
			 if(rb_cz[i].checked){
				document.getElementById("hdn_cz").value=rb_cz[i].value;//实际值
					}      
		}
}

//检查不能为空
function checkNull(){
if(activityId == "UserTask_3" ){
		var tMsg = "";
		var tErrMsg = "不能为空";			
		if(document.getElementById("hdn_sg").value==""){	
			tMsg = tMsg + "请确认预计交货日期是否OK" + tErrMsg + " \r\n";
		}if(txt_sg.value == "" && document.getElementById("hdn_sg").value!="Y"){	
			tMsg = tMsg + "生管确认-说明" + tErrMsg + " \r\n";
		}if(tMsg == ""){
			return true;
		}else{
			alert(tMsg);
			return false;
		}
		return true;
	}
if(activityId == "UserTask_14" ){
		var tMsg = "";
		var tErrMsg = "不能为空";			
		if(document.getElementById("hdn_sgzg").value==""){	
			tMsg = tMsg + "请确认预计交货日期是否OK" + tErrMsg + " \r\n";
		}if(txt_sgzg.value == "" && document.getElementById("hdn_sgzg").value!="Y"){	
			tMsg = tMsg + "生管主管确认-说明" + tErrMsg + " \r\n";
		}if(tMsg == ""){
			return true;
		}else{
			alert(tMsg);
			return false;
		}
		return true;
	}
if(activityId == "UserTask_19" ){
		var tMsg = "";
		var tErrMsg = "不能为空";			
		if(document.getElementById("hdn_cz").value==""){	
			tMsg = tMsg + "请确认预计交货日期是否OK" + tErrMsg + " \r\n";
		}if(txt_cz.value == "" && document.getElementById("hdn_cz").value!="Y"){	
			tMsg = tMsg + "厂长确认-说明" + tErrMsg + " \r\n";
		}if(kdcrq.value == "" && document.getElementById("hdn_cz").value!="Y"){	
			tMsg = tMsg + "厂长确认-可达成日期" + tErrMsg + " \r\n";
		}if(tMsg == ""){
			return true;
		}else{
			alert(tMsg);
			return false;
		}
		return true;
	}
if(activityId == "UserTask_24" ){
		var tMsg = "";
		var tErrMsg = "不能为空";			
		if(document.getElementById("txt_ywzg").value==""){	
			tMsg = tMsg + "业务主管备注" + tErrMsg + " \r\n";
		}if(tMsg == ""){
			return true;
		}else{
			alert(tMsg);
			return false;
		}
		return true;
	}
	else{return true;}
}




//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//