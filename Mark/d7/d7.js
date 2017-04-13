// alert(" to set debug ..");
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

var BPMPROD = new DataSource("d7","BPMPROD");//BPM正式
var T100TEST = new DataSource("d7","T100TEST");//T100测试
var T100PROD = new DataSource("d7","T100PROD");//T100正式
var BPMTEST = new DataSource("d7","BPMTEST");//BPM测试

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
txt04x=  "主分群码,说明,审核日期";
txt04y=  "主分群码,说明,审核日期";
txt04z=  "主分群码,说明,审核日期";


txt05= "select 供应商编号,供应商全名,资料审核日期 from BASIC005";
txt05x= "供应商编号,供应商全名,资料审核日期";
txt05y= "供应商编号,供应商全名,资料审核日期";
txt05z= "供应商编号,供应商全名,资料审核日期";

txt06= "SELECT 客户编号,客户全名,客户简称,客户全名二 FROM BASIC006";
txt06x= "客户编号,客户全名,客户简称,客户全名二";
txt06y= "客户编号,客户全名,客户简称,客户全名二";
txt06z= "客户编号,客户全名,客户简称,客户全名二";

txt07= "SELECT 状态,客户编号,客户简称,公司料件编号,公司料件品名,公司料件规格,产品特征,产品特征说明,客户料件编号,客户料件品名,客户料件规格     FROM BASIC007";
txt07x= "状态,客户编号,客户简称,公司料件编号,公司料件品名,公司料件规格,产品特征,产品特征说明,客户料件编号,客户料件品名,客户料件规格";
txt07y= "状态,客户编号,客户简称,公司料件编号,公司料件品名,公司料件规格,产品特征,产品特征说明,客户料件编号,客户料件品名,客户料件规格";
txt07z= "状态,客户编号,客户简称,公司料件编号,公司料件品名,公司料件规格,产品特征,产品特征说明,客户料件编号,客户料件品名,客户料件规格";


// SELECT 包装方式,包装说明,包装容器,品名,规格  FROM BASIC008
// SELECT 检验项目,说明,状态  FROM BASIC009
// SELECT 设备编号,名称,简称 FROM BASIC010A
// SELECT 模具编号,名称,简称 FROM BASIC010B
// SELECT 刀具编号,名称,简称 FROM BASIC010C
//  SELECT MRBE002,IMAAL003,IMAAL004 FROM BASIC011
   // SELECT 保养项目,说明 FROM BASIC012
   // SELECT 仪器编号,名称,简称 FROM BASIC013
txt08= "SELECT 包装方式,包装说明,包装容器,品名,规格  FROM BASIC008";
txt08x= "包装方式,包装说明,包装容器,品名,规格";
txt08y= "包装方式,包装说明,包装容器,品名,规格";
txt08z= "包装方式,包装说明,包装容器,品名,规格";

txt09= "SELECT 检验项目,说明,状态  FROM BASIC009";
txt09x= "检验项目,说明,状态";
txt09y= "检验项目,说明,状态";
txt09z= "检验项目,说明,状态";


txt101= "SELECT 设备编号,名称,简称 FROM BASIC010A";
txt101x= "设备编号,名称,简称";
txt101y= "设备编号,名称,简称";
txt101z= "设备编号,名称,简称";


txt102= "SELECT 模具编号,名称,简称 FROM BASIC010B";
txt102x= "模具编号,名称,简称";
txt102y= "模具编号,名称,简称";
txt102z= "模具编号,名称,简称";

txt103= "SELECT 刀具编号,名称,简称 FROM BASIC010C";
txt103x= "刀具编号,名称,简称";
txt103y= "刀具编号,名称,简称";
txt103z= "刀具编号,名称,简称";

txt11= "SELECT MRBE002,IMAAL003,IMAAL004 FROM BASIC011";
txt11x= "MRBE002,IMAAL003,IMAAL004";
txt11y= "MRBE002,IMAAL003,IMAAL004";
txt11z= "MRBE002,IMAAL003,IMAAL004";

txt12= "SELECT 保养项目,说明 FROM BASIC012";
txt12x= "保养项目,说明";
txt12y= "保养项目,说明";
txt12z= "保养项目,说明";


txt13= "SELECT 仪器编号,名称,简称 FROM BASIC013";
txt13x= "仪器编号,名称,简称";
txt13y= "仪器编号,名称,简称";
txt13z= "仪器编号,名称,简称";

function setTimeStart(){
	sql="SELECT to_char(sysdate,'mm/dd hh24:mm:ss') 查询时间 FROM BASIC005";
	rst= T100PROD.query(sql);  //執行SQL查詢  
	rst2= T100TEST.query(sql);  //執行SQL查詢  
	document.getElementById("txtQryStart").value = rst[0][0];
document.getElementById("txtQryStart2").value = rst2[0][0];

}

function setTimeEnd(){
	sql="SELECT to_char(sysdate,'mm/dd hh24:mm:ss') 查询时间 FROM BASIC005";
	rst= T100PROD.query(sql);  //執行SQL查詢  
	rst2= T100TEST.query(sql);  //執行SQL查詢  
		document.getElementById("txtQryEnd").value = rst[0][0];
		document.getElementById("txtQryEnd2").value = rst2[0][0];
}

function formCreate() {
    return true;
}

function formOpen() {
	document.getElementById("BTdc").readOnly = false;//栏位只读
document.getElementById("BTdc").disabled = false; //栏位不可编辑
	

initDebug();
setTimeStart();
initCnt();	
setTimeEnd();
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

// txt3
function txt03a_onclick(){
	mark003(dsT100Prod, txt03, txt03x, txt03y, txt03z);
}
function txt03b_onclick(){
	mark003(dsT100Test, txt03, txt03x, txt03y, txt03z);
}

// txt4
function txt04a_onclick(){
	mark003(dsT100Prod, txt04, txt04x, txt04y, txt04z);
}
function txt04b_onclick(){
	mark003(dsT100Test, txt04, txt04x, txt04y, txt04z);
}

// txt5
function txt05a_onclick(){
	mark003(dsT100Prod, txt05, txt05x, txt05y, txt05z);
}
function txt05b_onclick(){
	mark003(dsT100Test, txt05, txt05x, txt05y, txt05z);
}
// txt6
function txt06a_onclick(){
	mark003(dsT100Prod, txt06, txt06x, txt06y, txt06z);
}
function txt06b_onclick(){
	mark003(dsT100Test, txt06, txt06x, txt06y, txt06z);
}
// 7
function txt07a_onclick(){
	mark003(dsT100Prod, txt07, txt07x, txt07y, txt07z);
}
function txt07b_onclick(){
	mark003(dsT100Test, txt07, txt07x, txt07y, txt07z);
}

// 8
function txt08a_onclick(){
	mark003(dsT100Prod, txt08, txt08x, txt08y, txt08z);
}
function txt08b_onclick(){
	mark003(dsT100Test, txt08, txt08x, txt08y, txt08z);
}

// 09
function txt09a_onclick(){
	mark003(dsT100Prod, txt09, txt09x, txt09y, txt09z);
}
function txt09b_onclick(){
	mark003(dsT100Test, txt09, txt09x, txt09y, txt09z);
}



// 101
function txt101a_onclick(){
	mark003(dsT100Prod, txt101, txt101x, txt101y, txt101z);
}
function txt101b_onclick(){
	mark003(dsT100Test, txt101, txt101x, txt101y, txt101z);
}
// 102
function txt102a_onclick(){
	mark003(dsT100Prod, txt102, txt102x, txt102y, txt102z);
}
function txt102b_onclick(){
	mark003(dsT100Test, txt102, txt102x, txt102y, txt102z);
}
// 103
function txt103a_onclick(){
	mark003(dsT100Prod, txt103, txt103x, txt103y, txt103z);
}
function txt103b_onclick(){
	mark003(dsT100Test, txt103, txt103x, txt103y, txt103z);
}


// 11
function txt11a_onclick(){
	mark003(dsT100Prod, txt11, txt11x, txt11y, txt11z);
}
function txt11b_onclick(){
	mark003(dsT100Test, txt11, txt11x, txt11y, txt11z);
}

// 12
function txt12a_onclick(){
	mark003(dsT100Prod, txt12, txt12x, txt12y, txt12z);
}
function txt12b_onclick(){
	mark003(dsT100Test, txt12, txt12x, txt12y, txt12z);
}
// 13
function txt13a_onclick(){
	mark003(dsT100Prod, txt13, txt13x, txt13y, txt13z);
}
function txt13b_onclick(){
	mark003(dsT100Test, txt13, txt13x, txt13y, txt13z);
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
	rst= T100TEST.query(sql);  //執行SQL查詢  
	document.getElementById(txtb).value = rst[0][0];

}

// 员工用戶帳號
function initDebug(){
	//2017-04-13 , name:熊芳平 
	document.getElementById("txt01a").value = "01a";
	document.getElementById("txt01b").value =  "01b";

	document.getElementById("txt101a").value = "10a正式";
	document.getElementById("txt101b").value =  "10a测试";
	document.getElementById("txt102a").value = "10b正式";
	document.getElementById("txt102b").value =  "10b测试";
	document.getElementById("txt103a").value = "10c正式";
	document.getElementById("txt103b").value =  "10c测试";
	
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
	document.getElementById("txt10a").value = "10a";
	document.getElementById("txt10b").value =  "10b";
	document.getElementById("txt11a").value = "11a";
	document.getElementById("txt11b").value =  "11b";
	document.getElementById("txt12a").value = "12a";
	document.getElementById("txt12b").value =  "12b";
	document.getElementById("txt13a").value = "13a";
	document.getElementById("txt13b").value =  "13b";


}

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