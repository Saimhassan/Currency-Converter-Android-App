package saim.hassan.currencyconverter.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import saim.hassan.currencyconverter.data.models.Rates
import saim.hassan.currencyconverter.util.DispatcherProvider
import saim.hassan.currencyconverter.util.Resource
import kotlin.math.round

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
        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when(val ratesResponse = repository.getRates(fromCurrency)){
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency,rates)
                    if (rate == null){
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    }else{
                        val convertedCurrency = round(fromAmount * rate * 100) /100
                        _conversion.value = CurrencyEvent.Success("$fromAmount $fromCurrency = $convertedCurrency $toCurrency")
                    }
                }
            }
        }

    }
    private fun getRateForCurrency(currency: String,rates:Rates) = when(currency){

        "AUD" -> rates.AUD
        "BGN" -> rates.BGN
        "EUR" -> rates.EUR
        "BRL" -> rates.BRL
        "CAD" -> rates.CAD
        "CHF" ->  rates.CHF
        "CNY" -> rates.CNY
        "CZK" ->  rates.CZK
        "DKK" -> rates.DKK
        "GBP" -> rates.GBP
        "HKD" -> rates.HKD
        "HRK" -> rates.HRK
        "HUF" -> rates.HUF
        "IDR" -> rates.IDR
        "ILS" -> rates.ILS
        "INR" -> rates.INR
        "ISK" -> rates.ISK
        "JPY" -> rates.JPY
        "KRW" -> rates.KRW
        "MXN" -> rates.MXN
        "MYR" -> rates.MYR
        "NOK" -> rates.NOK
        "NZD" -> rates.NZD
        "PHP" -> rates.PHP
        "PLN" -> rates.PLN
        "RON" -> rates.RON
        "RUB" -> rates.RUB
        "SEK" -> rates.SEK
        "SGD" -> rates.SGD
        "THB" -> rates.THB
        "TRY" -> rates.TRY
        "USD" -> rates.USD
        "ZAR" ->  rates.ZAR
        else -> null
    }
}