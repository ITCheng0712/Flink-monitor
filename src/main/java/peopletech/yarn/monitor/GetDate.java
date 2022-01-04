package peopletech.yarn.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
    /**
     * 获取当前时间的方法
     * @return time
     */
    public static String date(){
        Date dd=new Date();
        //格式化
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sim.format(dd);
        return time;
    }
}
