package com.vickystft.statussaverpro.adapter;

import static com.vickystft.statussaverpro.util.Utils.RootDirectoryInsta;
import static com.vickystft.statussaverpro.util.Utils.startDownload;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vickystft.statussaverpro.R;
import com.vickystft.statussaverpro.databinding.ItemsWhatsappViewBinding;
import com.vickystft.statussaverpro.activity.VideoPlayerActivity;
import com.vickystft.statussaverpro.model.story.ItemModel;

import java.util.ArrayList;

public class StoriesListAdapter extends RecyclerView.Adapter<StoriesListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ItemModel> storyItemModelList;

    public StoriesListAdapter(Context context, ArrayList<ItemModel> list) {
        this.context = context;
        this.storyItemModelList = list;
    }

    @NonNull
    @Override
    public StoriesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.items_whatsapp_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesListAdapter.ViewHolder viewHolder, int position) {
        ItemModel itemModel = storyItemModelList.get(position);
        try {
            if (itemModel.getMedia_type()==2) {
                viewHolder.binding.ivPlay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.binding.ivPlay.setVisibility(View.GONE);
            }
            Glide.with(context)
                    .load(itemModel.getImage_versions2().getCandidates().get(0).getUrl())
                    .into(viewHolder.binding.pcw);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        viewHolder.binding.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("PathVideo",itemModel.getVideo_versions().get(0).getUrl());
                context.startActivity(intent);

            }
        });


        viewHolder.binding.tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModel.getMedia_type()==2) {
                    startDownload(itemModel.getVideo_versions().get(0).getUrl(),
                            RootDirectoryInsta, context,"story_"+itemModel.getId()+".mp4" );
                }else {
                    startDownload(itemModel.getImage_versions2().getCandidates().get(0).getUrl(),
                            RootDirectoryInsta, context, "story_"+itemModel.getId()+".png");
                }
            }
        });


    }
    @Override
    public int getItemCount() {
        return storyItemModelList == null ? 0 : storyItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ItemsWhatsappViewBinding binding;
        public ViewHolder(ItemsWhatsappViewBinding mbinding) {
            super(mbinding.getRoot());
            this.binding = mbinding;
        }
    }
}