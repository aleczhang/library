package cn.ox85.ui.listeners;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author alec zhang
 */
public interface Filter<M, T> {
    public int getCount(M mapper);

    public List<T> getResults(M mapper, RowBounds bounds);
}
