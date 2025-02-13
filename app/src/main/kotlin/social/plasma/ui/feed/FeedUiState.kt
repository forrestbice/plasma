package social.plasma.ui.feed

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import social.plasma.ui.components.NoteCardUiModel

sealed interface FeedUiState {
    object Loading : FeedUiState
    data class Loaded(
        val feedPagingFlow: Flow<PagingData<NoteCardUiModel>>,
    ) : FeedUiState
}
