import numpy as np
from sklearn.neighbors import NearestNeighbors

from knn.KNNStrategy import KNNStrategy


class KNNCosine(KNNStrategy):

    def __init__(self, target_preferences, users_preferences):
        super().__init__(target_preferences, users_preferences)

    def metric(self, u, v):
        cos_sim = np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))
        return 1 - cos_sim

    def solve(self, k):
        target_user_binary, users_binary = self.get_binary_vectors()

        # Fit KNN model
        knn = NearestNeighbors(n_neighbors=k, metric=self.metric)
        knn.fit(users_binary)

        # Find closest users to target_user
        distances, indices = knn.kneighbors([target_user_binary])
        closest_users_indices = indices[0]

        return closest_users_indices