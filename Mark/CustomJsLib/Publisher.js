/*�ǿ��ƫʸ�*/
var DataWrapper = (function () {
	function DataWrapper() {
		this.state = DataWrapper._STATE_REQUEST;
		this.action = DataWrapper._ACTION_GET;
		this.key = "some key";
		this.message = "just happening";
		this.pack = [];
	}
	//��ƾާ@�覡
	DataWrapper._ACTION_GET = 1;
	DataWrapper._ACTION_POST = 2;
	DataWrapper._ACTION_PUT = 3;
	DataWrapper._ACTION_DELETE = 4;
	//��ƾާ@�^���X
	DataWrapper._STATE_INITIAL = 999;
	DataWrapper._STATE_REQUEST = 998;
	DataWrapper._STATE_SUCCESS = 200;
	DataWrapper._STATE_FORBIDDEN = 403;
	DataWrapper._STATE_NOTFOUND = 404;
	return DataWrapper;
})();

//�d�߱���ʸ�
var CustomerQuery = (function (){
	function CustomerQuery() {
		this.numDataRowSwift=0;
		this.numDataRowTotal=0;
		this.numDataOffsetNow=0;
		this.numDataOffsetPre=0;
		this.numDataOffsetNext=0;
		this.txtEndTime="";
		this.txtSubject="";
		this.txtStartTime="";
	}
	return CustomerQuery;
})();

var Publisher=function(){
	var self=this;
	
	/**
	*���o���i�����M��
	*/
	Publisher.prototype.getPublishTypeList=function(pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_GET;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		ajax_AnnouncementManageAccessor.getAnnouncementTypeList(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(pDataBack.pack);
			}else{
				tFunc(false);
			}
		});
		return;
	};
			
	/**
	*�s�W���i�����M��
	*/
	Publisher.prototype.newPublishType=function(pData,pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		if(pData._publishType==undefined||pData._publishTypeName==undefined){
			tFunc(false);
			return;
		}
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_POST;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=new Object();
		tData.query.mapOptionalComponent._publishType="";
		tData.query.mapOptionalComponent._publishTypeName=pData._publishTypeName;
		ajax_AnnouncementManageAccessor.postAnnouncementType(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true,pDataBack);
			}else{
				tFunc(false);
			}	
		});
		return;
	};
	
	/**
	*�R�����i�����M��
	*/
	Publisher.prototype.dropPublishType=function(pData,pCallBack){
		var tFunc;
		if(pData._publishType==undefined||pData._publishTypeName==undefined){
			tFunc(false);
			return;
		}
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_DELETE;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=new Object();
		tData.query.mapOptionalComponent._publishType=pData._publishType;
		tData.query.mapOptionalComponent._publishTypeName=pData._publishTypeName;
		ajax_AnnouncementManageAccessor.deleteAnnouncementType(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true);
			}else{
				tFunc(false);
			}	
		});
		return;
	};
	
	/**
	*�ܧ󤽧i�����M��
	*/
	Publisher.prototype.changePublishType=function(pData,pCallBack){
		var tFunc;
		if(pData._publishType==undefined||pData._publishTypeName==undefined){
			tFunc(false);
			return;
		}
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_PUT;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=new Object();
		tData.query.mapOptionalComponent._publishType=pData._publishType;
		tData.query.mapOptionalComponent._publishTypeName=pData._publishTypeName;
		ajax_AnnouncementManageAccessor.putAnnouncementType(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true,pDataBack);
			}else{
				tFunc(false);
			}	
		});
		return;
	};
	
	/**
	*���o���i���n�ʲM��
	*/
	Publisher.prototype.getPublishLevelList=function(pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_GET;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		ajax_AnnouncementManageAccessor.getAnnouncementEmergencyList(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(pDataBack.pack);
			}else{
				tFunc(false);
			}
		});
		return;
	};
	
	/**
	*�s�W���i���n��
	*/
	Publisher.prototype.newPublishLevel=function(pData,pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		if(pData._emergency==undefined||pData._emergencyName==undefined){
			tFunc(false);
			return;
		}
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_POST;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=new Object();
		tData.query.mapOptionalComponent._emergency="";
		tData.query.mapOptionalComponent._emergencyName=pData._emergencyName;
		ajax_AnnouncementManageAccessor.postAnnouncementEmergency(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true,pDataBack);
			}else{
				tFunc(false);
			}	
		});
		return;		
	};
	
	/**
	*�R�����i���n��
	*/
	Publisher.prototype.dropPublishLevel=function(pData,pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		if(pData._emergency==undefined||pData._emergencyName==undefined){
			tFunc(false);
			return;
		}
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_DELETE;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=new Object();
		tData.query.mapOptionalComponent._emergency=pData._emergency;
		tData.query.mapOptionalComponent._emergencyName=pData._emergencyName;
		ajax_AnnouncementManageAccessor.deleteAnnouncementEmergency(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true);
			}else{
				tFunc(false);
			}	
		});
		return;		
	};
	
	/**
	*�ܧ󤽧i���n��
	*/
	Publisher.prototype.changePublishLevel=function(pData,pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		if(pData._emergency==undefined||pData._emergencyName==undefined){
			tFunc(false);
			return;
		}
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_PUT;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=new Object();
		tData.query.mapOptionalComponent._emergency=pData._emergency;
		tData.query.mapOptionalComponent._emergencyName=pData._emergencyName;
		ajax_AnnouncementManageAccessor.putAnnouncementEmergency(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true,pDataBack);
			}else{
				tFunc(false);
			}	
		});
		return;		
	};
	
	/**
	*���o���i
	*/
	Publisher.prototype.getPublish=function(pData,pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		if(pData._OID==undefined||pData._OID==''){
			tFunc(false);
			return;
		}
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_GET;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		ajax_AnnouncementManageAccessor.getAnnouncement(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true,pDataBack);
			}else{
				tFunc(false);
			}	
		});
		return;		
	};
	
	/**
	*�s�W���i
	*/
	Publisher.prototype.newPublish=function(pData,pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_POST;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=pData;
		ajax_AnnouncementManageAccessor.postAnnouncement(tData,pData._attachmentList,pData._permissionControl,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true,pDataBack);
			}else{
				tFunc(false);
			}	
		});
		return;			
	};
	
	/**
	*�R�����i
	*/
	Publisher.prototype.dropPublish=function(pData,pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_DELETE;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=pData;
		ajax_AnnouncementManageAccessor.deleteAnnouncementEmergency(tData,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true);
			}else{
				tFunc(false);
			}	
		});
		return;			
	};
	
	/**
	*�ܧ󤽧i
	*/
	Publisher.prototype.changePublish=function(pData,pCallBack){
		var tFunc;
		typeof pCallBack=='function'?tFunc=pCallBack:tFunc=function(){};
		var tData=new DataWrapper();
		tData.action=DataWrapper._ACTION_PUT;
		tData.state=DataWrapper._STATE_REQUEST;
		tData.query=new CustomerQuery();
		tData.query.mapOptionalComponent=pData;
		ajax_AnnouncementManageAccessor.putAnnouncement(tData,pData._attachmentList,pData._permissionControl,function(pDataBack){
			if(pDataBack.state==DataWrapper._STATE_SUCCESS){
				tFunc(true,pDataBack);
			}else{
				tFunc(false);
			}	
		});
		return;		
	};
	

}