package saim.hassan.currencyconverter

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import saim.hassan.currencyconverter.data.models.CurrencyResponse

interface CurrencyApi {
    @GET("/latest")
    suspend fun getRates(
        @Query("base") base:String
    ):Response<CurrencyResponse>
}