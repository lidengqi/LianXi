package com.lidengqi.lianxi.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lidengqi.lianxi.R;
import com.lidengqi.lianxi.adapter.GpuImgAdapter;
import com.lidengqi.lianxi.util.DisplayUtils;
import com.lidengqi.lianxi.util.GPUImageFilterTools;
import com.lidengqi.lianxi.util.RecycleSpacesItemDecoration;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * 在此添加[类的功能描述]
 *
 * @author dengqi.li
 * @date 2020/05/08
 */
public class GalleryActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 1;
    private GPUImageView gpuImageView;
    private RecyclerView recyclerView;
    private SeekBar seekBar;
    private GPUImageFilterTools.FilterAdjuster filterAdjuster;
    private GpuImgAdapter gpuImgAdapter;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        gpuImageView = findViewById(R.id.gpuimage);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (filterAdjuster != null) {
                    filterAdjuster.adjust(progress);
                }
                gpuImageView.requestRender();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleSpacesItemDecoration(DisplayUtils.dp2px(this, 8)));
        startPhotoPicker();
    }

    private void startPhotoPicker() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE);
    }

    private void saveImage() {
        String fileName = System.currentTimeMillis() + ".jpg";
        gpuImageView.saveToPictures("GPUImage", fileName, new GPUImageView.OnPictureSavedListener() {
            @Override
            public void onPictureSaved(Uri uri) {
                Toast.makeText(GalleryActivity.this, "Saved: " + uri.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchFilterTo(GPUImageFilter filter) {
        if (gpuImageView.getFilter() == null || gpuImageView.getFilter() != filter) {
            gpuImageView.setFilter(filter);
            filterAdjuster = new GPUImageFilterTools.FilterAdjuster(filter);
            if (filterAdjuster.canAdjust()) {
                seekBar.setVisibility(View.VISIBLE);
                filterAdjuster.adjust(seekBar.getProgress());
            } else {
                seekBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            gpuImageView.setImage(data.getData());
            if (gpuImgAdapter == null) {
                gpuImgAdapter = new GpuImgAdapter(this, imageUri, GPUImageFilterTools.getFilterList());
                gpuImgAdapter.setOnItemClickListener(new GpuImgAdapter.ItemClickListener() {
                    @Override
                    public void clickItem(GPUImageFilter filter) {
                        switchFilterTo(filter);
                        gpuImageView.requestRender();
                    }
                });
                recyclerView.setAdapter(gpuImgAdapter);
            } else {
                gpuImgAdapter.notifyDataSetChanged();
            }
        }
    }
}
