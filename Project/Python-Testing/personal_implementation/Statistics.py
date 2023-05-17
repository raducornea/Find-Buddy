

class Statistics():
    def __init__(self, preferences, relationships, indices, new_preference):
        self.preferences = preferences
        self.distances = relationships
        self.indices = indices
        self.new_preference = new_preference

    def accuracy(self):
        total = len(self.indices)
        total_intersected = 0

        new_preference_set = set(self.new_preference)
        for index in self.indices:
            found_preference = set(self.preferences[index])
            intersected = True if len((new_preference_set & found_preference)) != 0 else False

            if intersected is True:
                total_intersected += 1

        return total_intersected / total
