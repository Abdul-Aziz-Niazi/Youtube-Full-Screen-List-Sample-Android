package com.aziz.youtube_sample.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.List;


public abstract class RecyclerViewBaseAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected List<T> list;
    @Nullable
    protected ItemSelectedListener<T> itemSelectedListener;

    public RecyclerViewBaseAdapter(List<T> items, @Nullable ItemSelectedListener<T> itemSelectedListener) {
        list = items;
        this.itemSelectedListener = itemSelectedListener;
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public T getItem(int position) {
        int size = list.size();
        boolean isOk = position >= 0 && position < size;
        return isOk ? list.get(position) : null;
    }

    public void addItem(T item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
    }

    public void addItem(int position, T item) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void addItems(List<T> list) {
        addItem(getItemCount(), list);
    }

    public void addReverseItems(List<T> list) {
        addItem(0, list);
    }

    public void addItem(int position, List<T> list) {
        this.list.addAll(position, list);
        notifyItemRangeInserted(position, list.size());
    }

    public List<T> getList() {
        return list;
    }

    public int updateItem(T updateItem) {
        for (int i = 0; i < list.size(); i++) {
            T item = list.get(i);
            if (item.equals(updateItem)) {
                list.remove(i);
                list.add(i, updateItem);
                notifyItemChanged(i);
                return i;
            }
        }
        return -1;
    }

    public void clearList() {
        list.clear();
        notifyDataSetChanged();
    }

    public int getPosition(T item) {
        if (list.size() == 0) return -1;

        int i = 0;
        while (!list.get(i).equals(item)) {
            i++;
        }

        return i;
    }

    public void moveToFirst(T item) {
        int pos = getPosition(item);
        list.remove(pos);
        list.add(0, item);
        notifyItemMoved(pos, 0);
        notifyItemChanged(0);
    }

    public int removeItem(T item) {
        int pos = getPosition(item);
        list.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
        return pos;
    }

    public void setItemSelectedListener(@Nullable ItemSelectedListener<T> itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public interface ItemSelectedListener<T> {
        void onItemSelected(T listItem);
    }
}

