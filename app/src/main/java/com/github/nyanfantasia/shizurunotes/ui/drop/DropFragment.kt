package com.github.nyanfantasia.shizurunotes.ui.drop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.data.Equipment
import com.github.nyanfantasia.shizurunotes.databinding.FragmentDropBinding
import com.github.nyanfantasia.shizurunotes.ui.BottomNaviFragmentDirections
import com.github.nyanfantasia.shizurunotes.ui.base.BaseHintAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelEquipment
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelQuest
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import com.github.nyanfantasia.shizurunotes.utils.Utils

class DropFragment : Fragment() {

    private lateinit var sharedEquipment: SharedViewModelEquipment
    private lateinit var sharedQuest: SharedViewModelQuest
    private lateinit var dropVM: DropViewModel
    private lateinit var binding: FragmentDropBinding
    private lateinit var mAdapter: GridSelectAdapter
    private var maxSpanNum = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedEquipment = ViewModelProvider(requireActivity())[SharedViewModelEquipment::class.java]
        sharedQuest = ViewModelProvider(requireActivity())[SharedViewModelQuest::class.java]
        dropVM = ViewModelProvider(this)[DropViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDropBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = GridSelectAdapter(requireContext(), sharedEquipment)

        //根据屏幕大小调整每行显示图标数
        if (Utils.screenRatio < 2.0) {
            maxSpanNum = 5
        }

        val mLayoutManager = GridLayoutManager(context, maxSpanNum).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when(mAdapter.getItemViewType(position)) {
                        BaseHintAdapter.HINT_TEXT -> maxSpanNum
                        else -> 1
                    }
                }
            }
        }
        binding.apply {
            setOptionItemClickListener(dropToolbar)
            dropRecycler.apply {
                layoutManager = mLayoutManager
                adapter = mAdapter
                setHasFixedSize(true)
            }
        }
        setFloatingBarClickListener()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        binding.dropToolbar.menu.findItem(R.id.menu_drop_normal).isChecked = sharedQuest.includeNormal
        binding.dropToolbar.menu.findItem(R.id.menu_drop_hard).isChecked = sharedQuest.includeHard
    }

    private fun setFloatingBarClickListener() {
        binding.clickListener = View.OnClickListener { view ->
            if (view.id == R.id.drop_floating_button) {
                if (sharedEquipment.selectedDrops.value?.isNotEmpty() == true) {
                    val idList = mutableListOf<Int>()
                    sharedEquipment.selectedDrops.value?.forEach {
                        idList.add(it.itemId)
                    }
                    UserSettings.get().lastEquipmentIds = idList
                }
                view.findNavController().navigate(BottomNaviFragmentDirections.actionNavBottomNavigationToNavDropQuest())
            }
        }
    }

    private fun setObservers() {
        sharedEquipment.equipmentMap.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                dropVM.refreshList(it.values.toList())
                mAdapter.update(dropVM.itemList)
            }
        }
        sharedEquipment.loadingFlag.observe(viewLifecycleOwner) {
            binding.dropProgressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setOptionItemClickListener(toolbar: Toolbar) {
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_drop_before -> {
                    val ids = UserSettings.get().lastEquipmentIds
                    if (ids.isNotEmpty()) {
                        clearRecyclerView()
                        ids.forEach { id ->
                            for (item in mAdapter.itemList) {
                                if (item is Equipment && item.equipmentId == id) {
                                    sharedEquipment.selectedDrops.value?.add(item)
                                    val vh = binding.dropRecycler.findViewHolderForAdapterPosition(mAdapter.itemList.indexOf(item))
                                    vh?.let {
                                        (vh as BaseHintAdapter.InstanceViewHolder).binding.root.background = AppCompatResources.getDrawable(requireContext(), R.drawable.shape_selected_background)
                                    }
                                    break
                                }
                            }
                        }
                    }
                    true
                }
                R.id.menu_drop_cancel -> {
                    clearRecyclerView()
                    true
                }
                R.id.menu_drop_normal -> {
                    it.isChecked = !it.isChecked
                    sharedQuest.includeNormal = it.isChecked
                    true
                }
                R.id.menu_drop_hard -> {
                    it.isChecked = !it.isChecked
                    sharedQuest.includeHard = it.isChecked
                    true
                }
                else -> true
            }
        }
    }

    private fun clearRecyclerView() {
        binding.dropRecycler.children.forEach {
            it.background = AppCompatResources.getDrawable(requireContext(), R.drawable.shape_unselected_background)
        }
        sharedEquipment.selectedDrops.value?.clear()
    }
}