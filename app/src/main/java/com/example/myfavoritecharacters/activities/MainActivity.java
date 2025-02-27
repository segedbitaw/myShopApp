package com.example.myfavoritecharacters.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;

import com.example.myfavoritecharacters.R;
import com.example.myfavoritecharacters.fragments.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    CardView cardOne,cardTwo,cardThree;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();

    }


    public void signUp(){

        String email = ((EditText)findViewById(R.id.TextEmailAddress)).getText().toString();
        String password = ((EditText)findViewById(R.id.TextPassword)).getText().toString();
        String phone = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "reg ok", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(MainActivity.this, "reg failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void login(View view){
        String email = ((EditText)findViewById(R.id.TextEmailAddress)).getText().toString();
        String password = ((EditText)findViewById(R.id.TextPassword)).getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Navigation.findNavController(view).navigate(R.id.action_fragmentOne_to_allProductsFragment);

                        } else {
                            Toast.makeText(MainActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addUser(){
        String phone = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();
        String name = ((EditText)findViewById(R.id.TextName)).getText().toString();
        String password = ((EditText)findViewById(R.id.TextPassword)).getText().toString();
        String email = ((EditText)findViewById(R.id.TextEmailAddress)).getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(name);

        User user = new User(phone,name,email,password);
        myRef.setValue(user);
    }
    public void getUserName(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(name);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public boolean passIsLegit(){
        boolean isUpper,isSymbol,atLeast8;
        String password = ((EditText)findViewById(R.id.TextPassword)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.TextPassword_confirm)).getText().toString();
        isUpper = hasUpperCase(password);
        isSymbol = hasSymbol(password);
        atLeast8 = Atleast8(password);
        if(isUpper && isSymbol && atLeast8){
            return password.equals(confirmPassword);
        }
        return false;
    }
    private boolean hasUpperCase(String password){
       if(password.matches("(.*[A-Z].*)")){
           cardThree = findViewById(R.id.cardThree);
           cardThree.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
           return true;
       }
       return false;
    }
    private boolean hasSymbol(String password){
        if(password.matches("^(?=.*[_@#$&!()]).*$")){
            cardTwo= findViewById(R.id.cardTwo);
            cardTwo.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            return true;
        }
        return false;
    }
    private boolean Atleast8(String password){
        if(password.length() >= 8){
            cardOne = findViewById(R.id.cardOne);
            cardOne.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            return true;
        }
        return false;
    }
    public boolean allFieldFull(){
        String phone = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();
        String name = ((EditText)findViewById(R.id.TextName)).getText().toString();
        String password = ((EditText)findViewById(R.id.TextPassword)).getText().toString();
        String email = ((EditText)findViewById(R.id.TextEmailAddress)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.TextPassword_confirm)).getText().toString();
        boolean isFull = true;
        if(phone.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()){
            isFull = false;
        }
        return isFull;
    }


    void func(View view){
        Intent intent = new Intent(this, MainActivitySecond.class);
        startActivity(intent);
    }
}