package cn.ox85.ui.components.bbr;

import cn.ox85.models.report.DayData;
import cn.ox85.models.report.MonthData;
import cn.ox85.models.report.YearData;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BBRMap;
import org.apache.ibatis.session.SqlSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategorySeriesLabelGenerator;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.Year;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author alec zhang
 */
public class BBRReportDialog extends JDialog {
    private static final GradientPaint DEFAULT_GRADIENT_PAINT =
            new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, 64));
    private static final CategorySeriesLabelGenerator DEFAULT_LABEL_GENERATOR =
            new StandardCategorySeriesLabelGenerator("Tooltip: {0}");
    private static final String SERIES = "series";

    public BBRReportDialog(Frame owner) {
        super(owner);
        JPanel reportPanel = buildReportPanel();
        setContentPane(reportPanel);
        setModal(true);
        setSize(new Dimension(500, 400));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private JPanel buildReportPanel() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 0, 1);
        Date start = calendar.getTime();
        Date end = Calendar.getInstance().getTime();
        JFreeChart chart = createChart("BBRReport", "Date", "BorrowTimes", createMonthDataset(start, end));
        return new ChartPanel(chart);
    }

    protected CategoryDataset createDayDataset(Date start, Date end) {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<DayData> dayDataList;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            BBRMap bbrMap = session.getMapper(BBRMap.class);
            dayDataList = bbrMap.selectBBRDayReport(start, end);
        } finally {
            session.close();
        }
        if (dayDataList != null) {
            for (DayData dayData : dayDataList) {
                dataset.addValue(dayData.getDnum(), SERIES, dayData.getDdate());
            }
        }
        return dataset;
    }

    protected CategoryDataset createMonthDataset(Date start, Date end) {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<MonthData> monthDataList;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            BBRMap bbrMap = session.getMapper(BBRMap.class);
            monthDataList = bbrMap.selectBBRMonthReport(start, end);
        } finally {
            session.close();
        }
        if (monthDataList != null) {
            for (MonthData monthData : monthDataList) {
                dataset.addValue(monthData.getMnum(), SERIES, new Month(monthData.getDmonth(), monthData.getDyear()));
            }
        }
        return dataset;
    }

    protected CategoryDataset createYearDataset(Date start, Date end) {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<YearData> yearDataList;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            BBRMap bbrMap = session.getMapper(BBRMap.class);
            yearDataList = bbrMap.selectBBRYearReport(start, end);
        } finally {
            session.close();
        }
        if (yearDataList != null) {
            for (YearData yearData : yearDataList) {
                dataset.addValue(yearData.getYnum(), SERIES, new Year(yearData.getDyear()));
            }
        }
        return dataset;
    }

    private static JFreeChart createChart(String title, String categoryLabel, String valueLabel,
                                          CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                title, categoryLabel, valueLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setRangeCrosshairPaint(Color.blue);
        NumberAxis localNumberAxis = (NumberAxis) plot.getRangeAxis();
        localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setSeriesPaint(0, DEFAULT_GRADIENT_PAINT);
        renderer.setLegendItemToolTipGenerator(DEFAULT_LABEL_GENERATOR);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(0.5235987755982988D));
        return chart;
    }

}