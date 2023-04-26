import numpy as np
from sklearn.neighbors import NearestNeighbors

from knn.KNNStrategy import KNNStrategy


class KNNEuclidian(KNNStrategy):

    def __init__(self, target_preferences, users_preferences):
        super().__init__(target_preferences, users_preferences)

    def metric(self, u, v):
        dist = np.linalg.norm(u - v)
        return dist

    def solve(self, k):
        target_user_binary, users_binary = self.get_binary_vectors()

        # Fit KNN model
        knn = NearestNeighbors(n_neighbors=k, metric=self.metric)
        knn.fit(users_binary)

        # Find closest users to target_user
        distances, indices = knn.kneighbors([target_user_binary])
        closest_users_indices = indices[0]

        return closest_users_indices
