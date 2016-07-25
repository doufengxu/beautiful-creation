package com.example.zuimeichuangyi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zuimeichuangyi.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplyMaterialActivity extends AppCompatActivity {

    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplymaterial);
        initView();

    }

    private void initView() {
        et_email = (EditText) findViewById(R.id.et_email);
    }

    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

                Matcher matcher = pattern.matcher("a@aa.com");

                String et_emailstr  = et_email.getText().toString().trim();

                if (et_emailstr.equals(matcher)){

                    Toast.makeText(SupplyMaterialActivity.this, "亲,提交成功,谢谢您的支持!", Toast.LENGTH_SHORT).show();

                }

                finish();
                break;

            default:
                break;
        }
    }
    @Override
    public void finish() {

        super.finish();

    }
}

