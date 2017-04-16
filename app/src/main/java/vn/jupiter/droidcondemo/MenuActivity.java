package vn.jupiter.droidcondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.bt_constraint_set)
    public void onConstraintSetClicked(View v) {
        startActivity(new Intent(this, ConstrainSetActivity.class));
    }

    @OnClick(R.id.bt_performance)
    public void onConstraintBenchmark(View v) {
        startActivity(new Intent(this, PerformanceBenchmarkActivity.class));
    }

    @OnClick(R.id.bt_tricks)
    public void onTricksClicked(View view) {
        startActivity(new Intent(this, ConstraintTricksActivity.class));
    }

}
