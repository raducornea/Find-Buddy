import matplotlib.pyplot as plt
import numpy as np


def plotting(data):
    aggregated_data = {}
    for element in data:
        method = element["method"]

        forbidden_method = method == "k=110" or method == "k=110 & Cosine" or method == "k=110 & Jaccard" or method == "k=110 & Euclidian"
        if forbidden_method: continue

        if method not in aggregated_data:
            aggregated_data[method] = {key: value for key, value in element.items() if key != "total"}
        else:
            for key, value in element.items():
                if key != "method" and key != "total":
                    aggregated_data[method][key] += value

    for method, values in aggregated_data.items():
        print({"method": method, **values})

    # Extract the values from the aggregated data
    methods = [element["method"] for element in aggregated_data.values()]
    very_low_values = [element["very_low"] for element in aggregated_data.values()]
    low_values = [element["low"] for element in aggregated_data.values()]
    moderate_values = [element["moderate"] for element in aggregated_data.values()]
    high_values = [element["high"] for element in aggregated_data.values()]
    very_high_values = [element["very_high"] for element in aggregated_data.values()]

    # Set the width of each bar
    bar_width = 0.15

    # Calculate the positions of the bars on the x-axis
    r1 = np.arange(len(methods))
    r2 = [x + bar_width for x in r1]
    r3 = [x + bar_width for x in r2]
    r4 = [x + bar_width for x in r3]
    r5 = [x + bar_width for x in r4]

    # Plot the data as clustered columns
    plt.figure(figsize=(10, 6))
    plt.bar(r1, very_low_values, color="b", width=bar_width, edgecolor="white", label="Very Low")
    plt.bar(r2, low_values, color="g", width=bar_width, edgecolor="white", label="Low")
    plt.bar(r3, moderate_values, color="r", width=bar_width, edgecolor="white", label="Moderate")
    plt.bar(r4, high_values, color="c", width=bar_width, edgecolor="white", label="High")
    plt.bar(r5, very_high_values, color="m", width=bar_width, edgecolor="white", label="Very High")

    plt.xlabel("Method")
    plt.ylabel("Count")
    plt.title("Comparison of Preference Categories by Method")
    plt.xticks([r + bar_width * 2 for r in range(len(methods))], methods)
    plt.legend()
    plt.grid(True)
    plt.show()
