package cn.ox85.ui.components.reader;

import cn.ox85.models.Reader;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.ReaderMap;
import cn.ox85.ui.beans.ReaderBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.components.JImagePanel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;
import cn.ox85.ui.validation.ReaderValidator;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.awt.*;

/**
 * @author alec zhang
 */
public class ReaderEditDialog extends AbstractReaderDialog {

    public ReaderEditDialog(Frame owner, int readerId) {
        super(owner);
        setIconImage(IconLoader.getIcon32("main.png").getImage());
        setTitle(BundleUtil.getString("TITLE_ReaderEdit"));
        SqlSession session = ConnectionFactory.getSession().openSession();
        Reader reader;
        try {
            ReaderMap readerDAO = session.getMapper(ReaderMap.class);
            reader = readerDAO.selectReaderById(readerId);
            session.commit();
        } finally {
            session.close();
        }
        this.bindingModel_.setBean(new ReaderBean(reader));
        if (reader.getPhoto() != null) {
            photo_ = reader.getPhoto();
            ImageIcon image = new ImageIcon(photo_);
            int panelHeight = photoPanel_.getHeight();
            int panelWidth = photoPanel_.getWidth();
            int imageHeight = image.getIconHeight();
            int imageWidth = image.getIconWidth();
            if (imageHeight > panelHeight || imageWidth > panelWidth) {
                photoPanel_.setStyle(JImagePanel.Style.SCALED_KEEP_ASPECT_RATIO);
            } else {
                photoPanel_.setStyle(JImagePanel.Style.CENTERED);
            }
            photoPanel_.setImage(image.getImage());
        }
    }

    @Override
    protected BindingModel<ReaderBean> initBindingModel() {
        return new BindingModel<ReaderBean>(new ReaderBean(), new ReaderValidator());
    }

    @Override
    protected void initWidgetStatus() {
        barCodeTextField_.setEditable(false);
    }

    @Override
    protected boolean commitReader() {
        ReaderBean readerBean = bindingModel_.getBean();
        readerBean.setPhoto(photo_);
        return readerBean.updateReader();
    }
}
