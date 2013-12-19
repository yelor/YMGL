/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.AssetClientView;
import com.jskj.asset.client.layout.AssetNode;
import com.jskj.asset.client.layout.AssetTab;
import com.jskj.asset.client.layout.AssetTreeNode;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.BaseTask;
import static com.jskj.asset.client.layout.BaseTask.lock;
import com.jskj.asset.client.layout.BaseTreePane;
import com.jskj.asset.client.util.ClassHelper;
import java.awt.Component;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public class OpenTabTask extends BaseTask {

    private static Logger logger = Logger.getLogger(OpenTabTask.class);
    private AssetTreeNode treeNode;
    private int type;
    private String name;
    private BasePanel hosjPanel;
    private javax.swing.JTabbedPane mainTabPane;
    private boolean updateInExist;

    public OpenTabTask( AssetTreeNode treeNode, String name, BasePanel hosjPanel, int type, boolean updateInExist) {
        super();

        super.setProcessDisplay(false);

        this.treeNode = treeNode;
        this.mainTabPane = ((AssetClientView) (Application.getInstance(AssetClientApp.class).getMainView())).getMainViewPane().getRightPane();
        this.name = name;
        this.hosjPanel = hosjPanel;
        this.type = type;
        this.updateInExist = updateInExist;
    }

//    public OpenTabTask(Application app, AssetTreeNode treeNode, String name, BasePanel hosjPanel, int type) {
//        this(app, treeNode, name, hosjPanel, 0,false);
//    }
    public OpenTabTask(String name, BasePanel hosjPanel) {
        this(null, name, hosjPanel, 1, false);
    }

    public OpenTabTask(String name, BasePanel hosjPanel, boolean updateInExist) {
        this(null, name, hosjPanel, 1, updateInExist);
    }

    public OpenTabTask( AssetTreeNode treeNode) {
        this(treeNode, "", null, 0, false);
    }

    public OpenTabTask(AssetTreeNode treeNode, boolean updateInExist) {
        this(treeNode, "", null, 0, updateInExist);
    }

    /**
     * 当前TAB是否已经打开了
     * @param nodeNo
     * @return
     */
    private boolean isExist(String nodeNo) {
        boolean flag = false;
        ArrayList disTabCount = BaseTreePane.disTabCount;
        for (int i = 0; i < disTabCount.size(); i++) {
            if (nodeNo.equals(disTabCount.get(i))) {
                // tabbedPane.setSelectedComponent(tabbedPane.get);
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 得到存在的组件
     * @param nodeNo
     * @return
     */
    private BasePanel getSelectHosJPanel(String nodeNo) {
        Component[] cs = mainTabPane.getComponents();
        for (Component c : cs) {
            if (c.getName() != null && c.getName().equals(nodeNo)) {
                return (BasePanel) c;
            }
        }
        return null;
    }

    /**
     * 得到存在的TAB组件
     * @param nodeNo
     * @return
     */
    private AssetTab getSelectAssetTab(String tabName) {
        int total = mainTabPane.getTabCount();
        for (int i = 0; i < total; i++) {
            AssetTab hosTab = (AssetTab) mainTabPane.getTabComponentAt(i);
            if (hosTab.getName().equalsIgnoreCase(tabName)) {
                return hosTab;
            }
        }

        return null;
    }

    /**
     * 手动增加组件
     * @param name
     * @param hosjPanel
     */
    public boolean addTab(String name, BasePanel hosjPanel) {

        if (hosjPanel != null) {
            if (isExist(name)) {

                if (updateInExist) {
                    AssetTab hosTab = getSelectAssetTab(name);
                    if (hosTab != null) {
                        hosTab.close();
                        //System.out.println("@@@@@@@@@@hosTab:"+hosTab.getIdentity());
                    }
                } else {
                    mainTabPane.setSelectedComponent(getSelectHosJPanel(name));//打开存在的面板
                    return false;
                }
            }
            //增加面板
            setTabPane(hosjPanel, name);
            return true;
        }
        return false;
    }

    /**
     * 通过treePath主面板增加组件
     * @param treePath
     */
    public boolean addTab(AssetTreeNode treeNode) {

        AssetNode node = treeNode.getUserNode();

        String tabName = node.getNodeName();

        if (isExist(tabName)) {

            if (updateInExist) {
                AssetTab hosTab = getSelectAssetTab(name);
                if (hosTab != null) {
                    hosTab.close();
                }
            } else {
                BasePanel selectedPane = getSelectHosJPanel(tabName);
                if (selectedPane != null) {
                    mainTabPane.setSelectedComponent(selectedPane);//打开存在的面板
                }
                return false;
            }
        }

        String classObject = node.getLinkObject();
        if (classObject != null && !classObject.equals("")) {

            try {
                ClassHelper<BasePanel> classHelper = new ClassHelper<BasePanel>(classObject, new Class[]{});
                hosjPanel = classHelper.newInstance(new Object[]{});
            } catch (Exception ex) {
                hosjPanel = new NoFoundPanel(ex);
            }

            if (hosjPanel != null) {
                //增加面板
                setTabPane(hosjPanel, tabName);
                return true;
            }
        }
        return false;
    }

    /**
     * 增加一个Tab
     * @param basePanel
     * @param tabName
     */
    private void setTabPane(BasePanel basePanel, String tabName) {
        //增加面板
        lock.lock();
        try {
            mainTabPane.addTab(null, basePanel);
            mainTabPane.setTabComponentAt(mainTabPane.getTabCount() - 1, new AssetTab(mainTabPane, basePanel, tabName));
            mainTabPane.setSelectedComponent(basePanel);
            BaseTreePane.disTabCount.add(tabName);
            basePanel.setName(tabName);
            logger.debug("add tab:" + tabName);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public Object doBackgrounp() {
        try {
            if (type == 0) {
                if (!addTab(treeNode)) {
                    return 0;
                }
            } else if (type == 1) {
                if (!addTab(name, hosjPanel)) {
                    return 0;
                }
            }

            if (hosjPanel != null && treeNode != null) {

                //改装一下实现的业务的逻辑
                AssetNode node = treeNode.getUserNode();
                String linkObject = node.getLinkObject();

                logger.debug("reload:" + linkObject);

                Task task = hosjPanel.reload(node);
                if (task != null) {
                    logger.debug("reload execute with parameter.");
                    return task;
                } else {
                    Task taskNoNode = hosjPanel.reload();
                    if (taskNoNode != null) {
                        logger.debug("reload execute without parameter.");
                        return taskNoNode;
                    }
                }
            } else {
                Task taskNoNode = hosjPanel.reload();
                if (taskNoNode != null) {
                    logger.debug("reload execute without parameter.");
                    return taskNoNode;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
        return 0;
    }

    @Override
    public void onSucceeded(Object object) {

        if (object != null) {
            if (object instanceof java.lang.UnsupportedOperationException) {
            } else if (object instanceof Exception) {
                Exception e = (Exception) object;
                //e.printStackTrace();
                logger.error(e.toString());
                return;
            } else if (object != null && object instanceof Task) {
                ((Task) object).execute(); //执行task
            } else if (object instanceof Integer) {
                logger.debug("not execute task");
            }
        }

    }
}
