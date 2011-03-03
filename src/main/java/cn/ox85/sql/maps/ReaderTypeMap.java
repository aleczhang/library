package cn.ox85.sql.maps;

import cn.ox85.models.ReaderType;

import java.util.List;

/**
 * @author alec zhang
 */
public interface ReaderTypeMap {
    public int insertReaderType(ReaderType readerType);

    public List<ReaderType> selectReaderTypes();
}
