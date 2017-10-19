package nsy209.cnam.seldesave.activity.geolocation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import nsy209.cnam.seldesave.R;
import nsy209.cnam.seldesave.activity.home.HomeActivity;
import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.bean.GeolocationBean;
import nsy209.cnam.seldesave.dao.DaoFactory;

public class GeolocationMemberActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    /* daoFactory */
    private DaoFactory daoFactory;

    /* map */
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocation_member);

        /* database connexion */
        daoFactory = DaoFactory.getInstance(this);
        daoFactory.open();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        /* get geolocation to display */
        GeolocationBean geolocationMember = getIntent().getParcelableExtra(ActivityConstant.KEY_INTENT_GEOLOCATION_NAME);
        GeolocationBean geolocationMe = daoFactory.getMemberDao().getGeolocation(daoFactory.getMemberDao().getMemberByRemoteId(
                daoFactory.getMyProfileDao().getMyProfile().getRemote_id()).getId());

        /* add corresponding marker */
        LatLng member = new LatLng(geolocationMember.getLatitude(),geolocationMember.getLongitude());
        LatLng me = new LatLng(geolocationMe.getLatitude(),geolocationMe.getLongitude());
        Marker markerMember = mMap.addMarker(new MarkerOptions().position(member).title(daoFactory.getMemberDao().getMember(geolocationMember.getMemberId()).getForname()));
        markerMember.setTag(geolocationMember.getMemberId());
        markerMember.showInfoWindow();

        Marker markerMe = mMap.addMarker(new MarkerOptions().position(me).title(daoFactory.getMyProfileDao().getMyProfile().getForname())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        markerMe.setTag(geolocationMe.getMemberId());

        /* center camera on me and zoom on member */
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me,10));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        /* Retrieve the data from the marker */
        Long memberId = (Long) marker.getTag();
        if(!marker.getTitle().equals(daoFactory.getMyProfileDao().getMyProfile().getForname())) {
        /* display member */
            Intent intent = new Intent(this, AMemberDisplayActivity.class);
            intent.putExtra(ActivityConstant.KEY_INTENT_ID, memberId);
            startActivity(intent);
            finish();

            return true;
        } else {

            /* return default behaviour */
            return false;
        }
    }

    @Override
    protected void onResume() {
        daoFactory.open();
        super.onResume();
        HomeActivity.isInForeGround = false;
    }

    @Override
    protected void onPause() {
        daoFactory.close();
        super.onPause();
        HomeActivity.isInForeGround = true;
    }
}
