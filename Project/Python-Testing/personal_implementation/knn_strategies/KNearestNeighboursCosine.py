import numpy as np

from personal_implementation.knn_strategies.KNearestNeighbours import KNearestNeighbours


class KNearestNeighboursCosine(KNearestNeighbours):
    def __init__(self, k=3):
        super().__init__(k)

    def metric(self, u, v):
        return np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))

    def predict(self, new_point):
        similarities = self.get_relationships(new_point)

        similarities.sort(key=lambda x: -x[0])
        best_individuals = similarities[:self.k]

        return best_individuals