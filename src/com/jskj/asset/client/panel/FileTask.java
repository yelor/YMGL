/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTask;
import com.jskj.asset.client.util.ftp.FTPFactory;
import com.jskj.asset.client.util.ftp.FtpHelper;
import java.io.File;

/**
 *
 * @author 305027939
 */
public abstract class FileTask extends BaseTask {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FileTask.class);
    public final static int TYPE_UPLOAD = 0;
    public final static int TYPE_DOWNLOAD = 1;
    public final static int TYPE_DELETE = 2;

    private final static String tempAttchedPath = System.getProperty("java.io.tmpdir") + "AssetClient" + File.separator + "TempAttchment" + File.separator;

    private final int type;
    private final String uploadFilePathOrDownloadFileName;
    private final String remoteDir;

    public FileTask(int TYPE, String uploadFilePathOrDownloadFileName, String remoteDir) {
        this.type = TYPE;
        this.uploadFilePathOrDownloadFileName = uploadFilePathOrDownloadFileName;
        this.remoteDir = remoteDir;
    }

    @Override
    public Object doBackgrounp() {
        String fileName = "";
        if (type == TYPE_UPLOAD) {
            try {
                File file = new File(uploadFilePathOrDownloadFileName);
                if (file.exists() && file.isFile()) {
                    /*开始FTP上传图片*/
                    FtpHelper helper = FTPFactory.getConnectionInstance(remoteDir);
                    if (helper != null) {
                        if (helper.uploadFile(uploadFilePathOrDownloadFileName, file.getName())) {
                            fileName = file.getName();
                        }
                        helper.closeServer();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return ex;
            }
        } else if (type == TYPE_DOWNLOAD) {
            try {
                /*开始FTP下载图片*/
                FtpHelper helper = FTPFactory.getConnectionInstance(remoteDir);
                if (helper != null) {
                    if (helper.downloadPlus(uploadFilePathOrDownloadFileName, tempAttchedPath + uploadFilePathOrDownloadFileName, false)) {
                        fileName = tempAttchedPath + uploadFilePathOrDownloadFileName;
                    }
                    helper.closeServer();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return ex;
            }
        } else if (type == TYPE_DELETE) {
            try {
                /*开始FTP下载图片*/
                FtpHelper helper = FTPFactory.getConnectionInstance(remoteDir);
                if (helper != null) {
                    if (helper.deleteFile(uploadFilePathOrDownloadFileName)) {
                        fileName = uploadFilePathOrDownloadFileName;
                    }
                    helper.closeServer();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return ex;
            }
        }

        return fileName;
    }

    @Override
    public void onSucceeded(Object object) {
        if (object instanceof Exception) {
            Exception e = (Exception) object;
            clientView.setStatus(e.getMessage(), AssetMessage.ERROR_MESSAGE);
            logger.error(e);
        }
        if (object instanceof String) {
            responseResult(object.toString());
        } else {
            responseResult("");
        }
    }

    public abstract void responseResult(final String file);

}
