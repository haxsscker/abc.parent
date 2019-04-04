package com.autoserve.abc.web.module.screen.common.json;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.download.FileDownloadService;
import com.autoserve.abc.service.biz.intf.upload.FileUploadService;
/**
 * 下载文件
 * @author zhangkang
 *
 */
public class DownloadFile {
	@Resource
	private HttpServletResponse response;
	@Autowired
	private FileDownloadService fileDownloadService;
	
	@Autowired
	private FileUploadService fileUploadService; 

	public void execute(@Param("fileName") String fileName,
			@Param("path") String path) throws IOException {
		response.setCharacterEncoding("gb2312");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("GB2312"), "iso8859-1"));
		response.setContentType("application/ynd.ms-excel;charset=UTF-8");
		String basePath = fileUploadService.getBasePath();
		fileDownloadService.download(basePath+path, response.getOutputStream());
	}
}
