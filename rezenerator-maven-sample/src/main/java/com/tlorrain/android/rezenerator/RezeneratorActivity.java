
package com.tlorrain.android.rezenerator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class RezeneratorActivity
    extends Activity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater();
        return true;
    }

}
