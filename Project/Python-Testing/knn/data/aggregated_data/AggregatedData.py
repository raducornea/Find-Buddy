from abc import ABC, abstractmethod

from knn.data.SimilarityData import SimilarityData


class AggregatedData(ABC):
    def __init__(self):
        self.overall_accuracy = 0
        self.total_collected = 0
        self.similarity_data = SimilarityData()

    @abstractmethod
    def collect_from_observer(self, observer):
        pass

    @abstractmethod
    def print_details(self):
        pass

    def get_accuracy(self):
        return self.overall_accuracy / self.total_collected

    def perform_collection(self, observer):
        self.total_collected += 1
        self.overall_accuracy += observer.get_accuracy()
        self.similarity_data.very_low += observer.similarity_data.very_low
        self.similarity_data.low += observer.similarity_data.low
        self.similarity_data.moderate += observer.similarity_data.moderate
        self.similarity_data.high += observer.similarity_data.high
        self.similarity_data.very_high += observer.similarity_data.very_high