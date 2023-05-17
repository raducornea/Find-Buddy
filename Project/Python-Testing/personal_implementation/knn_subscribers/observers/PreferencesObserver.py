from personal_implementation.knn_strategies.KNearestNeighboursEuclidian import KNearestNeighboursEuclidian
from personal_implementation.knn_strategies.KNearestNeighboursJaccard import KNearestNeighboursJaccard
from personal_implementation.knn_strategies.KNearestNeighboursCosine import KNearestNeighboursCosine
from personal_implementation.knn_subscribers.Observer import Observer
from personal_implementation.Statistics import Statistics


k = 3
knn_cosine = KNearestNeighboursCosine()
knn_euclidian = KNearestNeighboursEuclidian()
knn_jaccard = KNearestNeighboursJaccard()


class PreferencesObserver(Observer):
    def __init__(self):
        self.overall_accuracy_cosine = 0
        self.overall_accuracy_euclidian = 0
        self.overall_accuracy_jaccard = 0
        self.tests_number = 0
        self.preferences = None

    def accuracy_testing(self, algorithm, training_data, testing_data):
        overall_accuracy = 0

        for new_preference in testing_data:
            knn = algorithm
            knn.__init__()
            knn.set_k(k)

            knn.fit(training_data)

            predictions = knn.predict(new_preference)
            relationships = list(map(lambda x: x[0], predictions))
            indices = list(map(lambda x: x[1], predictions))
            predicted_preferences = [self.preferences[x] for x in indices]  # vector of vectors of preferences

            statistics = Statistics(self.preferences, relationships, indices, new_preference)
            accuracy = statistics.accuracy()
            overall_accuracy += accuracy

        return overall_accuracy / len(testing_data)

    def update(self, preferences, training_preferences, testing_preferences):
        self.preferences = preferences
        self.tests_number += 1
        self.overall_accuracy_cosine += self.accuracy_testing(knn_cosine, training_preferences, testing_preferences)
        self.overall_accuracy_euclidian += self.accuracy_testing(knn_euclidian, training_preferences, testing_preferences)
        self.overall_accuracy_jaccard += self.accuracy_testing(knn_jaccard, training_preferences, testing_preferences)

    def print_accuracies(self):
        if self.tests_number == 0: return

        print(self.overall_accuracy_cosine / self.tests_number)
        print(self.overall_accuracy_euclidian / self.tests_number)
        print(self.overall_accuracy_jaccard / self.tests_number)
