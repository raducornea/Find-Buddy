import json
from knn.data.plotting import plotting

with open("../collected_data/results_aggregated_k.json", "r+") as file:
    data = json.load(file)

plotting(data)