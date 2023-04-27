import numpy as np
from sklearn.neighbors import NearestNeighbors

from knn.KNNStrategy import KNNStrategy


class KNNEuclidian(KNNStrategy):

    def __init__(self, training_users, fitting_users):
        self.knn = None
        self.preferences_count = self.get_features_count(training_users, fitting_users)
        self.training_users_binary = self.binarize_vectors(training_users, self.preferences_count)
        self.fitting_users_binary = self.binarize_vectors(fitting_users, self.preferences_count)
        self.name = "euclidian"

    def metric(self, u, v):
        dist = np.linalg.norm(u - v)
        return dist

    def train(self, k):
        self.knn = NearestNeighbors(n_neighbors=k, metric=self.metric)
        self.knn.fit(self.training_users_binary)

    # Find closest users to target_user
    def fit_distances(self, target_user):
        target_user_binary = self.binarize_vector(target_user, self.preferences_count)
        distances, indices = self.knn.kneighbors([target_user_binary])

        # x is distance (from 0 to inf) - should scale it somehow
        maximum_distance_found = 4.0
        closest_users_distances = list(map(lambda x: format((1 - (x / maximum_distance_found)), ".4f"), distances[0]))
        return closest_users_distances

    def fit_indices(self, target_user):
        target_user_binary = self.binarize_vector(target_user, self.preferences_count)
        distances, indices = self.knn.kneighbors([target_user_binary])

        closest_users_indices = indices[0]
        return closest_users_indices