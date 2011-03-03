package cn.ox85.sql.maps;

import cn.ox85.models.Reader;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author alec zhang
 */
public interface ReaderMap {

    public int insertReader(Reader reader);

    public int updateReader(Reader reader);

    public List<Reader> selectReaders();

    public Reader selectReaderById(int id);

    public Reader selectReaderByBarCode(String barCode);

    public int selectReaderCountByReaderType(int typeId);

    public List<Reader> searchReaderByReaderType(int typeId, RowBounds bounds);

    public int selectReaderCountByStatus(int status);

    public List<Reader> searchReaderByStatus(int status, RowBounds bounds);

    public int selectReaderCountBySex(int sex);

    public List<Reader> searchReaderBySex(int sex, RowBounds bounds);

    public int selectReaderCountByName(String name);

    public List<Reader> searchReaderByName(String name, RowBounds bounds);

    public int selectReaderCountByBarCode(String barCode);

    public List<Reader> searchReaderByBarCode(String barCode, RowBounds bounds);

    public int selectReaderCountByIdentityNum(String identityNum);

    public List<Reader> searchReaderByIdentityNum(String identityNum, RowBounds bounds);

    public int selectReaderCountByPhone(String phone);

    public List<Reader> searchReaderByPhone(String phone, RowBounds bounds);

    public int selectReaderCountByEmail(String email);

    public List<Reader> searchReaderByEmail(String email, RowBounds bounds);

    public int selectReaderCount();

    public List<Reader> selectReaders(RowBounds bounds);

    public int deleteReadersById(List<Integer> ids);
}
