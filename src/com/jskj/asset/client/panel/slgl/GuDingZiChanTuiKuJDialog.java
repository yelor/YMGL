/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.CaigoushenqingDetailEntity;
import com.jskj.asset.client.bean.entity.RukudanDetailEntity;
import com.jskj.asset.client.bean.entity.Zichanrukudantb;
import com.jskj.asset.client.bean.entity.ZiChanLieBiaotb;
import com.jskj.asset.client.bean.entity.ZichanliebiaoDetailEntity;
import com.jskj.asset.client.bean.entity.ZichanliebiaotbAll;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.slgl.task.CancelDengji;
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
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import net.sf.dynamicreports.report.exception.DRException;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author tt
 */
public class GuDingZiChanTuiKuJDialog extends BaseDialog{

    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GuDingZiChanTuiKuJDialog.class);
    private RukudanDetailEntity cgsq;
    private int userId;
    private String userName;
    private int supplierId;
    private List<ZiChanLieBiaotb> zc;
    CaigoushenqingDetailEntity detail;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String yuandanID;
    private String pihao;
    private Double totalprice;
    private float total = 0;
    private List<ZichanliebiaotbAll> list;
    private boolean isNew;
    private Map yuandanmap;
    /**
     * Creates new form GuDingZiChanRuKu
     * @param parent
     * @param modal
     */
    public GuDingZiChanTuiKuJDialog() {
        super();
        initComponents();
        
        zc = new ArrayList<ZiChanLieBiaotb>();
        userId = AssetClientApp.getSessionMap().getUsertb().getUserId();
        userName = AssetClientApp.getSessionMap().getUsertb().getUserName();
        isNew = false;
        yuandanmap = new HashMap();
        
        cgsqId.setText(DanHao.getDanHao("ZCLT"));
        cgsqId.setEditable(false);
        
        shenqingdanDate.setText(dateformate.format(new Date()).toString());
        shenqingdanDate.setEditable(false);
        
        jingbanren.setText(userName);
        jingbanren.setEditable(false);
        
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTable1).createSingleEditModel(new String[][]{
            {"gdzcId", "资产编号"}, {"gdzcName", "资产名称", "true"}, {"gdzcType", "类别"},{"gdzcPinpai", "品牌", "false"},
            {"gdzcXinghao", "型号"}, {"unitId", "单位", "false"}, {"quantity", "数量", "false"}, {"saleprice", "采购价", "false"}, {"liebiao.totalprice", "合价"},{"liebiao.pihao", "条码", "false"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "gdzclb";
            }

            public String getConditionSQL() {
                int selectedColumn = jTable1.getSelectedColumn();
                int selectedRow = jTable1.getSelectedRow();
                Object newColumnObj = jTable1.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                sql += " cgsq_id like \"%ZCTK%\" and status = 3 ";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += (" and cgzc_id in ( select gdzc_id  from gudingzichan where gdzc_name like \"%" + newColumnObj.toString() + "%\"" 
                        + " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")");
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"shenqingdan.shenqingdanId", "源单号"},{"shenqingdan.zhidanren", "申请人"}
                        ,{"gdzcId", "资产ID"},{"gdzcName", "资产名称"},{"liebiao.pihao", "条码"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object gdzcId = bindedMap.get("gdzcId");
                    Object gdzcName = bindedMap.get("gdzcName");
                    Object gdzcType = bindedMap.get("gdzcType");
                    Object gdzcPinpai = bindedMap.get("gdzcPinpai");
                    Object gdzcValue = bindedMap.get("saleprice");
                    Object gdzcCount = bindedMap.get("count");
                    Object gdzcXinghao = bindedMap.get("gdzcXinghao");
                    Object gdzcDanwei = bindedMap.get("unitId");

                    editTable.insertValue(0, gdzcId);
                    editTable.insertValue(1, gdzcName);
                    editTable.insertValue(2, gdzcType);
                    editTable.insertValue(3, gdzcPinpai);
                    editTable.insertValue(4, gdzcXinghao);
                    editTable.insertValue(5, gdzcDanwei);
                    editTable.insertValue(6, gdzcCount);
                    editTable.insertValue(7, gdzcValue);

                    HashMap map = (HashMap)bindedMap.get("shenqingdan");
                    yuandanID = (String)map.get("shenqingdanId");
                    
                    map = (HashMap)bindedMap.get("liebiao");
                    pihao = (String)map.get("pihao");
                    totalprice = (Double)map.get("totalprice");
                    editTable.insertValue(8, totalprice);
                    editTable.insertValue(9, pihao);
                    
//                    ZiChanLieBiaotb zclb = new ZiChanLieBiaotb();
//                    zclb.setCgsqId(cgsqId.getText());
//                    zclb.setCgzcId((Integer)gdzcId);
//                    zclb.setQuantity(Integer.parseInt("" + gdzcCount));
//                    zclb.setCgsqId(yuandanID);
//                    zc.add(zclb);
                    //保存原单号
                    yuandanmap.put(gdzcId+pihao, yuandanID);
                }

            }
        });
        
        //库房的popup
        ((BaseTextField) cangku).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "cangku/finddepot";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!cangku.getText().trim().equals("")) {
                    sql = "(depot_name like \"%" + cangku.getText() + "%\"" + " or zujima like \"%" + cangku.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"depotId", "仓库编号"}, {"depotName", "仓库名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
//                    shenqingdan.setSupplierId((Integer) (bindedMap.get("supplierId")));
                    cangku.setText(bindedMap.get("depotName") == null ? "" : bindedMap.get("depotName").toString());
                }
            }
        });

        jTable1.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                    int rows = jTable1.getRowCount();
                    total = 0;
                    for(int i = 0; i < rows; i++) {
                        if(!(("" + jTable1.getValueAt(i, 8)).equals(""))){
                            total += Float.parseFloat("" + jTable1.getValueAt(i, 8));
                        }
                    }
                    totalpricelabel.setText(total + "元");
            }
            
        });
    }
    
    public GuDingZiChanTuiKuJDialog(final JDialog parent,CaigoushenqingDetailEntity detail){
        super();
        initComponents();
        this.detail = detail;
        this.addWindowListener(new WindowListener(){

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                parent.setVisible(true);
            }

            @Override
            public void windowIconified(WindowEvent e) { }

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
            
        });
        super.bind(detail, middlePanel);
        jButton10.setEnabled(false);
        cgsqId.setEditable(false);
        shenqingdanDate.setText(DateHelper.format(detail.getShenqingdanDate(), "yyyy-MM-dd"));
        shenqingdanDate.setEditable(false);
        jingbanren.setEditable(false);
        shenqingdanRemark.setEditable(false);
        
        setListTable(detail.getResult());
    }
    
    public void setListTable(List<ZichanliebiaoDetailEntity> zclist){
        
        int size = zclist.size();
        Object[][] o = new Object[size][6];
        for( int i = 0; i < size; i++){
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

    public void close(){
        this.dispose();
    }
    
    @Action
    public void exit() {
        this.dispose();
    }

    private class Cancel extends CancelDengji{

        public Cancel(List<ZichanliebiaotbAll> zc) {
            super(zc);
        }
   
        @Override
        public void onSucceeded(Object object) {
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            close();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            GuDingZiChanTuiKuJDialog zichanruku = new GuDingZiChanTuiKuJDialog();
            zichanruku.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(zichanruku);
        }

    }
    
    //单个资产登记不合格情况
    @Action
    public Task buhege(){
        if(jTable1.getRowCount()-1 < 1){
            AssetMessage.ERRORSYS("请选择要取消退库的资产！",this);
            return null;
        }
        List<ZichanliebiaotbAll> lst = new ArrayList<ZichanliebiaotbAll>();
        for (int i = 0; i < jTable1.getRowCount()-1; i++) {
            ZichanliebiaotbAll lb = new ZichanliebiaotbAll();
            try{
                lb.setCgzcId(Integer.parseInt("" + jTable1.getValueAt(i, 0)));
            }catch(NumberFormatException e){
                AssetMessage.ERRORSYS("第" + (i+1) + "个资产的ID不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            lb.setCgsqId(yuandanmap.get(lb.getCgzcId() + jTable1.getValueAt(i, 9).toString()).toString());
            String reason = "";
            while (reason.isEmpty()) {
                reason = AssetMessage.showInputDialog(null, "请输入取消退库资产【"
                        + jTable1.getValueAt(i, 1) + "】的理由(必输)：");
                if (reason == null) {
                    return null;
                }
            }
            lb.setReason("【退库】" + reason);
            lst.add(lb);
        }
        return new Cancel(lst);
    }
    
    @Action
    public Task submitForm() throws ParseException{
        if (cangku.getText().isEmpty()) {
            AssetMessage.ERRORSYS("请输入仓库！", this);
            return null;
        }
        if(jTable1.getRowCount()-1 < 1){
            AssetMessage.ERRORSYS("请选择要退库的资产！",this);
            return null;
        }
        jTable1.getCellEditor(jTable1.getSelectedRow(),
                jTable1.getSelectedColumn()).stopCellEditing();
        cgsq = new RukudanDetailEntity();
        Zichanrukudantb sqd = new Zichanrukudantb();
        sqd.setRukudanId(cgsqId.getText());
        sqd.setRukudanDate(dateformate.parse(shenqingdanDate.getText()));
        sqd.setZhidanrenId(userId);
        sqd.setDanjuleixingId(18);
        sqd.setShenqingdanRemark(shenqingdanRemark.getText());
        sqd.setKufang(cangku.getText());
        
        zc = new ArrayList<ZiChanLieBiaotb>();
//        for (int i = 0; i < zc.size(); i++) {
        for (int i = 0; i < jTable1.getRowCount()-1; i++) {
            ZiChanLieBiaotb zclb = new ZiChanLieBiaotb();
            try{
                zclb.setCgzcId(Integer.parseInt("" + jTable1.getValueAt(i, 0)));
            }catch(NumberFormatException e){
                AssetMessage.ERRORSYS("第" + (i+1) + "个资产的ID不合法，请输入纯数字，不能包含字母或特殊字符！");
                return null;
            }
            zclb.setCgsqId(yuandanmap.get(zclb.getCgzcId() + jTable1.getValueAt(i, 9).toString()).toString());
            zclb.setQuantity(Integer.parseInt("" + jTable1.getValueAt(i, 6)));
            float price = Float.parseFloat("" + jTable1.getValueAt(i, 7));
            zclb.setPihao(jTable1.getValueAt(i, 9).toString());
            zclb.setSaleprice(price);
            zclb.setTotalprice(zclb.getQuantity()*price);
            zclb.setIsCompleted(0);
            zclb.setStatus(0);
            zc.add(zclb);
        }
        
        cgsq.setRukudan(sqd);
        cgsq.setZc(zc);        
        
        String serviceId = "gdzc/tuiku";
        return new CommUpdateTask<RukudanDetailEntity>(cgsq,serviceId) {

            @Override
            public void responseResult(ComResponse<RukudanDetailEntity> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    AssetMessage.showMessageDialog(null, "提交成功！");
                    close();
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    GuDingZiChanTuiKuJDialog zichanruku = new GuDingZiChanTuiKuJDialog();
                    zichanruku.setLocationRelativeTo(mainFrame);
                    AssetClientApp.getApplication().show(zichanruku);
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), GuDingZiChanTuiKuJDialog.this);
                }
            }
        };
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
        cangku = new BaseTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        shenqingdanRemark = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jingbanren = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new BaseTable(null);
        jToolBar1 = new javax.swing.JToolBar();
        jButton10 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        totalpricelabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(GuDingZiChanTuiKuJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel2.setName("jPanel2"); // NOI18N

        middlePanel.setName("middlePanel"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        cgsqId.setText(resourceMap.getString("cgsqId.text")); // NOI18N
        cgsqId.setName("cgsqId"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        shenqingdanDate.setName("shenqingdanDate"); // NOI18N

        cangku.setName("cangku"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        shenqingdanRemark.setColumns(20);
        shenqingdanRemark.setRows(2);
        shenqingdanRemark.setName("shenqingdanRemark"); // NOI18N
        jScrollPane2.setViewportView(shenqingdanRemark);

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jingbanren.setName("jingbanren"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout middlePanelLayout = new javax.swing.GroupLayout(middlePanel);
        middlePanel.setLayout(middlePanelLayout);
        middlePanelLayout.setHorizontalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cgsqId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 428, Short.MAX_VALUE)
                        .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, middlePanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(shenqingdanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, middlePanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cangku, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2)))
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
                    .addComponent(jLabel5)
                    .addComponent(cangku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(14, Short.MAX_VALUE))
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

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(GuDingZiChanTuiKuJDialog.class, this);
        jButton10.setAction(actionMap.get("submitForm")); // NOI18N
        jButton10.setIcon(resourceMap.getIcon("jButton10.icon")); // NOI18N
        jButton10.setText(resourceMap.getString("jButton10.text")); // NOI18N
        jButton10.setBorderPainted(false);
        jButton10.setFocusable(false);
        jButton10.setName("jButton10"); // NOI18N
        jButton10.setOpaque(false);
        jToolBar1.add(jButton10);

        jButton2.setAction(actionMap.get("buhege")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

        jButton13.setAction(actionMap.get("print")); // NOI18N
        jButton13.setIcon(resourceMap.getIcon("jButton13.icon")); // NOI18N
        jButton13.setText(resourceMap.getString("jButton13.text")); // NOI18N
        jButton13.setBorderPainted(false);
        jButton13.setFocusable(false);
        jButton13.setName("jButton13"); // NOI18N
        jButton13.setOpaque(false);
        jToolBar1.add(jButton13);

        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton15.setAction(actionMap.get("exit")); // NOI18N
        jButton15.setIcon(resourceMap.getIcon("jButton15.icon")); // NOI18N
        jButton15.setText(resourceMap.getString("jButton15.text")); // NOI18N
        jButton15.setBorderPainted(false);
        jButton15.setFocusable(false);
        jButton15.setName("jButton15"); // NOI18N
        jButton15.setOpaque(false);
        jToolBar1.add(jButton15);

        totalpricelabel.setText(resourceMap.getString("totalpricelabel.text")); // NOI18N
        totalpricelabel.setName("totalpricelabel"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

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
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalpricelabel)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(middlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(totalpricelabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 551, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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
            java.util.logging.Logger.getLogger(GuDingZiChanTuiKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanTuiKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanTuiKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuDingZiChanTuiKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GuDingZiChanTuiKuJDialog dialog = new GuDingZiChanTuiKuJDialog();
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

    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", cgsqId.getText()},
                    {"制单日期", shenqingdanDate.getText()},
                    {"制单人", jingbanren.getText()},
                    {"仓库", cangku.getText()},
                    {"备注", shenqingdanRemark.getText(),"single"}}, 
                    jTable1,
                    new String[][]{
                    });
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cangku;
    private javax.swing.JTextField cgsqId;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jingbanren;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JTextField shenqingdanDate;
    private javax.swing.JTextArea shenqingdanRemark;
    private javax.swing.JLabel totalpricelabel;
    // End of variables declaration//GEN-END:variables
}
