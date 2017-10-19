package nsy209.cnam.seldesave.background.jobService;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;

import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.background.helper.BackgroundConstant;
import nsy209.cnam.seldesave.background.helper.JobToScheduleUtil;
import nsy209.cnam.seldesave.background.intentService.CheckRemoteDataBaseIntentService;
import nsy209.cnam.seldesave.background.receiver.CheckRemoteDataBaseResultReceiver;
import nsy209.cnam.seldesave.dao.DaoFactory;

/**
 * Created by lavive on 18/07/2017.
 *
 * to schedule update
 */

public class ScheduleCheckingJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters){
        if(HomeActivity.firstHomeActivity != null) {
            /* launch check if local database is up to date */
            Intent intent = new Intent(HomeActivity.firstHomeActivity, CheckRemoteDataBaseIntentService.class);
            DaoFactory daoFactory = DaoFactory.getInstance(getApplicationContext());
            daoFactory.open();
            long myRemoteId = daoFactory.getMyProfileDao().getMyProfile().getRemote_id();
            ResultReceiver mResultReceiver = new CheckRemoteDataBaseResultReceiver(new Handler(), HomeActivity.firstHomeActivity, daoFactory);
            intent.putExtra(BackgroundConstant.RECEIVER_CHECK_DATABASE, mResultReceiver);
            intent.putExtra(BackgroundConstant.MY_PROFILE_BEAN, myRemoteId);
            getApplicationContext().startService(intent);
            JobToScheduleUtil.scheduleJob(HomeActivity.firstHomeActivity);
        }
        else{
            jobFinished(jobParameters, true);

        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters){
        return true;
    }
}
