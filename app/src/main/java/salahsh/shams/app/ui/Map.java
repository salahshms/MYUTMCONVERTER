package salahsh.shams.app.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.Objects;

import salahsh.shams.Deg2UTM;
import salahsh.shams.R;
import salahsh.shams.app.app;
import salahsh.shams.app.spref;

public class Map extends Fragment implements View.OnClickListener, LocationListener {
    double lat, lng;
    float zoom = 18;
    TextView utm_x, utm_y, degree_x, degree_y, txt_address, acuracy, alretwakegps;
    ImageView copy_utm_x, copy_utm_y, copy_degree_x, copy_degree_y, infoimage;
    Button copyxandyutm, copyxandydegree, copyaddress;
    Deg2UTM deg2UTM;
    public double utmx, degy, utmy, degx;
    private MapView osm;
    private MapController mc;
    private LocationManager locationManager;
    FloatingActionButton goloction;

    spref spreff;
    static String tmpaddress;
    String totaladddress, Holder;


    public Map() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_map, container, false);


        utm_x = view.findViewById(R.id.utmE);
        utm_y = view.findViewById(R.id.utmN);
        degree_x = view.findViewById(R.id.degreex);
        degree_y = view.findViewById(R.id.degreey);
        copyxandydegree = view.findViewById(R.id.copydegree);
        copyaddress = view.findViewById(R.id.copyaddress);
        copy_utm_x = view.findViewById(R.id.copyutme);
        copy_utm_y = view.findViewById(R.id.copyutmn);
        copy_degree_x = view.findViewById(R.id.copydegreex);
        copy_degree_y = view.findViewById(R.id.copydegreey);
        copyxandyutm = view.findViewById(R.id.copyutm);
        txt_address = view.findViewById(R.id.txt_address);
        acuracy = view.findViewById(R.id.acuracy);
        alretwakegps = view.findViewById(R.id.alretwakegps);
        infoimage = view.findViewById(R.id.infoimage);
        acuracy.bringToFront();
        alretwakegps.bringToFront();


        copyxandydegree.setOnClickListener(this);
        copyaddress.setOnClickListener(this);
        copy_utm_x.setOnClickListener(this);
        copy_utm_y.setOnClickListener(this);
        copy_degree_x.setOnClickListener(this);
        copy_degree_y.setOnClickListener(this);
        copyxandyutm.setOnClickListener(this);


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spreff = new spref();

        if (isGpsON(requireActivity())) {
            buildAlertMessageNoGps(requireContext());
        }
        osm = view.findViewById(R.id.mainMap);
        goloction = view.findViewById(R.id.goloction);
        lng = 46.1745500;
        lat = 35.521573692;
        setAddrAndGotoLocation(lat, lng);


        Context ctx = requireContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        osm.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        osm.setBuiltInZoomControls(true);
        osm.setMultiTouchControls(true);
        osm.computeScroll();
        final View perDialog = LayoutInflater.from(requireContext()).inflate(R.layout.askpermission_dialog, null, false);


        //----------------------------------------------------------------------------------------------------
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setView(perDialog);
            final AlertDialog dialog = builder.create();
            Button agree, disagree;
            agree = perDialog.findViewById(R.id.agree);
            disagree = perDialog.findViewById(R.id.disagree);

            agree.setOnClickListener(v -> {
                dialog.dismiss();
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 150);

            });
            disagree.setOnClickListener(v -> dialog.dismiss());
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        } else {
            {

                final Criteria criteria = new Criteria();
                try {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                Holder = locationManager.getBestProvider(criteria, false);
                mc = (MapController) osm.getController();
                mc.setZoom(15);

                osm.setMapListener(new DelayedMapListener(new MapListener() {
                    public boolean onZoom(final ZoomEvent e) {
                        //do something
                        return true;
                    }

                    @SuppressLint("SetTextI18n")
                    public boolean onScroll(final ScrollEvent e) {
                        deg2UTM = new Deg2UTM(e.getSource().getMapCenter().getLatitude(), e.getSource().getMapCenter().getLongitude());
                        GeoPoint geoPoint = new GeoPoint(e.getSource().getMapCenter().getLatitude(), e.getSource().getMapCenter().getLongitude());
                        addMarker(geoPoint);
                        degx = e.getSource().getMapCenter().getLatitude();
                        degy = e.getSource().getMapCenter().getLongitude();

                        degree_x.setText(degx + "   Lat");
                        degree_y.setText(degy + "   Long");
                        utmx = deg2UTM.Northing;
                        utmy = deg2UTM.Easting;
                        utm_x.setText(utmx + "   N");
                        utm_y.setText(utmy + "   E");
                        lat = e.getSource().getMapCenter().getLatitude();
                        lng = e.getSource().getMapCenter().getLongitude();
                        spreff.save(spref.dashbord_tbl.UTM_X, String.valueOf(e.getSource().getMapCenter().getLatitude()));
                        spreff.save(spref.dashbord_tbl.UTM_Y, String.valueOf(osm.getMapCenter().getLongitude()));
                        setAddrAndGotoLocation(e.getSource().getMapCenter().getLatitude(), e.getSource().getMapCenter().getLongitude());


                        return true;
                    }
                }, 1000));

                osm.setMapListener(new MapListener() {
                    @Override
                    public boolean onScroll(final ScrollEvent event) {

                        GeoPoint geoPoint = new GeoPoint(event.getSource().getMapCenter().getLatitude(), event.getSource().getMapCenter().getLongitude());
                        addMarker(geoPoint);

                        return false;
                    }

                    @Override
                    public boolean onZoom(ZoomEvent event) {
                        return false;
                    }

                });


            }
        }


        //----------------------------------------------------------------------------------------------------

        goloction.setOnClickListener(view1 -> {
            goloc();

        });
        goloc();

    }

    @SuppressLint("SetTextI18n")
    public void goloc() {
        final FusedLocationProviderClient fusedLocationClient;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 150);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                try {
                    acuracy.setText(" دقت : " + location.getAccuracy() / 100);
                    if ((location.getAccuracy() / 100) > 8) {
                        alretwakegps.setVisibility(View.VISIBLE);
                    } else {
                        alretwakegps.setVisibility(View.GONE);
                    }
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    spreff.save("lat :", String.valueOf(lat));
                    spreff.save("lat :", String.valueOf(lat));

                    setAddrAndGotoLocation(lat, lng);
                } catch (Exception e) {
                    e.printStackTrace();

                }

            });
        }

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == 150) {

            final Criteria criteria = new Criteria();
            try {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } catch (Exception e) {
                e.printStackTrace();
            }


            Holder = locationManager.getBestProvider(criteria, false);
            mc = (MapController) osm.getController();
            mc.setZoom(15);

            osm.setMapListener(new DelayedMapListener(new MapListener() {
                public boolean onZoom(final ZoomEvent e) {
                    //do something
                    return true;
                }

                @SuppressLint("SetTextI18n")
                public boolean onScroll(final ScrollEvent e) {
                    deg2UTM = new Deg2UTM(e.getSource().getMapCenter().getLatitude(), e.getSource().getMapCenter().getLongitude());
                    GeoPoint geoPoint = new GeoPoint(e.getSource().getMapCenter().getLatitude(), e.getSource().getMapCenter().getLongitude());
                    addMarker(geoPoint);
                    degx = e.getSource().getMapCenter().getLatitude();
                    degy = e.getSource().getMapCenter().getLongitude();
                    degree_x.setText(degx + "   Lat");
                    degree_y.setText(degy + "   Long");
                    utmx = deg2UTM.Northing;
                    utmy = deg2UTM.Easting;
                    utm_x.setText(utmx + "   N");
                    utm_y.setText(utmy + "   E");
                    lat = e.getSource().getMapCenter().getLatitude();
                    lng = e.getSource().getMapCenter().getLongitude();
                    spreff.save(spref.dashbord_tbl.UTM_X, String.valueOf(e.getSource().getMapCenter().getLatitude()));
                    spreff.save(spref.dashbord_tbl.UTM_Y, String.valueOf(osm.getMapCenter().getLongitude()));
                    setAddrAndGotoLocation(e.getSource().getMapCenter().getLatitude(), e.getSource().getMapCenter().getLongitude());


                    return true;
                }
            }, 1000));

            osm.setMapListener(new MapListener() {
                @Override
                public boolean onScroll(final ScrollEvent event) {

                    GeoPoint geoPoint = new GeoPoint(event.getSource().getMapCenter().getLatitude(), event.getSource().getMapCenter().getLongitude());
                    addMarker(geoPoint);
                    return false;
                }

                @Override
                public boolean onZoom(ZoomEvent event) {
                    return false;
                }

            });


        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addMarker(GeoPoint center) {
        try {
            org.osmdroid.views.overlay.Marker marker = new org.osmdroid.views.overlay.Marker(osm);
            marker.setPosition(center);
            marker.setAnchor(org.osmdroid.views.overlay.Marker.ANCHOR_CENTER, org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM);
            marker.setIcon(requireContext().getDrawable(R.drawable.ic_baseline_person_pin_circle_24));
            osm.getOverlays().clear();
            osm.getOverlays().add(marker);
            osm.invalidate();

            marker.setTitle("مکان فعلی شما");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAddrAndGotoLocation(double lat, double lng) {
        try {


            Criteria criteria = new Criteria();
            locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

            Holder = locationManager.getBestProvider(criteria, false);


            mc = (MapController) osm.getController();
            mc.setZoom(zoom);

            GeoPoint center = new GeoPoint(lat, lng);
            mc.animateTo(center);
            addMarker(center);


            String add = "https://nominatim.openstreetmap.org/reverse?format=geojson&lat=" + lat + "&lon=" + lng;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, add, response -> {

                String state = null;
                String display_name = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray ddd = jsonObject.getJSONArray("features");
                    JSONObject subfeaturre = ddd.getJSONObject(0);
                    JSONObject subproperties = subfeaturre.getJSONObject("properties");
                    JSONObject ddd1 = subproperties.getJSONObject("address");

                    try {
                        state = ddd1.getString("state");
                    } catch (Exception e) {
                        state = "";
                    }
                    try {
                        display_name = subproperties.getString("display_name");
                    } catch (Exception e) {
                        display_name = "";
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
                totaladddress = display_name;


                txt_address.setText(totaladddress);
                tmpaddress = totaladddress;

            }, error -> error.printStackTrace());
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copydegree:
                setClipboard(getContext(), lat + " X  - " + lng + "  Y");
                app.t("کپی شد");

                break;
            case R.id.copyutme:

                setClipboard(getContext(), utmx + "");
                app.t("کپی شد");

                break;
            case R.id.copyutmn:

                setClipboard(getContext(), utmy + "");
                app.t("کپی شد");
                break;
            case R.id.copydegreex:

                setClipboard(getContext(), degx + "");
                app.t("کپی شد");
                break;
            case R.id.copydegreey:

                setClipboard(getContext(), degy + "");
                app.t("کپی شد");
                break;
            case R.id.copyutm:
                setClipboard(getContext(), utmy + " E  - " + utmx + " N");
                app.t("کپی شد");

                break;
            case R.id.copyaddress:
                setClipboard(getContext(), totaladddress);
                app.t("کپی شد");

                break;
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }


    private void buildAlertMessageNoGps(final Context context) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.gpson_dialog, null, false);
        builder.setView(v);
        LottieAnimationView turnOnGPS = v.findViewById(R.id.turnOnGPS);
        LottieAnimationView cancelDialog = v.findViewById(R.id.cancelDialog);

        final android.app.AlertDialog alert = builder.create();
        turnOnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                alert.cancel();
            }
        });
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                app.Ctoast("به علت روشن نبودن GPS دستگاه قادر به تشخیص درست آدرس شما نیستیم");


            }
        });
        if (isGpsON(requireActivity())) alert.dismiss();
        alert.show();
    }


    public boolean isGpsON(Activity activity) {
        final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return !manager.isProviderEnabled(LocationManager.GPS_PROVIDER);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPause() {
        locationManager.removeUpdates(this);
        super.onPause();
    }
}
