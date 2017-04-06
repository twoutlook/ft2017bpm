//常用引入外部JS
document.write('<script type="text/javascript" src="../../CustomJsLib/EFGPShareMethod.js"></script>');//for 开窗

//数据库链接
var databaseCfgId_EFGP = "EFGPTEST";   //系統管理員資料來源(DataAccessDefinition)代號
var ds2 = "T100TEST";   //系統管理員資料來源(DataAccessDefinition)代號

// 2017-03-27准备设置一个通用的标签
// var lble1 = document.getElementById("lble1");
// var lble2 = document.getElementById("lble2");
// var lble3 = document.getElementById("lble3");
var lbl1 = document.getElementById("lbl1");
var lbl2 = document.getElementById("lbl2");
var lbl3 = document.getElementById("lbl3");
var lbl4 = document.getElementById("lbl4");
var lbl5 = document.getElementById("lbl5");
var lbl6 = document.getElementById("lbl6");
var lbl7 = document.getElementById("lbl7");


function btn1_onclick(){
   // alert("btn2_onclick");
       lbl1.value="品名";
   lbl2.value="规格";
   lbl3.value="品名";
   lbl4.value="单位";
   lbl5.value="...";
   lbl6.value="...";
   lbl7.value="...";
   // alert("lbl1.value="+lbl1.value);

  	var FileName = "SingleOpenWin";		
	var sql = " select BGYPNAME,BGYPSPEC,BGYPUOM from Z_BGYP  "; 
	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("品名","规格","单位");//客制开窗的Grid Label
	var QBEField = new Array("BGYPNAME","BGYPSPEC","BGYPUOM");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("品名","规格","单位");//模糊查询的Label
     var ReturnId = new Array("txte1","txte2","txte3");//表单上的栏位代号
	singleOpenWin(FileName, databaseCfgId_EFGP, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}

function btn2_onclick(){
    // alert("btn2_onclick");
       lbl1.value="单号";
   lbl2.value="生产料号";
   lbl3.value="品名";
   lbl4.value="本站作业";
   lbl5.value="生产数量";
   lbl6.value="预计完工日";
   lbl7.value="...";
   // alert("lbl1.value="+lbl1.value);

  	var FileName = "SingleOpenWin";		
	var sql = " SELECT 单号,生产料号,品名,本站作业,生产数量,预计完工日 FROM V002 "; 
	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("单号","生产料号","品名","本站作业","生产数量","预计完工日");//客制开窗的Grid Label
	var QBEField = new Array("单号","生产料号","品名","本站作业","生产数量","预计完工日");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("单号","生产料号","品名","本站作业","生产数量","预计完工日");//模糊查询的Label
     var ReturnId = new Array("txte1","txte2","txte3","txte4","txte5","txte6");//表单上的栏位代号
	singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}

function btn3_onclick(){
    // alert("btn2_onclick");
       lbl1.value="单号";
   lbl2.value="生产料号";
   lbl3.value="品名";
   lbl4.value="本站作业";
   lbl5.value="作业名称";
   lbl6.value="生产数量";
   lbl7.value="预计完工日";
   // alert("lbl1.value="+lbl1.value);

  	var FileName = "SingleOpenWin";		
	var sql = " SELECT 单号,生产料号,品名,本站作业,作业名称,生产数量,预计完工日 FROM V003 "; 
	// 单号	生产料号	品名	本站作业	作业名称	生产数量	预计完工日

	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("单号","生产料号","品名","本站作业","作业名称","生产数量","预计完工日");//客制开窗的Grid Label
	var QBEField = new Array("单号","生产料号","品名","本站作业","作业名称","生产数量","预计完工日");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("单号","生产料号","品名","本站作业","作业名称","生产数量","预计完工日");//模糊查询的Label
     var ReturnId = new Array("txte1","txte2","txte3","txte4","txte5","txte6","txte7");//表单上的栏位代号
	singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}


function btn4_onclick(){
    // alert("btn2_onclick");
     lbl1.value="主件料号";
   lbl2.value="品名";
   lbl3.value="审核日期";
   lbl4.value="...";
   lbl5.value="...";
   lbl6.value="...";
   lbl7.value="...";
   // alert("lbl1.value="+lbl1.value);

  	var FileName = "SingleOpenWin";		
	var sql = " select UNIQUE(a.bmaa001) 主件料号,b.imaal003 品名,TO_CHAR(a.bmaacnfdt, 'YYYY-MM-DD') 审核日期 from bmaa_t a,  imaal_t b WHERE b.imaal001=a.bmaa001"; 
	// 单号	生产料号	品名	本站作业	作业名称	生产数量	预计完工日

	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("主件料号"," 品名","审核日期");//客制开窗的Grid Label
	var QBEField = new Array("主件料号"," 品名","审核日期");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("主件料号"," 品名","审核日期");//模糊查询的Label
    var ReturnId = new Array("txte1","txte2","txte3");//表单上的栏位代号
	singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}

function btn5_onclick(){
    // alert("btn2_onclick");

   lbl1.value="供应商编号";
   lbl2.value="供应商全名";
   lbl3.value="资料审核日期";
   lbl4.value="...";
   lbl5.value="...";
   lbl6.value="...";
   lbl7.value="...";
   // alert("lbl1.value="+lbl1.value);


  	var FileName = "SingleOpenWin";		
	var sql = " select UNIQUE( a.pmaa001) 供应商编号,b.pmaal003 供应商全名,TO_CHAR(a.pmaacnfdt, 'YYYY-MM-DD') 资料审核日期  from pmaa_t a left join pmaal_t b on b.pmaal001=a.pmaa001 where a.pmaa002=1 order by a.pmaa001"; 
	// 单号	生产料号	品名	本站作业	作业名称	生产数量	预计完工日

	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("供应商编号"," 供应商全名","资料审核日期");//客制开窗的Grid Label
	var QBEField = new Array("供应商编号"," 供应商全名","资料审核日期");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("供应商编号"," 供应商全名","资料审核日期");//模糊查询的Label
    var ReturnId = new Array("txte1","txte2","txte3");//表单上的栏位代号
	singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}
function btn6_onclick(){
    // alert("btn2_onclick");

   lbl1.value="客户编号";
   lbl2.value="客户全名";
   lbl3.value="资料审核日期";
   lbl4.value="...";
   lbl5.value="...";
   lbl6.value="...";
   lbl7.value="...";
   // alert("lbl1.value="+lbl1.value);



  	var FileName = "SingleOpenWin";		
	var sql = "select UNIQUE(a.pmaa001) 客户编号,b.pmaal003 客户全名,TO_CHAR(a.pmaacnfdt, 'YYYY-MM-DD') 资料审核日期  from pmaa_t a left join pmaal_t b on b.pmaal001=a.pmaa001 where a.pmaa002=2"; 
	// 单号	生产料号	品名	本站作业	作业名称	生产数量	预计完工日

	var SQLClaused = new Array(sql);
	var SQLLabel = new Array("客户编号"," 客户全名","资料审核日期");//客制开窗的Grid Label
	var QBEField = new Array("客户编号"," 客户全名","资料审核日期");//模糊查询,須和DB Table栏位名称相同
	var QBELabel = new Array("客户编号"," 客户全名","资料审核日期");//模糊查询的Label
    var ReturnId = new Array("txte1","txte2","txte3");//表单上的栏位代号
	singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
     
}


function formCreate(){
return true;
}
function formOpen(){
return true;
}
function formSave(){
return true;
}
function formClose(){
return true;
}
//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//