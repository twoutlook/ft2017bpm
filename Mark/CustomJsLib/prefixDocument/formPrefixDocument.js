/*
 * 元件定義檔 - TIPTOP銷假單
 */
function getResumptionFormPrefix(){
  var tArray = []
  tArray[0] = "Block_Grid" + "," + "Label42,grid:LeaveGrid";
  tArray[1] = "Block_View" + "," + "Label41,lbl_cqg03,cqg03,cpj02,Label86,LeaveNo,lbl_cqg04,cqg04,cqg041,lbl_cqg06,cqg06,lbl_cqg05,cqg05,cqg051,lbl_cqg08,cqg08,Label60,Label61,RESUMPTION_REASON";

 
  for(var i = 0; i < tArray.length; i++){
      tArray[i] = tArray[i].split(",");
  }
  return tArray;	
}

