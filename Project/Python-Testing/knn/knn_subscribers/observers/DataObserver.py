from knn.data.SimilarityData import SimilarityData
from knn.knn_subscribers.Observer import Observer
from knn.data.Statistics import Statistics


class DataObserver(Observer):
    def __init__(self, knn, name, k=3):
        self.overall_accuracy = 0
        self.tests_number = 0
        self.preferences = None
        self.knn = knn
        self.k = k
        self.knn.set_k(k)
        self.name = name
        self.total_preferences_analyzed = 0  # for calculating similarity data
        self.similarity_data = SimilarityData()

    def __get_all_predictions(self, new_preference):
        # memento this so k keeps its initial value
        total_points = len(self.knn.points)
        self.knn.set_k(total_points)
        all_predictions = self.knn.predict(new_preference)
        self.knn.set_k(self.k)
        return all_predictions

    # we need all_relationships to compensate the distance in percentages
    def collect_similarities(self, relationships, all_relationships):
        # data transformation for distance metrics to similarity aproximations
        if self.knn.metric_type == "distance":
            maximum_distance = max(all_relationships)
            relationships = list(map(lambda r: 1 - (r / maximum_distance), relationships))

        # actuall data collecting
        self.similarity_data.collect_data(relationships)

    def testing(self, testing_data):
        test_set_accuracy = 0

        for new_preference in testing_data:
            self.total_preferences_analyzed += 1

            predictions = self.knn.predict(new_preference)
            relationships = list(map(lambda x: x[0], predictions))
            indices = list(map(lambda x: x[1], predictions))
            predicted_preferences = [self.preferences[x] for x in indices]  # vector of vectors of preferences

            all_predictions = self.__get_all_predictions(new_preference)
            all_relationships = list(map(lambda x: x[0], all_predictions))

            # similarities
            self.collect_similarities(relationships, all_relationships)

            # accuracy modifications
            statistics = Statistics(self.preferences, relationships, indices, new_preference)
            accuracy = statistics.accuracy()
            test_set_accuracy += accuracy

        self.overall_accuracy += test_set_accuracy / len(testing_data)

    def update(self, preferences, training_preferences, testing_preferences):
        self.preferences = preferences
        self.tests_number += 1

        # must initiate algorithm there and perform the training here
        self.knn.__init__(self.k)
        self.knn.fit(training_preferences)

        # perform statistics for all tests, collectively
        self.testing(testing_preferences)

    def get_accuracy(self):
        return self.overall_accuracy / self.tests_number

    def print_accuracies(self):
        accuracy = self.get_accuracy()
        print(f"Accuracy: \t{accuracy}")

    def print_similarities_table(self):
        self.similarity_data.print_statistics()

    def print_details(self):
        print("*" * 100)
        print(f"k={self.k} & {self.name}")
        self.print_accuracies()
        self.print_similarities_table()



