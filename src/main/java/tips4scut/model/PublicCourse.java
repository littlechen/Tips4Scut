package tips4scut.model;

/**
 * Created by haiqi on 14-3-20.
 */
public class PublicCourse {

    private String courseName;
    private String university;
    private String teacher;
    private String hasPlayed;

    public PublicCourse(String courseName,String university,String teacher,String hasPlayed,String detail){
        this.courseName = courseName;
        this.university = university;
        this.teacher = teacher;
        this.hasPlayed = hasPlayed;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setHasPlayed(String hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getHasPlayed() {
        return hasPlayed;
    }

    public String getTeacher() {
        return teacher;
    }

    @Override
    public String toString(){
        return String.format("%s|%s|%s|%s",courseName,teacher,university,hasPlayed);
    }
}
