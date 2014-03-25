package tips4scut.redis;

/**
 * Created by Administrator on 13-12-25.
 */
public class RedisKeyUtils {

    public static String getInformParttimejob(){
        return RedisConsts.INFORM_PARTTIMEJOB;
    }

    public static String getinformHospital(){
        return RedisConsts.INFORM_HOSPITAL;
    }

    public static String getCampusTransferMarchine(){
        return RedisConsts.INFORM_CAMPUS_TRANSFER_MARCHINE;
    }

    public static String getInformYourth(int page){
        return String.format("%s|%s",RedisConsts.INFORM_YOURTH,page);
    }

    public static String getInformLogistics(int page){
        return String.format("%s|%s",RedisConsts.INFORM_LOGISTICS,page);
    }

    public static String getCampusCard(int page){
        return String.format("%s|%s",RedisConsts.INFORM_CAMPUS_CARD,page);
    }

    public static String getInformLibrary(int page){
        return String.format("%s|%s",RedisConsts.INFORM_LIBRARY,page);
    }

    public static String getInformGlobalPartnerships(int page){
        return String.format("%s|%s",RedisConsts.INFORM_GLOBAL_PARTNERSHIPS,page);
    }

    public static String getInformCampusNetwork(int page){
        return String.format("%s|%s",RedisConsts.INFORM_CAMPUS_NETWORK,page);
    }

    public static String getAcademicAllCollege(int page){
        return String.format("%s|%s",RedisConsts.INFORM_ACADEMIC_ALL_COLLEGE,page);
    }

    public static String getAcademicMediaReport(int page){
        return String.format("%s|%s",RedisConsts.INFORM_ACADEMIC_MEDIA_REPORT,page);
    }

    public static String getAcademicNew(int page){
        return String.format("%s|%s",RedisConsts.INFORM_ACADEMIC_NEWS,page);
    }

    public static String getAcadeicNotic(int page){
        return String.format("%s|%s",RedisConsts.INFORM_ACADEMIC_NOTIC,page);
    }

    public static String getAcadeicCollegeByName(String college){
        return String.format("%s|%s",RedisConsts.INFORM_ACADEMIC_COLLEGE,college);
    }

    public static String getEmployInterships(int page,int pagecount){
        pagecount = 15;
        return String.format("%s|%s",RedisConsts.EMPLOY_INTERSHIP,page,pagecount);
    }

    public static String getEmployList(int page,int pagecount){
        pagecount = 15;
        return String.format("%s|%s",RedisConsts.EMPLOY_EMPLOYLIST,page,pagecount);
    }

    public static String getEmploySpecialRecent(int page,int pagecount){
        pagecount = 15;
        return String.format("%s|%s",RedisConsts.EMPLOY_SPECAILRECENT,page,pagecount);
    }

    public static String getEmploySpecialExitra(int page,int pagecount){
        pagecount = 15;
        return String.format("%s|%s",RedisConsts.EMPLOY_SPECIALEXITRA,page,pagecount);
    }

    public static String getEmploySpecialAll(int page,int pagecount){
        pagecount = 15;
        return String.format("%s|%s",RedisConsts.EMPLOY_SPECIALALL,page,pagecount);
    }

    public static String getEcardConsume(String student,int page,boolean today){
        return  String.format("%s|%s|%s|%s",RedisConsts.ECARD_CONSUME,student,page,today);
    }

    public static String getMarketElectron(){
        return  RedisConsts.MARKET_ELECTRON;
    }

    public static String getMarketBike(){
        return  RedisConsts.MARKET_BIKE;
    }

    public static String getMarketBook(){
        return  RedisConsts.MARKET_BOOK;
    }

    public static String getMarketHourse(){
        return  RedisConsts.MARKET_HOURSE;
    }


    public static String getUserID(String email){
        return String.format("%s|%s",RedisConsts.USER_ID,email);
    }

}
