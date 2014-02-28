/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.bean.entity.Yimiaodengjitb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ScanButton;
import com.jskj.asset.client.panel.ymgl.task.YimiaodengjiUpdateTask;
import com.jskj.asset.client.util.DateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JTextField;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoDengJi1JDialog extends javax.swing.JDialog {

    private static final Logger logger = Logger.getLogger(YiMiaoDengJi1JDialog.class);
    private Yimiaodengjitb yimiaodengji;
    private SimpleDateFormat dateformate;
    private boolean isNew;

    /**
     * Creates new form YiMiaoDengJi1JDialog
     */
    public YiMiaoDengJi1JDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        init();
        initComponents();

        ((BaseTextField) jTextFieldYimiaoName).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyimiao";
            }

            public String getConditionSQL() {
                String sql = "";
                sql += " yimiao_id in (select distinct yimiao_id from yimiaoshenqingdan where is_completed = 1 and status = 2 and danjuleixing_id=5)";
                if (!jTextFieldYimiaoName.getText().trim().equals("")) {
                    sql = "and yimiao_name like \"%" + jTextFieldYimiaoName.getText() + "%\"";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    dateformate = new SimpleDateFormat("yyyy-MM-dd");
                    jTextFieldYimiaoId.setText(bindedMap.get("yimiaoId") == null ? "" : bindedMap.get("yimiaoId").toString());
                    jTextFieldYimiaoName.setText(bindedMap.get("yimiaoName") == null ? "" : bindedMap.get("yimiaoName").toString());
                    jTextFieldguige.setText(bindedMap.get("yimiaoGuige") == null ? "" : bindedMap.get("yimiaoGuige").toString());
                    if (bindedMap.get("yimiaoJixing") == "液体剂型") {
                        jComboBoxJixing.setSelectedIndex(1);
                    } else if (bindedMap.get("yimiaoJixing") == "冻干剂型") {
                        jComboBoxJixing.setSelectedIndex(2);
                    } else if (bindedMap.get("yimiaoJixing") == "糖丸剂型") {
                        jComboBoxJixing.setSelectedIndex(3);
                    }
                    jTextFieldshengchanqiye.setText(bindedMap.get("shengchanqiye") == null ? "" : bindedMap.get("shengchanqiye").toString());
                    jTextFieldpizhunwenhao.setText(bindedMap.get("pizhunwenhao") == null ? "" : bindedMap.get("pizhunwenhao").toString());
                    jTextFieldpihao.setText(bindedMap.get("pihao") == null ? "" : bindedMap.get("pihao").toString());
                    jTextFieldYouxiaoqi.setText(bindedMap.get("youxiaoqi") == null ? "" : dateformate.format(bindedMap.get("youxiaoqi")));
                    jTextFieldunit.setText(bindedMap.get("unit") == null ? "" : bindedMap.get("unit").toString());
                    jTextFieldpiqianfahege.setText(bindedMap.get("piqianfahegezhenNo") == null ? "" : bindedMap.get("piqianfahegezhenNo").toString());
                    jTextFieldkucunDown.setText(bindedMap.get("kucunDown") == null ? "" : bindedMap.get("kucunDown").toString());
                    jTextFieldkucunUp.setText(bindedMap.get("kucunUp") == null ? "" : bindedMap.get("kucunUp").toString());
                    yimiaodengji.setYimiaoId((Integer) bindedMap.get("yimiaoId"));
                }
            }
        });

        ((ScanButton) jButton1).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_SCAN;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyimiao";
            }

            public String getConditionSQL() {
                return "yimiao_tiaoxingma =";
            }

            public String[][] displayColumns() {
                return null;
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    dateformate = new SimpleDateFormat("yyyy-MM-dd");
                    jTextFieldYimiaoId.setText(bindedMap.get("yimiaoId") == null ? "" : bindedMap.get("yimiaoId").toString());
                    jTextFieldYimiaoName.setText(bindedMap.get("yimiaoName") == null ? "" : bindedMap.get("yimiaoName").toString());
                    jTextFieldguige.setText(bindedMap.get("yimiaoGuige") == null ? "" : bindedMap.get("yimiaoGuige").toString());
                    if (bindedMap.get("yimiaoJixing") == "液体剂型") {
                        jComboBoxJixing.setSelectedIndex(1);
                    } else if (bindedMap.get("yimiaoJixing") == "冻干剂型") {
                        jComboBoxJixing.setSelectedIndex(2);
                    } else if (bindedMap.get("yimiaoJixing") == "糖丸剂型") {
                        jComboBoxJixing.setSelectedIndex(3);
                    }
                    jTextFieldshengchanqiye.setText(bindedMap.get("shengchanqiye") == null ? "" : bindedMap.get("shengchanqiye").toString());
                    jTextFieldpizhunwenhao.setText(bindedMap.get("pizhunwenhao") == null ? "" : bindedMap.get("pizhunwenhao").toString());
                    jTextFieldpihao.setText(bindedMap.get("pihao") == null ? "" : bindedMap.get("pihao").toString());
                    jTextFieldYouxiaoqi.setText(bindedMap.get("youxiaoqi") == null ? "" : dateformate.format(bindedMap.get("youxiaoqi")));
                    jTextFieldunit.setText(bindedMap.get("unit") == null ? "" : bindedMap.get("unit").toString());
                    jTextFieldpiqianfahege.setText(bindedMap.get("piqianfahegezhenNo") == null ? "" : bindedMap.get("piqianfahegezhenNo").toString());
                    jTextFieldkucunDown.setText(bindedMap.get("kucunDown") == null ? "" : bindedMap.get("kucunDown").toString());
                    jTextFieldkucunUp.setText(bindedMap.get("kucunUp") == null ? "" : bindedMap.get("kucunUp").toString());
                    yimiaodengji.setYimiaoId((Integer) bindedMap.get("yimiaoId"));
                }

            }
        });
    }

    DateChooser dateChooser1;
    JTextField regTextField;

    private void init() {
        regTextField = new JTextField();
        dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
        dateChooser1.register(regTextField);
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
        jTextFieldYimiaoId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldYimiaoName = new BaseTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldguige = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldshengchanqiye = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldtongguandanNo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldunit = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldYouxiaoqi = regTextField;
        jLabel9 = new javax.swing.JLabel();
        jTextFieldpizhunwenhao = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldpihao = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldpiqianfahege = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldQuantity = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldkucunDown = new javax.swing.JTextField();
        jTextFieldkucunUp = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboBoxSource = new javax.swing.JComboBox();
        jComboBoxJixing = new javax.swing.JComboBox();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new ScanButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoDengJi1JDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldYimiaoId.setText(resourceMap.getString("jTextFieldYimiaoId.text")); // NOI18N
        jTextFieldYimiaoId.setName("jTextFieldYimiaoId"); // NOI18N
        jTextFieldYimiaoId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldYimiaoIdActionPerformed(evt);
            }
        });

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldYimiaoName.setName("jTextFieldYimiaoName"); // NOI18N
        jTextFieldYimiaoName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldYimiaoNameActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldguige.setName("jTextFieldguige"); // NOI18N
        jTextFieldguige.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldguigeActionPerformed(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldshengchanqiye.setName("jTextFieldshengchanqiye"); // NOI18N
        jTextFieldshengchanqiye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldshengchanqiyeActionPerformed(evt);
            }
        });

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextFieldtongguandanNo.setName("jTextFieldtongguandanNo"); // NOI18N
        jTextFieldtongguandanNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldtongguandanNoActionPerformed(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jTextField6.setName("jTextField6"); // NOI18N
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jTextFieldunit.setName("jTextFieldunit"); // NOI18N
        jTextFieldunit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldunitActionPerformed(evt);
            }
        });

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jTextFieldYouxiaoqi.setName("jTextFieldYouxiaoqi"); // NOI18N
        jTextFieldYouxiaoqi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldYouxiaoqiActionPerformed(evt);
            }
        });

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jTextFieldpizhunwenhao.setName("jTextFieldpizhunwenhao"); // NOI18N
        jTextFieldpizhunwenhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldpizhunwenhaoActionPerformed(evt);
            }
        });

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jTextFieldpihao.setName("jTextFieldpihao"); // NOI18N
        jTextFieldpihao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldpihaoActionPerformed(evt);
            }
        });

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jTextFieldpiqianfahege.setName("jTextFieldpiqianfahege"); // NOI18N
        jTextFieldpiqianfahege.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldpiqianfahegeActionPerformed(evt);
            }
        });

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextFieldQuantity.setName("jTextFieldQuantity"); // NOI18N
        jTextFieldQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldQuantityActionPerformed(evt);
            }
        });

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jTextFieldkucunDown.setName("jTextFieldkucunDown"); // NOI18N
        jTextFieldkucunDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldkucunDownActionPerformed(evt);
            }
        });

        jTextFieldkucunUp.setName("jTextFieldkucunUp"); // NOI18N
        jTextFieldkucunUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldkucunUpActionPerformed(evt);
            }
        });

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jComboBoxSource.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "国产", "进口" }));
        jComboBoxSource.setName("jComboBoxSource"); // NOI18N

        jComboBoxJixing.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "请选择剂型...", "液体剂型", "冻干剂型", "糖丸剂型" }));
        jComboBoxJixing.setName("jComboBoxJixing"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldkucunDown, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxSource, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldshengchanqiye, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldguige, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldYimiaoId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldpiqianfahege, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldpihao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(161, 161, 161)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldkucunUp, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldYimiaoName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxJixing, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldpizhunwenhao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldYouxiaoqi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldunit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldtongguandanNo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldYimiaoId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldYimiaoName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldguige, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBoxJixing, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldpizhunwenhao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldshengchanqiye, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldYouxiaoqi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldpihao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldunit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldpiqianfahege, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldtongguandanNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBoxSource, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jTextFieldkucunUp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jTextFieldkucunDown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoDengJi1JDialog.class, this);
        jButton3.setAction(actionMap.get("submitForm")); // NOI18N
        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setOpaque(false);
        jToolBar1.add(jButton3);

        jButton4.setAction(actionMap.get("exit")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldYimiaoIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldYimiaoIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldYimiaoIdActionPerformed

    private void jTextFieldYimiaoNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldYimiaoNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldYimiaoNameActionPerformed

    private void jTextFieldguigeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldguigeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldguigeActionPerformed

    private void jTextFieldshengchanqiyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldshengchanqiyeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldshengchanqiyeActionPerformed

    private void jTextFieldtongguandanNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldtongguandanNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldtongguandanNoActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextFieldunitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldunitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldunitActionPerformed

    private void jTextFieldYouxiaoqiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldYouxiaoqiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldYouxiaoqiActionPerformed

    private void jTextFieldpizhunwenhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldpizhunwenhaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldpizhunwenhaoActionPerformed

    private void jTextFieldpihaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldpihaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldpihaoActionPerformed

    private void jTextFieldpiqianfahegeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldpiqianfahegeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldpiqianfahegeActionPerformed

    private void jTextFieldQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldQuantityActionPerformed

    private void jTextFieldkucunDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldkucunDownActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldkucunDownActionPerformed

    private void jTextFieldkucunUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldkucunUpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldkucunUpActionPerformed

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
            java.util.logging.Logger.getLogger(YiMiaoDengJi1JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoDengJi1JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoDengJi1JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoDengJi1JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoDengJi1JDialog dialog = new YiMiaoDengJi1JDialog(new javax.swing.JFrame(), true);
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

    public void setAddOrUpdate(boolean b) {
        isNew = b;
        if (isNew) {
            this.setTitle("Ⅰ类疫苗登记单");
            yimiaodengji = new Yimiaodengjitb();
        } else {
            this.setTitle("Ⅰ类疫苗登记单");
        }
    }

    public void setUpdatedData(Yimiaodengjitb yimiaodengji) {
        if (yimiaodengji == null) {
            return;
        }
        this.yimiaodengji = yimiaodengji;
        jTextFieldYimiaoId.setText((yimiaodengji.getYmdjId()).toString());

    }

    @Action
    public Task submitForm() throws ParseException {
        if (jTextFieldYimiaoName.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗名称!");
            return null;
        } else if (jTextFieldQuantity.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗数量!");
            return null;
        }
        yimiaodengji.setPizhunwenhao("国家准字号NDA1235465");
        dateformate = new SimpleDateFormat("yyyy-MM-dd");
        if (jTextFieldYouxiaoqi.getText().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗有效期!");
            return null;
        }
        yimiaodengji.setYouxiaoqi(dateformate.parse(jTextFieldYouxiaoqi.getText()));
        yimiaodengji.setQuantity(Integer.parseInt(jTextFieldQuantity.getText()));
        yimiaodengji.setPiqianfahegezhenno(232435);
        if (jComboBoxSource.getSelectedIndex() == 1) {
            yimiaodengji.setSource("进口");
            yimiaodengji.setTongguandanno(23244545);
        } else {
            yimiaodengji.setSource("国产");
        }
        return new SubmitFormTask(yimiaodengji);
    }

    @Action
    public void exit() {
        this.dispose();
    }

    private class SubmitFormTask extends YimiaodengjiUpdateTask {

        SubmitFormTask(Yimiaodengjitb yimiaodengji) {
            super(yimiaodengji, isNew ? YimiaodengjiUpdateTask.ENTITY_SAVE : YimiaodengjiUpdateTask.ENTITY_UPDATE);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            exit();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBoxJixing;
    private javax.swing.JComboBox jComboBoxSource;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextFieldQuantity;
    private javax.swing.JTextField jTextFieldYimiaoId;
    private javax.swing.JTextField jTextFieldYimiaoName;
    private javax.swing.JTextField jTextFieldYouxiaoqi;
    private javax.swing.JTextField jTextFieldguige;
    private javax.swing.JTextField jTextFieldkucunDown;
    private javax.swing.JTextField jTextFieldkucunUp;
    private javax.swing.JTextField jTextFieldpihao;
    private javax.swing.JTextField jTextFieldpiqianfahege;
    private javax.swing.JTextField jTextFieldpizhunwenhao;
    private javax.swing.JTextField jTextFieldshengchanqiye;
    private javax.swing.JTextField jTextFieldtongguandanNo;
    private javax.swing.JTextField jTextFieldunit;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
