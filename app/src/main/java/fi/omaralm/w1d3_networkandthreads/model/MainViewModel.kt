package fi.omaralm.w1d3_networkandthreads.model

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository
) : ViewModel() {
    private val userId: String = savedStateHandle["uid"] ?:
    throw IllegalArgumentException("Missing user ID")

    private val _user = MutableLiveData<UserService.User>()
    val user = _user as LiveData<UserService.User>

    init {
        viewModelScope.launch {
            try {
                // Calling the repository is safe as it will move execution off
                // the main thread
                val user = userRepository.getUserById(userId)
                _user.value
            } catch (error: Exception) {
                // show error message to user
            }

        }
    }
}


