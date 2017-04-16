package vn.jupiter.droidcondemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.lucasr.probe.Filter;
import org.lucasr.probe.Interceptor;
import org.lucasr.probe.Probe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerformanceBenchmarkActivity extends AppCompatActivity {
    private static final int NO_OF_TRIES = 10;
    private static Map<Integer, DemoItem> sDemoItems = new HashMap<>();

    static {
        sDemoItems.put(R.id.menu_complex, new DemoItem(R.layout.screen_with_complex_layout,
                R.layout.screen_constraint_with_complex_layout));
        sDemoItems.put(R.id.menu_grid,
                new DemoItem(R.layout.screen_with_grid_layout, R.layout.screen_constraint_with_grid_layout));
        sDemoItems.put(R.id.menu_linear,
                new DemoItem(R.layout.screen_with_linear_layout, R.layout.screen_constraint_with_linear_layout));
    }

    @BindView(R.id.fl_content)
    ScrollView svContent;
    @BindView(R.id.tv_normal_time)
    TextView tvNormal;
    @BindView(R.id.tv_constraint_time)
    TextView tvConstraint;
    private MeasureTimeInterceptor interceptor;
    private DemoItem currentDemoItem = sDemoItems.get(R.id.menu_linear);

    private double totalNormalTime = 0;
    private double totalConstraintTime = 0;
    private boolean isInNormalMode = false;
    private int measureTimes = 0;
    private Handler inflateHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        interceptor = new MeasureTimeInterceptor();
        Probe.deploy(this, interceptor, new Filter.ParentId(R.id.fl_content));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmark);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_benchmark, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        currentDemoItem = sDemoItems.get(item.getItemId());
        tvNormal.setText("0ms");
        tvConstraint.setText("0ms");
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.bt_normal)
    public void onNormalLayoutClicked() {
        isInNormalMode = true;
        measureTimes = 0;
        totalNormalTime = 0;
        doNormalInflation();
    }

    private void doNormalInflation() {
        svContent.removeAllViews();
        svContent.addView(getLayoutInflater().inflate(currentDemoItem.oldLayoutId, svContent, false));
    }

    private void doConstraintInflation() {
        svContent.removeAllViews();
        svContent.addView(getLayoutInflater().inflate(currentDemoItem.constraintLayoutId, svContent, false));
    }

    @OnClick(R.id.bt_constraint)
    public void onConstraintLayoutClicked() {
        isInNormalMode = false;
        measureTimes = 0;
        totalConstraintTime = 0;
        doConstraintInflation();
    }

    @OnClick(R.id.bt_remeasure)
    public void onRemeasureClicked() {
        if (isInNormalMode) {
            onNormalLayoutClicked();
        } else {
            onConstraintLayoutClicked();
        }
    }

    private void increaseMeasureTime(long measureTime) {
        if (isInNormalMode) {
            totalNormalTime += (measureTime / 1000000.f);
        } else {
            totalConstraintTime += (measureTime / 1000000.f);
        }
        measureTimes++;
        if (measureTimes % 2 == 0) {
            updateText();
        }
    }

    private void updateText() {
        Log.d("D.Vu", "updateText: " + (measureTimes) + " " + totalNormalTime + " " + totalConstraintTime);
        if (isInNormalMode) {
            tvNormal.setText(String.format("%.2fms", totalNormalTime / (measureTimes / 2)));
        } else {
            tvConstraint.setText(String.format("%.2fms", totalConstraintTime / (measureTimes / 2)));
        }
        inflateHandler.removeCallbacksAndMessages(null);
        if (measureTimes < (NO_OF_TRIES * 2)) {
            inflateHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isInNormalMode) {
                        doNormalInflation();
                    } else {
                        doConstraintInflation();
                    }
                }
            }, 500);
        }
    }

    private void increaseLayoutTime(long layoutTime) {
        if (isInNormalMode) {
            totalNormalTime += (layoutTime / 1000000.f);
        } else {
            totalConstraintTime += (layoutTime / 1000000.f);
        }
        measureTimes++;
        if (measureTimes % 2 == 0) {
            updateText();
        }
    }

    private static class DemoItem {

        int oldLayoutId;
        int constraintLayoutId;

        public DemoItem(int oldLayoutId, int constraintLayoutId) {
            this.oldLayoutId = oldLayoutId;
            this.constraintLayoutId = constraintLayoutId;
        }
    }

    private class MeasureTimeInterceptor extends Interceptor {

        @Override
        public void onMeasure(View view, int widthMeasureSpec, int heightMeasureSpec) {
            long startTime = System.nanoTime();
            super.onMeasure(view, widthMeasureSpec, heightMeasureSpec);
            Log.d("D.Vu", "onMeasure: " + view + " " + (System.nanoTime() - startTime));
            increaseMeasureTime(System.nanoTime() - startTime);
        }

        @Override
        public void onLayout(View view, boolean changed, int l, int t, int r, int b) {
            long startTime = System.nanoTime();
            super.onLayout(view, changed, l, t, r, b);
            Log.d("D.Vu", "onLayout: " + view + " " + (System.nanoTime() - startTime));
            increaseLayoutTime(System.nanoTime() - startTime);
        }



    }

}
