package com.example.salesapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salesapp.R;
import com.example.salesapp.api.ApiService;
import com.example.salesapp.api.RetrofitBuilder;
import com.example.salesapp.api.TokenManager;
import com.example.salesapp.model.GetResponseToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgotPasswordActivity";
    private ForgotPasswordActivity self;

    private EditText etEmail;
    private Button btnSendEmail;

    private View mProgressBar;
    private ProgressBar mCycleProgressBar;

    private ApiService service;
    private Call<GetResponseToken> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        self = this;

        initView();
        setupService();
        navigate();
    }

    private void navigate() {
        btnSendEmail.setOnClickListener(view -> navigateToLogin());
    }

    private void navigateToLogin() {
        String email = etEmail.getText().toString();

        mProgressBar.setVisibility(View.VISIBLE);
        mCycleProgressBar.setVisibility(View.VISIBLE);

        call = service.forgotPassword(email);
        call.enqueue(new Callback<GetResponseToken>() {
            @Override
            public void onResponse(Call<GetResponseToken> call, Response<GetResponseToken> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    startActivity(new Intent(self, LoginActivity.class));
                    finish();
                }
                mProgressBar.setVisibility(View.GONE);
                mCycleProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetResponseToken> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(self, t.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
                mCycleProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setupService() {
        service = RetrofitBuilder.createService(ApiService.class);
    }

    private void initView() {
        etEmail = findViewById(R.id.edit_text_email);
        btnSendEmail = findViewById(R.id.button_send_email);
        mProgressBar = findViewById(R.id.progress_bar_login);
        mCycleProgressBar = mProgressBar.findViewById(R.id.progress_bar_cycle);
    }
}