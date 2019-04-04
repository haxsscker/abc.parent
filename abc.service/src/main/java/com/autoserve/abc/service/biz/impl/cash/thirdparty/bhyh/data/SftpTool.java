package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SftpTool {
	private static Logger logger = LoggerFactory.getLogger(SftpTool.class);
	private String host;
    private String username;
    private String password;
    private int port = 22;
    private ChannelSftp sftp = null;
    private Session sshSession = null;
    
    public SftpTool() {
    	this.host = ConfigHelper.getSftpIp();
    	this.username = ConfigHelper.getSftpUserName();
        this.password = ConfigHelper.getSftpPassword();
        this.port = Integer.valueOf(ConfigHelper.getSftpPort());
    }
 
    public SftpTool(String host, String username, String password, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }
 
    public SftpTool(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }
    /**
     * connect server via sftp
     */
    public void connect() {
        try {
 
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            logger.info(("Session created."));
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.info("Session connected.");
            logger.info("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 关闭资源
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                logger.info("sftp is closed already");
            }
        }
 
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                logger.info("sshSession is closed already");
            }
 
        }
 
    }
 
    /**
     * 批量下载文件
     * 
     * @param remotPath
     *            远程下载目录(以路径符号结束)
     * @param localPath
     *            本地保存目录(以路径符号结束)
     * @param fileFormat
     *            下载文件格式(以特定字符开头,为空不做检验)
     * @param del
     *            下载后是否删除sftp文件
     * @return
     */
    public boolean batchDownLoadFile(String remotPath, String localPath,
            String fileFormat, boolean del) {
        try {
            connect();
            Vector v = listFiles(remotPath);
            if (v.size() > 0) {
 
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    LsEntry entry = (LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        if (fileFormat != null && !"".equals(fileFormat.trim())) {
                            if (filename.startsWith(fileFormat)) {
                                if (this.downloadFile(remotPath, filename,
                                        localPath, filename)
                                        && del) {
                                    deleteSFTP(remotPath, filename);
                                }
                            }
                        } else {
                            if (this.downloadFile(remotPath, filename,
                                    localPath, filename)
                                    && del) {
                                deleteSFTP(remotPath, filename);
                            }
                        }
                    }
                }
            }
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
        return false;
    }
 
    /**
     * 下载单个文件
     * 
     * @param remotPath
     *            远程下载目录(以路径符号结束)
     * @param remoteFileName
     *            下载文件名
     * @param localPath
     *            本地保存目录(以路径符号结束)
     * @param localFileName
     *            保存文件名
     * @return
     */
    public boolean downloadFile(String remotePath, String remoteFileName,
            String localPath, String localFileName) {
        try {
            sftp.cd(remotePath);
            File file = new File(localPath + localFileName);
            mkdirs(localPath + localFileName);
            sftp.get(remoteFileName, new FileOutputStream(file));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
 
        return false;
    }
 
    /**
     * 上传单个文件
     * 
     * @param remotePath
     *            远程保存目录
     * @param remoteFileName
     *            保存文件名
     * @param localPath
     *            本地上传目录(以路径符号结束)
     * @param localFileName
     *            上传的文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName,
            String localPath, String localFileName) {
        FileInputStream in = null;
        try {
            createDir(remotePath);
            File file = new File(localPath + localFileName);
            if(!file.exists()){
            	file.createNewFile();
            }
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
 
    /**
     * 批量上传文件
     * 
     * @param remotePath
     *            远程保存目录
     * @param localPath
     *            本地上传目录(以路径符号结束)
     * @param del
     *            上传后是否删除本地文件
     * @return
     */
    public boolean bacthUploadFile(String remotePath, String localPath,
            boolean del) {
        try {
            connect();
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()
                        && files[i].getName().indexOf("bak") == -1) {
                    if (this.uploadFile(remotePath, files[i].getName(),
                            localPath, files[i].getName())
                            && del) {
                        deleteFile(localPath + files[i].getName());
 
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
        return false;
 
    }
 
    /**
     * 删除本地文件
     * 
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
 
        if (!file.isFile()) {
            return false;
        }
 
        return file.delete();
    }
 
    /**
     * 创建目录
     * 
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath) {
        try {
            if (isDirExist(createpath)) {
                this.sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    sftp.chmod(Integer.parseInt("775",8),filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }
 
            }
            this.sftp.cd(createpath);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    /**
     * 判断目录是否存在
     * 
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }
 
    /**
     * 删除stfp文件
     * 
     * @param directory
     *            要删除文件所在目录
     * @param deleteFile
     *            要删除的文件
     * @param sftp
     */
    public void deleteSFTP(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 如果目录不存在就创建目录
     * 
     * @param path
     */
    public void mkdirs(String path) {
        File f = new File(path);
 
        String fs = f.getParent();
 
        f = new File(fs);
 
        if (!f.exists()) {
            f.mkdirs();
        }
    }
 
    /**
     * 列出目录下的文件
     * 
     * @param directory
     *            要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }
 
    public String getHost() {
        return host;
    }
 
    public void setHost(String host) {
        this.host = host;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public int getPort() {
        return port;
    }
 
    public void setPort(int port) {
        this.port = port;
    }
 
    public ChannelSftp getSftp() {
        return sftp;
    }
 
    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }
 
    public static void main(String[] args) {
    	SftpTool ftp = new SftpTool();
        String localPath = "D:/ftpLocalFile/";
        String remotePath = "/pfs/800055100010001/";
     // 本地文件名
    	String nowDateDir=FormatHelper.formatDate(new Date(), "yyyyMMdd");
    	remotePath+=nowDateDir;
        ftp.connect();
 
//        ftp.uploadFile(remotePath, "test.txt", localPath, "test.txt");
//        ftp.bacthUploadFile(remotePath,localPath,true);
        ftp.downloadFile(remotePath, "test.txt", localPath, "test.txt");
//        ftp.batchDownLoadFile(remotePath, localPath, null, true);
 
        ftp.disconnect();
        System.exit(0);
    }
}
