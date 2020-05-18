package com.lidengqi.lianxi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidengqi.lianxi.R;
import com.lidengqi.lianxi.util.GPUImageFilterTools;

import java.util.ArrayList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

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
    /**
     * 加上滤镜的bitmap
     */
    private List<Bitmap> filterBitmaps = new ArrayList<>();
    /**
     * 滤镜数据列表
     */
    private List<GPUImageFilter> filters = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public GpuImgAdapter(Context context, Uri imageUri, GPUImageFilterTools.FilterList filterList) {
        this.context = context;
        this.imageUri = imageUri;
        this.filterList = filterList;
        initFilters();
        updateData();
    }

    /**
     * 初始化滤镜
     */
    private void initFilters() {
        filters.clear();
        if (filterList != null && filterList.getNames() != null) {
            for (int i = 0; i < filterList.getNames().size(); i++) {
                GPUImageFilter filter = GPUImageFilterTools.createFilterForType(context, filterList.getFilters().get(i));
                filters.add(filter);
            }
        }
    }

    /**
     * 更新加上滤镜的bitmap
     */
    private void updateData() {
        filterBitmaps.clear();
        String path = "/storage/emulated/0/NubiaTheme/festival_wallpapers/festival_wallpaper_2018-06-01-00-00-07.png";

        Bitmap bitmap = getSmallBitmap(path);

        GPUImage gpuImage = new GPUImage(context);
        for (GPUImageFilter filter : filters) {
//            gpuImage.setImage(bitmap);
            gpuImage.setFilter(filter);
            filterBitmaps.add(gpuImage.getBitmapWithFilterApplied(bitmap));
        }
    }

    public static Bitmap getSmallBitmap(String filePath) {
        Bitmap b1 = BitmapFactory.decodeFile(filePath);
        Log.i("glide", "decode 1:" + b1);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 200, 200);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        Log.i("glide", "decode 2 height:" + height + " w:" + width);

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
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
        holder.gpuImageView.setImageBitmap(filterBitmaps.get(i));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.clickItem(filters.get(i));
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

        ImageView gpuImageView;
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
