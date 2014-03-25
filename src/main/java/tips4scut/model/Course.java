package tips4scut.model;

/**
 * Created by Administrator on 13-12-26.
 */
public class Course {
    private String courseName;
    private String time;
    private String professor;
    private String room;

    public Course(String courseName,String time,String professor,String room){
        this.courseName = courseName;
        this.time = time;
        this.professor = professor;
        this.room = room;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProfessor() {
        return professor;
    }

    public String getRoom() {
        return room;
    }

    public String getTime() {
        return time;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s",courseName,time,professor,room);
    }
}
