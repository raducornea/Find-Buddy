from knn.data.aggregated_data.AggregatedData import AggregatedData


class AggregatedDataK(AggregatedData):
    def __init__(self, k):
        super().__init__()
        self.k = k

    def collect_from_observer(self, observer):
        if observer.k == self.k:
            self.perform_collection(observer)

    def print_details(self):
        print("*" * 100)
        print(f"k={self.k}")
        self.similarity_data.print_statistics()
