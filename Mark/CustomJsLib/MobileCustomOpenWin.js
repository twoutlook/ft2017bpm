function MobileCustomOpenWin(){
	var self=this;
	var _gReturnId = null; //回傳至表單頁面的元件ID(單選開窗時為Array，多選時為String)
	var _gQBEField = null; //查詢欄位
	var _gSQLClaused = null; //SQL查詢語句
	var _gDatabaseCfgId = null; //DB的ID
	var _gQBELabel = null; //欄位的Label 
	var _gLblCurrentPage = null;//目前頁數
	var _gLblTotalPage = null;//總頁數
	var _gLblDataSize = null;//總資料量
	var _gSelPageSize = null;//一頁顯示筆數
	var _gSQLLabel = null;//GridField顯示的Label
	var _gChooseData = null; //客製開窗撈回的資料.
	var _gDataChooseType = null;//開窗的類型(multiple\single)
	var _gLocalStorageIndex=null;//本地儲存的索引
	var _gLocalLastUpdateTime=null;//最後更新時間
	var _gLocalStorage=__LocalStorage;
	var _gSqlId = null;
	var _gParam = null;
	var _gParamType = null;
	var _gParamKey = null;
	var _gParamValue = null;
	var _gWinKind = null;

	var _gSelectItems = null;//多選開窗的結果.
	//var _gCkbSelectAll = null;//多選開窗時，全選的按鈕.
	
	/*
		0:正常客製開窗回傳 [['11','22'],['33','44']]
		1:老闆電信開窗回傳 0001_人名1_xxxx;0002_人名2_ooooo
	*/
	var _gMultiRtnType = 0; //多選開窗回傳值的格式.
	
	/**
	  * 切換至客製開窗頁面
	  */
	MobileCustomOpenWin.prototype.changePageToCustomOpenWin=function(pDatabaseCfgId,pSQLClaused,pSQLLabel,pQBEField,pQBELabel,pReturnId,pDataChooseType){
		
		_gDataChooseType = pDataChooseType;//開窗的類型(multiple\single)
		_gReturnId = pReturnId;//回傳至表單頁面的元件ID(單選開窗時為Array，多選時為String)
		_gQBEField = pQBEField;//查詢欄位
		_gQBELabel = pQBELabel;//欄位的Label 
		_gSQLLabel = pSQLLabel;//GridField顯示的Label
		_gDatabaseCfgId = pDatabaseCfgId;//DB的ID
		_gWinKind = "sql";
		
		//SQL查詢語句
		if(typeof(pSQLClaused)=="string"){
			_gSQLClaused = pSQLClaused;
		}else{
			_gSQLClaused = pSQLClaused[0];
		}
		
		initData();
		
		//執行SQL撈取資料.
		setTimeout(function(){
			executeQueryInitial(_gSQLClaused);
		},20);

	}
	
	/**
	  * 切換至客製開窗頁面
	  */
	MobileCustomOpenWin.prototype.changePageToCustomOpenWinByParmater =function(pSqlId,gParamKey,gParamValue,pSQLLabel,pQBEField,pQBELabel,pReturnId,pDataChooseType){

		
		_gDataChooseType = pDataChooseType;//開窗的類型(multiple\single)
		_gReturnId = pReturnId;//回傳至表單頁面的元件ID(單選開窗時為Array，多選時為String)
		_gQBEField = pQBEField;//查詢欄位
		_gQBELabel = pQBELabel;//欄位的Label 
		_gSQLLabel = pSQLLabel;//GridField顯示的Label
		_gSqlId = pSqlId;
		_gParamKey = gParamKey;
		_gParamValue =gParamValue;
		_gWinKind = "sqlId_And_Param";
		
		initData();
		
		//執行SQL撈取資料.
		setTimeout(function(){
			executeQueryInitial(_gSQLClaused);
		},20);
		
	}	
	
	
	MobileCustomOpenWin.prototype.changePageToCustomOpenWinBySqlId =function(pSqlId,gParam,gParamType,pSQLLabel,pQBEField,pQBELabel,pReturnId,pDataChooseType){
		
		_gDataChooseType = pDataChooseType;//開窗的類型(multiple\single)
		_gReturnId = pReturnId;//回傳至表單頁面的元件ID(單選開窗時為Array，多選時為String)
		_gQBEField = pQBEField;//查詢欄位
		_gQBELabel = pQBELabel;//欄位的Label 
		_gSQLLabel = pSQLLabel;//GridField顯示的Label
		_gSqlId = pSqlId;
		_gParam = gParam;
		_gParamType =gParamType;
		_gWinKind = "sqlId";
		
		initData();
		
		//執行SQL撈取資料.
		setTimeout(function(){
			executeQueryInitial(_gSQLClaused);
		},20);
		
	}	
	
	function initData(){

		_gLblCurrentPage = __jQuery("#_lblCurrentPage");//目前頁數
		_gLblTotalPage = __jQuery("#_lblTotalPage");//總頁數
		_gLblDataSize = __jQuery("#_lblDataSize");//總資料量
		_gSelPageSize = __jQuery("#_selPageSize");//一頁顯示筆數

		// 切換頁面前先清空開窗畫面上資料_20160721_Pinchi
		var tMainList = document.getElementById("mainDataList");
		__jQuery(tMainList).empty();
		
		//切換頁面.
		__jQuery.mobile.pageContainer.pagecontainer("change","#dataChooser");
		
		//產生搜尋條件.
		generateQueryCriteria();
		
		//產生表格的TITLE.
		__jQuery("#dataChooserTitle").empty();
		
		//多選窗.
		if(_gDataChooseType=="multiple"){
			_gSelectItems = new Map();
			__jQuery("#divMultiSubmit").css({"display":""});//顯示多選窗使用的「確認」按鈕
			__jQuery("#divSelectAll").css({"display":""});//顯示多選窗使用的「全選」按鈕	
		}else{
			__jQuery("#divMultiSubmit").css({"display":"none"});
			__jQuery("#divSelectAll").css({"display":"none"});
		}

		//重新載入「一頁顯示幾筆」的下拉式選單.
		__jQuery("#_selPageSize").html(
			'<option value="10" selected="selected">10</option>'+
			'<option value="20">20</option>'+
			'<option value="50">50</option>'+
			'<option value="100">100</option>'
		);
		__jQuery("#_selPageSize").selectmenu('refresh');
	
		//執行SQL撈取資料.
		setTimeout(function(){
			executeQueryInitial(_gSQLClaused);
		},20);

	}
	
	
	/**
	  * 全選資料.
	  */
	function selectAll(){
		__jQuery("#mainDataList").find("input[name='_ckbItem']").each(function(index, ele){
			var tIndex = parseInt(ele.id.replace("_ckbItem",""));
			_gSelectItems.put(_gChooseData.recordValues[tIndex],""); //多選客製-搜尋勾選修正 105.07 WenFeng
			ele.checked = true;
		});
		this.checked = true;	
	}
	/**
	  * 全不選資料.
	  */
	function notSelectAll(){
		__jQuery("#mainDataList").find("input[name='_ckbItem']").each(function(index, ele){
			var tIndex = parseInt(ele.id.replace("_ckbItem",""));
			_gSelectItems.remove(tIndex);
			ele.checked = false;
		});
	}
	
	//產生搜尋條件.
	function generateQueryCriteria(){
			
		__jQuery("#queryCriteria").empty();
		
		for(var i=0;i<_gQBELabel.length;i++){
		
			var tr = document.createElement("tr");
			tr.style.textAlign = "center";
			
			var td = document.createElement("td");
			td.style.backgroundColor = "#white";
			td.innerHTML = _gQBELabel[i];
			tr.appendChild(td);
			
			td = document.createElement("td");
			td.style.backgroundColor = "#white";
			tr.appendChild(td);
			
			var input = document.createElement("input");
			input.setAttribute("id","_query_"+i);
			td.appendChild(input);
			
			document.getElementById("queryCriteria").appendChild(tr);
			
			__jQuery("#"+"_query_"+i).textinput();
		}
		
	}
	
	/**
	  * 執行SQL查詢.
	  */
	function executeQuery(pSQL){
		//產生所有資料.
		_gLocalLastUpdateTime=null;
		DWREngine.setAsync(false);
		
		if(_gWinKind=="sql"){
			ajax_DatabaseAccessor.executeQuery(_gDatabaseCfgId, pSQL, null, null,executeQueryCallBack);
		}else if(_gWinKind=="sqlId"){
			var tParam = new Array();
			for(var i=0;i<_gParam.length;i++){
				tParam.push(_gParam[i]);
			}
			
			var tParamType = new Array();
			for(var i=0;i<_gParamType.length;i++){
				tParamType.push(_gParamType[i]);
			}
			
			var tCriteriaId = new Array();
			var tCriteriaValue = new Array();
			for(var i=0;i<_gQBEField.length;i++){
				var tField = _gQBEField[i];
				var tValue = document.getElementById("_query_"+i).value;
				tCriteriaId.push(tField);
				tCriteriaValue.push(tValue);
			}
			
			ajax_DatabaseAccessor.queryForCustomDataChooser(_gSqlId, tParam, tParamType, tCriteriaId, tCriteriaValue, executeQueryCallBack);
						
		}else if(_gWinKind == "sqlId_And_Param"){
			var tParamKey = new Array();
			for(var i=0;i<_gParamKey.length;i++){
				tParamKey.push(_gParamKey[i]);
			}
			
			var tParamValue = new Array();
			for(var i=0;i<_gParamValue.length;i++){
				tParamValue.push(_gParamValue[i]);
			}
			
			var tCriteriaId = new Array();
			var tCriteriaValue = new Array();
			for(var i=0;i<_gQBEField.length;i++){
				var tField = _gQBEField[i];
				var tValue = document.getElementById("_query_"+i).value;
				tCriteriaId.push(tField);
				tCriteriaValue.push(tValue);
			}
			ajax_DatabaseAccessor.queryForProductDataChooser(_gSqlId, tParamKey, tParamValue, tCriteriaId, tCriteriaValue, executeQueryCallBack);
		}
		
	}
	

	/**
	  * 執行SQL查詢.
	  */
	function executeQueryInitial(pSQL){
		_gLocalStorageIndex=_gDatabaseCfgId+pSQL;
		// 一進入客制開窗不抓資料也不抓localStorage資料_20160721_Pinchi
		/*
		var tData=getQueryStorage(_gLocalStorageIndex);
		if(tData!=null&&tData.pack!=undefined&&tData.lastUpdate!=undefined){
			_gLocalLastUpdateTime=tData.lastUpdate;
			executeQueryCallBack(tData.pack);
		}else{
			clearQueryStorage(_gLocalStorageIndex);
			executeQuery(pSQL);
		}
		*/
		return ;
	}
	
	function executeQueryCallBack(pData){
		//沒有最後更新時間,代表沒有快取
		if(_gLocalLastUpdateTime==null){
			_gChooseData = pData;
			var tPageSize = parseInt(_gSelPageSize.val());//一頁顯示數量
			var tDataSize = _gChooseData.recordValues.length; //總資料量
			_gLblTotalPage.text(Math.ceil(tDataSize/tPageSize));//計算總頁數
			generateDataChooserTable(0,tPageSize);//建立客製開窗的TABLE
			_gLocalLastUpdateTime=getTimeNow();
			var tLocalStorage={pack:pData,lastUpdate:_gLocalLastUpdateTime};
			setQueryStorage(_gLocalStorageIndex,tLocalStorage);
		}else{
			_gChooseData = pData;
			var tPageSize = parseInt(_gSelPageSize.val());//一頁顯示數量
			var tDataSize = _gChooseData.recordValues.length; //總資料量
			_gLblTotalPage.text(Math.ceil(tDataSize/tPageSize));//計算總頁數
			generateDataChooserTable(0,tPageSize);//建立客製開窗的TABLE			
		}
		setMessageBox(_gLocalLastUpdateTime);
		return ;
	}
	
	//設定本地快取資料
	function setQueryStorage(pKey,pData){
		var tData=JSON.stringify(pData);
		_gLocalStorage._setQueryStorage(pKey,tData);
		return ;
	}
	
	//清除本地快取資料
	function clearQueryStorage(pKey){
		_gLocalStorage._clearQueryStorage(pKey);
		return ;
	}
	
	//取本地快取資料
	function getQueryStorage(pKey){
		var tLocal=_gLocalStorage._getQueryStorage(pKey);
		try{
			var tResult=JSON.parse(tLocal);
		}catch(err){
			var tResult=null;
		}
		return tResult;
	}
	
	function getTimeNow(){
		var timeDate= new Date();
		var tMonth = (timeDate.getMonth()+1) > 9 ? (timeDate.getMonth()+1) : '0'+(timeDate.getMonth()+1);
		var tDate = timeDate.getDate() > 9 ? timeDate.getDate() : '0'+timeDate.getDate();
		var tHours = timeDate.getHours() > 9 ? timeDate.getHours() : '0'+timeDate.getHours();
		var tMinutes = timeDate.getMinutes() > 9 ? timeDate.getMinutes() : '0'+timeDate.getMinutes();
		var tSeconds = timeDate.getSeconds() > 9 ? timeDate.getSeconds() : '0'+timeDate.getSeconds();
		return timeDate= timeDate.getFullYear()+'/'+ tMonth +'/'+ tDate +' '+ tHours +':'+ tMinutes +':'+ tSeconds;
	}
	
	function setMessageBox(pString){
		__jQuery('#dataChooser').find('span[name="messageBox"]').text(pString);
		return ;
	}
	

	/**
	  * 下查詢條件搜尋.
	  */
	function queryByCriteria(){
		
		if(_gWinKind=="sql"){
			//把SQL註冊後台的開窗
			//SQL查詢邏輯參考至SingleOpenWin.jsp
			var tempLowerSqlClause = _gSQLClaused.toLowerCase();//將SQL語句暫存並轉換為小寫切割用
			// 組查詢條件
			var tFindConditions="";
			//var tSqlOrderby = document.getElementById("sqlOrder").title ;
			for(var i=0 ; i<_gQBEField.length ; i++){
				var tempHead = " and "+_gQBEField[i]+" like '%";
				var tempValue = document.getElementById("_query_"+i).value;
				tempValue.replace(/\'/g,"’");
				re = /^[ ]+|[ ]+$/g;
				var newtempValue = tempValue.replace(re, "");
				var tempEnd = "%' ";
				if(document.getElementById("_query_"+i).value!=""){
					tFindConditions+=tempHead+newtempValue+tempEnd;
				}
			}

			var tFilter = tempLowerSqlClause.lastIndexOf("where");
			if(tFilter != -1 && tempLowerSqlClause.indexOf("union") == -1){
				tFindConditions = tFindConditions;
			}
			else{
				tFindConditions=" where -1 = -1"+tFindConditions;
			}

			var sqlClause= _gSQLClaused;
	  
			// 如果使用者在開窗介面中點選排序，則tSqlOrderBy會組語法，這時要把我們原本寫在sql語句中的order by語法拿掉
			if (tempLowerSqlClause.indexOf("order by") != -1 ){
				sqlClause = sqlClause.substring(0, tempLowerSqlClause.indexOf("order by"));
				tempLowerSqlClause = sqlClause.toLowerCase();
			}
	  
			// 如果使用者在開窗介面中自定條件，則要先把sql語法分成 select…where段 跟 order by段，再將自定條件加到 select…where 跟 order by中
			if (tFindConditions.length > 0 && tempLowerSqlClause.indexOf("order by") != -1){
				var tSql = sqlClause.substring(0, tempLowerSqlClause.indexOf("order by"));
				var tOrderBy = sqlClause.substring(tempLowerSqlClause.indexOf("order by"), sqlClause.length+1);
				sqlClause = tSql + tFindConditions + " " + tOrderBy;
			}else {
				sqlClause = sqlClause + tFindConditions;
			}
			executeQuery(sqlClause);
		}else if(_gWinKind == "sqlId"){
			executeQuery();
		}else if(_gWinKind == "sqlId_And_Param"){
			executeQuery();
		}
		
		return ;
	}
	
	
	
	/**
	  * 清空搜尋條件.
	  */
	function clearQueryFields(){
		for(var i=0;i<_gQBEField.length;i++){
			__jQuery("#_query_"+i).val("");
		}
	}
	
	/**
	  * 更改每頁顯示筆數
	  */
	function _changeRecoredSizePerPage(){
		var tNewPageSize = parseInt(_gSelPageSize.val());//希望一頁的筆數
		var tDataSize = _gChooseData.recordValues.length; //總資料量
		_gLblTotalPage.text(Math.ceil(tDataSize/tNewPageSize));
		generateDataChooserTable(0,tNewPageSize);//建立客製開窗的TABLE
	}

	
	/**
	  * 建立客製開窗的資料.
	  */
	function generateDataChooserTable(pStart, pEnd){
	
		var tCurrentPage = parseInt(_gLblCurrentPage.text());//目前頁數
		var tPageSize = parseInt(_gSelPageSize.val());//一頁顯示數量
		var tDataSize = _gChooseData.recordValues.length; //總資料量
		
		//更新資料總筆數
		_gLblDataSize.text(tDataSize);
		
		var tMainList = document.getElementById("mainDataList");
		__jQuery(tMainList).empty();//清空資料.

		if(tDataSize>0){
			for(i=pStart;i<pEnd;i++){

				if(i<tDataSize){
					var tLi = document.createElement("li");
					tMainList.appendChild(tLi);
					
					var tTable = document.createElement("table");
					tTable.style.width="100%";
					tLi.appendChild(tTable);
					
					if(_gDataChooseType == "single"){
						__jQuery(tTable).click(function() {
						
							//將點選的值帶回表單畫面.
						    for(m=0;m<_gReturnId.length;m++){
								for(j=0;j<this.rows.length;j++){
									document.getElementById(_gReturnId[m]).value = this.rows[m].cells[1].innerHTML;
								}
							}
							//回前一頁.
							__jQuery.mobile.back();
							 
							//開窗完後成呼叫此方法.
							try{
								checkPointOnClose(_gReturnId[0]);
							}catch(err){
								//頁面無此方法，就不執行.
							}
						});
					}
					var tBody = document.createElement("tbody");
					tTable.appendChild(tBody);
					
					if(_gDataChooseType == "multiple"){
						var tTr = document.createElement("tr");
						__jQuery(tTr).click(function(){
							var tCheckBox = __jQuery(this).find("input[type='checkbox']");
							var tIndex = parseInt(tCheckBox.attr("id").replace("_ckbItem",""));
						
							
							if (__jQuery(tCheckBox).is(':checked')) {
								_gSelectItems.remove(_gChooseData.recordValues[tIndex]); //多選客製-搜尋勾選修正 105.07 WenFeng
								tCheckBox.prop('checked', false);
							}else{
								_gSelectItems.put(_gChooseData.recordValues[tIndex],""); //多選客製-搜尋勾選修正 105.07 WenFeng
								tCheckBox.prop('checked', true);
							}
							
						});
						
						tBody.appendChild(tTr);
						
						var tTd1 = document.createElement("td");
						tTd1.style.width = "20px";
						tTd1.style.padding = "0px";
						tTd1.style.margin = "0px";
						tTr.appendChild(tTd1);
						
						var tTd2 = document.createElement("td");
						tTr.appendChild(tTd2);
						
						var tCheckBox = document.createElement('input');
						tCheckBox.name="_ckbItem";
						tCheckBox.value="_ckbItem"+i;
						tCheckBox.id="_ckbItem"+i;
						tCheckBox.type="checkbox";
						tCheckBox.setAttribute("data-role","none");
						
						//多選客製-搜尋勾選修正 105.07 WenFeng
						var tKeys = _gSelectItems.getKeys();
						for(var k=0;k<tKeys.length;k++){						
							if(tKeys[k] == String(_gChooseData.recordValues[i])){ 
								tCheckBox.checked = "checked";
								break;
							}
						}

						__jQuery(tCheckBox).click(function(){
							var tIndex = parseInt(this.id.replace("_ckbItem",""));
							if (__jQuery(this).is(':checked')) {
								_gSelectItems.put(_gChooseData.recordValues[tIndex],""); //多選客製-搜尋勾選修正 105.07 WenFeng
							}else{
								_gSelectItems.remove(_gChooseData.recordValues[tIndex]); //多選客製-搜尋勾選修正 105.07 WenFeng
							}
						});

						tTd1.appendChild(tCheckBox);
						
						var tInnerTable = document.createElement("table");
						
						tTd2.appendChild(tInnerTable);
						
						var tInnerBody = document.createElement("tbody");
						tInnerTable.appendChild(tInnerBody);
					
						for(var j=0;j<_gSQLLabel.length;j++){
							var tTr = document.createElement("tr");
							tInnerTable.appendChild(tTr);
							
							var tTd1 = document.createElement("td");
							tTd1.innerHTML = _gSQLLabel[j];
							tTr.appendChild(tTd1);
							
							var tTd2 = document.createElement("td");
							tTd2.innerHTML = _gChooseData.recordValues[i][j];
							tTr.appendChild(tTd2);
							
						}
					}else{
						var tTr = document.createElement("tr");				
						for(var j=0;j<_gSQLLabel.length;j++){
							var tTr = document.createElement("tr");
							tBody.appendChild(tTr);
							
							var tTd1 = document.createElement("td");
							tTd1.innerHTML = _gSQLLabel[j];
							tTr.appendChild(tTd1);
							
							var tTd2 = document.createElement("td");
							tTd2.innerHTML = _gChooseData.recordValues[i][j];
							tTr.appendChild(tTd2);
							
						}				
					}
				}
			}	
			__jQuery(tMainList).listview('refresh');	
		}
		return ;
	}
	
	
	
	/**
	  * 取得所有多選的結果.
	  */
	function getItems(){
	
		//回傳多選格式
		//[['a','b','c'],['d','e','f']]
		
		var tRtn = "";
		if(_gMultiRtnType==0){
			//正常客製開窗回傳.
			
			tRtn = "[";
			var tKeys = _gSelectItems.getKeys();
			for(var i=0;i<tKeys.length;i++){
				if(i>0){
					tRtn = tRtn + ",";
				}
				var tRow = tKeys[i]; //多選客製-搜尋勾選修正 105.07 WenFeng

				tRtn = tRtn + "[";
				for(var j=0;j<tRow.length;j++){
					var tValue = tRow[j];
					if(j!=0){
						tRtn = tRtn + ",";
					}
					tRtn = tRtn + "'"+ tValue + "'";
				}
				tRtn = tRtn + "]";
			}
			
			tRtn = tRtn + "]";
			
			if(tRtn=="[]"){
				tRtn = "";
			}
		}else if(_gMultiRtnType==1){
			
			//老闆電信專用回傳格式.
			var tKeys = _gSelectItems.getKeys();
			for(var i=0;i<tKeys.length;i++){
				var tIndex = tKeys[i];
				var tRow = _gChooseData.recordValues[tIndex];
				for(var j=0;j<tRow.length;j++){
					var tValue = tRow[j];
					
					if(j!=0){
						tRtn = tRtn + "_";
					}
					
					tRtn = tRtn + tValue;
					
					if(j==(tRow.length-1)){
						tRtn = tRtn + ";";
					}
				}
			}
			
			
			
		}
		
		__jQuery("#"+_gReturnId).val(tRtn);
		
		
		//回前一頁.
		__jQuery.mobile.back();
		
		//開窗完後成呼叫此方法.
		try{
			checkPointOnClose(_gReturnId);
		}catch(err){
			//頁面無此方法，就不執行.
		}
		return ;	
	}
	
	/**
	* 移動到第X頁.
	*/
	function changePage(pObj){
		
		systemMessageShow();//增加loading...

		var tCurrentPage = parseInt(_gLblCurrentPage.text());//目前頁數.
		var tPerPageSize = parseInt(_gSelPageSize.val());//一頁顯示數量.
		var tDataSize = _gChooseData.recordValues.length; //總資料量.
		
		var tStart = tCurrentPage*tPerPageSize;
		var tMaxIndex = (tCurrentPage+1)*tPerPageSize;
		var tEnd = 0;
		
		var tTargetPage = 0;	
		if(pObj=="pre"){//前一頁.
			tStart = (tCurrentPage-2)*tPerPageSize;
			tMaxIndex = (tCurrentPage-1)*tPerPageSize;
			tTargetPage = tCurrentPage-1;
		}else if(pObj=="next"){ //下一頁.
			tStart = tCurrentPage*tPerPageSize;
			tMaxIndex = (tCurrentPage+1)*tPerPageSize;
			tTargetPage = tCurrentPage+1;
		}else if(pObj=="first"){//第一頁.
			tStart = 0;
			tMaxIndex = tPerPageSize;
			tTargetPage = 1;
		}else if(pObj=="last"){ //最後一頁.
			var tLastPage = Math.ceil(tDataSize/tPerPageSize);
			tStart = (tLastPage-1)*tPerPageSize;
			tMaxIndex = tLastPage*tPerPageSize;
			tTargetPage = tLastPage;
		}
		
		if(tStart>=0 && (tMaxIndex-tDataSize)<tPerPageSize){
			_gLblCurrentPage.text(tTargetPage);
			generateDataChooserTable(tStart, tMaxIndex);
		}
		
		setTimeout(function(){
			systemMessageHide();
		},200);
	}
	
	
	/**
	  * 初始化客製開窗的頁面.
	  */
	MobileCustomOpenWin.prototype.initMobileCustomOpenWinPage = function(){

		//客製開窗的左移手勢.
		__jQuery("#dataChooser").on("swipeleft",function(){
			changePage("next");
		});
		
		//客製開窗的右移手勢.
		__jQuery("#dataChooser").on("swiperight",function(){
			changePage("pre");
		});
		
		
		//更改每一頁顯示筆數.
		__jQuery("#_selPageSize").on("change",function(){
			_changeRecoredSizePerPage();
		});
		
		//跳到第一頁.
		__jQuery("#_cuzOpenWinToFirstPage").on("click",function(){
			changePage('first');
		});
		
		//跳到下一頁.
		__jQuery("#_cuzOpenWinToNextPage").on("click",function(){
			changePage('next');
		});
		
		//跳到前一頁.
		__jQuery("#_cuzOpenWinToPrePage").on("click",function(){
			changePage('pre');
		});
		
		//跳到最後.
		__jQuery("#_cuzOpenWinToLastPage").on("click",function(){
			changePage('last');
		});
		
		//多選時，按下確認.
		__jQuery("#_cuzOpenWinGetItems").on("click",function(){
			getItems();
		});
		
		
		//全選.
		__jQuery("#_cuzOpenWinSelectAll").on("click",function(){
			selectAll();
		});
		
		//全不選.
		__jQuery("#_cuzOpenWinNotSelectAll").on("click",function(){
			notSelectAll();
		});
		
		//根據查詢條件開始搜尋
		__jQuery("#_cuzOpenWinQuery").on("click",function(){
			queryByCriteria();
		});
		
		//清空搜尋條件.
		__jQuery("#_cuzOpenWinClearQueryFields").on("click",function(){
			clearQueryFields();
		});
	}
	
	return self;
}