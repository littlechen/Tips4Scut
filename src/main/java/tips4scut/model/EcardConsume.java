package tips4scut.model;

import tips4scut.utils.StringUtil;

/**
 * Created by Administrator on 13-12-25.
 */
public class EcardConsume {
    private String time;
    private String consumeType;
    private String place;
    private String price;
    private String remain;
    private String count;

    public EcardConsume(String str){
        String [] arr = str.split(StringUtil.STR_DELIMIT_2ND,8);
        this.time = arr[0];
        this.consumeType = String.format("%s %s %s",arr[1],arr[3],arr[7]);
        this.place = arr[2];
        this.price = arr[4];
        this.remain = arr[5];
        this.count = arr[6];
    }

    @Override
    public String toString() {
        return String.format("%s$%s$%s$%s$%s$%s",time,consumeType,place,price,remain,count);
    }

    public EcardConsume(String str,boolean status){
        String [] arr = str.split(StringUtil.STR_DELIMIT_2ND,6);
        this.time = arr[0];
        this.consumeType =arr[1];
        this.place = arr[2];
        this.price = arr[3];
        this.remain = arr[4];
        this.count = arr[5];
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getTime() {
        return time;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public String getCount() {
        return count;
    }

    public String getPlace() {
        return place;
    }

    public String getPrice() {
        return price;
    }

    public String getRemain() {
        return remain;
    }
}
