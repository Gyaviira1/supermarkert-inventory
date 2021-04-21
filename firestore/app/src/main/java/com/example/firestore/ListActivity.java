    package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.List;
public class ListActivity extends AppCompatActivity {
  public  RecyclerView recyclerView;
    List<Model> modelList=new ArrayList<>();
    //layout manager for recycleview
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton addbutton;
    TextView sumitem,itemcosts;
    Boolean isAllvisible;
    //firestore instance
    FirebaseFirestore db;
    CustomAdapter  adapter;
    ProgressDialog pd;
   DocumentReference documentReference;
   FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inventory List");
        addbutton=findViewById(R.id.addbtn);
        sumitem=findViewById(R.id.totalsum);
        itemcosts=findViewById(R.id.itemcosts);
        firebaseAuth=FirebaseAuth.getInstance();
//handling froating actionbutton
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, AddProduct.class));
                finish();
            }
        });
        //initialize firestore
        db=FirebaseFirestore.getInstance();
        //initialize views
        recyclerView=findViewById(R.id.recycleview);
        //set recycleview properties
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //initiate dialog
        pd=new ProgressDialog(this);
        //show data in recycle view
        showData();
    }
    private void showData() {


        //showing number of items in the database.
        db.collection("Documents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = 0;
                    for (DocumentSnapshot document : task.getResult()) {
                        count++;
                    }
                    sumitem.setText(Integer.toString(count));
                    Log.d("TAG", count + "this is the right answer.....................");
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        //showing total cost of all items in the inventory.
                db.collection("Documents")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                  Double  total= Double.valueOf(0);
                    for(QueryDocumentSnapshot document:task.getResult()){
                        Double purchaseprice=document.getDouble("sale_price");
                        total +=purchaseprice;
                    }
                    itemcosts.setText(String.valueOf(total));
                    Log.d("TAG", String.valueOf(total));
                }else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        //set title of progress bar
        pd.setTitle("loading data....");
        //show progress dialogue
        pd.show();

        db.collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();

                        //called when data is retrieved
                        pd.dismiss();
                        for (DocumentSnapshot doc: task.getResult()){

                            Model model=new Model
                                    (doc.getString("id"),
                                    doc.getString("description"),
                                    doc.getString("product_code"),
                                    doc.getString("product_name"),
                                    doc.getString("cate"),
                                    doc.getString("date"),
                                    doc.getDouble("purchase_price"),
                                    doc.getDouble("sale_price"),
                                    doc.getDouble("qty"));
                            modelList.add(model);
                        }
                        //adapter
                        adapter=new CustomAdapter(ListActivity.this, modelList);
                        //set adapter to recyleview
                        recyclerView.setAdapter(adapter);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is an error while retrieving
                        pd.dismiss();
                        Toast.makeText(ListActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void show(){

    }

    public void deleteData(int index){
        //set title of progress bar
        pd.setTitle("Deleting  data....");


        //show progress dialogue
        pd.show();

        db.collection("Documents").document(modelList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when deleted successfully
                        Toast.makeText(ListActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                        //update data
                        showData();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error
                        pd.dismiss();
                        //get and show error
                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }
    private void searchData(String query) {

        // set title of progress bar
        pd.setTitle("searching...");
        //show progress bar
        pd.show();
        db.collection("Documents").whereEqualTo("product_code",query)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when searching is success
                        modelList.clear();
                        pd.dismiss();
                        // show data
                        for (DocumentSnapshot doc: task.getResult()){
                            Model model=new Model
                                    (doc.getString("id"),
                                    doc.getString("description"),
                                    doc.getString("product_code"),
                                    doc.getString("product_name"),
                                    doc.getString("cate"),
                                    doc.getString("date") ,
                                    doc.getDouble("purchase_price"),
                                    doc.getDouble("sale_price") ,
                                    doc.getDouble("qty")
                               );
                            modelList.add(model);
                        }
                        //adapter
                        adapter=new CustomAdapter(ListActivity.this, modelList);
                        //set adapter to recyleview
                        recyclerView.setAdapter(adapter);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when searching has failed
                        pd.dismiss();
                        //errow message
                        Toast.makeText(ListActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();


                    }
                });
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflating menu.xml
        getMenuInflater().inflate(R.menu.menu, menu);
        //searchView
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //called when pressing search button
                searchData(query);// function call with string entered in search view as parameter
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //called when we type a single letter
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle other menu items
        if (item.getItemId()==R.id.action_settings){
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}