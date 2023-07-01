import json
from knn.data.plotting import plotting_accuracy

with open("../collected_data/results_aggregated_metric.json", "r+") as file:
    data = json.load(file)

plotting_accuracy(data)