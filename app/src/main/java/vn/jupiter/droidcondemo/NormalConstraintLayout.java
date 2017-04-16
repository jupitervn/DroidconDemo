package vn.jupiter.droidcondemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NormalConstraintLayout extends AppCompatActivity {
    @BindView(R.id.bt_like)
    View btLike;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_constraint_margin);
        ButterKnife.bind(this);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btLike.getLayoutParams();
        layoutParams.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE;

    }
}
