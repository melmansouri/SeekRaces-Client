package com.mel.seekraces.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import com.mel.seekraces.R;
import com.mel.seekraces.activities.signin.SignInActivity;
import com.mel.seekraces.interfaces.login.ILoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //loginPresenter=new LoginPresenterImpl(this);
        Intent i=new Intent(this, SignInActivity.class);
        startActivity(i);
    }
}
