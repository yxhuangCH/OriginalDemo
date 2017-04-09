package com.yxhuang.retrofitdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.yxhuang.retrofitdemo.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPresenter = new MainPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void showNetworkResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }
}
