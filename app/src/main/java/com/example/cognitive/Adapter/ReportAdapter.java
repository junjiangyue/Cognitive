package com.example.cognitive.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cognitive.Bean.History;
import com.example.cognitive.Bean.Report;
import com.example.cognitive.R;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends BaseAdapter {
    private List<Report> data = new ArrayList<Report>();//联系人列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    public ReportAdapter(Context context, List<Report> data) {
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Report getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_report_list, null);//找到布局文件
            convertView.setTag(new ReportAdapter.ViewHolder(convertView));
        }
        initViews(getItem(position), (ReportAdapter.ViewHolder) convertView.getTag());
        return convertView;
    }
    @SuppressLint("SetTextI18n")
    private void initViews(Report data, ReportAdapter.ViewHolder holder) {//初始化数据
        holder.date.setText(data.getBeginDate()+" ~ "+data.getEndDate());
        holder.history_score.setText(data.getDailyCheck());
        holder.history_datetime.setText(data.getSportCheck());
    }

    protected class ViewHolder {
        private TextView date;
        private TextView history_score;
        private TextView history_datetime;
        public ViewHolder(View view) {
            date = view.findViewById(R.id.date);
            history_score = view.findViewById(R.id.dailyCheck);
            history_datetime = view.findViewById(R.id.sportCheck);

        }
    }
}
