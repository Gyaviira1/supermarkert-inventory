package com.example.firestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder>  {

    List<Model> productList;
    public void setProductList(List<Model>productList){
        this.productList=productList;
    }


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout,parent,false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        holder.productcode.setText(productList.get(position).getProduct_code());
        holder.productname.setText(productList.get(position).getProduct_name());
        holder.category.setText(productList.get(position).getCate());
//        holder.purchasingprice.setText(productList.get(position).getPurchase_price());
//        holder.salengprice.setText(productList.get(position).getSale_price());
//        holder.quantity.setText(productList.get(position).getSale_price());
        holder.description.setText(productList.get(position).getDescription());
        holder.date.setText(productList.get(position).getDate());


    }

    @Override
    public int getItemCount() {

        if (productList==null){
            return 0;

        }else {
            return productList.size();
        }

    }

    class ProductHolder extends ViewHolder{
        TextView productcode,productname,category,purchasingprice,salengprice,quantity,description,date;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);
               productcode=itemView.findViewById(R.id.lproductcode);
               productname=itemView.findViewById(R.id.lproductname);
               category=itemView.findViewById(R.id.lcategory);
               purchasingprice=itemView.findViewById(R.id.lpurchaseprice);
               salengprice=itemView.findViewById(R.id.lsaleprice);
               quantity=itemView.findViewById(R.id.lquantity);
               description=itemView.findViewById(R.id.ldescription);
               date=itemView.findViewById(R.id.lproductcode);

        }
    }
}
