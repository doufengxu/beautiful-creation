package com.example.zuimeichuangyi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zuimeichuangyi.R;

public class FeedBackActivity extends AppCompatActivity {

    private EditText editTextEmile,editTextText;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        init();
    }

    private void init() {
        editTextEmile= (EditText) findViewById(R.id.edittext_feedback);
        editTextText= (EditText) findViewById(R.id.edittext_text);
        textView= (TextView) findViewById(R.id.text_feed);
    }

    public void clickFeedBack(View view) {
        String emile=editTextEmile.getText().toString().trim();
        String str=editTextText.getText().toString().trim();

        if (emile.equals("")){
            if (str.equals("")){
                textView.setVisibility(View.VISIBLE);
            }else {
                textView.setVisibility(View.GONE);
                editTextEmile.setText("");
                editTextText.setText("");
                Toast.makeText(FeedBackActivity.this,"提交成功,不介意给我们32个赞吧",Toast.LENGTH_SHORT).show();
            }
        }else if ((emile.endsWith("@163.com")||emile.endsWith("@qq.com")||emile.endsWith("@126.com")
                ||emile.endsWith("@sina.com")||emile.endsWith("@sohu.com"))){
            if (str.equals("")){
                textView.setVisibility(View.VISIBLE);
            }else {
                textView.setVisibility(View.GONE);
                editTextEmile.setText("");
                editTextText.setText("");
                Toast.makeText(FeedBackActivity.this,"提交成功,不介意给我们32个赞吧",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(FeedBackActivity.this,"请输入正确的邮箱",Toast.LENGTH_SHORT).show();

        }

    }
}
