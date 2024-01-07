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
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
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
        // RadioGroupを取得する
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        // ラジオボタンの選択状態が変更された時のリスナーを設定する
        radioGroup.setOnCheckedChangeListener{ group, checkedId ->
            // 選択されたラジオボタンのIDから、選択されたラジオボタンを取得する
            val radio = group.findViewById<RadioButton>(checkedId)
            // トーストで選択されたラジオボタンのテキストを表示する
            Toast.makeText(
                this@MainActivity,
                String.format("選択されたラジオボタンは%sです", radio.text),
                Toast.LENGTH_SHORT,
                ).show()
        }
        // シークバーを取得する
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        // シークバーの値が変更された時のリスナーを設定する
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // シークバーの値が変更された時に呼ばれる
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean,
            ) {
                // トーストでシークバーの値を表示する
                // ここで表示しちゃうと、シークバーを動かすたびにトーストが表示されるので、邪魔
//                Toast.makeText(
//                    this@MainActivity,
//                    String.format("シークバーの値は%dです", progress),
//                    Toast.LENGTH_SHORT,
//                ).show()
            }
            // シークバーのつまみに触れた時に呼ばれる
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            // シークバーのつまみを離した時に呼ばれる
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val current = if (seekBar?.progress != null) {
                    (seekBar.progress - 10) * 10
                } else {
                    0
                }
                // トーストでシークバーの値を表示する
                Toast.makeText(
                    this@MainActivity,
                    String.format("シークバーの値は%dです", current),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        })
    }
}