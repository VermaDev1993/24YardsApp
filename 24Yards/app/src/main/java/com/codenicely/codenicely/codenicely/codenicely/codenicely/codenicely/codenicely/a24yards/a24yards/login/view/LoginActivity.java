package com.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.a24yards.a24yards.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.a24yards.a24yards.R;
import com.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.a24yards.a24yards.helper.Keys;
import com.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.a24yards.a24yards.helper.SharedPrefs;
import com.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.a24yards.a24yards.login.presenter.LoginData;
import com.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.a24yards.a24yards.login.presenter.LoginDataImp;
import com.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.a24yards.a24yards.login.provider.RetrofitLoginHelper;
import com.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.codenicely.a24yards.a24yards.otp_verify.view.OtpActivity;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {


    private EditText editTextMobile;
    private ProgressBar progressBar;
    private String mobile;
    private LoginData loginData;
    private SharedPrefs sharedPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("24 Yards");
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        sharedPrefs = new SharedPrefs(this);
        initialise();
    }

    public void initialise() {
        editTextMobile = (EditText) findViewById(R.id.input_mobile);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 10) {
                    hideKeyboard();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void proceed(View v) {
        mobile = editTextMobile.getText().toString();
        if (mobile.isEmpty()) {
            showProgressBar(false);
            showError("Fields cannot be empty");
        } else {
            loginData = new LoginDataImp(this, new RetrofitLoginHelper());
            loginData.getLoginData(mobile);
            hideKeyboard();
        }

    }


    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showLoginStatus(boolean login) {
        if (login) {

            sharedPrefs.setMobile(mobile);

            Intent i = new Intent(LoginActivity.this, OtpActivity.class);
            i.putExtra(Keys.KEY_MOBILE, mobile);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
