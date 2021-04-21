package com.example.firestore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    ListActivity listActivity;
    List<Model>modelList;


    public CustomAdapter(ListActivity listActivity, List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout,parent, false);

        ViewHolder viewHolder=new ViewHolder(itemView);
        //as handle item clicks here
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when the user clicks item

                //show data in toast on clicking
                String description=modelList.get(position).getDescription();
                String product_code=modelList.get(position).getProduct_code();
                String product_name=modelList.get(position).getProduct_name();
                String cate=modelList.get(position).getCate();
                Double purchase_price=modelList.get(position).getPurchase_price();
                Double sale_price=modelList.get(position).getSale_price();
                Double qty= modelList.get(position).getQty();
//                int purchase_price=modelList.get(position).getPurchase_price();
//                int sale_price=modelList.get(position).getSale_price();
//                int qty=modelList.get(position).getQty();
               String date=modelList.get(position).getDate();
                Toast.makeText(listActivity,"long press to update or sale item",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                //this will be called when the user long clicks item

                //creating the alert dialogue
                AlertDialog.Builder builder=new AlertDialog.Builder(listActivity);
                //options to display dialogue
                String[] options={"Update","Sale Item"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            //update is clicked
                            //get data
                            String id=modelList.get(position).getId();
                            String description=modelList.get(position).getDescription();
                            String product_code=modelList.get(position).getProduct_code();
                            String product_name=modelList.get(position).getProduct_name();
                            String cate=modelList.get(position).getCate();
                            Double purchase_price=modelList.get(position).getPurchase_price();
                            Double sale_price=modelList.get(position).getSale_price();
                            Double qty= modelList.get(position).getQty();
                            String date=modelList.get(position).getDate();

                            //intent to start activity
                            Intent intent=new Intent(listActivity, AddProduct.class);
                            //put data in intent
                            intent.putExtra("pId",id);
                            intent.putExtra("pDescription",description);
                            intent.putExtra("pProductcode",product_code);
                            intent.putExtra("pProductname",product_name);
                            intent.putExtra("pCategory",cate);
                            intent.putExtra("pPurchaseprice",purchase_price);
                            intent.putExtra("pSaleprice",sale_price);
                            intent.putExtra("pQuantity",qty);
                            intent.putExtra("pDate",date);

                            //start activity
                            listActivity.startActivity(intent);

                        }
                        if (which==1){
                            //Delete is clicked
                          //  listActivity.deleteData(position);
                            Intent intent=new Intent(listActivity,POS.class);
                            //put data in intent
                            listActivity.startActivity(intent);


                        }

                    }
                }).create().show();
            }
        });

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind views/ set data
      //  holder.mTitle.setText(modelList.get(position).getTitle());
        holder.mDescription.setText(modelList.get(position).getDescription());
        holder.mProductcode.setText(modelList.get(position).getProduct_code());
        holder.mProductname.setText(modelList.get(position).getProduct_name());
        holder.mCategory.setText(modelList.get(position).getCate());
//        holder.mPurchaseprice.setText(modelList.get(position).getPurchase_price());
//        holder.mSaleprice.setText(modelList.get(position).getSale_price());
//        holder.mQuantity.setText(modelList.get(position).getQty());
        holder.mDate.setText(modelList.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
