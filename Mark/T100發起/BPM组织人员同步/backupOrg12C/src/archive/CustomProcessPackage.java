package archive;

import java.util.List;
import java.util.ArrayList;

public class CustomProcessPackage extends ProcessPackage{
    public static String CUSTOM_PROCESS_PACKAGE_TABLE_NAME = "CustomProcessPackage";
    public String getTableName(){
        return CustomProcessPackage.CUSTOM_PROCESS_PACKAGE_TABLE_NAME;
    }
    public CustomProcessPackage(String pOID) {
        super(pOID);
    }
    public List generateDeleteCommands(){
        List tRtn = new ArrayList();
        StringBuffer tDeleteCommand = new StringBuffer();
        tDeleteCommand.append("Delete From ").append(this.CUSTOM_PROCESS_PACKAGE_TABLE_NAME).append(" Where OID = '").append(
                this.getOID()).append("';");
        tRtn.add(tDeleteCommand.toString());
        tRtn.addAll(super.generateDeleteCommandsWithoutPackage());
        return tRtn;
    }
}