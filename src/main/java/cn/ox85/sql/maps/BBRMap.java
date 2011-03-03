package cn.ox85.sql.maps;

import cn.ox85.models.BBR;
import cn.ox85.models.report.DayData;
import cn.ox85.models.report.MonthData;
import cn.ox85.models.report.YearData;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

/**
 * @author alec zhang
 */
public interface BBRMap {
    public int insertBBR(BBR bbr);

    public int updateBBR(BBR bbr);

    public BBR selectBookReturnByBookBarCode(String bookBarCode);

    public int selectBBRCount();

    public List<BBR> selectBBR(RowBounds bounds);

    public int selectBBRCountByReaderBarCode(String readerBarCode);

    public List<BBR> selectBBRByReaderBarCode(String readerBarCode, RowBounds bounds);

    public int selectBBRCountByBookBarCode(String bookBarCode);

    public List<BBR> selectBBRByBookBarCode(String bookBarCode, RowBounds bounds);

    public int selectBBRCountByReturned(boolean returned);

    public List<BBR> selectBBRByReturned(boolean returned, RowBounds bounds);

    public List<DayData> selectBBRDayReport(Date start, Date end);

    public List<MonthData> selectBBRMonthReport(Date start, Date end);

    public List<YearData> selectBBRYearReport(Date start, Date end);
}
