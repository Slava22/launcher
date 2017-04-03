package com.example.cometlauncher.launcher;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cometlauncher.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.AppViewHolder>{

    private int heightCard;
    private Context mContext;
    private List<Application> apps;

    public class AppViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView appName;
        ImageView appImage;

        AppViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            appName = (TextView)itemView.findViewById(R.id.app_name);
            appImage = (ImageView)itemView.findViewById(R.id.app_image);

            System.out.println(heightCard);
            cv.getLayoutParams().height = heightCard;

            cv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    apps.get(getAdapterPosition()).setCount(apps.get(getAdapterPosition()).getCount() + 1);
                    Toast.makeText(mContext, appName.getText(), Toast.LENGTH_LONG).show();
                }
            });

            cv.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.inflate(R.menu.context_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.del:
                                    apps.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    notifyDataSetChanged();
                                    return true;
                                case R.id.info:
                                    Toast.makeText(mContext, appName.getText()+ " нажато " + apps.get(getAdapterPosition()).getCount() + " раз(а) ", Toast.LENGTH_LONG).show();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                    return true;
                }
            });
            //cv.getBackground().setAlpha(0);
        }
    }

    MyAdapter(List<Application> apps, int heightCard, Context mContext){
        this.apps = apps;
        this.heightCard = heightCard;
        this.mContext = mContext;
    }

    @Override
    public long getItemId(int position){
        return apps.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        AppViewHolder appViewHolder = new AppViewHolder(v);
        return appViewHolder;
    }

    @Override
    public void onBindViewHolder(AppViewHolder personViewHolder, int i) {
        personViewHolder.appName.setText(apps.get(i).name);
        personViewHolder.appImage.setImageResource(apps.get(i).image);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
