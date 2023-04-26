from abc import ABC, abstractmethod

import numpy as np


class KNNStrategy(ABC):

    def __init__(self, target_preferences, users_preferences):
        self.target_preferences = target_preferences
        self.users_preferences = users_preferences

    @abstractmethod
    def metric(self, u, v):
        pass

    @abstractmethod
    def solve(self, k):
        pass

    def get_binary_vectors(self):
        all_users = self.users_preferences + [self.target_preferences]

        features_count = max(max(user) for user in all_users) + 1
        users_binary = []
        for user in self.users_preferences:
            user_binary = np.zeros(features_count)
            for preference in user:
                user_binary[preference] = 1
            users_binary.append(user_binary)

        target_user_binary = np.zeros(features_count)
        for preference in self.target_preferences:
            target_user_binary[preference] = 1

        return target_user_binary, users_binary