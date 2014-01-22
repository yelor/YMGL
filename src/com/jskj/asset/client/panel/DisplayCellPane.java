/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DisplayCellPane.java
 *
 * Created on Dec 28, 2010, 12:38:47 PM
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.Attach;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseFileChoose;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import javax.swing.ActionMap;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
;

/**
 *
 * @author woderchen
 */
public class DisplayCellPane extends javax.swing.JDialog {

    private static Logger logger = Logger.getLogger(DisplayCellPane.class);
    private javax.swing.JPanel jPanelAttath;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jButtonBrowse;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JButton jButtonDownload;

    /** Creates new form DisplayCellPane */
    public DisplayCellPane(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        //暂时关闭大对象的处理
        //attachInit();
    }

    private void attachInit() {
        ActionMap actionMap = Application.getInstance(AssetClientApp.class).getContext().getActionMap(DisplayCellPane.class, this);

        jTextField1 = new javax.swing.JTextField();
        jButtonBrowse = new javax.swing.JButton();
        //jButtonBrowse.setFont(Constants.GLOBAL_FONT);
        jButtonBrowse.setAction(actionMap.get("browseDirectory"));
        jButtonBrowse.setText("浏览");

        jButtonDownload = new javax.swing.JButton();
        jButtonDownload.setAction(actionMap.get("download"));
        jButtonDownload.setText("下载");
        //jButtonDownload.setFont(Constants.GLOBAL_FONT);

        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressBar1.setMaximum(100);
        jProgressBar1.setMaximum(0);
        jProgressBar1.setValue(0);

        jPanelAttath = new javax.swing.JPanel();
        jPanelAttath.setBorder(javax.swing.BorderFactory.createTitledBorder("附件下载")); // NOI18N
        jPanelAttath.setName("jPanelAttath"); // NOI18N

        javax.swing.GroupLayout jPanelAttathLayout = new javax.swing.GroupLayout(jPanelAttath);
        jPanelAttath.setLayout(jPanelAttathLayout);
        jPanelAttathLayout.setHorizontalGroup(
                jPanelAttathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelAttathLayout.createSequentialGroup().addContainerGap().addGroup(jPanelAttathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE).addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanelAttathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAttathLayout.createSequentialGroup().addComponent(jButtonBrowse).addGap(12, 12, 12)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAttathLayout.createSequentialGroup().addComponent(jButtonDownload).addContainerGap()))));
        jPanelAttathLayout.setVerticalGroup(
                jPanelAttathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelAttathLayout.createSequentialGroup().addGroup(jPanelAttathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButtonBrowse)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanelAttathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButtonDownload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));



    }

    private void changeLayout(boolean text) {
        Component com = jPanelMain.getComponent(jPanelMain.getComponentCount() - 1);
        if (text) {
            if (com instanceof javax.swing.JPanel) {
                jPanelMain.remove(jPanelAttath);
                textLayout();
            }
        } else {
            if (com instanceof javax.swing.JScrollPane) {
                jPanelMain.remove(jScrollPaneText);
                attachLayout();
            }
        }
    }

    private void attachLayout() {
        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanelAttath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE));
        jPanelMainLayout.setVerticalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanelAttath, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE));
        pack();
    }

    private void textLayout() {
        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPaneText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE));
        jPanelMainLayout.setVerticalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPaneText, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE));
        pack();
    }

    @Action
    public void browseDirectory() {
        BaseFileChoose hosFileChoose = new BaseFileChoose(this);
        String filePath = hosFileChoose.showSaveDialogWithFile(true);
        if (!filePath.equals("")) {
            jTextField1.setText(filePath);
        }
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jScrollPaneText = new javax.swing.JScrollPane();
        jTextContext = new javax.swing.JTextPane(){
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return (getSize().width < getParent().getSize().width);
            }

            @Override
            public void setSize(Dimension d) {
                if (d.width < getParent().getSize().width) {
                    d.width = getParent().getSize().width;
                }
                super.setSize(d);
            }
        };
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(DisplayCellPane.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanelMain.setName("jPanelMain"); // NOI18N

        jScrollPaneText.setAutoscrolls(true);
        jScrollPaneText.setName("jScrollPaneText"); // NOI18N

        jTextContext.setEditable(false);
        jTextContext.setFont(resourceMap.getFont("jTextContext.font")); // NOI18N
        jTextContext.setName("jTextContext"); // NOI18N
        jScrollPaneText.setViewportView(jTextContext);

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneText, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(DisplayCellPane.class, this);
        jButton1.setAction(actionMap.get("close")); // NOI18N
        jButton1.setFont(resourceMap.getFont("jButton1.font")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(228, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    /**
//    * @param args the command line arguments
//    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                DisplayCellPane dialog = new DisplayCellPane(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
    @Action
    public void close() {
        this.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPaneText;
    private javax.swing.JTextPane jTextContext;
    // End of variables declaration//GEN-END:variables

    /**
     * @param jTextContext the jTextContext to set
     */
    public void setCellValue(Object value) {
        if (value instanceof Attach) {
            changeLayout(false);
        } else {
            jTextContext.setText("");
            changeLayout(true);
            jTextContext.setText(value.toString());
            jTextContext.setCaretPosition(0);
        }
    }

    private class AttachLobTask extends AttachTask {

        private File file;

        public AttachLobTask(Attach attach, File file) {
            super(attach);
            this.file = file;
        }

        @Override
        public void onSucceeded(Object object) {
            if (object instanceof Exception) {
                logger.error(object);
            }
            if (object != null) {
                if (object instanceof Blob) {
                    logger.debug("fetch blob data.");
                    try {
                        Blob blob = (Blob) object;
                        makefile(blob.getBinaryStream());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error(ex);
                    }
                } else if (object instanceof Clob) {
                    logger.debug("fetch clob data.");
                    try {
                        Clob lob = (Clob) object;
                        makefile(lob.getAsciiStream());
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                } else if (object instanceof byte[]) {
                    logger.debug("fetch byte data.");
                    try {
                        makefileBytes((byte[]) object);
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                } else {
                    AssetMessage.INFO("暂时不能处理 数据类型！");
                }
            }
        }

        private void makefile(InputStream inStream) {
            try {
                //这次把BLOB信息对应到输入流中
                //定义文件输出流
                FileOutputStream outStream = new FileOutputStream(file);

                byte[] fileByte = new byte[1024];
                int fileLen = 0;
                //BLOB信息写到文件中
                while ((fileLen = inStream.read(fileByte)) != -1) {
                    outStream.write(fileByte, 0, fileLen);
                }
                outStream.flush();
                outStream.close();
                inStream.close();
                AssetMessage.CONFIRM("数据获取成功!");
            } catch (Exception e) {
                logger.error(e);
            }
        }

        private void makefileBytes(byte[] bytes) {
            try {
                //这次把BLOB信息对应到输入流中
                //定义文件输出流
                FileOutputStream outStream = new FileOutputStream(file);
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
}
