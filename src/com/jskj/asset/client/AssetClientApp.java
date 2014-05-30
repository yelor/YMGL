/*
 * AssetClientApp.java
 */
package com.jskj.asset.client;

import com.jskj.asset.client.bean.ParamSession;
import com.jskj.asset.client.bean.UserSessionEntity;
import com.jskj.asset.client.bean.entity.Appparam;
import com.jskj.asset.client.bean.entity.Departmenttb;
import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.login.LoginMain;
import com.jskj.asset.client.panel.message.MessagePanel;
import com.jskj.asset.client.panel.user.PermissionPanel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AssetClientApp extends SingleFrameApplication {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AssetClientApp.class);

    private static UserSessionEntity sessionMap;

    private static JFrame loginWindow;

    private static MessagePanel messagePanel;

    public static String[] DEFAULT_FONT = new String[]{
        "Table.font", "TableHeader.font", "CheckBox.font", "Tree.font", "Viewport.font", "ProgressBar.font",
        "RadioButtonMenuItem.font", "ToolBar.font", "ColorChooser.font", "ToggleButton.font", "Panel.font",
        "TextArea.font", "Menu.font", "TableHeader.font", "TextField.font", "OptionPane.font", "MenuBar.font", "Button.font", "Label.font",
        "PasswordField.font", "ScrollPane.font", "MenuItem.font",
        "ToolTip.font", "List.font", "EditorPane.font", "Table.font",
        "TabbedPane.font", "RadioButton.font", "CheckBoxMenuItem.font",
        "TextPane.font", "PopupMenu.font", "TitledBorder.font", "ComboBox.font"
    };

    private static List<Appparam> addMyPermession;
    private static List<Appparam> reduceMyPermession;

    public static List<Appparam> getAdditionalPermession() {
        return addMyPermession;
    }

    public static List<Appparam> getReductionPermession() {
        return reduceMyPermession;
    }

    /**
     * @return the sessionMap
     */
    public static UserSessionEntity getSessionMap() {
        return sessionMap;
    }

    /**
     * @param aSessionMap the sessionMap to set
     */
    public static void setSessionMap(UserSessionEntity aSessionMap) {
        sessionMap = aSessionMap;
    }

    public static MessagePanel getMessagePanel() {
        return messagePanel;
    }

    /**
     * 得到参数表中，指定父节点对应下得所有子参数
     *
     * @param parentParamName
     * @return
     */
    public static List<String> getChildParamNameByParentName(String parentParamName) {
        return ParamSession.getInstance().getChildNameByParentName(parentParamName);
    }

    /**
     * 根据参数类型，得到所有该类型的参数名
     *
     * @param type
     * @return
     */
    public static String[] getParamNamesByType(String type) {
        return ParamSession.getInstance().getParamNamesByType(type);
    }

    /**
     * 根据参数ID得到参数
     *
     * @param id
     * @return
     */
    public static Appparam getAppparamById(int id) {
        return ParamSession.getInstance().getAppparamById(id);
    }

    /**
     * 根据参数类型，得到所有该类型的参数对象
     *
     * @param type
     * @return
     */
    public static List<Appparam> getParamsByType(String type) {
        return ParamSession.getInstance().getParamsByType(type);
    }

    public static boolean permissionMoudle(String moudleName) {

        if (PermissionPanel.isExist(addMyPermession, moudleName)) {
            return true;
        }

        if (PermissionPanel.isExist(reduceMyPermession, moudleName)) {
            return false;
        }

        List<Appparam> appparams = AssetClientApp.getParamsByType("模块");
        UserSessionEntity session = AssetClientApp.getSessionMap();
        Departmenttb department = session.getDepartment();
        Usertb usertb = session.getUsertb();

        if (department == null || usertb == null) {
            logger.info(moudleName + "模块: 限制访问");
            return false;
        }
        if (appparams != null) //权限控制
        {
            boolean found = false;
            for (Appparam app : appparams) {
                if (app.getAppparamName().equals(moudleName)) { //模块名一致
                    found = true;
                    //得到权限
                    String rest = app.getAppparamDesc();
                    if (rest != null && !rest.trim().equals("")) {
                        String[] ress = rest.split(";"); //部门1:角色;部门2:角色;部门3:角色
                        for (String res : ress) {
                            // 部门:角色1:角色2
                            String[] departRoles = res.split(":");
                            if (departRoles[0].equals(department.getDepartmentName())) {
                                if (departRoles.length > 1) {
                                    for (int i = 1; i < departRoles.length; i++) {
                                        if (usertb.getUserRoles().indexOf(departRoles[i]) >= 0) {
                                            logger.info(moudleName + "模块: 允许访问");
                                            return true;
                                        }
                                    }
                                } else {//只配置了部门
                                    logger.info(moudleName + "模块: 允许访问");
                                    return true;
                                }
                            }

                        }
                    } else {
                        logger.info(moudleName + "模块: 限制访问");
                        return false;
                    }
                    break;
                }

            }
            if (!found) {
                return true;
            }
        } else {
            logger.info(moudleName + "模块: 允许访问");
            return true;
        }
        logger.info(moudleName + "模块: 限制访问");
        return false;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {

        if (getSessionMap() == null || getSessionMap().getUsertb() == null) {
            System.err.println("session is null, please re-login.");
            return;
        }
        AssetClientView view = new AssetClientView(this);

        addMyPermession = new ArrayList();
        reduceMyPermession = new ArrayList();

        show(view);
        view.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);;
        view.getFrame().setTitle(Constants.WINTITLE);

        StringBuilder message = new StringBuilder();
        Usertb usertb = sessionMap.getUsertb();
        message.append("用户:").append(usertb.getUserName());
        if (sessionMap.getUsertb().getUserRoles() != null) {
            message.append(",角色:[").append(usertb.getUserRoles() == null ? "" : usertb.getUserRoles()).append("]");
        }
        if (sessionMap.getDepartment() != null) {
            if (sessionMap.getDepartment().getDepartmentName() != null) {
                message.append(",所属部门:[").append(sessionMap.getDepartment().getDepartmentName()).append("]");
            }
        }

        String addPStr = usertb.getExt1() == null ? "" : usertb.getExt1();
        String reducePStr = usertb.getExt2() == null ? "" : usertb.getExt2();
        if (!addPStr.trim().equals("")) {
            String[] addArray = addPStr.split(",");
            for (String addS : addArray) {
                try {
                    int id = Integer.parseInt(addS);
                    Appparam app = AssetClientApp.getAppparamById(id);
                    if (app != null) {
                        addMyPermession.add(app);
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
            }

        }
        if (!reducePStr.trim().equals("")) {
            String[] reduceArray = reducePStr.split(",");

            for (String reS : reduceArray) {
                try {
                    int id = Integer.parseInt(reS);
                    Appparam app = AssetClientApp.getAppparamById(id);
                    if (app != null) {
                        reduceMyPermession.add(app);
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }

        //得到message
        view.setMessage(message.toString());

        //消息初始化
        messagePanel = new MessagePanel();

        //参数初始化
        ParamSession.getInstance().buildParamSession(view.loadMoudule()).execute();

        addExitListener(new Application.ExitListener() {

            @Override
            public boolean canExit(EventObject event) {
                int res = AssetMessage.CONFIRM("确定退出程序吗？");
                if (res == JOptionPane.OK_OPTION) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void willExit(EventObject event) {
            }
        });
    }

    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys();
                keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     *
     * @param root
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
        try {
            //ImageIcon icon = new ImageIcon(this.getClass().getResource("/com/jskj/asset/client/resources/icon.png"));
            root.setIconImage(Constants.logoIcon.getImage());

            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            // Font font = Constants.GLOBAL_FONT;
            InitGlobalFont(Constants.GLOBAL_FONT);

            // UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * A convenient static getter for the application instance.
     *
     * @return the instance of AssetClientApp
     */
    public static AssetClientApp getApplication() {
        return Application.getInstance(AssetClientApp.class);
    }

    /**
     * Main method launching the application.
     *
     * @param args
     */
    public static void main(final String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginMain(args).setVisible(true);
            }
        });
    }

    public static void startupApplication(String[] args, JFrame window, UserSessionEntity aSessionMap) {
        loginWindow = window;
        sessionMap = aSessionMap;
        launch(AssetClientApp.class, args);
    }

    public static void resetLoginWindow() {
        if (loginWindow != null) {
            loginWindow.setVisible(true);
        }
    }
}
