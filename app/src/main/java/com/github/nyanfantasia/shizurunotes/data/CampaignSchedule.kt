package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.utils.Utils
import java.time.LocalDateTime

class CampaignSchedule(
    id: Int,
    name: String,
    type: EventType,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    val category: Int,
    val campaignType: CampaignType,
    val value: Double,
    val systemId: Int
) : EventSchedule(id, name, type, startTime, endTime) {
    override val title: String by lazy {
        campaignType.description().format(Utils.roundIfNeed(value / 1000.0))
    }
    val shortTitle: String = campaignType.shortDescription().format(Utils.roundIfNeed(value / 1000.0))
}