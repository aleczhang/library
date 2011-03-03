package cn.ox85.ui.components.ri;

import cn.ox85.models.RI;
import cn.ox85.sql.maps.RIMap;
import cn.ox85.ui.listeners.Filter;
import cn.ox85.ui.util.BundleUtil;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author alec zhang
 */
public abstract class RISingleFilter<P> implements Filter<RIMap, RI> {
    protected P parameter_;

    @Override
    public abstract int getCount(RIMap mapper);

    @Override
    public abstract List<RI> getResults(RIMap mapper, RowBounds bounds);

    public void setParameter(P parameter) {
        parameter_ = parameter;
    }

    public P getParameter() {
        return parameter_;
    }

    public static final RISingleFilter NONE = new RISingleFilter<String>() {

        @Override
        public int getCount(RIMap riMap) {
            return riMap.selectRICount();
        }

        @Override
        public List<RI> getResults(RIMap riMap, RowBounds bounds) {
            return riMap.selectRI(bounds);
        }

        @Override
        public String toString() {
            return BundleUtil.getString("NODE_All");
        }
    };

    static class ReaderBarCode extends RISingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_ReaderBarCode");

        @Override
        public int getCount(RIMap riMap) {
            return riMap.selectRICountByReaderBarCode(parameter_);
        }

        @Override
        public List<RI> getResults(RIMap riMap, RowBounds bounds) {
            return riMap.selectRIByReaderBarCode(parameter_, bounds);
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class ReaderName extends RISingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_ReaderName");

        @Override
        public int getCount(RIMap riMap) {
            return riMap.selectRICountByReaderName(parameter_);
        }

        @Override
        public List<RI> getResults(RIMap riMap, RowBounds bounds) {
            return riMap.selectRIByReaderName(parameter_, bounds);
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