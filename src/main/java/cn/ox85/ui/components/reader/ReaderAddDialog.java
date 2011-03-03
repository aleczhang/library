package cn.ox85.ui.components.reader;

import cn.ox85.ui.beans.ReaderBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;
import cn.ox85.ui.validation.ReaderAddValidator;

import java.awt.*;

/**
 * @author alec zhang
 */
public class ReaderAddDialog extends AbstractReaderDialog {

    public ReaderAddDialog(Frame owner) {
        super(owner);
        setIconImage(IconLoader.getIcon32("main.png").getImage());
        setTitle(BundleUtil.getString("TITLE_ReaderAdd"));
    }

    @Override
    protected BindingModel<ReaderBean> initBindingModel() {
        return new BindingModel<ReaderBean>(new ReaderBean(), new ReaderAddValidator());
    }

    @Override
    protected void initWidgetStatus() {
        // Noting to do.
    }

    @Override
    protected boolean commitReader() {
        ReaderBean readerBean = bindingModel_.getBean();
        readerBean.setPhoto(photo_);
        return readerBean.insertReader();
    }
}