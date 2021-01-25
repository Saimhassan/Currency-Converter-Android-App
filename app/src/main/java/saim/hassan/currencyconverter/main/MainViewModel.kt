package saim.hassan.currencyconverter.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.lifecycle.ViewModel
import saim.hassan.currencyconverter.util.DispatcherProvider

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
):ViewModel() {
}