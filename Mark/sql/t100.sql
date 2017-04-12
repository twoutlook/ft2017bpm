

吴楠 ：

  您好！对应的查询SQL如下,请查收，谢谢！

 (6)客户基本资料整理

  SELECT pmaa001 客户编号,pmaal003 客户全名,pmaal004 客户简称,pmaal006 客户全名二

  FROM pmaa_t

  LEFT JOIN pmaal_t ON pmaalent = pmaaent AND pmaal001 = pmaa001 AND pmaal002 = 'zh_CN'

  WHERE pmaaent = 11 AND pmaastus = 'Y'

(7)客户料号对应资料整理

  SELECT pmaostus 状态,pmao001 客户编号,pmaal004 客户简称,pmao002 公司料件编号,imaal003 公司料件品名,

  imaal004 公司料件规格,pmao003 产品特征,imeal003 产品特征说明,pmao004 客户料件编号,pmao009 客户料件品名,

  pmao010 客户料件规格

  FROM pmao_t

  LEFT JOIN pmaal_t ON pmaalent = pmaoent AND pmaal001 = pmao001 AND pmaal002 = 'zh_CN'

  LEFT JOIN imaal_t ON imaalent = pmaoent AND imaal001 = pmao002 AND imaal002 = 'zh_CN'

  LEFT JOIN imeal_t ON imealent = pmaoent AND imeal001 = pmao003 AND imeal002 = 'zh_CN'

  WHERE pmaoent = 11 AND pmaostus = 'Y' AND pmao000 = '2'

(8)产品包装方式

  SELECT xmam001 包装方式,xmaml003 包装说明,xmam004 包装容器,imaal003 品名,imaal004 规格

   FROM xmam_t

   LEFT JOIN xmaml_t ON xmamlent = xmament AND xmaml001 = xmam001 AND xmaml002 = 'zh_CN'

   LEFT JOIN imaal_t ON imaalent = xmament AND imaal001 = xmam004 AND imaal002 = 'zh_CN'

   WHERE xmament = 11
(9)产品检验项目

  SELECT oocq002 检验项目,oocql004 说明,oocqstus 状态

  FROM oocq_t

  LEFT JOIN oocql_t ON oocqlent = oocqent AND oocql001 = oocq001 AND oocql002 = oocq002 AND oocql003 = 'zh_CN'

  WHERE oocqent = 11 AND oocq001 = '1051' AND oocqstus = 'Y'
(10)设备、模具、刀具

   SELECT mrba001 设备编号,mrba004 名称,mrba005 简称

    FROM mrba_t

    WHERE mrbaent = 11 AND mrbasite = '100' AND mrba000 = '1'

 

    SELECT mrba001 模具编号,mrba004 名称,mrba005 简称

    FROM mrba_t

    WHERE mrbaent = 11 AND mrbasite = '100' AND mrba000 = '3'

 

   SELECT mrba001 刀具编号,mrba004 名称,mrba005 简称

    FROM mrba_t

    WHERE mrbaent = 11 AND mrbasite = '100' AND mrba000 = '4'


(11)模具对应的易损件、产品

   SELECT DISTINCT mrbe002,imaal003,imaal004

    FROM mrbe_t LEFT JOIN imaal_t ON imaalent = mrbeent AND imaal001 = mrbe002 AND imaal002 = 'zh_CN'，mrba_t

    WHERE mrbeent = mrbaent AND mrbesite = mrbasite AND mrbe001 = mrbe001 AND mrba000 = '3'

    AND mrbeent = 11 AND mrbesite = '100'
(12)模具的保养项目

  SELECT DISTINCT mraj004 保养项目,oocql004 说明

   FROM mraj_t

   LEFT JOIN oocql_t ON oocqlent = mrajent AND oocql001 = '1110' AND oocql002 = mraj004  AND oocql003 = 'zh_CN'

   WHERE mrajent = 11 AND mrajsite = '100' AND mraj001 IN

   (SELECT oocq002 FROM oocq_t WHERE oocqent = mrajent AND oocq001 = '1101' AND oocq017 = 'Y' AND oocqstus = 'Y' ) 
(13)仪器资料整理

  SELECT mrba001 仪器编号,mrba004 名称,mrba005 简称

   FROM mrba_t

   WHERE mrbaent = 11 AND mrbasite = '100' AND mrba000 = '2'
(14)设备、模具、刀具   条码化
  同10

  全製程追踪 儀表板


 

 

 

杨小兵　yangxb
中国区制造事业群\华东T方案交付中心 \ 技术开发部
M 18817323349 

A 200072 上海市静安区江场路1377弄1号楼23F

E yangxb@digiwin.biz

说明: 说明: 说明: Fc_G

 

发件人: nan.wu@fulltech-metal.com [mailto:nan.wu@fulltech-metal.com]
发送时间: 2017年4月11日 16:33
收件人: 杨小兵 yangxiaobing
抄送: 陈炳陵; 谢留君 Nicole Xie
主题: 回复: 基础资料查询SQL

 

补充：

 

(10)设备、模具、刀具

(11)模具对应的易损件、产品

(12)模具的保养项目

和杨顾问一起还没有找到

(13)仪器资料整理

和杨顾问一起还没有找到

(14)设备、模具、刀具   条码化

同（10）

 

以员工资料为例

SQL：

select 
a.ooagent ENT,
a.ooagstus STATUS,
a.ooag001 EMPE_ID,
a.ooag011 EMPE_NAME,
a.ooag003 DEPT_ID,
b.ooefl003 DEPT_NAME,
a.ooag004 COMPANY_ID,
d.ooefl003 COMPANY_NAME,
a.OOAG005 JOB_ID,
c.oocql004 JBO_NAME,
a.ooag015 LEVEL_ID,
e.oocql004 LEVEL_NAME,
a.ooag018 SUP_ID,
f.ooag011 SUP_NAME,
TO_CHAR(a.ooagcrtdt, 'YYYY-MM-DD') CREATE_DATE 
from ooag_t a
left join ooefl_t b on b.ooefl001=a.OOAG003 and b.ooeflent=a.ooagent
left join OOCQL_T c on c.OOCQLENT=a.OOAGENT and c.oocql002=a.ooag005  and c.oocql001=5
left join ooefl_t d on d.ooefl001=a.OOAG004 and d.ooeflent=a.ooagent  and d.ooefl002='zh_CN'
left join OOCQL_T e on e.OOCQLENT=a.OOAGENT and e.oocql002=a.ooag015  and e.oocql001=16
left join OOAG_T f on f.ooag001=a.ooag018
where a.ooagent=11 and a.ooagstus='Y'



 

吴楠

     

    发件人： nan.wu@fulltech-metal.com

    发送时间： 2017-04-11 16:18

    收件人： yangxb

    抄送： Mark; xielj

    主题： 基础资料查询SQL

    杨顾问：

     

    您好！

     

    应庞顾问要求，在4月份要陸續完成以下資料,並錄入T100,

    (1)料件基本资料整理
    (2)BOM基本资料收集整理
    (3)产品工艺资料整理
    (4)料件主分群码资料整理
    (5)供应商基本资料整理
    (6)客户基本资料整理

    (7)客户料号对应资料整理

    (8)产品包装方式
    (9)产品检验项目
    (10)设备、模具、刀具
    (11)模具对应的易损件、产品
    (12)模具的保养项目
    (13)仪器资料整理
    (14)设备、模具、刀具   条码化

    为即時有效的查看整理的資料是否正確錄入系統，请顾问提供合理监控以上资料录入情况的SQL。

    谢谢！

     

     

     

     

    吴楠