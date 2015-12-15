These are the new requirements we have to fulfill if we want our software to be
competitive. So in other words we have to be:
• Modular/dynamic: This way, we will be able to have 24/7 systems, because
modules can go offline and come online without breaking or halting the
entire system. Additionally, this helps us better structure our applications as
they grow larger and manage their code base.
• Scalable: This way, we are going to be able to handle a huge amount of data
or large numbers of user requests.
• Fault-tolerant: This way, the system will appear stable to its users.
• Responsive: This means fast and available.

Iterators and observables are very similar abstraction to begin with.
Major difference lies in the strategy of data retrieval.
Iterators i.e list use a pull mechanism(user is constantly polling for new stream of data) 
whereas observable use a push mechanism. So when data is ready the data is pushed as notification to 
the subscriber.
  