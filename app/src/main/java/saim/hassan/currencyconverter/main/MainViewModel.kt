package saim.hassan.currencyconverter.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import saim.hassan.currencyconverter.util.DispatcherProvider

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
):ViewModel() {
    sealed class CurrencyEvent {
        class Success(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion
    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String,
    ){
         val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null){
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }
        viewModelScope.launch {
            
        }

    }
}