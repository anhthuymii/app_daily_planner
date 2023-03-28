package com.code.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registry extends AppCompatActivity {
    AccountHandle accountHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        accountHandle = new AccountHandle(this);
    }

    public void registry(View view) {
        Account account = new Account();
        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText repassword = findViewById(R.id.repassword);

        String user = username.getText().toString();
        String mail = email.getText().toString();
        String pwd = password.getText().toString();
        String repwd = repassword.getText().toString();

        String thongbao = "Không thể thêm tài khoản";
        if(TextUtils.isEmpty(user))
            Toast.makeText(Registry.this, "Chưa nhập username", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(mail))
            Toast.makeText(Registry.this, "Chưa nhập email", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(pwd))
            Toast.makeText(Registry.this, "Chưa nhập password", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(repwd))
            Toast.makeText(Registry.this, "Chưa nhập re-password", Toast.LENGTH_SHORT).show();
        else {
            Cursor c = accountHandle.findAccount(user);
            c.moveToFirst();
            if(c.getCount() >= 1){
                    thongbao = "Tài khoản đã tồn tại";
                }
            else if(pwd.equals(repwd)){
                account.setPassword(pwd);
                account.setUsername(username.getText().toString());
                account.setEmail(email.getText().toString());
                boolean kq = accountHandle.insertAccount(account);
                if(kq)
                    thongbao = "Đăng ký thành công";
            }
            else thongbao = "Password không khớp";
            Toast.makeText(Registry.this, thongbao, Toast.LENGTH_SHORT).show();
        }

    }
    public void loginHere(View view) {
        startActivity(new Intent(this, Login.class));
    }

}