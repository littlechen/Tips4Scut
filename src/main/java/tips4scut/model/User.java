package tips4scut.model;

/**
 * Created by haiqi on 14-3-20.
 */
public class User {

    private String keyword;
    private String interest;
    private String email;

    public User(String email, String keyword, String interest){
        this.keyword = keyword;
        this.email = email;
        this.interest = interest;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getInterest() {
        return interest;
    }

    public String getEmail() {
        return email;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
