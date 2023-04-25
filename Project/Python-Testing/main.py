import json
import numpy as np
from sklearn.neighbors import NearestNeighbors
from flask import Flask, request

app = Flask(__name__)


def knn_algorithm(target_user, users, k=100):
    all_users = users + [target_user]

    if k > len(users): k = len(users)

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

    # Fit KNN model
    knn = NearestNeighbors(n_neighbors=k, metric='jaccard')
    knn.fit(users_binary)

    # Find closest users to target_user
    distances, indices = knn.kneighbors([target_user_binary])
    closest_users_indices = indices[0]

    # print(closest_users_indices)
    return closest_users_indices


@app.route("/algorithms/knn", methods=["POST"])
def knn_route():
    body = str(request.data.decode())
    data = json.loads(body)

    target_preferences = data["target_preferences"]
    users_preferences = data["users_preferences"]

    # return users_preferences + [target_preferences]
    result = knn_algorithm(target_preferences, users_preferences, k=3)
    return str(result)


if __name__ == "__main__":
    app.debug = True
    app.run(host="localhost", port=5000)

