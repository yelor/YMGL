/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Baosuntb;
import com.jskj.asset.client.bean.entity.Stockpiletb;
import com.jskj.asset.client.bean.entity.YimiaoAll;
import com.jskj.asset.client.bean.entity.YimiaobaosunDetailEntity;
import com.jskj.asset.client.bean.entity.Yimiaobaosuntb;
import com.jskj.asset.client.bean.entity.YimiaobaosuntbFindEntity;
import com.jskj.asset.client.bean.entity.YimiaobaosunxiangdanEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseCellFocusListener;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.panel.ckgl.task.YimiaobaosunUpdateTask;
import static com.jskj.asset.client.panel.slgl.task.ShenQingTask.logger;
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
import net.sf.dynamicreports.report.exception.DRException;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Administrator
 */
public class YiMiaoBaoSunJDialog extends BaseDialog {

    private Yimiaobaosuntb yimiaobaosun;
    private Baosuntb baosun;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean isNew = true;
    private YimiaobaosuntbFindEntity yimiaobaosunEntity;
    private YimiaobaosunxiangdanEntity yimiaobaosunxiangdanEntity;
    private List<Stockpiletb> stockpileList = new ArrayList<Stockpiletb>();
    private float total = 0;

    /**
     * Creates new form GuDingZiChanRuKu
     */
    /**
     * Creates new form ymbs
     */
    public YiMiaoBaoSunJDialog() {
        super();
        initComponents();

        jTextFieldBaosunId.setText(DanHao.getDanHao(DanHao.TYPE_YIMIAOBS));
        jTextFieldBaosunId.setEditable(false);
        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldJingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());

        //库房的popup
        ((BaseTextField) jTextFieldCangku).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "cangku/finddepot";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!jTextFieldCangku.getText().trim().equals("")) {
                    sql = "(depot_name like \"%" + jTextFieldCangku.getText() + "%\"" + " or zujima like \"%" + jTextFieldCangku.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"depotId", "仓库编号"}, {"depotName", "仓库名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
//                    shenqingdan.setSupplierId((Integer) (bindedMap.get("supplierId")));
                    jTextFieldCangku.setText(bindedMap.get("depotName") == null ? "" : bindedMap.get("depotName").toString());
                }
            }
        });

        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"stockpileId", "库存编号"}, {"yimiaoName", "疫苗名称", "true"}, {"yimiaoType", "疫苗类型", "false"},{"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"shengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"unit", "单位", "false"}, {"youxiaoqi", "有效期至", "false"}, {"baosunQuantity", "数量", "true"}, {"price", "单价", "false"}, {"totalprice", "合价", "false"},
            {"xiaohuiAddr", "销毁地点", "true"}, {"xiaohuiDate", "销毁时间", "false"}, {"xiaohuiType", "销毁方式", "true"}, {"baosunReason", "报损原因", "true"}});

        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
//                这个URL是在Sale_detailController里面
                return Constants.HTTP + Constants.APPID + "addkucunyimiao";
            }

            public String getConditionSQL() {
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "stockPile_quantity>0";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += " and stockpile_id in (select distinct stockpile_id from stockpile,yimiao where stockpile.yimiao_id =yimiao.yimiao_id and (yimiao.yimiao_name like \"%" + newColumnObj.toString() + "%\" or yimiao.zujima like \"%" + newColumnObj.toString() + "%\")) ";
                }
                
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"stockpileId", "库存编号"}, {"yimiao.yimiaoName", "疫苗名称"}, {"pihao", "批号"},
                {"yimiao.yimiaoType", "疫苗类型"},{"youxiaodate", "有效期"}, {"stockpileQuantity", "库存数量"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Stockpiletb stockpile = new Stockpiletb();
                    stockpile.setStockpileQuantity(Integer.parseInt("" + bindedMap.get("stockpileQuantity")));
                    stockpileList.add(stockpile);

                    Object yimiaomap = bindedMap.get("yimiao");
                    HashMap yimiao = (HashMap) yimiaomap;
                    Object kucunId = bindedMap.get("stockpileId");
                    Object yimiaoName = yimiao.get("yimiaoName");
                    Object yimiaoType = yimiao.get("yimiaoType");
                    Object yimiaoGuige = yimiao.get("yimiaoGuige");
                    Object yimiaoJixing = yimiao.get("yimiaoJixing");
                    Object shengchanqiye = yimiao.get("yimiaoShengchanqiye");
                    Object unit = yimiao.get("unitId");
                    Object price = bindedMap.get("stockpilePrice");
                    String youxiaoqi = (String) bindedMap.get("youxiaodate").toString().substring(0, 10);
                    Object pihao = bindedMap.get("pihao");
                    
                    for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
                        BaseTable yimiaotable = ((BaseTable) jTableyimiao);
                        if (yimiaotable.getValue(i, "stockpileId").toString().trim().equals("" + bindedMap.get("stockpileId"))) {
                            AssetMessage.INFO("已经添加了该疫苗！", YiMiaoBaoSunJDialog.this);
                            return;
                        }
                    }

                    editTable.insertValue(0, kucunId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, yimiaoType);
                    editTable.insertValue(3, yimiaoGuige);
                    editTable.insertValue(4, yimiaoJixing);
                    editTable.insertValue(5, shengchanqiye);
                    editTable.insertValue(6, pihao);
                    editTable.insertValue(7, unit);
                    editTable.insertValue(8, youxiaoqi);
                    editTable.insertValue(10, price);
                    editTable.insertValue(13, dateformate.format(new Date()).toString());

                }

            }
        });

        //        自动计算出疫苗的合价显示
        ((BaseTable) jTableyimiao).addCellListener(new BaseCellFocusListener() {
            public void editingStopped(int selectedRow, int selectedColumn) {
                int col = selectedColumn;
                int row = selectedRow;

                if (col == 9) {
                    if ((!(("" + jTableyimiao.getValueAt(row, 9)).equals("")))
                            && (!(("" + jTableyimiao.getValueAt(row, 10)).equals("")))) {
                        try {
                            int count = Integer.parseInt("" + jTableyimiao.getValueAt(row, 9));
                            float price = Float.parseFloat("" + jTableyimiao.getValueAt(row, 10));
                            jTableyimiao.setValueAt(price * count, row, 11);
                        } catch (NumberFormatException e) {
                            AssetMessage.ERRORSYS("第" + (row + 1) + "个疫苗报损数量输入不合法，请输入纯数字，不能包含字母或特殊字符！");
                            return;
                        }
                    }
                    int rows = jTableyimiao.getRowCount();
                    total = 0;
                    for (int i = 0; i < rows; i++) {
                        if (!(("" + jTableyimiao.getValueAt(i, 11)).equals(""))) {
                            total += Float.parseFloat("" + jTableyimiao.getValueAt(i, 11));
                        }
                    }
                    totalPrice.setText(total + "元");
                }
            }
        }
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jTextFieldCangku = new BaseTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldBaosunId = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jLabel2 = new javax.swing.JLabel();
        jTextFieldJingbanren = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        totalPrice = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoBaoSunJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextFieldCangku.setName("jTextFieldCangku"); // NOI18N

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoBaoSunJDialog.class, this);
        jButton2.setAction(actionMap.get("submitForm")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setOpaque(false);
        jToolBar1.add(jButton2);

        jButton3.setAction(actionMap.get("print")); // NOI18N
        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setOpaque(false);
        jToolBar1.add(jButton3);

        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setOpaque(false);
        jToolBar1.add(jButton5);

        jButton8.setAction(actionMap.get("exit")); // NOI18N
        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setBorderPainted(false);
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton8.setName("jButton8"); // NOI18N
        jButton8.setOpaque(false);
        jToolBar1.add(jButton8);

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N
        jTextFieldzhidanDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldzhidanDateActionPerformed(evt);
            }
        });

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldBaosunId.setEditable(false);
        jTextFieldBaosunId.setName("jTextFieldBaosunId"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "疫苗编号", "疫苗名称", "规格", "剂型", "生产企业", "批号", "有效期至", "单位", "单价", "数量", "合价", "销毁地点", "销毁时间", "销毁方式", "报损原因"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableyimiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableyimiao.setName("jTableyimiao"); // NOI18N
        jScrollPane2.setViewportView(jTableyimiao);
        if (jTableyimiao.getColumnModel().getColumnCount() > 0) {
            jTableyimiao.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title0")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title1")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title2")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title14")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title5")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title7")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable1.columnModel.title6")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable1.columnModel.title9")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable1.columnModel.title10")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(12).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(13).setHeaderValue(resourceMap.getString("jTable1.columnModel.title12")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(14).setHeaderValue(resourceMap.getString("jTable1.columnModel.title13")); // NOI18N
        }

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldJingbanren.setEditable(false);
        jTextFieldJingbanren.setName("jTextFieldJingbanren"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        totalPrice.setText(resourceMap.getString("totalPrice.text")); // NOI18N
        totalPrice.setName("totalPrice"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldBaosunId, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldJingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldCangku, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPrice)
                        .addGap(3, 3, 3))))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldBaosunId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldJingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(jTextFieldCangku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(totalPrice))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldzhidanDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldzhidanDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldzhidanDateActionPerformed

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
            java.util.logging.Logger.getLogger(YiMiaoBaoSunJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoBaoSunJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoBaoSunJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoBaoSunJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                YiMiaoBaoSunJDialog dialog = new YiMiaoBaoSunJDialog(new javax.swing.JFrame(), true);
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

    public void setAddOrUpdate(boolean b) {
        isNew = b;
        if (isNew) {
            this.setTitle("Ⅱ类疫苗销售单");
            baosun = new Baosuntb();
            yimiaobaosun = new Yimiaobaosuntb();
        } else {
            this.setTitle("Ⅱ类疫苗销售单");
        }
    }

    public void setUpdatedData(Yimiaobaosuntb yimiaobaosun) {
        if (yimiaobaosun == null) {
            return;
        }
        this.yimiaobaosun = yimiaobaosun;
        jTextFieldBaosunId.setText((yimiaobaosun.getBaosunId()).toString());

    }

    @Action
    public Task submitForm() throws ParseException {
        jTableyimiao.getCellEditor(jTableyimiao.getSelectedRow(),
                jTableyimiao.getSelectedColumn()).stopCellEditing();
        if (jTextFieldzhidanDate.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入制单日期!");
            return null;
        } else if (jTextFieldCangku.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入仓库!");
            return null;
        }
        yimiaobaosunEntity = new YimiaobaosuntbFindEntity();
        baosun = new Baosuntb();
        baosun.setBaosunId(jTextFieldBaosunId.getText());
        baosun.setBaosunDate(dateformate.parse(jTextFieldzhidanDate.getText()));
        baosun.setDeport("冻库");
        System.out.println(AssetClientApp.getSessionMap().getUsertb().getUserId());
        baosun.setZhidanren(AssetClientApp.getSessionMap().getUsertb().getUserId());
        baosun.setJingbanren(AssetClientApp.getSessionMap().getUsertb().getUserId());
        total = 0;

        List<Yimiaobaosuntb> list = new ArrayList<Yimiaobaosuntb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            yimiaobaosun = new Yimiaobaosuntb();
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            yimiaobaosun.setBaosunId(jTextFieldBaosunId.getText());
            yimiaobaosun.setBaosunDate(dateformate.parse(jTextFieldzhidanDate.getText()));
            yimiaobaosun.setKucunId(Integer.parseInt(yimiaotable.getValue(i, "stockpileId").toString()));
            if (yimiaotable.getValue(i, "baosunQuantity").equals("")) {
                AssetMessage.ERRORSYS("请输入疫苗报损数量!");
                return null;
            } else if (Integer.parseInt("" + yimiaotable.getValue(i, "baosunQuantity")) > stockpileList.get(i).getStockpileQuantity()) {
                AssetMessage.ERRORSYS(yimiaotable.getValue(i, "yimiaoName").toString() + "报损数量不能大于库存数量:" + stockpileList.get(i).getStockpileQuantity());
                return null;
            }
            yimiaobaosun.setQuantity(Integer.parseInt(yimiaotable.getValue(i, "baosunQuantity").toString()));            
            yimiaobaosun.setPrice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "price"))));
            yimiaobaosun.setTotalprice(yimiaobaosun.getQuantity() * yimiaobaosun.getPrice());
            yimiaobaosun.setXiaohuiaddr((String) yimiaotable.getValue(i, "xiaohuiAddr"));
            yimiaobaosun.setXiaohuidate(dateformate.parse(jTextFieldzhidanDate.getText()));
            yimiaobaosun.setXiaohuireason((String) yimiaotable.getValue(i, "baosunReason"));
            yimiaobaosun.setXiaohuitype((String) yimiaotable.getValue(i, "xiaohuiType"));
            list.add(yimiaobaosun);
            total += yimiaobaosun.getTotalprice();
        }
        baosun.setDanjujine(total);
        yimiaobaosunEntity.setBaosun(baosun);
        yimiaobaosunEntity.setResult(list);
        return new SubmitFormTask(yimiaobaosunEntity);

    }

    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", jTextFieldBaosunId.getText()},
                    {"制单日期", jTextFieldzhidanDate.getText()},
                    {"经办人", jTextFieldJingbanren.getText()},
                    {"仓库", jTextFieldCangku.getText()}},
                    jTableyimiao,
                    new String[][]{{"制单人", jTextFieldzhidanren.getText()},});
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    @Action
    public void exit() {
        this.dispose();
    }

    private class SubmitFormTask extends YimiaobaosunUpdateTask {

        SubmitFormTask(YimiaobaosuntbFindEntity yimiaobaosunEntiy) {
            super(yimiaobaosunEntiy, isNew ? YimiaobaosunUpdateTask.ENTITY_SAVE : YimiaobaosunUpdateTask.ENTITY_UPDATE);
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
            YiMiaoBaoSunJDialog ymbs = new YiMiaoBaoSunJDialog();
            ymbs.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(ymbs);
        }
    }

    public YiMiaoBaoSunJDialog(final JDialog parent, YimiaobaosunxiangdanEntity yimiaobaosunxiangdanEntity) {
        super();
        initComponents();
        this.yimiaobaosunxiangdanEntity = yimiaobaosunxiangdanEntity;
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

        jButton2.setEnabled(false);

        jTextFieldBaosunId.setEditable(false);
        jTextFieldBaosunId.setText(yimiaobaosunxiangdanEntity.getBaosuntb().getBaosunId());
        jTextFieldzhidanDate.setText(DateHelper.format(yimiaobaosunxiangdanEntity.getBaosuntb().getBaosunDate(), "yyyy-MM-dd HH:mm:ss"));
        jTextFieldzhidanDate.setEditable(false);
        jTextFieldJingbanren.setEditable(false);
        jTextFieldJingbanren.setText(yimiaobaosunxiangdanEntity.getUserAll().getUserName());
        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(yimiaobaosunxiangdanEntity.getUserAll().getUserName());
        jTextFieldCangku.setEditable(false);
        jTextFieldCangku.setText("" + yimiaobaosunxiangdanEntity.getBaosuntb().getDeport());
        totalPrice.setText("" + yimiaobaosunxiangdanEntity.getBaosuntb().getDanjujine() + "元");

        setListTable(yimiaobaosunxiangdanEntity.getResult());
    }

    public void setListTable(List<YimiaobaosunDetailEntity> yimiaobaosunDetailEntityList) {

        int size = yimiaobaosunDetailEntityList.size();
        Object[][] o = new Object[size][15];
        for (int i = 0; i < size; i++) {
            Yimiaobaosuntb yimiaobaosuntb = yimiaobaosunDetailEntityList.get(i).getYimiaobaosuntb();
            YimiaoAll yimiaoAll = yimiaobaosunDetailEntityList.get(i).getYimiaoAll();
            Stockpiletb stockpile = yimiaobaosunDetailEntityList.get(i).getStockpileYimiao();
            o[i] = new Object[]{yimiaoAll.getYimiaoId(), yimiaoAll.getYimiaoName(), yimiaoAll.getYimiaoGuige(), yimiaoAll.getYimiaoJixing(), yimiaoAll.getYimiaoShengchanqiye(), stockpile.getPihao(), yimiaoAll.getUnitId(),
                 new SimpleDateFormat("yyyy-MM-dd").format((Date)stockpile.getYouxiaodate()), yimiaobaosuntb.getQuantity(), stockpile.getStockpilePrice(), yimiaobaosuntb.getQuantity() * stockpile.getStockpilePrice(), yimiaobaosuntb.getXiaohuiaddr(), 
                  new SimpleDateFormat("yyyy-MM-dd").format((Date)yimiaobaosuntb.getXiaohuidate()), yimiaobaosuntb.getXiaohuitype(), yimiaobaosuntb.getXiaohuireason()};
        }

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
                o,
                new String[]{
                    "疫苗编号", "疫苗名称", "规格", "剂型", "生产企业", "批号", "单位", "有效期", "数量", "单价", "合价", "销毁地点", "销毁时间", "销毁方式", "报损原因"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextField jTextFieldBaosunId;
    private javax.swing.JTextField jTextFieldCangku;
    private javax.swing.JTextField jTextFieldJingbanren;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel totalPrice;
    // End of variables declaration//GEN-END:variables
}
