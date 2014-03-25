package tips4scut.model;

import tips4scut.utils.StringUtil;

/**
 * Created by Administrator on 13-12-25.
 */
public class Employ {
    private String title;
    private String link;

    public Employ(String title,String link){
        this.title = title;
        this.link = link;
    }

    @Override
    public String toString() {
        return String.format("%s$%s",title,link);
    }

    public Employ(String redisStr){
        String [] arr = redisStr.split(StringUtil.STR_DELIMIT_2ND,2);
        this.title = arr[0];
        this.link = arr[1];
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
