package com.github.nyanfantasia.shizurunotes.ui.secretdungeon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nyanfantasia.shizurunotes.data.SecretDungeonSchedule
import com.github.nyanfantasia.shizurunotes.databinding.FragmentSecretDungeonBinding
import com.github.nyanfantasia.shizurunotes.ui.base.ViewType
import com.github.nyanfantasia.shizurunotes.ui.base.ViewTypeAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelSecretDungeon
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelSecretDungeonFactory

class SecretDungeonFragment : Fragment(), OnSecretDungeonScheduleClickListener<SecretDungeonSchedule> {
    private lateinit var binding: FragmentSecretDungeonBinding
    private lateinit var sharedSecretDungeon: SharedViewModelSecretDungeon
    private lateinit var secretDungeonVM: SecretDungeonViewModel
    private val secretDungeonAdapter by lazy { ViewTypeAdapter<ViewType<*>>(onItemActionListener = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedSecretDungeon = ViewModelProvider(requireActivity())[SharedViewModelSecretDungeon::class.java]
        secretDungeonVM = ViewModelProvider(this, SharedViewModelSecretDungeonFactory(sharedSecretDungeon))[SecretDungeonViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecretDungeonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedSecretDungeon.loadSecretDungeonSchedules()
        sharedSecretDungeon.secretDungeonScheduleList.observe(viewLifecycleOwner) {
            binding.secretDungeonProgressBar.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            secretDungeonAdapter.setUpdatedList(secretDungeonVM.viewList)
        }
        binding.secretDungeonToolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
        with (binding.secretDungeonRecycler) {
            adapter = secretDungeonAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onSecretDungeonScheduleClicked(item: SecretDungeonSchedule) {
        sharedSecretDungeon.selectedSchedule = item
        findNavController().navigate(SecretDungeonFragmentDirections.actionNavSecretDungeonToNavSecretDungeonWave())
    }

    override fun onItemClicked(position: Int) {
    }
}