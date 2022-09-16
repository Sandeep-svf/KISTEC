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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.in.kistec.R;
import com.in.kistec.StatusListIndivisualDetailsActivity;

import java.io.File;
import java.util.ArrayList;


public class Edit_Doc_Adapter extends RecyclerView.Adapter<Edit_Doc_View_Holder> {

    ArrayList<File> data;
    Context context;
    public static final int Theme_Black_NoTitleBar_Fullscreen = 16973834;

    public Edit_Doc_Adapter(ArrayList<File> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Edit_Doc_View_Holder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.edit_doc_view_holder,parent,false);
        return new Edit_Doc_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Edit_Doc_View_Holder holder, int position) {
        File  my_file=data.get(position);
        Log.e("image_path_screening","******"+my_file.getPath());

        holder.delete_Data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.logout_dialog);
                LinearLayout noDialogLogout = dialog.findViewById(R.id.noDialogLogout);
                LinearLayout yesDialogLogout = dialog.findViewById(R.id.yesDialogLogout);


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeItem(position);
                        dialog.dismiss();
                    }
                });

                noDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        if (my_file.getName().contains(".jpg") || my_file.getName().contains(".jpeg") || my_file.getName().contains(".png"))
        {
//            Toast.makeText(context, "BBB", Toast.LENGTH_SHORT).show();
            Glide.with(context)
                    .load(my_file)
                    .placeholder(R.color.codeGray)
                    .centerCrop()
                    .into(holder.image);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());

                        Dialog imageEditViewDialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        imageEditViewDialog.setContentView(R.layout.frame_help_2);
                        ImageView imageView = imageEditViewDialog.findViewById(R.id.imageViewEdit);
                        LinearLayout backArrow = imageEditViewDialog.findViewById(R.id.backArrow);
                        imageView.setImageURI(Uri.parse(my_file.getAbsolutePath()));
                        imageEditViewDialog.show();

                        backArrow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imageEditViewDialog.dismiss();
                            }
                        });

                        Log.e("image_edit_path","##########"+my_file.getAbsolutePath());

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("image_edit_path","*********"+e);
                    }

                }
            });
        }
        else  if (my_file.getName().toLowerCase().contains(".pdf"))
        {
//            Toast.makeText(context, "AAA", Toast.LENGTH_SHORT).show();

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
                        Log.e("image_path_screening","eee"+e);
                    }
                }
            });
        }
    }

    public void removeItem(int position) {
        try {
            data.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class Edit_Doc_View_Holder extends RecyclerView.ViewHolder {
    ImageView image , delete_Data2;

    public Edit_Doc_View_Holder(@NonNull  View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        delete_Data2 = itemView.findViewById(R.id.delete_Data2);
    }
}
