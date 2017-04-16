package vn.jupiter.droidcondemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConstrainSetActivity extends AppCompatActivity {
    @BindView(R.id.root_layout)
    ConstraintLayout constraintRootLayout;

    private ConstraintSet targetSet;
    private ConstraintSet originalSet;
    private boolean shouldShuffle = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_constraint_set);
        ButterKnife.bind(this);
        originalSet = new ConstraintSet();
        originalSet.clone(constraintRootLayout);
        targetSet = new ConstraintSet();
        targetSet.clone(this, R.layout.screen_destination_constraint_set);
    }

    @OnClick(R.id.bt_shuffle)
    public void onShuffle(View view) {
        TransitionManager.beginDelayedTransition(constraintRootLayout);
        if (shouldShuffle) {
            targetSet.applyTo(constraintRootLayout);
        } else {
            originalSet.applyTo(constraintRootLayout);
        }
        shouldShuffle = !shouldShuffle;
    }
}
