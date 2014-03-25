package tips4scut.model;

import sun.print.resources.serviceui_zh_TW;

/**
 * Created by Administrator on 13-12-26.
 */
public class Book4Borrow {
    private String title;
    private String place;
    private String startTime;
    private String endTime;
    private String remain;
    private String expired;
    private String continueLink;

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s",title,place,startTime,endTime,remain,expired,continueLink);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContinueLink(String continueLink) {
        this.continueLink = continueLink;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContinueLink() {
        return continueLink;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getExpired() {
        return expired;
    }

    public String getPlace() {
        return place;
    }

    public String getRemain() {
        return remain;
    }

    public String getStartTime() {
        return startTime;
    }


}
