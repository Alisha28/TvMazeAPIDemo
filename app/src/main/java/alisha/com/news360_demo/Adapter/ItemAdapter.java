package alisha.com.news360_demo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import alisha.com.news360_demo.Activity.EpisodeWebActivity;
import alisha.com.news360_demo.Model.Item;
import alisha.com.news360_demo.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

        private List<Item> itemList;
        public Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageView img;
            RelativeTimeTextView dt;
            
            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                img = (ImageView) view.findViewById(R.id.img);
                dt = (RelativeTimeTextView) view.findViewById(R.id.dt);
            }
        }
        public ItemAdapter(List<Item> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Item item = itemList.get(position);
            String getDate = item.getDt();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
            Date date = null;
            try {
                date = sdf.parse(getDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //setting date as required format ex: 2 days ago
            holder.dt.setReferenceTime(date.getTime());
            holder.title.setText(item.getName());
            //load image
            Glide.with(context).load(item.getImg().getMedium())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EpisodeWebActivity.class);
                    i.putExtra("url", item.getUrl());
                    i.putExtra("name", item.getName());
                    context.startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

}
