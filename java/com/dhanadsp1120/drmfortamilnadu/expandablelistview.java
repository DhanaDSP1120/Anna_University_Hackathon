package com.dhanadsp1120.drmfortamilnadu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class expandablelistview extends BaseExpandableListAdapter {
Context c;
List<String> list;
HashMap<String,List<String>> hashMap;

    public expandablelistview(Context c, List<String> list, HashMap<String, List<String>> hashMap) {
        this.c = c;
        this.list = list;
        this.hashMap = hashMap;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hashMap.get(list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hashMap.get(list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String  header=(String)getGroup(groupPosition);
        View  v= LayoutInflater.from(c).inflate(R.layout.listgroup,parent,false);
        TextView t=v.findViewById(R.id.listgrouptext);
        t.setText(header);
        return  v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String text=(String)getChild(groupPosition, childPosition);
        View  v= LayoutInflater.from(c).inflate(R.layout.listitems,parent,false);
        TextView t=v.findViewById(R.id.listitemtext);
        t.setText(text);
        return  v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
