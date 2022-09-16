package com.in.kistec.ImportDocHelperClass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.in.kistec.R;

import java.io.File;
import java.util.ArrayList;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ViewHolder>{

    Context context;
    ArrayList<String> stringArrayList;

    public SelectedImageAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.selected_image_list, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public  void onBindViewHolder(ViewHolder holder, final int position) {
        File my_file= new File(stringArrayList.get(position));
        if (stringArrayList.get(position).contains(".jpg") || stringArrayList.get(position).contains(".jpeg") || stringArrayList.get(position).contains(".png")) {
            Glide.with(context)
                    .load(stringArrayList.get(position))
                    .placeholder(R.color.codeGray)
                    .centerCrop()
                    .into(holder.image);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, FullImageActivity.class).putExtra("image", stringArrayList.get(position)));
                }
            });
        }else if(stringArrayList.get(position).contains("pdf") || stringArrayList.get(position).contains("PDF")) {
            Glide.with(context)
                    .load(R.drawable.pdf_image)
                    .into(holder.image);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());

                        Dialog imageViewDialog=new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        imageViewDialog.setContentView(R.layout.frame_help_view_pdf);

                        PDFView pdfView = imageViewDialog.findViewById(R.id.pdfv);



                        pdfView.fromFile(my_file.getAbsoluteFile())
                                .defaultPage(0)
                                .enableSwipe(true)

                                .swipeHorizontal(false)
                                .onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {

                                    }
                                })
                                .enableAnnotationRendering(true)
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {

                                    }
                                })
                                .scrollHandle(new DefaultScrollHandle(context))
                                .load();

                        imageViewDialog.show();


                    } catch (Exception e) {
                        Log.e("image_path_screeningggg","eee"+e);

                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}