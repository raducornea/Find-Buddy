import json
from knn.data.plotting import plotting

with open("../collected_data/results_aggregated_metric.json", "r+") as file:
    data = json.load(file)

plotting(data)