package cn.ox85.ui.components.reader;

import cn.ox85.models.Reader;
import cn.ox85.models.ReaderType;
import cn.ox85.sql.maps.ReaderMap;
import cn.ox85.ui.beans.ReaderBean;
import cn.ox85.ui.listeners.Filter;
import cn.ox85.ui.util.BundleUtil;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author alec zhang
 */
public abstract class ReaderSingleFilter<P> implements Filter<ReaderMap, Reader> {
    protected P parameter_;

    @Override
    public abstract int getCount(ReaderMap mapper);

    @Override
    public abstract List<Reader> getResults(ReaderMap mapper, RowBounds bounds);

    public void setParameter(P parameter) {
        parameter_ = parameter;
    }

    public P getParameter() {
        return parameter_;
    }

    public static final ReaderSingleFilter NONE = new ReaderSingleFilter<String>() {

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCount();
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.selectReaders(bounds);
        }
    };

    static class Type extends ReaderSingleFilter<ReaderType> {
        public Type(ReaderType type) {
            parameter_ = type;
        }

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCountByReaderType(getParameter().getId());
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.searchReaderByReaderType(getParameter().getId(), bounds);
        }

        @Override
        public String toString() {
            return parameter_.toString();
        }
    }

    static class Status extends ReaderSingleFilter<ReaderBean.Status> {
        public static final Status NROMAL = new Status(ReaderBean.Status.NORMAL);
        public static final Status LOST = new Status(ReaderBean.Status.LOST);

        private Status(ReaderBean.Status status) {
            parameter_ = status;
        }

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCountByStatus(getParameter().ordinal());
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.searchReaderByStatus(getParameter().ordinal(), bounds);
        }

        @Override
        public String toString() {
            return parameter_.toString();
        }
    }

    static class Sex extends ReaderSingleFilter<ReaderBean.Sex> {
        public static final Sex MALE = new Sex(ReaderBean.Sex.MALE);
        public static final Sex FEMALE = new Sex(ReaderBean.Sex.FEMALE);

        private Sex(ReaderBean.Sex sex) {
            parameter_ = sex;
        }

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCountBySex(getParameter().ordinal());
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.searchReaderBySex(getParameter().ordinal(), bounds);
        }

        @Override
        public String toString() {
            return parameter_.toString();
        }
    }

    static class Name extends ReaderSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_ReaderName");

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCountByName(getParameter());
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.searchReaderByName(getParameter(), bounds);
        }

        @Override
        public void setParameter(String parameter) {
            parameter_ = "%" + parameter + "%";
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class BarCode extends ReaderSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_ReaderBarCode");

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCountByBarCode(getParameter());
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.searchReaderByBarCode(getParameter(), bounds);
        }

        @Override
        public void setParameter(String parameter) {
            parameter_ = "%" + parameter + "%";
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class IdentityNum extends ReaderSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_ReaderIdentityNum");

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCountByIdentityNum(getParameter());
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.searchReaderByIdentityNum(getParameter(), bounds);
        }

        @Override
        public void setParameter(String parameter) {
            parameter_ = "%" + parameter + "%";
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class Phone extends ReaderSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_ReaderPhone");

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCountByPhone(getParameter());
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.searchReaderByPhone(getParameter(), bounds);
        }

        @Override
        public void setParameter(String parameter) {
            parameter_ = "%" + parameter + "%";
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class Email extends ReaderSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_ReaderEmail");

        @Override
        public int getCount(ReaderMap readerMap) {
            return readerMap.selectReaderCountByEmail(getParameter());
        }

        @Override
        public List<Reader> getResults(ReaderMap readerMap, RowBounds bounds) {
            return readerMap.searchReaderByEmail(getParameter(), bounds);
        }

        @Override
        public void setParameter(String parameter) {
            parameter_ = "%" + parameter + "%";
        }

        @Override
        public String toString() {
            return NAME;
        }
    }
}
