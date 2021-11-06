package salahsh.shams.app.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import salahsh.shams.CoordinateConversion;
import salahsh.shams.R;
import salahsh.shams.app.app;
import salahsh.shams.app.spref;


public class homeFrag extends Fragment {

    TextView degree_x, degree_y;
    CoordinateConversion coordinateConversion;
    EditText latit, longit;
    Spinner convertionunts;

    ImageView copy_degree_x, copy_degree_y;
    Button copyxandydegree, calculate;
    double lat, lng;
    TextInputLayout lon, lati;

    public homeFrag() {
        // Required empty public constructor
    }

    spref spreff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        coordinateConversion = new CoordinateConversion();
        degree_x = view.findViewById(R.id.degreex);
        convertionunts = view.findViewById(R.id.convertionunts);
        degree_y = view.findViewById(R.id.degreey);
        copyxandydegree = view.findViewById(R.id.copydegree);
        copy_degree_x = view.findViewById(R.id.copydegreex);
        copy_degree_y = view.findViewById(R.id.copydegreey);
        longit = view.findViewById(R.id.longit);
        latit = view.findViewById(R.id.latit);
        calculate = view.findViewById(R.id.calculate);
        lon = view.findViewById(R.id.lon);
        lati = view.findViewById(R.id.la);
        convertionunts.requestFocus();
        spreff = new spref();

        try {
            calculate.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {

                    switch (convertionunts.getSelectedItemPosition()) {
                        case 0:
                            app.t("واحد را برای تبدیل انتخاب کنید ");

                            break;
                        case 1:
                            try {
                                lat = Double.parseDouble(latit.getText().toString());
                                lng = Double.parseDouble(longit.getText().toString());
                                degree_y.setText(coordinateConversion.latLon2MGRUTM(lat, lng));
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                                app.t(e.getMessage());
                            }

                            break;
                        case 2:
                            try {
                                double i = coordinateConversion.degreeToRadian(Double.parseDouble(longit.getText().toString()));
                                degree_y.setText(i + "");
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                app.t(e.getMessage());
                            }
                            break;
                        case 3:
                            try {
                                double[] mlatlng;
                                mlatlng = coordinateConversion.mgrutm2LatLon(longit.getText().toString());
                                degree_y.setText(mlatlng[0] + "");
                                degree_x.setText(mlatlng[1] + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                                app.t(e.getMessage());
                            }
                            break;
                        case 4:
                            try {
                                lat = Double.parseDouble(latit.getText().toString());
                                lng = Double.parseDouble(longit.getText().toString());
                                String u = coordinateConversion.latLon2UTM(lat, lng);
                                String[] a = u.split(" ");
                                degree_x.setText(a[2]);
                                degree_y.setText(a[3]);
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                                app.t(e.getMessage());
                            }
                            break;
                        case 5:

                            try {
                                double res = coordinateConversion.radianToDegree(Double.parseDouble(longit.getText().toString()));
                                degree_y.setText(res + "");
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                app.t(e.getMessage());
                            }

                            break;
                        case 6:

                            try {
                                double[] res1 = coordinateConversion.utm2LatLon(longit.getText().toString());
                                degree_y.setText(res1[1] + " Longitude");
                                degree_x.setText(res1[0] + " Latitude");
                            } catch (Exception e) {

                                e.printStackTrace();
                                app.t(e.getMessage());
                            }

                            break;

                        default:
                            throw new IllegalStateException("Unexpected value: " + convertionunts.getSelectedItemPosition());
                    }

                }
            });

        } catch (NumberFormatException | IllegalStateException e) {
            e.printStackTrace();
            app.t(e.getMessage());
        }
        longit.setText(spreff.getStr("long"));
        latit.setText(spreff.getStr("lat"));

        latit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                spreff.save("lat", latit.getText().toString());

            }
        });
        longit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                spreff.save("long", longit.getText().toString());
            }
        });
        longit.setSelectAllOnFocus(true);
        latit.setSelectAllOnFocus(true);
        convertionunts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        degree_x.setVisibility(View.GONE);
                        copy_degree_x.setVisibility(View.GONE);
                        break;
                    case 2:
                        lati.setVisibility(View.GONE);
                        degree_x.setVisibility(View.GONE);
                        copy_degree_x.setVisibility(View.GONE);
                        longit.setHint("Enter Degree");
                        lon.setHint("Enter Degree");
                        break;
                    case 3:
                        longit.setHint("Enter MGR UTM");
                        lon.setHint("Enter MGR UTM");
                        lati.setVisibility(View.GONE);
                        degree_x.setVisibility(View.VISIBLE);
                        copy_degree_x.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        lati.setVisibility(View.VISIBLE);
                        degree_x.setVisibility(View.VISIBLE);
                        copy_degree_x.setVisibility(View.VISIBLE);
                        longit.setHint("Enter UTM");
                        lon.setHint("Enter UTM");

                        break;
                    case 5:
                        lati.setVisibility(View.GONE);
                        degree_x.setVisibility(View.GONE);
                        copy_degree_x.setVisibility(View.GONE);
                        longit.setHint("Enter Radian");
                        lon.setHint("Enter Radian");

                        break;
                    case 6:
                        lati.setVisibility(View.GONE);
                        longit.setHint("Enter UTM");
                        lon.setHint("Enter UTM");
                        degree_x.setVisibility(View.VISIBLE);
                        copy_degree_x.setVisibility(View.VISIBLE);

                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private class Deg2UTM {
        double Easting;
        double Northing;
        int Zone;
        char Letter;

        private Deg2UTM(double Lat, double Lon) {
            Zone = (int) Math.floor(Lon / 6 + 31);
            if (Lat < -72)
                Letter = 'C';
            else if (Lat < -64)
                Letter = 'D';
            else if (Lat < -56)
                Letter = 'E';
            else if (Lat < -48)
                Letter = 'F';
            else if (Lat < -40)
                Letter = 'G';
            else if (Lat < -32)
                Letter = 'H';
            else if (Lat < -24)
                Letter = 'J';
            else if (Lat < -16)
                Letter = 'K';
            else if (Lat < -8)
                Letter = 'L';
            else if (Lat < 0)
                Letter = 'M';
            else if (Lat < 8)
                Letter = 'N';
            else if (Lat < 16)
                Letter = 'P';
            else if (Lat < 24)
                Letter = 'Q';
            else if (Lat < 32)
                Letter = 'R';
            else if (Lat < 40)
                Letter = 'S';
            else if (Lat < 48)
                Letter = 'T';
            else if (Lat < 56)
                Letter = 'U';
            else if (Lat < 64)
                Letter = 'V';
            else if (Lat < 72)
                Letter = 'W';
            else
                Letter = 'X';
            Easting = 0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) * 0.9996 * 6399593.62 / Math.pow((1 + Math.pow(0.0820944379, 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)), 0.5) * (1 + Math.pow(0.0820944379, 2) / 2 * Math.pow((0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)))), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2) / 3) + 500000;
            Easting = Math.round(Easting * 100) * 0.01;
            Northing = (Math.atan(Math.tan(Lat * Math.PI / 180) / Math.cos((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) - Lat * Math.PI / 180) * 0.9996 * 6399593.625 / Math.sqrt(1 + 0.006739496742 * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) * (1 + 0.006739496742 / 2 * Math.pow(0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)))), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) + 0.9996 * 6399593.625 * (Lat * Math.PI / 180 - 0.005054622556 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + 4.258201531e-05 * (3 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 4 - 1.674057895e-07 * (5 * (3 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 4 + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 3);
            if (Letter < 'M')
                Northing = Northing + 10000000;
            Northing = Math.round(Northing * 100) * 0.01;
        }
    }

    private class UTM2Deg {
        double latitude;
        double longitude;

        private UTM2Deg(String UTM) {
            String[] parts = UTM.split(" ");
            int Zone = Integer.parseInt(parts[0]);
            char Letter = parts[1].toUpperCase(Locale.ENGLISH).charAt(0);
            double Easting = Double.parseDouble(parts[2]);
            double Northing = Double.parseDouble(parts[3]);
            double Hem;
            if (Letter > 'M')
                Hem = 'N';
            else
                Hem = 'S';
            double north;
            if (Hem == 'S')
                north = Northing - 10000000;
            else
                north = Northing;
            latitude = (north / 6366197.724 / 0.9996 + (1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) - 0.006739496742 * Math.sin(north / 6366197.724 / 0.9996) * Math.cos(north / 6366197.724 / 0.9996) * (Math.atan(Math.cos(Math.atan((Math.exp((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3)) - Math.exp(-(Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3))) / 2 / Math.cos((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996))) * Math.tan((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996)) - north / 6366197.724 / 0.9996) * 3 / 2) * (Math.atan(Math.cos(Math.atan((Math.exp((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3)) - Math.exp(-(Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3))) / 2 / Math.cos((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996))) * Math.tan((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996)) - north / 6366197.724 / 0.9996)) * 180 / Math.PI;
            latitude = Math.round(latitude * 10000000);
            latitude = latitude / 10000000;
            longitude = Math.atan((Math.exp((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3)) - Math.exp(-(Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3))) / 2 / Math.cos((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996)) * 180 / Math.PI + Zone * 6 - 183;
            longitude = Math.round(longitude * 10000000);
            longitude = longitude / 10000000;
        }
    }
}
