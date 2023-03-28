package com.code.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class Login<Logign> extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    TextView register;
    Button loginbtn;

    AccountHandle accountHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountHandle = new AccountHandle(this);

        //login
        loginbtn = findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //register
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registry();
            }
        });

//        //gg login
        googleBtn = findViewById(R.id.google_btn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            navigateToSecondActivity();
        }

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void login() {
        Account account = new Account();
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

//        String user = username.getText().toString();
//        String pwd = password.getText().toString();

        String user = username.getText().toString();
        String pwd = password.getText().toString();
        if(TextUtils.isEmpty(user))
            Toast.makeText(Login.this, "Chưa nhập username", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(pwd))
            Toast.makeText(Login.this, "Chưa nhập password", Toast.LENGTH_SHORT).show();
        else {
            Cursor c = accountHandle.findAccount(user);
            c.moveToFirst();
            String thongbao = "Sai username hoặc password";
            if(c.getCount() >= 1){
                int id = c.getColumnIndex("password");
                pwd = c.getString(id);
                if(pwd.equals(password.getText().toString())){
                    thongbao = "Đăng nhập thành công";
                    String mail = c.getString(c.getColumnIndex("email"));
                    Intent intent = new Intent(this, Setting.class);
                    intent.putExtra("username", user);  // Truyền một String
                    intent.putExtra("mail", mail);
                    startActivity(intent);
                }
            }
            Toast.makeText(Login.this, thongbao, Toast.LENGTH_SHORT).show();
        }
    }
    void registry() {
        Intent intent = new Intent(this, Registry.class);
        startActivity(intent);
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(Login.this,Setting.class);
        startActivity(intent);
    }

}