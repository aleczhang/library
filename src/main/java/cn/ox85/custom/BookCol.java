package cn.ox85.custom;

/**
 * @author alec zhang
 */
public enum BookCol {
    BAR_CODE("bar_code"), ISBN("isbn"), PRICE("price"), NAME("name"), SERIES("series"),
    VOLUME("volume"), PUBLISHER("publisher"), AUTHOR("autor"),
    CALL_NUM("call_num"), PAGE_NUM("page_num"), ENTER_DATE("enter_date"),
    SUMMARY("summary"), REMARKS("remarks");

    private String name_;

    private BookCol(String name) {
        name_ = name;
    }


    @Override
    public String toString() {
        return name_;
    }
}