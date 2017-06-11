package com.shmily.tjz.swap.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shmily.tjz.swap.Gson.NumberResult;
import com.shmily.tjz.swap.Gson.Shoes;
import com.shmily.tjz.swap.R;
import com.shmily.tjz.swap.Utils.Xutils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shmily_Z on 2017/6/11.
 */

public class CollectAdapter extends SwipeMenuAdapter<CollectAdapter.ViewHolder> {


    private Context mContext;

    private List<Shoes> mShoesList = new ArrayList<>();
    String username;
    private String loveurl = "http://www.shmilyz.com/ForAndroidHttp/love.action";
    private Xutils xutils;

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.collect_item, parent, false);

        return view;
    }

    @Override
    public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {


        return new ViewHolder(realContentView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView collect_image;
        TextView collect_shoesname,coolect_coast;

        public ViewHolder(View view) {

            super(view);
            collect_image= (ImageView) view.findViewById(R.id.collect_image);
            collect_shoesname= (TextView) view.findViewById(R.id.collect_shoesname);
            coolect_coast= (TextView) view.findViewById(R.id.coolect_coast);


        }
    }

    public CollectAdapter(List<Shoes> shoesList) {
        mShoesList = shoesList;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Shoes number = mShoesList.get(position);
        holder.collect_shoesname.setText(number.getBiaoti());
        holder.coolect_coast.setText("¥"+String.valueOf(number.getPrice()));
        Glide.with(mContext).load(number.getPicture()).into(holder.collect_image);



    }

    @Override
    public int getItemCount() {
        return mShoesList.size();
    }
}

