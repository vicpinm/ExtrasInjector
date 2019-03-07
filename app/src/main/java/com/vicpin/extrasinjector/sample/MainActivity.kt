package com.vicpin.extrasinjector.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vicpin.extrasinjector.Activities
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        next.setOnClickListener {
            val intent = Activities.intentForDetailsActivity(this,
                    textOptional = stringValue.text.toString(),
                    booleanRequired = booleanValue.isChecked,
                    doubleRequired = decimalValue.text.toString().toDoubleOrNull() ?: 0.0)

            startActivity(intent)
        }

    }

}

