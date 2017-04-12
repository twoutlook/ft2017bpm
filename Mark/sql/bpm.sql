
-- V_EMPE
select U.id EMPE_ID,U.userName EMPE_NAME, FD.functionDefinitionName JOB,OU.id DEPT_ID,OU.organizationUnitName DEPT_NAME,
 SUP.SUP_ID, SUP.SUP_NAME,
 TO_CHAR(U.leaveDate, 'YYYY-MM-DD') LEAVE_DATE
from Users U 
inner Join Functions F on F.occupantOID=U.OID 
inner Join OrganizationUnit OU ON OU.OID=F.organizationUnitOID 
inner Join FunctionDefinition FD ON FD.OID=F.definitionOID
left Join V_SUP SUP ON U.id=SUP.EMPE_ID AND OU.id=SUP.DEPT_ID
where U.leaveDate is null

-- V_EMPE_LEVEL
select a.EMPE_ID,a.EMPE_NAME,a.DEPT_ID,a.DEPT_NAME,a.JOB,a.SUP_ID,a.SUP_NAME,b.LEVELVALUE LEVEL_ID,b.FUNCTIONLEVELNAME LEVEL_NAME,a.LEAVE_DATE from V_EMPE a
LEFT JOIN V_LEVEL b ON b.EMPE_ID=a.EMPE_ID and b.DEPT_ID=a.DEPT_ID

-- V_LEVEL
select U.ID EMPE_ID,U.UserName EMPE_NAME,OU.ID DEPT_ID,OU.ORGANIZATIONUNITNAME DEPT_NAME, fl.functionlevelname,fl.Levelvalue from Users U 
 inner Join Functions F on F.occupantOID=U.OID 
 inner Join OrganizationUnit OU ON OU.OID=F.organizationUnitOID 
 inner Join FunctionDefinition FD ON FD.OID=F.definitionOID 
 inner join functionlevel Fl on fl.oid =F.Approvalleveloid

-- V_SUP
 select  B.id EMPE_ID,B.userName  EMPE_NAME, OrganizationUnit.ID DEPT_ID,OrganizationUnit.ORGANIZATIONUNITNAME DEPT_NAME,
nvl(A.id,C.id) as SUP_ID,nvl(A.username,C.username) as SUP_NAME 
 from Users A , Functions, Users B ,OrganizationUnit , Users C   where 
Functions.SPECIFIEDMANAGEROID = A.OID (+)
and Functions.OCCUPANTOID = B.OID 
--and Functions.isMain = 1 
and OrganizationUnit.managerOID = C.OID (+) 
and OrganizationUnit.OID  = Functions.organizationUnitOID


====================

====================

select U.id EMPE_ID,U.userName EMPE_NAME, FD.functionDefinitionName JOB,OU.id DEPT_ID,OU.organizationUnitName DEPT_NAME,
 SUP.SUP_ID, SUP.SUP_NAME,
 TO_CHAR(U.leaveDate, 'YYYY-MM-DD') LEAVE_DATE
from Users U 
inner Join Functions F on F.occupantOID=U.OID 
inner Join OrganizationUnit OU ON OU.OID=F.organizationUnitOID 
inner Join FunctionDefinition FD ON FD.OID=F.definitionOID
left Join (
 select  B.id EMPE_ID,B.userName  EMPE_NAME, OrganizationUnit.ID DEPT_ID,OrganizationUnit.ORGANIZATIONUNITNAME DEPT_NAME,
nvl(A.id,C.id) as SUP_ID,nvl(A.username,C.username) as SUP_NAME 
 from Users A , Functions, Users B ,OrganizationUnit , Users C   where 
Functions.SPECIFIEDMANAGEROID = A.OID (+)
and Functions.OCCUPANTOID = B.OID 
--and Functions.isMain = 1 
and OrganizationUnit.managerOID = C.OID (+) 
and OrganizationUnit.OID  = Functions.organizationUnitOID)

SUP ON U.id=SUP.EMPE_ID AND OU.id=SUP.DEPT_ID
where U.leaveDate is null

ORDER BY EMPE_ID


====================

====================
select COUNT(EMPE_ID) CNT from

(
select U.id EMPE_ID,U.userName EMPE_NAME, FD.functionDefinitionName JOB,OU.id DEPT_ID,OU.organizationUnitName DEPT_NAME,
 SUP.SUP_ID, SUP.SUP_NAME,
 TO_CHAR(U.leaveDate, 'YYYY-MM-DD') LEAVE_DATE
from Users U 
inner Join Functions F on F.occupantOID=U.OID 
inner Join OrganizationUnit OU ON OU.OID=F.organizationUnitOID 
inner Join FunctionDefinition FD ON FD.OID=F.definitionOID
left Join (
 select  B.id EMPE_ID,B.userName  EMPE_NAME, OrganizationUnit.ID DEPT_ID,OrganizationUnit.ORGANIZATIONUNITNAME DEPT_NAME,
nvl(A.id,C.id) as SUP_ID,nvl(A.username,C.username) as SUP_NAME 
 from Users A , Functions, Users B ,OrganizationUnit , Users C   where 
Functions.SPECIFIEDMANAGEROID = A.OID (+)
and Functions.OCCUPANTOID = B.OID 
--and Functions.isMain = 1 
and OrganizationUnit.managerOID = C.OID (+) 
and OrganizationUnit.OID  = Functions.organizationUnitOID)

SUP ON U.id=SUP.EMPE_ID AND OU.id=SUP.DEPT_ID
where U.leaveDate is null
)

