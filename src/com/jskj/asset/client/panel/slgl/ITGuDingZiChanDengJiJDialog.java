/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.panel.slgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Fushuliebiaotb;
import com.jskj.asset.client.bean.entity.Kuozhanxinxitb;
import com.jskj.asset.client.bean.entity.ZichandengjiAll;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseFileChoose;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.panel.FileTask;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author tt
 */
public class ITGuDingZiChanDengJiJDialog extends javax.swing.JDialog {

    private JTextField regTextField;
    private String imageUri;
    private ZichandengjiAll zc;
    private int userId;
    private String userName;
    private String yuandanID;
    private String supplier;
    private FushuliebiaoJDialog fslb;
    private KuozhanxinxiJDialog kzxx;
    private JFrame mainFrame;
    /**
     * Creates new form PTGuDingZiChanDengJiJDialog
     */
    public ITGuDingZiChanDengJiJDialog(java.awt.Frame parent) {
        super(parent);
        init();
        initComponents();
        userId = AssetClientApp.getSessionMap().getUsertb().getUserId();
        userName = AssetClientApp.getSessionMap().getUsertb().getUserName();
        mainFrame = AssetClientApp.getApplication().getMainFrame();
        
        ((BaseTextField) jTextFieldName).registerPopup(new IPopupBuilder() {

            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "gdzclb";
            }

            public String getConditionSQL() {
                String sql = "";
                sql += " cgsq_id like \"%GDZC%\" and is_completed = 1 and status = 0 ";
                if (!jTextFieldName.getText().trim().equals("")) {
                    sql += (" and cgzc_id in ( select gdzc_id  from gudingzichan where gdzc_type like \"%IT%\" and (gdzc_name like \"%" + jTextFieldName.getText() + "%\"" 
                        + " or zujima like \"" + jTextFieldName.getText().toLowerCase() + "%\"))");
                } else {
                    sql += (" and cgzc_id in ( select gdzc_id  from gudingzichan where gdzc_type like \"%IT%\" )");
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"shenqingdan.shenqingdanId", "源单号"},{"shenqingdan.zhidanren", "申请人"}
                        ,{"gdzcId", "资产ID"},{"gdzcName", "资产名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    jTextFieldZcid.setText(bindedMap.get("gdzcId") == null ? "" : bindedMap.get("gdzcId").toString());
                    jTextFieldZctype.setText(bindedMap.get("gdzcType") == null ? "" : bindedMap.get("gdzcType").toString());
                    jTextFieldName.setText(bindedMap.get("gdzcName") == null ? "" : bindedMap.get("gdzcName").toString());
                    jTextFieldPinpai.setText(bindedMap.get("gdzcPinpai") == null ? "" : bindedMap.get("gdzcPinpai").toString());
                    jTextFieldXinghao.setText(bindedMap.get("gdzcXinghao") == null ? "" : bindedMap.get("gdzcXinghao").toString());
                    jTextFieldGuige.setText(bindedMap.get("gdzcGuige") == null ? "" : bindedMap.get("gdzcGuige").toString());
                    jTextFieldPrice.setText(bindedMap.get("gdzcValue") == null ? "" : bindedMap.get("gdzcValue").toString());
                    jTextFieldUnit.setText(bindedMap.get("unitId") == null ? "" : bindedMap.get("unitId").toString());
                    jTextFieldBaoxiuqi.setText(bindedMap.get("gdzcGuaranteedate") == null ? "" : bindedMap.get("gdzcGuaranteedate").toString());
                    jTextFieldXuliehao.setText(bindedMap.get("gdzcSequence") == null ? "" : bindedMap.get("gdzcSequence").toString());
                    jTextAreaRemark.setText(bindedMap.get("gdzcRemark") == null ? "" : bindedMap.get("gdzcRemark").toString());
                    imageUri = bindedMap.get("gdzcPhoto") == null ? "" : bindedMap.get("gdzcPhoto").toString();
                    jTextFieldBaoxiuqi.setEditable(false);
                    jTextFieldQuantity.setText(bindedMap.get("count") == null ? "" : bindedMap.get("count").toString());
                    jTextFieldQuantity.setEditable(false);
                    HashMap map = (HashMap)bindedMap.get("shenqingdan");
                    yuandanID = (String)map.get("shenqingdanId");
                    map = (HashMap)bindedMap.get("suppliertb");
                    supplier = (String)map.get("supplierName");
                    jTextFieldSupplier.setText(supplier);
                }
            }
        });
    }

    private void init() {
        regTextField = new BaseTextField();
        ((BaseTextField) regTextField).registerPopup(IPopupBuilder.TYPE_DATE_CLICK, "yyyy-MM-dd");
    }
    
    @Action
    public void exit() {
        this.dispose();
    }
    
    @Action
    public Task uploadPic() {
        BaseFileChoose fileChoose = new BaseFileChoose(new String[]{"png", "jpg", "gif", "bmp"}, this);
        String selectedPath = fileChoose.openDialog();
        if (!selectedPath.trim().equals("")) {
            jTextFieldFile.setText(selectedPath);
            imageUri = selectedPath;
            return new FileTask(FileTask.TYPE_UPLOAD, selectedPath, "gudingzhichan") {
                @Override
                public void responseResult(String file) {
                }
            };
        }
        return null;
    }
    
    @Action
    public void fushuliebiao(){
        if(fslb == null){
            fslb = new FushuliebiaoJDialog();
            fslb.setLocationRelativeTo(mainFrame);
        }
        AssetClientApp.getApplication().show(fslb);
    }

    @Action
    public void kuozhan(){
        if(kzxx == null){
            kzxx = new KuozhanxinxiJDialog();
            kzxx.setLocationRelativeTo(mainFrame);
        }
        AssetClientApp.getApplication().show(kzxx);
    }

    @Action
    public Task submitForm() throws ParseException{
        if(jTextFieldName.getText().isEmpty()){
            AssetMessage.ERRORSYS("请输入资产名称！",this);
            return null;
        }
        if(jTextFieldQuantity.getText().isEmpty()){
            AssetMessage.ERRORSYS("请输入登记数量！",this);
            return null;
        }
        if(jTextField12.getText().isEmpty()){
            AssetMessage.ERRORSYS("请输入购置日期！",this);
            return null;
        }
        zc = new ZichandengjiAll();
        zc.setGdzcId(Integer.parseInt(jTextFieldZcid.getText()));
        SimpleDateFormat dateformate=new SimpleDateFormat("yyyy-MM-dd");
        zc.setGouzhiDate(dateformate.parse(jTextField12.getText()));
        zc.setDengjirenId(userId);
        zc.setQuantity(Integer.parseInt(jTextFieldQuantity.getText()));
        zc.setYuandanId(yuandanID);
        zc.setImguri(imageUri);
        if(fslb != null) {
             List<Fushuliebiaotb> list = fslb.getList();
            if(list.size() > 0){
                for(Fushuliebiaotb lb:list){
                    lb.setZhuzcId(zc.getGdzcId());
                }
            }
            zc.setFushulist(list);
        }
        if(kzxx != null) {
             List<Kuozhanxinxitb> kzlist = kzxx.getList();
            if(kzlist.size() > 0){
                for(Kuozhanxinxitb lb:kzlist){
                    lb.setZhuzcId(zc.getGdzcId());
                }
            }
            zc.setKuozhanlist(kzlist);
        }
        
        return new submitTask(zc);
    }
    
    private class submitTask extends DengjiTask{

        public submitTask(ZichandengjiAll zc) {
            super(zc);
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
            ITGuDingZiChanDengJiJDialog iTGuDingZiChanDengJiJDialog = new ITGuDingZiChanDengJiJDialog(mainFrame);
            iTGuDingZiChanDengJiJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(iTGuDingZiChanDengJiJDialog);
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
        jLabel1 = new javax.swing.JLabel();
        jTextFieldZcid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldName = new BaseTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldZctype = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldGuige = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldXinghao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldPinpai = new javax.swing.JTextField();
        jTextFieldUnit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldQuantity = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldSupplier = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField12 = regTextField;
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldBaoxiuqi = new javax.swing.JTextField();
        jTextFieldPrice = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldXuliehao = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaRemark = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextFieldFile = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(ITGuDingZiChanDengJiJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldZcid.setText(resourceMap.getString("jTextFieldZcid.text")); // NOI18N
        jTextFieldZcid.setName("jTextFieldZcid"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldName.setName("jTextFieldName"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldZctype.setText(resourceMap.getString("jTextFieldZctype.text")); // NOI18N
        jTextFieldZctype.setName("jTextFieldZctype"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldGuige.setName("jTextFieldGuige"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextFieldXinghao.setName("jTextFieldXinghao"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jTextFieldPinpai.setName("jTextFieldPinpai"); // NOI18N

        jTextFieldUnit.setName("jTextFieldUnit"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jTextFieldQuantity.setName("jTextFieldQuantity"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jTextFieldSupplier.setName("jTextFieldSupplier"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jTextField12.setName("jTextField12"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextFieldBaoxiuqi.setName("jTextFieldBaoxiuqi"); // NOI18N

        jTextFieldPrice.setName("jTextFieldPrice"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jTextFieldXuliehao.setName("jTextFieldXuliehao"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(ITGuDingZiChanDengJiJDialog.class, this);
        jButton2.setAction(actionMap.get("uploadPic")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setAction(actionMap.get("fushuliebiao")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextAreaRemark.setColumns(20);
        jTextAreaRemark.setRows(2);
        jTextAreaRemark.setName("jTextAreaRemark"); // NOI18N
        jScrollPane1.setViewportView(jTextAreaRemark);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        jButton5.setAction(actionMap.get("submitForm")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setOpaque(false);
        jToolBar1.add(jButton5);

        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setBorderPainted(false);
        jButton7.setFocusable(false);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar1.add(jButton7);

        jButton6.setIcon(resourceMap.getIcon("jButton6.icon")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setOpaque(false);
        jToolBar1.add(jButton6);

        jButton4.setAction(actionMap.get("exit")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton1.setAction(actionMap.get("kuozhan")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jTextFieldFile.setEditable(false);
        jTextFieldFile.setName("jTextFieldFile"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel14))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldZcid)
                                    .addComponent(jTextFieldZctype)
                                    .addComponent(jTextFieldPinpai)
                                    .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldGuige, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldXuliehao, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                    .addComponent(jTextFieldBaoxiuqi)
                                    .addComponent(jTextFieldUnit)
                                    .addComponent(jTextFieldXinghao)
                                    .addComponent(jTextFieldSupplier)
                                    .addComponent(jTextFieldName)))
                            .addComponent(jScrollPane1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldFile)))))
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldZcid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldZctype, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldGuige, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldXinghao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPinpai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldBaoxiuqi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldXuliehao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(ITGuDingZiChanDengJiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITGuDingZiChanDengJiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITGuDingZiChanDengJiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITGuDingZiChanDengJiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ITGuDingZiChanDengJiJDialog dialog = new ITGuDingZiChanDengJiJDialog(new javax.swing.JFrame());
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaRemark;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextFieldBaoxiuqi;
    private javax.swing.JTextField jTextFieldFile;
    private javax.swing.JTextField jTextFieldGuige;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPinpai;
    private javax.swing.JTextField jTextFieldPrice;
    private javax.swing.JTextField jTextFieldQuantity;
    private javax.swing.JTextField jTextFieldSupplier;
    private javax.swing.JTextField jTextFieldUnit;
    private javax.swing.JTextField jTextFieldXinghao;
    private javax.swing.JTextField jTextFieldXuliehao;
    private javax.swing.JTextField jTextFieldZcid;
    private javax.swing.JTextField jTextFieldZctype;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
