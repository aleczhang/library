package cn.ox85.models;

import java.util.Date;

/**
 * @author alec zhang
 */
public class Reader {

    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_LOST = 1;

    private int id;
    private String barCode;
    private ReaderType readerType;
    private String name;
    private String identityNum;
    private int sex = SEX_MALE;
    private String phone;
    private String email;
    private int status = STATUS_NORMAL;
    private byte[] photo;
    private String remarks;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public ReaderType getReaderType() {
        return readerType;
    }

    public void setReaderType(ReaderType readerType) {
        this.readerType = readerType;
    }

    public int getTypeId() {
        return readerType.getId();
    }

    public void setTypeId(int typeId) {
        if (readerType == null) {
            readerType = new ReaderType();
        }
        readerType.setId(typeId);
    }

    public String getTypeName() {
        return readerType.getName();
    }

    public void setTypeName(String typeName) {
        if (readerType == null) {
            readerType = new ReaderType();
        }
        readerType.setName(typeName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}