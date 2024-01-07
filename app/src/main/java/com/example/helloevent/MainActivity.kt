package com.example.helloevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.helloevent.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val txtResult = findViewById<TextView>(R.id.txtResult)
        outState.putString("txtResult", txtResult.text.toString())
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle,
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        val txtResult = findViewById<TextView>(R.id.txtResult)
        txtResult.text = savedInstanceState.getString("txtResult")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCurrent.setOnClickListener {
            val today = Date().toString()
            binding.txtResult.text = today
            val toast = Toast.makeText(this, today, Toast.LENGTH_LONG)
            toast.show()
            Log.d("CurrentTime", today)
        }
        val imgButton = findViewById<ImageButton>(R.id.imgButton)
        val editName = findViewById<EditText>(R.id.editName)
        val textResult = findViewById<TextView>(R.id.textResult)
        imgButton.setOnClickListener {
            textResult.text = getString(R.string.greet, editName.text)
        }
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        // チェックボックスをコード上から変更する場合もあるので、setOnCheckedChangeListenerを使う
        // クリックだけだったら、setOnClickListenerで良い
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this@MainActivity,
                if (isChecked) "メール送信ON" else "メール送信OFF",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}