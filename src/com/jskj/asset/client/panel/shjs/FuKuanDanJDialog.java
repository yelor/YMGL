/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.shjs;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.FukuanDetailEntity;
import com.jskj.asset.client.bean.entity.Fukuandantb;
import com.jskj.asset.client.bean.entity.FukuanshenqingDetailEntity;
import com.jskj.asset.client.bean.entity.ShenqingdanAll;
import com.jskj.asset.client.bean.entity.Qitafukuanliebiaotb;
import com.jskj.asset.client.bean.entity.Yingfukuandanjutb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.panel.shjs.task.FukuandanTask;
import com.jskj.asset.client.panel.shjs.task.YingfuliebiaoFindTask;
import com.jskj.asset.client.util.BindTableHelper;
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
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class FuKuanDanJDialog extends BaseDialog {

    private final static Logger logger = Logger.getLogger(FuKuanDanJDialog.class);
    private int userId;
    private String userName;
    private int supplierId;
    private float total;
    private List<Qitafukuanliebiaotb> ydlb;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<Yingfukuandanjutb> currentPageData;
    private BindTableHelper<Yingfukuandanjutb> bindTable;
    private HashMap parameterMap;
    private String conditionSql;
    

    /**
     * Creates new form FKDJDialog
     */
    public FuKuanDanJDialog() {
        super();
        initComponents();

        ydlb = new ArrayList<Qitafukuanliebiaotb>();
        userId = AssetClientApp.getSessionMap().getUsertb().getUserId();
        userName = AssetClientApp.getSessionMap().getUsertb().getUserName();

        fukuandanId.setText(DanHao.getDanHao(DanHao.TYPE_FKDJ));
        fukuandanDate.setText(dateformate.format(new Date()).toString());
        yingfu.setText("0.0");
        shenqingren.setText(userName);
        
        conditionSql = "";
        parameterMap = new HashMap();
        parameterMap.put("conditionSql", "");
        parameterMap.put("serviceId", "yflist");

        ((BaseTextField) supplier).registerPopup(new IPopupBuilder() {

            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "supplier";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!supplier.getText().trim().equals("")) {
                    sql = "(supplier_name like \"%" + supplier.getText() + "%\"" + " or supplier_zujima like \"%" + supplier.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"supplierName", "单位名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    supplier.setText(bindedMap.get("supplierName") == null ? "" : bindedMap.get("supplierName").toString());
                    accountNum.setText(bindedMap.get("supplierBanknumber") == null ? "" : bindedMap.get("supplierBanknumber").toString());
                    if (!accountNum.getText().isEmpty()) {
                        accountNum.setEditable(false);
                    }
                    supplierId = (Integer) bindedMap.get("supplierId");
                    parameterMap.put("supplierId", supplierId);
                    new RefreshTask().execute();
                }
            }
        });
        bindTable = new BindTableHelper<Yingfukuandanjutb>(jTable1, new ArrayList<Yingfukuandanjutb>());
        bindTable.createTable(new String[][]{{"yuandanId", "源单编号"}, {"zhidandate", "制单日期"},{"yuandantype", "源单类型"}
                , {"danjujine", "单据金额"}, {"increase", "增加金额"}, {"decrease", "减少金额"}, {"yingfu", "应付金额"}, {"remark", "备注"}});
        bindTable.setColumnType(Date.class, 2);
        //bindTable.setColumnType(Float.class, 8, 9);
        bindTable.bind().setColumnWidth(new int[]{0, 150}, new int[]{1, 150}, new int[]{2, 100}).setRowHeight(25);
    }

    public FuKuanDanJDialog(final JDialog parent, FukuanshenqingDetailEntity detail) {
        super();
        initComponents();

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
        super.bind(detail, jPanel1);
        jButton1.setEnabled(false);
        fukuandanId.setEditable(false);
        fukuandanDate.setText(DateHelper.format(detail.getFukuandanDate(), "yyyy-MM-dd"));
        supplier.setEditable(false);
        accountNum.setEditable(false);
        shenqingdanRemark.setEditable(false);
        fukuan.setEditable(false);
        youhui.setEditable(false);

        setListTable(detail.getYfklist());
    }

    public void setListTable(List<Yingfukuandanjutb> zclist) {

        int size = zclist.size();
        Object[][] o = new Object[size][6];
        for (int i = 0; i < size; i++) {
            Yingfukuandanjutb zclb = zclist.get(i);
            String date = DateHelper.format(zclb.getZhidandate(), "yyyy-MM-dd");
            o[i] = new Object[]{zclb.getYuandanId(), date, zclb.getYuandantype()
                    , zclb.getDanjujine(), zclb.getIncrease(), zclb.getDecrease(), zclb.getYingfu(), zclb.getRemark()};
        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                o,
                new String[]{
                    "源单编号", "制单日期", "源单类型", "单据金额", "增加金额", "减少金额", "应付金额", "备注"
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
    
    private class RefreshTask extends YingfuliebiaoFindTask{

        RefreshTask() {
            super(parameterMap);
        }

        @Override
        public void responseResult(CommFindEntity<Yingfukuandanjutb> response) {
            
            logger.debug("get current size:" + response.getResult().size());

            //存下所有的数据
            currentPageData = response.getResult();

            float total = 0;
            for(int i = 0; i < currentPageData.size(); i++){
                currentPageData.get(i).setFukuandanId(fukuandanId.getText());
                total += currentPageData.get(i).getYingfu();
                if(i > 0){
                    currentPageData.get(i).setYingfu(currentPageData.get(i - 1).getYingfu() +
                            currentPageData.get(i).getYingfu());
                }
            }
            yingfu.setText("" + total);
            bindTable.refreshData(currentPageData);
        }
        
    }

    @Action
    public Task submitForm() throws ParseException {
        if (supplier.getText().isEmpty()) {
            AssetMessage.ERRORSYS("请输入供应单位！", this);
            return null;
        }
        if (fukuan.getText().isEmpty()) {
            AssetMessage.ERRORSYS("请输入本次付款金额！", this);
            return null;
        }
        if (youhui.getText().isEmpty()) {
            AssetMessage.ERRORSYS("请输入优惠金额！", this);
            return null;
        }
        FukuanDetailEntity detail = new FukuanDetailEntity();
        Fukuandantb fkd = new Fukuandantb();
        super.copyToBean(fkd, jPanel1);
        fkd.setFukuandanDate(dateformate.parse(fukuandanDate.getText()));
        fkd.setDanjuleixingId(14);
        fkd.setIsCompleted(0);
        fkd.setSupplierId(supplierId);
        fkd.setZhidanrenId(userId);
        fkd.setFukuan(Float.parseFloat(fukuan.getText()));
        fkd.setYingfu(Float.parseFloat(yingfu.getText()));
        fkd.setYouhui(Float.parseFloat(youhui.getText()));
        fkd.setIsPaid(0);
        
        if (fkd.getFukuan() + fkd.getYouhui() > fkd.getYingfu()) {
            AssetMessage.ERRORSYS("付款金额+优惠金额大于应付金额！", this);
            return null;
        }
        
        detail.setFukuandan(fkd);
        detail.setYfklist(currentPageData);
        
        return new SaveTask(detail);
    }

    private class SaveTask extends FukuandanTask {

        public SaveTask(FukuanDetailEntity bean) {
            super(bean);
        }

        @Override
        public void responseResult(ComResponse<FukuanDetailEntity> response) {
            AssetMessage.showMessageDialog(null, "保存成功");
            exit();
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

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fukuandanId = new javax.swing.JTextField();
        supplier = new BaseTextField();
        accountNum = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        fukuandanDate = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        shenqingdanRemark = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new BaseTable(null);
        fukuan = new BaseTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        youhui = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        yingfu = new BaseTextField();
        jLabel8 = new javax.swing.JLabel();
        shenqingren = new BaseTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(FuKuanDanJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(FuKuanDanJDialog.class, this);
        jButton1.setAction(actionMap.get("submitForm")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton4.setAction(actionMap.get("print")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton6.setAction(actionMap.get("exit")); // NOI18N
        jButton6.setIcon(resourceMap.getIcon("jButton6.icon")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setOpaque(false);
        jToolBar1.add(jButton6);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        fukuandanId.setEditable(false);
        fukuandanId.setText(resourceMap.getString("fukuandanId.text")); // NOI18N
        fukuandanId.setName("fukuandanId"); // NOI18N

        supplier.setText(resourceMap.getString("supplier.text")); // NOI18N
        supplier.setName("supplier"); // NOI18N

        accountNum.setText(resourceMap.getString("accountNum.text")); // NOI18N
        accountNum.setName("accountNum"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        fukuandanDate.setEditable(false);
        fukuandanDate.setText(resourceMap.getString("fukuandanDate.text")); // NOI18N
        fukuandanDate.setName("fukuandanDate"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        shenqingdanRemark.setColumns(20);
        shenqingdanRemark.setRows(2);
        shenqingdanRemark.setName("shenqingdanRemark"); // NOI18N
        jScrollPane1.setViewportView(shenqingdanRemark);

        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "源单编号", "制单日期", "源单类型", "单据金额", "增加金额", "减少金额", "应付金额", "备注"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
            jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
            jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
            jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N
            jTable1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title6")); // NOI18N
            jTable1.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
            jTable1.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
        );

        fukuan.setName("fukuan"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        youhui.setName("youhui"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        yingfu.setEditable(false);
        yingfu.setName("yingfu"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        shenqingren.setEditable(false);
        shenqingren.setName("shenqingren"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(fukuandanId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 258, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(fukuandanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(accountNum, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(fukuan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(youhui, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(shenqingren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(yingfu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fukuandanId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(fukuandanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountNum, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fukuan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(youhui, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(shenqingren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(yingfu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(FuKuanDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FuKuanDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FuKuanDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FuKuanDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", fukuandanId.getText()},
                    {"制单日期", fukuandanDate.getText()},
                    {"供应单位", supplier.getText()},
                    {"结算账户", accountNum.getText()},
                    {"付款金额", fukuan.getText()},
                    {"优惠", youhui.getText()},
                    {"备注", shenqingdanRemark.getText(),"single"}}, 
                    jTable1,
                    new String[][]{{"制单人", shenqingren.getText()},
                    {"应付总金额", yingfu.getText()}
                    });
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accountNum;
    private javax.swing.JTextField fukuan;
    private javax.swing.JTextField fukuandanDate;
    private javax.swing.JTextField fukuandanId;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea shenqingdanRemark;
    private javax.swing.JTextField shenqingren;
    private javax.swing.JTextField supplier;
    private javax.swing.JTextField yingfu;
    private javax.swing.JTextField youhui;
    // End of variables declaration//GEN-END:variables
}
