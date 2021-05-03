package salahsh.shams.app.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import salahsh.shams.R;
import salahsh.shams.shareApk;

public class share extends Fragment {
    public share() {
        // Required empty public constructor
    }


    ImageView shareappimg;
    shareApk shareApkk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_share, container, false);


        shareappimg=view.findViewById(R.id.shareappimg);
        shareappimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApkk=new shareApk();
                shareApk.shareAPK(requireActivity());

            }
        });

        return view;
    }
}
