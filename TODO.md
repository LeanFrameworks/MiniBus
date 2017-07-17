* Event timestamp
* Topics with multiple parents (a topic can be covered by several other topics)
* (Time?) validity per topic or per event/content
* Don't even send expired messages?
* Event request vs initial value map (with expiry time)
* Subscribe per class instead of topic (like IP)
* Unregister all handlers for a particular topic
* Multi-threading / asynchronous dispatching and handling
* Time-out while dispatching or hanging handler
* Multiple event buses/dispatchers
* Async dispatch (see Mycila PubSub)
* Queue for sync/async dispatch
* Post-during-dispatch strategy
* Weak references
* Priorities/ordering
* Async handling completion handler with(out) timeout (see Mycila PubSub)
* Wait for response, with(out) timeout (see Mycila PubSub)
* Multicast (channel)
* Regex filtering
* Veto
* Singleton, utility class, multiple buses/dispatchers
* Use of annotated method interfaces for publishing and consuming events (see Mycila PubSub)
* Temporarily disable/inhibit dispatch?
