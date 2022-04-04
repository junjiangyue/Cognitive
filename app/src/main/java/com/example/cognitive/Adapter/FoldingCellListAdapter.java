package com.example.cognitive.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cognitive.Bean.AdviceItem;
import com.example.cognitive.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends ArrayAdapter<AdviceItem> {

    private final HashSet<Integer> unfoldedIndexes = new HashSet<>();

    public FoldingCellListAdapter(Context context, List<AdviceItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        AdviceItem item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.attribute = cell.findViewById(R.id.attribute);
            viewHolder.advice=cell.findViewById(R.id.cell_advice);
            viewHolder.level=cell.findViewById(R.id.level);
            viewHolder.contentAttribute=cell.findViewById(R.id.content_attribute);
            viewHolder.contentLevel=cell.findViewById(R.id.content_level);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        // bind data from selected element to view through view holder
        viewHolder.attribute.setText(item.getAttribute());
        viewHolder.advice.setText(item.getAdvice());
        viewHolder.level.setText(item.getLevel());
        viewHolder.contentLevel.setText(item.getLevel());
        viewHolder.contentAttribute.setText(item.getAttribute());
        if(item.getLevel().equals("正常"))
        {
            viewHolder.level.setTextColor(Color.parseColor("#32CD32"));
            viewHolder.contentLevel.setTextColor(Color.parseColor("#32CD32"));
        }
        else if(item.getLevel().equals("极低"))
        {
            viewHolder.level.setTextColor(Color.parseColor("#FF0000"));
            viewHolder.contentLevel.setTextColor(Color.parseColor("#FF0000"));
        }
        else
        {
            viewHolder.level.setTextColor(Color.parseColor("#FFA500"));
            viewHolder.contentLevel.setTextColor(Color.parseColor("#FFA500"));
        }

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }


    // View lookup cache
    private static class ViewHolder {
        TextView attribute;
        TextView advice;
        TextView level;
        TextView contentAttribute;
        TextView contentLevel;
    }
}
