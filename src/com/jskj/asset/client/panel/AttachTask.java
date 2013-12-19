/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.bean.Attach;
import com.jskj.asset.client.layout.BaseTask;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author woderchen
 */
public class AttachTask extends BaseTask {

    private static Logger logger = Logger.getLogger(AttachTask.class);
    private Attach attach;

    public AttachTask(Attach attach) {
        this.attach = attach;
    }

    @Override
    public Object doBackgrounp() {

        Object object = null;

        String table = attach.getQueryTablesString();
        String column = attach.getColumnName();
        int dbKey = attach.getDbKey();
        int rowNum = attach.getRowNum();
        String sql = "select " + column + " from " + table;

        logger.info("start fetch special data...");
        //获取链接
        Connection connection = null;

        try {
//            connection = ConnectFactory.getConnection(dbKey);
//            //开始查询
//            PreparedStatement ps = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//            ResultSet rs = ps.executeQuery();
//
//            int row = 0;
//
//            while (rs.next()) {
//                row++;
//                if (row == rowNum) {
//                    object = rs.getObject(column);
//                    break;
//                }
//            }
//
//            logger.debug("got data:"+object);
//
//            rs.close();
//            ps.close();

        } catch (Exception e) {
            return e;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    return ex;
                }
            }
        }
        return object;
    }

    @Override
    public void onSucceeded(Object object) {
    }
}
