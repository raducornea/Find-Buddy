import numpy as np

from knn.knn_strategies.KNN import KNN


class KNNCosine(KNN):
    def __init__(self, k=3):
        super().__init__(k)
        self.metric_type = "similarity"
        self.name = "Cosine"

    def metric(self, u, v):
        return np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))

    def predict(self, new_point, target_index=None):
        similarities = self.get_relationships(new_point, target_index)

        similarities.sort(key=lambda x: -x[0])
        best_individuals = similarities[:self.k]

        return best_individuals
