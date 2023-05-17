import json
from flask import Flask, request

from knn.KNNCosine import KNNCosine
from knn.KNNEuclidian import KNNEuclidian
from knn.KNNJaccard import KNNJaccard

app = Flask(__name__)


def get_preferences_from_request(request):
    body = str(request.data.decode())
    data = json.loads(body)

    users_preferences = data["users_preferences"]
    target_preferences = data["target_preferences"]

    return users_preferences, target_preferences


@app.route("/algorithms/knn/<strategy>", methods=["POST"])
def knn_route(strategy):
    users_preferences, target_preferences = get_preferences_from_request(request)

    if strategy == "cosine":
        knn = KNNCosine(users_preferences, [target_preferences])
    elif strategy == "jaccard":
        knn = KNNJaccard(users_preferences, [target_preferences])
    elif strategy == "euclidian":
        knn = KNNEuclidian(users_preferences, [target_preferences])
    else:
        knn = KNNJaccard(users_preferences, [target_preferences])

    k = len(users_preferences)
    knn.train(k)
    result = knn.fit_indices(target_preferences)

    # change result so it returns a list of number with 1 space instead
    result = str(list(result)).replace(", ", " ")
    return str(result)


if __name__ == "__main__":
    app.debug = True
    app.run(host="localhost", port=5000)
