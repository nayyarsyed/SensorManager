package com.example.sensormanager;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


@RequiresApi(api = Build.VERSION_CODES.N)
public class memory_info_frag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       return inflater.inflate( R.layout.fragment_memory_info, container, false );

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated( savedInstanceState );


        ProgressBar pb = getActivity().findViewById( R.id.progressBar );
         ActivityManager actManager;
        actManager = (ActivityManager) getActivity().getSystemService( getActivity().ACTIVITY_SERVICE );
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo( memInfo );
        long totalMemory = memInfo.totalMem;
        long availableMemory = memInfo.availMem;
        long convertbytes_to_gb = 1073741824;
        double totalMemory_in_gb = ((double) totalMemory / convertbytes_to_gb);
        String formated_total_memory = String.format( "%.2f", totalMemory_in_gb );
        double availableMemory_in_gb = ((double) availableMemory / convertbytes_to_gb);
        String formated_avl_mem = String.format( "%.2f", availableMemory_in_gb );

//        String details = "Total Memory : " + formated_total_memory + " Gb"
//                + "\nAvailable Memory : " + formated_avl_mem + " Gb";
        TextView jtm = getActivity().findViewById( R.id.tm );
        jtm.setText( formated_total_memory );
        TextView jam = getActivity().findViewById( R.id.am );
        jam.setText( formated_avl_mem );
        pb.setMax((int)totalMemory_in_gb);
        pb.setProgress( (int)availableMemory_in_gb );

    }
}
