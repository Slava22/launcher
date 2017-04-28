package com.example.cometlauncher.launcher.Fragments.FavouritesFragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cometlauncher.Application;
import com.example.cometlauncher.R;

import java.util.List;

public class RecyclerViewFavouritesFragmentAdapter extends RecyclerView.Adapter<RecyclerViewFavouritesFragmentAdapter.AppViewHolder> {
    private static final String SCHEME = "package";
    private int heightCard;
    private Context mContext;
    private List<Application> apps;
    private PackageManager packageManager = null;

    public RecyclerViewFavouritesFragmentAdapter(List<Application> apps, int heightCard, Context mContext, PackageManager packageManager) {
        this.apps = apps;
        this.heightCard = heightCard;
        this.mContext = mContext;
        this.packageManager = packageManager;
    }

    @Override
    public long getItemId(int position) {
        return apps.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public RecyclerViewFavouritesFragmentAdapter.AppViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        RecyclerViewFavouritesFragmentAdapter.AppViewHolder appViewHolder = new RecyclerViewFavouritesFragmentAdapter.AppViewHolder(v);
        return appViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewFavouritesFragmentAdapter.AppViewHolder personViewHolder, int i) {
        personViewHolder.appName.setText(apps.get(i).getAppName());
        personViewHolder.appImage.setImageDrawable(apps.get(i).getImage());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView appName;
        ImageView appImage;

        AppViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            appName = (TextView) itemView.findViewById(R.id.app_name);
            appImage = (ImageView) itemView.findViewById(R.id.app_image);

            System.out.println(heightCard);
            cv.getLayoutParams().height = heightCard;

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = packageManager.getLaunchIntentForPackage(apps.get(getAdapterPosition()).getPackageName());
                    mContext.startActivity(intent);
                }
            });

            cv.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.inflate(R.menu.context_menu_fav);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.del:
                                    apps.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                    return true;
                                case R.id.info:
                                    Intent intentInfo = new Intent();
                                    intentInfo.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts(SCHEME, apps.get(getAdapterPosition()).getPackageName(), null);
                                    intentInfo.setData(uri);
                                    mContext.startActivity(intentInfo);
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
}
