package evolution;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class DataLineChart extends ApplicationFrame {

    int[][] data;
    int cycles;

    public DataLineChart(String title, String chartTitle, int[][] data, int maxScore, int cycles) {
        super(title);
        this.data = data;
        this.cycles = cycles;
        Font font = Font.getFont("Arial");

//                ChartFactory.createXYLineChart(
//                chartTitle,
//                "Generation","Score",
//                createDataset(),
//                PlotOrientation.VERTICAL,
//                true,true,false);

        XYSeriesCollection scoreData = createScoreDataset();
        XYSeriesCollection geneData = createGeneDataset();

        XYPlot plot = new XYPlot();
        plot.setDataset(0, scoreData);
        plot.setDataset(1, geneData);
        plot.setBackgroundPaint(Color.BLACK);


        XYSplineRenderer sr1 = new XYSplineRenderer();
        XYSplineRenderer sr2 = new XYSplineRenderer();
        sr1.setSeriesShapesVisible(0, false);
        sr1.setSeriesShapesVisible(1, false);
        sr2.setSeriesShapesVisible(0, false);
        sr2.setSeriesShapesVisible(1, false);
        // WHY DOESN'T THIS WORK??
        sr2.setSeriesFillPaint(0, Color.GREEN);
        sr2.setSeriesFillPaint(1, Color.MAGENTA);
        plot.setRenderer(0, sr1);
        plot.setRenderer(1, sr2);


        NumberAxis range1 = new NumberAxis("Score");
        NumberAxis range2 = new NumberAxis("Genome Size");
        range1.setRange(0, maxScore);
        range1.setTickUnit(new NumberTickUnit(10));
        range2.setRange(0, 1.5 * data[4][1]);
        range2.setTickUnit(new NumberTickUnit(10));

        NumberAxis domain = new NumberAxis("Cycle");
        domain.setRange(0, cycles);
        domain.setTickUnit(new NumberTickUnit(cycles/10));

        plot.setRangeAxis(0, range1);
        plot.setRangeAxis(1, range2);
        plot.setDomainAxis(domain);

        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);

        JFreeChart lineChart = new JFreeChart(chartTitle, font, plot, true);
        lineChart.setBackgroundPaint(Color.GRAY);
        lineChart.getLegend().setBackgroundPaint(Color.GRAY);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        setContentPane(chartPanel);
    }

    private XYSeriesCollection createGeneDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Max Genome Size");
        XYSeries series2 = new XYSeries("Avg Genome Size");
        for (int i = 1; i <= data[0].length; i++) {
            if (i % (cycles / 100) == 0) {
                series1.add(i, data[2][i-1]);
                series2.add(i, data[3][i-1]);
            }
        }
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }

    private XYSeriesCollection createScoreDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Max Score");
        XYSeries series2 = new XYSeries("Avg Score");
        for (int i = 1; i <= data[0].length; i++) {
            if (i % (cycles / 100) == 0) {
                series1.add(i, data[0][i-1]);
                series2.add(i, data[1][i-1]);
            }
        }
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }
}
