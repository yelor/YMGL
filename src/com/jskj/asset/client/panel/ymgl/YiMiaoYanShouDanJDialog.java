/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel.ymgl;

import com.jskj.asset.client.AssetClientApp;
import com.jskj.asset.client.bean.entity.Yimiaoshenqingdantb;
import com.jskj.asset.client.bean.entity.YimiaoshenqingliebiaoEntity;
import com.jskj.asset.client.bean.entity.Yimiaoyanshou_detail_tb;
import com.jskj.asset.client.bean.entity.Yimiaoyanshou_detail_tbFindEntity;
import com.jskj.asset.client.bean.entity.Yimiaoyanshoutb;
import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BaseDialog;
import com.jskj.asset.client.layout.BaseTable;
import com.jskj.asset.client.layout.BaseTextField;
import com.jskj.asset.client.layout.IPopupBuilder;
import com.jskj.asset.client.layout.ws.CommFindEntity;
import com.jskj.asset.client.panel.ymgl.task.CancelYimiaoDengji;
import com.jskj.asset.client.panel.ymgl.task.WeidengjiyimiaoTask;
import static com.jskj.asset.client.panel.ymgl.task.WeidengjiyimiaoTask.logger;
import com.jskj.asset.client.panel.ymgl.task.Yimiaoyanshou_detailUpdateTask;
import com.jskj.asset.client.util.DanHao;
import com.jskj.asset.client.util.DateChooser;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.print.Doc;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author huiqi
 */
public class YiMiaoYanShouDanJDialog extends BaseDialog {

    private static final Logger logger = Logger.getLogger(YiMiaoYanShouDanJDialog.class);
    private Yimiaoyanshou_detail_tbFindEntity yimiaoyanshouEntity;
    private Yimiaoyanshou_detail_tb yimiaoyanshou_detail;
    private Yimiaoyanshoutb yimiaoyanshou;
    private boolean isNew;
    private SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat riqi = new SimpleDateFormat("yyyy-MM-dd");
    private List<YimiaoshenqingliebiaoEntity> list;
    private Map xiangdanIdmap;
    private Map shenqingdanIdmap;
    private Map piqianfaNomap;
    private boolean wait;
    private String sqid;

    /**
     * Creates new form yimiaoyanshouJDialog
     */
    public YiMiaoYanShouDanJDialog() {
        super();
        init();
        initComponents();
        yimiaoyanshou = new Yimiaoyanshoutb();
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

        xiangdanIdmap = new HashMap();
        shenqingdanIdmap = new HashMap();
        piqianfaNomap = new HashMap();
        jTextFieldYimiaoyanshouId.setText(DanHao.getDanHao("YMYS"));
        jTextFieldYimiaoyanshouId.setEditable(false);

        jTextFieldzhidanDate.setText(dateformate.format(new Date()).toString());
        jTextFieldStarttime.setText(dateformate.format(new Date()).toString());
        jTextFieldArrivetime.setText(dateformate.format(new Date()).toString());
        jTextFieldjingbanren.setText(AssetClientApp.getSessionMap().getUsertb().getUserName());
        jTextFielddepartment.setText(AssetClientApp.getSessionMap().getDepartment().getDepartmentName());

        //供应单位的popup
        ((BaseTextField) jTextFieldSupplierName).registerPopup(new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TEXT;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "supplier";
            }

            public String getConditionSQL() {
                String sql = "  supplier_type = 0 ";
                if (!jTextFieldSupplierName.getText().trim().equals("")) {
                    sql += " and  (supplier_name like \"%" + jTextFieldSupplierName.getText() + "%\"" + " or supplier_zujima like \"%" + jTextFieldSupplierName.getText().trim().toLowerCase() + "%\")";
                }
                return sql;
            }

            public String[][] displayColumns() {
                return new String[][]{{"supplierId", "供应单位编号"}, {"supplierName", "供应单位名称"}, {"supplierConstactperson", "联系人"}};
            }

            public void setBindedMap(HashMap bindedMap) {
                if (bindedMap != null) {
                    yimiaoyanshou.setSupplierId(Integer.parseInt((String) ("" + bindedMap.get("supplierId"))));
                    jTextFieldSupplierName.setText(bindedMap.get("supplierName") == null ? "" : bindedMap.get("supplierName").toString());
                    jTextFieldFamiaoperson.setText(bindedMap.get("supplierConstactperson") == null ? "" : bindedMap.get("supplierConstactperson").toString());
                }
            }
        });

        //疫苗表中的内容
        final BaseTable.SingleEditRowTable editTable = ((BaseTable) jTableyimiao).createSingleEditModel(new String[][]{
            {"xiangdanId", "详单编号"}, {"yimiaoId", "疫苗编号"}, {"yimiaoName", "疫苗名称", "true"}, {"yimiaoGuige", "规格", "false"},
            {"yimiaoJixing", "剂型", "false"}, {"yimiaoShengchanqiye", "生产企业", "false"}, {"pihao", "批号", "false"}, {"youxiaoqi", "有效期", "false"}, {"unitId", "单位", "false"}, {"price", "进价", "false"},
            {"quantity", "数量", "false"}, {"fuheyuan", "复核员", "true"}, {"fahuoyuan", "发货员", "true"}});

        editTable.registerPopup(2, new IPopupBuilder() {
            public int getType() {
                return IPopupBuilder.TYPE_POPUP_TABLE;
            }

            public String getWebServiceURI() {
                return Constants.HTTP + Constants.APPID + "adddengjiyimiao";
            }

            public String getConditionSQL() {
                wait = true;
                chooseYimiao();
                while (wait) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                int selectedColumn = jTableyimiao.getSelectedColumn();
                int selectedRow = jTableyimiao.getSelectedRow();
                Object newColumnObj = jTableyimiao.getValueAt(selectedRow, selectedColumn);
                String sql = "";

                sql = " (shenqingdan_id like \"YMLQ%\" OR shenqingdan_id like \"YMSG%\") and is_completed = 1 and status = 1"
                        + " and shenqingdan_id NOT IN( SELECT shenqingdan_id FROM (SELECT shenqingdan_id,COUNT(*) AS num FROM yimiaoshenqingdan WHERE STATUS=0 GROUP BY shenqingdan_id) AS a WHERE a.num > 0)";
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
                return new String[][]{{"yimiaoshenqingtb.xiangdanId", "详单ID"}, {"shenqingdan.shenqingdanId", "源单单号"}, {"shenqingdan.shenqingdanDate", "申报日期"}, {"yimiaoAll.yimiaoName", "疫苗名称"},
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
                    Object pihao;
                    try {
                        pihao = yimiaodengji.get("pihao");
                    } catch (Exception e) {
                        pihao = null;
                    }

                    Object piqianfaNo = yimiaodengji.get("piqianfahegezhenno");
                    Object youxiaoqi = yimiaodengji.get("youxiaoqi").toString().subSequence(0, 10);
                    Object unit = yimiaoAll.get("unitId");
                    Object quantity = yimiaoshenqingdan.get("quantity");
                    Object buyprice = yimiaoshenqingdan.get("buyprice");
                    Object xiangdanId = yimiaoshenqingdan.get("xiangdanId");
                    Object shenqingdanId = yimiaoshenqingdan.get("shenqingdanId");
                    
                    jTextFieldSupplierName.setText((String) gongyingdanwei.get("supplierName"));
                    jTextFieldFamiaoperson.setText(gongyingdanwei.get("supplierConstactperson")== null ? "" : (String) gongyingdanwei.get("supplierConstactperson"));

                    editTable.insertValue(0, xiangdanId);
                    editTable.insertValue(1, yimiaoId);
                    editTable.insertValue(2, yimiaoName);
                    editTable.insertValue(3, yimiaoGuige);
                    editTable.insertValue(4, yimiaoJixing);
                    editTable.insertValue(5, shengchanqiye);
                    editTable.insertValue(6, pihao);
                    editTable.insertValue(7, youxiaoqi);
                    editTable.insertValue(8, unit);
                    editTable.insertValue(9, buyprice);
                    editTable.insertValue(10, quantity);

                    xiangdanIdmap.put(xiangdanId, xiangdanId);
                    shenqingdanIdmap.put(xiangdanId, shenqingdanId);
                    piqianfaNomap.put(xiangdanId, piqianfaNo);
                }

            }
        });

//        jTableyimiao.getModel().addTableModelListener(new TableModelListener(){
//            @Override
//            public void tableChanged(TableModelEvent e) {
//
//              if(jTextFieldSupplierName.getText().trim().equals("")){
//                  String danweiSql="and supplier_id in Selecte where shenqingdan";
//              }
//            }
//
//        });

        jTextFieldStarttime.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date begin = df.parse(jTextFieldArrivetime.getText());
                    Date end = df.parse(jTextFieldStarttime.getText());
                    long l = begin.getTime() - end.getTime();
                    long day = l / (24 * 60 * 60 * 1000);
                    long hour = (l / (60 * 60 * 1000) - day * 24);
                    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                    jTextFieldTotaltime.setText("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date begin = df.parse(jTextFieldArrivetime.getText());
                    Date end = df.parse(jTextFieldStarttime.getText());
                    long l = begin.getTime() - end.getTime();
                    long day = l / (24 * 60 * 60 * 1000);
                    long hour = (l / (60 * 60 * 1000) - day * 24);
                    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                    jTextFieldTotaltime.setText("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date begin = df.parse(jTextFieldArrivetime.getText());
                    Date end = df.parse(jTextFieldStarttime.getText());
                    long l = begin.getTime() - end.getTime();
                    long day = l / (24 * 60 * 60 * 1000);
                    long hour = (l / (60 * 60 * 1000) - day * 24);
                    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                    jTextFieldTotaltime.setText("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        jTextFieldArrivetime.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date begin = df.parse(jTextFieldArrivetime.getText());
                    Date end = df.parse(jTextFieldStarttime.getText());
                    long l = begin.getTime() - end.getTime();
                    long day = l / (24 * 60 * 60 * 1000);
                    long hour = (l / (60 * 60 * 1000) - day * 24);
                    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                    jTextFieldTotaltime.setText("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date begin = df.parse("" + jTextFieldArrivetime.getText());
                    Date end = df.parse(jTextFieldStarttime.getText());
                    long l = begin.getTime() - end.getTime();
                    long day = l / (24 * 60 * 60 * 1000);
                    long hour = (l / (60 * 60 * 1000) - day * 24);
                    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                    jTextFieldTotaltime.setText("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date begin = df.parse(jTextFieldArrivetime.getText());
                    Date end = df.parse(jTextFieldStarttime.getText());
                    long l = begin.getTime() - end.getTime();
                    long day = l / (24 * 60 * 60 * 1000);
                    long hour = (l / (60 * 60 * 1000) - day * 24);
                    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                    jTextFieldTotaltime.setText("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

    }

    JTextField regTextField1;
    DateChooser dateChooser2;
    JTextField regTextField2;
    DateChooser dateChooser3;
    JTextField regTextField3;

    private void init() {
        regTextField1 = new JTextField();
        regTextField2 = new JTextField();
        regTextField3 = new JTextField();

        dateChooser2 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
        dateChooser2.register(regTextField2);

        dateChooser3 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
        dateChooser3.register(regTextField3);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldYimiaoyanshouId = new javax.swing.JTextField();
        jTextFieldzhidanDate = regTextField1;
        jTextFielddepartment = new javax.swing.JTextField();
        jTextFieldsongmiaoren = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextFieldFamiaoperson = new javax.swing.JTextField();
        jTextFieldstarttemp1 = new javax.swing.JTextField();
        jTextFieldArrivetemp1 = new javax.swing.JTextField();
        jTextFieldCarcondition = new javax.swing.JTextField();
        jTextFieldXingshiKM = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldEquipment = new javax.swing.JTextField();
        jTextFieldStoragetype = new javax.swing.JTextField();
        jTextFieldstarttemp2 = new javax.swing.JTextField();
        jTextFieldArrivetemp2 = new javax.swing.JTextField();
        jTextFieldBefmiles = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextFieldSupplierName = new BaseTextField();
        jTextFieldStarttime = regTextField2;
        jTextFieldTotaltime = new javax.swing.JTextField();
        jTextFieldArrivetime = regTextField3;
        jTextFieldBackmiles = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextFieldjingbanren = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableyimiao = new BaseTable(null);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane2.setViewportView(jTable1);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.setName("jTable2"); // NOI18N
        jScrollPane3.setViewportView(jTable2);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable3.setName("jTable3"); // NOI18N
        jScrollPane4.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(YiMiaoYanShouDanJDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setOpaque(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getActionMap(YiMiaoYanShouDanJDialog.class, this);
        jButton1.setAction(actionMap.get("submitForm")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setOpaque(false);
        jToolBar1.add(jButton1);

        jButton2.setAction(actionMap.get("buhege")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setName("jButton2"); // NOI18N
        jToolBar1.add(jButton2);

        jButton4.setAction(actionMap.get("print")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setOpaque(false);
        jToolBar1.add(jButton4);

        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setName("jButton3"); // NOI18N
        jToolBar1.add(jButton3);

        jButton5.setAction(actionMap.get("exit")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setOpaque(false);
        jToolBar1.add(jButton5);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldYimiaoyanshouId.setEditable(false);
        jTextFieldYimiaoyanshouId.setText(resourceMap.getString("jTextFieldYimiaoyanshouId.text")); // NOI18N
        jTextFieldYimiaoyanshouId.setName("jTextFieldYimiaoyanshouId"); // NOI18N

        jTextFieldzhidanDate.setEditable(false);
        jTextFieldzhidanDate.setText(resourceMap.getString("jTextFieldzhidanDate.text")); // NOI18N
        jTextFieldzhidanDate.setName("jTextFieldzhidanDate"); // NOI18N
        jTextFieldzhidanDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldzhidanDateActionPerformed(evt);
            }
        });

        jTextFielddepartment.setEditable(false);
        jTextFielddepartment.setText(resourceMap.getString("jTextFielddepartment.text")); // NOI18N
        jTextFielddepartment.setName("jTextFielddepartment"); // NOI18N

        jTextFieldsongmiaoren.setText(resourceMap.getString("jTextFieldsongmiaoren.text")); // NOI18N
        jTextFieldsongmiaoren.setName("jTextFieldsongmiaoren"); // NOI18N

        jTextField5.setText(resourceMap.getString("jTextField5.text")); // NOI18N
        jTextField5.setName("jTextField5"); // NOI18N

        jTextField6.setText(resourceMap.getString("jTextField6.text")); // NOI18N
        jTextField6.setName("jTextField6"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jTextField7.setText(resourceMap.getString("jTextField7.text")); // NOI18N
        jTextField7.setName("jTextField7"); // NOI18N

        jTextFieldFamiaoperson.setEditable(false);
        jTextFieldFamiaoperson.setText(resourceMap.getString("jTextFieldFamiaoperson.text")); // NOI18N
        jTextFieldFamiaoperson.setName("jTextFieldFamiaoperson"); // NOI18N

        jTextFieldstarttemp1.setText(resourceMap.getString("jTextFieldstarttemp1.text")); // NOI18N
        jTextFieldstarttemp1.setName("jTextFieldstarttemp1"); // NOI18N

        jTextFieldArrivetemp1.setText(resourceMap.getString("jTextFieldArrivetemp1.text")); // NOI18N
        jTextFieldArrivetemp1.setName("jTextFieldArrivetemp1"); // NOI18N

        jTextFieldCarcondition.setText(resourceMap.getString("jTextFieldCarcondition.text")); // NOI18N
        jTextFieldCarcondition.setName("jTextFieldCarcondition"); // NOI18N

        jTextFieldXingshiKM.setText(resourceMap.getString("jTextFieldXingshiKM.text")); // NOI18N
        jTextFieldXingshiKM.setName("jTextFieldXingshiKM"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        jTextFieldEquipment.setText(resourceMap.getString("jTextFieldEquipment.text")); // NOI18N
        jTextFieldEquipment.setName("jTextFieldEquipment"); // NOI18N

        jTextFieldStoragetype.setText(resourceMap.getString("jTextFieldStoragetype.text")); // NOI18N
        jTextFieldStoragetype.setName("jTextFieldStoragetype"); // NOI18N

        jTextFieldstarttemp2.setText(resourceMap.getString("jTextFieldstarttemp2.text")); // NOI18N
        jTextFieldstarttemp2.setName("jTextFieldstarttemp2"); // NOI18N

        jTextFieldArrivetemp2.setText(resourceMap.getString("jTextFieldArrivetemp2.text")); // NOI18N
        jTextFieldArrivetemp2.setName("jTextFieldArrivetemp2"); // NOI18N

        jTextFieldBefmiles.setText(resourceMap.getString("jTextFieldBefmiles.text")); // NOI18N
        jTextFieldBefmiles.setName("jTextFieldBefmiles"); // NOI18N

        jTextField18.setEditable(false);
        jTextField18.setText(resourceMap.getString("jTextField18.text")); // NOI18N
        jTextField18.setName("jTextField18"); // NOI18N

        jTextFieldSupplierName.setEditable(false);
        jTextFieldSupplierName.setText(resourceMap.getString("jTextFieldSupplierName.text")); // NOI18N
        jTextFieldSupplierName.setName("jTextFieldSupplierName"); // NOI18N

        jTextFieldStarttime.setText(resourceMap.getString("jTextFieldStarttime.text")); // NOI18N
        jTextFieldStarttime.setName("jTextFieldStarttime"); // NOI18N

        jTextFieldTotaltime.setText(resourceMap.getString("jTextFieldTotaltime.text")); // NOI18N
        jTextFieldTotaltime.setName("jTextFieldTotaltime"); // NOI18N

        jTextFieldArrivetime.setText(resourceMap.getString("jTextFieldArrivetime.text")); // NOI18N
        jTextFieldArrivetime.setName("jTextFieldArrivetime"); // NOI18N

        jTextFieldBackmiles.setText(resourceMap.getString("jTextFieldBackmiles.text")); // NOI18N
        jTextFieldBackmiles.setName("jTextFieldBackmiles"); // NOI18N

        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        jTextFieldjingbanren.setEditable(false);
        jTextFieldjingbanren.setText(resourceMap.getString("jTextFieldjingbanren.text")); // NOI18N
        jTextFieldjingbanren.setName("jTextFieldjingbanren"); // NOI18N

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setRows(2);
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane6.setViewportView(jTextArea2);

        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setName("jLabel31"); // NOI18N

        jLabel32.setText(resourceMap.getString("jLabel32.text")); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(48, 48, 48)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel1)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel8)))
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldFamiaoperson, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jTextFieldYimiaoyanshouId, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                                .addComponent(jTextFieldsongmiaoren))
                                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jTextFieldstarttemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jTextFieldArrivetemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel27))
                                                .addComponent(jTextFieldCarcondition, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldXingshiKM)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel32)))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldBefmiles, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldArrivetemp2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldstarttemp2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30))
                                .addGap(50, 50, 50))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldStoragetype, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldEquipment, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 50, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel19))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextFieldSupplierName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldStarttime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldTotaltime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldArrivetime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jTextFieldBackmiles)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel31)))
                                    .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldYimiaoyanshouId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldzhidanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldsongmiaoren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldFamiaoperson, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldstarttemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldArrivetemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCarcondition, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldXingshiKM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFielddepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel13)
                            .addComponent(jTextFieldEquipment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldStarttime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(jTextFieldStoragetype, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTotaltime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel15)
                            .addComponent(jTextFieldstarttemp2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldArrivetime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(jTextFieldArrivetemp2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldBackmiles, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel17)
                            .addComponent(jTextFieldBefmiles, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldjingbanren, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel18)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap())
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTableyimiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "源单编号", "疫苗名称", "规格", "批号", "生产企业", "有效期至", "批签发合格证编号", "单位数量", "复核员", "发货员"
            }
        ));
        jTableyimiao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTableyimiao.setName("jTableyimiao"); // NOI18N
        jScrollPane5.setViewportView(jTableyimiao);
        if (jTableyimiao.getColumnModel().getColumnCount() > 0) {
            jTableyimiao.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title0")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title1")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title2")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title3")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title4")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title5")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title6")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title7")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title8")); // NOI18N
            jTableyimiao.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTableyimiao.columnModel.title9")); // NOI18N
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YiMiaoYanShouDanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                YiMiaoYanShouDanJDialog dialog = new YiMiaoYanShouDanJDialog();
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

    public void setUpdatedData(Yimiaoyanshou_detail_tb yimiaoyanshou_detail) {
        if (yimiaoyanshou_detail == null) {
            return;
        }
        this.yimiaoyanshou_detail = yimiaoyanshou_detail;
        jTextFieldYimiaoyanshouId.setText((yimiaoyanshou_detail.getYmysId()).toString());
        jTextFieldzhidanDate.setText(yimiaoyanshou.getYmysDate().toString());
    }

    //疫苗验收不合格情况
    @Action
    public Task buhege() {
        if (jTableyimiao.getRowCount() - 1 < 0) {
            AssetMessage.ERRORSYS("请选择要取消入库的疫苗！", this);
            return null;
        }
        List<YimiaoshenqingliebiaoEntity> lst = new ArrayList<YimiaoshenqingliebiaoEntity>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            YimiaoshenqingliebiaoEntity lb = new YimiaoshenqingliebiaoEntity();
            Yimiaoshenqingdantb yimiaoshenqingdan = new Yimiaoshenqingdantb();
            yimiaoshenqingdan.setShenqingdanId(shenqingdanIdmap.get(jTableyimiao.getValueAt(i, 0)).toString());
            yimiaoshenqingdan.setXiangdanId(Integer.parseInt(xiangdanIdmap.get(jTableyimiao.getValueAt(i, 0)).toString()));
            lb.setYimiaoshenqingdan(yimiaoshenqingdan);
            String reason = "";
            while (reason.isEmpty()) {
                reason = AssetMessage.showInputDialog(null, "请输入取消验收疫苗【"
                        + jTableyimiao.getValueAt(i, 1) + "】的理由(必输)：");
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
    public Task submitForm() throws ParseException {
        jTableyimiao.getCellEditor(jTableyimiao.getSelectedRow(),
                jTableyimiao.getSelectedColumn()).stopCellEditing();
        if (jTextFieldSupplierName.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入送苗单位!");
            return null;
        } else if (jTextFieldStarttime.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入起运时间!");
            return null;
        } else if (jTextFieldArrivetime.getText().trim().equals("")) {
            AssetMessage.ERRORSYS("请输入到达时间!");
            return null;
        }

        yimiaoyanshouEntity = new Yimiaoyanshou_detail_tbFindEntity();

        yimiaoyanshou.setYmysId(jTextFieldYimiaoyanshouId.getText());
        yimiaoyanshou.setDepartmentId(AssetClientApp.getSessionMap().getDepartment().getDepartmentId());
        yimiaoyanshou.setUserName(AssetClientApp.getSessionMap().getUsertb().getUserName());
        yimiaoyanshou.setYmysDate(dateformate.parse(jTextFieldzhidanDate.getText()));
        yimiaoyanshou.setYmysSendperson(jTextFieldsongmiaoren.getText() == null ? "" : jTextFieldsongmiaoren.getText());
        yimiaoyanshou.setYmysEquipment(jTextFieldEquipment.getText() == null ? "" : jTextFieldEquipment.getText());
        yimiaoyanshou.setYmysStoragetype(jTextFieldStoragetype.getText() == null ? "" : jTextFieldStoragetype.getText());
        yimiaoyanshou.setYmysStarttime(riqi.parse(jTextFieldStarttime.getText()));
        yimiaoyanshou.setYmysStrattemp1(Float.valueOf(jTextFieldstarttemp1.getText().trim().equals("") ? "0" : jTextFieldstarttemp1.getText()));
        yimiaoyanshou.setYmysStarttemp2(Float.valueOf(jTextFieldstarttemp2.getText().trim().equals("") ? "0" : jTextFieldstarttemp2.getText()));
        yimiaoyanshou.setYmysTotaltime(jTextFieldTotaltime.getText());
        yimiaoyanshou.setYmysArrivetemp1(Float.valueOf(jTextFieldArrivetemp1.getText().trim().equals("") ? "0" : jTextFieldArrivetemp1.getText()));
        yimiaoyanshou.setYmysArrivetemp2(Float.valueOf(jTextFieldArrivetemp2.getText().trim().equals("") ? "0" : jTextFieldArrivetemp2.getText()));
        yimiaoyanshou.setYmysArrivetime(riqi.parse(jTextFieldArrivetime.getText()));
        float BefMiles = Float.valueOf(jTextFieldBefmiles.getText().trim().equals("") ? "0" : jTextFieldBefmiles.getText());
        yimiaoyanshou.setYmysBefmiles(BefMiles);
        float BackMiles = Float.valueOf(jTextFieldBackmiles.getText().trim().equals("") ? "0" : jTextFieldBackmiles.getText());
        yimiaoyanshou.setYmysBackmiles(BackMiles);
        yimiaoyanshou.setYmysCarcondition(jTextFieldCarcondition.getText() == null ? "" : jTextFieldCarcondition.getText());
        yimiaoyanshou.setYmysKm(Float.valueOf(jTextFieldXingshiKM.getText().trim().equals("") ? "0" : jTextFieldXingshiKM.getText()));
        yimiaoyanshou.setYmysArriveaddr("广安疾控中心");

        List<Yimiaoyanshou_detail_tb> list = new ArrayList<Yimiaoyanshou_detail_tb>();
        for (int i = 0; i < jTableyimiao.getRowCount() - 1; i++) {
            yimiaoyanshou_detail = new Yimiaoyanshou_detail_tb();
            BaseTable yimiaotable = ((BaseTable) jTableyimiao);
            yimiaoyanshou_detail.setYimiaoId(Integer.parseInt(yimiaotable.getValue(i, "yimiaoId").toString()));
            yimiaoyanshou_detail.setYmysId(jTextFieldYimiaoyanshouId.getText());
            yimiaoyanshou_detail.setYouxiaodate(riqi.parse((String) ("" + yimiaotable.getValue(i, "youxiaoqi"))));
            yimiaoyanshou_detail.setPihao((String) ("" + yimiaotable.getValue(i, "pihao")));
            yimiaoyanshou_detail.setFahuoyuan((String) ("" + yimiaotable.getValue(i, "fahuoyuan")));
            yimiaoyanshou_detail.setFuheyuan((String) ("" + yimiaotable.getValue(i, "fuheyuan")));
            yimiaoyanshou_detail.setQuantity((Integer) yimiaotable.getValue(i, "quantity"));
            yimiaoyanshou_detail.setPrice(yimiaotable.getValue(i, "price") == null ? 0 : Float.parseFloat("" + yimiaotable.getValue(i, "price")));
            yimiaoyanshou_detail.setPiqianfahegeno(piqianfaNomap.get(jTableyimiao.getValueAt(i, 0)).toString());
            yimiaoyanshou_detail.setXiangdanId(Integer.parseInt(xiangdanIdmap.get(jTableyimiao.getValueAt(i, 0)).toString()));
            list.add(yimiaoyanshou_detail);
        }
        yimiaoyanshouEntity.setYimiaoyanshou(yimiaoyanshou);
        yimiaoyanshouEntity.setResult(list);
        return new SubmitFormTask(yimiaoyanshouEntity);
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
        String sql = " (shenqingdan_id like \"YMSG%\" or shenqingdan_id like \"YMLQ%\") and is_completed = 1 and status = 1"
                + " and shenqingdan_id NOT IN( SELECT shenqingdan_id FROM (SELECT shenqingdan_id,COUNT(*) AS num FROM yimiaoshenqingdan WHERE STATUS=0 GROUP BY shenqingdan_id) AS a WHERE a.num > 0)";
        new CloseTask(sql).execute();
    }

    public void close() {
        this.dispose();
    }

    private class CloseTask extends WeidengjiyimiaoTask {

        public CloseTask(String sql) {
            super(sql, "");
        }

        @Override
        public void responseResult(CommFindEntity<YimiaoshenqingliebiaoEntity> response) {

            logger.debug("get current size:" + response.getResult().size());
            list = response.getResult();
            if (list != null && list.size() > 0) {
                StringBuilder string = new StringBuilder();
                for (YimiaoshenqingliebiaoEntity yimiao : list) {
                    string.append("单据").append(yimiao.getYimiaoshenqingdan().getShenqingdanId()).append("有未验收项（")
                            .append(yimiao.getYimiao().getYimiaoName()).append(")\n");
                }
                string.append("是否继续验收？选“否”或“取消”会要求输入原因，并不再验收以上所有疫苗");
                int result = AssetMessage.showConfirmDialog(null, string.toString(), "确认", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    return;
                }
                for (YimiaoshenqingliebiaoEntity lb : list) {
                    String reason = "";
                    while (reason.isEmpty()) {
                        reason = AssetMessage.showInputDialog(null, "请输入取消验收疫苗【"
                                + lb.getYimiao().getYimiaoName() + "】的理由(必输)：");
                        if (reason == null) {
                            return;
                        }
                    }
                    lb.getYimiaoshenqingdan().setReason("【验收】" + reason);
                }
                new YiMiaoYanShouDanJDialog.Cancel(list).execute();
            }
            close();
        }

    }

    public void chooseYimiao() {
        String sql = "(shenqingdan_id like \"YMLQ%\" OR shenqingdan_id like \"YMSG%\") and is_completed = 1 and status = 1";
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
            YiMiaoYanShouDanJDialog yimiaoYanShouJDialog = new YiMiaoYanShouDanJDialog();
            yimiaoYanShouJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yimiaoYanShouJDialog);
        }

    }

    private class SubmitFormTask extends Yimiaoyanshou_detailUpdateTask {

        SubmitFormTask(Yimiaoyanshou_detail_tbFindEntity yimiaoyanshouEntity) {
            super(yimiaoyanshouEntity, Yimiaoyanshou_detailUpdateTask.ENTITY_SAVE);
        }

        @Override
        public void onSucceeded(Object result) {
            if (result instanceof Exception) {
                Exception e = (Exception) result;
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e);
                return;
            }
            AssetMessage.INFO("提交成功！", YiMiaoYanShouDanJDialog.this);
            close();
            JFrame mainFrame = AssetClientApp.getApplication().getMainFrame();
            YiMiaoYanShouDanJDialog yiMiaoYanShouJDialog = new YiMiaoYanShouDanJDialog();
            yiMiaoYanShouJDialog.setLocationRelativeTo(mainFrame);
            AssetClientApp.getApplication().show(yiMiaoYanShouJDialog);
        }
    }

    @Action
    public void print() {
        try {
            super.print(this.getTitle(),
                    new String[][]{{"单据编号", jTextFieldYimiaoyanshouId.getText()},
                    {"制单日期", jTextFieldzhidanDate.getText()},
                    {"部门", jTextFielddepartment.getText()},
                    {"送苗人", jTextFieldzhidanDate.getText()},
                    {"操作员", jTextFielddepartment.getText()},
                    {"库房", jTextFieldzhidanDate.getText()},
                    {"驾驶员", jTextFielddepartment.getText()},
                    {"运输设备名称", jTextFieldzhidanDate.getText()},
                    {"发苗单位", jTextFielddepartment.getText()},
                    {"发苗人", jTextFieldzhidanDate.getText()},
                    {"冷藏方式", jTextFielddepartment.getText()},
                    {"起运时间", jTextFieldzhidanDate.getText()},
                    {"起运疫苗存储温度", jTextFielddepartment.getText()},
                    {"起运疫苗环境温度", jTextFieldzhidanDate.getText()},
                    {"途中累计时间", jTextFielddepartment.getText()},
                    {"到达疫苗存储温度", jTextFieldzhidanDate.getText()},
                    {"到达疫苗环境温度", jTextFielddepartment.getText()},
                    {"到达时间", jTextFieldzhidanDate.getText()},
                    {"车辆及制冷情况", jTextFielddepartment.getText()},
                    {"出车前里程数", jTextFieldzhidanDate.getText()},
                    {"返回时里程数", jTextFielddepartment.getText()},
                    {"行驶公里", jTextFieldzhidanDate.getText()},
                    {"收苗单位", jTextFielddepartment.getText()},
                    {"收苗人", ""},
                    {"备注", ""}},
                    jTableyimiao,
                    new String[][]{{"", ""},
                    {"", ""}
                    });
        } catch (DRException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTableyimiao;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextFieldArrivetemp1;
    private javax.swing.JTextField jTextFieldArrivetemp2;
    private javax.swing.JTextField jTextFieldArrivetime;
    private javax.swing.JTextField jTextFieldBackmiles;
    private javax.swing.JTextField jTextFieldBefmiles;
    private javax.swing.JTextField jTextFieldCarcondition;
    private javax.swing.JTextField jTextFieldEquipment;
    private javax.swing.JTextField jTextFieldFamiaoperson;
    private javax.swing.JTextField jTextFieldStarttime;
    private javax.swing.JTextField jTextFieldStoragetype;
    private javax.swing.JTextField jTextFieldSupplierName;
    private javax.swing.JTextField jTextFieldTotaltime;
    private javax.swing.JTextField jTextFieldXingshiKM;
    private javax.swing.JTextField jTextFieldYimiaoyanshouId;
    private javax.swing.JTextField jTextFielddepartment;
    private javax.swing.JTextField jTextFieldjingbanren;
    private javax.swing.JTextField jTextFieldsongmiaoren;
    private javax.swing.JTextField jTextFieldstarttemp1;
    private javax.swing.JTextField jTextFieldstarttemp2;
    private javax.swing.JTextField jTextFieldzhidanDate;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
