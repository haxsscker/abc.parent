package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data;

import java.util.List;
import java.util.Map;


public class HuiFuData {
	/**
	 * 汇付接口定义的参数（所有的参数必须去掉trim()出来）
	 * 可选的信息一般不填写，传数据的时候传空字符串（不能传null）,但是有特殊说明的和默认的必须填写，例如：usrId，idType
	 * 在传送给汇付的时候，首字符要大写
	 * 以上接口的部分数据可以在chinaprn.properties中配置和读取
	 * (例如：version=SystemProperties.getChinaPrnString("Version"),必须从服务器中读取，mian方法测试不行)
	 */
		/*汇付接受请求的地址    必选*/
		private String url;
		/*版本号            必选*/
		private String version;
		/*消息类型            必选 */
		private String cmdId;
		/* 商户客户号  由汇付生成，商户的唯一标识      必选*/
		private String merCustId;
		/*商户后台应答地址             必选 */
		private String bgRetUrl;
		/*页面返回 url         可选 */
		private String retUrl;
		/*商户下平台的用户号，必须唯一  变成6-25（该数据作为返回数据和平台用户信息的关联，此处数据用用户名，虽然数据可选，但是此处必选）    可选*/
		private String usrId;
		/*用户的真实姓名    可选 */
		private String usrName;
		/*证件类型 默认00 （身份证号）可选 */
		private String idType;
		/*证件号 可选*/
		private String idNo;
		/*手机号 可选 */
		private String usrMp;
		/*邮箱 可选 */
		private String usrEmail;
		/*商户私有域 可选 （该数据在配置文件中读取）*/
		private String merPriv;
		/*编码集   该数据在配置文件中读取）  可选 */
		private String charSet;
		/*签名                必选 */
		private String chkValue;
		/*应答返回码   000代表成功  必须*/
		private  String respCode;
		/*应答描述  必须*/
		private  String respDesc;
		/*商户专属平台3交易唯一标示, 必须打印到返回页面,解冻等要用到  */
		private  String trxId;
		/*商户页面要输出的特殊字符串，规则：由固定字符串RECV_ORD_ID和交易的订单号构成，如果没有交易的订单号，则用TrxId代替,必须在页面打印*/
		private  String recvOrdId;
		/*用户客户号（汇付平台提供，绑卡时候生成 ）必须保存到数据库中*/
		private  String usrCustId;
		/*订单日期*/
		private String ordDate;
		/*订单号  必须   （变长20位）*/
		private String ordId;
		/*支付网关业务代号 可选*/
		private String gateBusiId;
		/* 银行开户代号 可选*/
		private String openBankId;
		/* dcFlag 定长1位 借贷记标记 D:借记 储蓄 C:贷记 信用卡 可选*/
		private String dcFlag;
		/*transAmt 交易金额 小数点后保留2位 ，变长14位 必须*/
		private String transAmt;
		/*开户银行代号 可选*/
		private String gateBankId;
		/*手续费金额  （商户向平台用户收取） 可选*/
		private String feeAmt;
		/*手续费扣款客户号  可选*/
		private String  feeCustId;
		/*手续费扣款子账号  必须*/
		private String feeAcctId;
		/*开户银行账号  可选*/
		private String openAcctId;
		/*商户收取服务费金额  可选*/
		private String servFee;
		/* 商户子商户号  变长9位  商户用来收取服务器的子账号*/
		private String servFeeAcctId;
		/*备注 可选*/
		private String remark;
		/*用于扩展请求参数*/
		private String reqExt;
		/*用于扩展返回参数*/
		private String respExt;
		/*子账户类型    可选*/
		private String subAcctId;
		/*子账户号        可选*/
		private String subAcctType;
		/* 最大投资手续费费率值 （变长6位） 保留2位 小数  如0.01*/
		private String maxTenderRate;
		/*借款手续费*/
		private String borrowerRate;
		/*借款金额*/
		private String borrowerAmt;
		/*借款人客户号*/
		private String borrowerCustId;
		/*项目ID*/
		private String proId;
		/*借款人信息*/
		private String borrowerDetails;
		/* isFreeze        是否冻结  Y:冻结 N:不冻结   必须     如果Y,那么FreezeOrdId必填 */
		private String isFreeze;
		/*冻结订单号 变成20位*/
		private String  freezeOrdId;
		/*冻结标示 定长18位   组成规则：8位商户专属平台日期+10系统流水号*/
		private String freezeTrxId;
		/*是否解冻 定长1位  Y:解冻  N:不解冻*/
		private String isUnFreeze;
		/*解冻订单号      */
		private String unFreezeOrdId;
		/*outCustId  出账客户号（汇付生成的usrCustId）*/
		private String outCustId;
		/*手续费*/
		private String fee;
		/*订单号（投资的ordID）*/
		private String subOrdId;
		/*投资日期（YYYYMMDD）*/
		private String subOrdDate;
		/*入账客户号*/
		private String inCustId;
		/*分账账号串（分利息的账号串）,Json的数据格式保存数据,当fee=0.00是，divDetails="".*/
		private String divDetails;
		/*FeeObjFlag 续费收取对象的标志  I/O  I:向入款客户号inCustId收取 O:向出款客户号outCustId收取*/
		private String feeObjFlag;
		/*出账子账号  变成9为   用户在汇付的虚拟资金账户*/
		private String outAcctId;
		/*inCustId 入账客户号（汇付生成的usrCustId）*/	
		private String inAcctId;
		/*cardId 银行卡号*/	
		private String cardId;
		/*债权转让明细*/
		private String bidDetails;
		/*dataMap 多数据传输*/
		private List<Map<String,String>> dataMap ;
		/*auditFlag 复审表示*/
		private String auditFlag;
		
		
		
		public String getAuditFlag() {
			return auditFlag;
		}
		public void setAuditFlag(String auditFlag) {
			this.auditFlag = auditFlag;
		}
		public List<Map<String, String>> getDataMap() {
			return dataMap;
		}
		public void setDataMap(List<Map<String, String>> dataMap) {
			this.dataMap = dataMap;
		}
		public String getBorrowerDetails() {
			return borrowerDetails;
		}
		public void setBorrowerDetails(String borrowerDetails) {
			this.borrowerDetails = borrowerDetails;
		}
		public String getBorrowerCustId() {
			return borrowerCustId;
		}
		public void setBorrowerCustId(String borrowerCustId) {
			this.borrowerCustId = borrowerCustId;
		}
		public String getBorrowerAmt() {
			return borrowerAmt;
		}
		public void setBorrowerAmt(String borrowerAmt) {
			this.borrowerAmt = borrowerAmt;
		}
		public String getProId() {
			return proId;
		}
		public void setProId(String proId) {
			this.proId = proId;
		}
		/**
		 * 银行卡号
		 * 
		 */
		public String getCardId() {
			return cardId;
		}
		/**
		 * 银行卡号
		 * 
		 */
		public void setCardId(String cardId) {
			this.cardId = cardId;
		}
		/**
		 * divAmt 平台手续费分配钱数
		 * divAmt的总和=平台收取的手续费
		 * 
		 */
		private String divAmt;
		
		/**
		 *  divAmt 平台手续费分配钱数
		 *  divAmt的总和=平台收取的手续费
		 * @return
		 */
		public String getDivAmt() {
			return divAmt;
		}
		/**
		 * divAmt 平台手续费分配钱数
		 * divAmt的总和=平台收取的手续费
		 * @param divAmt
		 */
		public void setDivAmt(String divAmt) {
			this.divAmt = divAmt;
		}
		/**
		 * 转出原标金额
		 */
		private String bidCreditAmt;
		
		/**
		 * 转出原标金额
		 * @return
		 */
		public String getBidCreditAmt() {
			return bidCreditAmt;
		}
		/**
		 * 转出原标金额(从原投标订单中转出的本金)    
		 * @param bidCreditAmt  
		 */
		public void setBidCreditAmt(String bidCreditAmt) {
			this.bidCreditAmt = bidCreditAmt;
		}
		/**
		 * 转出原标借款金额
		 */
		private String borrowerCreditAmt;
		
		/**
		 * 转出原标借款金额
		 * @return
		 */
		public String getBorrowerCreditAmt() {
			return borrowerCreditAmt;
		}
		/**
		 * 转出原标借款金额
		 * @param borrowerCreditAmt
		 */
		public void setBorrowerCreditAmt(String borrowerCreditAmt) {
			this.borrowerCreditAmt = borrowerCreditAmt;
		}
		/**
		 * 投标计划类型,定长1位{P,W} P：部分授权  W:全部授权
		 */	
		private String tenderPlanType;
		
		
		/**
		 * 投标计划类型,定长1位{P,W} P：部分授权  W:全部授权
		 * @return
		 */
		public String getTenderPlanType() {
			return tenderPlanType;
		}
		/**
		 * 投标计划类型,定长1位{P,W} P：部分授权  W:全部授权
		 * @param tenderPlanType
		 */
		public void setTenderPlanType(String tenderPlanType) {
			this.tenderPlanType = tenderPlanType;
		}
		/**
		 * 原借款人的usrCustId
		 */
		private String BidborrowerCustId;	
		/**
		 * 原借款人的usrCustId
		 * @return
		 */
		public String getBidborrowerCustId() {
			return BidborrowerCustId;
		}
		/**
		 * 原借款人的usrCustId
		 * @param bidborrowerCustId
		 */
		public void setBidborrowerCustId(String bidborrowerCustId) {
			BidborrowerCustId = bidborrowerCustId;
		}
		/**
		 * 已还本金  (借款人还款金额中所占本金的部分)
		 */
		private String prinAmt;
		
		/**
		 * 原投标的ordId
		 */
		private String BidOrdId;
		/**
		 * 原投标的日期
		 */
		private String BidOrdDate;
		
		
		public String getBidOrdId() {
			return BidOrdId;
		}
		public void setBidOrdId(String bidOrdId) {
			BidOrdId = bidOrdId;
		}
		public String getBidOrdDate() {
			return BidOrdDate;
		}
		public void setBidOrdDate(String bidOrdDate) {
			BidOrdDate = bidOrdDate;
		}
		/**
		 * 已还本金  (借款人还款金额中所占本金的部分)
		 * @return 已还本金  (借款人还款金额中所占本金的部分)
		 */
		public String getPrinAmt() {
			return prinAmt;
		}
		/**
		 * 已还本金  (借款人还款金额中所占本金的部分)
		 * @return 已还本金  (借款人还款金额中所占本金的部分)
		 */
		public void setPrinAmt(String prinAmt) {
			this.prinAmt = prinAmt;
		}
		/**
		 * 债券承让人的usrCustId
		 */
		private String buyCustId;
		
		/**
		 * 债券承让人的usrCustId
		 * @return 债券承让人的usrCustId
		 */
		
		public String getBuyCustId() {
			return buyCustId;
		}
		/**
		 * 债券承让人的usrCustId
		 * @param 债券承让人的usrCustId
		 */
		public void setBuyCustId(String buyCustId) {
			this.buyCustId = buyCustId;
		}
		/**
		 * 转让金额
		 */
		private String creditAmt;
		/**
		 * 承接金额
		 */
		private String creditDealAmt;
		
		/**
		 * 承接金额
		 * @return 承接金额
		 */
		public String getCreditDealAmt() {
			return creditDealAmt;
		}
		/**
		 * 承接金额
		 * @param 承接金额
		 */
		public void setCreditDealAmt(String creditDealAmt) {
			this.creditDealAmt = creditDealAmt;
		}
		/**
		 * 转让金额
		 * @return 转让金额
		 */
		public String getCreditAmt() {
			return creditAmt;
		}
		/**
		 * 转让金额
		 * @param 转让金额
		 */
		public void setCreditAmt(String creditAmt) {
			this.creditAmt = creditAmt;
		}
		/**
		 * 转让人的的usrCustId
		 */
		private String sellCustId;
		
		/**
		 * 转让人的的usrCustId
		 */
		public String getSellCustId() {
			return sellCustId;
		}
		/**
		 * 转让人的的usrCustId
		 */
		public void setSellCustId(String sellCustId) {
			this.sellCustId = sellCustId;
		}
		/**
		 * inCustId 入账客户号（汇付生成的usrCustId）
		 * @return
		 */
		public String getInAcctId() {
			return inAcctId;
		}
		/**
		 * inCustId 入账客户号（汇付生成的usrCustId）
		 * @param inAcctId
		 */
		public void setInAcctId(String inAcctId) {
			this.inAcctId = inAcctId;
		}
		/**
		 * 出账子账号  变成9为   用户在汇付的虚拟资金账户
		 * @return
		 */
		public String getOutAcctId() {
			return outAcctId;
		}
		/**
		 * 出账子账号  变成9为   用户在汇付的虚拟资金账户
		 * @return
		 */
		public void setOutAcctId(String outAcctId) {
			this.outAcctId = outAcctId;
		}
		/**
		 * 续费收取对象的标志  I/O  I:向入款客户号inCustId收取 O:向出款客户号outCustId收取
		 * @return
		 */
		/*是否添加到资金池   定长1位  Y/N   此处选N*/
		private String isDefault;
		
		/**
		 * 是否添加到资金池   定长1位  Y/N   此处选N
		 * @return
		 */
		public String getIsDefault() {
			return isDefault;
		}
		/**
		 * 是否添加到资金池   定长1位  Y/N   此处选N
		 * @param isDefault
		 */
		public void setIsDefault(String isDefault) {
			this.isDefault = isDefault;
		}
		public String getFeeObjFlag() {
			return feeObjFlag;
		}
		/**
		 * 续费收取对象的标志  I/O  I:向入款客户号inCustId收取 O:向出款客户号outCustId收取
		 * @param feeObjFlag
		 */
		public void setFeeObjFlag(String feeObjFlag) {
			this.feeObjFlag = feeObjFlag;
		}
		/**
		 * 分账账号串（分利息的账号串）,Json的数据格式保存数据，当fee=0.00是，divDetails="".
		 * @return
		 */
		
		public String getDivDetails() {
			return divDetails;
		}
		/**
		 * 分账账号串（分利息的账号串）,Json的数据格式保存数据，当fee=0.00是，divDetails="".
		 * @param divDetails
		 */
		public void setDivDetails(String divDetails) {
			this.divDetails = divDetails;
		}
		/**
		 * 入账客户号
		 * @return
		 */
		public String getInCustId() {
			return inCustId;
		}
		/**
		 * 出账客户号
		 * @return
		 */
		public void setInCustId(String inCustId) {
			this.inCustId = inCustId;
		}
		/**
		 * 投标日期
		 * @return
		 */
		
		public String getSubOrdDate() {
			return subOrdDate;
		}
		/**
		 * 投标日期
		 * @return
		 */
		public void setSubOrdDate(String subOrdDate) {
			this.subOrdDate = subOrdDate;
		}
		/**
		 * 订单号（投资的ordID）
		 * @return
		 */
		public String getSubOrdId() {
			return subOrdId;
		}
		/**
		 * 订单号（投资的ordID）
		 * @param subOrdId
		 */
		public void setSubOrdId(String subOrdId) {
			this.subOrdId = subOrdId;
		}
		/**
		 * 手续费 放款的时候设置为0.00
		 * @return
		 */
		public String getFee() {
			return fee;
		}
		/**
		 * 手续费 放款的时候设置为0.00
		 * @param fee
		 */
		public void setFee(String fee) {
			this.fee = fee;
		}
		/**
		 * outCustId  出账客户号（汇付生成的usrCustId）
		 * @return
		 */
		public String getOutCustId() {
			return outCustId;
		}
		/**
		 * outCustId  出账客户号（汇付生成的usrCustId）
		 * @param outCustId
		 */
		public void setOutCustId(String outCustId) {
			this.outCustId = outCustId;
		}
		public String getIsUnFreeze() {
			return isUnFreeze;
		}
		public void setIsUnFreeze(String isUnFreeze) {
			this.isUnFreeze = isUnFreeze;
		}
		public String getUnFreezeOrdId() {
			return unFreezeOrdId;
		}
		public void setUnFreezeOrdId(String unFreezeOrdId) {
			this.unFreezeOrdId = unFreezeOrdId;
		}
		public String getFreezeTrxId() {
			return freezeTrxId;
		}
		public void setFreezeTrxId(String freezeTrxId) {
			this.freezeTrxId = freezeTrxId;
		}
		public String getIsFreeze() {
			return isFreeze;
		}
		public void setIsFreeze(String isFreeze) {
			this.isFreeze = isFreeze;
		}
		public String getFreezeOrdId() {
			return freezeOrdId;
		}
		public void setFreezeOrdId(String freezeOrdId) {
			this.freezeOrdId = freezeOrdId;
		}
		public String getMaxTenderRate() {
			return maxTenderRate;
		}
		public void setMaxTenderRate(String maxTenderRate) {
			this.maxTenderRate = maxTenderRate;
		}
		public String getSubAcctId() {
			return subAcctId;
		}
		public void setSubAcctId(String subAcctId) {
			this.subAcctId = subAcctId;
		}
		public String getSubAcctType() {
			return subAcctType;
		}
		public void setSubAcctType(String subAcctType) {
			this.subAcctType = subAcctType;
		}
		public String getRespExt() {
			return respExt;
		}
		public void setRespExt(String respExt) {
			this.respExt = respExt;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getReqExt() {
			return reqExt;
		}
		public void setReqExt(String reqExt) {
			this.reqExt = reqExt;
		}
		public String getServFee() {
			return servFee;
		}
		public void setServFee(String servFee) {
			this.servFee = servFee;
		}
		public String getServFeeAcctId() {
			return servFeeAcctId;
		}
		public void setServFeeAcctId(String servFeeAcctId) {
			this.servFeeAcctId = servFeeAcctId;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getCmdId() {
			return cmdId;
		}
		public void setCmdId(String cmdId) {
			this.cmdId = cmdId;
		}
		public String getMerCustId() {
			return merCustId;
		}
		public void setMerCustId(String merCustId) {
			this.merCustId = merCustId;
		}
		public String getBgRetUrl() {
			return bgRetUrl;
		}
		public void setBgRetUrl(String bgRetUrl) {
			this.bgRetUrl = bgRetUrl;
		}
		public String getRetUrl() {
			return retUrl;
		}
		public void setRetUrl(String retUrl) {
			this.retUrl = retUrl;
		}
		public String getUsrId() {
			return usrId;
		}
		public void setUsrId(String usrId) {
			this.usrId = usrId;
		}
		public String getUsrName() {
			return usrName;
		}
		public void setUsrName(String usrName) {
			this.usrName = usrName;
		}
		public String getIdType() {
			return idType;
		}
		public void setIdType(String idType) {
			this.idType = idType;
		}
		public String getIdNo() {
			return idNo;
		}
		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}
		public String getUsrMp() {
			return usrMp;
		}
		public void setUsrMp(String usrMp) {
			this.usrMp = usrMp;
		}
		public String getUsrEmail() {
			return usrEmail;
		}
		public void setUsrEmail(String usrEmail) {
			this.usrEmail = usrEmail;
		}
		public String getMerPriv() {
			return merPriv;
		}
		public void setMerPriv(String merPriv) {
			this.merPriv = merPriv;
		}
		public String getCharSet() {
			return charSet;
		}
		public void setCharSet(String charSet) {
			this.charSet = charSet;
		}
		public String getChkValue() {
			return chkValue;
		}
		public void setChkValue(String chkValue) {
			this.chkValue = chkValue;
		}
		public String getRespCode() {
			return respCode;
		}
		public void setRespCode(String respCode) {
			this.respCode = respCode;
		}
		public String getRespDesc() {
			return respDesc;
		}
		public void setRespDesc(String respDesc) {
			this.respDesc = respDesc;
		}
		public String getTrxId() {
			return trxId;
		}
		public void setTrxId(String trxId) {
			this.trxId = trxId;
		}
		public String getRecvOrdId() {
			return recvOrdId;
		}
		public void setRecvOrdId(String recvOrdId) {
			this.recvOrdId = recvOrdId;
		}
		public String getUsrCustId() {
			return usrCustId;
		}
		public void setUsrCustId(String usrCustId) {
			this.usrCustId = usrCustId;
		}
		public String getOrdDate() {
			return ordDate;
		}
		public void setOrdDate(String ordDate) {
			this.ordDate = ordDate;
		}
		public String getOrdId() {
			return ordId;
		}
		public void setOrdId(String ordId) {
			this.ordId = ordId;
		}
		public String getGateBusiId() {
			return gateBusiId;
		}
		public void setGateBusiId(String gateBusiId) {
			this.gateBusiId = gateBusiId;
		}
		public String getOpenBankId() {
			return openBankId;
		}
		public void setOpenBankId(String openBankId) {
			this.openBankId = openBankId;
		}
		public String getDcFlag() {
			return dcFlag;
		}
		public void setDcFlag(String dcFlag) {
			this.dcFlag = dcFlag;
		}
		/**
		 * 交易金额   14位 保留2位小数点   必须 
		 * @return
		 */
		public String getTransAmt() {
			return transAmt;
		}
		/**
		 * 交易金额  14位 保留2位小数点  必须
		 * @return
		 */
		public void setTransAmt(String transAmt) {
			this.transAmt = transAmt;
		}
		public String getGateBankId() {
			return gateBankId;
		}
		public void setGateBankId(String gateBankId) {
			this.gateBankId = gateBankId;
		}
		public String getFeeAmt() {
			return feeAmt;
		}
		public void setFeeAmt(String feeAmt) {
			this.feeAmt = feeAmt;
		}
		public String getFeeCustId() {
			return feeCustId;
		}
		public void setFeeCustId(String feeCustId) {
			this.feeCustId = feeCustId;
		}
		public String getFeeAcctId() {
			return feeAcctId;
		}
		public void setFeeAcctId(String feeAcctId) {
			this.feeAcctId = feeAcctId;
		}
		/**
		 * inAcctId  入账子账户   变成9为   用户在汇付的虚拟资金账户
		 * @return
		 */
		public String getOpenAcctId() {
			return openAcctId;
		}
		/**
		 * inAcctId  入账子账户   变成9为   用户在汇付的虚拟资金账户
		 * @param openAcctId
		 */
		public void setOpenAcctId(String openAcctId) {
			this.openAcctId = openAcctId;
		}
		public String getBorrowerRate() {
			return borrowerRate;
		}
		public void setBorrowerRate(String borrowerRate) {
			this.borrowerRate = borrowerRate;
		}
		public String getBidDetails() {
			return bidDetails;
		}
		public void setBidDetails(String bidDetails) {
			this.bidDetails = bidDetails;
		}
		
		
}
