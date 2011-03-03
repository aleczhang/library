package cn.ox85.ui.beans;

import cn.ox85.models.Reader;
import cn.ox85.models.ReaderType;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.ReaderMap;
import cn.ox85.ui.util.BundleUtil;
import com.jgoodies.binding.beans.Model;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

/**
 * @author alec zhang
 */
public class ReaderBean extends Model {
    public static final String PROPERTYNAME_ID = "id";
    public static final String PROPERTYNAME_BARCODE = "barCode";
    public static final String PROPERTYNAME_TYPE = "type";
    public static final String PROPERTYNAME_NAME = "name";
    public static final String PROPERTYNAME_IDENTITYNUM = "identityNum";
    public static final String PROPERTYNAME_SEX = "sex";
    public static final String PROPERTYNAME_PHONE = "phone";
    public static final String PROPERTYNAME_EMAIL = "email";
    public static final String PROPERTYNAME_STATUS = "status";
    public static final String PROPERTYNAME_PHOTO = "photo";
    public static final String PROPERTYNAME_REMARKS = "remarks";
    public static final String PROPERTYNAME_DATE = "date";

    public static final Sex[] SEX_CHOICES = new Sex[]{Sex.MALE, Sex.FEMALE};
    public static final Status[] STATUS_CHOICES = new Status[]{Status.NORMAL, Status.LOST};

    private Reader reader_;

    public ReaderBean() {
        reader_ = new Reader();
    }

    public ReaderBean(Reader reader) {
        reader_ = reader;
    }

    public boolean insertReader() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        try {
            ReaderMap readerDAO = session.getMapper(ReaderMap.class);
            int count = readerDAO.insertReader(reader_);
            session.commit();
            return count > 0;
        } finally {
            session.close();
        }
    }

    public boolean updateReader() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        try {
            ReaderMap readerDAO = session.getMapper(ReaderMap.class);
            int count = readerDAO.updateReader(reader_);
            session.commit();
            return count > 0;
        } finally {
            session.close();
        }
    }

    public void setId(int newId) {
        int oldId = getId();
        reader_.setId(newId);
        firePropertyChange(PROPERTYNAME_ID, oldId, newId);
    }

    public void setBarCode(String newBarCode) {
        String oldBarCode = getBarCode();
        reader_.setBarCode(newBarCode);
        firePropertyChange(PROPERTYNAME_BARCODE, oldBarCode, newBarCode);
    }

    public void setType(ReaderType newType) {
        ReaderType oldType = getType();
        reader_.setReaderType(newType);
        firePropertyChange(PROPERTYNAME_TYPE, oldType, newType);
    }

    public void setName(String newName) {
        String oldName = getName();
        reader_.setName(newName);
        firePropertyChange(PROPERTYNAME_NAME, oldName, newName);
    }

    public void setIdentityNum(String newIdentityNum) {
        String oldIdentityNum = getIdentityNum();
        reader_.setIdentityNum(newIdentityNum);
        firePropertyChange(PROPERTYNAME_IDENTITYNUM, oldIdentityNum, newIdentityNum);
    }

    public void setSex(Sex newSex) {
        Sex oldSex = getSex();
        reader_.setSex(newSex.ordinal());
        firePropertyChange(PROPERTYNAME_SEX, oldSex, newSex);
    }

    public void setPhone(String newPhone) {
        String oldPhone = getPhone();
        reader_.setPhone(newPhone);
        firePropertyChange(PROPERTYNAME_PHONE, oldPhone, newPhone);
    }

    public void setEmail(String newEmail) {
        String oldEmail = getEmail();
        reader_.setEmail(newEmail);
        firePropertyChange(PROPERTYNAME_EMAIL, oldEmail, newEmail);
    }

    public void setStatus(Status newStatus) {
        Status oldStatus = getStatus();
        reader_.setStatus(newStatus.ordinal());
        firePropertyChange(PROPERTYNAME_STATUS, oldStatus, newStatus);
    }

    public void setPhoto(byte[] newPhoto) {
        byte[] oldPhoto = getPhoto();
        reader_.setPhoto(newPhoto);
        firePropertyChange(PROPERTYNAME_PHOTO, oldPhoto, newPhoto);
    }

    public void setRemarks(String newRemarks) {
        String oldRemarks = getRemarks();
        reader_.setRemarks(newRemarks);
        firePropertyChange(PROPERTYNAME_REMARKS, oldRemarks, newRemarks);
    }

    public void setDate(Date newDate) {
        Date oldDate = getDate();
        reader_.setDate(newDate);
        firePropertyChange(PROPERTYNAME_DATE, oldDate, newDate);
    }

    public int getId() {
        return reader_.getId();
    }

    public String getBarCode() {
        return reader_.getBarCode();
    }

    public ReaderType getType() {
        return reader_.getReaderType();
    }

    public String getName() {
        return reader_.getName();
    }

    public String getIdentityNum() {
        return reader_.getIdentityNum();
    }

    public Sex getSex() {
        return Sex.values()[reader_.getSex()];
    }

    public String getPhone() {
        return reader_.getPhone();
    }

    public String getEmail() {
        return reader_.getEmail();
    }

    public Status getStatus() {
        return Status.values()[reader_.getStatus()];
    }

    public byte[] getPhoto() {
        return reader_.getPhoto();
    }

    public String getRemarks() {
        return reader_.getRemarks();
    }

    public Date getDate() {
        return reader_.getDate();
    }

    public static enum Sex {
        MALE(BundleUtil.getString("ITEM_SexMale")),
        FEMALE(BundleUtil.getString("ITEM_SexFemale"));

        private String name_;

        private Sex(String name) {
            name_ = name;
        }

        @Override
        public String toString() {
            return name_;
        }
    }

    public static enum Status {
        NORMAL(BundleUtil.getString("ITEM_StatusNormal")),
        LOST(BundleUtil.getString("ITEM_StatusLost"));

        private String name_;

        private Status(String name) {
            name_ = name;
        }

        @Override
        public String toString() {
            return name_;
        }
    }
}