/*
 * AssetClientApp.java
 */
package com.jskj.asset.client;

import com.jskj.asset.client.bean.ParamSession;
import com.jskj.asset.client.bean.UserSessionEntity;
import com.jskj.asset.client.bean.entity.Appparam;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.login.LoginMain;
import com.jskj.asset.client.panel.MessagePanel;
import java.awt.Font;
import java.util.EventObject;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AssetClientApp extends SingleFrameApplication {

    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AssetClientApp.class);

    private static UserSessionEntity sessionMap;

    private static JFrame loginWindow;

    public static String[] DEFAULT_FONT = new String[]{
        "Table.font", "TableHeader.font", "CheckBox.font", "Tree.font", "Viewport.font", "ProgressBar.font",
        "RadioButtonMenuItem.font", "ToolBar.font", "ColorChooser.font", "ToggleButton.font", "Panel.font",
        "TextArea.font", "Menu.font", "TableHeader.font" // ,"TextField.font"
        , "OptionPane.font", "MenuBar.font", "Button.font", "Label.font",
        "PasswordField.font", "ScrollPane.font", "MenuItem.font",
        "ToolTip.font", "List.font", "EditorPane.font", "Table.font",
        "TabbedPane.font", "RadioButton.font", "CheckBoxMenuItem.font",
        "TextPane.font", "PopupMenu.font", "TitledBorder.font", "ComboBox.font"
    };

    public static MessagePanel getMessagePanel() {
        return new MessagePanel();
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
     * 根据参数类型，得到所有该类型的参数对象
     *
     * @param type
     * @return
     */
    public static List<Appparam> getParamsByType(String type) {
        return ParamSession.getInstance().getParamsByType(type);
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

        show(view);
        view.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);;
        view.getFrame().setTitle(Constants.WINTITLE);

        StringBuilder message = new StringBuilder();
        message.append("用户:").append(sessionMap.getUsertb().getUserName());
        if (sessionMap.getUsertb().getUserRoles() != null) {
            message.append(",角色:[").append(sessionMap.getUsertb().getUserRoles() == null ? "" : sessionMap.getUsertb().getUserRoles()).append("]");
        }
        if (sessionMap.getDepartment() != null) {
            if (sessionMap.getDepartment().getDepartmentName() != null) {
                message.append(",所属部门:[").append(sessionMap.getDepartment().getDepartmentName()).append("]");
            }
        }
        //得到message
        view.setMessage(message.toString());

        //初始化必要的功能
        view.loadMoudule().execute();

        //参数初始化
        ParamSession.getInstance().buildParamSession().execute();

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
            ImageIcon icon = new ImageIcon(this.getClass().getResource("/com/jskj/asset/client/resources/icon.png"));
            root.setIconImage(icon.getImage());
            Font font = Constants.GLOBAL_FONT;
            for (String DEFAULT_FONT1 : AssetClientApp.DEFAULT_FONT) {
                UIManager.put(DEFAULT_FONT1, font);
            }
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
//        try {
//            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
//            UIManager.put("ToolBar.isPaintPlainBackground", Boolean.TRUE);
//            UIManager.put("RootPane.setupButtonVisible", false);
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(3, 20, 2, 20));
//        } catch (Exception ex) {
//            logger.error(ex);
//        }
        launch(AssetClientApp.class, args);
    }

    public static void resetLoginWindow() {
        if (loginWindow != null) {
            loginWindow.setVisible(true);
        }
    }
}
