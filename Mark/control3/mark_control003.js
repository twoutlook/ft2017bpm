
//常用引入外部JS
document.write('<script type="text/javascript" src="../../CustomJsLib/EFGPShareMethod.js"></script>'); //for 开窗
//数据库链接
var databaseCfgId_EFGP = "EFGPTEST"; //办公用品
var ds2 = "T100TEST"; //查询T100测试环境
var ds3 = "T100PROD"; //查询T100正式环境


var dsBpmTest = "BPMTEST"; //查询T100测试环境
var dsBpmProd = "BPMPROD"; //查询T100正式环境
var dsT100Test = "T100TEST"; //查询T100测试环境
var dsT100Prod = "T100PROD"; //查询T100正式环境

var BPMPROD = new DataSource("control002","BPMPROD");//BPM正式
var T100TEST = new DataSource("control002","T100TEST");//T100测试
var T100PROD = new DataSource("control002","T100PROD");//T100正式
var BPMTEST = new DataSource("control002","BPMTEST");//BPM测试
   
function formCreate() {
    return true;
}

function formOpen() {
test();
	
    return true;
}

function formSave() {
    return true;
}

function formClose() {
    return true;
}

function txt1_onclick(){
	//alert("1");
	var FileName = "PluralityOpenWin";
	var tSql = " select EMPE_ID,EMPE_NAME,DEPT_NAME,JBO_NAME,LEVEL_NAME,SUP_NAME  from V_EMPE_LEVEL "; 
	var SQLClaused = new Array(tSql);
	var SQLLabel = new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"); //客制开窗的Grid Label
    var QBEField = new Array("EMPE_ID","EMPE_NAME","DEPT_NAME","JBO_NAME","LEVEL_NAME","SUP_NAME" ); //模糊查询,須和DB Table栏位名称相同
    var QBELabel = new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"); //模糊查询的Label
	var ReturnId = new Array("HDtzry");
	
	pluralityOpenWin(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}


function txt2_onclick(){
	//alert("1");
	var FileName = "PluralityOpenWin";
	var tSql = " select EMPE_ID,EMPE_NAME,DEPT_NAME,JBO_NAME,LEVEL_NAME,SUP_NAME  from V_EMPE_LEVEL "; 
	var SQLClaused = new Array(tSql);
	var SQLLabel = new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"); //客制开窗的Grid Label
    var QBEField = new Array("EMPE_ID","EMPE_NAME","DEPT_NAME","JBO_NAME","LEVEL_NAME","SUP_NAME" ); //模糊查询,須和DB Table栏位名称相同
    var QBELabel = new Array("员工工号","员工姓名","部门名称","职称","核决层级","直属主管名"); //模糊查询的Label
	var ReturnId = new Array("HDtzry");
	
	pluralityOpenWin(FileName, dsT100Test, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 600);
}






function test(){
	var tSql = "select 'T100员工[正式]' 查询主题,COUNT(*) 笔数,to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 from V_EMPE_LEVEL ";
	var tResult= T100PROD.query(tSql);  //執行SQL查詢  
	document.getElementById("txt1").value = tResult[0][1];
	document.getElementById("txt7").value = tResult[0][2];
	
	var tSql1 = "select 'T100员工[测试]' 查询主题,COUNT(*) 笔数,to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 from V_EMPE_LEVEL  ";
	var tResult1= T100TEST.query(tSql1);  //執行SQL查詢  
	document.getElementById("txt2").value = tResult1[0][1];
	document.getElementById("txt7").value = tResult1[0][2];
	
	var tSql2 = "select 'T100用户[正试]' 查询主题,COUNT(*) 笔数,to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 from gzxa_t where gzxaent=11 and gzxastus='Y'";
	var tResult2= T100PROD.query(tSql2);  //執行SQL查詢  
	document.getElementById("txt3").value = tResult2[0][1];
	document.getElementById("txt7").value = tResult2[0][2];
	
	var tSql3 = "select 'T100用户[测试]' 查询主题,COUNT(*) 笔数,to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 from gzxa_t where gzxaent=11 and gzxastus='Y'";
	var tResult3= T100TEST.query(tSql3);  //執行SQL查詢  
	document.getElementById("txt4").value = tResult3[0][1];
	document.getElementById("txt7").value = tResult3[0][2];
	
	var tSql4 = "select 'BPM账号[正试]' 查询主题,COUNT(*) 笔数,to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 from V_EMPE_LEVEL ";
	var tResult4= BPMPROD.query(tSql4);  //執行SQL查詢  
	document.getElementById("txt5").value = tResult4[0][1];
	document.getElementById("txt7").value = tResult4[0][2];
	
	var tSql5 = "select 'BPM账号[测试]' 查询主题,COUNT(*) 笔数,to_char(sysdate,'yyyy-mm-dd hh24:mm:ss') 查询时间 from V_EMPE_LEVEL  ";
	var tResult5= BPMTEST.query(tSql5);  //執行SQL查詢  
	document.getElementById("txt6").value = tResult5[0][1];
	document.getElementById("txt7").value = tResult5[0][2];
	
	
}


//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//