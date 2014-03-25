package tips4scut.model;

import tips4scut.utils.StringUtil;

/**
 * Created by Administrator on 13-12-25.
 */
public class Item4Sell {
   private String title;
   private String link;
   private String time;
   private String more;

    public Item4Sell(String title,String time,String link,String more){
        this.title = title;
        this.time = time;
        this.link = link;
        this.more = more;
    }

    public Item4Sell(String redisStr){
        String [] arr = redisStr.split(StringUtil.STR_DELIMIT_1ST);
        this.title = arr[0];
        this.link = arr[1];
        this.time = arr[2];
        this.more = arr[3];
    }

    @Override
    public String toString() {
        return String.format("%s$%s$%s$%s",title,link,time,more);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getMore() {
        return more;
    }

    public String getTime() {
        return time;
    }
}
