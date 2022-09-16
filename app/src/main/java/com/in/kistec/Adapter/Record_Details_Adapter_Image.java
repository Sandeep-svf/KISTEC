package com.in.kistec.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.in.kistec.ImportDocHelperClass.FullImageActivity;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.SettingsActivity.MyProfileActivity;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class Record_Details_Adapter_Image extends RecyclerView.Adapter<RecordImageViewHolder> {

Context context;
String[] list;
private int i;


    public Record_Details_Adapter_Image(Context context, String[] list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecordImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.record_details_image_view_holder,parent,false);
        return new RecordImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecordImageViewHolder holder, int position) {
     //   Toast.makeText(context, list[0], Toast.LENGTH_SHORT).show();

        Glide.with(context).load(API_Client.BASE_IMAGE_DOC+list[position]).into(holder.image);


         //   Toast.makeText(context, list[i], Toast.LENGTH_SHORT).show();
            if( list[position].contains(".jpg") || list[position].contains(".jpeg") || list[position].contains(".png") )
            {
                Log.e("Indise_filter", "true");
                Glide.with(context).load(API_Client.BASE_IMAGE_DOC+list[position]).into(holder.image);

                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                      public void onClick(View v) {

                        try {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse(API_Client.BASE_IMAGE_DOC+list[position]), "image/*");
                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Someting went really wrong! Flling sorry for you", Toast.LENGTH_SHORT).show();
                        }

                    /*    try {
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            Dialog imageDetailsViewDialog =new Dialog(context,
                                    android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            imageDetailsViewDialog.setContentView(R.layout.frame_help_3);
                            ImageView imageViewDTL = imageDetailsViewDialog.findViewById(R.id.imageViewDTL);
                            LinearLayout backArrow = imageDetailsViewDialog.findViewById(R.id.backArrow);
                            imageViewDTL.setImageURI(Uri.parse(API_Client.BASE_IMAGE_DOC+list[position]));
                            imageDetailsViewDialog.show();



                            backArrow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imageDetailsViewDialog.dismiss();
                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("dialogImageView" ,"Something went wrong :"+e);
                        }*/

                      }
                });


            }else if(list[position].toLowerCase().contains(".pdf") ) {
                Glide.with(context)
                        .load(R.drawable.pdf_image)
                        .into(holder.image);
                Log.e("Indise_filter", "true");
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(API_Client.BASE_IMAGE_DOC+list[position]), "application/pdf");
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                   /*     StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());

                        Dialog imageViewDialog1 = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        imageViewDialog1.setContentView(R.layout.frame_help_view_pdf2);

                        PDFView pdfView = imageViewDialog1.findViewById(R.id.pdfv);

                        File file =  new File(API_Client.BASE_IMAGE_DOC+list[position]);
                        String path = API_Client.BASE_IMAGE_DOC+list[position];
                        Log.e("path","pdf : "+path);

                        pdfView.fromUri(Uri.parse(API_Client.BASE_IMAGE_DOC+list[position]))
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

                        imageViewDialog1.show();*/


                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
class RecordImageViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    public RecordImageViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image);
    }
}