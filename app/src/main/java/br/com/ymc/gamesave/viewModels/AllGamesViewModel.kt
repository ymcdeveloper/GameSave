package br.com.ymc.gamesave.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllGamesViewModel : ViewModel()
{
    private val _badgeCount = MutableLiveData(0)
    val badgeCountLiveData: LiveData<Int> = _badgeCount

    private val _typedValue = MutableLiveData("")
    val typedValue: LiveData<String> = _typedValue

    fun saveValue(typed: String)
    {
        _typedValue.value = typed
    }

    fun increaseBadge()
    {
        _badgeCount.value = _badgeCount.value!!.plus(1)
    }
}