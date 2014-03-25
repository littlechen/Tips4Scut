package tips4scut.quartz;

import tips4scut.crawler.Notice4Scut;
import tips4scut.crawler.ScutAcademic;
import tips4scut.crawler.ScutEmploy;
import tips4scut.crawler.ScutSecondMarket;

/**
 * Created by Administrator on 13-12-25.
 */
public class RunTask {

    public void crawlInform(){


        Notice4Scut.crawlInform();
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        ScutAcademic.crawlAcademicInform();

        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        ScutSecondMarket.crawlMarket();

        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        ScutEmploy.crawlEmploy();
    }
}
