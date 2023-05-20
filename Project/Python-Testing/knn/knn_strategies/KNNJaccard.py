import numpy as np

from knn.knn_strategies.KNN import KNN


class KNNJaccard(KNN):
    def __init__(self, k=3):
        super().__init__(k)
        self.metric_type = "similarity"
        self.name = "Jaccard"

    def metric(self, u, v):
        # convert the binary list into sets to operate easier on those numbers
        u = set(np.where(u == 1)[0])  # {2, 5, 6}
        v = set(np.where(v == 1)[0])  # {5}
        intersection = len(u.intersection(v))
        union = len(u.union(v))
        jaccard_sim = intersection / union
        return jaccard_sim

    def predict(self, new_point):
        similarities = self.get_relationships(new_point)

        similarities.sort(key=lambda x: -x[0])
        best_individuals = similarities[:self.k]

        return best_individuals