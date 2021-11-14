package com.example.cognitive.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cognitive.R;

import java.util.List;


public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context mContext = null;
    private List<String> mGroupList = null;
    private List<List<String>> mItemList = null;
    public int[] status=new int[5];
    public int[] score=new int[5];




    public MyExpandableListViewAdapter(Context context, List<String> groupList,
                                       List<List<String>> itemList) {
        this.mContext = context;
        this.mGroupList = groupList;
        this.mItemList = itemList;
    }



    /**
     * 获取组的个数
     *
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }



    /**
     * 获取指定组中的子元素个数
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mItemList.get(groupPosition).size();
    }


    /**
     * 获取指定组中的数据
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public String getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }


    /**
     * 获取指定组中的指定子元素数据。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return mItemList.get(groupPosition).get(childPosition);
    }


    /**
     * 获取指定组的ID，这个组ID必须是唯一的
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    /**
     * 获取指定组中的指定子元素ID
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    /**
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象
     * @param parent        返回的视图对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, View,
     * ViewGroup)
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expandlist_group, null);

            groupHolder = new GroupHolder();
            groupHolder.groupNameTv = (TextView) convertView.findViewById(R.id.groupname_tv);
            Log.i("gwr", "getGroupView: "+Integer.toString(groupPosition));
            //groupHolder.groupImg = (ImageView) convertView.findViewById(R.id.group_img);
            convertView.setTag(groupHolder);
        }
        else
        {
            groupHolder = (GroupHolder) convertView.getTag();
        }

//        if (isExpanded) {
//            groupHolder.groupImg.setImageResource(R.drawable.group_img);
//        } else {
//            groupHolder.groupImg.setImageResource(R.drawable.group_open_two);
//        }

        groupHolder.groupNameTv.setText(mGroupList.get(groupPosition));

        return convertView;
    }


    /**
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView   重用已有的视图(View)对象
     * @param parent        返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View,
     * ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        int pos=groupPosition;
        if (convertView == null||status[groupPosition]!=1) {
            Log.i("gwr", "getChildView1: "+Integer.toString(groupPosition));
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expandlist_item, null);
            itemHolder = new ItemHolder();
            itemHolder.pos=groupPosition;
        }
        else
        {
            Log.i("gwr", "getChildView2: "+Integer.toString(groupPosition));
            itemHolder = (ItemHolder) convertView.getTag();
        }
        ItemHolder finalItemHolder = itemHolder;
        itemHolder.nameTv = (TextView) convertView.findViewById(R.id.itemname_tv);
        itemHolder.itemBlock=(LinearLayout)convertView.findViewById(R.id.item_block);
        itemHolder.yesButton=(Button) convertView.findViewById(R.id.yes_button);
        itemHolder.noButton=(Button) convertView.findViewById(R.id.no_button);
        itemHolder.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("gwr", "onClick:yes-"+Integer.toString(groupPosition+1)+" "+Integer.toString(status[groupPosition]));
                finalItemHolder.yesButton.setBackgroundResource(R.drawable.white_button_chosen);
                if(status[pos]==1)
                    finalItemHolder.noButton.setBackgroundResource(R.drawable.white_button);
                status[pos]=1;
                score[pos]=1;
            }
        });
        itemHolder.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("gwr", "onClick:no-"+Integer.toString(groupPosition+1));
                finalItemHolder.noButton.setBackgroundResource(R.drawable.white_button_chosen);
                if(status[pos]==1)
                    finalItemHolder.yesButton.setBackgroundResource(R.drawable.white_button);
                status[pos]=1;
                score[pos]=0;
            }
        });

        convertView.setTag(itemHolder);

        itemHolder.nameTv.setText(mItemList.get(groupPosition).get(childPosition));
        //itemHolder.iconImg.setBackgroundResource(R.drawable.group_img);
        return convertView;
    }

    public boolean judge(int[] array)
    {
        int res=0;
        for(int i=0;i<5;i++)
        {
            res+=array[i];
        }
        return res == 5;
    }


    /**
     * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     *
     * @return
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 是否选中指定位置上的子元素。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    class GroupHolder {
        public TextView groupNameTv;
       // public ImageView groupImg;
    }


    class ItemHolder {
        //public ImageView iconImg;
        public TextView nameTv;
        public LinearLayout itemBlock;
        public Button yesButton;
        public Button noButton;
        public int pos;

    }
}