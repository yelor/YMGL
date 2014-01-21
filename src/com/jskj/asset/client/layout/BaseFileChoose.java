/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.layout;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.PathCacheBean;
import com.jskj.asset.client.bean.PathCacheBean.PathBean;
import com.jskj.asset.client.util.XMLHelper;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;

/**
 *
 * @author woderchen
 */
public class BaseFileChoose extends JFileChooser {

    private static Logger logger = Logger.getLogger(BaseFileChoose.class);
    private Component parent;
    private PathCacheBean pathCacheVO;
    private XMLHelper<PathCacheBean> xmlhelper;
    private List<PathBean> pathBeans;
    private String currentDialogPath;

    public BaseFileChoose(String title, String[] filters, Component parent) {
        super();
        setDialogTitle(title);

        if (filters != null) {
            setAcceptAllFileFilterUsed(false);
            setFileFilter(new FileNameExtensionFilter("", filters));
        }

       //addChoosableFileFilter(new BaseFileFilter(filters));
        this.parent = parent;
        pathCacheVO = new PathCacheBean();
        xmlhelper = new XMLHelper<PathCacheBean>(pathCacheVO);
        currentDialogPath = setCurrentDirectoryPlus();
    }

    public BaseFileChoose(Component parent) {
        this("浏览", null, parent);
    }

    public BaseFileChoose(String filter, Component parent) {
        this("浏览", new String[]{filter}, parent);
    }

    public BaseFileChoose(String[] filters, Component parent) {
        this("浏览", filters, parent);
    }

    /**
     * 设置当前文件
     */
    public String setCurrentDirectoryPlus() {

        String currentPath = "";

        try {
            pathCacheVO = xmlhelper.read();
        } catch (JAXBException ex) {
            logger.error(ex);
        }

        String keyName = parent.getClass().getName();
        pathBeans = pathCacheVO.getPaths();

        if (pathBeans == null) {
            pathBeans = new ArrayList<PathBean>();
            currentPath = System.getProperty("user.dir");
        } else {
            boolean exist = false;
            for (PathBean p : pathBeans) {
                if (p.getKey().equals(keyName)) {
                    currentPath = p.getValue();
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                currentPath = System.getProperty("user.dir");
            }
        }
        if (!currentPath.equals("")) {
            setCurrentDirectory(new File(currentPath));
        }

        return currentPath;
    }

    /**
     * 打开保存对话框
     *
     * @return
     */
    public String showSaveDialog() {
        String selectFilePath = "";

        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = showSaveDialog(AssetClientApp.getApplication().getMainFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = getSelectedFile();
                // System.out.println("file:" + file.getName());
                selectFilePath = file.getCanonicalPath();

                saveCache(pathBeans, file, true);//save directory

            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(ex);
            }
        }

        return selectFilePath;
    }

    /**
     * 打开保存对话框
     *
     * @return
     */
    public String showSaveDialogWithFile(boolean verify) {
        String selectFilePath = "";

        setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = showSaveDialog(AssetClientApp.getApplication().getMainFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = getSelectedFile();

                if (verify) {

                    int res = AssetMessage.CONFIRM("文件已经存在，是否覆盖？");
                    if (res == JOptionPane.OK_OPTION) {
                    } else {
                        return "";
                    }

                }
                // System.out.println("file:" + file.getName());
                selectFilePath = file.getCanonicalPath();

                saveCache(pathBeans, file, true);//save directory

            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(ex);
            }
        }

        return selectFilePath;
    }

    /**
     * 选择文件
     *
     * @return
     */
    public String openDialog() {
        String selectFilePath = "";

        int result = showOpenDialog(AssetClientApp.getApplication().getMainFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = getSelectedFile();
                // System.out.println("file:" + file.getName());
                selectFilePath = file.getCanonicalPath();

                saveCache(pathBeans, file);//save directory

            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(ex);
            }
        }

        return selectFilePath;

    }

    private void saveCache(List<PathBean> paths, File selectedFile) {

        String keyName = parent.getClass().getName();
        PathBean pathBean = null;
        boolean exist = false;
        for (PathBean p : paths) {
            if (p.getKey().equals(keyName)) {
                pathBean = p;
                exist = true;
                break;
            }
        }
        if (!exist) {
            pathBean = new PathBean();
        }

        pathBean.setKey(parent.getClass().getName());
        pathBean.setValue(selectedFile.getParent());
        if (!exist) {
            paths.add(pathBean);
        }
        pathCacheVO.setPaths(paths);

        try {
            xmlhelper.write();
        } catch (JAXBException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }

    }

    private void saveCache(List<PathBean> paths, File selectedFile, boolean isDirectory) {

        String keyName = parent.getClass().getName();
        PathBean pathBean = null;
        boolean exist = false;
        for (PathBean p : paths) {
            if (p.getKey().equals(keyName)) {
                pathBean = p;
                exist = true;
                break;
            }
        }
        if (!exist) {
            pathBean = new PathBean();
        }

        pathBean.setKey(parent.getClass().getName());
        pathBean.setValue(selectedFile.getAbsolutePath());
        if (!exist) {
            paths.add(pathBean);
        }
        pathCacheVO.setPaths(paths);

        try {
            xmlhelper.write();
        } catch (JAXBException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }

    }

    /**
     * @return the currentDialogPath
     */
    public String getCurrentDialogPath() {
        return currentDialogPath;
    }

    public String getRootDirectory() {
        return System.getProperty("user.dir");
    }
}
