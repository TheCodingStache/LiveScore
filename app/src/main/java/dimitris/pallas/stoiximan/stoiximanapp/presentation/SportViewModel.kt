package dimitris.pallas.stoiximan.stoiximanapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import dimitris.pallas.stoiximan.stoiximanapp.domain.repository.SportRepository
import kotlinx.coroutines.flow.onEach

class SportViewModel(
    private val repository: SportRepository,
) : ViewModel() {
    val loader = MutableLiveData<Boolean>()
    val sports = liveData {
        loader.postValue(true)
        emitSource(
            repository.getSport()
                .onEach {
                    loader.postValue(false)
                }
                .asLiveData()
        )
    }

//    val sports = MutableLiveData<Result<List<SportsModel>>>()
//    init {
//        viewModelScope.launch {
//            repository.getSport().collect { events ->
//                sports.value = events
//            }
//        }
//    }
}