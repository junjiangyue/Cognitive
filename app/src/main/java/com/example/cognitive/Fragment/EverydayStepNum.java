package com.example.cognitive.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cognitive.R;

public class EverydayStepNum extends Fragment {
    //Resources resourse = this.getResources();
    //String[] data = resourse.getStringArray(R.array.step);
    String[] data = { "11月9号            12345步", "11月8号            11101步", "11月7号            9666步", "11月6号            8574步",
            "11月5号            12561步", "11月4号            14567步", "11月3号            7333步", "11月2号            8641步", "11月1号            11111步"};
    private ListView listView;
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.everyday_step_num,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_expandable_list_item_1,data
        );
        listView =view.findViewById(R.id.step_list);
        listView.setAdapter(adapter);

    }

}
