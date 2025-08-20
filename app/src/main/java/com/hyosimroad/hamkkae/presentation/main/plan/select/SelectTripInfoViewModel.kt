// SelectTripInfoViewModel.kt 라는 이름으로 새 파일을 만드세요.
package com.hyosimroad.hamkkae.presentation.main.plan.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyosimroad.hamkkae.R

class SelectTripInfoViewModel : ViewModel() {

    private val _startDate = MutableLiveData<String?>()
    val startDate: LiveData<String?> = _startDate

    private val _endDate = MutableLiveData<String?>()
    val endDate: LiveData<String?> = _endDate

    private val _personCount = MutableLiveData(0)
    val personCount: LiveData<Int> = _personCount

    private val _selectedBudget = MutableLiveData<Int?>()
    val selectedBudget: LiveData<Int?> = _selectedBudget

    fun setStartDate(date: String?) {
        _startDate.value = date
    }

    fun setEndDate(date: String?) {
        _endDate.value = date
    }

    fun setPersonCount(count: Int) {
        _personCount.value = count
    }

    fun setSelectedBudget(viewId: Int?) {
        _selectedBudget.value = viewId
    }
}