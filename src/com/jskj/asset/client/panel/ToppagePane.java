/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ToppagePane.java
 *
 * Created on Jan 7, 2011, 1:27:35 PM
 */
package com.jskj.asset.client.panel;

import com.jskj.asset.client.constants.Constants;
import com.jskj.asset.client.layout.AssetMessage;
import com.jskj.asset.client.layout.BasePanel;
import com.jskj.asset.client.layout.BaseTask;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.PiePlot;
import de.erichseifert.gral.plots.colors.LinearGradient;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Random;
import org.apache.log4j.Logger;
import org.jdesktop.application.Task;

/**
 *
 * @author woderchen
 */
public class ToppagePane extends BasePanel {

    private static Logger logger = Logger.getLogger(ToppagePane.class);

    /**
     * Creates new form ToppagePane
     */
    public ToppagePane() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorTopPage = new javax.swing.JEditorPane();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.jskj.asset.client.AssetClientApp.class).getContext().getResourceMap(ToppagePane.class);
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jEditorTopPage.setEditable(false);
        jEditorTopPage.setBorder(null);
        jEditorTopPage.setName("jEditorTopPage"); // NOI18N
        jScrollPane1.setViewportView(jEditorTopPage);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public Task reload() {
        return new SendUrlTask();
    }

    @Override
    public Task reload(Object param) {
        return null;
    }

    private class SendUrlTask extends BaseTask {

        @Override
        public Object doBackgrounp() {
            try {
                jEditorTopPage.setPage(Constants.HTTP + "/AssetsSys/toppage.jsp");
            } catch (IOException e) {
                jEditorTopPage.setText(e.getMessage());
                AssetMessage.ERRORSYS(e.getMessage());
                logger.error(e.getMessage());
            }
            return null;
        }

        @Override
        public void onSucceeded(Object object) {
            //new GraphicTask().execute();
            //new MyTaskFindTask(messagePanel,true).execute();
        }

    }

    private class GraphicTask extends BaseTask {

        private final Color COLOR1 = new Color(55, 170, 200);
        /**
         * Second corporate color used as signal color
         */
        private final Color COLOR2 = new Color(200, 80, 75);
        private static final int SAMPLE_COUNT = 10;
        /**
         * Instance to generate random data values.
         */
        private Random random = new Random();

        @Override
        public Object doBackgrounp() {
            // Create data
            DataTable data = new DataTable(Integer.class);
            for (int i = 0; i < SAMPLE_COUNT; i++) {
                int val = random.nextInt(8) + 2;
                data.add((random.nextDouble() <= 0.15) ? -val : val);
            }

            // Create new pie plot
            PiePlot plot = new PiePlot(data);

            // Format plot
            plot.getTitle().setText("疫苗采购统计");
            // Change relative size of pie
            plot.setRadius(0.9);
            // Display a legend
            plot.setLegendVisible(true);
            // Add some margin to the plot area
            plot.setInsets(new Insets2D.Double(20.0, 40.0, 40.0, 40.0));

            PiePlot.PieSliceRenderer pointRenderer
                    = (PiePlot.PieSliceRenderer) plot.getPointRenderer(data);
            // Change relative size of inner region
            pointRenderer.setInnerRadius(0.4);
            // Change the width of gaps between segments
            pointRenderer.setGap(0.2);
            // Change the colors
            LinearGradient colors = new LinearGradient(COLOR1, COLOR2);
            pointRenderer.setColor(colors);
            // Show labels
            pointRenderer.setValueVisible(true);
            pointRenderer.setValueColor(Color.WHITE);
            pointRenderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD));
            return new InteractivePanel(plot);
        }

        @Override
        public void onSucceeded(Object object) {

            if (object instanceof InteractivePanel) {
//                InteractivePanel panelGrx = (InteractivePanel)object;
//                javax.swing.GroupLayout jPanelGphLayout = new javax.swing.GroupLayout(jPanelGph);
//                jPanelGph.setLayout(jPanelGphLayout);
//                jPanelGphLayout.setHorizontalGroup(
//                        jPanelGphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(jPanelGphLayout.createSequentialGroup()
//                                .addGap(2, 2, 2)
//                                .addComponent(panelGrx, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addContainerGap(2, Short.MAX_VALUE))
//                );
//                jPanelGphLayout.setVerticalGroup(
//                        jPanelGphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(jPanelGphLayout.createSequentialGroup()
//                                .addGap(42, 42, 42)
//                                .addComponent(panelGrx, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addContainerGap(118, Short.MAX_VALUE))
//                );
            }

        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorTopPage;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
