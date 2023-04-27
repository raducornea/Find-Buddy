import json
import numpy as np
from flask import Flask, request

from knn.KNNCosine import KNNCosine
from knn.KNNEuclidian import KNNEuclidian
from knn.KNNJaccard import KNNJaccard

app = Flask(__name__)


def cosine_similarity(u, v):
    cos_sim = np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))
    return cos_sim


def jaccard_similarity(u, v):
    intersection = len(set(u).intersection(set(v)))
    union = len(set(u).union(set(v)))
    jaccard_sim = intersection / union
    return jaccard_sim


def cosine_similarity_algorithm(target_user, users):
    all_users = users + [target_user]

    # binary vectors
    features_count = max(max(user) for user in all_users)
    users_binary = []
    for user in users:
        user_binary = np.zeros(features_count + 1)
        for preference in user:
            user_binary[preference] = 1
        users_binary.append(user_binary)

    target_user_binary = np.zeros(features_count + 1)
    for preference in target_user:
        target_user_binary[preference] = 1

    # similarities between those vectors
    # [0.8164965809277259, 0.6666666666666667, 0.6666666666666667, 0.2886751345948129, 0.5773502691896258, 0.0, 0.0]
    cosine_similarities = []
    for index, user_binary in enumerate(users_binary):
        cosine_similarities.append((cosine_similarity(target_user_binary, user_binary), index))

    # [(0.8164965809277259, 0), (0.6666666666666667, 1), (0.6666666666666667, 2), (0.5773502691896258, 4), (0.2886751345948129, 3), (0.0, 5), (0.0, 6)]
    cosine_similarities.sort(key=lambda x: -x[0])
    cosine_similarities_indices = list([similarity[1] for similarity in cosine_similarities])

    return np.array(cosine_similarities_indices)


# ignore the above lines - todo use for other algorithms

def get_preferences_from_request(request):
    body = str(request.data.decode())
    data = json.loads(body)

    target_preferences = data["target_preferences"]
    users_preferences = data["users_preferences"]

    return target_preferences, users_preferences


@app.route("/algorithms/knn/<strategy>", methods=["POST"])
def knn_route(strategy):
    target_preferences, users_preferences = get_preferences_from_request(request)

    if strategy == "cosine":
        knn = KNNCosine(target_preferences, users_preferences)
    elif strategy == "jaccard":
        knn = KNNJaccard(target_preferences, users_preferences)
    elif strategy == "euclidian":
        knn = KNNEuclidian(target_preferences, users_preferences)
    else:
        knn = KNNJaccard(target_preferences, users_preferences)

    result = knn.solve(k=len(users_preferences))
    return str(result)


if __name__ == "__main__":
    app.debug = True
    app.run(host="localhost", port=5000)
