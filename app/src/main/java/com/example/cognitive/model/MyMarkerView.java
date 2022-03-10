package com.example.cognitive.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.example.cognitive.R;


public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private IAxisValueFormatter xAxisValueFormatter;

    public MyMarkerView(Context context) {
        super(context, R.layout.layout_markerview);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface) 每次 MarkerView 重绘此方法都会被调用，并为您提供更新它显示的内容的机会
    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        //这里就设置你想显示到makerview上的数据，Entry可以得到X、Y轴坐标，也可以e.getData()获取其他你设置的数据

        //xAxisValueFormatter.getFormattedValue(e.getX(),);
        tvContent.setText(""+Utils.formatNumber(e.getY(), 0, true));
        super.refreshContent(e, highlight);
    }


    /*
     * offset 是以點到的那個點作為 (0,0) 中心然後往右下角畫出來 该方法是让markerview现实到坐标的上方
     * 所以如果要顯示在點的上方
     * X=寬度的一半，負數
     * Y=高度的負數
     */
    @Override
    public MPPointF getOffset() {
        // Log.e("ddd", "width:" + (-(getWidth() / 2)) + "height:" + (-getHeight()));
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}