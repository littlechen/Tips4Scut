package tips4scut.model;

/**
 * Created by Administrator on 13-12-26.
 */
public class Jw2005Score {
    private String courseName;
    private String type;
    private String paperScore;
    private String score;
    private String credit;

    public Jw2005Score(String courseName,String type,String paperScore,String score,String credit){
        this.courseName = courseName;
        this.type = type;
        this.paperScore = paperScore;
        this.score = score;
        this.credit = credit;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s",courseName,type,paperScore,score,credit);
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setPaperScore(String paperScore) {
        this.paperScore = paperScore;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCredit() {
        return credit;
    }

    public String getPaperScore() {
        return paperScore;
    }

    public String getScore() {
        return score;
    }

    public String getType() {
        return type;
    }
}
