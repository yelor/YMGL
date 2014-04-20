/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Yimiaodengjitb;
import com.jskj.asset.client.bean.entity.Yimiaoshenqingdantb;
import com.jskj.asset.client.bean.entity.YimiaoshenqingliebiaoEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ScanButton;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.panel.ymgl.task.CancelYimiaoDengji;
import com.jskj.asset.client.panel.ymgl.task.WeidengjiyimiaoTask;
import com.jskj.asset.client.panel.ymgl.task.YimiaodengjiUpdateTask;
import com.jskj.asset.client.util.DateChooser;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoDengJi1JDialog extends BaseDialog {

    private static final Logger logger = Logger.getLogger(YiMiaoDengJi1JDialog.class);
    private Yimiaodengjitb yimiaodengji;
    private SimpleDateFormat dateformate;
    private boolean isNew;
    private List<YimiaoshenqingliebiaoEntity> list;
    private String shengqingdanID;
    private int xiangdanId;

    /**
     * Creates new form YiMiaoDengJi1JDialog
     *
     */
    public YiMiaoDengJi1JDialog() {
        super();
        init();
        initComponents();
        yimiaodengji = new Yimiaodengjitb();
        isNew = false;
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

        ((BaseTextField) jTextFieldYimiaoName).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addlingyongyimiao";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!jTextFieldYimiaoName.getText().trim().equals("")) {
                    sql += "xiangdan_id in (select distinct yimiaoshenqingdan.xiangdan_id from yimiaoshenqingdan,yimiao where yimiaoshenqingdan.danjuleixing_id=5 and yimiaoshenqingdan.is_completed = 1 and yimiaoshenqingdan.status = 0 and (yimiao.yimiao_name like \"%" + jTextFieldYimiaoName.getText() + "%\" or yimiao.zujima like \"%" + jTextFieldYimiaoName.getText().toLowerCase() + "%\")) ";
                } else {
                    sql += "xiangdan_id in (select distinct xiangdan_id from yimiaoshenqingdan where danjuleixing_id=5 and is_completed = 1 and status = 0)";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"shenqingdan.shenqingdanId", "源单单号"}, {"shenqingdan.shenqingdanDate", "领用日期"}, {"yimiaoAll.yimiaoName", "疫苗名称"},
                {"yimiaoAll.yimiaoJixing", "剂型"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiaoAll");
                    HashMap yimiaoAll = (HashMap) yimiaomap;
                    Object yimiaoshenqingdanmap = bindedMap.get("yimiaoshenqingtb");
                    HashMap yimiaoshenqingdan = (HashMap) yimiaoshenqingdanmap;

                    dateformate = new SimpleDateFormat("yyyy-MM-dd");
                    jTextFieldYimiaoId.setText(yimiaoAll.get("yimiaoId") == null ? "" : yimiaoAll.get("yimiaoId").toString());
                    jTextFieldYimiaoName.setText(yimiaoAll.get("yimiaoName") == null ? "" : yimiaoAll.get("yimiaoName").toString());
                    jTextFieldguige.setText(yimiaoAll.get("yimiaoGuige") == null ? "" : yimiaoAll.get("yimiaoGuige").toString());
                    jTextFieldYimiaoJixing.setText(yimiaoAll.get("yimiaoJixing") == null ? "" : yimiaoAll.get("yimiaoJixing").toString());
                    jTextFieldQuantity.setText(yimiaoshenqingdan.get("quantity") == null ? "" : yimiaoshenqingdan.get("quantity").toString());
                    jTextFieldshengchanqiye.setText(yimiaoAll.get("yimiaoShengchanqiye") == null ? "" : yimiaoAll.get("yimiaoShengchanqiye").toString());
                    jTextFieldpizhunwenhao.setText(yimiaoAll.get("yimiaoPizhunwenhao") == null ? "" : yimiaoAll.get("yimiaoPizhunwenhao").toString());
                    jTextFieldunit.setText(yimiaoAll.get("unitId") == null ? "" : yimiaoAll.get("unitId").toString());
                    jTextFieldkucunDown.setText(yimiaoAll.get("yimiaoStockdown") == null ? "" : yimiaoAll.get("yimiaoStockdown").toString());
                    jTextFieldkucunUp.setText(yimiaoAll.get("yimiaoStockup") == null ? "" : yimiaoAll.get("yimiaoStockup").toString());
                    yimiaodengji.setYimiaoId((Integer) yimiaoAll.get("yimiaoId"));
                    yimiaodengji.setXiangdanId((Integer) yimiaoshenqingdan.get("xiangdanId"));
                    xiangdanId = (Integer) yimiaoshenqingdan.get("xiangdanId");
                    shengqingdanID = (String) yimiaoshenqingdan.get("shenqingdanId");
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
                    jTextFieldYimiaoJixing.setText(bindedMap.get("yimiaoName") == null ? "" : bindedMap.get("yimiaoName").toString());
                    jTextFieldguige.setText(bindedMap.get("yimiaoGuige") == null ? "" : bindedMap.get("yimiaoGuige").toString());

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
        jTextFieldYimiaoJixing = new javax.swing.JTextField();
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
        jTextFieldYimiaoName = new BaseTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new ScanButton();
        jButton2 = new ScanButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        jTextFieldYimiaoJixing.setName("jTextFieldYimiaoJixing"); // NOI18N
        jTextFieldYimiaoJixing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldYimiaoJixingActionPerformed(evt);
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

        jTextFieldtongguandanNo.setEditable(false);
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

        jTextFieldpizhunwenhao.setText(resourceMap.getString("jTextFieldpizhunwenhao.text")); // NOI18N
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
        jComboBoxSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSourceActionPerformed(evt);
            }
        });

        jTextFieldYimiaoName.setName("jTextFieldYimiaoName"); // NOI18N
        jTextFieldYimiaoName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldYimiaoNameActionPerformed(evt);
            }
        });

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
                        .addComponent(jTextFieldYimiaoJixing, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jTextFieldYimiaoJixing, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoDengJi1JDialog.class, this);
        jButton3.setAction(actionMap.get("submitForm")); // NOI18N
        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setOpaque(false);
        jToolBar1.add(jButton3);

        jButton5.setAction(actionMap.get("buhege")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setName("jButton5"); // NOI18N
        jToolBar1.add(jButton5);

        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

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
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldYimiaoIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldYimiaoIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldYimiaoIdActionPerformed

    private void jTextFieldYimiaoJixingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldYimiaoJixingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldYimiaoJixingActionPerformed

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

    private void jComboBoxSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSourceActionPerformed
        // TODO add your handling code here:

        if (jComboBoxSource.getSelectedIndex() == 1) {
            jTextFieldtongguandanNo.setEditable(true);
        } else {
            jTextFieldtongguandanNo.setEditable(false);
            yimiaodengji.setTongguandanno(null);
        }
    }//GEN-LAST:event_jComboBoxSourceActionPerformed

    private void jTextFieldYimiaoNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldYimiaoNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldYimiaoNameActionPerformed

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
                YiMiaoDengJi1JDialog dialog = new YiMiaoDengJi1JDialog();
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
        } else if (jTextFieldpihao.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗批号!");
            return null;
        }
        if (jTextFieldpiqianfahege.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗批签发合格证编号!");
            return null;
        }
        yimiaodengji.setPihao(jTextFieldpihao.getText());
        dateformate = new SimpleDateFormat("yyyy-MM-dd");
        if (jTextFieldYouxiaoqi.getText().equals("")) {
            AssetMessage.ERRORSYS("请输入疫苗有效期!");
            return null;
        }
        yimiaodengji.setYouxiaoqi(dateformate.parse(jTextFieldYouxiaoqi.getText()));
        yimiaodengji.setQuantity(Integer.parseInt(jTextFieldQuantity.getText()));
        yimiaodengji.setPiqianfahegezhenno(jTextFieldpiqianfahege.getText());
        if (jComboBoxSource.getSelectedIndex() == 1) {
            if (jTextFieldtongguandanNo.getText().equals("")) {
                AssetMessage.ERRORSYS("请输入疫苗进口通关单编号!");
                return null;
            }
            yimiaodengji.setTongguandanno(jTextFieldtongguandanNo.getText());
        } else if (jComboBoxSource.getSelectedIndex() == 0) {
            yimiaodengji.setTongguandanno(null);
        }
        yimiaodengji.setSource((String) jComboBoxSource.getSelectedItem());
        return new SubmitFormTask(yimiaodengji);
    }

    //单个疫苗登记不合格情况
    @Action
    public Task buhege() {
        if (jTextFieldYimiaoName.getText().isEmpty()) {
            AssetMessage.ERRORSYS("请输入疫苗名称！", this);
            return null;
        }
        if (jTextFieldYimiaoId.getText().isEmpty()) {
            AssetMessage.ERRORSYS("请输入疫苗编号！", this);
            return null;
        }
        List<YimiaoshenqingliebiaoEntity> list = new ArrayList<YimiaoshenqingliebiaoEntity>();
        YimiaoshenqingliebiaoEntity lb = new YimiaoshenqingliebiaoEntity();
        Yimiaoshenqingdantb yimiaoshenqingdan = new Yimiaoshenqingdantb();
        yimiaoshenqingdan.setShenqingdanId(shengqingdanID);
        yimiaoshenqingdan.setXiangdanId(xiangdanId);
        lb.setYimiaoshenqingdan(yimiaoshenqingdan);
        String reason = "";
        while (reason.isEmpty()) {
            reason = AssetMessage.showInputDialog(null, "请输入取消登记疫苗【"
                    + jTextFieldYimiaoName.getText() + "】的理由(必输)：");
            if (reason == null) {
                return null;
            }
        }
        lb.getYimiaoshenqingdan().setReason("【登记】" + reason);
        list.add(lb);
        return new Cancel(list);
    }

    private class BuhegeTask extends org.jdesktop.application.Task<Object, Void> {

        BuhegeTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to BuhegeTask fields, here.
            super(app);
        }

        @Override
        protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    public void setNew() {
        isNew = true;
    }

    @Action
    public void exit() {
        if (isNew) {
            close();
            return;
        }
        String sql = " shenqingdan_id like \"YMLQ%\" and danjuleixing_id=5 and is_completed = 1 and status = 0";
        new CloseTask(sql).execute();
    }

    public void close() {
        this.dispose();
    }

    private class CloseTask extends WeidengjiyimiaoTask {

        public CloseTask(String sql) {
            super(sql, "普通");
        }

        @Override
        public void responseResult(CommFindEntity<YimiaoshenqingliebiaoEntity> response) {

            logger.debug("get current size:" + response.getResult().size());
            list = response.getResult();
            if (list != null && list.size() > 0) {
                StringBuilder string = new StringBuilder();
                for (YimiaoshenqingliebiaoEntity yimiao : list) {
                    string.append("单据").append(yimiao.getYimiaoshenqingdan().getShenqingdanId()).append("有未登记项（")
                            .append(yimiao.getYimiao().getYimiaoName()).append(")\n");
                }
                string.append("是否继续登记？选“否”或“取消”会要求输入原因，并不再登记以上所有资产");
                int result = AssetMessage.showConfirmDialog(null, string.toString(), "确认", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    return;
                }
                for (YimiaoshenqingliebiaoEntity lb : list) {
                    String reason = "";
                    while (reason.isEmpty()) {
                        reason = AssetMessage.showInputDialog(null, "请输入取消登记资产【"
                                + lb.getYimiao().getYimiaoName() + "】的理由(必输)：");
                        if (reason == null) {
                            return;
                        }
                    }
                    lb.getYimiaoshenqingdan().setReason("【登记】" + reason);
                }
                new Cancel(list).execute();
            }
            close();
        }

    }

    private class Cancel extends CancelYimiaoDengji {

        public Cancel(List<YimiaoshenqingliebiaoEntity> zc) {
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
            YiMiaoDengJi1JDialog yimiaoDengJi1JDialog = new YiMiaoDengJi1JDialog();
            yimiaoDengJi1JDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaoDengJi1JDialog);
        }

    }

    private class SubmitFormTask extends YimiaodengjiUpdateTask {

        SubmitFormTask(Yimiaodengjitb yimiaodengji) {
            super(yimiaodengji, YimiaodengjiUpdateTask.ENTITY_SAVE);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            AssetMessage.INFO("提交成功！", YiMiaoDengJi1JDialog.this);
            close();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            YiMiaoDengJi1JDialog yiMiaoDengJi1JDialog = new YiMiaoDengJi1JDialog();
            yiMiaoDengJi1JDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yiMiaoDengJi1JDialog);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JTextField jTextFieldYimiaoJixing;
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
