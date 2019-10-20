package com.db.inout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.db.inout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TransViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(TransViewModel::class.java)
        setSpinner()
        setTextWatcher()
        binding.clear.setOnClickListener { clear() }
        binding.share.setOnClickListener {
            if (binding.resultText.text.isNotEmpty()) {
                send()
            } else {
                Toast.makeText(this, getString(R.string.blank_text), Toast.LENGTH_SHORT).show()
            }
        }

        binding.spinnerTarget.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    setViewModel(
                        binding.sourceText.text.toString().trim().toLowerCase(),
                        parent.selectedItem.toString().toLowerCase()
                    )
                }
            }
        }
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.language_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerTarget.adapter = adapter
        }
    }

    private fun send() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, binding.resultText.text)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }

    private fun clear() {
        binding.sourceText.setText("")
        binding.resultText.text = ""
    }

    private fun setTextWatcher() {
        binding.sourceText.onChange { s ->
            if (s.isNotEmpty()) {
                if (checkInternet(this)) {
                    setViewModel(s, binding.spinnerTarget.selectedItem.toString().toLowerCase())
                } else {
                    Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show()
                }
            } else {
                binding.resultText.text = ""
            }
        }
    }

    private fun setViewModel(s: String, targetLang: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.resultText.visibility = View.INVISIBLE
        viewModel.getResults(
            targetLang,
            "english",
            2,
            s
        ).observe(this, Observer {
            binding.resultText.text = it.responseList[0].run { outString[0] }
            binding.progressBar.visibility = View.INVISIBLE
            binding.resultText.visibility = View.VISIBLE
        })
    }

}
