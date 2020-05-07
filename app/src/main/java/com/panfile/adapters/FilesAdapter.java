package com.panfile.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panfile.R;
import com.panfile.entity.json.PanFile;
import com.panfile.event.MainThreadEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FilesHolder>{

    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<PanFile> files;

    public FilesAdapter(Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        files = new ArrayList<>();
    }

    public void setFiles(List<PanFile> files) {
        this.files = files;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilesHolder(layoutInflater.inflate(R.layout.item_file,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilesHolder holder, int position) {
        holder.tv_name.setText(files.get(position).getName());
        holder.iv_icon.setBackgroundResource(files.get(position).getFileType() == 0 ? R.mipmap.directory : R.mipmap.file);
        holder.v.setOnClickListener( v ->{
            EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.FILES_FILE_CLICK,files.get(position)));
        });

        holder.v.setOnLongClickListener( v ->{
            EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.FILES_FILE_LONGCLICK,files.get(position)));
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return files == null ? 0 : files.size();
    }

    class FilesHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        View v;
        FilesHolder(View v){
            super(v);
            this.v = v;
            iv_icon = v.findViewById(R.id.iv_file_item_icon);
            tv_name = v.findViewById(R.id.tv_file_item_name);
        }
    }
}
