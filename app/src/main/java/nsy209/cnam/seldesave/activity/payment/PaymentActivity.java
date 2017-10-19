package nsy209.cnam.seldesave.activity.payment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{

    /* to control history */
    public static Activity firstPaymentActivity;

    /* controllers */
    private Button payment;
    private Button paymentNFC;
    private Button receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        firstPaymentActivity = this;

        /* view & controller */
        payment = (Button) findViewById(R.id.Payment);
        payment.setOnClickListener(this);

        paymentNFC = (Button) findViewById(R.id.PaymentNFC);
        paymentNFC.setOnClickListener(this);

        receive = (Button) findViewById(R.id.receivePayment);
        receive.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == payment){
            Intent intent = new Intent(this, PaymentSupplyDemandActivity.class);
            startActivity(intent);
        } else if(view == paymentNFC){
            Intent intent = new Intent(this, PaymentSupplyDemandActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_NFC,true);
            startActivity(intent);
        } else if(view == receive){
            Intent intent = new Intent(this, PaymentByNFCActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
