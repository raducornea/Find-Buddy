import json
import numpy as np
from sklearn.neighbors import NearestNeighbors
from flask import Flask, request

app = Flask(__name__)


def cosine_similarity(u, v):
    cos_sim = np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))
    return cos_sim


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


@app.route("/algorithms/cosine-similarity", methods=["POST"])
def cosine_similarity_route():
    body = str(request.data.decode())
    data = json.loads(body)

    target_preferences = data["target_preferences"]
    users_preferences = data["users_preferences"]

    # return users_preferences + [target_preferences]
    result = cosine_similarity_algorithm(target_preferences, users_preferences)
    return str(result)


if __name__ == "__main__":
    app.debug = True
    app.run(host="localhost", port=5000)

