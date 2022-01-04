package peopletech.yarn.monitor;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class YarnMonitor {

    public static void main(String[] args) {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String[] appNames = new String[3];
            appNames[0] = "cdp_realtime_73_peopleapp_plus_183";
            appNames[1] = "cdp_realtime_73_peopleapp_plus_184";
            appNames[2] = "cdp_realtime_73_peopleapp_plus_185";
            boolean yarnIsContains = yarnIsContains(appNames);
            String time = GetDate.date();
            String title = "Flink任务运行状态";
            String message = "信息:Flink任务cdp_realtime_73_peopleapp_plus运行状态异常";
            String webhook =
                    "https://oapi.dingtalk.com/robot/send?access_token=811092e50f106a971b02fbcd647fd515d5344909e556f78a7e22c1d4b802e6fe";
            if (yarnIsContains) {
                System.out.println(yarnIsContains + "==>" + time + "==>" + "cdp_realtime_73_peopleapp_plus Flink任务运行中！");
                continue;
            } else {
                System.out.println(yarnIsContains + "==>" + time + "==>" + "cdp_realtime_73_peopleapp_plus Flink任务运行异常！");
                DingUtil.sendToDingDing(title, message, webhook);
            }
        }
    }

    /**
     * 根据任务名获取任务状态
     *
     * @param appNames
     * @return boolean
     * @author chenghao
     */
    public static boolean yarnIsContains(String[] appNames) {
        Configuration conf = new YarnConfiguration();
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(conf);
        yarnClient.start();
        boolean isContains_183 = false;
        boolean isContains_184 = false;
        boolean isContains_185 = false;
        boolean isContains = false;
        List<ApplicationReport> applications = new ArrayList<ApplicationReport>();
        try {
            applications = yarnClient.getApplications(EnumSet.of(YarnApplicationState.RUNNING));
            for (ApplicationReport application : applications) {
                String name = application.getName();
                if (name.equals(appNames[0])) {
//                    System.out.println("name ============> " + application.getName());
//                    System.out.println(applications);
                    isContains_183 = true;
                } else if (name.equals(appNames[1])) {
//                    System.out.println("name ============> " + application.getName());
//                    System.out.println(applications);
                    isContains_184 = true;
                } else if (name.equals(appNames[2])) {
//                    System.out.println("name ============> " + application.getName());
//                    System.out.println(applications);
                    isContains_185 = true;
                }
            }
            if (isContains_183 && isContains_184 && isContains_185) {
                isContains = true;
            }
        } catch (YarnException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            yarnClient.stop();
        }
        return isContains;
    }
}