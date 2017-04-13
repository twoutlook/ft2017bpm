alert(" (4)無法開窗");
//常用引入外部JS
document.write('<script type="text/javascript" src="../../CustomJsLib/EFGPShareMethod.js"></script>'); //for 开窗
//数据库链接
var databaseCfgId_EFGP = "EFGPTEST"; //办公用品
var ds2 = "T100TEST"; //查询T100测试环境
var ds3 = "T100PROD"; //查询T100正式环境


var dsBpmTest = "BPMTEST"; //查询BPM测试环境
var dsBpmProd = "BPMPROD"; //查询BPM正式环境
var dsT100Test = "T100TEST"; //查询T100测试环境
var dsT100Prod = "T100PROD"; //查询T100正式环境

var BPMPROD = new DataSource("control002","BPMPROD");//BPM正式
var T100TEST = new DataSource("control002","T100TEST");//T100测试
var T100PROD = new DataSource("control002","T100PROD");//T100正式
var BPMTEST = new DataSource("control002","BPMTEST");//BPM测试

// ddlSql=Array( "", "select 料号, 品名,审核日期 from BASIC001 ORDER BY 料号", "select 主件料号, 品名,审核日期 from BASIC002 ORDER BY 主件料号", "select 工艺料号,品名,工艺编号,说明,审核日期 from BASIC003 ORDER BY 工艺料号", "select 主分群码,说明,审核日期 from BASIC004", "select 供应商编号,供应商全名,资料审核日期 from BASIC005", "select EMPE_ID, EMPE_NAME, DEPT_NAME, JOB, SUP_NAME, LEVEL_NAME FROM V_EMPE_LEVEL", ) ;
// alert(ddlSql[1]);

txt01= "select 料号, 品名,审核日期 from BASIC001 ORDER BY 料号";
txt01x= "料号, 品名,审核日期";
txt01y= "料号, 品名,审核日期";
txt01z= "料号, 品名,审核日期";

txt02=  "select 主件料号, 品名,审核日期 from BASIC002 ORDER BY 主件料号";
txt02x=  "主件料号, 品名,审核日期";
txt02y=  "主件料号, 品名,审核日期";
txt02z=  "主件料号, 品名,审核日期";


txt03= "select 工艺料号,品名,工艺编号,说明,审核日期 from BASIC003 ORDER BY 工艺料号";
txt03x= "工艺料号,品名,工艺编号,说明,审核日期";
txt03y= "工艺料号,品名,工艺编号,说明,审核日期";
txt03z= "工艺料号,品名,工艺编号,说明,审核日期";

txt04=  "select 主分群码,说明,审核日期 from BASIC004";
txt05= "select 供应商编号,供应商全名,资料审核日期 from BASIC005";







   
function formCreate() {
    return true;
}

function formOpen() {
	document.getElementById("BTdc").readOnly = false;//栏位只读
document.getElementById("BTdc").disabled = false; //栏位不可编辑
	
initDebug();
setTimeStart();
setTimeEnd();
initCnt();
	

    return true;
}

function formSave() {
    return true;
}

function formClose() {
    return true;
}

function txt01a_onclick(){
	mark003(dsT100Prod, txt01, txt01x, txt01y, txt01z);
}
function txt01b_onclick(){
	mark003(dsT100Test, txt01, txt01x, txt01y, txt01z);
}

function txt02a_onclick(){
	mark003(dsT100Prod, txt02, txt02x, txt02y, txt02z);
}
function txt02b_onclick(){
	mark003(dsT100Test, txt02, txt02x, txt02y, txt02z);
}
function txt03a_onclick(){
	mark003(dsT100Prod, txt03, txt03x, txt03y, txt03z);
}
function txt03b_onclick(){
	mark003(dsT100Test, txt03, txt03x, txt03y, txt03z);
}

function txt04a_onclick(){
	mark003(dsT100Prod, txt04, txt04x, txt04y, txt04z);
}
function txt04b_onclick(){
	mark003(dsT100Test, txt04, txt04x, txt04y, txt04z);
}


//2017-04-13, by Mark
function mark001(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId){
	alert("mark001")
	pluralityOpenWin(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);


}

function mark001Prod( SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId){
	alert("mark001Prod")
	pluralityOpenWin("PluralityOpenWin", dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);


}

function mark001ProdV2( SQLClaused, SQLLabel, QBEField, QBELabel){
	alert("mark001ProdV2 ...debug")
	var ReturnId = new Array("HDtzry");
	pluralityOpenWin("PluralityOpenWin", dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}


function mark002(ds, SQLClaused, SQLLabel, QBEField, QBELabel){
	alert("mark002 ...debug")
	var ReturnId = new Array("HDtzry");
	// pluralityOpenWin("PluralityOpenWin", dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
	pluralityOpenWin("PluralityOpenWin", ds, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}

function mark003(ds, strSQLClaused, strSQLLabel, strQBEField, strQBELabel){
	// alert("mark003 ...debug")

// SQLClaused
// SQLLabel
// QBEField,
// QBELabel
	var SQLClaused = new Array(strSQLClaused);
	var SQLLabel = strSQLLabel.split( "," );
    var QBEField = strQBEField.split( "," );
    var QBELabel = strQBELabel.split( "," );

	var ReturnId = new Array("HDtzry");
	// pluralityOpenWin("PluralityOpenWin", dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
	pluralityOpenWin("PluralityOpenWin", ds, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}



//2017-04-12, 祁玉镠
function txt3_onclick(){
	//alert("//2017-04-12, 祁玉镠 xxxxtxt3_onclick");
	var FileName = "PluralityOpenWin";

	//select a.gzxa001 USER_ID,a.gzxa003 EMPE_ID,b.ooag011 EMPE_NAME,TO_CHAR(a.gzxacrtdt, 'YYYY-MM-DD') CREATE_DATE   from gzxa_t aleft join ooag_t b on b.ooag001=a.gzxa003 where a.gzxaent=11 and a.gzxastus='Y'

	var tSql = "select a.gzxa001 USER_ID,a.gzxa003 EMPE_ID,b.ooag011 EMPE_NAME,TO_CHAR(a.gzxacrtdt, 'YYYY-MM-DD') CREATE_DATE   from gzxa_t a left join ooag_t b on b.ooag001=a.gzxa003 where a.gzxaent=11 and a.gzxastus='Y'"; 
	var SQLClaused = new Array(tSql);
	// var SQLLabel = new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"); //客制开窗的Grid Label
	var SQLLabel = new Array( "用户ID","员工工号","员工姓名","生成日期"); //客制开窗的Grid Label


    var QBEField = new Array( "gzxa001","gzxa003","ooag011"); //模糊查询,須和DB Table栏位名称相同
    // var QBELabel = new Array( "EMPE_ID","EMPE_NAME","DEPT_NAME","JOB","SUP_NAME","LEVEL_NAME","LEAVE_DATE"); //模糊查询的Label
    var QBELabel = new Array( "用户ID","员工工号","员工姓名");
    var ReturnId = new Array("HDtzry");
	
	pluralityOpenWin(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}
function txt4_onclick(){
	// alert("//2017-04-12, 张韬 xxxxtxt4_onclick");
	var FileName = "PluralityOpenWin";

	//select a.gzxa001 USER_ID,a.gzxa003 EMPE_ID,b.ooag011 EMPE_NAME,TO_CHAR(a.gzxacrtdt, 'YYYY-MM-DD') CREATE_DATE   from gzxa_t aleft join ooag_t b on b.ooag001=a.gzxa003 where a.gzxaent=11 and a.gzxastus='Y'

	var tSql = "select a.gzxa001 USER_ID,a.gzxa003 EMPE_ID,b.ooag011 EMPE_NAME,TO_CHAR(a.gzxacrtdt, 'YYYY-MM-DD') CREATE_DATE   from gzxa_t a left join ooag_t b on b.ooag001=a.gzxa003 where a.gzxaent=11 and a.gzxastus='Y'"; 
	var SQLClaused = new Array(tSql);
	// var SQLLabel = new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"); //客制开窗的Grid Label
	var SQLLabel = new Array( "用户ID","员工工号","员工姓名","生成日期"); //客制开窗的Grid Label


    var QBEField = new Array( "gzxa001","gzxa003","ooag011"); //模糊查询,須和DB Table栏位名称相同
    // var QBELabel = new Array( "EMPE_ID","EMPE_NAME","DEPT_NAME","JOB","SUP_NAME","LEVEL_NAME","LEAVE_DATE"); //模糊查询的Label
    var QBELabel = new Array( "用户ID","员工工号","员工姓名");
    var ReturnId = new Array("HDtzry");
	
	pluralityOpenWin(FileName, dsT100Test, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}



// function txt01b_onclick(){
// 	//alert("1");
// 	var FileName = "PluralityOpenWin";
// 	var tSql = " select EMPE_ID,EMPE_NAME,DEPT_NAME,JBO_NAME,LEVEL_NAME,SUP_NAME  from V_EMPE_LEVEL "; 
// 	var SQLClaused = new Array(tSql);
// 	var SQLLabel = new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"); //客制开窗的Grid Label
//     var QBEField = new Array("EMPE_ID","EMPE_NAME","DEPT_NAME","JBO_NAME","LEVEL_NAME","SUP_NAME" ); //模糊查询,須和DB Table栏位名称相同
//     var QBELabel = new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"); //模糊查询的Label
// 	var ReturnId = new Array("HDtzry");
	
// 	pluralityOpenWin(FileName, dsT100Test, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
// }


function txtSecA_onclick(){
	var FileName = "PluralityOpenWin";
txtSec_sql="select  EMPE_ID,EMPE_NAME,DEPT_NAME,JBO_NAME,LEVEL_NAME,SUP_NAME from V_EMPE_LEVEL2";
txtSec_field=new Array("EMPE_ID","EMPE_NAME","DEPT_NAME","JBO_NAME","LEVEL_NAME","SUP_NAME"  ); //模糊查询,須和DB Table栏位名称相同
txtSec_label=new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"  ); //模糊查询,須和DB Table栏位名称相同

	var SQLClaused = new Array(txtSec_sql);
	var SQLLabel = txtSec_label;
    var QBEField = txtSec_field;
    var QBELabel = txtSec_label;
	var ReturnId = new Array("HDtzry");
	pluralityOpenWin(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}

function txtSecB_onclick(){
	var FileName = "PluralityOpenWin";
txtSec_sql="select  EMPE_ID,EMPE_NAME,DEPT_NAME,JBO_NAME,LEVEL_NAME,SUP_NAME from V_EMPE_LEVEL2";
txtSec_field=new Array("EMPE_ID","EMPE_NAME","DEPT_NAME","JBO_NAME","LEVEL_NAME","SUP_NAME"  ); //模糊查询,須和DB Table栏位名称相同
txtSec_label=new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"  ); //模糊查询,須和DB Table栏位名称相同

	var SQLClaused = new Array(txtSec_sql);
	var SQLLabel = txtSec_label;
    var QBEField = txtSec_field;
    var QBELabel = txtSec_label;
	var ReturnId = new Array("HDtzry");
	
	pluralityOpenWin(FileName, dsT100Test, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}

function initTxt(sql,txta, txtb){
		// sql="SELECT COUNT(*) CNT FROM BASIC005";
	rst= T100PROD.query(sql);  //執行SQL查詢  
	document.getElementById(txta).value = rst[0][0];
	// alert ("PROD ");
	// alert (sql);

	rst= T100TEST.query(sql);  //執行SQL查詢  
	document.getElementById(txtb).value = rst[0][0];
	// alert ("TEST ")
// alert (sql);
	
}


// 2017-04-13 15:13, by Mark
function setTimeStart(){
	sql="SELECT to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 FROM BASIC005";
	rst= T100PROD.query(sql);  //執行SQL查詢  
	document.getElementById("txtQryStart").value = rst[0][0];
	// alert ("PROD ");
	// alert (sql);

	// rst= T100TEST.query(sql);  //執行SQL查詢  
	// document.getElementById(txtb).value = rst[0][0];
	// alert ("TEST ")
// alert (sql);
	
}

function setTimeEnd(){
	sql="SELECT to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 FROM BASIC005";
	rst= T100PROD.query(sql);  //執行SQL查詢  
	document.getElementById("txtQryEnd").value = rst[0][0];
}

// 员工用戶帳號

function initDebug(){

	document.getElementById("txt01a").value = "01a";
	document.getElementById("txt01b").value =  "01b";
	document.getElementById("txt02a").value = "02a";
	document.getElementById("txt02b").value =  "02b";
	


	document.getElementById("txt03a").value = "03a";
	document.getElementById("txt03b").value =  "03b";
	document.getElementById("txt04a").value = "04a";
	document.getElementById("txt04b").value =  "04b";
	document.getElementById("txt05a").value = "05a";
	document.getElementById("txt05b").value =  "05b";
	document.getElementById("txt06a").value = "06a";
	document.getElementById("txt06b").value =  "06b";
	document.getElementById("txt07a").value = "07a";
	document.getElementById("txt07b").value =  "07b";
	document.getElementById("txt08a").value = "08a";
	document.getElementById("txt08b").value =  "08b";
	document.getElementById("txt09a").value = "09a";
	document.getElementById("txt09b").value =  "09b";

	document.getElementById("txt101a").value = "101a";
	document.getElementById("txt101b").value =  "101b";
	document.getElementById("txt102a").value = "102a";
	document.getElementById("txt102b").value =  "102b";
	document.getElementById("txt103a").value = "103a";
	document.getElementById("txt103b").value =  "103b";


	document.getElementById("txt11a").value = "11a";
	document.getElementById("txt11b").value =  "11b";
	document.getElementById("txt12a").value = "12a";
	document.getElementById("txt12b").value =  "12b";
	document.getElementById("txt13a").value = "13a";
	document.getElementById("txt13b").value =  "13b";
	


}

// var txt1Cnt = "select 'txt01' 查询主题,COUNT(*) 笔数,to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 from BASIC001";
	

function initCnt(){

	initTxt("SELECT COUNT(*) CNT FROM BASIC001", "txt01a", "txt01b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC002", "txt02a", "txt02b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC003", "txt03a", "txt03b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC004", "txt04a", "txt04b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC005", "txt05a", "txt05b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC006", "txt06a", "txt06b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC007", "txt07a", "txt07b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC008", "txt08a", "txt08b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC009", "txt09a", "txt09b");

	// BE CAREFUL NAMING
	initTxt("SELECT COUNT(*) CNT FROM BASIC010A", "txt101a", "txt101b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC010B", "txt102a", "txt102b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC010C", "txt103a", "txt103b");
	
	initTxt("SELECT COUNT(*) CNT FROM BASIC011", "txt11a", "txt11b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC012", "txt12a", "txt12b");
	initTxt("SELECT COUNT(*) CNT FROM BASIC013", "txt13a", "txt13b");

	
	
}

function checkPointOnClose(pReturnId){

	if(pReturnId == "HDtzry"){
		 
		 Grid1Obj.reload(eval(document.getElementById("HDtzry").value));
	}

}

//汇出Excel
function BTdc_onclick(){

	var gridData=Grid1Obj.getData();
	if(gridData.length>0){
	AutomateExcelall();
	}else{
	alert("详细信息没有可导出的数据！");
	return true;
	}
}
	
function   AutomateExcelall()   
  {     

     var   i,j;   
     var   oXL ;//  =   new   ActiveXObject("Excel.Application");   
	 
try {
  oXL = new ActiveXObject('Excel.Application');
 }catch (e) {
  alert("无法启动Excel!\n\n如果您确信您的电脑中已经安装了Excel，"+"那么请调整IE的安全级别。\n\n具体操作：\n\n"+"工具 → Internet选项 → 安全 → 自定义级别 → 对没有标记为安全的ActiveX进行初始化和脚本运行 → 启用");
  return false;
 }
     oXL.Visible   =   true;  
     var   oWB   =   oXL.Workbooks.Add();   
     var   oSheet   =   oWB.ActiveSheet;   
        // 添加Excel的列头
oSheet.Cells(1,1).Value   =   "员工工号"; 		
oSheet.Cells(1,2).Value   =   "员工姓名"; 
oSheet.Cells(1,3).Value   =   "部门名称"; 
oSheet.Cells(1,4).Value   =   "职称"; 
oSheet.Cells(1,5).Value   =   "核决层级";   
oSheet.Cells(1,6).Value   =   "直属主管名";  


 

     var tResultArray = Grid1Obj.getData();
   
	 for(var i=0;i<tResultArray.length; i++){
        oSheet.Cells(i+2,1).Value   = 	tResultArray[i][0];
		    oSheet.Cells(i+2,2).Value   = 	tResultArray[i][1];
				oSheet.Cells(i+2,3).Value   = 	tResultArray[i][2];
				oSheet.Cells(i+2,4).Value   = 	tResultArray[i][3];
				oSheet.Cells(i+2,5).Value   = 	tResultArray[i][4];
				oSheet.Cells(i+2,6).Value   = 	tResultArray[i][5];

		} 
              oXL.Visible   =   true;   
              oXL.UserControl   =   true;   
  } 

//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//