package com.example.mjapp.ui.screen.accountbook.add

import androidx.annotation.DrawableRes
import com.example.mjapp.R

enum class IncomeExpenditureType(
    val type: String,
    val typeName: String,
    @DrawableRes val imageRes: Int
) {
    Meal("meal", "식비", R.drawable.ic_meal),
    Network("network", "인터넷", R.drawable.ic_network),
    Shopping("shopping", "쇼핑", R.drawable.ic_shopping),
    Butty("butty", "미용", R.drawable.ic_butty),
    Game("game", "게임", R.drawable.ic_game),
    Study("study", "학습", R.drawable.ic_study),
    Life("life", "생활", R.drawable.ic_game),
    Entertainment("entertainment", "모임", R.drawable.ic_entertainment),
    Hospital("hospital", "의료", R.drawable.ic_hospital),
    Salary("salary", "급여", R.drawable.ic_salary),
    Transport("transport", "교통", R.drawable.ic_transport),
    CommunicationCost("communication_cost", "통신", R.drawable.ic_communication_cost),
    Bank("bank", "금융", R.drawable.ic_bank),
    FamilyEvent("family_event", "경조사", R.drawable.ic_family_event),
    Other("other", "기타", R.drawable.ic_other);

    companion object {
        fun getImageByType(type: String) = values().firstOrNull { it.type == type }?.imageRes ?: R.drawable.ic_other

        fun getNameByType(type: String) = values().firstOrNull { it.type == type }?.typeName ?: ""
    }
}