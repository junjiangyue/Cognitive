package com.example.cognitive.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.cognitive.Bean.Contacts;
import com.example.cognitive.R;
import com.lucasurbas.listitemview.ListItemView;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends BaseAdapter {
    private List<Contacts> data = new ArrayList<Contacts>();//联系人列表集合

    private Context context;
    private LayoutInflater layoutInflater;

    public ContactsAdapter(Context context, List<Contacts> data) {
        this.data = data;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size(); //返回列表的长度
    }

    @Override
    public Contacts getItem(int position) {
        return data.get(position); //通过列表的位置 获得集合中的对象
    }

    @Override
    public long getItemId(int position) { // 获得集合的Item的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_contacts, null);//找到布局文件
            convertView.setTag(new ContactsAdapter.ViewHolder(convertView));
        }
        initViews(getItem(position), (ContactsAdapter.ViewHolder) convertView.getTag());
        return convertView;

    }

    private void initViews(Contacts data, ContactsAdapter.ViewHolder holder) {//初始化数据
        holder.myFamily.setTitle(data.getUserName());
        holder.myFamily.setSubtitle(data.getUserPhone()+data.getUserSex()+data.getUserBirth());
    }

    protected class ViewHolder {
        private ListItemView myFamily;

        public ViewHolder(View view) {
            myFamily = (ListItemView) view.findViewById(R.id.family);
        }
    }
}
