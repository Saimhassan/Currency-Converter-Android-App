package saim.hassan.currencyconverter.main

import saim.hassan.currencyconverter.data.models.CurrencyResponse
import saim.hassan.currencyconverter.util.Resource


interface MainRepository {
    suspend fun getRates(base:String):Resource<CurrencyResponse>
}