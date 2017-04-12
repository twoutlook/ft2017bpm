/*
 * 根據傳入的區塊ID將定義檔內該區塊所定義的元件做隱藏
 * 傳入參數: 需隱藏元件的代號Prefix, ex: "Block_A,Block_B"
 */
function hideColumnByPrefix(formPrefixDocument,prefix){
	try{
		var tColumnArray = formPrefixDocument;//元件檔的二維陣列
		var tPrefixArray = prefix.split(",");//將prefix split成array
	
		if(prefix == "ALL"){
			for(var i = 0; i < tColumnArray.length; i++){
				for(var k = 1; k < tColumnArray[i].length; k++){
					var tGrid = tColumnArray[i][k].indexOf(":");//判斷該元件是否為grid
					if(tGrid == -1){
						var tDocument = document.getElementById(tColumnArray[i][k]);//將元件存成變數, 增加效能
						if(tDocument != null){
							document.getElementById(tColumnArray[i][k]).style.display = "none";
						}
					}
					else{
						var tGrdArray = tColumnArray[i][k].split(":");
						if(document.getElementById(tGrdArray[1] + "Body") == null){//非列印表單
							var tTrueGrid = eval(tGrdArray[1] + "Obj");
							tTrueGrid.setStyle("display","none"); 
						}
						else{//印列表單
							document.getElementById(tGrdArray[1] + "_div").style.display = "none";
						}
					}
				}
			}
		}
		else{
			for(var i = 0; i < tColumnArray.length; i++){
				for(var j = 0; j < tPrefixArray.length; j++){//需隱藏元件的代號Array
					if(tColumnArray[i][0] == tPrefixArray[j] ){
						for(var k = 1; k < tColumnArray[i].length; k++){
							var tGrid = tColumnArray[i][k].indexOf(":");//判斷該元件是否為grid
							if (tGrid == -1){
								var tDocument = document.getElementById(tColumnArray[i][k]);//將元件存成變數, 增加效能
								if(tDocument != null){
									document.getElementById(tColumnArray[i][k]).style.display = "none";
								}
							}
							else{
								var tGrdArray = tColumnArray[i][k].split(":");
								if(document.getElementById(tGrdArray[1] + "Body") == null){//非列印表單
									var tTrueGrid = eval(tGrdArray[1] + "Obj");
									tTrueGrid.setStyle("display","none"); 
								}
								else{//印列表單
									document.getElementById(tGrdArray[1] + "_div").style.display = "none";
								}
							}
						}
					}
				}
			}
		}
	}
	catch(Err){
		alert("系統錯誤,請洽系統管理員(元件定義檔有誤-隱藏) !!");
	}
}

/*
 * 根據表單元件代號Prefix將相關元件顯示並位移
 * formPrefixDocument : 移動區塊定義檔Array
 * prefix : 需顯示元件的代號Prefix, ex: "Block_A,Block_B"
 * beginId : 起始位置原件ID
 * intervalValue : 偏移植
 */
function movePrefixByComponent(formPrefixDocument,prefix, beginId, intervalValue){
	moveColumnByPrefix(formPrefixDocument,prefix, deletePX(document.getElementById(beginId).style.top), intervalValue);
}

/*
 * 根據表單元件代號Prefix將相關元件顯示並位移
 * formPrefixDocument : 移動區塊定義檔Array
 * prefix : 需顯示元件的代號Prefix, ex: "Block_A,Block_B"
 * beginValue : 起始位置top
 * intervalValue : 偏移植
 */
function movePrefixByBeginValue(formPrefixDocument,prefix, beginValue, intervalValue){
	moveColumnByPrefix(formPrefixDocument,prefix, beginValue, intervalValue);
}

function moveColumnByPrefix(formPrefixDocument,prefix, beginValue, intervalValue){
	try{
		hideColumnByPrefix(formPrefixDocument,"ALL");//第一步先全部隱藏
		var tColumnArray = formPrefixDocument;//元件檔的二維陣列
		var tPrefixArray = prefix.split(",");//將prefix split成array
		var tBeginValue = beginValue//區塊起始位置
		var tEndValue = "";//
		var tMoveValue = "";//移動距離
		for(var i = 0; i < tColumnArray.length; i++){//元件檔的二維陣列
			for(var j = 0; j < tPrefixArray.length; j++){//需顯示元件的代號Array
				if(tColumnArray[i][0] == tPrefixArray[j] ){
					for(var k = 1; k < tColumnArray[i].length; k++){
						var tGrid = tColumnArray[i][k].indexOf(":");//判斷該元件是否為grid
						if(j == 0){//第一個區塊
							if(tMoveValue == ""){//第一個移動的元件
								//第一個移動的元件需計算移動了多少
								var tFirstTop = deletePX(document.getElementById(tColumnArray[i][k]).style.top);
								tMoveValue = (Number(tFirstTop) - Number(tBeginValue)).toString();
								if(tGrid == -1){
									var tDocument = document.getElementById(tColumnArray[i][k]);//將元件存成變數, 增加效能
									if(tDocument != null){
										document.getElementById(tColumnArray[i][k]).style.display = "";//顯示
										document.getElementById(tColumnArray[i][k]).style.top = beginValue+"px";
									}
								}
								else{
									var tGrdArray = tColumnArray[i][k].split(":");
									if(document.getElementById(tGrdArray[1] + "Body") == null){//非列印表單
										var tTrueGrid = eval(tGrdArray[1] + "Obj");
										tTrueGrid.setStyle("display",""); 
										tTrueGrid.setStyle("top", beginValue);
									}
									else{//印列表單
										document.getElementById(tGrdArray[1] + "_div").style.display = "";//顯示
										document.getElementById(tGrdArray[1] + "_div").style.top = beginValue+"px";
									}
								}
							}
							else{
								if(tGrid == -1){
									var tDocument = document.getElementById(tColumnArray[i][k]);//將元件存成變數, 增加效能
									if(tDocument != null){
										var tOtherTop = deletePX(document.getElementById(tColumnArray[i][k]).style.top);
										document.getElementById(tColumnArray[i][k]).style.display = "";//顯示
										document.getElementById(tColumnArray[i][k]).style.top = (Number(tOtherTop) - Number(tMoveValue)).toString()+"px";
									}
								}
								else{
									var tGrdArray = tColumnArray[i][k].split(":");
									if(document.getElementById(tGrdArray[1] + "Body") == null){//非列印表單
										var tTrueGrid = eval(tGrdArray[1] + "Obj");
										var tOtherTop = deletePX(document.getElementById(tTrueGrid.getId()).style.top);
										tTrueGrid.setStyle("display",""); 
										tTrueGrid.setStyle("top", (Number(tOtherTop) - Number(tMoveValue)).toString());
									}
									else{//印列表單
										var tOtherTop = deletePX(document.getElementById(tGrdArray[1] + "_div").style.top);
										document.getElementById(tGrdArray[1] + "_div").style.display = "";//顯示
										document.getElementById(tGrdArray[1] + "_div").style.top = (Number(tOtherTop) - Number(tMoveValue)).toString()+"px";
									}
									
									
									
								}

								var tOtherHeight = "";
								if(tGrid == -1){
									var tDocument = document.getElementById(tColumnArray[i][k]);//將元件存成變數, 增加效能
									if(tDocument != null){
										tOtherHeight = deletePX(document.getElementById(tColumnArray[i][k]).style.height);
									}
								}
								else{
									var tGrdArray = tColumnArray[i][k].split(":");
									if(document.getElementById(tGrdArray[1] + "Body") == null){//非列印表單
										var tTrueGrid = eval(tGrdArray[1] + "Obj");
										tOtherHeight = deletePX(document.getElementById(tTrueGrid.getId()).style.height);
									}
									else{//印列表單
										tOtherHeight = deletePX(document.getElementById(tGrdArray[1] + "_div").style.height);
									}
																					
								}
								var tTemp = (Number(tOtherTop) - Number(tMoveValue) + Number(tOtherHeight));
								if(tTemp > Number(tEndValue)){//為了取每個區塊的最下面
									tEndValue = tTemp.toString()
								}
							}	
						}
						else{//不是第一個區塊
							if(k == 1){//第一個元件
								if(tGrid == -1){	
									var tDocument = document.getElementById(tColumnArray[i][k]);//將元件存成變數, 增加效能
									if(tDocument != null){
										var tFirstTop = deletePX(document.getElementById(tColumnArray[i][k]).style.top);
										tMoveValue = (Number(tFirstTop) - (Number(tEndValue) + Number(intervalValue))).toString();//新區塊的移動距離
										document.getElementById(tColumnArray[i][k]).style.display = "";//顯示
										document.getElementById(tColumnArray[i][k]).style.top = (Number(tEndValue) + Number(intervalValue)).toString()+"px";	
									}
								}
								else{
									var tGrdArray = tColumnArray[i][k].split(":");
									if(document.getElementById(tGrdArray[1] + "Body") == null){//非列印表單
										var tTrueGrid = eval(tGrdArray[1] + "Obj");
										var tFirstTop = deletePX(document.getElementById(tTrueGrid.getId()).style.top);
										tMoveValue = (Number(tFirstTop) - (Number(tEndValue) + Number(intervalValue))).toString();//新區塊的移動距離
										
										tTrueGrid.setStyle("display",""); 
										tTrueGrid.setStyle("top", (Number(tEndValue) + Number(intervalValue)).toString());
									}
									else{//印列表單
										var tFirstTop = deletePX(document.getElementById(tGrdArray[1] + "_div").style.top);
										tMoveValue = (Number(tFirstTop) - (Number(tEndValue) + Number(intervalValue))).toString();//新區塊的移動距離
										
										document.getElementById(tGrdArray[1] + "_div").style.display = "";//顯示
										document.getElementById(tGrdArray[1] + "_div").style.top = (Number(tEndValue) + Number(intervalValue)).toString()+"px";	
									}
									
								}	
							}
							else{								
								if(tGrid == -1){
									var tDocument = document.getElementById(tColumnArray[i][k]);//將元件存成變數, 增加效能
									if(tDocument != null){
										var tOtherTop = deletePX(document.getElementById(tColumnArray[i][k]).style.top);
										document.getElementById(tColumnArray[i][k]).style.display = "";//顯示
										document.getElementById(tColumnArray[i][k]).style.top = (Number(tOtherTop) - Number(tMoveValue)).toString()+"px";
									}
								}
								else{
									var tGrdArray = tColumnArray[i][k].split(":");	
									if(document.getElementById(tGrdArray[1] + "Body") == null){//非列印表單
										var tTrueGrid = eval(tGrdArray[1] + "Obj");
										var tOtherTop = deletePX(document.getElementById(tTrueGrid.getId()).style.top);
										tTrueGrid.setStyle("display",""); 
										tTrueGrid.setStyle("top", (Number(tOtherTop) - Number(tMoveValue)).toString());
									}
									else{//印列表單
										var tOtherTop = deletePX(document.getElementById(tGrdArray[1] + "_div").style.top);
										document.getElementById(tGrdArray[1] + "_div").style.display = "";//顯示
										document.getElementById(tGrdArray[1] + "_div").style.top = (Number(tOtherTop) - Number(tMoveValue)).toString()+"px";
									}
								}
	
								var tOtherHeight = "";
								if(tGrid == -1){	
									var tDocument = document.getElementById(tColumnArray[i][k]);//將元件存成變數, 增加效能
									if(tDocument != null){
										tOtherHeight = deletePX(document.getElementById(tColumnArray[i][k]).style.height);
									}
								}
								else{
									var tGrdArray = tColumnArray[i][k].split(":");			
									if(document.getElementById(tGrdArray[1] + "Body") == null){//非列印表單
										var tTrueGrid = eval(tGrdArray[1] + "Obj");
										tOtherHeight = deletePX(document.getElementById(tTrueGrid.getId()).style.height);
									}
									else{//印列表單
										tOtherHeight = deletePX(document.getElementById(tGrdArray[1] + "_div").style.height);
									}
								}
								var tTemp = (Number(tOtherTop) - Number(tMoveValue) + Number(tOtherHeight));
								if(tTemp > Number(tEndValue)){//為了取每個區塊的最下面
									tEndValue = tTemp.toString();
								}
							}
						}
					}
				}
			}
		}
		document.getElementById(formId + "_shell").style.height = (Number(tEndValue) + 20).toString();
	}
	catch(err){
		alert("系統錯誤,請洽系統管理員(元件定義檔有誤-顯示) !!");
	}
}

/*
 * 去掉px
 */
function deletePX(oldValue){
	var tOldIndex = oldValue.indexOf("px");
	return oldValue.substring(0,tOldIndex);//去掉px
}