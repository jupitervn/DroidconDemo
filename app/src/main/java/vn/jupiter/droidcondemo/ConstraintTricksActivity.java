package vn.jupiter.droidcondemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ConstraintTricksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tricks, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_center_fab:
                setContentView(R.layout.screen_with_center_fab);
                break;
            case R.id.menu_center_view:
                setContentView(R.layout.screen_with_around_center_of_view);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
