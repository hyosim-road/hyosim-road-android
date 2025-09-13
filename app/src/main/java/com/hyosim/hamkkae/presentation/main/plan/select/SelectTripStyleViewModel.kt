package com.hyosim.hamkkae.presentation.main.plan.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.domain.model.TripStyle

class SelectTripStyleViewModel: ViewModel() {
    val tripStyleList = listOf(
        TripStyle(1, "자연",   "NATURE", R.drawable.ic_style_nature),
        TripStyle(2, "사찰",   "TEMPLE", R.drawable.ic_style_temple),
        TripStyle(3, "역사",   "HISTORY", R.drawable.ic_style_history),
        TripStyle(4, "식도락", "FOOD", R.drawable.ic_style_food),
//        TripStyle(5, "문화",   R.drawable.ic_style_culture),
//        TripStyle(6, "쇼핑",   R.drawable.ic_style_shopping),
//        TripStyle(7, "힐링",   R.drawable.ic_style_healing),
//        TripStyle(8, "체험",   R.drawable.ic_style_experience)
    )

    /*private val _selectedStyles = MutableLiveData<List<TripStyle>>(emptyList())
    val selectedStyles: LiveData<List<TripStyle>> = _selectedStyles*/
    private val _selectedStyle = MutableLiveData<TripStyle?>()
    val selectedStyle: LiveData<TripStyle?> = _selectedStyle

    fun selectStyle(style: TripStyle) {
        _selectedStyle.value = style
    }


   /* fun toggleTripStyleSelection(tripStyle: TripStyle) {
        val currentList = _selectedStyles.value.orEmpty().toMutableList()

        if (currentList.contains(tripStyle)) {
            currentList.remove(tripStyle)
        } else {
            currentList.add(tripStyle)
        }
        _selectedStyles.value = currentList
    }*/
}