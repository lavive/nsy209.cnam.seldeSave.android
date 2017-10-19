package nsy209.cnam.seldesave.activity.payment;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.PaymentByNFCModel;
import nsy209.cnam.seldesave.validator.helper.EnumField;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;

import static android.nfc.NfcAdapter.getDefaultAdapter;

public class PaymentByNFCActivity extends AppCompatActivity
        implements View.OnClickListener,Observer,NfcAdapter.OnNdefPushCompleteCallback {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* views */
    private TextView title;
    private TextView debtor;
    private TextView creditor;
    private TextView supplyDemand;
    private TextView amount;
    private TextView debtorTitle;
    private TextView creditorTitle;
    private TextView supplyDemandTitle;
    private TextView amountTitle;

    /* controller */
    private ImageButton back;

    /* model */
    private PaymentByNFCModel paymentByNFCModel;

    /* datas */
    private boolean payment;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_nfc);

        /* Check for available NFC Adapter*/
        mNfcAdapter = getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, getString(R.string.bad_nfc), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        /* views */
        title = (TextView)findViewById(R.id.titleNFC);
        debtor = (TextView)findViewById(R.id.debtorName);
        creditor = (TextView)findViewById(R.id.creditorName);
        supplyDemand = (TextView)findViewById(R.id.supplyDemandTitle);
        amount = (TextView)findViewById(R.id.amountValue);
        debtorTitle = (TextView)findViewById(R.id.debtor);
        creditorTitle = (TextView)findViewById(R.id.creditor);
        supplyDemandTitle = (TextView)findViewById(R.id.supplyDemandPayed);
        amountTitle = (TextView)findViewById(R.id.amount);

        /* controller */
        back = (ImageButton) findViewById(R.id.backHome);
        back.setOnClickListener(this);

        /* data */
        payment = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_NFC,false);

        /* model */
        paymentByNFCModel = new PaymentByNFCModel();
        paymentByNFCModel.addObserver(this);
    }

    @Override
    public void onClick(View view){
        if(view == back){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void update(Observable observable,Object object){
        /* update views */
        title.setText(((PaymentByNFCModel) observable).getTitle());

        if(((PaymentByNFCModel) observable).isPayment()){
            /* set views invisible */
            debtor.setVisibility(View.INVISIBLE);
            creditor.setVisibility(View.INVISIBLE);
            supplyDemand.setVisibility(View.INVISIBLE);
            amount.setVisibility(View.INVISIBLE);
            debtorTitle.setVisibility(View.INVISIBLE);
            creditorTitle.setVisibility(View.INVISIBLE);
            supplyDemandTitle.setVisibility(View.INVISIBLE);
            amountTitle.setVisibility(View.INVISIBLE);

            /* message to send: sender's id + service's id + amount to pay */
            String textToSend = daoFactory.getMyProfileDao().getMyProfile().getRemote_id()+ ActivityConstant.REGEX+
                    getIntent().getLongExtra(ActivityConstant.KEY_INTENT_SUPPLYDEMAND_ID,ActivityConstant.BAD_ID)+ActivityConstant.REGEX+
                    getIntent().getStringExtra(EnumField.AMOUNT.getWording());
            NdefRecord record = NdefRecord.createMime("text/plain", textToSend.getBytes());
            NdefMessage msg = new NdefMessage(record);
            mNfcAdapter.setNdefPushMessage(msg,this);

            /* Register callback to listen for message-sent success */
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);

        } else {
            /* display payment result */
            debtor.setText(((PaymentByNFCModel) observable).getDebtor());
            creditor.setText(((PaymentByNFCModel) observable).getCreditor());
            supplyDemand.setText(((PaymentByNFCModel) observable).getSupplyDemand());
            amount.setText(((PaymentByNFCModel) observable).getAmount());
        }

        if(((PaymentByNFCModel) observable).isVisibleButton()){
            back.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        paymentByNFCModel.onCreate(getString(R.string.wait_nfc),"","","","",false,payment);

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }

        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }

    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        /* return home menu */
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(ActivityConstant.KEY_INTENT_NFC,true);
        HomeActivity.firstHomeActivity.finish();

        startActivity(intent);
        finish();
    }

    void processIntent(Intent intent) {
        /* parse detected tag */
        Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(detectedTag);
        try {
            ndef.makeReadOnly();
        }catch(Exception e){
        }
        Parcelable[] rawMsgs =
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        String transfer ="";
        if (rawMsgs != null) {
            NdefMessage[] messages = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                messages[i] = (NdefMessage) rawMsgs[i];
                String str = new String(messages[i].getRecords()[0].getPayload());

                transfer += str;
            }
        }

        /* parse infos */
        String[] infos = transfer.split(ActivityConstant.REGEX);
        try {
            long debtorId = Long.parseLong(infos[0]);
            long supplyDemandId = Long.parseLong(infos[1]);
            BigDecimal amountTransaction = BigDecimal.valueOf(Double.parseDouble((infos[2])));

            /* transaction creation */
            TransactionBean transactionBean = new TransactionBean();
            MemberBean debtor = daoFactory.getMemberDao().getMember(debtorId);
            MemberBean creditor = MyProfileBean.myProfileToMember(daoFactory);
            SupplyDemandBean supplyDemand = daoFactory.getSupplyDemandDao().getSupplyDemand(supplyDemandId);

            transactionBean.setDebtor(debtor);
            transactionBean.setCreditor(creditor);
            transactionBean.setSupplyDemand(supplyDemand);
            transactionBean.setAmount(amountTransaction);

            daoFactory.getTransactionDao().addNewTransaction(transactionBean);

            /* update views */
            paymentByNFCModel.onCreate(getString(R.string.message_nfc),debtor.toString(),
                    creditor.toString(),supplyDemand.getTitle(),amountTransaction.toString(),true,false);

        } catch (Exception e){
            /* update views */
            paymentByNFCModel.onCreate(getString(R.string.error_nfc),"","","","",true,false);
        }


    }
}
