package com.github.nyanfantasia.shizurunotes.ui.charadetails

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.data.Chara
import com.github.nyanfantasia.shizurunotes.databinding.FragmentCharaDetailsBinding
import com.github.nyanfantasia.shizurunotes.ui.base.AttackPatternContainerAdapter
import com.github.nyanfantasia.shizurunotes.ui.base.BaseHintAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelChara
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelCharaFactory
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import com.github.nyanfantasia.shizurunotes.utils.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// TODO: Changed to use the ViewType interface and adapter,
//  to avoid frame loss caused by NestedScrollView rendering all views at one time
class CharaDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var detailsViewModel: CharaDetailsViewModel
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var binding: FragmentCharaDetailsBinding
    private val args: CharaDetailsFragmentArgs by navArgs()

    private val adapterSkill by lazy { SkillAdapter(sharedChara) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity())[SharedViewModelChara::class.java]
        detailsViewModel = ViewModelProvider(this, SharedViewModelCharaFactory(sharedChara))[CharaDetailsViewModel::class.java]

        sharedElementEnterTransition =
            context?.let {
                TransitionInflater.from(it)
                    .inflateTransition(android.R.transition.move).setDuration(300)
            }

        sharedElementReturnTransition =
            context?.let {
                TransitionInflater.from(it)
                    .inflateTransition(android.R.transition.move).setDuration(300)
            }
    }

//    override fun onResume() {
//        super.onResume()
//        binding.toolbar.menu.findItem(R.id.menu_chara_show_expression).isChecked = UserSettings.get().getExpression()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCharaDetailsBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            detailsItemChara.transitionName = "transItem_${args.charaId}"
            if (sharedChara.backFlag)
                appbar.setExpanded(false, false)
            detailsVM = detailsViewModel
            clickListener = this@CharaDetailsFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            bigImageView.setOnLongClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    MaterialAlertDialogBuilder(bigImageView.context)
                        .setTitle(R.string.save_image_confirmation)
                        .setMessage(R.string.save_image_message)
                        .setPositiveButton(R.string.save_image_accept) { _, _ ->

                            val imgUrl = sharedChara.selectedChara?.imageUrl?.removeSuffix("@w800")
                            Glide.with(this.bigImageView)
                                .asBitmap()
                                .load(imgUrl)
                                .into(
                                    object : CustomTarget<Bitmap>() {
                                        override fun onResourceReady(
                                            resource: Bitmap,
                                            transition: Transition<in Bitmap>?
                                        ) {
                                            val unitId = sharedChara.selectedChara?.unitId
                                            Utils.saveBitmap(it.context, resource, "$unitId.png")
                                        }

                                        override fun onLoadCleared(placeholder: Drawable?) {
                                            // this is called when imageView is cleared on lifecycle call or for
                                            // some other reason.
                                            // if you are referencing the bitmap somewhere else too other than this imageView
                                            // clear it here as you can no longer have the bitmap
                                        }
                                    })
                            Toast.makeText(it.context, R.string.image_save_to_storage, Toast.LENGTH_LONG).show()

                        }
                        .setNegativeButton(R.string.save_image_decline) { _, _ -> }
                        .show()

                } else {
                    Toast.makeText(it.context, R.string.save_image_not_supported_sdk_level, Toast.LENGTH_LONG).show()
                }
                true
            }
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_chara_customize -> {
                        Navigation.findNavController(binding.root).navigate(
                            CharaDetailsFragmentDirections.actionNavCharaDetailsToNavAnalyze()
                        )
                    }
                    R.id.menu_chara_show_expression -> {
                        val singleItems = I18N.getStringArray(R.array.setting_kill_expression_options)
                        val checkedItem = UserSettings.get().getExpression()
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(I18N.getString(R.string.setting_skill_expression_title))
                            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                                if (UserSettings.get().getExpression() != which) {
                                    UserSettings.get().setExpression(which)
                                    sharedChara.mSetSelectedChara(sharedChara.selectedChara)
                                    adapterSkill.notifyDataSetChanged()
                                }
                                dialog.dismiss()
                            }.show()
                    }
                }
                true
            }
        }

        // Skill loop
        val adapterAttackPattern = AttackPatternContainerAdapter(context).apply {
            initializeItems(detailsViewModel.mutableChara.value?.attackPatternList)
        }
        binding.attackPatternRecycler.apply {
            layoutManager = GridLayoutManager(context, 6).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when(adapterAttackPattern.getItemViewType(position)) {
                            BaseHintAdapter.HINT_TEXT -> 6
                            else -> 1
                        }
                    }
                }
            }
            adapter = adapterAttackPattern
        }

        // Skill Recycler
        val layoutManagerSkill = LinearLayoutManager(context)
        binding.skillRecycler.apply {
            layoutManager = layoutManagerSkill
            adapter = adapterSkill
        }

        // Observe the changes of chara (it can be deleted after removing the rank drop-down box in 1.0.0, keep it as a backup)
        detailsViewModel.mutableChara.observe(viewLifecycleOwner) { chara: Chara ->
            binding.detailsVM = detailsViewModel
            adapterSkill.update(chara.skills)
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.detailsItemCharaContainer) {
            val action =
                CharaDetailsFragmentDirections.actionNavCharaDetailsToNavCharaProfile()
            Navigation.findNavController(v).navigate(action)
        }
    }
}