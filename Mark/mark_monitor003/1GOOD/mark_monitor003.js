// to format js 
// http://jsbeautifier.org/
alert("xxx to format js, http://jsbeautifier.org/ ");

//常用引入外部JS
document.write('<script type="text/javascript" src="../../CustomJsLib/EFGPShareMethod.js"></script>'); //for 开窗
//document.write('<script type="text/javascript" src="http://10.10.0.70/projectnote/js"></script>'); //for 开窗
//document.write('<script type="text/javascript" src="http://10.10.0.70/projectnote/js2"></script>'); //for 开窗

// 04/06 21:30 GOOD, 
ddlResult = Array(Array("0", "--- ( 請選擇) ---"), Array("1", "(1)料件"), Array("2", "(2)BOM"), Array("3", "(3)产品工艺"), Array("4", "(4)料件主分群码"), Array("5", "(5)供应商"), );
ddlSql = Array("", "select 料号, 品名,审核日期 from V_PART", "select 主件料号, 品名,审核日期 from BASIC002", "select 工艺料号,品名,工艺编号,说明,审核日期 from BASIC003", "select 主分群码,说明,审核日期 from BASIC004", "select 供应商编号,供应商全名,资料审核日期 from BASIC005", );
ddlLbl = Array(Array(), Array("料号", " 品名", "审核日期"), Array("主件料号", " 品名", "审核日期"), Array("工艺料号", "品名", "工艺编号", "说明", "审核日期"), Array("主分群码", "说明", "审核日期"), Array("供应商编号", "供应商全名", "资料审核日期"), );




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
// 2017-04-07, reviewed by Mark
// 按範例由SQL查出獲得二維Array用來初始下拉
// 先改成由代碼產生器產生代碼放到這檔案前頭處
function initDropdownlist() {
    // GREAT!
    // 2017-04-06, by Mark
    // ddlResult is mocking query result
    // It turns out working very well!
    tResult = ddlResult;

    // 以下是原範例代碼，
    // 連預設的下拉在這階段名稱仍沿用 drp_testSQL
    var tResultArray = eval(tResult); //查詢結果為一個二維陣列   
    DWRUtil.removeAllOptions("drp_testSQL"); //先移除所有下拉式選單內容  
    DWRUtil.addOptions("drp_testSQL", tResultArray, 0, 1); //將陣列中的資料加入下拉式選單中     
    //系統會將被選到的值存在一個由元件代號加上_hdn的隱藏欄位中  
    //因此每次資料載入完畢後需將被選到的值由隱藏欄位中取出並設定至下拉式選單中  
    var tDropdownHdn = document.getElementById("drp_testSQL_hdn");
    if (tDropdownHdn != null) {
        var tSelectedSQLDropdown = eval(tDropdownHdn.value);
        DWRUtil.setValue("drp_testSQL", tSelectedSQLDropdown);
    }
}

// 2017-04-07, reviewed by Mark
// Keep this one as basic practice!
function xxxbtn1_onclick() {
    lbl = new Array("品名", "规格", "单位");
    var sql = " select BGYPNAME,BGYPSPEC,BGYPUOM from Z_BGYP  ";
    btn_core_bpm(lbl, sql);
    return true;

    //***以下代碼是最先示範
    clearTxt();
    lbl1.value = "品名";
    lbl2.value = "规格";
    lbl3.value = "单位";

    var FileName = "SingleOpenWin";
    var sql = " select BGYPNAME,BGYPSPEC,BGYPUOM from Z_BGYP  ";
    var SQLClaused = new Array(sql);
    var SQLLabel = new Array("品名", "规格", "单位"); //客制开窗的Grid Label
    var QBEField = new Array("BGYPNAME", "BGYPSPEC", "BGYPUOM"); //模糊查询,須和DB Table栏位名称相同
    var QBELabel = new Array("品名", "规格", "单位"); //模糊查询的Label
    var ReturnId = new Array("txt1", "txt2", "txt3", ); //表单上的栏位代号

    // 2017-04-07, reviewed by Mark
    // to change data source with better naming convention
    //singleOpenWin(FileName, databaseCfgId_EFGP, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
    singleOpenWin(FileName, dsBpmTest, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
}


function btn_core(lbl, sql) {
    clearTxt(); //清畫面
    // 2017-04-07, reviewed by Mark
    // a trick to fill out desired title
    // table th
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

    // 2017-04-07, reviewed by Mark
    // a trick to choose proper set of Array 
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

    // 2017-04-07, reviewed by Mark
    // txtRadio.value, 0 =>正式环境, 1 =>测试环境
    // rewirte this 
    ds = dsT100Prod;
    if (txtRadio.value == 0) {
        // singleOpenWin(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
    }
    if (txtRadio.value == 1) {
        // singleOpenWin(FileName, dsT100Test, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
        ds = dsT100Test
    }
    singleOpenWin(FileName, ds, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
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
        // sql=sql.replace(/select/,"select 'T100正式環境' AS ENV,");
        // alert("*** 正式环境 ***"+sql);
        var SQLClaused = new Array(sql);

        singleOpenWin(FileName, dsT100Prod, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);
    }
    if (txtRadio.value == 1) {
        // alert("--- 测试环境 ---");
        // singleOpenWin(FileName, ds2, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);

        // sql=sql.replace(/select/,"select 'T100測試環境' AS ENV,");
        var SQLClaused = new Array(sql);
        singleOpenWin(FileName, dsT100Test, SQLClaused, SQLLabel, QBEField, QBELabel, ReturnId, 720, 430);

    }
    // alert("end of core");
}




// 2017-04-07, reviewed by Mark
// not in use, just keep it for reference
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



// 2017-04-07, reviewed byMark
// getElementsByName, 不是ById
//
function radio_onclick() { //radiobutton控件取值
    var Rb_frb = document.getElementsByName("radio");
    for (var i = 0; i < Rb_frb.length; i++) {
        if (Rb_frb[i].checked) {
            txtRadio.value = Rb_frb[i].value; //实际值
        }
    }
    // 2017-04-07, reviewed byMark
    // 直接点选正式环境或测试环境的radio也要能直接觸發新的查詢

    drp_testSQL_onchange();
}


// 2017-04-07, reviewed byMark
// 由於在開窗的畫面想刷Keyword會被認為是選取而關閉開窗
// 因此有這btn可以再次同條件查詢
function btnRun_onclick() {
    drp_testSQL_onchange();
}


// 2017-04-07, reviewed byMark
// 這是給一初值，避開一些異常
var selectId = 0


// 2017-04-07, reviewed byMark
// 這是舊的下拉控件,畫面上已刪掉
// TODO
// function ddl_onclick() {
//     var obj = document.getElementById("ddl"); //获取select控件，Select1为控件Select的id
//     var index = obj.selectedIndex; //选中的索引；
//     var text = obj.options[index].text; //选中的文本
//     var value = obj.options[index].value; //选中值
//     selectId = obj.options[index].value; //选中值
//     // document.getElementById("txtDdlText").value = text;//当选择正常领用时，hdn_sqlx显示为正常领用
//     document.getElementById("txtDdlId").value = selectId; //当选择正常领用时，hdn_sqlxid显示为1

// }


// 2017-04-07, reviewed byMark
// TODO ...這是按範例由SQL語句查詢取得array後更新

// tDropdownHdn
function drp_testSQL_onchange() {
    var obj = document.getElementById("drp_testSQL"); //获取select控件，Select1为控件Select的id
    var index = obj.selectedIndex; //选中的索引；
    var text = obj.options[index].text; //选中的文本
    var value = obj.options[index].value; //选中值
    selectId = obj.options[index].value; //选中值
    // document.getElementById("txtDdlText").value = text;//当选择正常领用时，hdn_sqlx显示为正常领用
    document.getElementById("drp_testSQL_hdn").value = selectId; //当选择正常领用时，hdn_sqlxid显示为1
    // document.getElementById("txtDebug").value = selectId;//当选择正常领用时，hdn_sqlxid显示为1


    // lbl = new Array("料号", " 品名", "审核日期");
    // lbl = new Array("料号", " 品名", "审核日期");
    // var sql = "select UNIQUE(a.imaa001) 料号,b.imaal003 品名,TO_CHAR(a.imaacnfdt, 'YYYY-MM-DD') 审核日期 from imaa_t a left join  imaal_t b on b.imaal001=a.imaa001 where  a.imaaent=11";
    //var sql = "select  料号, 品名,审核日期 from V_PART";
    var sql = ddlSql[selectId];
    var lbl = ddlLbl[selectId];
    // var lbl_zh = ddlLbl_zh [selectId];

    //http://www.w3school.com.cn/jsref/jsref_replace.asp
    // &#39;
    //sql=sql.replace(/&#39;/g,"'");
    document.getElementById("txtDebug").value = sql; //当选择正常领用时，hdn_sqlxid显示为1
    // document.getElementById("txtDebug2").value =lbl_en;//当选择正常领用时，hdn_sqlxid显示为1

    // var sql = "select a.imaa001 C1,b.imaal003 C2,TO_CHAR(a.imaacnfdt, 'YYYY-MM-DD') C3 from imaa_t a left join  imaal_t b on b.imaal001=a.imaa001 where  a.imaaent=11";
    // alert(lbl_en);

    // btn_core_t100(lbl, sql);
    btn_core_t100(lbl, sql);




}

function formCreate() {
    return true;
}

function formOpen() {
    // 2017-04-07, reviewed by Mark
    // init Dropdownlist
    initDropdownlist();
    return true;
}

function formSave() {
    return true;
}

function formClose() {
    return true;
}
//$-----Auto generated script block, Please do not edit or modify script below this line.-----$//