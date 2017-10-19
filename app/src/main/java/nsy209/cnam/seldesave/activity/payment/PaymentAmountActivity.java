package nsy209.cnam.seldesave.activity.payment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.helper.ConfirmActivity;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.PaymentAmountModel;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class PaymentAmountActivity extends AppCompatActivity implements View.OnClickListener,Observer{

    /* constant */
    public final static int REQUEST_EXIT = 0;

    /* daoFactory */
    private DaoFactory daoFactory;

    /* views */
    private EditText amountValue;
    private TextView errorAmount;
    private TextView memberToPay;
    private TextView supplyDemandToPay;

    /* controller */
    private ImageButton validate;

    /* model */
    private PaymentAmountModel paymentAmountModel;

    /* datas */
    long member_id;
    long supplyDemand_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_amount);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* views */
        amountValue = (EditText) findViewById(R.id.amount);
        errorAmount = (TextView) findViewById(R.id.errorAmount);
        memberToPay = (TextView) findViewById(R.id.to_member);
        supplyDemandToPay = (TextView) findViewById(R.id.for_supplyDemand);

        /* controller */
        validate = (ImageButton) findViewById(R.id.validateAmount);
        validate.setOnClickListener(this);

        /* datas */
        member_id = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_MEMBER_ID,ActivityConstant.BAD_ID);
        supplyDemand_id = getIntent().getLongExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID,ActivityConstant.BAD_ID);

        /* model */
        paymentAmountModel = new PaymentAmountModel();
        paymentAmountModel.addObserver(this);

        /* if start from ConfirmActivity */
        if(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_CONFIRM,false)){
            amountValue.setText(getIntent().getStringExtra(EnumField.AMOUNT.getWording()));
        }
    }

    @Override
    public void onClick(View view){
        if(view == validate){
            /* check error */
            paymentAmountModel.onChange(amountValue.getText().toString());
            /* confirm */
            if(errorAmount.getText().equals("")) {
                if(getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_NFC,false)){
                    /* go to payment by NFC */
                    Intent intent = new Intent(this, PaymentByNFCActivity.class);
                    intent.putExtra(ActivityConstant.KEY_INTENT_NFC,true);
                    intent.putExtra(ActivityConstant.KEY_INTENT_MEMBER_ID, member_id);
                    intent.putExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID, supplyDemand_id);
                    intent.putExtra(EnumField.AMOUNT.getWording(), amountValue.getText().toString());
                    startActivity(intent);
                } else {
                    /* go to confirm payment */
                    Intent intent = new Intent(this, ConfirmActivity.class);
                    intent.putExtra(ActivityConstant.KEY_INTENT_MY_SUPPLYDEMAND, false);
                    intent.putExtra(ActivityConstant.KEY_INTENT_NOTIFICATIONS, false);
                    intent.putExtra(ActivityConstant.KEY_INTENT_FILTER, false);
                    intent.putExtra(ActivityConstant.KEY_INTENT_PAYMENT, true);
                    intent.putExtra(ActivityConstant.KEY_INTENT_MEMBER_ID, member_id);
                    intent.putExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID, supplyDemand_id);
                    intent.putExtra(EnumField.AMOUNT.getWording(), amountValue.getText().toString());
                    startActivityForResult(intent, REQUEST_EXIT);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* finish this activity if result ok */
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update view */
        supplyDemandToPay.setText(((PaymentAmountModel) observable).getSupplyDemand());
        memberToPay.setText(((PaymentAmountModel) observable).getMember());
        errorAmount = (TextView) findViewById(R.id.errorAmount);
        errorAmount.setText( daoFactory.getErrorMessageDao().getErrorMessage( ((PaymentAmountModel) observable).getErrorCheck() ) );

        if(((PaymentAmountModel) observable).getErrorCheck() != null) {
            amountValue.setBackgroundColor(ContextCompat.getColor(PaymentAmountActivity.this,R.color.orange));
        } else {
            amountValue.setBackgroundColor(ContextCompat.getColor(PaymentAmountActivity.this,R.color.transparent));
        }
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        if(member_id < 0 || supplyDemand_id < 0){
            Toast.makeText(this,getString(R.string.error_data),Toast.LENGTH_LONG).show();
        }else {
            paymentAmountModel.onCreate(daoFactory, member_id,supplyDemand_id);
        }
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
