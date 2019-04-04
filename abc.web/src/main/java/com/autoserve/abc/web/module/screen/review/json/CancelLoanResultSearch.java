package com.autoserve.abc.web.module.screen.review.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.RedUseDO;
import com.autoserve.abc.dao.intf.DealRecordDao;
import com.autoserve.abc.dao.intf.InvestDao;
import com.autoserve.abc.dao.intf.InvestOrderDao;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.RedUseDao;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanTraceRecord;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.InvestState;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.RsState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.manage.LoanManageService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.exception.BusinessException;
/**
 * 流标结果查询
 * @author sunlu
 *
 */
public class CancelLoanResultSearch {
	private final static Logger logger = LoggerFactory.getLogger(CancelLoanResultSearch.class);
	@Resource
    private LoanDao                   loanDao;
	@Resource
    private DealRecordService             dealRecordService;
    @Resource
    private InvestService       investService;
    @Resource
    private InvestOrderService    investOrderService;
    @Resource
    private RedUseDao               redUseDao;
    @Resource
    private RedsendService          redsendService;
    @Resource
    private LoanManageService loanManageService;
    @Resource
    private LoanService           loanService;
	@Resource
    private UserService               userService;
	@Autowired
    private ReviewService        reviewService;
	/**
     * 1.下载结果文件并解析文件
     * 2.调用流标接口
     * 3.修改用户红包状态为未使用
     * 4.修改投资活动记录状态
     * 5.修改投资订单记录状态
     * 6.修改投资解冻记录状态
     * 7.修改普通标状态并添加项目跟踪状态记录
     * 8.更新审核状态
     */
	public BaseResult execute(@Param("loanId") int loanId){
		BaseResult result = new BaseResult();
		List<Integer> investIdList = new ArrayList<Integer>();//abc_invest投资记录ID
		boolean isCancleSuccess=true;
		boolean isBatInvestCancleSuccess=true;
		String message = "流标成功";
		String BatchNo = "";
		LoanDO loanDO = loanDao.findById(loanId);
		if(null != loanDO && null != loanDO.getSeqNo()){
			String MerBillNo = loanDO.getSeqNo();
			BatchNo = MerBillNo.substring(MerBillNo.length()-10);
			//下载结果文件并解析文件
			SftpTool ftp = new SftpTool();
			// 本地路径
			String localPath = ConfigHelper.getSftpLocalPath();
			// 目标路径
			String remotePath = ConfigHelper.getSftpRemoteDownPath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
//			String remotePath = ConfigHelper.getSftpRemoteDownPath()+"20180413";
			//结果文件名
			String fileName = "RESULT_"+FileNameUtil.getFileName("BatInvestCancle", "txt",BatchNo);
//			fileName=fileName.replace("20180416", "20180413");
			logger.info("下载批量投标撤销结果文件========================="+fileName);
			boolean isDownloadFileSuccess=true;
			try {
				ftp.connect();
				isDownloadFileSuccess=ftp.downloadFile(remotePath, fileName, localPath, fileName);
				ftp.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				isDownloadFileSuccess=false;
				isCancleSuccess=false;
			}
			if(isDownloadFileSuccess){
				logger.info("=========================解析批量投标撤销结果文件=========================");
				String fileContent=FileUtils.readByBufferedReader(localPath+fileName);
				String[] fileStrArr = fileContent.split(FileUtils.LINE_SEPARATOR);
				String lineStr="";
				for(int i=0,len=fileStrArr.length;i<len;i++){
					lineStr=fileStrArr[i];
//					logger.info("第"+(i+1)+"行===={}",lineStr);
					String colum1=lineStr.split("\\|")[0];
					String colum2=lineStr.split("\\|")[1];
					String colum3=lineStr.split("\\|")[2];
					String colum4=lineStr.split("\\|")[3];
					String colum5=lineStr.split("\\|")[4];
					if(i>0){//从第二行读取明细
						investIdList.add(Integer.valueOf(colum1));//abc_invest投资记录ID
						if(!"000000".equals(colum3)){
							isBatInvestCancleSuccess=false;
							isCancleSuccess=false;
							logger.error(colum1+"======投标撤销失败======="+colum4);
							message = colum4;
							break;
						}
					}
				}
			}else{
				logger.info("=========================下载批量投标撤销结果文件失败=========================");
				isBatInvestCancleSuccess=false;
				isCancleSuccess=false;
				message = "下载批量投标撤销结果文件失败";
			}
			if (isBatInvestCancleSuccess && null != loanDO && loanDO.getLoanState().equals(LoanState.BID_CANCELING.getState())) {
				try {
					//调用流标接口
					Map<String, String> map = new HashMap<String, String>();
					map.put("BorrowId", String.valueOf(loanDO.getLoanId()));
					Map<String, String> resultMap = loanManageService.cancelBid(map);
					if("000000".equals(resultMap.get("RespCode")) || "MCG99993".equals(resultMap.get("RespCode"))){//流标成功或已流标
						// 修改用户红包状态为未使用(通过投资记录的id查询红包使用记录，再通过红包的发送ID查询发放红包记录，修改为未使用状态)
						List<Integer> redsendIdList = new ArrayList<Integer>();
						//红包使用记录
						if (CollectionUtils.isNotEmpty(investIdList)) {
							List<RedUseDO> redUseList = redUseDao.findListByInvestIds(investIdList);
							for(RedUseDO rs : redUseList){
								redsendIdList.add(rs.getRuRedvsendId());
							}
						}
						// 红包发放记录状态修改
						if (CollectionUtils.isNotEmpty(redsendIdList)) {
							BaseResult redSendModResult = redsendService.batchModifyState(redsendIdList, RsState.USE, RsState.WITHOUT_USE);
							if (!redSendModResult.isSuccess()) {
								throw new BusinessException(CommonResultCode.ERROR_DB.getCode(), "红包发放记录状态修改失败");
							}
						}
						// 修改投资活动记录状态
						int r=investService.batchUpdateInvestState(loanDO.getLoanId(), BidType.COMMON_LOAN.getType(), InvestState.PAID.getState(), InvestState.WITHDRAWED.getState());
						if (r <= 0) {
							throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"修改投资活动记录状态出错");
						}
						// 修改投资订单记录状态
						int r1=investOrderService.batchUpdateInvestOrderState(loanDO.getLoanId(), BidType.COMMON_LOAN.getType(), InvestState.PAID.getState(), InvestState.WITHDRAWED.getState());
						if (r1 <= 0) {
							throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"修改投资订单记录状态出错");
						}
						// 修改投资解冻交易记录状态
						DealRecordDO updateDealRecord = new DealRecordDO();
						updateDealRecord.setDrInnerSeqNo(MerBillNo);//与撤销申请流水号一致
						updateDealRecord.setDrState(DealState.SUCCESS.getState());
						int flag1 = dealRecordService.updateDealRecordState(updateDealRecord);
						if (flag1 <= 0) {
							throw new BusinessException(CommonResultCode.ERROR_DB.getCode(),"修改投资解冻交易记录状态出错");
						}
						// 更新标的状态并添加项目跟踪状态记录
						Loan toModify = new Loan();
						toModify.setLoanId(loanDO.getLoanId());
						toModify.setLoanState(LoanState.BID_CANCELED);
						
						LoanTraceRecord traceRecord = new LoanTraceRecord();
						traceRecord.setLoanId(toModify.getLoanId());
						traceRecord.setCreator(135);
						traceRecord.setLoanTraceOperation(LoanTraceOperation.cancelLoan);
						traceRecord.setOldLoanState(LoanState.BID_CANCELING);
						traceRecord.setNewLoanState(LoanState.BID_CANCELED);
						traceRecord.setNote("普通标项目流标成功");
						
						BaseResult modResult = loanService.modifyLoanInfo(toModify, traceRecord);
						if (!modResult.isSuccess()) {
							throw new BusinessException("普通标状态修改失败");
						}
						//更新审核状态
				        int r2=reviewService.updateReviewState(ReviewState.FAILED_PASS_REVIEW.state,
				        		ReviewState.CANCEL_WAIT_REVIEW.state,loanDO.getLoanId());
				        if (r2 <= 0) {
				            logger.error("更新审核状态出错");
				            throw new BusinessException("更新审核状态出错");
				        }
					}else{
						logger.info("调用流标接口失败====="+resultMap.get("RespDesc"));
						throw new BusinessException("调用流标接口失败："+resultMap.get("RespDesc"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					isCancleSuccess=false;
					message = e.getMessage();
				}
			} 
		}else{
			isCancleSuccess=false;
			message = "未查询到该标的信息";
		}
		if(!isCancleSuccess){
			result.setErrorMessage(CommonResultCode.BIZ_ERROR, message);
		}else{
			result.setMessage(message);
		}
        return result;
	}
	public static void main(String[] args) {
		String str = "2437|201804130006624564|000000|交易成功|100000|";
		String []strArr=str.split("\\|");
		System.out.println(strArr.length);
		for(int i=0;i<strArr.length;i++){
			System.out.println(strArr[i]);
		}
	}
}
