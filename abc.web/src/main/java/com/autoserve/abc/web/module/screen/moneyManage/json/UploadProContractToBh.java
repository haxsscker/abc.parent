package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data.SftpTool;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileNameUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FileUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 *项目合同手动上传
 *
 * @author luzhi 2018年7月30日 下午5:50:22
 */
public class UploadProContractToBh {
	private final static Logger logger = LoggerFactory.getLogger(UploadProContractToBh.class);

    @Resource
    private FullTransferService fullTransferService;
    @Resource
    private LoanDao loanDao;
    @Resource
    private LoanService loanService;
    @Resource
    private SendMailService sendMailService;
    @Resource  
    private ParserRequestContext parserContext; 
    
    public JsonBaseVO execute(ParameterParser params, @Param("loanId") int loanId, Context context) {
    	String contractPath="";
    	JsonBaseVO jsonBaseVO = new JsonBaseVO();
    	String outFile = SystemGetPropeties.getBossString("abc.message.mail.outFile");//和邮件发送合同附件的路径共用一个path
    	String dir = outFile + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separatorChar;
    	try {
			logger.info("================开始上传项目合同文件================");

            // 判断目录是否存在，不存在创建
            File tempFile = new File(dir);
            if (!tempFile.exists()) {
                tempFile.mkdir();
            }
            
            DiskFileItemFactory factory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8"); 
			
			FileItem item = parserContext.getParameters().getFileItem("file");
			if(null==item){
				//按照传统方式获取数据
				jsonBaseVO.setMessage("没有文件上传");
	            jsonBaseVO.setSuccess(false);
	            return jsonBaseVO;
			}
			
            //如果fileitem中封装的是普通输入项的数据
            if(item.isFormField()){
                String name = item.getFieldName();
                //解决普通输入项的数据的中文乱码问题
	            String value = item.getString("UTF-8");
	            //value = new String(value.getBytes("iso8859-1"),"UTF-8");
	            logger.info("================"+name + "=" + value+"================");
	        }else{//如果fileitem中封装的是上传文件
                //得到上传的文件名称，
                String filename = item.getName();
                contractPath  = dir + File.separatorChar + filename;
                logger.info("================"+filename+"================");
                
	            //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
	            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
	            filename = filename.substring(filename.lastIndexOf(File.separatorChar)+1);
	            //获取item中的上传文件的输入流
	            InputStream in = item.getInputStream();
                //创建一个文件输出流
                FileOutputStream out = new FileOutputStream(contractPath);
                //创建一个缓冲区
                byte buffer[] = new byte[1024];
                //判断输入流中的数据是否已经读完的标识
	            int len = 0;
	            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
	            while((len=in.read(buffer))>0){
	               //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
	               out.write(buffer, 0, len);
	            }
                //关闭输入流
                in.close();
                //关闭输出流
                out.close();
	            //删除处理文件上传时生成的临时文件
	            item.delete();
	      }
		  
		} catch (Exception e) {
			logger.error("================文件上传失败================"+e.getMessage());
			jsonBaseVO.setMessage("文件上传失败");
            jsonBaseVO.setSuccess(false);
            return jsonBaseVO;
		}
		
		try {
			logger.info("================向银行上传项目合同文件================");
			LoanDO loanDo2 = loanDao.findByLoanId(loanId);
			String merBillNo = ConfigHelper.getConfig("merchantId")+SeqnoHelper.getId(16);
			String fileName = FileNameUtil.getFileName("ContractFileUpload", "zip",merBillNo);
			String newPdffileName = FileNameUtil.getFileName("ContractFileUpload", "pdf",merBillNo);
			// 本地路径
			File pdffile = new File(contractPath);
	        String localPath = contractPath.replace(pdffile.getName(), "");
	        File newpdffile = new File(localPath+newPdffileName);
	        //复制原文件，并重命名，防止中文文件名乱码
	        FileUtils.copyFileUsingFileStreams(pdffile,newpdffile);
			FileUtils.zipFiles(newpdffile,localPath+fileName);
			
			SftpTool ftp = new SftpTool();
	    	
	        // 目标路径
	        String remotePath = ConfigHelper.getSftpRemotePath()+FormatHelper.formatDate(new Date(), "yyyyMMdd");
	       
	        //上送成功标志文件名
	        String uploadSuccessFileName = FileNameUtil.getFileNameBySuffix(fileName, "OK");
	        ftp.connect();
        	boolean isUploadSuccess = ftp.uploadFile(remotePath, fileName, localPath, fileName);
        	isUploadSuccess = ftp.uploadFile(remotePath, uploadSuccessFileName, localPath, uploadSuccessFileName);
        	ftp.disconnect();
        	if(isUploadSuccess){
        		//发送请求,获取数据
        		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        		paramsMap.put("biz_type", "ContractFileUpload");
        		paramsMap.put("MerBillNo", merBillNo);
        		paramsMap.put("BorrowId", String.valueOf(loanDo2.getLoanId()));
        		paramsMap.put("FileName", fileName);
        		paramsMap.put("MerPriv", "");
        		Map<String,String> resultMap = ExchangeDataUtils.submitData(paramsMap);
        		if("000000".equals(resultMap.get("RespCode"))){
        			jsonBaseVO.setMessage("项目合同发送成功！");
                    jsonBaseVO.setSuccess(true);
                    return jsonBaseVO;
        		}
        		if(!"000000".equals(resultMap.get("RespCode"))){
        			logger.error("合同接口上传调用失败，"+resultMap.get("RespDesc"));
        			jsonBaseVO.setMessage("合同接口上传调用失败，"+resultMap.get("RespDesc"));
                    jsonBaseVO.setSuccess(false);
                    return jsonBaseVO;
        		}
        	}else{
        		logger.error("项目合同文件上传失败，请重新上传!");
        		jsonBaseVO.setMessage("项目合同文件上传失败，请重新上传!");
                jsonBaseVO.setSuccess(false);
                return jsonBaseVO;
        	}
	        
		}catch (Exception e) {
			logger.error("================向银行上传项目合同文件失败================"+e.getMessage());
			jsonBaseVO.setMessage("向银行上传项目合同文件失败，请重新上传!");
            jsonBaseVO.setSuccess(false);
            return jsonBaseVO;
		}
		jsonBaseVO.setMessage("项目合同发送成功！");
        jsonBaseVO.setSuccess(true);
        return jsonBaseVO;
       
    }
}
