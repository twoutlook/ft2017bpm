//常用引入外部JS
document.write('<script type="text/javascript" src="../../CustomJsLib/EFGPShareMethod.js"></script>');//for 开窗

//数据库链接
var databaseCfgId_EFGP = "EFGPTEST";   //系統管理員資料來源(DataAccessDefinition)代號

//全局变量定义
var txt_systemDateTime = document.getElementById("txt_systemDateTime");//日期

txt_systemDateTime.ReadOnly="true";

//单选开窗  只要数据库有东西，任何人都可以调用
function btn_btn_onclick(){
     alert("婷婷1 btn_btn_onclick");
  	var FileName = "SingleOpenWin";		
	var sql = " select BGYPNAME,BGYPSPEC,BGYPUOM from Z_BGYP  "; 
	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("品名","规格","单位");//客制开窗的Grid Label
	var QBEField = new Array("BGYPNAME","BGYPSPEC","BGYPUOM");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("品名","规格","单位");//模糊查询的Label
     var ReturnId = new Array("txt_pm","txt_gg","txt_dw");//表单上的栏位代号
	singleOpenWin(FileName, databaseCfgId_EFGP, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}


function formCreate(){
//alert("婷婷111  formCreate")
txt_systemDateTime.value=systemDateTime;
return true;
}
function formOpen(){
//alert("婷婷222  formOpen")
return true;
}
function formSave(){
//alert("婷婷333  formSave")
return true;
}
function formClose(){
//alert("婷婷444  formClose")
return true;
}
//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//