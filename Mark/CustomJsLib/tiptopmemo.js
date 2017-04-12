/**
 * @author Lalaling
 */
 
/**
 * EFGP與TIPTOP整合之表單備註	
 * @param pLabelArray1 備註欄位(Array)
 * @param pLabelArray2  備註資料(Array)
 * @Exception
 */
function openWindTipTop(pLabelArray1,pLabelArray2){
	var tLabelArray1 = encodeURIComponent(pLabelArray1.toString());
	var tLabelArray2 = encodeURIComponent(pLabelArray2.toString());
	
	openDialog("/NaNaWeb/CustomOpenWin/TiptopMemo.jsp?LabelArray1="+tLabelArray1+"&LabelArray2="+tLabelArray2, "700", "400", "titlebar,scrollbars,status,resizable");
	
}