package com.example.lab1.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.R
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sum = intent.getIntExtra(MainActivity.EXTRA_SUM, 0)

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.okButton)

        textView.text = sum.toString()
        button.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                sum += editText.text.toString().toInt()
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(MainActivity.EXTRA_SUM, sum)
                }
                setResult(Activity.RESULT_OK, intent)
                editText.text.clear()
                finish()
            } else {
                Toast.makeText(this, R.string.empty_field_toast, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
