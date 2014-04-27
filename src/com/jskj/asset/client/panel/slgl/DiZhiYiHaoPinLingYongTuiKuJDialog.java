/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.panel.slgl.task.LingyongtuikuTask;
import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.LingyongtuikuDetailEntity;
import com.jskj.asset.client.bean.entity.Lingyongtuikudantb;
import com.jskj.asset.client.bean.entity.ZiChanLieBiaotb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author tt
 */
public class DiZhiYiHaoPinLingYongTuiKuJDialog extends javax.swing.JDialog {

    private static final Log logger = LogFactory.getLog(DiZhiYiHaoPinLingYongTuiKuJDialog.class);
    
    private LingyongtuikuDetailEntity lytk;
    private int userId;
    private String userName;
    private String department;
    private List<ZiChanLieBiaotb> zc;
    private float total = 0;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * Creates new form GuDingZiChanRuKu
     */
    public DiZhiYiHaoPinLingYongTuiKuJDialog(java.awt.Frame parent) {
        super(parent);
        initComponents();
        
        zc = new ArrayList<ZiChanLieBiaotb>();
        userId = AssetClientApp.getSessionMap().getUsertb().getUserId();
        userName = AssetClientApp.getSessionMap().getUsertb().getUserName();
        department = AssetClientApp.getSessionMap().getDepartment().getDepartmentName();
        
        jTextFieldShenqingren.setText(userName);
        jTextFieldDept.setText(department);
                
        jTextField1.setText(DanHao.getDanHao("YHTK"));
        jTextField1.setEditable(false);
        
        jTextField2.setText(dateformate.format(new Date()).toString());
        jTextField2.setEditable(false);
        
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTable1).createSingleEditModel(new String[][]{
            {"dzyhpId", "物品编号"}, {"dzyhpName", "物品名称", "true"}, {"dzyhpType", "物品类别"},{"dzyhpPinpai", "品牌", "false"},
            {"dzyhpXinghao", "型号"}, {"quantity", "数量", "true"},{"dzyhpValue", "原值", "false"}, {"total", "合价"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            @Override
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            @Override
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "dizhiyihaopin/";
            }

            @Override
            public String getConditionSQL() {
                int selectedColumn = jTable1.getSelectedColumn();
                int selectedRow = jTable1.getSelectedRow();
                Object newColumnObj = jTable1.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql = "(dzyhp_name like \"%" + newColumnObj.toString() + "%\""+ " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")";
                }
                return sql;
            }

            @Override
            public String[][] displayColumns() {
                return new String[][]{{"dzyhpId", "物品ID"},{"dzyhpName", "物品名称"}};
            }

            @Override
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object dzyhpId = bindedMap.get("dzyhpId");
                    Object dzyhpName = bindedMap.get("dzyhpName");
                    Object dzyhpType = bindedMap.get("dzyhpType");
                    Object dzyhpPinpai = bindedMap.get("dzyhpPinpai");
                    Object gdzcXinghao = bindedMap.get("dzyhpXinghao");
                    Object dzyhpValue = bindedMap.get("dzyhpValue");

                    editTable.insertValue(0, dzyhpId);
                    editTable.insertValue(1, dzyhpName);
                    editTable.insertValue(2, dzyhpType);
                    editTable.insertValue(3, dzyhpPinpai);
                    editTable.insertValue(4, gdzcXinghao);
                    editTable.insertValue(6, dzyhpValue);

//                    ZiChanLieBiaotb zclb = new ZiChanLieBiaotb();
//                    zclb.setCgsqId(jTextField1.getText());
//                    zclb.setCgzcId((Integer)dzyhpId);
//                    zclb.setQuantity(0);
//                    zc.add(zclb);
                }

            }
        });
        
        jTable1.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                int col = e.getColumn();
                int row = e.getFirstRow();

                if (col == 5 || col == 6) {
                    if ((!(("" + jTable1.getValueAt(row, 5)).equals("")))
                            && (!(("" + jTable1.getValueAt(row, 6)).equals("")))) {
                        int count = Integer.parseInt("" + jTable1.getValueAt(row, 5));
                        float price = Float.parseFloat("" + jTable1.getValueAt(row, 6));
                        jTable1.setValueAt(price * count, row, 7);
                    }
                    int rows = jTable1.getRowCount();
                    total = 0;
                    for(int i = 0; i < rows; i++) {
                        if(!(("" + jTable1.getValueAt(i, 7)).equals(""))){
                            total += Float.parseFloat("" + jTable1.getValueAt(i, 7));
                        }
                    }
                    totalprice.setText(total + "元");
                }
            }
            
        });
    }

    @Action
    public void exit() {
        this.dispose();
    }
    
    @Action
    public Task submitForm() throws ParseException{
        if(jTable1.getRowCount()-1 < 1) {
            AssetMessage.showMessageDialog(null, "请选择要退库的物品！");
            return null;
        }
        jTable1.getCellEditor(jTable1.getSelectedRow(),
                jTable1.getSelectedColumn()).stopCellEditing();
        lytk = new LingyongtuikuDetailEntity();
        Lingyongtuikudantb sqd = new Lingyongtuikudantb();
        sqd.setLytkId(jTextField1.getText());
        sqd.setLytkDate(dateformate.parse(jTextField2.getText()));
        sqd.setLytkRemark(jTextArea1.getText());
        sqd.setShenqingrenId(userId);
        sqd.setZhidanrenId(userId);
        
        zc = new ArrayList<ZiChanLieBiaotb>();
//        for (int i = 0; i < zc.size(); i++) {
        for (int i = 0; i < jTable1.getRowCount()-1; i++) {
            ZiChanLieBiaotb zclb = new ZiChanLieBiaotb();
            zclb.setCgsqId(jTextField1.getText());
            try{
                zclb.setCgzcId(Integer.parseInt("" + jTable1.getValueAt(i, 0)));
            }catch(NumberFormatException e){
                AssetMessage.ERRORSYS("第" + (i+1) + "个资产的ID不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            if (jTable1.getValueAt(i, 5).toString().equals("")) {
                AssetMessage.ERRORSYS("请输入第" + (i + 1) + "个物品的退库数量！", this);
                return null;
            }
            try {
                zclb.setQuantity(Integer.parseInt("" + jTable1.getValueAt(i, 5)));
            } catch (NumberFormatException e) {
                AssetMessage.ERRORSYS("第" + (i + 1) + "个物品的退库数量输入不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            float price = Float.parseFloat("" + jTable1.getValueAt(i, 6));
            zclb.setSaleprice(price);
            zclb.setTotalprice(zclb.getQuantity()*price);
            zclb.setIsCompleted(1);
            zclb.setStatus(9);
            zc.add(zclb);
        }
        
        lytk.setSqd(sqd);
        lytk.setZc(zc);        
        
        return new submitTask(lytk);
    }

    private class SubmitFormTask extends org.jdesktop.application.Task<Object, Void> {
        SubmitFormTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to SubmitFormTask fields, here.
            super(app);
        }
        @Override protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }
        @Override protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }
    
    private class submitTask extends LingyongtuikuTask{

        public submitTask(LingyongtuikuDetailEntity lytk) {
            super(lytk);
        }
        
        @Override
        protected void succeeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            JOptionPane.showMessageDialog(null, "提交成功！");
            exit();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            DiZhiYiHaoPinLingYongTuiKuJDialog diZhiYiHaoPinLingYongTuiKuJDialog = new DiZhiYiHaoPinLingYongTuiKuJDialog(mainFrame);
            diZhiYiHaoPinLingYongTuiKuJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(diZhiYiHaoPinLingYongTuiKuJDialog);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new BaseTable(null);
        totalprice = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        middlePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldDept = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldShenqingren = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(DiZhiYiHaoPinLingYongTuiKuJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setMinimumSize(new java.awt.Dimension(796, 577));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "资产编码", "资产名称", "资产类别", "规格", "型号", "单位", "数量", "单价", "合价"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
            jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
            jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N
            jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
            jTable1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title9")); // NOI18N
            jTable1.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title6")); // NOI18N
            jTable1.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
            jTable1.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
        }

        totalprice.setText(resourceMap.getString("totalprice.text")); // NOI18N
        totalprice.setName("totalprice"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalprice)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalprice)
                    .addComponent(jLabel5))
                .addGap(12, 12, 12))
        );

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(DiZhiYiHaoPinLingYongTuiKuJDialog.class, this);
        jButton1.setAction(actionMap.get("submitForm")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

        jButton10.setAction(actionMap.get("exit")); // NOI18N
        jButton10.setIcon(resourceMap.getIcon("jButton10.icon")); // NOI18N
        jButton10.setText(resourceMap.getString("jButton10.text")); // NOI18N
        jButton10.setBorderPainted(false);
        jButton10.setFocusable(false);
        jButton10.setName("jButton10"); // NOI18N
        jButton10.setOpaque(false);
        jToolBar1.add(jButton10);

        middlePanel.setName("middlePanel"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextField2.setEditable(false);
        jTextField2.setName("jTextField2"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldDept.setEditable(false);
        jTextFieldDept.setName("jTextFieldDept"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jTextFieldShenqingren.setEditable(false);
        jTextFieldShenqingren.setName("jTextFieldShenqingren"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout middlePanelLayout = new javax.swing.GroupLayout(middlePanel);
        middlePanel.setLayout(middlePanelLayout);
        middlePanelLayout.setHorizontalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldShenqingren, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, middlePanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2)
                            .addComponent(jTextFieldDept, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        middlePanelLayout.setVerticalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldDept, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldShenqingren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(middlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(middlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DiZhiYiHaoPinLingYongTuiKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiZhiYiHaoPinLingYongTuiKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiZhiYiHaoPinLingYongTuiKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiZhiYiHaoPinLingYongTuiKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DiZhiYiHaoPinLingYongTuiKuJDialog dialog = new DiZhiYiHaoPinLingYongTuiKuJDialog(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldDept;
    private javax.swing.JTextField jTextFieldShenqingren;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JLabel totalprice;
    // End of variables declaration//GEN-END:variables
}
