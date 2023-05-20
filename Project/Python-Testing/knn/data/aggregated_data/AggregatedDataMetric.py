from knn.data.aggregated_data.AggregatedData import AggregatedData


class AggregatedDataMetric(AggregatedData):
    def __init__(self, name):
        super().__init__()
        self.name = name

    def collect_from_observer(self, observer):
        if observer.name == self.name:
            self.perform_collection(observer)

    def print_details(self):
        print("*" * 100)
        print(f"{self.name}")
        self.similarity_data.print_statistics()
