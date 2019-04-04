package com.autoserve.abc.service.biz.intf.download;

import java.io.OutputStream;

public interface FileDownloadService {
	
	public void download(String path, OutputStream os);
}
