package cn.ox85.sql;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/**
 * @author alec zhang
 */
public class ConnectionFactory {

    private static SqlSessionFactory sqlMapper_;
    private static Reader reader_;

    static {
        try {
            reader_ = Resources.getResourceAsReader("configuration.xml");
            sqlMapper_ = new SqlSessionFactoryBuilder().build(reader_);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession() {
        return sqlMapper_;
    }
}
