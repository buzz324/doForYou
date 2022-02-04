package com.example.doforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    Button bSignIn,bViewAll,bNextPage;
    EditText firstName,lastName;
    ListView listView ;
    ArrayAdapter customerArrayAdapter;
    SQLiteHelper dataBaseHelper;
    TextView forgotTextLink;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        bSignIn= (Button) findViewById(R.id.sign_up);
        bViewAll = (Button) findViewById(R.id.view_all);
        bNextPage= (Button) findViewById(R.id.next_page);

        firstName= (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        listView = (ListView)  findViewById(R.id.lv_list);
        forgotTextLink = (TextView) findViewById(R.id.forgotPassword);

        dataBaseHelper = new SQLiteHelper(SignIn.this);

        showCustormerOnListView(dataBaseHelper);

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User customer;
                try {
                    customer= new User(-1,firstName.getText().toString(), lastName.getText().toString());
                   // Toast.makeText(MainActivity.this, customer.toString(), Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(SignIn.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                    customer = new User(-1,"no", "name");
                }


                SQLiteHelper dataBaseHelper = new SQLiteHelper(SignIn.this);

              boolean success = dataBaseHelper.addOne(customer);
              Toast.makeText(SignIn.this,"success= "+ success,Toast.LENGTH_SHORT).show();
                showCustormerOnListView(dataBaseHelper);


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //Which person is clicked
                User clickedCustomer = (User) parent.getItemAtPosition(i);
                dataBaseHelper.DeleteOne(clickedCustomer);
                showCustormerOnListView(dataBaseHelper);
                Toast.makeText(SignIn.this,"Deleted "+ clickedCustomer.toString(),Toast.LENGTH_SHORT);
            }
        });

        bViewAll.setOnClickListener(view -> {
            dataBaseHelper = new SQLiteHelper(SignIn.this);

            //Arrayadapter to show the elements of the array
            showCustormerOnListView(dataBaseHelper);

        });


        //Click next button
        bNextPage.setOnClickListener(view -> {

            onClickMain(view);

        });

        //https://www.youtube.com/watch?v=UMNeeMSUZl0 -reference
        //Forgot password
        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your email to received Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send reset link

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(SignIn.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignIn.this,"Error! Reset link is not sent" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Close the dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });

    }

    public void onClickMain(View v){

        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    private void showCustormerOnListView(SQLiteHelper dataBaseHelper2) {
        customerArrayAdapter = new ArrayAdapter<User>(SignIn.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        listView.setAdapter(customerArrayAdapter);
    }
}

