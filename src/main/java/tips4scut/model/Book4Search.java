package tips4scut.model;

import tips4scut.utils.StringUtil;

/**
 * Created by Administrator on 13-12-25.
 */
public class Book4Search {
    private String id;
    private String title;
    private String author;
    private String publish;
    private String isbn;
    private String callNum;
    private String year;
    private String type;

    public Book4Search(String id,String title,String author,String publish,String isbn,String callNum){
        this.id =id;
        this.title = title;
        this.author = author;
        this.publish = publish;
        this.isbn = isbn;
        this.callNum = callNum;
    }

    public Book4Search(){

    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s %s",id,title,author,publish,isbn,year,callNum,type);
    }

    public static Book4Search createBook4Search(String content){
        String [] arr = content.split(StringUtil.STR_DELIMIT_2ND,8);
        Book4Search book = new Book4Search();
        book.id = arr[0];
        book.title = arr[1];
        book.author = arr[2];
        book.publish =arr[3];
        book.isbn = arr[4];
        book.year = arr[5];
        book.callNum = arr[6];
        book.type = arr[7];
        return book;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCallNum() {
        return callNum;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublish() {
        return publish;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCallNum(String callNum) {
        this.callNum = callNum;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }
}
