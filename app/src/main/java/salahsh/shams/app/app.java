package salahsh.shams.app;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import salahsh.shams.R;

public class app {

    public static class main {
        public static final String TAG = "utmconverter";



    }
    public static void Ctoast(String message) {
        TextView cutomtoasttxt;
        View view = LayoutInflater.from(application.getContext()).inflate(R.layout.cusotm_toast, null, false);
        cutomtoasttxt = view.findViewById(R.id.cutomtoasttxt);
        cutomtoasttxt.setText(message);
        final Toast toast = new Toast(application.getContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();


    }
    public static void t(String message) {

        Toast.makeText(application.getContext(), message, Toast.LENGTH_LONG).show();


    }


       }

