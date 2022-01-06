package com.example.cognitive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.cognitive.Bean.History;
import com.example.cognitive.R;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    private List<History> data = new ArrayList<History>();//联系人列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    public HistoryAdapter(Context context, List<History> data) {
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public History getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_history, null);//找到布局文件
            convertView.setTag(new HistoryAdapter.ViewHolder(convertView));
        }
        initViews(getItem(position), (HistoryAdapter.ViewHolder) convertView.getTag());
        return convertView;
    }
    private void initViews(History data, HistoryAdapter.ViewHolder holder) {//初始化数据
        holder.history_name.setText(data.getTestName());
        holder.history_score.setText(data.getTestScore());
        holder.history_datetime.setText(data.getTestTime());

    }

    protected class ViewHolder {
        private TextView history_name;
        private TextView history_score;
        private TextView history_datetime;
        public ViewHolder(View view) {

            history_name = view.findViewById(R.id.history_name);
            history_score = view.findViewById(R.id.history_score);
            history_datetime = view.findViewById(R.id.history_datetime);
        }
    }
}
