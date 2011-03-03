package cn.ox85.sql.maps;

import cn.ox85.models.RI;
import cn.ox85.models.report.DayData;
import cn.ox85.models.report.MonthData;
import cn.ox85.models.report.YearData;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

/**
 * @author alec zhang
 */
public interface RIMap {
    public int insertRI(RI ri);

    public int selectRICount();

    public List<RI> selectRI(RowBounds bounds);

    public int selectRICountByReaderBarCode(String readerBarCode);

    public List<RI> selectRIByReaderBarCode(String readerBarCode, RowBounds bounds);

    public int selectRICountByReaderName(String readerName);

    public List<RI> selectRIByReaderName(String readerName, RowBounds bounds);

    public List<DayData> selectRIDayReport(Date start, Date end);

    public List<MonthData> selectRIMonthReport(Date start, Date end);

    public List<YearData> selectRIYearReport(Date start, Date end);
}
