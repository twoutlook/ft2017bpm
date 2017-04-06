//常用引入外部JS
document.write('<script type="text/javascript" src="../../CustomJsLib/EFGPShareMethod.js"></script>');//for 开窗

//数据库链接
var databaseCfgId_EFGP = "EFGPTEST";   //系統管理員資料來源(DataAccessDefinition)代號

//全局变量定义
var tdrq = document.getElementById("tdrq");//填单日期
var txt_tdrid = document.getElementById("txt_tdrid");//填单人代号
var txt_tdrname = document.getElementById("txt_tdrname");//填单人名称
var txt_tdbmid = document.getElementById("txt_tdbmid");//填单部门代号
var txt_tdbmname = document.getElementById("txt_tdbmname");//填单部门名称

function formCreate(){
  DefalInfo();
return true;
}
function formOpen(){
	
  var tGrid1 = document.getElementById("Grid1").value; //取出儲存在隱藏欄位中的Grid資料   
  if(typeof(Grid1Obj) != "undefined"){  //判斷grid物件是否存在表單中   
  
    if(tGrid1.length >1 ){  //判斷Grid是否有資料   
      Grid1Obj.reload(eval(tGrid1));  //若Grid有資料則將存於隱藏中的值載入Grid中   
    }   
  }   
return true;
}
function formSave(){
  		if(typeof(Grid1Obj) != "undefined")
  		{  //判斷grid物件是否存在表單中   
    		document.getElementById("Grid1").value = Grid1Obj.toArrayString();  //將Grid裡的資料儲存至隱藏欄位中   
     	}
     	//判斷Grid是否有資料 
     	if(document.getElementById("Grid1").value=="[]")
		{
			alert("单身不能为空，请按新增按钮将单头资料插入单身中！");
			return false ;
   		}
   		return true;
}
function formClose(){
return true;
}

//表单预设值
function DefalInfo(){
    tdrq.value = systemDateTime;//申请日期
	txt_tdrid.value = userId;//申请人ID
	txt_tdrname.value = userName;//申请人姓名
	txt_tdbmid.value = mainOrgUnitIds;//申请部门ID
	txt_tdbmname.value = mainOrgUnitNames;//申请部门
}

//请假人开窗
function btn_qjd_onclick(){

  	var FileName = "SingleOpenWin";		
	var sql = " select U.id,U.userName,T.functiondefinitionname "+
	                 " from Users U left join Functions F on F.occupantOID = U.OID "+
	                 " left join OrganizationUnit OU on OU.OID = F.organizationUnitOID "+
	                 " inner join Organization O on O.OID = OU.organizationOID left join FunctionDefinition T on T.OID=F.definitionOID"+
	                 " where U.leaveDate is null and F.isMain = 1 and OU.organizationUnitType = 0 "; 
	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("申请人代号","申请人姓名","职务");//客制开窗的Grid Label
	var QBEField = new Array("U.id","U.userName","T.functiondefinitionname");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("申请人代号","申请人姓名","职务");//模糊查询的Label
     var ReturnId = new Array("txt_qjrid","txt_qjrname","txt_zw");//表单上的栏位代号
	singleOpenWin(FileName, databaseCfgId_EFGP, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}

//Grid新增
function btn_add_onclick(){  
  Grid1Obj.addRow();  //將Binding欄位的資料填入Grid中   
  Grid1Obj.clearBinding();  //新增後清除Binding欄位資料   
  document.getElementById("Grid1").value = Grid1Obj.toArrayString();  //將新的資料存入Grid隱藏欄位中   
}   
  //Grid修改
function btn_edit_onclick(){   
  Grid1Obj.editRow();  //將修改後的欄位的資料填入Grid中   
  Grid1Obj.clearBinding();  //清除Binding欄位資料   
  document.getElementById("Grid1").value = Grid1Obj.toArrayString();  //將新的資料存入Grid隱藏欄位中   
}   
  //Grid删除
function btn_delete_onclick(){   
  Grid1Obj.deleteRow();  //將Grid某筆資料刪除   
  Grid1Obj.clearBinding();  //清除Binding欄位資料   
  document.getElementById("Grid1").value = Grid1Obj.toArrayString();  //將新的資料存入Grid隱藏欄位中   
}  

//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//