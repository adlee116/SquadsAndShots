package com.shots.squads_and_shots.presentation.homePage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shots.squads_and_shots.databinding.HomePageBinding

class HomePage : AppCompatActivity() {

    private lateinit var binding: HomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setListeners()
    }

    private fun setListeners() {
        binding.createGameButton.setOnClickListener {
            if (isNameValid()) {
                createGameChooserDialog(getNameText().toString())
            } else {
                Toast.makeText(this, "What do we call you? Add your name", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.joinGameButton.setOnClickListener {
            if (isNameValid()) {
                createCodeAskingDialog(getNameText().toString())
            } else {
                Toast.makeText(this, "What do we call you? Add your name", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun createCodeAskingDialog(userName: String) {
        val fragment = GameCodeInputDialog.newInstance(userName)
        fragment.show(this.supportFragmentManager, "GameChooserDialog")
    }

    private fun createGameChooserDialog(userName: String) {
        val fragment = GameChooserDialog.newInstance(userName)
        fragment.show(this.supportFragmentManager, "GameChooserDialog")
    }

    private fun isNameValid(): Boolean {
        return getNameText()?.isNotEmpty() ?: false
    }

    private fun getNameText(): CharSequence? {
        return binding.nameEditText.text
    }
}