package com.example.sensormanager;

import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class memory_info_frag extends Fragment {

    public PieChart pc;
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

        //another way to check ram shorten the code further

//        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
//        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
//        activityManager.getMemoryInfo(mi);
//        double availableMegs = mi.availMem / 0x100000L;
//        //Percentage can be calculated for API 16+
//        double percentAvail = mi.availMem / (double)mi.totalMem * 100.0;
//        Log.d("mem", "memory" + String.valueOf( availableMegs ) + "precent % " +percentAvail );


        //Actually implemented in this app
        ActivityManager actManager;
//        TextView jtm = getActivity().findViewById( R.id.tm );
//        TextView jam = getActivity().findViewById( R.id.am );
//        ProgressBar pb = getActivity().findViewById( R.id.progressBar );
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager = (ActivityManager) getActivity().getSystemService( getActivity().ACTIVITY_SERVICE );
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
//
//
//
//        //==============================
//        jtm.setText( "Total Memory as of now        : " + formated_total_memory +"Giga-bytes");
//        jam.setText( "Available Memory as of now    : " +formated_avl_mem +"Giga-bytes");
//        pb.setMax((int)totalMemory_in_gb);
//        pb.setProgress( (int)availableMemory_in_gb );
//=====================================================
        pc= getView().findViewById (R.id.xpc );
        pc.setUsePercentValues(  true );
        pc.getDescription().setEnabled( false );

        pc.setExtraOffsets( 5,10,5,5 );
        pc.setDragDecelerationFrictionCoef( 0.5f );

        pc.setDrawHoleEnabled( true );
//        pc.setHoleColor( Color.RED );
        pc.setTransparentCircleRadius( 31f );

        ArrayList<PieEntry> vals = new ArrayList<>( );
        vals.add (new PieEntry( Float.parseFloat( formated_total_memory ),"Total Ram"));
        vals.add (new PieEntry( Float.parseFloat( formated_avl_mem ),"Available Ram"));

        PieDataSet dataSet = new PieDataSet( vals,"Memory" );
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift( 5f );
        dataSet.setColors( ColorTemplate.COLORFUL_COLORS );

        PieData data = new PieData( dataSet );
        data.setValueTextSize( 5f );
        data.setValueTextColor( Color.BLUE );

        pc.setData( data );


    }

}
