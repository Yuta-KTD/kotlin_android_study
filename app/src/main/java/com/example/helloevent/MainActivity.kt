package com.example.helloevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.helloevent.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        // スピナーを取得する
        var isFirstSelection = true  // フラグを初期化

        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isFirstSelection) {
                    Toast.makeText(this@MainActivity, "選択項目: ${(parent as Spinner).selectedItem}", Toast.LENGTH_SHORT).show()
                } else {
                    isFirstSelection = false  // 初回の選択イベントを無視
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        createSpinner()
    }
    // スピナーに項目をセットするメソッド
    private fun createSpinner() {
        val list = mutableListOf<String>()
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
        val calendar = Calendar.getInstance()
        // 今日から1週間分の日付をリストに追加する
        for (i in 0..6) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + i)
            list.add(format.format(calendar.time))
        }
        // 配列をウィジェットに渡す準備
        val calenderSpinner = findViewById<Spinner>(R.id.dateSpinner)
        calenderSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            list,
        )
    }
}