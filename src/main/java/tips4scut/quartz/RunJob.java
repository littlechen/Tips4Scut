package tips4scut.quartz;


import java.util.TimerTask;

/**
* Created by Administrator on 13-12-25.
*/
public class RunJob extends TimerTask {

    @Override
    public void run(){
        RunTask task = new RunTask();
        task.crawlInform();
    }
}
