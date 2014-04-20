/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Churukudantb;
import com.jskj.asset.client.bean.entity.Churukudanyimiaoliebiaotb;
import com.jskj.asset.client.bean.entity.Kehudanweitb;
import com.jskj.asset.client.bean.entity.Sale_detail_tb;
import com.jskj.asset.client.bean.entity.SaleyimiaoEntity;
import com.jskj.asset.client.bean.entity.YimiaochurukuEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseCellFocusListener;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.ckgl.task.CancelChuKu;
import com.jskj.asset.client.panel.ckgl.task.WeiChuKuYimiaoTask;
import com.jskj.asset.client.util.DanHao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Administrator
 */
public class YiMiaoChuKu2JDialog extends BaseDialog {

    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat riqiformate = new SimpleDateFormat("yyyy-MM-dd");
    private Churukudantb churukudan;
    private List<Kehudanweitb> kehudanweilist = new ArrayList<Kehudanweitb>();
    private List<Churukudanyimiaoliebiaotb> bindedMapyimiaoliebiaoList = new ArrayList<Churukudanyimiaoliebiaotb>();
    private float total = 0;
    private List<SaleyimiaoEntity> list;
    private List<Sale_detail_tb> saledetailMaplist = new ArrayList<Sale_detail_tb>();
    private boolean isNew;

    /**
     * Creates new form ymcrk1
     *
     * @param parent
     * @param modal
     */
    public YiMiaoChuKu2JDialog() {
        super();
        initComponents();

        churukudan = new Churukudantb();
        jTextFielddanjuNo.setText(DanHao.getDanHao("YMCK"));
        jTextFielddanjuNo.setEditable(false);
        jTextFieldzhidanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());

        //库房的popup
        ((BaseTextField) jTextFieldkufang).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "cangku/finddepot";
            }

            public String getConditionSQL() {
                String sql = "";
                if (!jTextFieldkufang.getText().trim().equals("")) {
                    sql = "(depot_name like \"%" + jTextFieldkufang.getText() + "%\"" + " or zujima like \"%" + jTextFieldkufang.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"depotId", "仓库编号"}, {"depotName", "仓库名称"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
//                    shenqingdan.setSupplierId((Integer) (bindedMap.get("supplierId")));
                    jTextFieldkufang.setText(bindedMap.get("depotName") == null ? "" : bindedMap.get("depotName").toString());
                }
            }
        });
        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"stockpileId", "库存编号", "false"}, {"yimiaoName", "疫苗名称", "true"}, {"source", "国产/出口", "false"}, {"tongguandanNo", "进口通关单编号", "false"}, {"quantity", "数量", "true"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"youxiaodate", "有效期", "false"}, {"unitId", "单位", "false"},
            {"piqianfaNo", "批签发合格证编号", "false"}, {"yimiaoPizhunwenhao", "批准文号", "true"}, {"stockpilePrice", "单价", "true"}, {"totalPrice", "合价", "true"},
            {"jingbanren", "经办人", "true"}, {"gongyingdanwei", "收货单位", "true"}, {"duifangjingbanren", "对方经办人", "true"}});
        editTable.registerPopup(1, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addxiafayimiao";
            }

            public String getConditionSQL() {
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += "sale_detail_id in (select distinct sale_detail.sale_detail_id from sale_detail,yimiao where and sale_detail.is_completed = 1 and sale_detail.status = 0 and sale_detail.price>0 and (yimiao.yimiao_name like \"%" + newColumnObj.toString() + "%\" or yimiao.zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")) ";
                } else {
                    sql += "sale_detail_id in (select distinct sale_detail_id from sale_detail where is_completed = 1 and status = 0 and price>0 )";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"saletb.saleId", "源单单号"}, {"saletb.saleDate", "申报日期"}, {"yimiaoAll.yimiaoName", "疫苗名称"},
                {"yimiaoAll.yimiaoJixing", "剂型"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiaoAll");
                    Object stockpilemap = bindedMap.get("stockpile");
                    Object saletbmap = bindedMap.get("saletb");
                    Object sale_detail_tbmap = bindedMap.get("sale_detail_tb");
                    Object kehudanweimap = bindedMap.get("kehudanwei");
                    HashMap yimiaoAll = (HashMap) yimiaomap;
                    HashMap stockpile = (HashMap) stockpilemap;
                    HashMap saletb = (HashMap) saletbmap;
                    HashMap sale_detail_tb = (HashMap) sale_detail_tbmap;
                    HashMap kehudanwei = (HashMap) kehudanweimap;

                    Churukudanyimiaoliebiaotb chukudan = new Churukudanyimiaoliebiaotb();
                    chukudan.setXiangdanId(Integer.parseInt((String) ("" + sale_detail_tb.get("saleDetailId"))));
                    bindedMapyimiaoliebiaoList.add(chukudan);

                    Sale_detail_tb saledetail = new Sale_detail_tb();
                    saledetail.setSaleDetailId(Integer.parseInt((String) ("" + sale_detail_tb.get("saleDetailId"))));
                    saledetail.setSaleId((String) sale_detail_tb.get("saleId"));
                    saledetailMaplist.add(saledetail);

                    Object yimiaoId = stockpile.get("stockpileId");
                    Object yimiaoName = yimiaoAll.get("yimiaoName");
                    Object yimiaoGuige = yimiaoAll.get("yimiaoGuige");
                    Object yimiaoJixing = yimiaoAll.get("yimiaoJixing");
                    Object shengchanqiye = yimiaoAll.get("yimiaoShengchanqiye");
                    Object unit = yimiaoAll.get("unitId");
                    Object pihao = stockpile.get("pihao");
                    Object youxiaoqi = stockpile.get("youxiaodate");
                    Object piqianfaNo = stockpile.get("piqianfano");
                    Object pizhunwenhao = yimiaoAll.get("yimiaoPizhunwenhao");
                    Object source = stockpile.get("source");
                    Object tongguandanNo = yimiaoAll.get("jinkoutongguanno");
                    Object quantity = sale_detail_tb.get("quantity");
                    Object price = sale_detail_tb.get("price");
                    Object totalPrice = sale_detail_tb.get("totalprice");
                    Object kehudanweiName = kehudanwei.get("kehudanweiName");
                    Object duifangjinbangren = kehudanwei.get("kehudanweiConstactperson");

                    editTable.insertValue(0, yimiaoId);
                    editTable.insertValue(1, yimiaoName);
                    editTable.insertValue(2, source);
                    editTable.insertValue(3, tongguandanNo);
                    editTable.insertValue(4, quantity);
                    editTable.insertValue(5, yimiaoGuige);
                    editTable.insertValue(6, yimiaoJixing);
                    editTable.insertValue(7, shengchanqiye);
                    editTable.insertValue(8, pihao);
                    editTable.insertValue(9, youxiaoqi);
                    editTable.insertValue(10, unit);
                    editTable.insertValue(11, piqianfaNo);
                    editTable.insertValue(12, pizhunwenhao);
                    editTable.insertValue(13, price);
                    editTable.insertValue(14, totalPrice);
                    editTable.insertValue(15, AssetClientApp.getSessionMap().getUsertb().getUserName());
                    editTable.insertValue(16, kehudanweiName);
                    editTable.insertValue(17, duifangjinbangren);

                    Kehudanweitb kehudanwei1 = new Kehudanweitb();
                    kehudanwei1.setKehudanweiId(Integer.parseInt("" + kehudanwei.get("kehudanweiId")));
                    kehudanweilist.add(kehudanwei1);

                }

            }
        });

        ((BaseTable) jTableyimiao).addCellListener(new BaseCellFocusListener() {
            public void editingStopped(int selectedRow, int selectedColumn) {
                int col = selectedColumn;
                int row = selectedRow;

                if (col == 6) {
                    if ((!(("" + jTableyimiao.getValueAt(row, 6)).equals("")))
                            && (!(("" + jTableyimiao.getValueAt(row, 7)).equals("")))) {
                        int count = Integer.parseInt("" + jTableyimiao.getValueAt(row, 6));
                        float price = Float.parseFloat("" + jTableyimiao.getValueAt(row, 7));
                        jTableyimiao.setValueAt(price * count, row, 8);
                    }
                    int rows = jTableyimiao.getRowCount();
                    total = 0;
                    for (int i = 0; i < rows; i++) {
                        if (!(("" + jTableyimiao.getValueAt(i, 8)).equals(""))) {
                            total += Float.parseFloat("" + jTableyimiao.getValueAt(i, 8));
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

        jLabel6 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton7 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldzhidanren = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jTextFielddanjuNo = new BaseTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        totalPrice = new javax.swing.JLabel();
        jTextFieldkufang = new BaseTextField();
        jLabel5 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoChuKu2JDialog.class);
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoChuKu2JDialog.class, this);
        jButton7.setAction(actionMap.get("save")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton12.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setBorderPainted(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setOpaque(false);
        jToolBar1.add(jButton7);

        jButton1.setAction(actionMap.get("buhege")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setName("jButton1"); // NOI18N
        jToolBar1.add(jButton1);

        jButton16.setIcon(resourceMap.getIcon("jButton16.icon")); // NOI18N
        jButton16.setText(resourceMap.getString("jButton16.text")); // NOI18N
        jButton16.setBorderPainted(false);
        jButton16.setFocusable(false);
        jButton16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton16.setName("jButton16"); // NOI18N
        jButton16.setOpaque(false);
        jToolBar1.add(jButton16);

        jButton18.setIcon(resourceMap.getIcon("jButton18.icon")); // NOI18N
        jButton18.setText(resourceMap.getString("jButton18.text")); // NOI18N
        jButton18.setBorderPainted(false);
        jButton18.setFocusable(false);
        jButton18.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton18.setName("jButton18"); // NOI18N
        jButton18.setOpaque(false);
        jToolBar1.add(jButton18);

        jButton19.setAction(actionMap.get("exit")); // NOI18N
        jButton19.setIcon(resourceMap.getIcon("jButton19.icon")); // NOI18N
        jButton19.setText(resourceMap.getString("jButton19.text")); // NOI18N
        jButton19.setBorderPainted(false);
        jButton19.setFocusable(false);
        jButton19.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton19.setName("jButton19"); // NOI18N
        jButton19.setOpaque(false);
        jToolBar1.add(jButton19);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldzhidanren.setEditable(false);
        jTextFieldzhidanren.setText(resourceMap.getString("jTextFieldzhidanren.text")); // NOI18N
        jTextFieldzhidanren.setName("jTextFieldzhidanren"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "日期", "数量", "生产企业", "规格", "剂型", "批号", "有效期至", "批签合格证编号", "批准文号", "供应单位", "单价", "合价", "经办人", "对方单位经办人"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableyimiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableyimiao.setName("jTableyimiao"); // NOI18N
        jScrollPane1.setViewportView(jTableyimiao);
        if (jTableyimiao.getColumnModel().getColumnCount() > 0) {
            jTableyimiao.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title0")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title1")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title2")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title5")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title6")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTableyimiao.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title7")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title8")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title9")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable1.columnModel.title12")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable1.columnModel.title13")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(12).setHeaderValue(resourceMap.getString("jTable1.columnModel.title10")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(13).setPreferredWidth(100);
            jTableyimiao.getColumnModel().getColumn(13).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N
        }

        jTextFielddanjuNo.setEditable(false);
        jTextFielddanjuNo.setName("jTextFielddanjuNo"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        totalPrice.setText(resourceMap.getString("totalPrice.text")); // NOI18N
        totalPrice.setName("totalPrice"); // NOI18N

        jTextFieldkufang.setName("jTextFieldkufang"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 312, Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2)))
                            .addComponent(jScrollPane1)))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldkufang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(totalPrice)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldkufang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldzhidanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(totalPrice))
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(YiMiaoChuKu2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoChuKu2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoChuKu2JDialog dialog = new YiMiaoChuKu2JDialog();
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

    //疫苗取消入库情况
    @Action
    public Task buhege() {
        if (bindedMapyimiaoliebiaoList.size() < 1) {
            AssetMessage.ERRORSYS("请选择要取消出库的疫苗！", this);
            return null;
        }
        List<SaleyimiaoEntity> lst = new ArrayList<SaleyimiaoEntity>();
        for (int i = 0; i < bindedMapyimiaoliebiaoList.size(); i++) {
            SaleyimiaoEntity lb = new SaleyimiaoEntity();
            Sale_detail_tb saledetail = new Sale_detail_tb();
            saledetail.setSaleDetailId(saledetailMaplist.get(i).getSaleDetailId());
            saledetail.setSaleId(saledetailMaplist.get(i).getSaleId());
            lb.setSale_detail_tb(saledetail);
            String reason = "";
            while (reason.isEmpty()) {
                reason = AssetMessage.showInputDialog(null, "请输入取消出库疫苗【"
                        + jTableyimiao.getValueAt(i, 1) + "】的理由(必输)：");
                if (reason == null) {
                    return null;
                }
            }
            lb.getSale_detail_tb().setReason("【出库】" + reason);
            lst.add(lb);
        }
        return new Cancel(lst);
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

    @Action
    public Task save() throws ParseException {
        YimiaochurukuEntity yimiaochukuEntity = new YimiaochurukuEntity();
        if (jTextFielddanjuNo.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入出库疫苗!");
            return null;
        }

        if (jTextFieldkufang.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请选择出库库房!");
            return null;
        }
        churukudan.setKufang(jTextFieldkufang.getText());
        churukudan.setChurukuId(jTextFielddanjuNo.getText());
        churukudan.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
        churukudan.setZhidanren(AssetClientApp.getSessionMap().getUsertb().getUserId());
        yimiaochukuEntity.setChurukutb(churukudan);

        List<Churukudanyimiaoliebiaotb> list = new ArrayList<Churukudanyimiaoliebiaotb>();

        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            Churukudanyimiaoliebiaotb yimiaoliebiao = new Churukudanyimiaoliebiaotb();
            yimiaoliebiao.setChurukuId(jTextFielddanjuNo.getText());
            yimiaoliebiao.setPihao((String) yimiaotable.getValue(i, "pihao"));
            yimiaoliebiao.setPiqianfahegeno((String) yimiaotable.getValue(i, "piqianfaNo"));
            yimiaoliebiao.setPrice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "stockpilePrice"))));
            yimiaoliebiao.setSource((String) ("" + yimiaotable.getValue(i, "source")));
            yimiaoliebiao.setTongguandanno((String) ("" + yimiaotable.getValue(i, "tongguandanNo")));
            yimiaoliebiao.setYouxiaoqi(riqiformate.parse((String) ("" + yimiaotable.getValue(i, "youxiaodate"))));
            yimiaoliebiao.setYimiaoId(Integer.parseInt((String) ("" + yimiaotable.getValue(i, "stockpileId"))));
            yimiaoliebiao.setQuantity(Integer.parseInt((String) ("" + yimiaotable.getValue(i, "quantity"))));
            yimiaoliebiao.setTotalprice(yimiaoliebiao.getPrice() * yimiaoliebiao.getPrice());
            yimiaoliebiao.setXiangdanId(bindedMapyimiaoliebiaoList.get(i).getXiangdanId());
            yimiaoliebiao.setWanglaidanweiId(kehudanweilist.get(i).getKehudanweiId());
            list.add(yimiaoliebiao);
        }
        yimiaochukuEntity.setResult(list);

        String serviceId = "yimiaochuku/add";
        return new CommUpdateTask<YimiaochurukuEntity>(yimiaochukuEntity, serviceId) {

            @Override
            public void responseResult(ComResponse<YimiaochurukuEntity> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    JOptionPane.showMessageDialog(null, "提交成功！");
                    exit();
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    YiMiaoChuKu2JDialog ymck2 = new YiMiaoChuKu2JDialog();
                    ymck2.setLocationRelativeTo(mainFrame);
                    AssetClientApp.getApplication().show(ymck2);
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YiMiaoChuKu2JDialog.this);
                }
            }

        };

    }

    private class SaveTask extends org.jdesktop.application.Task<Object, Void> {

        SaveTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to SaveTask fields, here.
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
        String sql = " (sale_id like \"YMXS%\") and is_completed = 1 and status = 0";
        new CloseTask(sql).execute();
    }

    public void close() {
        this.dispose();
    }

    private class CloseTask extends WeiChuKuYimiaoTask {

        public CloseTask(String sql) {
            super(sql);
        }

        @Override
        public void responseResult(CommFindEntity<SaleyimiaoEntity> response) {

            logger.debug("get current size:" + response.getResult().size());
            list = response.getResult();
            if (list != null && list.size() > 0) {
                StringBuilder string = new StringBuilder();
                for (SaleyimiaoEntity zc : list) {
                    string.append("单据").append(zc.getSale_detail_tb().getSaleId()).append("有未出库项【")
                            .append(zc.getYimiaoAll().getYimiaoName()).append("】\n");
                }
                string.append("是否继续出库？选“否”会要求输入原因，并不再出库以上所有疫苗");
                int result = AssetMessage.showConfirmDialog(null, string.toString(),
                        "确认", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    return;
                }
                for (SaleyimiaoEntity lb : list) {
                    String reason = null;
                    while (reason == null || reason.isEmpty()) {
                        reason = AssetMessage.showInputDialog(null, "请输入取消出库疫苗【"
                                + lb.getYimiaoAll().getYimiaoName() + "】的理由(必输)：");
                    }
                    lb.getSale_detail_tb().setReason("【出库】" + reason);
                }
                new Cancel(list).execute();
            }
            close();
        }

    }

    private class Cancel extends CancelChuKu {

        public Cancel(List<SaleyimiaoEntity> zc) {
            super(zc);
        }

        @Override
        public void onSucceeded(Object object) {
            if (object instanceof Exception) {
                Exception e = (Exception) object;
                AssetMessage.ERRORSYS(e.getMessage());
                WeiChuKuYimiaoTask.logger.error(e);
                return;
            }
            close();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            YiMiaoChuKu2JDialog yimiaochuku = new YiMiaoChuKu2JDialog();
            yimiaochuku.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaochuku);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFielddanjuNo;
    private javax.swing.JTextField jTextFieldkufang;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JTextField jTextFieldzhidanren;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel totalPrice;
    // End of variables declaration//GEN-END:variables
}
