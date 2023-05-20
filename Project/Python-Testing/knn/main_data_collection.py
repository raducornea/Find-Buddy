from knn.data.aggregated_data.AggregatedDataK import AggregatedDataK
from knn.data.aggregated_data.AggregatedDataKMetric import AggregatedDataKMetric
from knn.data.aggregated_data.AggregatedDataMetric import AggregatedDataMetric
from knn.knn_strategies.KNNEuclidian import KNNEuclidian
from knn.knn_strategies.KNNJaccard import KNNJaccard
from knn.knn_strategies.KNNCosine import KNNCosine
from knn.knn_subscribers.observers.DataObserver import DataObserver
from knn.knn_subscribers.subjects.PreferencesSubject import PreferencesSubject


k_values = [3, 5, 7, 11, 110]
metric_values = ["Cosine", "Euclidian", "Jaccard"]
subject = PreferencesSubject()

# prepare all observers
observers = []
for k in k_values:
    observers.append(DataObserver(KNNCosine(), "Cosine", k))
    observers.append(DataObserver(KNNEuclidian(), "Euclidian", k))
    observers.append(DataObserver(KNNJaccard(), "Jaccard", k))

# register all observers
for observer in observers:
    subject.register(observer)

# aggregated tables
aggregated_data = []
for k in k_values: aggregated_data.append(AggregatedDataK(k))
for metric in metric_values: aggregated_data.append(AggregatedDataMetric(metric))
for k in k_values:
    for metric in metric_values:
        aggregated_data.append(AggregatedDataKMetric(k, metric))

for i in range(10):
    subject.shuffle_preferences()

    for observer in observers:
        # observer.print_details()

        for data in aggregated_data:
            data.collect_from_observer(observer)

for data in aggregated_data:
    data.print_details()
