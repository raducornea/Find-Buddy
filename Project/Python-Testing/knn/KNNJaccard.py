import numpy as np
from sklearn.metrics import DistanceMetric
from sklearn.neighbors import NearestNeighbors, BallTree

from knn.KNNStrategy import KNNStrategy


class KNNJaccard(KNNStrategy):

    def __init__(self, training_users, fitting_users):
        self.k = 3
        self.knn_tree = None
        self.preferences_count = self.get_features_count(training_users, fitting_users)
        self.training_users_binary = self.binarize_vectors(training_users, self.preferences_count)
        self.fitting_users_binary = self.binarize_vectors(fitting_users, self.preferences_count)
        self.name = "jaccard"

    def metric(self, u, v):
        # convert the binary list into sets to operate easier on those numbers
        u = set(np.where(u == 1)[0])  # {2, 5, 6}
        v = set(np.where(v == 1)[0])  # {5}
        intersection = len(u.intersection(v))
        union = len(u.union(v))
        jaccard_sim = intersection / union
        jaccard_dist = 1 - jaccard_sim
        return jaccard_dist

    def train(self, k):
        self.knn_tree = BallTree(self.training_users_binary, metric=self.metric)
        self.k = k

    # Find closest users to target_user
    def fit_distances(self, target_user):
        target_user_binary = self.binarize_vector(target_user, self.preferences_count)
        distances, indices = self.knn_tree.query([target_user_binary], k=self.k)

        closest_users_distances = list(map(lambda x: format(1 - x, ".4f"), distances[0]))
        return closest_users_distances

    def fit_indices(self, target_user):
        target_user_binary = self.binarize_vector(target_user, self.preferences_count)
        distances, indices = self.knn_tree.query([target_user_binary], k=self.k)

        closest_users_indices = indices[0]
        return closest_users_indices
