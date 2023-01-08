package social.plasma.relay

import java.time.Instant
import java.util.*

data class RequestMessage(
    val subscriptionId: String = "plasma-request-${UUID.randomUUID()}",
    val filters: Filters = Filters(since = Instant.now())
)

