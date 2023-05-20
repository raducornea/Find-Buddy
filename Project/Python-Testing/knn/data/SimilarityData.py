class SimilarityData(object):
    def __init__(self):
        self.very_low = 0  # [0-30)%
        self.low = 0  # [30-50)%
        self.moderate = 0  # [50-70)%
        self.high = 0  # [70-80)%
        self.very_high = 0  # [80-100]%

    def collect_data(self, relationships):
        for relationship in relationships:
            if relationship < 0.3: self.very_low += 1
            elif 0.3 <= relationship < 0.5: self.low += 1
            elif 0.5 <= relationship < 0.7: self.moderate += 1
            elif 0.7 <= relationship < 0.8: self.high += 1
            else: self.very_high += 1

    def print_result(self):
        print("*"*100)
        self.print_statistics()

    def print_statistics(self):
        total = self.very_low + self.low + self.moderate + self.high + self.very_high
        print(f"total:    \t{total}")
        print(f"very low: \t{self.very_low}")
        print(f"low:      \t{self.low}")
        print(f"moderate: \t{self.moderate}")
        print(f"high:     \t{self.high}")
        print(f"very high:\t{self.very_high}")