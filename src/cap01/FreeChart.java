//package cap01;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PiePlot;
//import org.jfree.data.general.DefaultPieDataset;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class FreeChart extends JFrame {
//
//    public FreeChart(String title) {
//        super(title);
//
//        DefaultPieDataset datos = creaDataset();
//
//        JFreeChart chart = createChart(datos);
//
//        ChartPanel chartPanel = new ChartPanel(chart);
//        //chartPanel.setPreferredSize(new Dimension(500, 300));
//        this.setSize(600, 400);
//        this.setLocationRelativeTo( null );
//
//        setContentPane(chartPanel);
//    }
//
//    private DefaultPieDataset creaDataset() {
//        DefaultPieDataset dataset = new DefaultPieDataset();
//        dataset.setValue("Italia", 500000);
//        dataset.setValue("Alemania", 350000);
//        dataset.setValue("Inglaterra", 200000);
//        dataset.setValue("Francia", 150000);
//        dataset.setValue("Japón", 120000);
//        dataset.setValue("Rusia", 300000);
//        return dataset;
//    }
//
//    private JFreeChart createChart(DefaultPieDataset dataset) {
//        JFreeChart chart = ChartFactory.createPieChart(
//                "Fallecidos en la Segunda Guerra Mundial",
//                dataset,
//                true,
//                true,
//                false);
//
//        PiePlot plot = (PiePlot) chart.getPlot();
//        return chart;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            FreeChart grafico = new FreeChart("WWII");
//            grafico.setSize(600, 400);
//            grafico.setLocationRelativeTo(null);
//            grafico.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            grafico.setVisible(true);
//        });
//    }
//}