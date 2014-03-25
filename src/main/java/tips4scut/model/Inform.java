package tips4scut.model;

import tips4scut.utils.StringUtil;

/**
 * Created by Administrator on 13-12-25.
 */
public class Inform {

    private String time;
    private String author;
    private String title;
    private String content;
    private String link;
    private String forUserType;

    public Inform(String title,String link,String author){
        this.title = title;
        this.link = link;
        this.author = author;
    }

    public Inform(String title,String link){
        this.title = title;
        this.link = link;
    }

    public Inform(String redisStr){
        String [] arr = redisStr.split(StringUtil.STR_DELIMIT_2ND,3);
        this.title = arr[0];
        this.link = arr[1];
        this.author = arr[2];
    }

    public Inform(String redisStr,int page){
        String [] arr = redisStr.split(StringUtil.STR_DELIMIT_2ND,3);
        this.title = arr[0];
        this.time = arr[1];
        this.author = arr[2];
    }



    @Override
    public String toString() {
        return String.format("%s$%s$%s",title,link,author);
    }

    public String toAcademicString(){
        return String.format("%s$%s$%s",title,time,author);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setForUserType(String forUserType) {
        this.forUserType = forUserType;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getForUserType() {
        return forUserType;
    }

}
