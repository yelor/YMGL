/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.TreeSet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * code from net blog
 * @author woderchen
 */
public class FTPHelper {
    private  String username;
    private  String password;
    private  String ip;
    private  int port;
   // private static Properties property=null;//配置
    //private static String configFile;//配置文件的路径名

    private  FTPClient ftpClient=null;
    private  SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm");

    private static final String [] FILE_TYPES={"文件","目录","符号链接","未知类型"};

    public FTPHelper(){
       
    }

//    public static void main(String[] args) {
//         setConfigFile("woxingwosu.properties");//设置配置文件路径
//         connectServer();
//         listAllRemoteFiles();//列出所有文件和目录
//         changeWorkingDirectory("webroot");//进入文件夹webroot
//         listRemoteFiles("*.jsp");//列出webroot目录下所有jsp文件
//         setFileType(FTP.BINARY_FILE_TYPE);//设置传输二进制文件
//         uploadFile("woxingwosu.xml","myfile.xml");//上传文件woxingwosu.xml，重新命名为myfile.xml
//         renameFile("viewDetail.jsp", "newName.jsp");//将文件viewDetail.jsp改名为newName.jsp
//         deleteFile("UpdateData.class");//删除文件UpdateData.class
//         loadFile("UpdateData.java","loadFile.java");//下载文件UpdateData.java，并且重新命名为loadFile.java
//         closeConnect();//关闭连接
//     }

    /**
      * 上传文件
      * @param localFilePath--本地文件路径
      * @param newFileName--新的文件名
     */
    public  boolean uploadFile(String localFilePath,String newFileName) throws Exception{
         connectServer();
        //上传文件
         BufferedInputStream buffIn=null;
        try{
             buffIn=new BufferedInputStream(new FileInputStream(localFilePath));
             return ftpClient.storeFile(newFileName, buffIn);
         }catch(Exception e){
             e.printStackTrace();
             throw e;
         }finally{
            try{
                if(buffIn!=null)
                     buffIn.close();
             }catch(Exception e){
                 e.printStackTrace();
                  throw e;
             }
         }
     }

    /**
      * 下载文件
      * @param remoteFileName --服务器上的文件名
      * @param localFileName--本地文件名
     */
    public  void loadFile(String remoteFileName,String localFileName) throws Exception{
         connectServer();
        //下载文件
         BufferedOutputStream buffOut=null;
        try{
             buffOut=new BufferedOutputStream(new FileOutputStream(localFileName));
             ftpClient.retrieveFile(remoteFileName, buffOut);
         }catch(Exception e){
             e.printStackTrace();
              throw e;
         }finally{
            try{
                if(buffOut!=null)
                     buffOut.close();
             }catch(Exception e){
                 e.printStackTrace();
                  throw e;
             }
         }
     }

    /**
      * 列出服务器上所有文件及目录
     */
    public  void listAllRemoteFiles() throws Exception{
         listRemoteFiles("*");
     }

/**
      * 列出服务器上文件和目录
      * @param regStr --匹配的正则表达式
     */
     @SuppressWarnings("unchecked")
    public  void listRemoteFiles(String regStr) throws Exception{
         connectServer();
        try{
             FTPFile[] files=ftpClient.listFiles(regStr);
            if(files==null||files.length==0)
                 System.out.println("There has not any file!");
            else{
                 TreeSet<FTPFile> fileTree=new TreeSet(
                        new Comparator(){
                            //先按照文件的类型排序(倒排)，然后按文件名顺序排序
                            public int compare(Object objFile1,Object objFile2){
                                if(objFile1==null)
                                    return -1;
                                else if(objFile2==null)
                                    return 1;
                                else{
                                     FTPFile file1=(FTPFile)objFile1;
                                     FTPFile file2=(FTPFile)objFile2;
                                    if(file1.getType()!=file2.getType())
                                        return file2.getType()-file1.getType();
                                    else
                                        return file1.getName().compareTo(file2.getName());
                                 }
                             }
                         }
                 );
                for(FTPFile file:files)
                     fileTree.add(file);
                 System.out.printf("%-35s%-10s%15s%15s\n","名称","类型","修改日期","大小");
                for(FTPFile file:fileTree){
                     System.out.printf("%-35s%-10s%15s%15s\n",iso8859togbk(file.getName()),FILE_TYPES[file.getType()]
                             ,dateFormat.format(file.getTimestamp().getTime()),FileUtils.byteCountToDisplaySize(file.getSize()));
                 }
             }
         }catch(Exception e){
             e.printStackTrace();
         }
     }

    /**
      * 关闭连接
     */
    public  void closeConnect(){
        try{
            if(ftpClient!=null){
                 ftpClient.logout();
                 ftpClient.disconnect();
             }
         }catch(Exception e){
             e.printStackTrace();
         }
     }

    /**
      * 设置配置文件
      * @param configFile
     */
    public  void setConfigFile(String configFile) {
        // FTPHelper.configFile = configFile;
     }

    /**
      * 设置传输文件的类型[文本文件或者二进制文件]
      * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE
     */
    public  void setFileType(int fileType){
        try{
             connectServer();
             ftpClient.setFileType(fileType);
         }catch(Exception e){
             e.printStackTrace();
         }
     }

    /**
      * 扩展使用
      * @return
     */
    protected  FTPClient getFtpClient() throws Exception{
         connectServer();
        return ftpClient;
     }

    /**
      * 设置参数
      * @param configFile --参数的配置文件
     */
    private  void setArg(){
             this.username="hosclient";
             password="123456";
             ip="localhost";
             port=21;
     }

    /**
      * 连接到服务器
     */
    public  void connectServer() throws Exception {
        if (ftpClient == null) {
            int reply;
                 setArg();
                 ftpClient=new FTPClient();
                // ftpClient.configure(getFtpConfig());
                 ftpClient.connect(ip);
                 ftpClient.login(username, password);
                 ftpClient.setDefaultPort(port);
                 System.out.print("FTP reply:"+ftpClient.getReplyString());
                 reply = ftpClient.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                     ftpClient.disconnect();
                     System.err.println("FTP server refused connection.");
                 }
         }
     }

    /**
      * 进入到服务器的某个目录下
      * @param directory
     */
    public  void changeWorkingDirectory(String directory) throws Exception{
        try{
             connectServer();
             ftpClient.changeWorkingDirectory(directory);
         }catch(IOException ioe){
             ioe.printStackTrace();
         }
     }

    /**
      * 返回到上一层目录
     */
    public  void changeToParentDirectory() throws Exception{
        try{
             connectServer();
             ftpClient.changeToParentDirectory();
         }catch(IOException ioe){
             ioe.printStackTrace();
              throw ioe;
         }
     }

    /**
      * 删除文件
     */
    public  void deleteFile(String filename) throws Exception{
        try{
             connectServer();
             ftpClient.deleteFile(filename);
         }catch(IOException ioe){
             ioe.printStackTrace();
             throw ioe;

         }
     }

    /**
      * 重命名文件
      * @param oldFileName --原文件名
      * @param newFileName --新文件名
     */
    public  void renameFile(String oldFileName,String newFileName) throws Exception{
        try{
             connectServer();
             ftpClient.rename(oldFileName, newFileName);
         }catch(IOException ioe){
             ioe.printStackTrace();
             throw ioe;
         }
     }

    /**
      * 设置FTP客服端的配置--一般可以不设置
      * @return
     */
    private static FTPClientConfig getFtpConfig(){
         FTPClientConfig ftpConfig=new FTPClientConfig(FTPClientConfig.SYST_UNIX);
         ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
        return ftpConfig;
     }

    /**
      * 转码[ISO-8859-1 ->   GBK]
      *不同的平台需要不同的转码
      * @param obj
      * @return
     */
    private static String iso8859togbk(Object obj){
        try{
            if(obj==null)
                return "";
            else
                return new String(obj.toString().getBytes("iso-8859-1"),"GBK");
         }catch(Exception e){
            return "";
         }
     }

}

