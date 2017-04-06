//常用引入外部JS
document.write('<script type="text/javascript" src="../../CustomJsLib/EFGPShareMethod.js"></script>');//for 开窗

//数据库链接
var databaseCfgId_EFGP = "EFGPTEST";   //系統管理員資料來源(DataAccessDefinition)代號

//全局变量定义
var txt_sqrq = document.getElementById("txt_sqrq");//申请日期
var txt_sqrid = document.getElementById("txt_sqrid");//申请人代号
var txt_sqr = document.getElementById("txt_sqr");//申请人名称
var txt_sqbmid = document.getElementById("txt_sqbmid");//申请部门代号
var txt_sqbm = document.getElementById("txt_sqbm");//申请部门名称
var d_sqlx = document.getElementById("d_sqlx");//申请类型
var txt_sqzs = document.getElementById("txt_sqzs");//申请总数
var txt_pm =  document.getElementById("txt_pm");//品名
var txt_gg =  document.getElementById("txt_gg");//规格
var txt_dw =  document.getElementById("txt_dw");//单位
var txt_sqsl =  document.getElementById("txt_sqsl");//申请数量
txt_sqrid.ReadOnly="true"
txt_sqr.ReadOnly="true"
txt_sqbmid.ReadOnly="true"
txt_sqbm.ReadOnly="true"
txt_sqrq.ReadOnly="true"

function formCreate(){
  DefalInfo();
return true;
}

//表单预设值
function DefalInfo(){
    txt_sqrq.value = systemDateTime;//申请日期
	txt_sqrid.value = userId;//申请人ID
	txt_sqr.value = userName;//申请人姓名
	txt_sqbmid.value = mainOrgUnitIds;//申请部门ID
	txt_sqbm.value = mainOrgUnitNames;//申请部门
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
if(checkNull()){
	if(typeof(Grid1Obj) != "undefined")
  		{  //判斷grid物件是否存在表單中   
    		document.getElementById("Grid1").value = Grid1Obj.toArrayString();  //將Grid裡的資料儲存至隱藏欄位中   
     	}
     	//判斷Grid是否有資料 
     	if(document.getElementById("Grid1").value=="[]")
		{
			alert("（婷婷3）单身不能为空，请按新增按钮将单头资料插入单身中！");
			return false ;
   		}
   		return true;
}
return false;
}

function formClose(){
return true;
}

function checkNull(){
if(activityId == "UserTask_3" ){
		var tMsg = "";
		var tErrMsg = "不能为空";			
		if(d_sqlx.value == "" ){	
			tMsg = tMsg + "（婷婷）申请类型" + tErrMsg + " \r\n";
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

//Grid新增
function btn_add_onclick(){  
if(gridcheckNull()){  
  Grid1Obj.addRow();  //將Binding欄位的資料填入Grid中   
  Grid1Obj.clearBinding();  //新增後清除Binding欄位資料   
  document.getElementById("Grid1").value = Grid1Obj.toArrayString();  //將新的資料存入Grid隱藏欄位中   
  GridTotal();
  }
}   
  //Grid修改
function btn_edit_onclick(){  
if(gridcheckNull()){  
  Grid1Obj.editRow();  //將修改後的欄位的資料填入Grid中   
  Grid1Obj.clearBinding();  //清除Binding欄位資料   
  document.getElementById("Grid1").value = Grid1Obj.toArrayString();  //將新的資料存入Grid隱藏欄位中   
  GridTotal();
  }
}   
  //Grid删除
function btn_del_onclick(){   
  Grid1Obj.deleteRow();  //將Grid某筆資料刪除   
  Grid1Obj.clearBinding();  //清除Binding欄位資料   
  document.getElementById("Grid1").value = Grid1Obj.toArrayString();  //將新的資料存入Grid隱藏欄位中   
  GridTotal();
} 

//Grid中的申请数量合计
function GridTotal(){
	var gridData = Grid1Obj.getData();
	var tSellingPriceTemp = 0;
	
	if (gridData.length > 0){				
		for (i = 0; i < gridData.length; i++){
			if(gridData[i][4] != ""){
				tSellingPriceTemp = tSellingPriceTemp + eval(gridData[i][4]);
			}			
		}				
		txt_sqzs.value = eval(tSellingPriceTemp );
	}
	else{
		txt_sqzs.value = '0';
	}
}
//单身检查必填项
function gridcheckNull(){
		var tMsg = "";
		var tErrMsg = "不能为空";	
		if(txt_pm.value == "" ){	
			tMsg = tMsg + "品名" + tErrMsg + " \r\n";
		}if(txt_gg.value == "" ){	
			tMsg = tMsg + "规格" + tErrMsg + " \r\n";
		}if(txt_dw.value == "" ){	
			tMsg = tMsg + "单位" + tErrMsg + " \r\n";
		}if(txt_sqsl.value == "" ){	
			tMsg = tMsg + "（婷婷2）申请数量" + tErrMsg + " \r\n";
		}
		//--申请数量必须是数字
		var str = document.getElementById("txt_sqsl").value ;
		if(!isNaN(str) || str == ".") {
			
		}else{
		tMsg = tMsg + "申请数量必须是数字"  + " \r\n";
		}
		//
		if(tMsg == ""){
			return true;
		}else{
			alert(tMsg);
			return false;
		}
		return true;
}

//申请类型下拉框
function d_sqlx_onclick(){
	var obj=document.getElementById("d_sqlx") ;//获取select控件，Select1为控件Select的id
	var index=obj.selectedIndex;//选中的索引；
	var text=obj.options[index].text;//选中的文本
	var Value=obj.options[index].value;//选中值
	document.getElementById("hdn_sqlx").value = text;//当选择正常领用时，hdn_sqlx显示为正常领用
	document.getElementById("hdn_sqlxid").value = Value;//当选择正常领用时，hdn_sqlxid显示为1
}

//申请数量必须是数字
function txt_sqsl_onblur(){
	var str = document.getElementById("txt_sqsl").value ;
	if(!isNaN(str) || str == ".") {
		return true;
	}else{
		alert("申请数量必须是数字");
		document.getElementById("txt_sqsl").focus();
	}
}

//单选开窗  只要数据库有东西，任何人都可以调用
function btn_bgyp_onclick(){
    alert("婷婷1btn_bgyp_onclick");
  	var FileName = "SingleOpenWin";		
	var sql = " select BGYPNAME,BGYPSPEC,BGYPUOM from Z_BGYP  "; 
	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("品名","规格","单位");//客制开窗的Grid Label
	var QBEField = new Array("BGYPNAME","BGYPSPEC","BGYPUOM");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("品名","规格","单位");//模糊查询的Label
     var ReturnId = new Array("txt_pm","txt_gg","txt_dw");//表单上的栏位代号
	singleOpenWin(FileName, databaseCfgId_EFGP, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}

//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//