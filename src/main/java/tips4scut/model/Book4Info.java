package tips4scut.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-12-25.
 */
public class Book4Info {
    private String title;
    private String author;
    private String publish;
    private String type;
    private String isbn;
    private String carrier;
    private String callNumber;
    private String more;

    private String remainCount;

    private String status;

    public Book4Info(Map<String,String> infoMap,String remainCount,String status){
        this.title = infoMap.get("题名");
        this.author = infoMap.get("主要责任者");
        this.publish = infoMap.get("出版资料");
        this.type = infoMap.get("中图分类");
        this.isbn = infoMap.get("ISBN");
        this.carrier = infoMap.get("载体形态");
        this.callNumber = infoMap.get("索书号");
        this.more = infoMap.get("附注");
        this.status = status;
        this.remainCount = remainCount;

    }

    @Override
    public String toString() {
        return String.format(
                "%s\n" +
                "%s\n" +
                "%s\n" +
                "%s\n" +
                "%s\n" +
                "%s\n" +
                "%s\n" +
                "%s\n" +
                "%s\n" +
                "%s\n",
                title,author,publish,type,isbn,callNumber,callNumber,more,status,remainCount);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public void setRemainCount(String remainCount) {
        this.remainCount = remainCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getRemainCount() {
        return remainCount;
    }

    public String getAuthor() {
        return author;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getMore() {
        return more;
    }

    public String getPublish() {
        return publish;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }
}
