//常用引入外部JS
document.write('<script type="text/javascript" src="../../CustomJsLib/EFGPShareMethod.js"></script>'); //for 开窗
document.write('<script type="text/javascript" src="http://10.10.0.70/projectnote/js"></script>'); //for 开窗
document.write('<script type="text/javascript" src="http://10.10.0.70/projectnote/js2"></script>'); //for 开窗

//数据库链接
var databaseCfgId_EFGP = "EFGPTEST"; //办公用品
var ds2 = "T100TEST"; //查询T100测试环境
var ds3 = "T100PROD"; //查询T100正式环境


var dsBpmTest = "BPMTEST"; //查询T100测试环境
var dsBpmProd = "BPMPROD"; //查询T100正式环境
var dsT100Test = "T100TEST"; //查询T100测试环境
var dsT100Prod = "T100PROD"; //查询T100正式环境

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
var txtRadio = document.getElementById("txtRadio"); //实际值

function clearTxt() {
    val = ""
    val2 = "..."
    document.getElementById("txt1").value = val;
    document.getElementById("txt2").value = val;
    document.getElementById("txt3").value = val;
    document.getElementById("txt4").value = val;
    document.getElementById("txt5").value = val;
    document.getElementById("txt6").value = val;
    document.getElementById("txt7").value = val;

    document.getElementById("lbl1").value = val2;
    document.getElementById("lbl2").value = val2;
    document.getElementById("lbl3").value = val2;
    document.getElementById("lbl4").value = val2;
    document.getElementById("lbl5").value = val2;
    document.getElementById("lbl6").value = val2;
    document.getElementById("lbl7").value = val2;


    return true;

}


// 2017-04-06, 
// http://10.10.0.33:8086/NaNaWeb/GP/Authentication
function addDropdownBySQL1(){  
  // var tDataSource= new DataSource("formId","SQLId"); //建立連線(填入表單代號以及SQL Commend代號)  
  var tDataSource= new DataSource("mark_monitor001","SQL3"); //建立連線(填入表單代號以及SQL Commend代號)  
  
  // var tSql= "Select column1, column2 from table1"; 
  var tSql= "SELECT COLUMN1,COLUMN2 FROM TABLE1"; 

  var tResult= tDataSource.query(tSql);  //執行SQL查詢  

  // tResult=Array(Array("1","A"),Array("2","B"), Array("3","Commend代號"))  ;

// GREAT!
// 2017-04-06, by Mark
// ddlResult is mocking query result
// It turns out working very well!
tResult=ddlResult;

  // alert("???tResult");
  // alert(tResult);

  var tResultArray=eval(tResult);  //查詢結果為一個二維陣列  
  // alert("tResultArray");
  // alert(tResultArray);


  DWRUtil.removeAllOptions("drp_testSQL");  //先移除所有下拉式選單內容  
  DWRUtil.addOptions("drp_testSQL",tResultArray, 0, 1);   //將陣列中的資料加入下拉式選單中     
  //系統會將被選到的值存在一個由元件代號加上_hdn的隱藏欄位中  
  //因此每次資料載入完畢後需將被選到的值由隱藏欄位中取出並設定至下拉式選單中  
  
  var tDropdownHdn=document.getElementById("drp_testSQL_hdn");     
  if (tDropdownHdn != null) {       
    var tSelectedSQLDropdown=eval(tDropdownHdn.value);     
    DWRUtil.setValue("drp_testSQL", tSelectedSQLDropdown);     
  }  
} 




function btn19_onclick() {
   // 2017-04-05 working well! 
   // alert("$js_a1=\"12345\"; $js_a2=\"ABC\",,,,js_a1= " + $js_a1);//
   // alert("a3= " + js_a3);//

   // alert("length? " + $lbl_array.length);//
   //  alert("toString? " + $lbl_array[19].toString());//
   // alert("lbl_19? " + myLblArray[19]);//

   // lbl = new Array("DEPT_ID", "DEPT_NAME", "EMPE_ID","EMPE_NAME","JOB","LEAVE_DATE","SUP_NAME");
   lbl = myLblArray[19];
   alert("獲得的語句? " + myLblArray[19]);//
  
   var sql = " select a.DEPT_ID,a.DEPT_NAME,a.EMPE_ID,a.EMPE_NAME,a.JOB,a.LEAVE_DATE,a.SUP_NAME from V_EMPE a  ";
     btn_core_bpm(lbl, sql);
    return true;
}

function btn1_onclick() {
   lbl = new Array("品名", "规格", "单位");
    var sql = " select BGYPNAME,BGYPSPEC,BGYPUOM from Z_BGYP  ";
     btn_core_bpm(lbl, sql);
    return true;

    // 
    clearTxt();
    // alert("婷婷1btn_bgyp_onclick");
    lbl1.value = "品名";
    lbl2.value = "规格";
    lbl3.value = "单位";
    // lbl4.value = "...";
    // lbl5.value = "...";
    // lbl6.value = "...";
    // lbl7.value = "...";

    var FileName = "SingleOpenWin";
    var sql = " select BGYPNAME,BGYPSPEC,BGYPUOM from Z_BGYP  ";
    var SQLClaused = new Array(sql);
    var SQLLabel = new Array("品名", "规格", "单位"); //客制开窗的Grid Label
    var QBEField = new Array("BGYPNAME", "BGYPSPEC", "BGYPUOM"); //模糊查询,須和DB Table栏位名称相同
    var QBELabel = new Array("品名", "规格", "单位"); //模糊查询的Label
    var ReturnId = new Array("txt1", "txt2", "txt3", ); //表单上的栏位代号
    //singleOpenWin(FileName, databaseCfgId_EFGP, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
    singleOpenWin(FileName, dsBpmTest, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);

}

function btn2_onclick() {
    lbl = new Array("报工单号", " 报工类型", "录单人员", "姓名", "审核日期");
    var sql = " select UNIQUE(a.sffbdocno) 报工单号,a.sffb001 报工类型,a.sffbcrtid 录单人员,c.ooag011 姓名,TO_CHAR(a.sffbcnfdt, 'YYYY-MM-DD') 审核日期 from sffb_t a left join ooag_t c on c.ooag001=a.sffbcrtid where a.sffbent=11";
    btn_core(lbl, sql);
    return true;

}

function btn3_onclick() {
    lbl = new Array("单号", "生产料号", "品名", "本站作业", "作业名称", "生产数量", "预计完工日");
    var sql = "select a.sfaadocno 单号,a.sfaa010 生产料号,d.imaal003 品名,c.sfcb003 本站作业,e.oocql004 作业名称,b.sfca003 生产数量,TO_CHAR(c.sfcb045, 'YYYY-MM-DD') 预计完工日 from sfaa_t a  left join sfca_t b on b.sfcadocno=a.sfaadocno left join sfcb_t c on c.sfcbdocno=a.sfaadocno  left join imaal_t d on d.imaal001=a.sfaa010 left join oocql_t e on e.oocql002=c.sfcb003 where c.sfcb003 like 'T%' ";
    btn_core(lbl, sql);
    return true;
}


function btn4_onclick() {
    lbl = new Array("主件料号", " 品名", "审核日期");
    var sql = " select UNIQUE(a.bmaa001) 主件料号,b.imaal003 品名,TO_CHAR(a.bmaacnfdt, 'YYYY-MM-DD') 审核日期 from bmaa_t a,  imaal_t b WHERE b.imaal001=a.bmaa001";
    btn_core(lbl, sql);
    return true;


}


function btn_core(lbl, sql) {
    clearTxt(); //清畫面
    //table th
    if (lbl.length >= 1) lbl1.value = lbl[0];
    if (lbl.length >= 2) lbl2.value = lbl[1];
    if (lbl.length >= 3) lbl3.value = lbl[2];
    if (lbl.length >= 4) lbl4.value = lbl[3];
    if (lbl.length >= 5) lbl5.value = lbl[4];
    if (lbl.length >= 6) lbl6.value = lbl[5];
    if (lbl.length >= 7) lbl7.value = lbl[6];

    // T100 BPM 要求的樣式
    var FileName = "SingleOpenWin";
    var SQLClaused = new Array(sql);
    var SQLLabel = lbl; //客制开窗的Grid Label
    var QBEField = lbl; //模糊查询,須和DB Table栏位名称相同
    var QBELabel = lbl; //模糊查询的Label

    if (lbl.length == 3) {
        var ReturnId = new Array("txt1", "txt2", "txt3");
    }
    if (lbl.length == 4) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4");
    }
    if (lbl.length == 5) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5");
    }
    if (lbl.length == 6) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5", "txt6");
    }
    if (lbl.length == 7) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5", "txt6", "txt7");
    }

    if (txtRadio.value == 0) {
        // alert("*** 正式环境 ***");
        // singleOpenWin(FileName, ds3, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        singleOpenWin(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
    }
    if (txtRadio.value == 1) {
        // alert("--- 测试环境 ---");
        // singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        
        singleOpenWin(FileName, dsT100Test, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        
    }
    // alert("end of core");
}


function btn_core_t100(lbl, sql) {

    //2017-04-01, by Mark
    //http://stackoverflow.com/questions/586182/how-to-insert-an-item-into-an-array-at-a-specific-index
    // lbl=lbl.splice(0,0,"ENV");

    clearTxt(); //清畫面
    //table th
    if (lbl.length >= 1) lbl1.value = lbl[0];
    if (lbl.length >= 2) lbl2.value = lbl[1];
    if (lbl.length >= 3) lbl3.value = lbl[2];
    if (lbl.length >= 4) lbl4.value = lbl[3];
    if (lbl.length >= 5) lbl5.value = lbl[4];
    if (lbl.length >= 6) lbl6.value = lbl[5];
    if (lbl.length >= 7) lbl7.value = lbl[6];

    // T100 BPM 要求的樣式
    var FileName = "SingleOpenWin";


    // 2017-04-01, by Mark 
    // to show ENV
    //



    // var SQLClaused = new Array(sql);
    var SQLLabel = lbl; //客制开窗的Grid Label
    var QBEField = lbl; //模糊查询,須和DB Table栏位名称相同
    var QBELabel = lbl; //模糊查询的Label

    if (lbl.length == 3) {
        var ReturnId = new Array("txt1", "txt2", "txt3");
    }
    if (lbl.length == 4) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4");
    }
    if (lbl.length == 5) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5");
    }
    if (lbl.length == 6) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5", "txt6");
    }
    if (lbl.length == 7) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5", "txt6", "txt7");
    }

    if (txtRadio.value == 0) {
        // alert("*** 正式环境 ***");
        // singleOpenWin(FileName, ds3, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        sql=sql.replace(/select/,"select 'T100正式環境' AS ENV,");
        // alert("*** 正式环境 ***"+sql);
        var SQLClaused = new Array(sql);

        singleOpenWin(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
    }
    if (txtRadio.value == 1) {
        // alert("--- 测试环境 ---");
        // singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        
        sql=sql.replace(/select/,"select 'T100測試環境' AS ENV,");
         var SQLClaused = new Array(sql);
        singleOpenWin(FileName, dsT100Test, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        
    }
    // alert("end of core");
}

function btn_core_bpm(lbl, sql) {
    clearTxt(); //清畫面
    //table th
    if (lbl.length >= 1) lbl1.value = lbl[0];
    if (lbl.length >= 2) lbl2.value = lbl[1];
    if (lbl.length >= 3) lbl3.value = lbl[2];
    if (lbl.length >= 4) lbl4.value = lbl[3];
    if (lbl.length >= 5) lbl5.value = lbl[4];
    if (lbl.length >= 6) lbl6.value = lbl[5];
    if (lbl.length >= 7) lbl7.value = lbl[6];

    // T100 BPM 要求的樣式
    var FileName = "SingleOpenWin";
    var SQLClaused = new Array(sql);
    var SQLLabel = lbl; //客制开窗的Grid Label
    var QBEField = lbl; //模糊查询,須和DB Table栏位名称相同
    var QBELabel = lbl; //模糊查询的Label

    if (lbl.length == 3) {
        var ReturnId = new Array("txt1", "txt2", "txt3");
    }
    if (lbl.length == 4) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4");
    }
    if (lbl.length == 5) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5");
    }
    if (lbl.length == 6) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5", "txt6");
    }
    if (lbl.length == 7) {
        var ReturnId = new Array("txt1", "txt2", "txt3", "txt4", "txt5", "txt6", "txt7");
    }

    if (txtRadio.value == 0) {
        // alert("*** 正式环境 ***");
        // singleOpenWin(FileName, ds3, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        singleOpenWin(FileName, dsBpmProd, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
    }
    if (txtRadio.value == 1) {
        // alert("--- 测试环境 ---");
        // singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        
        singleOpenWin(FileName, dsBpmTest, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        
    }
    // alert("end of core");
}



function btn5_onclick() {
    lbl = new Array("供应商编号", "供应商全名", "资料审核日期");
    var sql = " select UNIQUE( a.pmaa001) 供应商编号,b.pmaal003 供应商全名,TO_CHAR(a.pmaacnfdt, 'YYYY-MM-DD') 资料审核日期  from pmaa_t a left join pmaal_t b on b.pmaal001=a.pmaa001 where a.pmaa002=1 order by a.pmaa001";
    btn_core(lbl, sql);
    return true;
}

function btn6_onclick() {

    lbl = new Array("客户编号", " 客户全名", "资料审核日期");
    var sql = "select UNIQUE(a.pmaa001) 客户编号,b.pmaal003 客户全名,TO_CHAR(a.pmaacnfdt, 'YYYY-MM-DD') 资料审核日期  from pmaa_t a left join pmaal_t b on b.pmaal001=a.pmaa001 where a.pmaa002=2";
    btn_core(lbl, sql);
    return true;


}


function btn7_onclick() {
    lbl = new Array("料号", " 品名", "审核日期");
    var sql = "select UNIQUE(a.imaa001) 料号,b.imaal003 品名,TO_CHAR(a.imaacnfdt, 'YYYY-MM-DD') 审核日期 from imaa_t a left join  imaal_t b on b.imaal001=a.imaa001 where  a.imaaent=11";
    btn_core(lbl, sql);
    return true;

}

function btn20_onclick() {
    // alert("btn20_onclick");
    lbl = new Array("ENV","工序编号", " 工序名称", "创建日期");
    var sql = "\
    select b.oocql002 工序编号,b.oocql004 工序名称,to_char(a.oocqcrtdt,'yyyy-mm-dd') 创建日期 from oocq_t a \
left join oocql_t b on b.oocql002=a.oocq002 and b.oocqlent=a.oocqent \
where a.oocq001=221  and a.oocqent=11 \
order by b.oocql002  \
";
// alert(sql);
    btn_core_t100(lbl, sql);
    return true;

}



function btn8_onclick() {
    lbl = new Array("审核日期", " 品名", " 工艺编号", " 说明", "审核日期");
    var sql = "select UNIQUE(a.ecba001) 工艺料号,b.imaal003 品名,a.ecba002 工艺编号,a.ecba003 说明,TO_CHAR(a.ecbacnfdt, 'YYYY-MM-DD') 审核日期 from ecba_t a, imaal_t b where b.imaal001=a.ecba001 and  ecbaent=11";
    btn_core(lbl, sql);
    return true;



}

function btn9_onclick() {

    lbl = new Array("库位编号", " 库位名称", " 库位管控", " 录入日期");
    var sql = "select UNIQUE(a.inaa001) 库位编号,b.inayl003 库位名称,a.inaa007 库位管控,TO_CHAR(a.inaacrtdt, 'YYYY-MM-DD') 录入日期 from inaa_t a left join inayl_t b on b.INAYL001=a.inaa001 where  a.inaaent=11";
    btn_core(lbl, sql);
    return true;


}

function btn10_onclick() {

    lbl = new Array("主分群码", " 说明", " 审核日期");
    var sql = "select UNIQUE(a.imca001) 主分群码,b.oocql004 说明,TO_CHAR(a.imcacrtdt, 'YYYY-MM-DD') 审核日期  from imca_t a left join oocql_t b on b.oocql002=a.imca001 where  a.imcaent=11  order by a.imca001";
    btn_core(lbl, sql);
    return true;


}

function btn11_onclick() {
    lbl = new Array("员工编号", " 全名", " 归属部门", " 录入日期");
    var sql = "select UNIQUE(a.ooag001) 员工编号,a.ooag011 全名,a.ooag003 归属部门,TO_CHAR(a.ooagcrtdt, 'YYYY-MM-DD') 录入日期  from ooag_t a where  a.ooagent=11";
    btn_core(lbl, sql);
    return true;

}


function btn12_onclick() {
    lbl = new Array("订单号", " 订单日期", " 业务人员", " 姓名", " 客户编号", " 客户名称");
    var sql = "select UNIQUE(a.xmdadocno) 订单号,TO_CHAR(a.xmdadocdt, 'YYYY-MM-DD') 订单日期,a.xmda002 业务人员,c.ooag011 姓名,a.xmda004 客户编号,b.PMAAL003 客户名称 from xmda_t a left join pmaal_t b on b.PMAAL001=a.XMDA004 left join ooag_t c on c.ooag001=a.xmda002 where a.xmdaent=11";
    btn_core(lbl, sql);
    return true;

}

function btn13_onclick() {
    lbl = new Array("客户编号", " 客户简称", " 公司料号", " 品名", " 客户料号", " 品名", " 录入日期");
    var sql = "select UNIQUE(a.pmao001) 客户编号,b.pmaal004 客户简称,a.pmao002 公司料号,c.imaal003 品名,a.pmao004 客户料号,a.pmao009 品名,TO_CHAR(a.pmaocrtdt, 'YYYY-MM-DD') 录入日期 from pmao_t a left join pmaal_t b on b.pmaal001=pmao001 left join imaal_t c on c.imaal001=pmao002 where a.pmaoent=11";
    btn_core(lbl, sql);
    return true;



}

function btn14_onclick() {

    lbl = new Array("用户", " 员工编号", " 员工姓名", " 录入日期");
    var sql = "select UNIQUE(a.gzxa001) 用户,a.gzxa003 员工编号,b.ooag011 员工姓名,TO_CHAR(a.gzxacrtdt,'YYYY-MM-DD') 录入日期 from gzxa_t a left join ooag_t b on b.ooag001=a.gzxa003 where a.gzxaent=11";
    btn_core(lbl, sql);
    return true;



}

function btn15_onclick() {

    lbl = new Array("采购单号", " 采购员", " 员工姓名", " 录入日期", " 单据状态");
    var sql = "select UNIQUE(a.pmdldocno) 采购单号,a.pmdl002 采购员,b.ooag011 员工姓名,TO_CHAR(a.pmdlcrtdt,'YYYY-MM-DD') 录入日期,a.pmdlstus 单据状态 from pmdl_t a left join ooag_t b on b.ooag001=a.pmdl002 where a.pmdlent=11 and a.pmdl005=1";
    btn_core(lbl, sql);
    return true;


}

function btn16_onclick() {

    lbl = new Array("采购单号", " 采购员", " 员工姓名", " 录入日期", " 单据状态");
    var sql = "select UNIQUE(a.pmdldocno) 采购单号,a.pmdl002 采购员,b.ooag011 员工姓名,TO_CHAR(a.pmdlcrtdt,'YYYY-MM-DD') 录入日期,a.pmdlstus 单据状态 from pmdl_t a left join ooag_t b on b.ooag001=a.pmdl002 where a.pmdlent=11 and a.pmdl005=2";
    btn_core(lbl, sql);
    return true;
}

function btn17_onclick() {

    lbl = new Array("料号", " 品名", " 库位", " 批号", " 账面库存");
    var sql = "select UNIQUE(a.inag001) 料号,b.IMAAL003 品名,a.inag004 库位,a.inag006 批号,a.inag008 账面库存 from inag_t a left join imaal_t b on b.imaal001=a.inag001 where a.inagent=11";
    btn_core(lbl, sql);
    return true;
}

function btn18_onclick() {
    lbl = new Array("工单号", " 生产料号", " 品名", " 生产数量", " 录入日期");
    var sql = "select a.sfaadocno 工单号,a.sfaa010 生产料号,b.imaal003 品名,a.sfaa012 生产数量,TO_CHAR(a.sfaadocdt, 'YYYY-MM-DD') 录入日期 from sfaa_t a left join imaal_t b on b.imaal001=a.sfaa010 where a.sfaaent=11";
    btn_core(lbl, sql);
    return true;
}

// select b.oocql002 工序编号,b.oocql004 工序名称,to_char(a.oocqcrtdt,'yyyy-mm-dd') 创建日期 from oocq_t a 
// left join oocql_t b on b.oocql002=a.oocq002
// where a.oocq001=221  and a.oocqent=11
// select 'T100PROD' AS ENV,b.oocql002 工序编号,b.oocql004 工序名称,to_char(a.oocqcrtdt,'yyyy-mm-dd') 创建日期 from oocq_t a  left join oocql_t b on b.oocql002=a.oocq002 where a.oocq001=221  and a.oocqent=11…



// function btn18_onclick() {
// alert("btn18_onclick");

// clearTxt();

// if (txtRadio.value == 0) {

//     alert("选择环境值的代码为0");
// }
// if (txtRadio.value == 1) {

//     alert("选择环境值的代码为1");
// }

// }

//供应商性质单选
function radio_onclick() { //radiobutton控件取值
    var Rb_frb = document.getElementsByName("radio");
    for (var i = 0; i < Rb_frb.length; i++) {
        if (Rb_frb[i].checked) {
            txtRadio.value = Rb_frb[i].value; //实际值
        }
    }
//直接点选正式环境或测试环境
 btnRun_onclick();

}

var selectId=0
function ddl_onclick(){
    var obj=document.getElementById("ddl") ;//获取select控件，Select1为控件Select的id
    var index=obj.selectedIndex;//选中的索引；
    var text=obj.options[index].text;//选中的文本
    var value=obj.options[index].value;//选中值
    selectId=obj.options[index].value;//选中值
    // document.getElementById("txtDdlText").value = text;//当选择正常领用时，hdn_sqlx显示为正常领用
    document.getElementById("txtDdlId").value = selectId;//当选择正常领用时，hdn_sqlxid显示为1

    // if (value==1) btn1_onclick();
    // if (value==2) btn2_onclick();
    // if (value==3) btn3_onclick();


}

// tDropdownHdn
function drp_testSQL_onchange(){
 var obj=document.getElementById("drp_testSQL") ;//获取select控件，Select1为控件Select的id
    var index=obj.selectedIndex;//选中的索引；
    var text=obj.options[index].text;//选中的文本
    var value=obj.options[index].value;//选中值
    selectId=obj.options[index].value;//选中值
    // document.getElementById("txtDdlText").value = text;//当选择正常领用时，hdn_sqlx显示为正常领用
    document.getElementById("drp_testSQL_hdn").value = selectId;//当选择正常领用时，hdn_sqlxid显示为1


}


function ddl2_onchange(){
// alert("ddl2_onchange");
    var obj=document.getElementById("ddl2") ;//获取select控件，Select1为控件Select的id
    var index=obj.selectedIndex;//选中的索引；
    var text=obj.options[index].text;//选中的文本
    var value=obj.options[index].value;//选中值
    selectId=obj.options[index].value;//选中值
    // document.getElementById("txtDdlText").value = text;//当选择正常领用时，hdn_sqlx显示为正常领用
    document.getElementById("txtDdlId").value = selectId;//当选择正常领用时，hdn_sqlxid显示为1

    // if (value==1) btn1_onclick();
    // if (value==2) btn2_onclick();
    // if (value==3) btn3_onclick();
    btnRun_onclick();

}

function btnRun_onclick(){
     if (selectId==1) btn1_onclick();
    if (selectId==2) btn2_onclick();
    if (selectId==3) btn3_onclick();
     if (selectId==4) btn4_onclick();
    if (selectId==5) btn5_onclick();
    if (selectId==6) btn6_onclick();
     if (selectId==7) btn7_onclick();
    if (selectId==8) btn8_onclick();
    if (selectId==9) btn9_onclick();
     if (selectId==10) btn10_onclick();
    if (selectId==11) btn11_onclick();
    if (selectId==12) btn12_onclick();
     if (selectId==13) btn13_onclick();
    if (selectId==14) btn14_onclick();
    if (selectId==15) btn15_onclick();
     if (selectId==16) btn16_onclick();
    if (selectId==17) btn17_onclick();
    if (selectId==18) btn18_onclick();
    if (selectId==19) btn19_onclick();
    if (selectId==20) btn20_onclick();
    

}

function formCreate() {
    return true;
}

function formOpen() {

    // txtRadio.value = 0; //实际值
    // alert("选择环境值的代码为" + txtRadio.value);
    addDropdownBySQL1();
    return true;
}

function formSave() {
    return true;
}

function formClose() {
    return true;
}
//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//