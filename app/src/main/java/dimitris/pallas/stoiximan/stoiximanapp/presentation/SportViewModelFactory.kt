package dimitris.pallas.stoiximan.stoiximanapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dimitris.pallas.stoiximan.stoiximanapp.domain.repository.SportRepository
import javax.inject.Inject

class SportViewModelFactory @Inject constructor(
    private val repository: SportRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SportViewModel(repository) as T
    }
}