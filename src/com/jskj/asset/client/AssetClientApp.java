/*
 * AssetClientApp.java
 */
package com.jskj.asset.client;

import com.jskj.asset.client.bean.UserSessionEntity;
import com.jskj.asset.client.bean.entity.Usertb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import java.awt.Font;
import java.util.EventObject;
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
        view.getFrame().setTitle(Constants.WINTITLE + "【当前登陆用户: " + getSessionMap().getUsertb().getUserName() + "，角色：" + getSessionMap().getUsertb().getUserRoles() + "】");

        //初始化必要的功能
        view.loadMoudule().execute();
        
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
            UIManager.put("Tree.font", Constants.TREE_FONT);

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
     */
    public static void main(String[] args) {
//        try {
//            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
//            UIManager.put("ToolBar.isPaintPlainBackground", Boolean.TRUE);
//            UIManager.put("RootPane.setupButtonVisible", false);
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(3, 20, 2, 20));
//        } catch (Exception ex) {
//            logger.error(ex);
//        }
        UserSessionEntity session = new UserSessionEntity();
        Usertb usertb = new Usertb();
        usertb.setUserName("Debug User");
        usertb.setUserRoles("管理用户");
        session.setUsertb(usertb);
        AssetClientApp.setSessionMap(session);
        
        launch(AssetClientApp.class, args);
    }
    
    public static void startupApplication(String[] args, JFrame window, UserSessionEntity aSessionMap) {
        loginWindow = window;
        sessionMap = aSessionMap;
        launch(AssetClientApp.class, args);
    }
}
