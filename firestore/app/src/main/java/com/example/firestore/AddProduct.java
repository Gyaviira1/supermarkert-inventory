package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class AddProduct extends AppCompatActivity {
    private EditText mtitle;
    private EditText mdescription;
    private EditText productname;
    private EditText category;
    private EditText purchaseprice;
    private EditText saleprice;
    private EditText quantity;
    double sale_price;
    double purchase_price;
    double qty;
    private TextView productcode,showingtotalitems;
    public static TextView resultTextView;
    private Button save,list,scan,date,login;
    private ProgressDialog pd;
    private DatePickerDialog datePickerDialog;

    //firestore instance
    private FirebaseFirestore db;
    String pId;
    String pTitle;
    String pDescription;
    String pProductcode;
    String pProductname;
    String pCategory;
    String pPurchaseprice;
    String pSaleprice;
    String pQuantity;
    String pDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        //actionbar and its title
        ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle("Add data");
        mtitle = findViewById(R.id.title);
        mdescription = findViewById(R.id.description);
        productcode = findViewById(R.id.scan_code);
        resultTextView = findViewById(R.id.scan_code);
        productname = findViewById(R.id.product_name);
        category = findViewById(R.id.category);
        purchaseprice = findViewById(R.id.price);
        saleprice = findViewById(R.id.sale_price);
        quantity = findViewById(R.id.quantity);
        date = findViewById(R.id.date);
        save = findViewById(R.id.btnsave);
        scan = findViewById(R.id.scann);
        date.setText(getTodaysDate());

        // date field
        initDatePicker();
        //came here after clicking update dialugue alert
        Bundle bundle=getIntent().getExtras();
        if (bundle !=null){
            //update data
            actionBar.setTitle("Update data");
            save.setText("Update");
            pId=bundle.getString("pId");
           // pTitle=bundle.getString("pTitle");
            pDescription=bundle.getString("pDescription");
            pProductcode=bundle.getString("pProductcode");
            pProductname=bundle.getString("pProductname");
            pCategory=bundle.getString("pCategory");
            pPurchaseprice=bundle.getString("pPurchaseprice");
            pSaleprice=bundle.getString("pSaleprice");
            pQuantity=bundle.getString("pQuantity");
            pDate=bundle.getString("pDate");

            //setdata
          //  mtitle.setText(pTitle);
            mdescription.setText(pDescription);
            productcode.setText(pProductcode);
            productname.setText(pProductname);
            category.setText(pCategory);
            purchaseprice.setText(pPurchaseprice);
            saleprice.setText(pSaleprice);
            quantity.setText(pQuantity);
            date.setText(pDate);

        }
        else {
            //new data
            actionBar.setTitle("Add Item");
            save.setText("save");
        }
// prograss bar
        pd = new ProgressDialog(this);
//firestore
        db = FirebaseFirestore.getInstance();



//scan code item
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Scan_code_Activity
                .class));
            }
        });
        //click button to upload data
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle1 = getIntent().getExtras();
                String dat = null;
                if (bundle1 != null) {
                    //updating
                    //input data
                    String id = pId;
                    //   String title = mtitle.getText().toString().trim();
                    String description = mdescription.getText().toString().trim();
                    String product_code = productcode.getText().toString().trim();
                    String product_name = productname.getText().toString().trim();
                    String cate = category.getText().toString().trim();
                    Double purchase_price = Double.valueOf(Integer.parseInt(purchaseprice.toString().trim()));
                    Double sale_price = Double.valueOf(Integer.parseInt(saleprice.getText().toString().trim()));
                    Double qty = Double.valueOf(Integer.parseInt(quantity.getText().toString().trim()));
                    dat = date.getText().toString().trim();


                    // function call to update data
                    updateData(id, description, product_code, product_name, cate, purchase_price,
                            sale_price, qty, dat);
                    mdescription.setText("");
                    purchaseprice.setText("");
                    productname.setText("");
                    resultTextView.setText("");
                    quantity.setText("");
                    saleprice.setText("");
                    category.setText("");

                } else {
                    //adding new
                    //input data
                    //    String title = mtitle.getText().toString().trim();
                    sale_price = Double.parseDouble(saleprice.getText().toString().trim());
                    purchase_price = Double.parseDouble(purchaseprice.getText().toString().trim());
                    qty = Double.parseDouble(quantity.getText().toString().trim());
                    String description = mdescription.getText().toString().trim();
                    String product_code = resultTextView.getText().toString().trim();
                    String product_name = productname.getText().toString().trim();
                    String cate = category.getText().toString().trim();
                    String day= date.getText().toString().trim();



//                    if(TextUtils.isEmpty(product_code)){
//                        resultTextView.setError("product code is required.");
//                        return;
//                    }
//                    if(TextUtils.isEmpty(product_name)){
//                        productname.setError("product name is required.");
//                        return;
//                    }
//                    if(TextUtils.isEmpty(cate)){
//                        category.setError("category is required.");
//                        return;
//                    }
//                    if(TextUtils.isEmpty("")){
//                        purchaseprice.setError("phone number is required.");
//                        return;
//                    }
//                    if(TextUtils.isEmpty("")) {
//                        saleprice.setError("sale price is required.");
//                        return;
//                    }
//                    if(TextUtils.isEmpty("")) {
//                        quantity.setError("address is required.");
//                        return;
//                    }
//                    if(TextUtils.isEmpty(description)) {
//                        mdescription.setError("address is required.");
//                        return;
//                    }
//                    if(TextUtils.isEmpty(dat)) {
//                        date.setError("date is required.");
//                        return;
//                    }
                    //function call to upload data
                    uploadData(description, product_code,
                            product_name, cate, purchase_price
                            , sale_price, qty, day);

                }

//                //clearing edittext after submiting
                mdescription.setText("");
                purchaseprice.setText("");
                productname.setText("");
                resultTextView.setText("");
                quantity.setText("");
                saleprice.setText("");
                category.setText("");
//
//                CollectionReference dbproducts = db.collection("products");
//                titles titlem = new titles(
//                        title, description
//                );
//                dbproducts.add(titlem)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Toast.makeText(MainActivity.this, "added", Toast.LENGTH_LONG).show();
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                });


            }
        });


    }




    private void updateData(String id, String description, String product_code,
                            String product_name, String cate, Double purchase_price,
                            Double sale_price, Double qty, String date) {
        // set title of progress bar
        pd.setTitle("updating data..");
        //show progress bar
        pd.show();

        db.collection("Documents").document(id).update("description",description,"product_code",product_code
        ,"product_name",product_name,"cate",cate,"purchase_price",purchase_price,"sale_price",sale_price,"qty",qty,"date",date)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when updated successfully
                        pd.dismiss();
                        Toast.makeText(AddProduct.this, "updated...", Toast.LENGTH_LONG).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when failed to update (any error)
                        pd.dismiss();
                        //get and show data
                        Toast.makeText(AddProduct.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void uploadData(String description, String product_code, String product_name,
                            String cate, double purchase_price, double sale_price, double qty, String date) {
        // set title of progress bar
        pd.setTitle("storing data..");
        //show progress bar
        pd.show();
        //random id for data to be stor
        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);// id for data
       // doc.put("title", title);
     //   doc.put("search", title.toLowerCase());
        doc.put("description", description);
        doc.put("product_code", product_code);
        doc.put("product_name", product_name);
        doc.put("cate", cate);
        doc.put("purchase_price", purchase_price);
        doc.put("sale_price", sale_price);
        doc.put("qty", qty);
        doc.put("date", date);
        //add this data
        db.collection("Documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called when data is added success
                        pd.dismiss();
                        Toast.makeText(AddProduct.this, "uploaded", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called when data is fail to be added
                        pd.dismiss();
                        Toast.makeText(AddProduct.this, e.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });

    }
//for date

    private String getTodaysDate() {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        month=month+1;
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(dayOfMonth,month,year);

    }


    private void initDatePicker() {
DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month=month+1;
        String datee=makeDateString(dayOfMonth,month,year);
        date.setText(datee);
    }
};

        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
        int sytle= AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog=new DatePickerDialog(this,sytle,dateSetListener,year,month,dayOfMonth);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth+"\t"+getMonthFormat(month)+"\t"+year;
    }

    private String getMonthFormat(int month) {
        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAy";
        if(month==6)
            return "JUN";
        if(month==7)
            return "JUL";
        if(month==8)
            return "AUG";
        if(month==9)
            return "SEPT";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";
//default should never happen
        return "JAN";
    }


    public void openDatePicker(View view) {

        datePickerDialog.show();
    }


    //menu
}