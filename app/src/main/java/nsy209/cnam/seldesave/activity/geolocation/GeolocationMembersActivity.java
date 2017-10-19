package nsy209.cnam.seldesave.activity.geolocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Observable;
import java.util.Observer;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.utils.Connected;
import nsy209.cnam.seldesave.bean.GeolocationBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.models.GeolocationMembersModel;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;


public class GeolocationMembersActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener,
        View.OnClickListener,Observer {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* controller */
    private Button memberDisplay;
    private Button membersDisplay;
    private Button filters;

    /* model */
    private GeolocationMembersModel geolocationMembersModel;

    /* check if apply filter */
    private boolean applyFilter;

    /* map */
    private GoogleMap mMap;

    /* marker tag selected */
    private long markerTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocation_members);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* view */
        membersDisplay = (Button) findViewById(R.id.membersDisplay);
        memberDisplay = (Button) findViewById(R.id.gotoMember);
        filters = (Button) findViewById(R.id.applyFilterButton);

        /* controller */
        membersDisplay.setOnClickListener(this);
        memberDisplay.setOnClickListener(this);
        filters.setOnClickListener(this);

        /* applyFilter */
        applyFilter = getIntent().getBooleanExtra(ActivityConstant.KEY_INTENT_FILTER,false);

        /* model */
        geolocationMembersModel = new GeolocationMembersModel();
        geolocationMembersModel.addObserver(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /* marker listener */
        mMap.setOnMarkerClickListener(this);

        /* check permission */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        /* add markers for each geolocationBean and move camera around */
        for(GeolocationBean geolocationBean:geolocationMembersModel.getGeolocationsBean()){
            if(daoFactory.getMemberDao().getMember(geolocationBean.getMemberId()).getRemote_id() !=
                    daoFactory.getMyProfileDao().getMyProfile().getRemote_id()) {
                LatLng member = new LatLng(geolocationBean.getLatitude(), geolocationBean.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(member).title(daoFactory.getMemberDao().getMember(geolocationBean.getMemberId()).getForname()));
                marker.setTag(geolocationBean.getMemberId());
            }
        }
        MemberBean memberMe = daoFactory.getMemberDao().getMemberByRemoteId(daoFactory.getMyProfileDao().getMyProfile().getRemote_id());
        GeolocationBean geolocationMe = daoFactory.getMemberDao().getGeolocation(memberMe.getId());
        LatLng me = new LatLng(geolocationMe.getLatitude(),geolocationMe.getLongitude());
        Marker markerMe = mMap.addMarker(new MarkerOptions().position(me).title(daoFactory.getMyProfileDao().getMyProfile().getForname())
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        markerMe.setTag(daoFactory.getMyProfileDao().getMyProfile().getId());
        markerMe.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me,10));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        /* get selected tag */
        if(marker.getTag() != null) {
            markerTag = (Long) marker.getTag();
        }

        /* return default behaviour */
        return false;
    }

    @Override
    public void onClick(View view){
        if(view == membersDisplay){
            /* display members list */
            Intent intent = new Intent(this, MembersDisplayActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_FILTER,applyFilter);
            startActivity(intent);
            finish();
        } else if(view == filters){
            /* apply or disable filters */
            applyFilter = !applyFilter;
            geolocationMembersModel.onCreate(this,daoFactory,applyFilter);
        } else if(view == memberDisplay){
            if(markerTag != daoFactory.getMyProfileDao().getMyProfile().getId()) {
                /* display member */
                Intent intent = new Intent(this, AMemberDisplayActivity.class);
                intent.putExtra(ActivityConstant.KEY_INTENT_ID, markerTag);
                startActivity(intent);
            }
        }
    }

    @Override
    public void update(Observable observable, Object object){
        /* update markers and view */
        if(mMap != null) {
            mMap.clear();
            onMapReady(mMap);
        }
        filters.setText(((GeolocationMembersModel) observable).getApplyFilter());
    }


    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();

        /* get geolocations */
        geolocationMembersModel.onCreate(this,daoFactory,applyFilter);

        /* draw attention to network connection default */
        if (!Connected.isConnectedToInternet(this)) {
            Toast.makeText(this,getString(R.string.no_connection_update_fail),Toast.LENGTH_LONG).show();
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
