package nsy209.cnam.seldesave.background.helper;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import nsy209.cnam.seldesave.background.jobService.ScheduleCheckingJobService;

/**
 * Created by lavive on 18/07/2017.
 */

public class JobToScheduleUtil {

    /* schedule the start of the service */
    public static void scheduleJob (Activity activity){
        ComponentName componentName = new ComponentName(activity, ScheduleCheckingJobService.class);
        JobInfo.Builder jobBuilder = new JobInfo.Builder(0,componentName);
        jobBuilder.setPeriodic(BackgroundConstant.PERIOD_UPDATE).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        JobScheduler jobScheduler = activity.getSystemService(JobScheduler.class);
        jobScheduler.schedule(jobBuilder.build());
    }

    /* cancel the service */
    public static void cancelJob(Context context){
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(0);
    }
}
