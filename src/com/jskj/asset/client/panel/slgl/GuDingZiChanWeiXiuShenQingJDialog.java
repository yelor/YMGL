/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.panel.slgl.task.ShenQingTask;
import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.ShenQingDetailEntity;
import com.jskj.asset.client.bean.entity.Shenqingdantb;
import com.jskj.asset.client.bean.entity.CaigoushenqingDetailEntity;
import com.jskj.asset.client.bean.entity.ZiChanLieBiaotb;
import com.jskj.asset.client.bean.entity.ZichanliebiaoDetailEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateHelper;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author tt
 */
public class GuDingZiChanWeiXiuShenQingJDialog extends BaseDialog {

    private ShenQingDetailEntity wxsq;
    private int userId;
    private String userName;
    private String department;
    private List<ZiChanLieBiaotb> zc;
    private float totalPrice;
    CaigoushenqingDetailEntity detail;
    private float total = 0;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Creates new form GuDingZiChanRuKu
     */
    public GuDingZiChanWeiXiuShenQingJDialog() {
        super();
        initComponents();

        zc = new ArrayList<ZiChanLieBiaotb>();
        userId = AssetClientApp.getSessionMap().getUsertb().getUserId();
        userName = AssetClientApp.getSessionMap().getUsertb().getUserName();
        department = AssetClientApp.getSessionMap().getDepartment().getDepartmentName();

        jingbanren.setText(userName);
        dept.setText(department);

        cgsqId.setText(DanHao.getDanHao(DanHao.TYPE_WXSQ));
        cgsqId.setEditable(false);

        shenqingdanDate.setText(dateformate.format(new Date()).toString());
        shenqingdanDate.setEditable(false);

        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTable1).createSingleEditModel(new String[][]{
            {"gdzcId", "资产编号"}, {"gdzcName", "资产名称", "true"}, {"gdzcType", "类别"}, {"gdzcPinpai", "品牌", "false"},
            {"gdzcXinghao", "型号"}, {"quantity", "数量", "true"},{"gdzcValue", "原值"}, {"total", "合价"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            @Override
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            @Override
            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "gdzc";
            }

            @Override
            public String getConditionSQL() {
                int selectedColumn = jTable1.getSelectedColumn();
                int selectedRow = jTable1.getSelectedRow();
                Object newColumnObj = jTable1.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql = "(gdzc_name like \"%" + newColumnObj.toString() + "%\"" + " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")";
                }
                return sql;
            }

            @Override
            public String[][] displayColumns() {
                return new String[][]{{"gdzcId", "资产ID"}, {"gdzcName", "资产名称"}};
            }

            @Override
            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object gdzcId = bindedMap.get("gdzcId");
                    Object gdzcName = bindedMap.get("gdzcName");
                    Object gdzcType = bindedMap.get("gdzcType");
                    Object gdzcPinpai = bindedMap.get("gdzcPinpai");
                    Object gdzcXinghao = bindedMap.get("gdzcXinghao");
                    Object gdzcValue = bindedMap.get("gdzcValue");

                    editTable.insertValue(0, gdzcId);
                    editTable.insertValue(1, gdzcName);
                    editTable.insertValue(2, gdzcType);
                    editTable.insertValue(3, gdzcPinpai);
                    editTable.insertValue(4, gdzcXinghao);
                    editTable.insertValue(6, gdzcValue);

//                    ZiChanLieBiaotb zclb = new ZiChanLieBiaotb();
//                    zclb.setCgsqId(cgsqId.getText());
//                    zclb.setCgzcId((Integer) gdzcId);
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

    public GuDingZiChanWeiXiuShenQingJDialog(final JDialog parent, CaigoushenqingDetailEntity detail) {
        super();
        initComponents();
        this.detail = detail;
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (parent != null) {
                    parent.setVisible(true);
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

        });
        super.bind(detail, middlePanel);
        totalprice.setText(detail.getDanjujine() + "元");
        jButton1.setEnabled(false);
        jLabel3.setText("维修费用：" + detail.getDanjujine() + "元");
        cgsqId.setEditable(false);
        shenqingdanDate.setText(DateHelper.format(detail.getShenqingdanDate(), "yyyy-MM-dd"));
        shenqingdanDate.setEditable(false);
        dept.setEditable(false);
        jingbanren.setEditable(false);
        shenqingdanRemark.setEditable(false);

        setListTable(detail.getResult());
    }

    public void setListTable(List<ZichanliebiaoDetailEntity> zclist) {

        int size = zclist.size();
        Object[][] o = new Object[size][6];
        for (int i = 0; i < size; i++) {
            ZichanliebiaoDetailEntity zclb = zclist.get(i);
            o[i] = new Object[]{zclb.getGdzcId(), zclb.getGdzcName(), zclb.getGdzcType(), zclb.getGdzcPinpai(), zclb.getGdzcValue(), zclb.getCount()};
        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                o,
                new String[]{
                    "资产编号", "资产名称", "类别", "品牌", "原值", "数量"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };
        });
    }

    @Action
    public void exit() {
        this.dispose();
    }

    @Action
    public Task submitForm() throws ParseException {
        if (jTable1.getRowCount()-1 < 1) {
            AssetMessage.showMessageDialog(null, "请选择要维修的资产！");
            return null;
        }
        jTable1.getCellEditor(jTable1.getSelectedRow(),
                jTable1.getSelectedColumn()).stopCellEditing();
        wxsq = new ShenQingDetailEntity();
        Shenqingdantb sqd = new Shenqingdantb();
        sqd.setShenqingdanId(cgsqId.getText());
        sqd.setShenqingdanDate(dateformate.parse(shenqingdanDate.getText()));
        sqd.setShenqingdanRemark(shenqingdanRemark.getText());
        sqd.setJingbanrenId(userId);
        sqd.setZhidanrenId(userId);
        sqd.setDanjuleixingId(21);
        sqd.setDanjujine((float) 0);

        zc = new ArrayList<ZiChanLieBiaotb>();
//        for (int i = 0; i < zc.size(); i++) {
        for (int i = 0; i < jTable1.getRowCount()-1; i++) {
            ZiChanLieBiaotb zclb = new ZiChanLieBiaotb();
            zclb.setCgsqId(cgsqId.getText());
            try{
                zclb.setCgzcId(Integer.parseInt("" + jTable1.getValueAt(i, 0)));
            }catch(NumberFormatException e){
                AssetMessage.ERRORSYS("第" + (i+1) + "个资产的ID不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            if (jTable1.getValueAt(i, 5).toString().equals("")) {
                AssetMessage.ERRORSYS("请输入第" + (i + 1) + "个资产的维修数量！", this);
                return null;
            }
            try {
                zclb.setQuantity(Integer.parseInt("" + jTable1.getValueAt(i, 5)));
            } catch (NumberFormatException e) {
                AssetMessage.ERRORSYS("第" + (i + 1) + "个资产的维修数量输入不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            zclb.setSaleprice(Float.parseFloat("" + jTable1.getValueAt(i, 6)));
            zclb.setTotalprice(zclb.getQuantity() * zclb.getSaleprice());
            zclb.setIsCompleted(0);
            zclb.setStatus(5);
            zc.add(zclb);
        }

        wxsq.setSqd(sqd);
        wxsq.setZc(zc);

        return new submitTask(wxsq);
    }

    private class submitTask extends ShenQingTask {

        public submitTask(ShenQingDetailEntity cgsq) {
            super(cgsq);
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
            GuDingZiChanWeiXiuShenQingJDialog guDingZiChanWeiXiuShenQingJDialog = new GuDingZiChanWeiXiuShenQingJDialog();
            guDingZiChanWeiXiuShenQingJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(guDingZiChanWeiXiuShenQingJDialog);
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
        jLabel7 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        middlePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cgsqId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        shenqingdanDate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dept = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jingbanren = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        shenqingdanRemark = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(GuDingZiChanWeiXiuShenQingJDialog.class);
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

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalprice)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalprice)
                    .addComponent(jLabel7))
                .addGap(13, 13, 13))
        );

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(GuDingZiChanWeiXiuShenQingJDialog.class, this);
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

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jToolBar1.add(jLabel5);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jToolBar1.add(jLabel3);

        middlePanel.setName("middlePanel"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        cgsqId.setText(resourceMap.getString("cgsqId.text")); // NOI18N
        cgsqId.setName("cgsqId"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        shenqingdanDate.setEditable(false);
        shenqingdanDate.setName("shenqingdanDate"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        dept.setEditable(false);
        dept.setName("dept"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jingbanren.setEditable(false);
        jingbanren.setName("jingbanren"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        shenqingdanRemark.setColumns(20);
        shenqingdanRemark.setRows(2);
        shenqingdanRemark.setName("shenqingdanRemark"); // NOI18N
        jScrollPane2.setViewportView(shenqingdanRemark);

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
                            .addComponent(jingbanren, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, middlePanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(cgsqId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(shenqingdanDate)
                            .addComponent(dept, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        middlePanelLayout.setVerticalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(cgsqId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(shenqingdanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dept, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(GuDingZiChanWeiXiuShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanWeiXiuShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanWeiXiuShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanWeiXiuShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                GuDingZiChanWeiXiuShenQingJDialog dialog = new GuDingZiChanWeiXiuShenQingJDialog(new javax.swing.JFrame());
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cgsqId;
    private javax.swing.JTextField dept;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jingbanren;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JTextField shenqingdanDate;
    private javax.swing.JTextArea shenqingdanRemark;
    private javax.swing.JLabel totalprice;
    // End of variables declaration//GEN-END:variables
}
