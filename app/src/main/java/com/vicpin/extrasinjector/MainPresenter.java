package com.vicpin.extrasinjector;

import com.vicpin.extrasprocessor.annotations.ForActivity;
import com.vicpin.extrasprocessor.annotations.InjectExtra;

/**
 * Created by victor on 25/1/18.
 */

@ForActivity(activityClass = MainActivity.class)
public class MainPresenter {

    @InjectExtra String value;
}

