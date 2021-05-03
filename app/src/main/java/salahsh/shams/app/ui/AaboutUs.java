package salahsh.shams.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import salahsh.shams.R;
import salahsh.shams.app.app;

public class AaboutUs extends Fragment {
    public AaboutUs() {
        // Required empty public constructor
    }
    ImageView myimg;
    TextView email,website;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_aabout_us, container, false);

        myimg=view.findViewById(R.id.myimg);
        email=view.findViewById(R.id.email);
        website=view.findViewById(R.id.website);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"salahshms@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "در باره نرم افزار تبدیل UTM");
                i.putExtra(Intent.EXTRA_TEXT   , "سلام من ...");
                try {

                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    app.t("There are no email clients installed.");
                }
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://salahshams.ir"));
                startActivity(intent);
            }
        });



        return view;
    }
}
