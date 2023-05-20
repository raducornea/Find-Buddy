from knn.knn_strategies.KNNCosine import KNNCosine
from knn.knn_strategies.KNNEuclidian import KNNEuclidian
from knn.knn_strategies.KNNJaccard import KNNJaccard

preferences = [[9, 10, 4, 5, 11, 12], [13, 5, 14, 15, 6, 4, 16, 17], [18, 19, 6, 4, 5, 14, 20, 21, 17, 16], [8, 22, 23, 13, 24, 20], [16], [17, 6, 25], [4, 26, 27], [6, 12, 21, 4], [19, 28, 29, 30, 31], [32, 33, 34, 35], [36, 37, 38, 39], [40, 41, 42, 43, 44], [45, 46, 47, 48], [21, 49, 31, 35], [50, 51, 52, 53, 29], [54, 33, 32, 55], [56, 28, 57, 39], [58, 59, 60, 61, 62], [21, 19, 6, 63, 64, 32, 57, 65], [58, 19, 8, 31, 29, 50, 28], [6, 11, 14, 66, 15, 67, 68, 69], [19, 70, 29, 30, 52, 53, 71], [21, 58, 19, 72, 73, 59, 65], [74, 18, 75, 76, 77, 78, 36, 79], [6, 14, 66, 11, 80, 81, 69, 65], [19, 21, 58, 31, 29, 50, 57], [6, 14, 66, 67, 15, 68, 11, 69], [4, 5, 6, 14, 66, 82, 83, 84, 79], [83, 84, 85, 86, 87, 4, 5, 6, 14, 79], [4, 5, 6, 88, 82, 83, 84, 37, 79], [83, 84, 85, 89, 4, 5, 6, 14, 79], [4, 5, 6, 14, 66, 83, 84, 28, 79], [4, 5, 6, 14, 67, 82, 83, 84, 28, 79], [83, 84, 85, 4, 5, 6, 14, 79], [4, 5, 6, 14, 15, 83, 84, 37, 79], [83, 84, 85, 89, 4, 5, 6, 14, 79], [19, 90, 91, 92, 29], [12, 93, 6, 14, 81], [94, 95, 96, 63, 64], [21, 20, 97, 98, 80], [14, 6, 5, 4, 66], [19, 99, 81, 4, 5], [30, 100, 71, 51, 8], [3, 2, 101, 102, 95], [6, 14, 66, 80, 103], [33, 104, 105, 106, 107], [74, 108, 78], [58, 109, 110], [111, 98, 4], [77, 21, 112], [19, 99, 4], [21], [8], [113], [18], [19, 8], [11, 15], [74, 78], [114, 115], [66, 103, 80, 69], [116, 117, 80, 118, 119], [21, 20, 97, 98, 120], [111, 121, 81, 122], [19, 123, 124, 125], [113, 126, 127, 81], [128, 129, 102, 130], [12, 93, 81, 120, 131, 122, 94], [80, 103, 15, 66, 82], [128, 15, 102, 11, 94], [19, 123, 124, 125, 14, 132], [21, 20, 98, 14, 133], [67, 134, 135, 103, 80], [136, 14, 80, 137, 138, 63], [139, 140, 81, 14, 133], [111, 141, 98, 14, 133], [80, 103, 14, 66, 132, 94], [142, 58, 19, 4, 5, 6, 14], [142, 19, 4, 5, 6, 66, 67], [142, 19, 4, 5, 6, 15], [58, 19, 4, 5, 6, 14], [142, 58, 19, 4, 5, 6, 66], [142, 58, 4, 5, 6, 67], [58, 19, 4, 5, 6, 15], [142, 19, 4, 5, 6, 14], [58, 19, 4, 5, 6, 66], [142, 58, 4, 5, 6, 15], [58, 21, 19, 72], [12, 143, 144], [21, 3, 14], [6, 11, 145], [142, 19, 12, 146], [58, 116, 18, 146], [21, 58, 113, 72], [19, 145, 146], [6, 19, 146], [143, 19, 6, 12, 146], [147, 148, 149], [104, 150], [147, 151], [105, 152], [147, 153], [154, 155], [156, 157], [104, 158], [159, 160], [161, 162], [147, 153], [148, 163], [164, 165], [147, 151], [154, 166], [157, 156], [147, 160], [104, 167], [161, 168], [169, 19, 170, 171], [172, 173, 174, 175], [176, 177, 145, 178], [148, 179, 180, 181], [104, 182, 183], [158, 184, 185, 186], [161, 187, 188], [189, 190, 191, 192], [193, 194, 195], [196, 197, 198, 199], [170, 192, 200, 201], [202, 105, 203], [165, 161, 173], [176, 158, 204], [205, 206, 207, 208], [19, 209, 210, 211, 212], [19, 70, 90, 91, 213], [209, 211, 19, 214, 63], [19, 29, 212], [19, 210, 215, 216], [19, 70, 29, 51], [19, 209, 211, 91, 90], [29, 19, 217, 209], [19, 210, 216], [19, 90, 91, 29], [19, 29, 212], [19, 209, 211, 210], [215, 19, 209], [19, 212, 90, 29], [19, 29, 210], [19, 209, 211], [19, 29, 212], [58, 21, 70, 209, 211, 212, 94, 30], [218, 8, 100, 71, 70], [21, 114, 209, 210, 64, 63], [3, 219, 18, 209, 210, 214, 94], [21, 70, 218, 94, 220, 221, 30, 222], [116, 223, 210, 209, 64, 63, 94], [19, 63, 64, 94, 220, 224, 225], [223, 209, 218, 64, 30, 50], [58, 18, 210, 209, 94, 64, 63], [116, 223, 209, 211, 30, 94, 63], [1, 2, 3, 4, 5, 6, 7, 8]]
new_individ1 = [5, 19, 32]
new_individ2 = [16]
new_individ3 = [141, 5, 19, 55, 13]
new_preferences = [new_individ1, new_individ2, new_individ3]

k = 5
knn_cosine = KNNCosine(k)
knn_euclidian = KNNEuclidian(k)
knn_jaccard = KNNJaccard(k)
knns = [knn_cosine, knn_euclidian, knn_jaccard]

for knn in knns:
    knn.fit(preferences)

for preference in new_preferences:
    print("*"*100)

    for knn in knns:
        print(knn.name)
        predictions = knn.predict(preference)
        indices = list(map(lambda x: x[1], predictions))
        predicted_preferences = [preferences[x] for x in indices]
        print(predicted_preferences)

