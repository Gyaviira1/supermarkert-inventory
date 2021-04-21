package com.example.firestore;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView mDescription,mProductcode,mProductname,mCategory,mPurchaseprice,mSaleprice,mQuantity,mDate;
    View mView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView=itemView;
        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());

            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
        // initialise views with model
        //mTitle=itemView.findViewById(R.id.ltitle);
        mDescription=itemView.findViewById(R.id.ldescription);
        mProductcode=itemView.findViewById(R.id.lproductcode );
        mProductname=itemView.findViewById(R.id.lproductname);
        mCategory=itemView.findViewById(R.id.lcategory);
        mPurchaseprice=itemView.findViewById(R.id.lpurchaseprice);
        mSaleprice=itemView.findViewById(R.id.lsaleprice);
        mQuantity=itemView.findViewById(R.id.lquantity);
        mDate=itemView.findViewById(R.id.ldate);

    }
    private ViewHolder.ClickListener mClickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }
    public  void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener=clickListener;


    }
}
