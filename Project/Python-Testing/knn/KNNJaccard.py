import numpy as np
from sklearn.metrics import DistanceMetric
from sklearn.neighbors import NearestNeighbors, BallTree

from knn.KNNStrategy import KNNStrategy


class KNNJaccard(KNNStrategy):

    def __init__(self, target_preferences, users_preferences):
        super().__init__(target_preferences, users_preferences)

    def metric(self, u, v):
        # convert the binary list into sets to operate easier on those numbers
        u = set(np.where(u == 1)[0])  # {2, 5, 6}
        v = set(np.where(v == 1)[0])  # {5}
        intersection = len(u.intersection(v))
        union = len(u.union(v))
        jaccard_sim = intersection / union
        jaccard_dist = 1 - jaccard_sim
        return jaccard_dist

    def solve(self, k):
        target_user_binary, users_binary = self.get_binary_vectors()

        # Fit BallTree model
        tree = BallTree(users_binary, metric=self.metric)

        # Find closest users to target_user
        distances, indices = tree.query([target_user_binary], k=k)
        closest_users_indices = indices[0]

        return closest_users_indices
