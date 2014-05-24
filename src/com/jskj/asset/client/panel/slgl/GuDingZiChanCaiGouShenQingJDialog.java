/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.panel.slgl.task.ShenQingTask;
import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.CaigoushenqingDetailEntity;
import com.jskj.asset.client.bean.entity.ShenQingDetailEntity;
import com.jskj.asset.client.bean.entity.Shenqingdantb;
import com.jskj.asset.client.bean.entity.ZiChanLieBiaotb;
import com.jskj.asset.client.bean.entity.ZichanliebiaoDetailEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
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
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author tt
 */
public class GuDingZiChanCaiGouShenQingJDialog extends BaseDialog {

    public static final Logger logger = Logger.getLogger(GuDingZiChanCaiGouShenQingJDialog.class);
    private ShenQingDetailEntity cgsq;
    private int userId;
    private String userName;
    private int supplierId;
    private List<ZiChanLieBiaotb> zc;
    CaigoushenqingDetailEntity detail;
    private float total = 0;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Creates new form GuDingZiChanRuKu
     *
     * @param parent
     * @param modal
     */
    public GuDingZiChanCaiGouShenQingJDialog() {
        super();
        initComponents();

        zc = new ArrayList<ZiChanLieBiaotb>();
        userId = AssetClientApp.getSessionMap().getUsertb().getUserId();
        userName = AssetClientApp.getSessionMap().getUsertb().getUserName();

        cgsqId.setText(DanHao.getDanHao(DanHao.TYPE_GDZC));
        cgsqId.setEditable(false);

        shenqingdanDate.setText(dateformate.format(new Date()).toString());
        shenqingdanDate.setEditable(false);

        jingbanren.setText(userName);
        jingbanren.setEditable(false);

        ((BaseTextField) supplier).registerPopup(new IPopupBuilder() {

            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "supplier";
            }

            public String getConditionSQL() {
                String sql = " supplier_type = 1 ";
                if (!supplier.getText().trim().equals("")) {
                    sql += "  and (supplier_name like \"%" + supplier.getText() + "%\"" + " or supplier_zujima like \"%" + supplier.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"supplierName", "单位名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    supplier.setText(bindedMap.get("supplierName") == null ? "" : bindedMap.get("supplierName").toString());
                    supplierId = (Integer) bindedMap.get("supplierId");
                }
            }
        });

        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTable1).createSingleEditModel(new String[][]{
            {"gdzcId", "资产编号"}, {"gdzcName", "资产名称", "true"}, {"gdzcType", "类别"}, {"gdzcPinpai", "品牌", "false"},
            {"gdzcXinghao", "型号"}, {"unitId", "单位", "false"},{"quantity", "数量", "true"}, {"gdzcValue", "采购价", "true"}, {"total", "合价"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "gdzc";
            }

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

            public String[][] displayColumns() {
                return new String[][]{{"gdzcId", "资产ID"}, {"gdzcName", "资产名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object gdzcId = bindedMap.get("gdzcId");
                    Object gdzcName = bindedMap.get("gdzcName");
                    Object gdzcType = bindedMap.get("gdzcType");
                    Object gdzcPinpai = bindedMap.get("gdzcPinpai");
                    Object gdzcXinghao = bindedMap.get("gdzcXinghao");
                    Object gdzcDanwei = bindedMap.get("unitId");

                    editTable.insertValue(0, gdzcId);
                    editTable.insertValue(1, gdzcName);
                    editTable.insertValue(2, gdzcType);
                    editTable.insertValue(3, gdzcPinpai);
                    editTable.insertValue(4, gdzcXinghao);
                    editTable.insertValue(5, gdzcDanwei);

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

                if (col == 6 || col == 7) {
                    if ((!(("" + jTable1.getValueAt(row, 6)).equals("")))
                            && (!(("" + jTable1.getValueAt(row, 7)).equals("")))) {
                        int count = Integer.parseInt("" + jTable1.getValueAt(row, 6));
                        float price = Float.parseFloat("" + jTable1.getValueAt(row, 7));
                        jTable1.setValueAt(price * count, row, 8);
                    }
                    int rows = jTable1.getRowCount();
                    total = 0;
                    for(int i = 0; i < rows; i++) {
                        if(!(("" + jTable1.getValueAt(i, 8)).equals(""))){
                            total += Float.parseFloat("" + jTable1.getValueAt(i, 8));
                        }
                    }
                    totalprice.setText(total + "元");
                }
            }
            
        });
    }

    public GuDingZiChanCaiGouShenQingJDialog(final JDialog parent, CaigoushenqingDetailEntity detail) {
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
        jButton10.setEnabled(false);
        cgsqId.setEditable(false);
        shenqingdanDate.setText(DateHelper.format(detail.getShenqingdanDate(), "yyyy-MM-dd"));
        shenqingdanDate.setEditable(false);
        supplier.setEditable(false);
        jingbanren.setEditable(false);
        shenqingdanRemark.setEditable(false);

        setListTable(detail.getResult());
    }

    public void setListTable(List<ZichanliebiaoDetailEntity> zclist) {

        int size = zclist.size();
        Object[][] o = new Object[size][6];
        for (int i = 0; i < size; i++) {
            ZichanliebiaoDetailEntity zclb = zclist.get(i);
            o[i] = new Object[]{zclb.getGdzcId(), zclb.getGdzcName(), zclb.getGdzcType(), zclb.getGdzcPinpai(),zclb.getGdzcXinghao(), zclb.getUnitId(), zclb.getCount(), zclb.getSaleprice(), zclb.getSaleprice() * zclb.getCount()};
        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                o,
                new String[]{
                    "资产编号", "资产名称", "类别", "品牌", "型号", "单位", "数量","采购价", "合价"
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
        if (supplier.getText().isEmpty()) {
            AssetMessage.ERRORSYS("请输入供应单位！", this);
            return null;
        }
        if (jTable1.getRowCount()-1 < 1) {
            AssetMessage.ERRORSYS("请选择要采购的资产！", this);
            return null;
        }
        jTable1.getCellEditor(jTable1.getSelectedRow(),
                jTable1.getSelectedColumn()).stopCellEditing();
        cgsq = new ShenQingDetailEntity();
        Shenqingdantb sqd = new Shenqingdantb();
        sqd.setShenqingdanId(cgsqId.getText());
        sqd.setShenqingdanDate(dateformate.parse(shenqingdanDate.getText()));
        sqd.setSupplierId(supplierId);
        sqd.setJingbanrenId(userId);
        sqd.setZhidanrenId(userId);
        sqd.setDanjuleixingId(2);
        sqd.setIsCompleted(0);
        sqd.setIsPaid(0);
        sqd.setShenqingdanRemark(shenqingdanRemark.getText());
        total = 0;
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
            for(int j = 0; j < i; j++) {
                int id = Integer.parseInt("" + jTable1.getValueAt(j, 0));
                if(zclb.getCgzcId() == id) {
                    AssetMessage.ERRORSYS("第" + (i+1) + "个资产与第" + (j+1) + "个资产ID重复，每张单据单个物品只能出现一次，请删除其中一个！");
                    return null;
                }
            }
            if (jTable1.getValueAt(i, 6).toString().equals("")) {
                AssetMessage.ERRORSYS("请输入第" + (i + 1) + "个资产的采购数量！", this);
                return null;
            }
            if (jTable1.getValueAt(i, 7).toString().equals("")) {
                AssetMessage.ERRORSYS("请输入第" + (i + 1) + "个资产的采购价！", this);
                return null;
            }
            try{
                zclb.setQuantity(Integer.parseInt("" + jTable1.getValueAt(i, 6)));
            }catch(NumberFormatException e){
                AssetMessage.ERRORSYS("第" + (i+1) + "个资产的采购数量输入不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            try{
                float price = Float.parseFloat("" + jTable1.getValueAt(i, 7));
                zclb.setSaleprice(price);
                zclb.setTotalprice(zclb.getQuantity() * price);
            }catch(NumberFormatException e){
                AssetMessage.ERRORSYS("第" + (i+1) + "个资产的采购价格输入不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            zclb.setIsCompleted(0);
            zclb.setStatus(0);
            total += zclb.getTotalprice();
            zc.add(zclb);
        }
        sqd.setDanjujine(total);

        cgsq.setSqd(sqd);
        cgsq.setZc(zc);

        return new submitTask(cgsq);
    }

    private class submitTask extends ShenQingTask {

        public submitTask(ShenQingDetailEntity cgsq) {
            super(cgsq);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            JOptionPane.showMessageDialog(null, "提交成功！");
            exit();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            GuDingZiChanCaiGouShenQingJDialog guDingZiChanCaiGouSQSHJDialog = new GuDingZiChanCaiGouShenQingJDialog();
            guDingZiChanCaiGouSQSHJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(guDingZiChanCaiGouSQSHJDialog);
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

        jPanel2 = new javax.swing.JPanel();
        middlePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cgsqId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        shenqingdanDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        supplier = new BaseTextField();
        jLabel4 = new javax.swing.JLabel();
        jingbanren = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        shenqingdanRemark = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new BaseTable(null);
        jToolBar1 = new javax.swing.JToolBar();
        jButton10 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        totalprice = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(GuDingZiChanCaiGouShenQingJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel2.setMinimumSize(new java.awt.Dimension(796, 577));
        jPanel2.setName("jPanel2"); // NOI18N

        middlePanel.setName("middlePanel"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        cgsqId.setText(resourceMap.getString("cgsqId.text")); // NOI18N
        cgsqId.setName("cgsqId"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        shenqingdanDate.setName("shenqingdanDate"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        supplier.setName("supplier"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jingbanren.setName("jingbanren"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

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
                    .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel1))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cgsqId)
                            .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 324, Short.MAX_VALUE)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(shenqingdanDate)
                            .addComponent(jingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jLabel3)
                    .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "资产编号", "资产名称", "类别", "品牌", "单价", "数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
            jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
            jTable1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
        }

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(GuDingZiChanCaiGouShenQingJDialog.class, this);
        jButton10.setAction(actionMap.get("submitForm")); // NOI18N
        jButton10.setIcon(resourceMap.getIcon("jButton10.icon")); // NOI18N
        jButton10.setText(resourceMap.getString("jButton10.text")); // NOI18N
        jButton10.setBorderPainted(false);
        jButton10.setFocusable(false);
        jButton10.setName("jButton10"); // NOI18N
        jButton10.setOpaque(false);
        jToolBar1.add(jButton10);

        jButton13.setAction(actionMap.get("print")); // NOI18N
        jButton13.setIcon(resourceMap.getIcon("jButton13.icon")); // NOI18N
        jButton13.setText(resourceMap.getString("jButton13.text")); // NOI18N
        jButton13.setBorderPainted(false);
        jButton13.setFocusable(false);
        jButton13.setName("jButton13"); // NOI18N
        jButton13.setOpaque(false);
        jToolBar1.add(jButton13);

        jButton15.setAction(actionMap.get("exit")); // NOI18N
        jButton15.setIcon(resourceMap.getIcon("jButton15.icon")); // NOI18N
        jButton15.setText(resourceMap.getString("jButton15.text")); // NOI18N
        jButton15.setBorderPainted(false);
        jButton15.setFocusable(false);
        jButton15.setName("jButton15"); // NOI18N
        jButton15.setOpaque(false);
        jToolBar1.add(jButton15);

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        totalprice.setText(resourceMap.getString("totalprice.text")); // NOI18N
        totalprice.setName("totalprice"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(middlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalprice)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(middlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(totalprice))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 796, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(GuDingZiChanCaiGouShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanCaiGouShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanCaiGouShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanCaiGouShenQingJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                GuDingZiChanCaiGouShenQingJDialog dialog = new GuDingZiChanCaiGouShenQingJDialog(new javax.swing.JFrame(),true);
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

    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", cgsqId.getText()},
                    {"制单日期", shenqingdanDate.getText()},
                    {"供应单位", supplier.getText()},
                    {"申请人", jingbanren.getText()},
                    {"备注", shenqingdanRemark.getText(),"single"}}, 
                    jTable1,
                    new String[][]{{"", ""},
                    {"总金额", totalprice.getText()}
                    });
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cgsqId;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jingbanren;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JTextField shenqingdanDate;
    private javax.swing.JTextArea shenqingdanRemark;
    private javax.swing.JTextField supplier;
    private javax.swing.JLabel totalprice;
    // End of variables declaration//GEN-END:variables
}
