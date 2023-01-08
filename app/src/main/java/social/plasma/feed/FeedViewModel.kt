package social.plasma.feed

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.AndroidUiDispatcher
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import social.plasma.models.Note
import social.plasma.relay.Relays
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    recompositionClock: RecompositionClock,
    relays: Relays
) : ViewModel() {
    private val moleculeScope =
        CoroutineScope(viewModelScope.coroutineContext + AndroidUiDispatcher.Main)

    // TODO only subscribe to relays once during the entire app cycle
    // https://github.com/nostr-protocol/nips/blob/master/01.md#other-notes
    private val relaySubscription = relays.subscribe("wss://relay.damus.io").take(5)

    val uiState: StateFlow<FeedListUiState> = moleculeScope.launchMolecule(recompositionClock) {
        // TODO move this to some type of repository that can aggregate the list
        val messageList = remember { mutableStateListOf<Note>() }
        val newMessage by relaySubscription.collectAsState(initial = "")

        // TODO filter at repository level using strongly typed classes
        if (newMessage.isNotBlank() && newMessage.contains("kind\":1")) {
            messageList.add(Note(newMessage))
        }

        if (messageList.isEmpty()) {
            FeedListUiState.Loading
        } else {
            FeedListUiState.Loaded(messageList)
        }
    }
}