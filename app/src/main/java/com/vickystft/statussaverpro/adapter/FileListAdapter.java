package com.vickystft.statussaverpro.adapter;

import android.annotation.SuppressLint;
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
import com.vickystft.statussaverpro.activity.VideoPlayerActivity;
import com.vickystft.statussaverpro.databinding.ItemsFileViewBinding;
import com.vickystft.statussaverpro.interfaces.FileListClickInterface;
import java.io.File;
import java.util.ArrayList;


public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<File> fileArrayList;
    private LayoutInflater layoutInflater;
    private FileListClickInterface fileListClickInterface;

    public FileListAdapter(Context context, ArrayList<File> files, FileListClickInterface fileListClickInterface) {
        this.context = context;
        this.fileArrayList = files;
        this.fileListClickInterface=  fileListClickInterface;
    }

    @NonNull
    @Override
    public FileListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        return new ViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.items_file_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileListAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        File fileItem = fileArrayList.get(i);

        try {
            String extension = fileItem.getName().substring(fileItem.getName().lastIndexOf("."));
            if (extension.equals(".mp4")) {
                viewHolder.mbinding.ivPlay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mbinding.ivPlay.setVisibility(View.GONE);
            }


            viewHolder.mbinding.ivPlay.setOnClickListener(v -> {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("PathVideo",fileItem.getPath().toString());
                context.startActivity(intent);
            });


            Glide.with(context)
                    .load(fileItem.getPath())
                    .into(viewHolder.mbinding.pc);
        }catch (Exception ex){
        }

        viewHolder.mbinding.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileListClickInterface.getPosition(i,fileItem);
            }
        });
    }


    @Override
    public int getItemCount() {
        return fileArrayList == null ? 0 : fileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemsFileViewBinding mbinding;

        public ViewHolder(ItemsFileViewBinding mbinding) {
            super(mbinding.getRoot());
            this.mbinding = mbinding;
        }
    }
}