package cn.ox85.custom;

import cn.ox85.models.Book;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BookMap;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.ox85.custom.BookCol.*;

/**
 * 导入书目数据：
 * 该类只支持特定格式的Excel
 * Excel必须包含CLUMN_MAPPING中的对应的列
 *
 * @author alec zhang
 */
public class BookImporter {
    private static final Logger logger = LoggerFactory.getLogger(BookImporter.class);
    private static final Map<String, BookCol> COLUMN_MAPPING;

    static {
        Map<String, BookCol> column_map = new HashMap<String, BookCol>();
        column_map.put("条码号", BAR_CODE);
        column_map.put("ISBN", ISBN);
        column_map.put("著录价格", PRICE);
        column_map.put("题名", NAME);
        column_map.put("丛书名", SERIES);
        column_map.put("分辑号", VOLUME);
        column_map.put("出版者", PUBLISHER);
        column_map.put("第一责任者", AUTHOR);
        column_map.put("索取号", CALL_NUM);
        column_map.put("卷页", PAGE_NUM);
        column_map.put("验收日期", ENTER_DATE);
        column_map.put("内容附注", SUMMARY);
        column_map.put("一般附注", REMARKS);
        COLUMN_MAPPING = Collections.unmodifiableMap(column_map);
    }

    public static void importExcel(String excel)
            throws BiffException, IOException, ParseException, CloneNotSupportedException {
        Workbook workbook = Workbook.getWorkbook(new File(excel));
        Sheet sheet = workbook.getSheet(0);
        int col_num = sheet.getColumns();
        int row_num = sheet.getRows();
        BookCol[] column_map = new BookCol[col_num];
        logger.info("Total: " + col_num + " columns, " + row_num + " rows.");
        int row = 0;
        // Skip no data rows
        for (; row < row_num; row++) {
            if (!sheet.getCell(0, row).getContents().equals("")) {
                break;
            }
        }
        // Init the column map array
        for (int col = 0; col < col_num; col++) {
            String content = sheet.getCell(col, row).getContents();
            BookCol target = COLUMN_MAPPING.get(content);
            logger.info(content + ": " + target);
            if (target != null) {
                column_map[col] = target;
            }
        }
        row++;
        // Import data
        SqlSession session = ConnectionFactory.getSession().openSession();
        try {
            for (; row < row_num; row++) {
                Book book = new Book();
                List<String> barCodes = null;
                for (int col = 0; col < col_num; col++) {
                    String content = sheet.getCell(col, row).getContents();
                    if (column_map[col] != null) {
                        if (column_map[col].equals(BAR_CODE)) {
                            //parse multiply bar codes and insert more records
                            barCodes = parseBarCode(content);
                        } else {
                            fillBook(book, column_map[col], content);
                        }
                    }
                }
                if (barCodes != null) {
                    for (String barCode : barCodes) {
                        book.setBarCode(barCode);
                        insertBook(session, book);
                        book = book.clone();
                    }
                }

            }
        } finally {
            session.close();
        }
    }

    private static void insertBook(SqlSession session, Book book) {
        BookMap bookDAO = session.getMapper(BookMap.class);
        int count = bookDAO.insertBook(book);
        session.commit();
        logger.info(count + " book is inserted: " + book.getBarCode());
    }

    private static List<String> parseBarCode(String barCode) {
        List<String> barCodes = new ArrayList<String>();
        String[] tmp1 = barCode.split(",");
        for (String str1 : tmp1) {
            String[] tmp2 = str1.split("-", 2);
            if (tmp2.length == 1) {
                barCodes.add(tmp2[0]);
            } else {
                int start = Integer.valueOf(tmp2[0]);
                int end = Integer.valueOf(tmp2[1]);
                for (int id = start; id <= end; id++) {
                    barCodes.add(String.format("%08d", id));
                }
            }
        }
        return barCodes;
    }

    private static void fillBook(Book book, BookCol col, String content) throws ParseException {
        switch (col) {
            case ISBN:
                book.setIsbn(content);
                return;
            case PRICE:
                try {
                    book.setPrice(toPrice(content));
                } catch (NumberFormatException e) {
                    logger.warn("Can't convert \"" + content + "\" to price. " +
                            "Use 0 instead.");
                    logger.debug("Detail exception: ", e);
                    book.setPrice(0);
                }
                return;
            case NAME:
                book.setName(content);
                return;
            case SERIES:
                book.setSeries(content);
                return;
            case VOLUME:
                book.setSeries(content);
                return;
            case PUBLISHER:
                book.setPublisher(content);
                return;
            case AUTHOR:
                book.setAuthor(content);
                return;
            case CALL_NUM:
                book.setCallNum(content);
                return;
            case PAGE_NUM:
                try {
                    book.setPageNum(toPageNum(content));
                } catch (NumberFormatException e) {
                    logger.warn("Can't convert \"" + content + "\" to page number. " +
                            "Use 0 instead.");
                    logger.debug("Detail exception: ", e);
                    book.setPageNum(0);
                }
                return;
            case ENTER_DATE:
                book.setEnterDate(toDate(content));
                return;
            case SUMMARY:
                book.setSummary(content);
                return;
            case REMARKS:
                book.setRemarks(content);
                return;
            default:
                return;
        }

    }

    private static double toPrice(String value) {
        char[] chars = value.toCharArray();
        int i = 0;
        //过滤CNY
        for (; i < chars.length; i++) {
            if (isNumber(chars[i])) {
                break;
            }
        }
        StringBuilder builder = new StringBuilder("0");
        //提取数字
        for (; i < chars.length; i++) {
            if (isNumber(chars[i]) || '.' == chars[i]) {
                builder.append(chars[i]);
            } else {
                break;
            }
        }
        return Double.valueOf(builder.toString());
    }

    private static int toPageNum(String value) {
        int index = value.indexOf("(");
        char[] chars;
        if (index > 0) {
            //处理： 3册(1877页)
            chars = value.substring(index + 1).toCharArray();
        } else {
            //处理： 613页
            chars = value.toCharArray();
        }
        StringBuilder builder = new StringBuilder("0");
        for (int i = 0; i < chars.length; i++) {
            int plus = chars[i] - '0';
            if (plus >= 0 && plus < 10) {
                builder.append(chars[i]);
            }
        }
        return Integer.valueOf(builder.toString());
    }

    private static Date toDate(String value) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(value.substring(0, 9));
    }

    private static boolean isNumber(char c) {
        int plus = c - '0';
        return plus >= 0 && plus < 10;
    }
}