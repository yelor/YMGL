package com.jskj.asset.client.util.ftp;

import com.jskj.asset.client.constants.Constants;
import org.apache.log4j.Logger;

public class FTPFactory {

    private static final Logger logger = Logger.getLogger(FTPFactory.class);

    private static FtpHelper ftphelp;

    public static FtpHelper getConnectionInstance(Host host, String path) throws Exception {
        if (ftphelp == null) {
            ftphelp = new FtpApache();
            if (!ftphelp.connectServer(host, path)) {
                ftphelp = null;
                logger.error("[FTP]connected failure.");
                throw new Exception("[FTP]connected failure.");
            } else {
                logger.info("[FTP]file protocol: FTP");
            }
        }
        return ftphelp;
    }

    public static FtpHelper getConnectionInstance(String path) throws Exception {
        if (ftphelp == null) {
            ftphelp = new FtpApache();
        }
        //在系统中，host的所有参数都是固定的,需要看<FTPSERVER>\res\conf\ftpd-asset.xml
        Host host = new Host();
        host.setIp(Constants.SERVICE_IP);
        host.setPassword("zaq12wsx");
        host.setUserid("assetclient");
        if (!ftphelp.connectServer(host, path)) {
            ftphelp = null;
            logger.error("[FTP]connected failure.");
            throw new Exception("[FTP]connected failure.");
        } else {
            logger.info("[FTP]file protocol: FTP");
        }

        return ftphelp;
    }

}
