document.write('<script type="text/javascript" src="../../dwrDefault/engine.js"></script>');
document.write('<script type="text/javascript" src="../../dwrDefault/util.js"></script>');
document.write('<script type="text/javascript" src="../../dwrDefault/interface/ajax_DatabaseAccessor.js"></script>');
document.write('<script type="text/javascript" src="../../dwrDefault/interface/ajax_ExtOrgAccessor.js"></script>');
document.write('<script type="text/javascript" src="../../dwrDefault/interface/ajax_OrgAccessor.js"></script>');

/**
 * 適用於表單form呼叫jsp開窗 - 單選 by Jerry after 2.0
 * @param pFileName      JSP檔名                  Type:Array
 * @param pDatabaseCfgId 系統管理員所設定的資料庫名稱,依系統管理員設定不同,而需調整    Type:string
 * @param pSQLClaused    SQL句                   Type:Array
 * @param pSQLLabel      GridField顯示的Label     Type:Array
 * @param pQBEField      查詢欄位                 Type:Array
 * @param pQBELabel      查詢欄位的Lable          Type:Array
 * @param pReturnId      回傳欄位ID               Type:Array
 * @param pWidth         視窗寬度                 Type:Number
 * @param pHeight        視窗高度                 Type:Number
 */
function singleOpenWin(pFileName,pDatabaseCfgId,pSQLClaused,pSQLLabel,pQBEField,pQBELabel,pReturnId,pWidth,pHeight){
    var tFileName = encodeURI(pFileName);
    var tDatabaseCfgId = pDatabaseCfgId;
    var pSQLClauseArry = "";
    for(var i = 0; i < pSQLClaused.length; i++){
        pSQLClauseArry = pSQLClauseArry + pSQLClaused[i] + "~~";
    }
    var tSQLClaused = encodeURI(pSQLClauseArry.substring(0, pSQLClauseArry.length - 2));
    var tSQLLabel = encodeURI(pSQLLabel.toString());
    var tQBEField = encodeURI(pQBEField.toString());
    var tQBELabel = encodeURI(pQBELabel.toString());
    var tReturnId = encodeURI(pReturnId.toString());
    openDialog("/NaNaWeb/CustomOpenWin/" + tFileName + ".jsp?SQLClaused="+tSQLClaused+"&SQLLabel="+tSQLLabel+"&QBEField="+tQBEField+"&QBELabel="+tQBELabel+"&ReturnId="+tReturnId+"&DatabaseCfgId="+tDatabaseCfgId+"&prop0=nmt01&prop1=nmt02", pWidth, pHeight, "titlebar,scrollbars,status,resizable");		
}

/**
 * 適用於表單form呼叫jsp開窗 - 多選 by Jerry after 2.0
 * @param pFileName        JSP檔名                  Type:Array
 * @param pDatabaseCfgId   系統管理員所設定的資料庫名稱,依系統管理員設定不同,而需調整 Type:string
 * @param pSQLClaused      SQL句                   Type:Array
 * @param pSQLLabel        GridField顯示的Label     Type:Array
 * @param pQBEField        查詢欄位                 Type:Array
 * @param pQBELabel        查詢欄位的Lable           Type:Array
 * @param pOriginallyData  初始值                   Type:string
 * @param pWidth           視窗寬度                  Type:Number
 * @param pHeight          視窗高度                  Type:Number
 */
function pluralityOpenWin(pFileName,pDatabaseCfgId,pSQLClaused,pSQLLabel,pQBEField,pQBELabel,pOriginallyData,pWidth,pHeight){
    var tFileName = encodeURI(pFileName);
    var tDatabaseCfgId = pDatabaseCfgId;
    var pSQLClauseArry = "";
    for(var i = 0; i < pSQLClaused.length; i++){
        pSQLClauseArry = pSQLClauseArry + pSQLClaused[i] + "~~";
    }
    var tSQLClaused = encodeURI(pSQLClauseArry.substring(0, pSQLClauseArry.length - 2));
    var tSQLLabel = encodeURI(pSQLLabel.toString());
    var tQBEField = encodeURI(pQBEField.toString());
    var tQBELabel = encodeURI(pQBELabel.toString());
    var tOriginallyData = encodeURI(pOriginallyData.toString());

    openDialog("/NaNaWeb/CustomOpenWin/" + tFileName + ".jsp?SQLClaused="+tSQLClaused+"&SQLLabel="+tSQLLabel+"&QBEField="+tQBEField+"&QBELabel="+tQBELabel+"&DatabaseCfgId="+tDatabaseCfgId+"&OriginallyData="+tOriginallyData+"&prop0=nmt01&prop1=nmt02", pWidth, pHeight, "titlebar,scrollbars,status,resizable");
}
