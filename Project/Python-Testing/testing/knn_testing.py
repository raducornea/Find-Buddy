import random
import copy

from knn.KNNCosine import KNNCosine
from knn.KNNEuclidian import KNNEuclidian
from knn.KNNJaccard import KNNJaccard

preferences = [[9, 10, 4, 5, 11, 12], [13, 5, 14, 15, 6, 4, 16, 17], [18, 19, 6, 4, 5, 14, 20, 21, 17, 16], [8, 22, 23, 13, 24, 20], [16], [17, 6, 25], [4, 26, 27], [6, 12, 21, 4], [19, 28, 29, 30, 31], [32, 33, 34, 35], [36, 37, 38, 39], [40, 41, 42, 43, 44], [45, 46, 47, 48], [21, 49, 31, 35], [50, 51, 52, 53, 29], [54, 33, 32, 55], [56, 28, 57, 39], [58, 59, 60, 61, 62], [21, 19, 6, 63, 64, 32, 57, 65], [58, 19, 8, 31, 29, 50, 28], [6, 11, 14, 66, 15, 67, 68, 69], [19, 70, 29, 30, 52, 53, 71], [21, 58, 19, 72, 73, 59, 65], [74, 18, 75, 76, 77, 78, 36, 79], [6, 14, 66, 11, 80, 81, 69, 65], [19, 21, 58, 31, 29, 50, 57], [6, 14, 66, 67, 15, 68, 11, 69], [4, 5, 6, 14, 66, 82, 83, 84, 79], [83, 84, 85, 86, 87, 4, 5, 6, 14, 79], [4, 5, 6, 88, 82, 83, 84, 37, 79], [83, 84, 85, 89, 4, 5, 6, 14, 79], [4, 5, 6, 14, 66, 83, 84, 28, 79], [4, 5, 6, 14, 67, 82, 83, 84, 28, 79], [83, 84, 85, 4, 5, 6, 14, 79], [4, 5, 6, 14, 15, 83, 84, 37, 79], [83, 84, 85, 89, 4, 5, 6, 14, 79], [19, 90, 91, 92, 29], [12, 93, 6, 14, 81], [94, 95, 96, 63, 64], [21, 20, 97, 98, 80], [14, 6, 5, 4, 66], [19, 99, 81, 4, 5], [30, 100, 71, 51, 8], [3, 2, 101, 102, 95], [6, 14, 66, 80, 103], [33, 104, 105, 106, 107], [74, 108, 78], [58, 109, 110], [111, 98, 4], [77, 21, 112], [19, 99, 4], [21], [8], [113], [18], [19, 8], [11, 15], [74, 78], [114, 115], [66, 103, 80, 69], [116, 117, 80, 118, 119], [21, 20, 97, 98, 120], [111, 121, 81, 122], [19, 123, 124, 125], [113, 126, 127, 81], [128, 129, 102, 130], [12, 93, 81, 120, 131, 122, 94], [80, 103, 15, 66, 82], [128, 15, 102, 11, 94], [19, 123, 124, 125, 14, 132], [21, 20, 98, 14, 133], [67, 134, 135, 103, 80], [136, 14, 80, 137, 138, 63], [139, 140, 81, 14, 133], [111, 141, 98, 14, 133], [80, 103, 14, 66, 132, 94], [142, 58, 19, 4, 5, 6, 14], [142, 19, 4, 5, 6, 66, 67], [142, 19, 4, 5, 6, 15], [58, 19, 4, 5, 6, 14], [142, 58, 19, 4, 5, 6, 66], [142, 58, 4, 5, 6, 67], [58, 19, 4, 5, 6, 15], [142, 19, 4, 5, 6, 14], [58, 19, 4, 5, 6, 66], [142, 58, 4, 5, 6, 15], [58, 21, 19, 72], [12, 143, 144], [21, 3, 14], [6, 11, 145], [142, 19, 12, 146], [58, 116, 18, 146], [21, 58, 113, 72], [19, 145, 146], [6, 19, 146], [143, 19, 6, 12, 146], [147, 148, 149], [104, 150], [147, 151], [105, 152], [147, 153], [154, 155], [156, 157], [104, 158], [159, 160], [161, 162], [147, 153], [148, 163], [164, 165], [147, 151], [154, 166], [157, 156], [147, 160], [104, 167], [161, 168], [169, 19, 170, 171], [172, 173, 174, 175], [176, 177, 145, 178], [148, 179, 180, 181], [104, 182, 183], [158, 184, 185, 186], [161, 187, 188], [189, 190, 191, 192], [193, 194, 195], [196, 197, 198, 199], [170, 192, 200, 201], [202, 105, 203], [165, 161, 173], [176, 158, 204], [205, 206, 207, 208], [19, 209, 210, 211, 212], [19, 70, 90, 91, 213], [209, 211, 19, 214, 63], [19, 29, 212], [19, 210, 215, 216], [19, 70, 29, 51], [19, 209, 211, 91, 90], [29, 19, 217, 209], [19, 210, 216], [19, 90, 91, 29], [19, 29, 212], [19, 209, 211, 210], [215, 19, 209], [19, 212, 90, 29], [19, 29, 210], [19, 209, 211], [19, 29, 212], [58, 21, 70, 209, 211, 212, 94, 30], [218, 8, 100, 71, 70], [21, 114, 209, 210, 64, 63], [3, 219, 18, 209, 210, 214, 94], [21, 70, 218, 94, 220, 221, 30, 222], [116, 223, 210, 209, 64, 63, 94], [19, 63, 64, 94, 220, 224, 225], [223, 209, 218, 64, 30, 50], [58, 18, 210, 209, 94, 64, 63], [116, 223, 209, 211, 30, 94, 63], [1, 2, 3, 4, 5, 6, 7, 8]]
users_count = len(preferences)  # 158 elements
training_count = int(0.7 * users_count)


def get_similarities_by_running_tests(tests_count):
    similarities = []

    for test in range(0, tests_count):
        random.shuffle(preferences)
        training_users = preferences[:training_count]
        fitting_users = preferences[training_count:]

        k_values = [3, 5, 7, 11, len(training_users)]
        for k in k_values:
            knn_cosine = KNNCosine(training_users, fitting_users)
            knn_euclidian = KNNEuclidian(training_users, fitting_users)
            knn_jaccard = KNNJaccard(training_users, fitting_users)

            knn_metrics = [knn_cosine, knn_euclidian, knn_jaccard]
            for knn in knn_metrics:
                knn.train(k)

                similarity_data = SimilarityData(knn, k)
                similarity_data.collect_data(fitting_users)
                similarities.append(similarity_data)

    return similarities


class SimilarityData(object):

    def __init__(self, knn, k):
        self.very_low = 0  # [0-10)%
        self.low = 0  # [10-50)%
        self.moderate = 0  # [50-70)%
        self.high = 0  # [70-80)%
        self.very_high = 0  # [80-100]%
        self.k = k
        self.knn = knn
        self.name = self.knn.name

    def collect_data(self, fitting_users):
        for target_user in fitting_users:
            distances = list(map(lambda x: float(x), self.knn.fit_distances(target_user)))
            for distance in distances:
                if distance < 0.1: self.very_low += 1
                elif 0.1 <= distance < 0.5: self.low += 1
                elif 0.5 <= distance < 0.7: self.moderate += 1
                elif 0.7 <= distance < 0.8: self.high += 1
                else: self.very_high += 1

    def print_result(self):
        print("*"*100)
        print(self.k)
        print(self.name)
        self.print_statistics()

    def print_statistics(self):
        total = self.very_low + self.low + self.moderate + self.high + self.very_high
        print(f"total:    \t{total}")
        print(f"very low: \t{self.very_low}")
        print(f"low:      \t{self.low}")
        print(f"moderate: \t{self.moderate}")
        print(f"high:     \t{self.high}")
        print(f"very high:\t{self.very_high}")


class Table(object):

    def __init__(self, similarities):
        self.similarities = similarities

    def __accumulate_similarities(self):
        final_similarity = copy.deepcopy(self.similarities[0])
        for index in range(1, len(self.similarities)):
            final_similarity.very_low += self.similarities[index].very_low
            final_similarity.low += self.similarities[index].low
            final_similarity.moderate += self.similarities[index].moderate
            final_similarity.high += self.similarities[index].high
            final_similarity.very_high += self.similarities[index].very_high
        return final_similarity

    def print_table_with_name(self):
        print("*"*100)
        final_similarity = self.__accumulate_similarities()

        print(final_similarity.name)
        final_similarity.print_statistics()

    def print_table_with_k(self):
        print("*"*100)
        final_similarity = self.__accumulate_similarities()

        print(final_similarity.k)
        final_similarity.print_statistics()

    def print_table_with_name_and_k(self):
        print("*"*100)
        final_similarity = self.__accumulate_similarities()

        print(final_similarity.k)
        print(final_similarity.name)
        final_similarity.print_statistics()

    def print_similarities(self):
        for similarity in self.similarities:
            similarity.print_result()


def get_matching_users_metrics_profiles(fitting_user, k):
    random.shuffle(preferences)
    training_users = preferences[:training_count]
    fitting_users = [fitting_user]

    knn_cosine = KNNCosine(training_users, fitting_users)
    knn_euclidian = KNNEuclidian(training_users, fitting_users)
    knn_jaccard = KNNJaccard(training_users, fitting_users)

    knn_cosine.train(k)
    knn_euclidian.train(k)
    knn_jaccard.train(k)

    metrics_and_profiles = []
    indices = knn_cosine.fit_indices(fitting_user)
    profiles = [preferences[x] for x in indices]
    method = 'cosine'
    metrics_and_profiles.append((method, profiles, indices))

    indices = knn_euclidian.fit_indices(fitting_user)
    profiles = [preferences[x] for x in indices]
    method = 'euclidian'
    metrics_and_profiles.append((method, profiles, indices))

    indices = knn_jaccard.fit_indices(fitting_user)
    profiles = [preferences[x] for x in indices]
    method = 'jaccard'
    metrics_and_profiles.append((method, profiles, indices))

    return metrics_and_profiles


def print_tables_with_k(similarities):
    table_k_3 = Table(list(filter(lambda x: x.k == 3, similarities)))
    table_k_5 = Table(list(filter(lambda x: x.k == 5, similarities)))
    table_k_7 = Table(list(filter(lambda x: x.k == 7, similarities)))
    table_k_11 = Table(list(filter(lambda x: x.k == 11, similarities)))
    table_k_len = Table(list(filter(lambda x: x.k == training_count, similarities)))

    table_k_3.print_table_with_k()
    table_k_5.print_table_with_k()
    table_k_7.print_table_with_k()
    table_k_11.print_table_with_k()
    table_k_len.print_table_with_k()


def print_tables_with_name(similarities):
    table_cosine = Table(list(filter(lambda x: x.name == "cosine", similarities)))
    table_euclidian = Table(list(filter(lambda x: x.name == "euclidian", similarities)))
    table_jaccard = Table(list(filter(lambda x: x.name == "jaccard", similarities)))

    table_cosine.print_table_with_name()
    table_euclidian.print_table_with_name()
    table_jaccard.print_table_with_name()


def print_tables_with_name_and_k(similarities):
    for k in [3, 5, 7, 11, 110]:
        for metric in ["cosine", "euclidian", "jaccard"]:
            table = Table(list(filter(lambda x: x.k == k and x.name == metric, similarities)))
            table.print_table_with_name_and_k()


if __name__ == "__main__":
    tests_count = 1
    similarities = get_similarities_by_running_tests(tests_count)
    similarities.sort(key=lambda x: x.k)

    table_totals = Table(similarities)
    # table_totals.print_similarities()

    # print_tables_with_name(similarities)
    # print_tables_with_k(similarities)
    # print_tables_with_name_and_k(similarities)


    # 3 indivizi noi cu ceilalti cei mai potriviti cu 3 metode si de afisat preferintele celorlalti (k=3/5):
    new_individ1 = [5, 19, 32]
    new_individ2 = [16]
    new_individ3 = [141, 5, 19, 55, 13]
    new_users = [new_individ1, new_individ2, new_individ3]

    for user in new_users:
        metrics_and_profiles_and_indices = get_matching_users_metrics_profiles(user, k=5)

        print("*"*100)
        print(f"Target preferences: {user}")
        for metric_profile_indice in metrics_and_profiles_and_indices:
            print(f"{metric_profile_indice[0]}: \t{metric_profile_indice[1]}")

            # pentru a repera usori indicii
            # print(metric_profile_indice[2])
            # for index in metric_profile_indice[2]:
            #     print(preferences[index])

    # de trimis codul dupa modifciari
