package com.example.lab1.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_SUM = "com.example.lab1.SUM"
const val SUM_KEY = "sum"
const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {
    private var sum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.okButton)
        button.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                sum += editText.text.toString().toInt()
                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra(EXTRA_SUM, sum)
                }
                editText.text.clear()
                startActivityForResult(intent, REQUEST_CODE)
            } else {
                Toast.makeText(this, R.string.empty_field_toast, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                sum = data.getIntExtra(EXTRA_SUM, 0)
            }
        }
        textView.text = sum.toString()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        sum = savedInstanceState.getInt(SUM_KEY)
        textView.text = sum.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(SUM_KEY, sum)
            super.onSaveInstanceState(outState)
        }
    }
}
