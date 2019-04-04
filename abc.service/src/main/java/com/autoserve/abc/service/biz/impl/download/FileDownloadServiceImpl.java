package com.autoserve.abc.service.biz.impl.download;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.autoserve.abc.service.biz.intf.download.FileDownloadService;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {

	@Override
	public void download(String path, OutputStream os) {
		
		try {
			BufferedInputStream input = new BufferedInputStream(
					new FileInputStream(path));
			byte buffBytes[] = new byte[1024];
			int read = 0;
			while ((read = input.read(buffBytes)) != -1) {
				os.write(buffBytes, 0, read);
			}
			os.flush();
			os.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
