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
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.slgl.task.CancelDengji;
import com.jskj.asset.client.panel.slgl.task.WeidengjizichanTask;
import static com.jskj.asset.client.panel.slgl.task.WeidengjizichanTask.logger;
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
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author tt
 */
public class YihaopinRuKuJDialog extends BaseDialog {

    private RukudanDetailEntity cgsq;
    private int userId;
    private String userName;
    private int supplierId;
    private List<ZiChanLieBiaotb> zc;
    CaigoushenqingDetailEntity detail;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String yuandanID;
    private String pihao;
    private List<ZichanliebiaotbAll> list;
    private boolean isNew;
    private Map yuandanmap;
    /**
     * Creates new form GuDingZiChanRuKu
     * @param parent
     * @param modal
     */
    public YihaopinRuKuJDialog() {
        super();
        initComponents();
        
        zc = new ArrayList<ZiChanLieBiaotb>();
        userId = AssetClientApp.getSessionMap().getUsertb().getUserId();
        userName = AssetClientApp.getSessionMap().getUsertb().getUserName();
        isNew = false;
        yuandanmap = new HashMap();
        
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
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
        
        cgsqId.setText(DanHao.getDanHao("ZCRK"));
        cgsqId.setEditable(false);
        
        shenqingdanDate.setText(dateformate.format(new Date()).toString());
        shenqingdanDate.setEditable(false);
        
        jingbanren.setText(userName);
        jingbanren.setEditable(false);
        
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTable1).createSingleEditModel(new String[][]{
            {"dzyhpId", "资产编号"}, {"dzyhpName", "资产名称", "true"}, {"dzyhpType", "类别"},{"dzyhpPinpai", "品牌", "false"},
            {"dzyhpValue", "采购价", "false"},{"quantity", "数量", "false"},{"liebiao.pihao", "批号", "false"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "yhplb";
            }

            public String getConditionSQL() {
                int selectedColumn = jTable1.getSelectedColumn();
                int selectedRow = jTable1.getSelectedRow();
                Object newColumnObj = jTable1.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                sql += " cgsq_id like \"%YHCG%\" and is_completed = 1 and status = 1 "
                        + " and cgsq_id NOT IN( SELECT cgsq_id FROM (SELECT cgsq_id,COUNT(*) AS num FROM zichanliebiao WHERE STATUS=0 GROUP BY cgsq_id) AS a WHERE a.num > 0)";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += (" and cgzc_id in ( select dzyhp_id  from dizhiyihaopin where dzyhp_name like \"%" + newColumnObj.toString() + "%\"" 
                        + " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")");
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"shenqingdan.shenqingdanId", "源单号"},{"shenqingdan.zhidanren", "申请人"}
                        ,{"dzyhpId", "资产ID"},{"dzyhpName", "资产名称"},{"liebiao.pihao", "批号"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object dzyhpId = bindedMap.get("dzyhpId");
                    Object dzyhpName = bindedMap.get("dzyhpName");
                    Object dzyhpType = bindedMap.get("dzyhpType");
                    Object dzyhpPinpai = bindedMap.get("dzyhpPinpai");
                    Object dzyhpValue = bindedMap.get("saleprice");
                    Object dzyhpCount = bindedMap.get("count");

                    editTable.insertValue(0, dzyhpId);
                    editTable.insertValue(1, dzyhpName);
                    editTable.insertValue(2, dzyhpType);
                    editTable.insertValue(3, dzyhpPinpai);
                    editTable.insertValue(4, dzyhpValue);
                    editTable.insertValue(5, dzyhpCount);

                    HashMap map = (HashMap)bindedMap.get("shenqingdan");
                    yuandanID = (String)map.get("shenqingdanId");
                    
                    map = (HashMap)bindedMap.get("liebiao");
                    pihao = (String)map.get("pihao");
                    editTable.insertValue(6, pihao);
                    
//                    ZiChanLieBiaotb zclb = new ZiChanLieBiaotb();
//                    zclb.setCgsqId(cgsqId.getText());
//                    zclb.setCgzcId((Integer)gdzcId);
//                    zclb.setQuantity(Integer.parseInt("" + gdzcCount));
//                    zclb.setCgsqId(yuandanID);
//                    zc.add(zclb);
                    //保存原单号
                    yuandanmap.put(dzyhpId+pihao, yuandanID);
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

    }
    
    public YihaopinRuKuJDialog(final JDialog parent,CaigoushenqingDetailEntity detail){
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
            o[i] = new Object[]{zclb.getGdzcId(),zclb.getGdzcName(),zclb.getGdzcType(),zclb.getGdzcPinpai(),zclb.getGdzcValue(),zclb.getCount()};
        }
        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                o,
                new String[]{
                    "资产编号", "资产名称", "类别", "品牌", "单价", "数量"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };
        });
    }

    public void setNew(){
        isNew = true;
    }
    
    @Action
    public void exit() {
        if(isNew){
            close();
            return;
        }
        String sql = " cgsq_id like \"YHCG%\" and is_completed = 1 and status = 1"
            + " and cgsq_id NOT IN( SELECT cgsq_id FROM (SELECT cgsq_id,COUNT(*) AS num FROM zichanliebiao WHERE STATUS=0 GROUP BY cgsq_id) AS a WHERE a.num > 0)";
        new CloseTask(sql).execute();
    }
    
    public void close(){
        this.dispose();
    }
    
    private class CloseTask extends WeidengjizichanTask{

        public CloseTask(String sql) {
            super(sql);
        }
        
        @Override
        public void responseResult(CommFindEntity<ZichanliebiaotbAll> response) {

            logger.debug("get current size:" + response.getResult().size());
            list = response.getResult();
            if (list != null && list.size() > 0) {
                StringBuilder string = new StringBuilder();
                for (ZichanliebiaotbAll zc : list) {
                    string.append("单据").append(zc.getCgsqId()).append("有未入库项【")
                            .append(zc.getZcName()).append("】\n");
                }
                string.append("是否继续入库？选“否”会要求输入原因，并不再入库以上所有资产");
                int result = AssetMessage.showConfirmDialog(null, string.toString(),
                        "确认",JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    return;
                }
                for (ZichanliebiaotbAll lb : list) {
                    String reason = "";
                    //修改在点击取消时不做处理，直接返回入库页面
                    while (reason.isEmpty()) {
                        reason = AssetMessage.showInputDialog(null, "请输入取消入库资产【"
                                + lb.getZcName() + "】的理由(必输)：");
                        if (reason == null) {
                            return;
                        }
                    }
                    lb.setReason("【入库】" + reason);
                }
                new Cancel(list).execute();
            }
            close();
        }
        
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
            YihaopinRuKuJDialog zichanruku = new YihaopinRuKuJDialog();
            zichanruku.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(zichanruku);
        }

    }
    
    //单个资产登记不合格情况
    @Action
    public Task buhege(){
        if(jTable1.getRowCount()-1 < 1){
            AssetMessage.ERRORSYS("请选择要取消入库的资产！",this);
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
            lb.setCgsqId(yuandanmap.get(lb.getCgzcId() + jTable1.getValueAt(i, 6).toString()).toString());
            String reason = "";
            while (reason.isEmpty()) {
                reason = AssetMessage.showInputDialog(null, "请输入取消入库资产【"
                        + jTable1.getValueAt(i, 1) + "】的理由(必输)：");
                if (reason == null) {
                    return null;
                }
            }
            lb.setReason("【入库】" + reason);
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
            AssetMessage.ERRORSYS("请选择要入库的物品！",this);
            return null;
        }
        jTable1.getCellEditor(jTable1.getSelectedRow(),
                jTable1.getSelectedColumn()).stopCellEditing();
        cgsq = new RukudanDetailEntity();
        Zichanrukudantb sqd = new Zichanrukudantb();
        sqd.setRukudanId(cgsqId.getText());
        sqd.setRukudanDate(dateformate.parse(shenqingdanDate.getText()));
        sqd.setZhidanrenId(userId);
        sqd.setDanjuleixingId(23);
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
            zclb.setCgsqId(yuandanmap.get(zclb.getCgzcId()+ jTable1.getValueAt(i, 6).toString()).toString());
            zclb.setQuantity(Integer.parseInt("" + jTable1.getValueAt(i, 5)));
            float price = Float.parseFloat("" + jTable1.getValueAt(i, 4));
            zclb.setPihao(jTable1.getValueAt(i, 8).toString());
            zclb.setSaleprice(price);
            zclb.setTotalprice(zclb.getQuantity()*price);
            zclb.setIsCompleted(0);
            zclb.setStatus(0);
            zc.add(zclb);
        }
        
        cgsq.setRukudan(sqd);
        cgsq.setZc(zc);        
        
        String serviceId = "yhp/ruku";
        return new CommUpdateTask<RukudanDetailEntity>(cgsq,serviceId) {

            @Override
            public void responseResult(ComResponse<RukudanDetailEntity> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    AssetMessage.showMessageDialog(null, "提交成功！");
                    close();
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    YihaopinRuKuJDialog zichanruku = new YihaopinRuKuJDialog();
                    zichanruku.setLocationRelativeTo(mainFrame);
                    AssetClientApp.getApplication().show(zichanruku);
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YihaopinRuKuJDialog.this);
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
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        shenqingdanRemark = new javax.swing.JTextArea();
        jingbanren = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cangku = new BaseTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new BaseTable(null);
        jToolBar1 = new javax.swing.JToolBar();
        jButton10 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YihaopinRuKuJDialog.class);
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

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        shenqingdanRemark.setColumns(20);
        shenqingdanRemark.setRows(2);
        shenqingdanRemark.setName("shenqingdanRemark"); // NOI18N
        jScrollPane2.setViewportView(shenqingdanRemark);

        jingbanren.setName("jingbanren"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        cangku.setName("cangku"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout middlePanelLayout = new javax.swing.GroupLayout(middlePanel);
        middlePanel.setLayout(middlePanelLayout);
        middlePanelLayout.setHorizontalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(middlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addComponent(jingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(cangku, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(middlePanelLayout.createSequentialGroup()
                        .addComponent(cgsqId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 345, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(shenqingdanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel3)
                        .addComponent(cangku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
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

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YihaopinRuKuJDialog.class, this);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addComponent(middlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(middlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 817, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(YihaopinRuKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YihaopinRuKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YihaopinRuKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YihaopinRuKuJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YihaopinRuKuJDialog dialog = new YihaopinRuKuJDialog();
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jingbanren;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JTextField shenqingdanDate;
    private javax.swing.JTextArea shenqingdanRemark;
    // End of variables declaration//GEN-END:variables
}
