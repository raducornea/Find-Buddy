import numpy as np

from knn.knn_strategies.KNN import KNN


class KNNEuclidian(KNN):
    def __init__(self, k=3):
        super().__init__(k)
        self.metric_type = "distance"
        self.name = "Euclidian"

    def metric(self, u, v):
        return np.sqrt(np.sum((np.array(u) - np.array(v)) ** 2))  # return np.linalg.norm(u - v)

    def predict(self, new_point):
        distances = self.get_relationships(new_point)

        distances.sort(key=lambda x: x[0])
        best_individuals = distances[:self.k]

        return best_individuals
