package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class POS extends AppCompatActivity {

   FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
   // ProgressDialog pd;
    String title,desc, productid, userid;
    private ArrayList<String> data=new ArrayList<String>();
    private ArrayList<String> data1=new ArrayList<String>();
    private ArrayList<String> data2=new ArrayList<String>();
    private ArrayList<String> data3=new ArrayList<String>();
    private ArrayList<String> data4=new ArrayList<String>();
    private TableLayout table1;
    private EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8;
    private  Button Addbtn,b2,b3,btn_search;
    TextView textView;
    Double price,quantity,total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_o_s);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();

        //receiving values

        ed1=findViewById(R.id.pid);
        ed2=findViewById(R.id.product_name);
        ed3=findViewById(R.id.quantity);
        ed4=findViewById(R.id.price);
        ed5=findViewById(R.id.total);
        ed6=findViewById(R.id.payment);
        ed7=findViewById(R.id.balance);
        textView=findViewById(R.id.date);
        Addbtn=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        btn_search=findViewById(R.id.sach);
        table1=findViewById(R.id.tb1);

//adding data to the database

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productname=ed2.getText().toString().trim();
                 quantity= Double.parseDouble(ed3.getText().toString().trim());
                 price= Double.parseDouble(ed4.getText().toString().trim());
                 total= Double.parseDouble(ed5.getText().toString().trim());
//                String date=textView.getText().toString();


                uploadData(productname, quantity,
                        price, total);


            }
        });
//calculating balance

        ed6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int subtotal=Integer.parseInt(ed5.getText().toString());
                    int pay=Integer.parseInt(ed6.getText().toString());
                    int bal=pay-subtotal;
                    ed7.setText(String.valueOf(bal));
                }catch (Exception exception){
                }
            }
        });


//add data to the table
        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add();


                }catch (Exception ex){

                }

            }
        });
    }


//method for storing sales in firebase
    private void uploadData(String productname, Double quantity, Double price, Double totalprice) {
        // set title of progress bar
//        pd.setTitle("storing data..");
        //show progress bar
     //   pd.show();
        //random id for data to be stored
        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("productname", productname);
        doc.put("quantity", quantity);
        doc.put("price", price);
        doc.put("totalprice", totalprice);
     //   doc.put("date", date);

        db.collection("sales").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //this will be called when data is added success
                //pd.dismiss();
                Toast.makeText(POS.this, "uploaded", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //this will be called when data is fail to be added
               // pd.dismiss();
                Toast.makeText(POS.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void add() {
        try {
            int tot;
            //  String proid=ed1.getText().toString();
            String prodname=ed2.getText().toString();
            int qty=Integer.parseInt(ed3.getText().toString());
            int price=Integer.parseInt(ed4.getText().toString());
            tot=qty*price;
            data.add(prodname);
            data1.add(String.valueOf(qty));
            data2.add(String.valueOf(price));
            data3.add(String.valueOf(tot));

            TableLayout table=(TableLayout) findViewById(R.id.tb1);
            TableRow row=new TableRow(this);
            TextView t1=new TextView(this);
            TextView t2=new TextView(this);
            TextView t3=new TextView(this);
            TextView t4=new TextView(this);

            String total;

            int sum=0;
            for(int j=0; j<data.size(); j++)
            {

                String pname=data.get(j);
                String qtyy=data1.get(j);
                String prc=data2.get(j);
                total=data3.get(j);
//setting data to the table
                t1.setText(pname);
                t2.setText(qtyy);
                t3.setText(prc);
                t4.setText(total);

                sum=sum+ Integer.parseInt(data3.get(j).toString());
            }
            row.addView(t1);
            row.addView(t2);
            row.addView(t3);
            row.addView(t4);
            table1.addView(row);

            ed5.setText(String.valueOf(sum));
            // ed1.setText("");
//            ed2.setText("");
//            ed3.setText("");
//            ed4.setText("");
//            ed2.requestFocus();

        }catch (Exception exception){

        }
    }

    }
