import numpy as np
from sklearn.neighbors import NearestNeighbors

from knn.KNNStrategy import KNNStrategy


class KNNCosine(KNNStrategy):

    def __init__(self, target_preferences, users_preferences):
        super().__init__(target_preferences, users_preferences)
        self.metric_strategy = self.cosine_similarity

    def cosine_similarity(self, u, v):
        cos_sim = np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))
        return 1 - cos_sim

    def solve(self, k):
        if k > len(self.users_preferences): k = len(self.users_preferences)

        # binary vectors
        target_user_binary, users_binary = self.get_binary_vectors()

        # Fit KNN model
        knn = NearestNeighbors(n_neighbors=k, metric=self.metric_strategy)
        knn.fit(users_binary)

        # Find closest users to target_user
        distances, indices = knn.kneighbors([target_user_binary])
        closest_users_indices = indices[0]

        return closest_users_indices