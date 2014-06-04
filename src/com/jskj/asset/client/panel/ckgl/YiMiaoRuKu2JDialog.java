/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ckgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Churukudantb;
import com.jskj.asset.client.bean.entity.Churukudanyimiaoliebiaotb;
import com.jskj.asset.client.bean.entity.YimiaochurukuEntity;
import com.jskj.asset.client.bean.entity.Yimiaoshenqingdantb;
import com.jskj.asset.client.bean.entity.YimiaoshenqingliebiaoEntity;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ScanButton;
import com.jskj.asset.client.layout.ws.ComResponse;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.layout.ws.CommUpdateTask;
import com.jskj.asset.client.panel.ymgl.task.CancelYimiaoDengji;
import com.jskj.asset.client.panel.ymgl.task.WeidengjiyimiaoTask;
import static com.jskj.asset.client.panel.ymgl.task.WeidengjiyimiaoTask.logger;
import com.jskj.asset.client.util.DanHao;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.dynamicreports.report.exception.DRException;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Administrator
 */
public class YiMiaoRuKu2JDialog extends BaseDialog {

    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat riqiformate = new SimpleDateFormat("yyyy-MM-dd");
    private Churukudantb churukudan;
    private boolean isNew;
    private List<YimiaoshenqingliebiaoEntity> list;
    private Map xiangdanIdmap;
    private Map shenqingdanIdmap;
    private Map supplierIdmap;
    private boolean wait;
    private String sqid;

    /**
     * Creates new form YiMiaoRuKu2JDialog
     */
    public YiMiaoRuKu2JDialog() {
        super();
        initComponents();
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

        churukudan = new Churukudantb();

        jTextFielddanjuNo.setText(DanHao.getDanHao("YMRK"));
        jTextFielddanjuNo.setEditable(false);
        jTextFieldzhidanDate.setText(dateformate.format(new Date()));
        jTextFieldjingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());

        xiangdanIdmap = new HashMap();
        shenqingdanIdmap = new HashMap();
        supplierIdmap = new HashMap();

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
            {"xaingdanId", "详单编号", "false"}, {"yimiaoId", "疫苗编号", "false"}, {"yimiaoName", "疫苗名称", "true"}, {"source", "国产/出口", "false"},
            {"tongguandanNo", "进口通关单编号", "false"}, {"quantity", "数量", "false"}, {"yimiaoGuige", "规格", "false"}, {"yimiaoJixing", "剂型", "false"},
            {"yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"youxiaodate", "有效期", "false"}, {"unitId", "单位", "false"}, {"barcode", "条形码", "false"},
            {"piqianfaNo", "批签发合格证编号", "false"}, {"yimiaoPizhunwenhao", "批准文号", "false"}, {"price", "单价", "false"}, {"totalPrice", "合价", "false"},
            {"jingbanren", "经办人", "false"}, {"gongyingdanwei", "供应单位", "false"}, {"duifangjingbanren", "对方经办人", "false"}});
        editTable.registerPopup(2, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyanshouyimiao";
            }

            public String getConditionSQL() {
                wait = true;
                chooseYimiao();
                while (wait) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(YiMiaoRuKu2JDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";
                sql = " shenqingdan_id like \"YMSG%\" and is_completed = 1 and status = 2"
                        + " and shenqingdan_id NOT IN( SELECT shenqingdan_id FROM (SELECT shenqingdan_id,COUNT(*) AS num FROM yimiaoshenqingdan WHERE STATUS=1 GROUP BY shenqingdan_id) AS a WHERE a.num > 0)";
                if (newColumnObj instanceof String && !newColumnObj.toString().trim().equals("")) {
                    sql += (" and yimiao_id in ( select yimiao_id  from yimiao where yimiao_name like \"%" + newColumnObj.toString() + "%\""
                            + " or zujima like \"%" + newColumnObj.toString().toLowerCase() + "%\")");
                }
                if (sqid != null) {
                    sql += " and shenqingdan_id = \"" + sqid + "\" ";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"shenqingdan.shenqingdanId", "源单单号"}, {"shenqingdan.shenqingdanDate", "申报日期"}, {"yimiaoAll.yimiaoName", "疫苗名称"},
                {"yimiaoAll.yimiaoJixing", "剂型"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    Object yimiaomap = bindedMap.get("yimiaoAll");
                    HashMap yimiaoAll = (HashMap) yimiaomap;
                    Object yimiaoshenqingdanmap = bindedMap.get("yimiaoshenqingtb");
                    HashMap yimiaoshenqingdan = (HashMap) yimiaoshenqingdanmap;
                    Object yimiaodengjimap = bindedMap.get("yimiaodengji");
                    HashMap yimiaodengji = (HashMap) yimiaodengjimap;
                    Object gongyingdanweimap = bindedMap.get("gongyingdanwei");
                    HashMap gongyingdanwei = (HashMap) gongyingdanweimap;

                    Object yimiaoId = yimiaoAll.get("yimiaoId");
                    Object yimiaoName = yimiaoAll.get("yimiaoName");
                    Object yimiaoGuige = yimiaoAll.get("yimiaoGuige");
                    Object yimiaoJixing = yimiaoAll.get("yimiaoJixing");
                    Object shengchanqiye = yimiaoAll.get("yimiaoShengchanqiye");
                    Object unit = yimiaoAll.get("unitId");
                    Object barcode = yimiaodengji.get("barcode");
                    Object pihao;
                    try {
                        pihao = yimiaodengji.get("pihao");
                    } catch (Exception e) {
                        pihao = null;
                    }
                    Object source;
                    try {
                        source = yimiaodengji.get("source");
                    } catch (Exception e) {
                        source = null;
                    }
                    Object youxiaoqi;
                    try {
                        youxiaoqi = yimiaodengji.get("youxiaoqi").toString().subSequence(0, 10);
                    } catch (Exception e) {
                        youxiaoqi = "";
                    }
                    Object piqianfahegezhenno;
                    try {
                        piqianfahegezhenno = yimiaodengji.get("piqianfahegezhenno");
                    } catch (Exception e) {
                        piqianfahegezhenno = null;
                    }
                    Object yimiaoPizhunwenhao = yimiaoAll.get("yimiaoPizhunwenhao");
                    Object tongguandanno;
                    try {
                        tongguandanno = yimiaodengji.get("tongguandanno");
                    } catch (Exception e) {
                        tongguandanno = null;
                    }
                    Object quantity = yimiaoshenqingdan.get("quantity");
                    Object gongyingdanweiName;
                    try {
                        gongyingdanweiName = gongyingdanwei.get("supplierName");
                    } catch (Exception e) {
                        gongyingdanweiName = "";
                    }
                    Object userName;
                    try {
                        userName = gongyingdanwei.get("supplierConstactperson");
                    } catch (Exception e) {
                        userName = "";
                    }
                    Object buyprice = yimiaoshenqingdan.get("buyprice");
                    Object totalprice = yimiaoshenqingdan.get("totalprice");

                    Object supplierId = gongyingdanwei.get("supplierId");
                    Object xiangdanId = yimiaoshenqingdan.get("xiangdanId");
                    Object shenqingdanId = yimiaoshenqingdan.get("shenqingdanId");

                    supplierIdmap.put(xiangdanId, supplierId);
                    xiangdanIdmap.put(xiangdanId, xiangdanId);
                    shenqingdanIdmap.put(xiangdanId, shenqingdanId);

                    for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
                        BaseTable yimiaotable = ((BaseTable) jTableyimiao);
                        if (yimiaotable.getValue(i, "barcode").toString().trim().equals("" + yimiaodengji.get("barcode"))) {
                            AssetMessage.INFO("已经添加了该疫苗！", YiMiaoRuKu2JDialog.this);
                            return;
                        }
                    }

                    editTable.insertValue(0, xiangdanId);
                    editTable.insertValue(1, yimiaoId);
                    editTable.insertValue(2, yimiaoName);
                    editTable.insertValue(3, source);
                    editTable.insertValue(4, tongguandanno);
                    editTable.insertValue(5, quantity);
                    editTable.insertValue(6, yimiaoGuige);
                    editTable.insertValue(7, yimiaoJixing);
                    editTable.insertValue(8, shengchanqiye);
                    editTable.insertValue(9, pihao);
                    editTable.insertValue(10, youxiaoqi);
                    editTable.insertValue(11, unit);
                    editTable.insertValue(12, barcode);
                    editTable.insertValue(13, piqianfahegezhenno);
                    editTable.insertValue(14, yimiaoPizhunwenhao);
                    editTable.insertValue(15, buyprice);
                    editTable.insertValue(16, totalprice);
                    editTable.insertValue(17, AssetClientApp.getSessionMap().getUsertb().getUserName());
                    editTable.insertValue(18, gongyingdanweiName);
                    editTable.insertValue(19, userName);

                }

            }
        });

        ((ScanButton) jButton18).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_SCAN;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "addyanshouyimiao";
            }

            public String getConditionSQL() {
                String sql = "";
                sql = " shenqingdan_id like \"YMSG%\" and is_completed = 1 and status = 2"
                        + " and shenqingdan_id NOT IN( SELECT shenqingdan_id FROM (SELECT shenqingdan_id,COUNT(*) AS num FROM yimiaoshenqingdan WHERE STATUS=1 GROUP BY shenqingdan_id) AS a WHERE a.num > 0)";
                sql += (" and yimiao_id in ( select yimiao_id  from yimiaodengji where barcode = \"#\" )");
                return sql;
            }

            public String[][] displayColumns() {
                return null;
            }

            public void setBindedMap(HashMap bindedMap) {
                updateTable(bindedMap, editTable, true);
            }
        });
    }

    private void updateTable(HashMap bindedMap, BaseTable.SingleEditRowTable editTable, boolean insertBlankRow) {
        if (bindedMap != null) {
            Object yimiaomap = bindedMap.get("yimiaoAll");
            HashMap yimiaoAll = (HashMap) yimiaomap;
            Object yimiaoshenqingdanmap = bindedMap.get("yimiaoshenqingtb");
            HashMap yimiaoshenqingdan = (HashMap) yimiaoshenqingdanmap;
            Object yimiaodengjimap = bindedMap.get("yimiaodengji");
            HashMap yimiaodengji = (HashMap) yimiaodengjimap;
            Object gongyingdanweimap = bindedMap.get("gongyingdanwei");
            HashMap gongyingdanwei = (HashMap) gongyingdanweimap;

            Object yimiaoId = yimiaoAll.get("yimiaoId");
            Object yimiaoName = yimiaoAll.get("yimiaoName");
            Object yimiaoGuige = yimiaoAll.get("yimiaoGuige");
            Object yimiaoJixing = yimiaoAll.get("yimiaoJixing");
            Object shengchanqiye = yimiaoAll.get("yimiaoShengchanqiye");
            Object unit = yimiaoAll.get("unitId");
            Object barcode = yimiaodengji.get("barcode");
            Object pihao;
            try {
                pihao = yimiaodengji.get("pihao");
            } catch (Exception e) {
                pihao = null;
            }
            Object source;
            try {
                source = yimiaodengji.get("source");
            } catch (Exception e) {
                source = null;
            }
            Object youxiaoqi;
            try {
                youxiaoqi = yimiaodengji.get("youxiaoqi").toString().subSequence(0, 10);
            } catch (Exception e) {
                youxiaoqi = "";
            }
            Object piqianfahegezhenno;
            try {
                piqianfahegezhenno = yimiaodengji.get("piqianfahegezhenno");
            } catch (Exception e) {
                piqianfahegezhenno = null;
            }
            Object yimiaoPizhunwenhao = yimiaoAll.get("yimiaoPizhunwenhao");
            Object tongguandanno;
            try {
                tongguandanno = yimiaodengji.get("tongguandanno");
            } catch (Exception e) {
                tongguandanno = null;
            }
            Object quantity = yimiaoshenqingdan.get("quantity");
            Object gongyingdanweiName;
            try {
                gongyingdanweiName = gongyingdanwei.get("supplierName");
            } catch (Exception e) {
                gongyingdanweiName = "";
            }
            Object userName;
            try {
                userName = gongyingdanwei.get("supplierConstactperson");
            } catch (Exception e) {
                userName = "";
            }
            Object buyprice = yimiaoshenqingdan.get("buyprice");
            Object totalprice = yimiaoshenqingdan.get("totalprice");

            Object supplierId = gongyingdanwei.get("supplierId");
            Object xiangdanId = yimiaoshenqingdan.get("xiangdanId");
            Object shenqingdanId = yimiaoshenqingdan.get("shenqingdanId");

            supplierIdmap.put(xiangdanId, supplierId);
            xiangdanIdmap.put(xiangdanId, xiangdanId);
            shenqingdanIdmap.put(xiangdanId, shenqingdanId);

            for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
                BaseTable yimiaotable = ((BaseTable) jTableyimiao);
                if (yimiaotable.getValue(i, "barcode").toString().trim().equals("" + yimiaodengji.get("barcode"))) {
                    AssetMessage.INFO("已经添加了该疫苗！", YiMiaoRuKu2JDialog.this);
                    return;
                }
            }

            editTable.insertValue(0, xiangdanId);
            editTable.insertValue(1, yimiaoId);
            editTable.insertValue(2, yimiaoName);
            editTable.insertValue(3, source);
            editTable.insertValue(4, tongguandanno);
            editTable.insertValue(5, quantity);
            editTable.insertValue(6, yimiaoGuige);
            editTable.insertValue(7, yimiaoJixing);
            editTable.insertValue(8, shengchanqiye);
            editTable.insertValue(9, pihao);
            editTable.insertValue(10, youxiaoqi);
            editTable.insertValue(11, unit);
            editTable.insertValue(12, barcode);
            editTable.insertValue(13, piqianfahegezhenno);
            editTable.insertValue(14, yimiaoPizhunwenhao);
            editTable.insertValue(15, buyprice);
            editTable.insertValue(16, totalprice);
            editTable.insertValue(17, AssetClientApp.getSessionMap().getUsertb().getUserName());
            editTable.insertValue(18, gongyingdanweiName);
            editTable.insertValue(19, userName);

            if (insertBlankRow) {
                editTable.addNewRow();
            }

        }
// 
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
        jButton18 = new ScanButton();
        jButton19 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldzhidanDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldjingbanren = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);
        jTextFielddanjuNo = new BaseTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextFieldkufang = new BaseTextField();
        jLabel4 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoRuKu2JDialog.class);
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

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoRuKu2JDialog.class, this);
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

        jButton16.setAction(actionMap.get("print")); // NOI18N
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

        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setText(resourceMap.getString("jTextFieldjingbanren.text")); // NOI18N
        jTextFieldjingbanren.setName("jTextFieldjingbanren"); // NOI18N

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

        jTextFieldkufang.setName("jTextFieldkufang"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFielddanjuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldkufang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldkufang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(YiMiaoRuKu2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoRuKu2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoRuKu2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoRuKu2JDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                YiMiaoRuKu2JDialog dialog = new YiMiaoRuKu2JDialog();
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
        if (jTableyimiao.getRowCount() < 1) {
            AssetMessage.ERRORSYS("请选择要取消入库的疫苗！", this);
            return null;
        }
        List<YimiaoshenqingliebiaoEntity> lst = new ArrayList<YimiaoshenqingliebiaoEntity>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            if (jTableyimiao.getValueAt(i, 2) == null | jTableyimiao.getValueAt(i, 2) == "") {
                continue;
            }
            YimiaoshenqingliebiaoEntity lb = new YimiaoshenqingliebiaoEntity();
            Yimiaoshenqingdantb yimiaoshenqingdan = new Yimiaoshenqingdantb();
            yimiaoshenqingdan.setShenqingdanId(shenqingdanIdmap.get(jTableyimiao.getValueAt(i, 0)).toString());
            yimiaoshenqingdan.setXiangdanId(Integer.parseInt(xiangdanIdmap.get(jTableyimiao.getValueAt(i, 0)).toString()));
            lb.setYimiaoshenqingdan(yimiaoshenqingdan);
            String reason = "";
            while (reason.isEmpty()) {
                reason = AssetMessage.showInputDialog(null, "请输入取消入库疫苗【"
                        + jTableyimiao.getValueAt(i, 2) + "】的理由(必输)：");
                if (reason == null) {
                    return null;
                }
            }
            lb.getYimiaoshenqingdan().setReason("【入库】" + reason);
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
        YimiaochurukuEntity yimiaorukuEntity = new YimiaochurukuEntity();

        if (jTextFieldkufang.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请选择入库库房!");
            return null;
        }

        churukudan.setChurukuId(jTextFielddanjuNo.getText());
        churukudan.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
        churukudan.setKufang(jTextFieldkufang.getText());
        churukudan.setZhidanren(AssetClientApp.getSessionMap().getUsertb().getUserId());

        yimiaorukuEntity.setChurukutb(churukudan);

        List<Churukudanyimiaoliebiaotb> list = new ArrayList<Churukudanyimiaoliebiaotb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            if (yimiaotable.getValue(i, "yimiaoName") == null | yimiaotable.getValue(i, "yimiaoName") == "") {
                continue;
            }
            Churukudanyimiaoliebiaotb yimiaoliebiao = new Churukudanyimiaoliebiaotb();
//            if (yimiaotable.getValue(i, "yimiaoName").toString().trim().equals("")) {
//                AssetMessage.ERRORSYS("请输入入库疫苗!");
//            }
            yimiaoliebiao.setChurukuId(jTextFielddanjuNo.getText());
            yimiaoliebiao.setZhidandate(dateformate.parse(jTextFieldzhidanDate.getText()));
            yimiaoliebiao.setYimiaoId(Integer.parseInt(yimiaotable.getValue(i, "yimiaoId").toString()));
            yimiaoliebiao.setPihao((String) yimiaotable.getValue(i, "pihao"));
            yimiaoliebiao.setPiqianfahegeno((String) yimiaotable.getValue(i, "piqianfaNo"));
            yimiaoliebiao.setRukuQuantity((Integer) yimiaotable.getValue(i, "quantity"));
            yimiaoliebiao.setSource((String) ("" + yimiaotable.getValue(i, "source")));
            yimiaoliebiao.setTongguandanno((String) ("" + yimiaotable.getValue(i, "tongguandanNo")));
            yimiaoliebiao.setBarcode((String) ("" + yimiaotable.getValue(i, "barcode")));
            yimiaoliebiao.setPrice(Float.parseFloat((String) ("" + yimiaotable.getValue(i, "price"))));
            yimiaoliebiao.setTotalprice(Float.parseFloat((String) ("" + yimiaoliebiao.getRukuQuantity() * yimiaoliebiao.getPrice())));
            if (yimiaotable.getValue(i, "youxiaodate").toString().trim().equals("")) {
                yimiaoliebiao.setYouxiaoqi(null);
            } else {
                yimiaoliebiao.setYouxiaoqi(riqiformate.parse((String) ("" + yimiaotable.getValue(i, "youxiaodate"))));
            }
            yimiaoliebiao.setWanglaidanweiId(Integer.parseInt(supplierIdmap.get(jTableyimiao.getValueAt(i, 0)).toString()));
            yimiaoliebiao.setXiangdanId(Integer.parseInt(xiangdanIdmap.get(jTableyimiao.getValueAt(i, 0)).toString()));
            list.add(yimiaoliebiao);
        }

        yimiaorukuEntity.setResult(list);

        String serviceId = "yimiaoruku/add";
        return new CommUpdateTask<YimiaochurukuEntity>(yimiaorukuEntity, serviceId) {

            @Override
            public void responseResult(ComResponse<YimiaochurukuEntity> response) {
                if (response.getResponseStatus() == ComResponse.STATUS_OK) {
                    JOptionPane.showMessageDialog(null, "提交成功！");
                    close();
                    JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
                    YiMiaoRuKu2JDialog ymrk2 = new YiMiaoRuKu2JDialog();
                    ymrk2.setLocationRelativeTo(mainFrame);
                    AssetClientApp.getApplication().show(ymrk2);
                } else {
                    AssetMessage.ERROR(response.getErrorMessage(), YiMiaoRuKu2JDialog.this);
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
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", jTextFielddanjuNo.getText()},
                    {"制单日期", jTextFieldzhidanDate.getText()},
                    {"经办人", jTextFieldjingbanren.getText()},
                    {"仓库", jTextFieldkufang.getText()},
                    {"备注", jTextArea1.getText(), "single"}},
                    jTableyimiao,
                    new String[][]{{"", ""},});
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    @Action
    public void exit() {
        if (isNew) {
            close();
            return;
        }
        String sql = " shenqingdan_id like \"YMSG%\" and is_completed = 1 and status = 2"
                + " and shenqingdan_id NOT IN( SELECT shenqingdan_id FROM (SELECT shenqingdan_id,COUNT(*) AS num FROM yimiaoshenqingdan WHERE STATUS=1 GROUP BY shenqingdan_id) AS a WHERE a.num > 0)";
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
                    string.append("单据").append(yimiao.getYimiaoshenqingdan().getShenqingdanId()).append("有未登记项【")
                            .append(yimiao.getYimiao().getYimiaoName()).append("】\n");
                }
                string.append("是否继续入库？选“否”或“取消”会要求输入原因，并不再入库以上所有疫苗");
                int result = AssetMessage.showConfirmDialog(null, string.toString(), "确认", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    return;
                }
                for (YimiaoshenqingliebiaoEntity lb : list) {
                    String reason = null;
                    while (reason == null || reason.isEmpty()) {
                        reason = AssetMessage.showInputDialog(null, "请输入取消入库疫苗【"
                                + lb.getYimiao().getYimiaoName() + "】的理由(必输)：");
                        if (reason == null) {
                            return;
                        }
                    }
                    lb.getYimiaoshenqingdan().setReason("【入库】" + reason);
                }
                new YiMiaoRuKu2JDialog.Cancel(list).execute();
            }
            close();
        }

    }

    public void chooseYimiao() {
        String sql = "shenqingdan_id like \"YMSG%\" and is_completed = 1 and status = 2";
        new ChooseTask(sql).execute();
    }

    private class ChooseTask extends WeidengjiyimiaoTask {

        public ChooseTask(String sql) {
            super(sql, "");
        }

        @Override
        public void responseResult(CommFindEntity<YimiaoshenqingliebiaoEntity> response) {

            logger.debug("get current size:" + response.getResult().size());
            list = response.getResult();
            sqid = null;
            wait = false;
            if (list.size() > 0) {
                sqid = list.get(0).getYimiaoshenqingdan().getShenqingdanId();
            }
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
            YiMiaoRuKu2JDialog yimiaoruku = new YiMiaoRuKu2JDialog();
            yimiaoruku.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaoruku);
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFielddanjuNo;
    private javax.swing.JTextField jTextFieldjingbanren;
    private javax.swing.JTextField jTextFieldkufang;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
