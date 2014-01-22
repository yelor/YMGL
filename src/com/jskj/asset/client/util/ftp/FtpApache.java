package com.jskj.asset.client.util.ftp;

import com.jskj.asset.client.util.ClassHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class FtpApache implements FtpHelper {

    private static final Logger logger = Logger.getLogger(FtpApache.class);
    private FTPClient ftpClient;
    public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
    public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

    @Override
    public boolean connectServer(Host host, String path) {
        boolean flag = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(host.getIp(), 21);
            ftpClient.setControlEncoding("GBK");
            // logger.info("Connected to FTP server:" + host.getIp() +
            // ", return code:"+ftpClient.getReplyCode());
            ftpClient.login(host.getUserid(), host.getPassword());
            ftpClient.setDataTimeout(120000);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // Path is the sub-path of the FTP path
            if (path.length() != 0) {
                ftpClient.makeDirectory(path);
                ftpClient.changeWorkingDirectory(path);
            }
            flag = true;
        } catch (Exception e) {
            logger.error(e);
            // e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        FtpApache ftp = new FtpApache();
        Host host = new Host();
        host.setIp("127.0.0.1");
        host.setPassword("zaq12wsx");
        host.setUserid("assetclient");
        ftp.connectServer(host, "test");
        try {
            ftp.uploadFile("D:\\workspace\\任务至1月18号.zip", "task.zip");
            ftp.closeServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // FTP.BINARY_FILE_TYPE | FTP.ASCII_FILE_TYPE
    // Set transform type
    public void setFileType(int fileType) throws IOException {
        ftpClient.setFileType(fileType);
    }

    @Override
    public void closeServer() {
        if (ftpClient == null) {
            return;
        }
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // =======================================================================
    // == About directory =====
    // The following method using relative path better.
    // =======================================================================
    public boolean changeDirectory(String path) throws IOException {
        return ftpClient.changeWorkingDirectory(path);
    }

    public boolean createDirectory(String pathName) throws IOException {
        return ftpClient.makeDirectory(pathName);
    }

    public boolean removeDirectory(String path) throws IOException {
        return ftpClient.removeDirectory(path);
    }

    // delete all subDirectory and files.
    public boolean removeDirectory(String path, boolean isAll)
            throws IOException {

        if (!isAll) {
            return removeDirectory(path);
        }

        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr == null || ftpFileArr.length == 0) {
            return removeDirectory(path);
        }
        //
        for (FTPFile ftpFile : ftpFileArr) {
            String name = ftpFile.getName();
            if (ftpFile.isDirectory()) {
                if (!ftpFile.getName().equals(".")
                        && (!ftpFile.getName().equals(".."))) {
                    System.out.println("* [sD]Delete subPath [" + path + "/"
                            + name + "]");
                    removeDirectory(path + "/" + name, true);
                }
            } else if (ftpFile.isFile()) {
                System.out.println("* [sF]Delete file [" + path + "/" + name
                        + "]");
                deleteFile(path + "/" + name);
            } else if (ftpFile.isSymbolicLink()) {

            } else if (ftpFile.isUnknown()) {

            }
        }
        return ftpClient.removeDirectory(path);
    }

    // Check the path is exist; exist return true, else false.
    public boolean existDirectory(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory()
                    && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    // =======================================================================
    // == About file =====
    // Download and Upload file using
    // ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE) better!
    // =======================================================================
    // #1. list & delete operation
    // Not contains directory
    public List<String> getFileList(String path) throws IOException {
        // listFiles return contains directory and file, it's FTPFile instance
        // listNames() contains directory, so using following to filer
        // directory.
        // String[] fileNameArr = ftpClient.listNames(path);
        FTPFile[] ftpFiles = ftpClient.listFiles(path);

        List<String> retList = new ArrayList<String>();
        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                retList.add(ftpFile.getName());
            }
        }
        return retList;
    }

    public boolean deleteFile(String pathName) throws IOException {
        return ftpClient.deleteFile(pathName);
    }

    // #2. upload to ftp server
    // InputStream <------> byte[] simple and See API
    @Override
    public boolean uploadFile(String fileName, String newName)
            throws Exception {
        logger.info("[FTP]upload " + fileName + "...");
        boolean flag = false;
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(fileName);
            flag = ftpClient.storeFile(newName, iStream);
            if (flag) {
                logger.info("[FTP]upload successfully.");
            } else {
                logger.error("[FTP]upload failed. return:" + flag);
            }
        } catch (Exception e) {
            flag = false;
            logger.error("[FTP]upload failed." + e.getMessage());
            e.printStackTrace();
            return flag;
        } finally {
            if (iStream != null) {
                iStream.close();
            }
        }
        return flag;
    }

    public boolean uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, fileName);
    }

    public boolean uploadFile(InputStream iStream, String newName)
            throws IOException {
        boolean flag = false;
        try {
            // can execute [OutputStream storeFileStream(String remote)]
            // Above method return's value is the local file stream.
            flag = ftpClient.storeFile(newName, iStream);
        } catch (IOException e) {
            flag = false;
            return flag;
        } finally {
            if (iStream != null) {
                iStream.close();
            }
        }
        return flag;
    }

    // // #3. Down load
    //
    // public boolean download(String remoteFileName, String localFileName)
    // throws IOException {
    // logger.info("[FTP]please wait, get files from "+remoteFileName);
    // boolean flag = false;
    // File outfile = new File(localFileName);
    // OutputStream oStream = null;
    // try {
    // //System.out.println("@localFileName:"+localFileName);
    // oStream = new FileOutputStream(outfile);
    // flag = ftpClient.retrieveFile(remoteFileName, oStream);
    // if(flag)
    // logger.info("[FTP]receive successfully.");
    // else{
    // logger.error("[FTP]dowload failed.");
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // flag = false;
    // logger.error("[FTP]dowload failed."+e.getMessage());
    // } finally {
    // oStream.close();
    // }
    // return flag;
    // }
    public InputStream downFile(String sourceFileName) throws IOException {
        return ftpClient.retrieveFileStream(sourceFileName);
    }

    /**
     * Supports the point of interruption
     *
     * @param remote
     * @param local
     * @param message
     * @return
     * @throws IOException
     */
    public boolean downloadPlus(String remote, String local,
            boolean supportInterrup) throws Exception {

        boolean flag = false;
        logger.info("[FTP]please wait, get files from " + remote);
		// //�����Զ����Ʒ�ʽ����
        // ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        // ���Զ���ļ��Ƿ����
        FTPFile[] files = ftpClient.listFiles(remote);
        if (files.length != 1) {
            logger.error("[FTP]remote file is not existing.");
            return false;
        }

        long lRemoteSize = files[0].getSize();
        File f = new File(local);
        // ���ش����ļ������жϵ�����
        if (f.exists() && supportInterrup) {
            long localSize = f.length();
            if (localSize >= lRemoteSize) {
                logger.error("[FTP]local file size.");
                return false;
            }

            logger.info("[FTP]continue from the point of interruption");
            FileOutputStream out = new FileOutputStream(f, true);
            ftpClient.setRestartOffset(localSize);
            InputStream in = ftpClient.retrieveFileStream(remote);
            byte[] bytes = new byte[1024];
            long step = lRemoteSize / 100;
            long process = localSize / step;
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localSize += c;
                long nowProcess = localSize / step;
                if (nowProcess > process) {
                    process = nowProcess;
                    if (process % 10 == 0) {
                        // System.out.println("���ؽ�ȣ�"+process);
                    }
                }
            }
            in.close();
            out.close();
            boolean isDo = ftpClient.completePendingCommand();
            if (isDo) {
                logger.info("[FTP]download done from the point of interruption");
                flag = true;
            } else {
                logger.error("[FTP]download failing from the point of interruption");
                flag = false;
            }
        } else {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(f);
            InputStream in = ftpClient.retrieveFileStream(remote);
            byte[] bytes = new byte[1024];
            long step = lRemoteSize / 100;
            long process = 0;
            long localSize = 0L;
            int c;
            Long totalKb = lRemoteSize / 1000;

            logger.info("[FTP]download...");

            while ((c = in.read(bytes)) != -1) {
//                if (arg0 != null) {
//                    if (arg0.isCanceled()) {
//                        logger.error("[FTP]download has been canceled.");
//                        return false;
//                    }
//                }
                out.write(bytes, 0, c);
                localSize += c;
                long nowProcess = localSize / step;
                Long nowProcessByte = localSize / 1000;
                if (nowProcess > process) {
                    process = localSize;
                    if (process % 10 == 0) {
                    }
                }
            }

            in.close();
            out.close();
            boolean upNewStatus = ftpClient.completePendingCommand();
            if (upNewStatus) {
                logger.info("[FTP]download done.");
                flag = true;
            } else {
                logger.info("[FTP]download fail.");
                flag = false;
            }
        }
        return flag;
    }

}
