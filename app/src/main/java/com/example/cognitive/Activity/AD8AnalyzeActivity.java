package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cognitive.Adapter.FoldingCellListAdapter;
import com.example.cognitive.Bean.AdviceItem;
import com.example.cognitive.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class AD8AnalyzeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad8_analyze);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用


        // get our list view
        ListView theListView = findViewById(R.id.mainListView);

        Intent intent=getIntent();
        int cognitionScore=intent.getIntExtra("cognition_point",0);
        int judgementScore=intent.getIntExtra("judgement_point",0);
        int memoryScore=intent.getIntExtra("memory_point",0);


        // prepare elements to display
        //final ArrayList<Item> items = Item.getTestingList();

        //        int strengthScore=1;
//        int cognitionScore=1;
//        int judgementScore=1;
//        int memoryScore=1;
        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        ArrayList<AdviceItem> items = new ArrayList<>();
        items.add(new AdviceItem("健康",0));
        items.add(new AdviceItem("体力",0));
        items.add(new AdviceItem("意识",cognitionScore));
        items.add(new AdviceItem("判断",judgementScore));
        items.add(new AdviceItem("记忆",memoryScore));
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items);


        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}


