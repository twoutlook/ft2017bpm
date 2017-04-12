/**
 * @author windy
 * @date 2008/11/07
 */
/**
 * @param pFileName JSP檔名(String)ay)
 * @param pDataBaseCfgld 採用 系統管理員-系統管理-資料來源設定
 * @param pLeaveUserId 請假表單上的申請人代號欄位
 * @param pLeaveUserName 請假表單上的申請人名稱欄位 
 * @param pLocal傳入語系 (Bernice add 2009-06-24)
 */
function openWind(pFileName,pDatabaseCfgId, pLeaveUserId, pLeaveUserName,pLocale){
	var tFileName = encodeURI(pFileName);
	var tDatabaseCfgId = encodeURI(pDatabaseCfgId);
	var tLeaveUserId = encodeURI(pLeaveUserId);
	var tLeaveUserName = encodeURI(pLeaveUserName);
	var tLocale = encodeURI(pLocale);
	
	openDialog("/NaNaWeb/CustomOpenWin/"+tFileName+".jsp?DatabaseCfgId="+tDatabaseCfgId+"&LeaveUserId="+tLeaveUserId+"&LeaveUserName="+tLeaveUserName+"&Locale="+tLocale, "800", "500", "titlebar,scrollbars,status,resizable");
}

/**
 * @author nickylee
 * @date 2008/11/12
 */
/**
 * @param pFileName JSP檔名(String)ay)
 * @param pDataBaseCfgld 採用 系統管理員-系統管理-資料來源設定
 * @param pLeaveUserId 請假表單上的申請人部門欄位
 * @param pLeaveUserName 請假表單上的起始日期欄位 
 * @param pLeaveUserName 請假表單上的截止日期欄位 
 */
function openWindUnit(pFileName,pDatabaseCfgId, pLeaveUnit, pDateStartDate, pDateEndDate,pLocale){
 var tFileName = encodeURI(pFileName);
 var tDatabaseCfgId = encodeURI(pDatabaseCfgId);
 var tLeaveUnit = encodeURI(pLeaveUnit);
 var tDateStartDate = encodeURI(pDateStartDate);
 var tDateEndDate = encodeURI(pDateEndDate);
 var tLocale = encodeURI(pLocale);
 openDialog("/NaNaWeb/CustomOpenWin/"+tFileName+".jsp?DatabaseCfgId="+tDatabaseCfgId+"&LeaveUnit="+tLeaveUnit+"&DateStartDate="+tDateStartDate+"&DateEndDate="+tDateEndDate+"&Locale="+tLocale, "800", "500", "titlebar,scrollbars,status,resizable");
} 

/**
 * @author windy
 * @date 2009/03/05
 */
/**
 * @param pDate 日期物件
 * @return tReturnDate 回傳日期字串 yyyy/MM/dd
 */
function formatDate(pDate){
   var tYearValue = pDate.getYear();
   var tMonthValue = (pDate.getMonth()) + 1;
   var tDateValue = pDate.getDate();
   
    var tReturnDate = tYearValue;

   if (tMonthValue < 10) {
       tReturnDate = tReturnDate + "/0" + tMonthValue;
   }else {
      tReturnDate = tReturnDate + "/" + tMonthValue;
   }

   if (tDateValue < 10) {
        tReturnDate = tReturnDate + "/0" + tDateValue;
   }else {
        tReturnDate = tReturnDate + "/" + tDateValue;
   }

   return tReturnDate;
}



	
