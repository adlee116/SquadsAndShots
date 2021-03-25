package com.shots.squads_and_shots.presentation.mainGameRoom

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shots.squads_and_shots.databinding.GameRoomBinding
import com.shots.squads_and_shots.network.models.DrinkOccasion
import com.shots.squads_and_shots.presentation.homePage.GameChooserDialog
import com.shots.squads_and_shots.presentation.models.SecretTasks
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainGameRoom : AppCompatActivity() {

    private val viewModel: GameRoomViewModel by viewModel()

    private lateinit var binding: GameRoomBinding
    private lateinit var generalRulesAdapter: RulesAdapter
    private lateinit var nominatedRulesAdapter: RulesAdapter
    private lateinit var roomCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GameRoomBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setObservers()
        setAdapters()
        val code = intent.getStringExtra(GameChooserDialog.GAME_CODE)
        code?.let {
            binding.lobbyCode.text = it
            viewModel.createValueEventListener(it)
            roomCode = it
            viewModel.observeDrinkHistoryList(roomCode)
        }
    }

    private fun setObservers() {
        viewModel.roomModelLiveData.observe(this, Observer {
            val generalRules = it.generalRules ?: emptyList()
            val nominatedRules = it.nominatedRules ?: emptyList()
            val generalRuleAdapterItems = viewModel.createGeneralRuleItems(generalRules)
            val nominatedRuleAdapterItems = viewModel.createNominatedRuleItems(nominatedRules)
            generalRulesAdapter.updateItems(generalRuleAdapterItems)
            nominatedRulesAdapter.updateItems(nominatedRuleAdapterItems)
            it.secretTasks?.let { secretTasks ->
                viewModel.secretTaskAvailable(secretTasks, viewModel.getUserUniqueId())
                    ?.let { task ->
                        setSecretTask(task)
                    } ?: run {
                    hideSecretTask()
                }
            } ?: run {
                hideSecretTask()
            }
        })

        viewModel.drinkHistory.observe(this, Observer {
            viewModel.checkIfINeedToDrink(it)
        })

        viewModel.drinkOccasions.observe(this, Observer {
            if (it.isNotEmpty()) {
                alertTheUser()
                showTimeToDrinkDialog(it)
            }
        })
    }

    private fun setAdapters() {
        // General Rules Adapter
        generalRulesAdapter = RulesAdapter(onClickListeners)
        val generalRulesLayoutManager = LinearLayoutManager(this)
        generalRulesLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rulesRecycler.layoutManager = generalRulesLayoutManager
        binding.rulesRecycler.adapter = generalRulesAdapter

        // Nominated Rules Adapter
        nominatedRulesAdapter = RulesAdapter(onClickListeners)
        val nominatedRulesLayoutManager = LinearLayoutManager(this)
        nominatedRulesLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.nominatedRuleRecycler.layoutManager = nominatedRulesLayoutManager
        binding.nominatedRuleRecycler.adapter = nominatedRulesAdapter
    }

    private fun setSecretTask(task: SecretTasks) {
        binding.secretTaskTitle.text = task.title
        binding.secretTaskItem.setOnClickListener {
            showRuleDetail(
                viewModel.createRuleViewHolderItem(
                    task.title, task.description, task.image,
                    task.players
                )
            )
        }
    }

    private fun showRuleDetail(task: RuleViewHolderItem) {
        val fragment = DrinkDetailFragment.newInstance(task.title, task.description)
        fragment.show(this.supportFragmentManager, "AreYouSureDrinkPromptFragment")
    }

    private fun showDrinkAreYouSure(rule: RuleViewHolderItem) {
        val fragment = AreYouSureDrinkPromptFragment.newInstance(
            rule.player,
            roomCode,
            rule.title,
            rule.ruleFunnyString
        )
        fragment.show(this.supportFragmentManager, "AreYouSureDrinkPromptFragment")
    }

    private val onClickListeners = object : RulesViewHolder.OnClickListener {
        override fun drinkClicked(rule: RuleViewHolderItem) {
            showDrinkAreYouSure(rule)
        }

        override fun ruleCardClicked(rule: RuleViewHolderItem) {
            showRuleDetail(rule)
        }
    }

    private fun alertTheUser() {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    private fun hideSecretTask() {
        binding.secretTaskItem.isVisible = false
        binding.secretTaskTitle.isVisible = false
        binding.secretDownButtonImage.isVisible = false
        binding.secretTitle.isVisible = false
    }

    private fun showTimeToDrinkDialog(list: List<DrinkOccasion>) {
        val funnyList = mutableListOf<String>()
        list.forEach {
            funnyList.add(it.ruleFunnyString)
        }
        val fragment = TimeToDrinkFragment.newInstance(funnyList.size, funnyList as ArrayList<String>)
        fragment.show(this.supportFragmentManager, "TimeToDrinkFragment")
    }

}