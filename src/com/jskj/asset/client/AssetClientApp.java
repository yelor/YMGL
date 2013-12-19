/*
 * AssetClientApp.java
 */
package com.jskj.asset.client;

import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.EventObject;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AssetClientApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        AssetClientView view = new AssetClientView(this);
        show(view);

        //界面初始化
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//获得屏幕大小
        int y = screenSize.height / 2 - view.getFrame().getHeight() / 2;
        int x = screenSize.width / 2 - view.getFrame().getWidth() / 2;
        view.getFrame().setBounds(x, y, Constants.MAINFRAME_WIDTH, Constants.MAINFRAME_HEIGHT);
        view.getFrame().setMinimumSize(new Dimension(Constants.MAINFRAME_WIDTH, Constants.MAINFRAME_HEIGHT));
        view.getFrame().setTitle(Constants.WINTITLE + "【当前登陆用户: Test1，所属工作组：防疫站】");

        //初始化必要的功能
        view.initStart();
        view.displayMainView();

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
            UIManager.put("ToolTip.font", font);
            UIManager.put("Table.font", font);
            UIManager.put("TableHeader.font", font);
            UIManager.put("TextField.font", font);
            UIManager.put("ComboBox.font", font);
            UIManager.put("TextField.font", font);
            UIManager.put("PasswordField.font", font);
            UIManager.put("TextArea.font", font);
            UIManager.put("TextPane.font", font);
            UIManager.put("EditorPane.font", font);
            UIManager.put("FormattedTextField.font", font);
            UIManager.put("Button.font", font);
            UIManager.put("CheckBox.font", font);
            UIManager.put("RadioButton.font", font);
            UIManager.put("ToggleButton.font", font);
            UIManager.put("ProgressBar.font", font);
            UIManager.put("DesktopIcon.font", font);
            UIManager.put("TitledBorder.font", font);
            UIManager.put("Label.font", font);
            UIManager.put("List.font", font);
            UIManager.put("TabbedPane.font", font);
            UIManager.put("MenuBar.font", font);
            UIManager.put("Menu.font", font);
            UIManager.put("MenuItem.font", font);
            UIManager.put("PopupMenu.font", font);
            UIManager.put("CheckBoxMenuItem.font", font);
            UIManager.put("RadioButtonMenuItem.font", font);
            UIManager.put("Spinner.font", font);
            UIManager.put("Tree.font", Constants.TREE_FONT);
            UIManager.put("ToolBar.font", font);
            UIManager.put("OptionPane.messageFont", font);
            UIManager.put("OptionPane.buttonFont", font);
        } catch (Exception e) {
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
        launch(AssetClientApp.class, args);
    }
}
