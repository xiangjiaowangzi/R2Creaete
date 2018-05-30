package xjr.com.mylibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by LiuBin.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    TextView textView = findViewById(R.id.test_1);
    textView.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R2.id.test_1:
        break;
      case R2.id.new_test:
        break;
      case R2.id.btn1:
        break;
    }
  }
}
