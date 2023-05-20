import json
from flask import Flask, request

from knn.knn_strategies.KNNCosine import KNNCosine
from knn.knn_strategies.KNNEuclidian import KNNEuclidian
from knn.knn_strategies.KNNJaccard import KNNJaccard

app = Flask(__name__)

k = 55
knn_cosine = KNNCosine(k)
knn_euclidian = KNNEuclidian(k)
knn_jaccard = KNNJaccard(k)
knns = [knn_cosine, knn_euclidian, knn_jaccard]


def get_preferences_from_request(request):
    body = str(request.data.decode())
    data = json.loads(body)

    users_preferences = data["users_preferences"]
    target_preferences = data["target_preferences"]

    return users_preferences, target_preferences


@app.route("/algorithms/knn/<strategy>", methods=["POST"])
def knn_route(strategy):
    users_preferences, target_preferences = get_preferences_from_request(request)
    # k = len(users_preferences)

    if strategy == "cosine":
        knn = knn_cosine
    elif strategy == "euclidian":
        knn = knn_euclidian
    elif strategy == "jaccard":
        knn = knn_jaccard
    else:
        knn = knn_jaccard

    knn.fit(users_preferences)

    # todo
    #  should train WITH user_preferences, and NOT the default data set
    predictions = knn.predict(target_preferences)
    indices = list(map(lambda x: (x[1]), predictions))

    # change result so it returns a list of number with 1 space instead
    result = str(list(indices)).replace(", ", " ")
    return str(result)


if __name__ == "__main__":
    for knn in knns:
        knn.fit_default()

    app.debug = True
    app.run(host="localhost", port=5000)
