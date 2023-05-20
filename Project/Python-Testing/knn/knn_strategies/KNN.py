from abc import abstractmethod, ABC

import numpy as np


# the smaller the relationship, the closer the data
# the bigger the similarity, the closer the data


class KNN(ABC):
    def __init__(self, k=3):
        """
        :param k: k nearest neighbours
        points - actual points in numerical representation
        binarized_points - points as binary vectors
        preferences_set - a set for ALL preferences - used to RETRAIN the model if there comes a new preference
        """
        self.k = k
        self.points = []
        self.binarized_points = []

        # this will be used to keep track of the preferences stored in case another user will come with a new preference
        self.preferences_set = []

    def fit_default(self):
        preferences = [[9, 10, 4, 5, 11, 12], [13, 5, 14, 15, 6, 4, 16, 17], [18, 19, 6, 4, 5, 14, 20, 21, 17, 16], [8, 22, 23, 13, 24, 20], [16], [17, 6, 25], [4, 26, 27], [6, 12, 21, 4], [19, 28, 29, 30, 31], [32, 33, 34, 35], [36, 37, 38, 39], [40, 41, 42, 43, 44], [45, 46, 47, 48], [21, 49, 31, 35], [50, 51, 52, 53, 29], [54, 33, 32, 55], [56, 28, 57, 39], [58, 59, 60, 61, 62], [21, 19, 6, 63, 64, 32, 57, 65], [58, 19, 8, 31, 29, 50, 28], [6, 11, 14, 66, 15, 67, 68, 69], [19, 70, 29, 30, 52, 53, 71], [21, 58, 19, 72, 73, 59, 65], [74, 18, 75, 76, 77, 78, 36, 79], [6, 14, 66, 11, 80, 81, 69, 65], [19, 21, 58, 31, 29, 50, 57], [6, 14, 66, 67, 15, 68, 11, 69], [4, 5, 6, 14, 66, 82, 83, 84, 79], [83, 84, 85, 86, 87, 4, 5, 6, 14, 79], [4, 5, 6, 88, 82, 83, 84, 37, 79], [83, 84, 85, 89, 4, 5, 6, 14, 79], [4, 5, 6, 14, 66, 83, 84, 28, 79], [4, 5, 6, 14, 67, 82, 83, 84, 28, 79], [83, 84, 85, 4, 5, 6, 14, 79], [4, 5, 6, 14, 15, 83, 84, 37, 79], [83, 84, 85, 89, 4, 5, 6, 14, 79], [19, 90, 91, 92, 29], [12, 93, 6, 14, 81], [94, 95, 96, 63, 64], [21, 20, 97, 98, 80], [14, 6, 5, 4, 66], [19, 99, 81, 4, 5], [30, 100, 71, 51, 8], [3, 2, 101, 102, 95], [6, 14, 66, 80, 103], [33, 104, 105, 106, 107], [74, 108, 78], [58, 109, 110], [111, 98, 4], [77, 21, 112], [19, 99, 4], [21], [8], [113], [18], [19, 8], [11, 15], [74, 78], [114, 115], [66, 103, 80, 69], [116, 117, 80, 118, 119], [21, 20, 97, 98, 120], [111, 121, 81, 122], [19, 123, 124, 125], [113, 126, 127, 81], [128, 129, 102, 130], [12, 93, 81, 120, 131, 122, 94], [80, 103, 15, 66, 82], [128, 15, 102, 11, 94], [19, 123, 124, 125, 14, 132], [21, 20, 98, 14, 133], [67, 134, 135, 103, 80], [136, 14, 80, 137, 138, 63], [139, 140, 81, 14, 133], [111, 141, 98, 14, 133], [80, 103, 14, 66, 132, 94], [142, 58, 19, 4, 5, 6, 14], [142, 19, 4, 5, 6, 66, 67], [142, 19, 4, 5, 6, 15], [58, 19, 4, 5, 6, 14], [142, 58, 19, 4, 5, 6, 66], [142, 58, 4, 5, 6, 67], [58, 19, 4, 5, 6, 15], [142, 19, 4, 5, 6, 14], [58, 19, 4, 5, 6, 66], [142, 58, 4, 5, 6, 15], [58, 21, 19, 72], [12, 143, 144], [21, 3, 14], [6, 11, 145], [142, 19, 12, 146], [58, 116, 18, 146], [21, 58, 113, 72], [19, 145, 146], [6, 19, 146], [143, 19, 6, 12, 146], [147, 148, 149], [104, 150], [147, 151], [105, 152], [147, 153], [154, 155], [156, 157], [104, 158], [159, 160], [161, 162], [147, 153], [148, 163], [164, 165], [147, 151], [154, 166], [157, 156], [147, 160], [104, 167], [161, 168], [169, 19, 170, 171], [172, 173, 174, 175], [176, 177, 145, 178], [148, 179, 180, 181], [104, 182, 183], [158, 184, 185, 186], [161, 187, 188], [189, 190, 191, 192], [193, 194, 195], [196, 197, 198, 199], [170, 192, 200, 201], [202, 105, 203], [165, 161, 173], [176, 158, 204], [205, 206, 207, 208], [19, 209, 210, 211, 212], [19, 70, 90, 91, 213], [209, 211, 19, 214, 63], [19, 29, 212], [19, 210, 215, 216], [19, 70, 29, 51], [19, 209, 211, 91, 90], [29, 19, 217, 209], [19, 210, 216], [19, 90, 91, 29], [19, 29, 212], [19, 209, 211, 210], [215, 19, 209], [19, 212, 90, 29], [19, 29, 210], [19, 209, 211], [19, 29, 212], [58, 21, 70, 209, 211, 212, 94, 30], [218, 8, 100, 71, 70], [21, 114, 209, 210, 64, 63], [3, 219, 18, 209, 210, 214, 94], [21, 70, 218, 94, 220, 221, 30, 222], [116, 223, 210, 209, 64, 63, 94], [19, 63, 64, 94, 220, 224, 225], [223, 209, 218, 64, 30, 50], [58, 18, 210, 209, 94, 64, 63], [116, 223, 209, 211, 30, 94, 63], [1, 2, 3, 4, 5, 6, 7, 8]]
        self.fit(preferences)

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

    @abstractmethod
    def metric(self, u, v):
        """
        :param u: first vector
        :param v: second vector
        :return: this will set the metric according to the strategy used
        """
        pass

    @abstractmethod
    def predict(self, new_point):
        """
        :param new_point: vector of numerical preferences
        :return:
        (*). based on those relationships and metric used, get best k individuals
        """
        pass

    def get_relationships(self, new_point):
        """
        :param new_point: vector of numerical preferences
        :return:
        1. make a set of those preferences
        2. based on those preferences, possibly RETRAIN the model if it doesn't have THOSE preferences
        3. binarize input preferences
        4. get the actual relationships
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

        # get relationships for that prediction
        relationships = []
        for index, binarized_point in enumerate(self.binarized_points):
            relationship = self.metric(binarized_point, binarized_target_preferences)
            relationships.append([relationship, index])

        return relationships

    def set_k(self, k):
        self.k = k


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