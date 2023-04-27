from abc import ABC, abstractmethod
import numpy as np


class KNNStrategy(ABC):

    @abstractmethod
    def metric(self, u, v):
        pass

    @abstractmethod
    def fit_distances(self, target_user):
        pass

    @abstractmethod
    def fit_indices(self, target_user):
        pass

    def get_features_count(self, training_users, fitting_users):
        all_users = training_users + fitting_users
        return max(max(user) for user in all_users) + 1

    def binarize_vectors(self, users, preferences_count):
        training_users_binary = []
        for user in users:
            user_binary = self.binarize_vector(user, preferences_count)
            training_users_binary.append(user_binary)
        return training_users_binary

    def binarize_vector(self, user, preferences_count):
        user_binary = np.zeros(preferences_count)
        for preference in user:
            user_binary[preference] = 1
        return user_binary