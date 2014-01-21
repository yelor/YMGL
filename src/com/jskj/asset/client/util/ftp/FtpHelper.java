package com.jskj.asset.client.util.ftp;

import java.io.IOException;


public interface FtpHelper {
	
	public boolean connectServer(Host host, String path);
	public void closeServer();
	public boolean  downloadPlus(String remote,String local, boolean supportInterrup) throws Exception;
	
	public boolean uploadFile(String fileName, String newName) throws Exception;
        
        public boolean deleteFile(String pathName) throws IOException;

}
