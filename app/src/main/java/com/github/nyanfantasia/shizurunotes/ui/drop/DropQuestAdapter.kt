package com.github.nyanfantasia.shizurunotes.ui.drop

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.data.Item
import com.github.nyanfantasia.shizurunotes.data.Quest
import com.github.nyanfantasia.shizurunotes.databinding.ItemDropEquipmentBinding
import com.github.nyanfantasia.shizurunotes.databinding.ItemHintTextBinding
import com.github.nyanfantasia.shizurunotes.databinding.ItemQuestDropBinding
import com.github.nyanfantasia.shizurunotes.ui.base.BaseHintAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelEquipment

class DropQuestAdapter(
    private val mContext: Context,
    private val sharedEquipment: SharedViewModelEquipment
) : BaseHintAdapter<ItemQuestDropBinding, ItemHintTextBinding>(mContext, R.layout.item_quest_drop, R.layout.item_hint_text) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HintTextViewHolder -> {
                with(holder.binding as ItemHintTextBinding) {
                    hintText = itemList[position] as String
                    executePendingBindings()
                }
            }
            is InstanceViewHolder -> {
                val thisQuest = itemList[position] as Quest
                with(holder.binding as ItemQuestDropBinding) {
                    quest = thisQuest
                    when(thisQuest.questType) {
                        Quest.QuestType.Hard -> this.textQuestType.setBackgroundResource(R.drawable.shape_text_tag_background_variant)
                        else -> this.textQuestType.setBackgroundResource(R.drawable.shape_text_tag_background)
                    }
                    dropIconContainer.removeAllViews()
                    thisQuest.dropList.forEach {
                        val rewardItem = DataBindingUtil.inflate<ItemDropEquipmentBinding>(
                            LayoutInflater.from(mContext), R.layout.item_drop_equipment, dropIconContainer, false
                        ).apply {
                            reward = it
                        }
                        sharedEquipment.selectedDrops.value?.let { itemList ->
                            for (item: Item in itemList) {
                                if (it.rewardId % 10000 == item.itemId % 10000) {
                                    rewardItem.root.background = AppCompatResources.getDrawable(mContext, R.drawable.shape_text_border)
                                    break
                                }
                            }
                        }
                        dropIconContainer.addView(rewardItem.root, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
                    }
                    executePendingBindings()
                }
            }
        }
    }
}