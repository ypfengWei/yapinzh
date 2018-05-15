package cn.yapin.gzh.entity;

public class BasePage {
    private String fields;
    private String tables;
    private String wheres;
    private String grops;
    private String orders;
    private int pageindex;
    private int pageCount;
    private int totalSize;
    private int totalPage;
    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public String getWheres() {
        return wheres;
    }

    public void setWheres(String wheres) {
        this.wheres = wheres;
    }

    public String getGrops() {
        return grops;
    }

    public void setGrops(String grops) {
        this.grops = grops;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public int getPageindex() {
        return pageindex;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
