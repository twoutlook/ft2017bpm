  

table {
    border-collapse:collapse;
    width:100%;
    max-width:700px;
    min-width:400px;
    text-align:center;
}

caption {
    caption-side:bottom;
    font-weight:bold;
    font-style:italic;
    margin:4px;
}

table,th, td {
    border: 1px solid gray;
}

th, td {
    height: 24px;
    padding:4px;
    vertical-align:middle;
}

th {
    background-image:url(table-shaded.png);
}

.rowtitle {
    font-weight:bold;
}


    <script>  
    function printToExcel(html) {  
      try{  
        var ExApp = new ActiveXObject("Excel.Application")  
        var ExWBk = ExApp.workbooks.add()  
        var ExWSh = ExWBk.worksheets(1)  
        ExApp.DisplayAlerts = false  
        ExApp.visible = true  
      }catch(e){  
        alert("您所设定的安全级别太高，或者您的电脑没有安装Microsoft Excel软件！")  
        return false  
      }  
      window.clipboardData.setData("Text", html);    
      ExWBk.worksheets(1).Paste;  
      ExWBk.worksheets(1).Columns.AutoFit;  
      ExWBk.worksheets(1).Rows.AutoFit;  
    }  
      
      function   exportToExcel(table)      
      {   
          if(confirm("确认要导出吗?")){  
          var   oXL   =   new   ActiveXObject("Excel.Application");       
          var   oWB   =   oXL.Workbooks.Add();    
          var   oSheet   =   oWB.ActiveSheet;   
          var   hang   =   table.rows.length;       
          var   lie   =   table.rows(0).cells.length;         
          for   (i=0;i<hang;i++)    
          {    
          for   (j=0;j<lie;j++)    
          {    
          oSheet.Cells(i+1,j+1).Value   =   table.rows(i).cells(j).innerText;    
          }       
          }    
          oXL.Visible   =   true;    
          oXL.UserControl   =   true;         
          }  
      }   
    if(confirm("是否导出表格数据为 Excel?")) {  
        printToExcel(dataArea.innerHTML);  
    }  
    </script>  
    <!-- 以下为数据区 -->  
    <div id="dataArea">  
    <font color=red>test表</font>  
        <table border="1"  cellpadding="0" style="border-collapse: collapse; " bordercolor="#000000">  
    <tbody>  
          <tr><td>1</td><td>1-1</td><td>1-2</td></tr>  
        <tr><td>2</td><td>2-1</td><td>2-2</td></tr>  
        <tr><td>3</td><td>3-1</td><td>3-2</td></tr>  
        <tr><td>4</td><td>4-1</td><td>4-2</td></tr>  
    </tbody>   
      
    </table>   
      
    </div>  