package tips4scut.model;

import tips4scut.utils.MD5;

import java.util.Date;

/**
 * Created by haiqi on 14-3-20.
 */
public class Account {

    private static final long serialVersionID = 1L;

    private String email;
    private String password;
    private String keyword;
    private String interest;
    private String checkcode;
    private String id;
    private Date createTime;

    public String getEmail() {
        return email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getInterest() {
        return interest;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString(){
        return String.format("%s|%s|%s|$s", MD5.getMD5Code(password),keyword,interest,createTime);
    }
}
