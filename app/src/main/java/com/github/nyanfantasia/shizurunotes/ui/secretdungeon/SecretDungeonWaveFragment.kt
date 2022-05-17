package com.github.nyanfantasia.shizurunotes.ui.secretdungeon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.github.nyanfantasia.shizurunotes.data.WaveGroup
import com.github.nyanfantasia.shizurunotes.databinding.FragmentSecretDungeonWaveBinding
import com.github.nyanfantasia.shizurunotes.ui.base.ViewType
import com.github.nyanfantasia.shizurunotes.ui.base.ViewTypeAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelSecretDungeon
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelSecretDungeonFactory

class SecretDungeonWaveFragment : Fragment(), OnSecretDungeonQuestClickListener {
    private lateinit var binding: FragmentSecretDungeonWaveBinding
    private lateinit var sharedSecretDungeon: SharedViewModelSecretDungeon
    private lateinit var sharedClanBattle: SharedViewModelClanBattle
    private lateinit var secretDungeonWaveVM: SecretDungeonWaveViewModel
    private val secretDungeonWaveAdapter by lazy { ViewTypeAdapter<ViewType<*>>(onItemActionListener = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedSecretDungeon = ViewModelProvider(requireActivity())[SharedViewModelSecretDungeon::class.java]
        sharedClanBattle = ViewModelProvider(requireActivity())[SharedViewModelClanBattle::class.java]
        secretDungeonWaveVM = ViewModelProvider(this, SharedViewModelSecretDungeonFactory(sharedSecretDungeon))[SecretDungeonWaveViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecretDungeonWaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.secretDungeonWaveToolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
        secretDungeonWaveAdapter.setList(secretDungeonWaveVM.viewList)
        with (binding.secretDungeonWaveRecycler) {
            adapter = secretDungeonWaveAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }
    }

    override fun onSecretDungeonQuestClicked(waveGroup: WaveGroup) {
        sharedClanBattle.mSetSelectedBoss(waveGroup.enemyList)
        findNavController().navigate(SecretDungeonWaveFragmentDirections.actionNavSecretDungeonWaveWaveToNavEnemy())
    }

    override fun onItemClicked(position: Int) {
    }
}