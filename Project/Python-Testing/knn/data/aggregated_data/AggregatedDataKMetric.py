import json

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

    def save_to_files(self):
        method = f"k={self.k} & {self.name}"

        very_low = self.similarity_data.very_low
        low = self.similarity_data.low
        moderate = self.similarity_data.moderate
        high = self.similarity_data.high
        very_high = self.similarity_data.very_high
        total = very_low + low + moderate + high + very_high

        data_to_save = {
            "method": method,
            "total": total,
            "very_low": very_low,
            "low": low,
            "moderate": moderate,
            "high": high,
            "very_high": very_high
        }

        with open("collected_data/results_aggregated_k_metric.json", "r+") as file:
            collected_data = json.load(file)

        collected_data.append(data_to_save)

        with open("collected_data/results_aggregated_k_metric.json", "w") as file:
            json.dump(collected_data, file, indent=4)
