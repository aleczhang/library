package cn.ox85.ui.components.bbr;

import cn.ox85.models.BBR;
import cn.ox85.sql.maps.BBRMap;
import cn.ox85.ui.listeners.Filter;
import cn.ox85.ui.util.BundleUtil;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author alec zhang
 */
public abstract class BBRSingleFilter<P> implements Filter<BBRMap, BBR> {
    protected P parameter_;

    @Override
    public abstract int getCount(BBRMap mapper);

    @Override
    public abstract List<BBR> getResults(BBRMap mapper, RowBounds bounds);

    public void setParameter(P parameter) {
        parameter_ = parameter;
    }

    public P getParameter() {
        return parameter_;
    }

    public static final BBRSingleFilter NONE = new BBRSingleFilter<String>() {

        @Override
        public int getCount(BBRMap bbrMap) {
            return bbrMap.selectBBRCount();
        }

        @Override
        public List<BBR> getResults(BBRMap bbrMap, RowBounds bounds) {
            return bbrMap.selectBBR(bounds);
        }
    };

    static class Status extends BBRSingleFilter<Boolean> {
        public static final Status RETURNED = new Status(true, BundleUtil.getString("NODE_BBRReturned"));
        public static final Status NORETURNED = new Status(false, BundleUtil.getString("NODE_BBRNoReturned"));

        private String name_;

        private Status(Boolean returned, String name) {
            parameter_ = returned;
            name_ = name;
        }

        @Override
        public int getCount(BBRMap mapper) {
            return mapper.selectBBRCountByReturned(parameter_);
        }

        @Override
        public List<BBR> getResults(BBRMap mapper, RowBounds bounds) {
            return mapper.selectBBRByReturned(parameter_, bounds);
        }

        @Override
        public String toString() {
            return name_;
        }
    }

    static class ReaderBarCode extends BBRSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_ReaderBarCode");

        @Override
        public int getCount(BBRMap bbrMap) {
            return bbrMap.selectBBRCountByReaderBarCode(parameter_);
        }

        @Override
        public List<BBR> getResults(BBRMap bbrMap, RowBounds bounds) {
            return bbrMap.selectBBRByReaderBarCode(parameter_, bounds);
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class BookBarCode extends BBRSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITME_BookBarCode");

        @Override
        public int getCount(BBRMap bbrMap) {
            return bbrMap.selectBBRCountByBookBarCode(parameter_);
        }

        @Override
        public List<BBR> getResults(BBRMap bbrMap, RowBounds bounds) {
            return bbrMap.selectBBRByBookBarCode(parameter_, bounds);
        }

        @Override
        public String toString() {
            return NAME;
        }
    }
}