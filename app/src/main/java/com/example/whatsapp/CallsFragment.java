package com.example.whatsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CallsFragment extends Fragment {

    View callsFragmentView;
    RecyclerView recyclerViewCalls;

    ArrayList<CallsModel> arrayList = new ArrayList<>();
    public CallsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        callsFragmentView =  inflater.inflate(R.layout.fragment_calls, container, false);

        recyclerViewCalls = callsFragmentView.findViewById(R.id.recyclerViewCalls);
        recyclerViewCalls.setLayoutManager(new LinearLayoutManager(getActivity()));

        arrayList.clear();
        arrayList.add(new CallsModel(R.drawable.whatsapp_avatar, "Abhishek More", R.drawable.call_arrow_outward_missed, "8 February, 5:30 pm", R.drawable.call_video_logo));
        arrayList.add(new CallsModel(R.drawable.whatsapp_avatar, "Saish Thorat", R.drawable.call_arrow_outward_received, "8 February, 5:30 pm", R.drawable.voice_call_logo));
        arrayList.add(new CallsModel(R.drawable.whatsapp_avatar, "Darshan", R.drawable.call_arrow_outward_received, "20 February, 2:30 pm", R.drawable.call_video_logo));
        arrayList.add(new CallsModel(R.drawable.whatsapp_avatar, "Sandip Zalte", R.drawable.call_arrow_outward_missed, "8 February, 5:30 pm", R.drawable.call_video_logo));
        arrayList.add(new CallsModel(R.drawable.whatsapp_avatar, "Abhishek More", R.drawable.call_arrow_outward_missed, "8 February, 5:30 pm", R.drawable.call_video_logo));
        arrayList.add(new CallsModel(R.drawable.whatsapp_avatar, "Saish Thorat", R.drawable.call_arrow_outward_received, "8 February, 5:30 pm", R.drawable.voice_call_logo));
        arrayList.add(new CallsModel(R.drawable.whatsapp_avatar, "Darshan", R.drawable.call_arrow_outward_received, "20 February, 2:30 pm", R.drawable.call_video_logo));
        arrayList.add(new CallsModel(R.drawable.whatsapp_avatar, "Sandip Zalte", R.drawable.call_arrow_outward_missed, "8 February, 5:30 pm", R.drawable.call_video_logo));

        RecyclerCallsAdapter adapter = new RecyclerCallsAdapter(getActivity(), arrayList);
        recyclerViewCalls.setAdapter(adapter);

        return callsFragmentView;
    }
}