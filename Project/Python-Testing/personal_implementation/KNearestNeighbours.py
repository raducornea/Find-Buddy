import numpy as np


def euclidean_distance(u, v):
    return np.sqrt(np.sum((np.array(u) - np.array(v)) ** 2))  # return np.linalg.norm(u - v)


def binarize_vector(user, total_preferences):
    user_binary = np.zeros(total_preferences)
    for preference in user:
        user_binary[preference - 1] = 1
    return user_binary


def binarize_vectors(users, total_preferences):
    training_users_binary = []
    for user in users:
        user_binary = binarize_vector(user, total_preferences)
        training_users_binary.append(user_binary)
    return training_users_binary


class KNearestNeighbours:
    def __init__(self, k=3):
        self.k = k
        self.points = []
        self.binarized_points = []

        # this will be used to keep track of the preferences stored in case another user will come with a new preference
        self.preferences_set = []

    def fit(self, points):
        """
        :param points: unsorted vector of vectors of numerical preferences
        1. make a set out of these preferences
        2. fit binarized points
        """
        self.points = points

        preferences = [item for sublist in points for item in sublist]
        self.preferences_set = list(set(preferences))

        self.__fit()

    def __fit(self):
        """
        1. determine maximum number of preference (total_preferences)
        2. based on (total_preferences), binarize vectors and store the points
        """
        total_preferences = self.preferences_set[-1]
        self.binarized_points = binarize_vectors(self.points, total_preferences)

    def predict(self, new_point):
        """
        :param new_point: vector of numerical preferences
        :return:
        1. make a set of those preferences
        2. based on those preferences, possibly RETRAIN the model if it doesn't have THOSE preferences
        3. binarize input preferences
        4. get the actual distances
        5. based on those distances, get best k individuals
        """
        preferences = list(set(new_point))

        # possible refitting
        refit_model = False
        for pref in preferences:
            if pref not in self.preferences_set:
                self.preferences_set.append(pref)
                refit_model = True
        if refit_model:
            self.preferences_set.sort()
            self.__fit()

        binarized_target_preferences = binarize_vector(preferences, self.preferences_set[-1])

        # get distances for that prediction
        distances = []
        for index, binarized_point in enumerate(self.binarized_points):
            distance = euclidean_distance(list(binarized_point), binarized_target_preferences)
            distances.append([distance, index])

        # get the actual individuals with their distances
        distances.sort(key=lambda x: x[0])
        best_individuals = distances[:self.k]

        return best_individuals