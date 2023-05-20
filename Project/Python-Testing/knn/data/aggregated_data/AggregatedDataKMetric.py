from knn.data.aggregated_data.AggregatedData import AggregatedData


class AggregatedDataKMetric(AggregatedData):
    def __init__(self, k, name):
        super().__init__()
        self.k = k
        self.name = name

    def collect_from_observer(self, observer):
        if observer.name == self.name and observer.k == self.k:
            self.perform_collection(observer)

    def print_details(self):
        print("*" * 100)
        print(f"k={self.k} & {self.name}")
        self.similarity_data.print_statistics()
