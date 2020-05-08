package com.lidengqi.lianxi.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidengqi.lianxi.R;
import com.lidengqi.lianxi.util.GPUImageFilterTools;

import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

/**
 * 在此添加[类的功能描述]
 *
 * @author dengqi.li
 * @date 2020/05/08
 */
public class GpuImgAdapter extends RecyclerView.Adapter<GpuImgAdapter.Holder> {

    private Context context;
    private GPUImageFilterTools.FilterList filterList;
    private Uri imageUri;
    private ItemClickListener itemClickListener;

    public GpuImgAdapter(Context context, Uri imageUri, GPUImageFilterTools.FilterList filterList) {
        this.context = context;
        this.imageUri = imageUri;
        this.filterList = filterList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(context).inflate(R.layout.layout_gpuimage_item, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.tvName.setText(filterList.getNames().get(i));
        holder.gpuImageView.setImage(imageUri);
        final GPUImageFilter filter = GPUImageFilterTools.createFilterForType(context, filterList.getFilters().get(i));
        holder.gpuImageView.setFilter(filter);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.clickItem(filter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (filterList == null || filterList.getNames() == null) ? 0 : filterList.getNames().size();
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    public class Holder extends RecyclerView.ViewHolder {

        GPUImageView gpuImageView;
        TextView tvName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            gpuImageView = itemView.findViewById(R.id.item_gpuimage);
            tvName = itemView.findViewById(R.id.tv_gpuimage);
        }
    }

    public interface ItemClickListener {
        void clickItem(GPUImageFilter filter);
    }
}
