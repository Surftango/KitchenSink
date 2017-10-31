package com.maa.ca.kitchensink;

import android.os.Bundle;
import android.view.View;

/**
 * Created by sugsh04 on 5/25/16.
 */
public class FragmentActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_layout);
        initializeNavigation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bFragmentActivity:
                break;
            default:
                super.onClick(v);
        }
    }
}
