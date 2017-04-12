/**
 * @author Bernice
 */
/**
 * 適用於表單form呼叫jsp開窗 - 單選 by Jerry
 * @param pFileName JSP檔名(String)
 * @param pDatabaseCfgId 系統管理員所設定的資料庫名稱,依系統管理員設定不同,而需調整 Type:string
 * @param pSQLID       查詢DB SQL的Key值
 * @param pSQLLabel    GridField顯示的Label, Type:Array
 * @param pQBEField    查詢欄位, Type:Array
 * @param pQBELabel    查詢欄位的Lable, Type:Array
 * @param pReturnId      回傳欄位ID(Array)
 * @Exception
 */
function WarOpenWindForSingle(pFileName,pDatabaseCfgId,pSQLClaused,pSQLLabel,pQBEField,pQBELabel,pReturnId){
    var tFileName = encodeURI(pFileName);
	var tDatabaseCfgId = pDatabaseCfgId;
	var tSQLClaused = encodeURI(pSQLClaused.toString());
    var tSQLLabel = encodeURI(pSQLLabel.toString());
    var tQBEField = encodeURI(pQBEField.toString());
    var tQBELabel = encodeURI(pQBELabel.toString());
	var tReturnId = encodeURI(pReturnId.toString());
    openDialog("/NaNaWeb/CustomOpenWin/" + tFileName + ".jsp?SQLClaused="+tSQLClaused+"&SQLLabel="+tSQLLabel+"&QBEField="+tQBEField+"&QBELabel="+tQBELabel+"&ReturnId="+tReturnId+"&DatabaseCfgId="+tDatabaseCfgId+"&prop0=nmt01&prop1=nmt02", "800", "500", "titlebar,scrollbars,status,resizable");		
}


