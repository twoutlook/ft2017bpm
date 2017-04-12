/*
 * 20120110 ANGEL MODI  singleOpenWinHidden 單選開窗可依欄位順序隱藏欄位
 */
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
	if(window.changePageToCustomOpenWin){
		if(typeof(pReturnId)=='string'){
			changePageToCustomOpenWin(pDatabaseCfgId,pSQLClaused,pSQLLabel,pQBEField,pQBELabel,pReturnId,"multiple");			
		}else{
			changePageToCustomOpenWin(pDatabaseCfgId,pSQLClaused,pSQLLabel,pQBEField,pQBELabel,pReturnId,"single");
		}
	}else{
		/*
		var tFileName = encodeURI(pFileName);
		var tDatabaseCfgId = pDatabaseCfgId;
		
		var pSQLClauseArry = "";
		for(var i = 0; i < pSQLClaused.length; i++){
			pSQLClauseArry = pSQLClauseArry + pSQLClaused[i] + "~~";
		}
		var tSQLClaused = encodeURIComponent(pSQLClauseArry.substring(0, pSQLClauseArry.length - 2));
		//在encodeURIComponent後將% ( ) 換成 $ ~1~ ~2~ , 進JSP後再自行在進JSP後再自行decodeURIComponent
		tSQLClaused = tSQLClaused.replace(/%/g,"$").replace(/\(/g,"~1~").replace(/\)/g,"~2~").replace(/\./g,"~3~");
		
		var tSQLLabel = encodeURI(pSQLLabel.toString());
		var tQBEField = encodeURI(pQBEField.toString());
		var tQBELabel = encodeURI(pQBELabel.toString());
		var tReturnId = encodeURI(pReturnId.toString());
		openDialog("/NaNaWeb/CustomOpenWin/" + tFileName + ".jsp?SQLClaused="+tSQLClaused+"&SQLLabel="+tSQLLabel+"&QBEField="+tQBEField+"&QBELabel="+tQBELabel+"&ReturnId="+tReturnId+"&DatabaseCfgId="+tDatabaseCfgId+"&prop0=nmt01&prop1=nmt02", pWidth, pHeight, "titlebar,scrollbars,status,resizable");		
		*/
		CustomDataChooser.openWin(pFileName, pDatabaseCfgId, pSQLClaused, pSQLLabel, pQBEField, pQBELabel, pReturnId, pWidth, pHeight, "");
	}
    
}

/**
 * 適用於表單form呼叫jsp開窗 - 單選開窗可依欄位順序隱藏欄位 by ANGEL after 2.0
 * @param pFileName      JSP檔名                  Type:Array
 * @param pDatabaseCfgId 系統管理員所設定的資料庫名稱,依系統管理員設定不同,而需調整    Type:string
 * @param pSQLClaused    SQL句                   Type:Array
 * @param pSQLLabel      GridField顯示的Label     Type:Array
 * @param pQBEField      查詢欄位                 Type:Array
 * @param pQBELabel      查詢欄位的Lable          Type:Array
 * @param pHidden      	 需隱藏的欄位順序          Type:Array
 * @param pReturnId      回傳欄位ID               Type:Array
 * @param pWidth         視窗寬度                 Type:Number
 * @param pHeight        視窗高度                 Type:Number
 */
function singleOpenWinHidden(pFileName,pDatabaseCfgId,pSQLClaused,pSQLLabel,pQBEField,pQBELabel,pHidden,pReturnId,pWidth,pHeight){
    
    var tFileName = encodeURI(pFileName);
    var tDatabaseCfgId = pDatabaseCfgId;
    
    var pSQLClauseArry = "";
    for(var i = 0; i < pSQLClaused.length; i++){
        pSQLClauseArry = pSQLClauseArry + pSQLClaused[i] + "~~";
    }
    var tSQLClaused = encodeURIComponent(pSQLClauseArry.substring(0, pSQLClauseArry.length - 2));
    //在encodeURIComponent後將% ( ) 換成 $ ~1~ ~2~ , 進JSP後再自行在進JSP後再自行decodeURIComponent
    tSQLClaused = tSQLClaused.replace(/%/g,"$").replace(/\(/g,"~1~").replace(/\)/g,"~2~").replace(/\./g,"~3~");
    //alert(tSQLClaused);
    var tSQLLabel = encodeURI(pSQLLabel.toString());
    var tQBEField = encodeURI(pQBEField.toString());
    var tQBELabel = encodeURI(pQBELabel.toString());
    var tReturnId = encodeURI(pReturnId.toString());
    var tHidden = encodeURI(pHidden.toString());
    openDialog("/NaNaWeb/CustomOpenWin/" + tFileName + ".jsp?SQLClaused="+tSQLClaused+"&SQLLabel="+tSQLLabel+"&QBEField="+tQBEField+"&QBELabel="+tQBELabel+"&ReturnId="+tReturnId+"&DatabaseCfgId="+tDatabaseCfgId+"&Hiddens="+tHidden+"&prop0=nmt01&prop1=nmt02", pWidth, pHeight, "titlebar,scrollbars,status,resizable");		
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
	if(window.changePageToCustomOpenWin){
		changePageToCustomOpenWin(pDatabaseCfgId,pSQLClaused,pSQLLabel,pQBEField,pQBELabel,pOriginallyData,"multiple");
	}else{
		/*
		var tFileName = encodeURI(pFileName);
		var tDatabaseCfgId = pDatabaseCfgId;
		
		var pSQLClauseArry = "";
		for(var i = 0; i < pSQLClaused.length; i++){
			pSQLClauseArry = pSQLClauseArry + pSQLClaused[i] + "~~";
		}
		var tSQLClaused = encodeURIComponent(pSQLClauseArry.substring(0, pSQLClauseArry.length - 2));
		//在encodeURIComponent後將% ( ) 換成 $ ~1~ ~2~ , 進JSP後再自行在進JSP後再自行decodeURIComponent
		tSQLClaused = tSQLClaused.replace(/%/g,"$").replace(/\(/g,"~1~").replace(/\)/g,"~2~").replace(/\./g,"~3~");
		
		var tSQLLabel = encodeURI(pSQLLabel.toString());
		var tQBEField = encodeURI(pQBEField.toString());
		var tQBELabel = encodeURI(pQBELabel.toString());
		var tOriginallyData = encodeURI(pOriginallyData.toString());

		openDialog("/NaNaWeb/CustomOpenWin/" + tFileName + ".jsp?SQLClaused="+tSQLClaused+"&SQLLabel="+tSQLLabel+"&QBEField="+tQBEField+"&QBELabel="+tQBELabel+"&DatabaseCfgId="+tDatabaseCfgId+"&OriginallyData="+tOriginallyData+"&prop0=nmt01&prop1=nmt02", pWidth, pHeight, "titlebar,scrollbars,status,resizable");
		*/
		CustomDataChooser.openWin(pFileName, pDatabaseCfgId, pSQLClaused, pSQLLabel, pQBEField, pQBELabel, pOriginallyData, pWidth, pHeight, "");
	}
    
}


/**
 * 隱藏一個或多個欄位邊框
 * @param pId 欄位名稱 - 以","分隔
 */
function hideFrame(pId){
    var tIdArray = pId.split(",");
    for(var i = 0; i < tIdArray.length; i++){
        document.getElementById(tIdArray[i]).style.borderStyle = "none";
    }
}

/**
 * 將一個或多個欄位邊框變成下框線
 * @param pId 欄位名稱 - 以","分隔
 */
function changeFrameUnderTheBorder(pId){
    var tIdArray = pId.split(",");
    for(var i = 0; i < tIdArray.length; i++){
        document.getElementById(tIdArray[i]).style.borderStyle = "none";
		document.getElementById(tIdArray[i]).style.borderBottom = "1px solid #000000";
    }
}

/**
 * 隱藏一個或多個欄位
 * @param pId 欄位名稱 - 以","分隔
 */
function hideColumn(pId){
    var tIdArray = pId.split(",");
    for(var i = 0; i < tIdArray.length; i++){
		var tGrid = tIdArray[i].indexOf(":");//判斷該元件是否為grid
		if (tGrid == -1) {//非grid
			var tDocument = document.getElementById(tIdArray[i]);//將元件存成變數, 增加效能
			if (tDocument != null) {
				if (tDocument.type != "select-one") {//為了解決dropDown再列印表單會變_txt
					tDocument.style.display = "none";
				}
				else{
					if(document.getElementById(tIdArray[i] + "_txt") == null){//為了解決dropDown再列印表單會變_txt
						tDocument.style.display = "none";
					}
					else{
						document.getElementById(tIdArray[i] + "_txt").style.display = "none";
					}
				}
			}
			else if(document.getElementById(tIdArray[i] + "_txt") != null){//為了考慮Date元件
				document.getElementById(tIdArray[i] + "_txt").style.display = "none";
			}
		}
		else{//grid
			var tGrdArray = tIdArray[i].split(":");
			if (document.getElementById(tGrdArray[1] + "Body") == null) {//非列印表單
				var tTrueGrid = eval(tGrdArray[1] + "Obj");
				tTrueGrid.setStyle("display","none"); 
			}
			else{//印列表單
				document.getElementById(tGrdArray[1] + "_div").style.display = "none";
			}
		}
    }
}

/**
 * 顯示一個或多個欄位
 * @param pId 欄位名稱 - 以","分隔
 */
function showColumn(pId){
    var tIdArray = pId.split(",");
    for(var i = 0; i < tIdArray.length; i++){
		var tGrid = tIdArray[i].indexOf(":");//判斷該元件是否為grid
		if (tGrid == -1) {//非grid
			var tDocument = document.getElementById(tIdArray[i]);//將元件存成變數, 增加效能
			if (tDocument != null) {
				if (tDocument.type != "select-one") {//為了解決dropDown再列印表單會變_txt
					tDocument.style.display = "";
				}
				else{
					if(document.getElementById(tIdArray[i] + "_txt") == null){//為了解決dropDown再列印表單會變_txt
						tDocument.style.display = "";
					}
					else{
						document.getElementById(tIdArray[i] + "_txt").style.display = "";
					}
				}
			}
			else if(document.getElementById(tIdArray[i] + "_txt") != null){//為了考慮Date元件
				document.getElementById(tIdArray[i] + "_txt").style.display = "";
			}
		}
		else{//grid
			var tGrdArray = tIdArray[i].split(":");
			if (document.getElementById(tGrdArray[1] + "Body") == null) {//非列印表單
				var tTrueGrid = eval(tGrdArray[1] + "Obj");
				tTrueGrid.setStyle("display",""); 
			}
			else{//印列表單
				document.getElementById(tGrdArray[1] + "_div").style.display = "";
			}
		}
    }
}

/**
 * 刪除一個或多個欄位值
 * @param {Object} pId - 以","分隔
 */
function clearColumnValue(pId){
	var tIdArray = pId.split(",");
    for(var i = 0; i < tIdArray.length; i++){
		if(document.getElementById(tIdArray[i]).type == "checkbox" || document.getElementById(tIdArray[i]).type == "radio"){
			document.getElementById(tIdArray[i]).checked = false;
		}
		else{
			document.getElementById(tIdArray[i]).value = "";
		}
    }
}

/**
 * 去除空白
 * @param {Object} pValue 傳入值
 */
function trim(pValue){
	return pValue.replace(/^[\s]*/gi,"").replace(/[\s]*$/gi,"");
}

/**
 * 欄位檢查 - 檢查是否輸入的是數字
 * @param {Object} pId 元件ID
 * @param {Object} pStatus 是否含小數 true : 含  false : 不含
 * modi by 4182 : 增加轉換Float , 若為整數再轉換為Integer , 以避免溢位問題, 調整符點數的正規表示式以避免科學符號的輸入
 */
function numberCheck(pId,pStatus){
	var tValue = document.getElementById(pId).value;
	var tFoamt = "";
	if(pStatus){
		tFoamt =/^[0-9]+\.?[0-9]*$/.test(tValue);
	}
	else{
		tFoamt = /^[0-9]*$/g.test(tValue);
	}
	if(tValue != ""){
		if(tFoamt){//輸入格式符合
			document.getElementById(pId).value  = parseFloat(document.getElementById(pId).value);
			if(pStatus == false) document.getElementById(pId).value  = parseInt(document.getElementById(pId).value);
			return true;
		}
		else{//輸入格式不符
			alert("輸入格式不符 !");
			document.getElementById(pId).value = "";
			return false;
		}
	}
}

/**
 * 欄位檢查 - 檢查是否輸入的是數字
 * @param {Object} pId 元件ID
 * @param {Object} pNumber 小數點位數 , 預設不限位數
 * @param {Object} pMessage 回傳訊息  , 預設"輸入格式錯誤"
 */
function floatNumbersCheck(pId,pNumber,pMessage){	
	var tValue =  document.getElementById(pId).value;
	if(pMessage == "")pMessage = "輸入格式錯誤";
	var reg = "";
	if (pNumber == "") {
		var reg = new RegExp("/^[0-9]+\.?[0-9]*$/");
	}else{
		var reg = new RegExp("/^[0-9]+\.?[0-9]{0," + pNumber + "}$/");
	}
	if(tValue != ""){
		if(!reg.test(tValue)){
			alert(pMessage);			
			return false;	
		}else{
			document.getElementById(pId).value  = parseFloat(document.getElementById(pId).value);
			return true;
		}
	}	
}


/**
 * 將一個或多個欄位變成指定顏色
 * @param {Object} pId - 以","分隔
 * @param {Object} pColor 顏色
 */
function componentBgColor(pId, pColor){
	var tIdArray = pId.split(",");
    for(var i = 0; i < tIdArray.length; i++){
        document.getElementById(tIdArray[i]).style.backgroundColor = pColor;
    }
}

/**
 * 一個或多個元件disable控制
 * @param {Object} pId
 * @param {Object} pStatus  true - disable   false - enable
 */
function componentDisable(pId, pStatus){
	var tIdArray = pId.split(",");
    for(var i = 0; i < tIdArray.length; i++){
        document.getElementById(tIdArray[i]).disabled = pStatus;
    }
}

/**
 * 四捨五入
 * @param {Object} value 傳入值
 * @param {Object} num 小數點第幾位
 */
function roundMath(value,num){
    return Math.round(parseFloat(value) * Math.pow(10,num)) / Math.pow(10,num);
}

/**
 * 千分位轉換
 * @param {String} pValue 要被轉換的資料
 * @param {Boolean} pTrue true:轉為千分位顯示 false:轉為無千分位。
 */
function setThousandths(pValue, pTrue){
   if(pValue == null){return "";}
	 var tValue = pValue.toString().replace(/,/g, '');
	 var tResult = '';
	 if (tValue.length > 0) {	
		if (pTrue) { //顯示千分位
			re = /(\d{1,3})(?=(\d{3})+(?:$|\.))/g;//千分號
			tValue = eval(tValue).toString();
			tResult = tValue.replace(re, "$1,");
								
			tIndexNum = tResult.indexOf(".");					
			var tNum_1 = "";
			var tNum_2 = "";
			if(tIndexNum != -1){
				var tNum_1 = tResult.substring(0,tIndexNum);
				var tNum_2 = tResult.substring(tIndexNum);
				tNum_2 = tNum_2.replace(/,/g,"");
				tResult = tNum_1 + tNum_2
			}
		}
		else {//不顯示千分位
			tValue = tValue.replace(/,/g, '');
			tResult = eval(tValue).toString();
		}
	}
	return tResult;
}

/**
 * 動態產生dropDown
 * @param pDatabaseCfgId 系統管理員所設定的資料庫名稱,依系統管理員設定不同,而需調整 Type:string
 * @param pComponentId dropDown ID
 * @param pHdnId 隱藏欄位ID
 * @param pSqlClaused SQL句
 */
var tComponentId = "";
var tHdnId = "";
function getDropDownFromSQL(pDatabaseCfgId, pComponentId, pHdnId, pSqlClaused){ 
	DWREngine.setAsync(false);//同步化	
	tComponentId = pComponentId;
	tHdnId = pHdnId;
	ajax_DatabaseAccessor.executeQuery(pDatabaseCfgId, pSqlClaused, null, null, setDropDownFromSQL);      
} 

function setDropDownFromSQL(data){
    if(data.recordValues.length == 0){
        alert("無資料(" + tComponentId + ")!!");
    }
    var arrayCategory = data.recordValues;        
    var arrayCategoryValue ="";
    var arrayCategoryRow=0;        
    while (arrayCategory[arrayCategoryRow] != null){
        arrayCategoryValue = arrayCategoryValue+"'"+arrayCategory[arrayCategoryRow][0]+"'"+":"+"'"+arrayCategory[arrayCategoryRow][1] + "',";
        arrayCategoryRow++; 
    }        
    var arrayFindReqCount = arrayCategoryValue.lastIndexOf(","); //取最後一個逗號的位置        
    var arrayFindReqValue = arrayCategoryValue.substring(0,arrayFindReqCount); //取逗號之前的字串
    var ReqOptionValues=objectEval("{"+arrayFindReqValue+"}"); //加大括號
    DWRUtil.removeAllOptions(tComponentId);  
    DWRUtil.addOptions(tComponentId,ReqOptionValues); //將值寫入畫面	
    
	if(document.getElementById(tHdnId).value == ""){
		document.getElementById(tHdnId).value = document.getElementById(tComponentId).value;
	}
	else{
		document.getElementById(tComponentId).value = document.getElementById(tHdnId).value;
	}
}

/**
 * 動態產生dropDown - 特殊 : 第一筆為請選擇 , 要另存ID跟Name
 * @param pDatabaseCfgId 系統管理員所設定的資料庫名稱,依系統管理員設定不同,而需調整 Type:string
 * @param pComponentId dropDown ID
 * @param pHdnId 隱藏欄位ID
 * @param pSqlClaused SQL句
 */
var tComponentId = "";
var tHdnId = "";
var tTxtId = "";
function getDropDownFromSQLForTxt(pDatabaseCfgId, pComponentId, pHdnId, pTxtId, pSqlClaused){ 
	DWREngine.setAsync(false);//同步化	
	tComponentId = pComponentId;
	tHdnId = pHdnId;
	tTxtId = pTxtId;
	ajax_DatabaseAccessor.executeQuery(pDatabaseCfgId, pSqlClaused, null, null, setDropDownFromSQLForTxt);      
} 

function setDropDownFromSQLForTxt(data){
    var arrayCategory = data.recordValues;        
    var arrayCategoryValue ="'0':'請選擇',";
    var arrayCategoryRow=0;        
    while (arrayCategory[arrayCategoryRow] != null){
        arrayCategoryValue = arrayCategoryValue+"'"+arrayCategory[arrayCategoryRow][0]+"'"+":"+"'"+arrayCategory[arrayCategoryRow][1] + "',";
        arrayCategoryRow++; 
    }        
    var arrayFindReqCount = arrayCategoryValue.lastIndexOf(","); //取最後一個逗號的位置        
    var arrayFindReqValue = arrayCategoryValue.substring(0,arrayFindReqCount); //取逗號之前的字串
    var ReqOptionValues=objectEval("{"+arrayFindReqValue+"}"); //加大括號
    DWRUtil.removeAllOptions(tComponentId);  
    DWRUtil.addOptions(tComponentId,ReqOptionValues); //將值寫入畫面	
    
	if(document.getElementById(tHdnId).value == ""){
		document.getElementById(tHdnId).value = document.getElementById(tComponentId).value;
		document.getElementById(tTxtId).value = document.getElementById(tComponentId).options[document.getElementById(tComponentId).selectedIndex].text;//name
	}
	else{
		document.getElementById(tComponentId).value = document.getElementById(tHdnId).value;
	}
}

/*
 * 將字串轉成物件
 */
function objectEval(text){
    text = text.replace(/\n/g, " ");
    text = text.replace(/\r/g, " ");
    if (text.match(/^\s*\{.*\}\s*$/)){
        text = "[" + text + "][0]"; 
    }   
    return eval(text);
}

function ParseXml(pData){
	var xmlDoc = "";
	//回傳String,parseXML
	if (window.ActiveXObject) {   
        xmlDoc=new ActiveXObject("Microsoft.XMLDOM");   
        xmlDoc.async="false";   
        xmlDoc.loadXML(pData.toString());  
    } else {   
        parser=new DOMParser();   
        xmlDoc=parser.parseFromString(pData,"text/xml");  
    }
    return xmlDoc;
}


