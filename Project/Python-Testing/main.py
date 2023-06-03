import ast
import json
from flask import Flask, request
import atexit

from knn.knn_strategies.KNN import get_best_x_percentage_best_predictions, map_preferences, get_preferences_as_numeric
from knn.knn_strategies.KNNCosine import KNNCosine
from knn.knn_strategies.KNNEuclidian import KNNEuclidian
from knn.knn_strategies.KNNJaccard import KNNJaccard

app = Flask(__name__)

k = 110
knn_cosine = KNNCosine(k)
knn_euclidian = KNNEuclidian(k)
knn_jaccard = KNNJaccard(k)
knns = [knn_cosine, knn_euclidian, knn_jaccard]

preferences_string = []
preferences_numeric = []
dictionary_of_indices, dictionary_of_preferences = None, None


def get_preferences_from_request(request):
    body = str(request.data.decode())
    data = json.loads(body)

    target_preferences = data["target_preferences"]  # string of preferences
    target_index = data["target_index"]  # index of current user (it is already registered since he can perform those operations, which means he HAS an index associated
    knn_percentage = data["knn_percentage"]  # voting percentage

    return target_preferences, target_index, knn_percentage


# todo add register callback
@app.route("/algorithms/register/<user>", methods=["POST"])
def register_callback(user):
    pass


@app.route("/algorithms/knn/<strategy>", methods=["POST"])
def knn_route(strategy):
    target_preferences_string, target_index, knn_percentage = get_preferences_from_request(request)
    target_preferences_numeric = get_preferences_as_numeric(target_preferences_string, dictionary_of_preferences)
    # knn_percentage = 0.7  # in case you want to force a percentage without requesting

    print(target_index)

    if strategy == "cosine": knn = knn_cosine
    elif strategy == "euclidian": knn = knn_euclidian
    elif strategy == "jaccard": knn = knn_jaccard
    else: knn = knn_jaccard

    predictions = knn.predict(target_preferences_numeric, target_index)
    indices = list(map(lambda x: x[1], predictions))

    k_predicted_preferences = [preferences_numeric[x] for x in indices]

    best_x_percentage_predictions = get_best_x_percentage_best_predictions(target_preferences_numeric, k_predicted_preferences, percentage=knn_percentage)
    print(best_x_percentage_predictions)

    # actual indices after voting
    best_x_percentage_predictions_indices = []
    for preference in best_x_percentage_predictions:
        for index, user_preference in enumerate(preferences_numeric):
            if user_preference == preference:
                best_x_percentage_predictions_indices.append(index)
                break

    # change result so it returns a list of number with 1 space instead
    result = str(list(best_x_percentage_predictions_indices)).replace(", ", " ")
    return str(result)


# should persist the preferences of the users too
def on_exit():
    print("Flask application is exiting.")
    with open('knn/stored_preferences.txt', 'w') as file:
        file.write(str(preferences_string))


atexit.register(on_exit)

if __name__ == "__main__":
    # prefit with collected data
    with open("knn/stored_preferences.txt", "r+") as file:
        text = file.read()
        preferences_string = ast.literal_eval(text)
        # print(preferences_string)

    for knn in knns:
        # knn.fit_default()
        dictionary_of_indices, dictionary_of_preferences = map_preferences(preferences_string)  # 0: '.net'; '.net': 0
        preferences_numeric = [get_preferences_as_numeric(x, dictionary_of_preferences) for x in preferences_string]
        knn.fit(preferences_numeric)

    app.debug = True
    app.run(host="localhost", port=5000)
