package tips4scut.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 13-12-26.
 */
public class Jw2005Schedule {
    public static enum WEEK{
        MON,TUE,WED,THU,FRI,SAT,SUN,NULL
    }

    private Map<String,List<Course>> schedule = new HashMap<String, List<Course>>();

    public void addSchedule(String day,List<Course> list){
        schedule.put(day,list);
    }

    public List<Course> getScheduleByDay(String day){
        if(schedule.containsKey(day)){
            return  schedule.get(day);
        }else {
            return  null;
        }
    }

    public void setSchedule(Map<String, List<Course>> schedule) {
        this.schedule = schedule;
    }

    public Map<String, List<Course>> getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return schedule.toString();
    }
}
