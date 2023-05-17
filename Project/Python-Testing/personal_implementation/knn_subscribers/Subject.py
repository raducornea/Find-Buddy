class Subject:
    def __init__(self):
        self.observers = []

    def register(self, observer):
        self.observers.append(observer)

    def notify(self, *args, **kwargs):
        for observer in self.observers:
            observer.update(*args, **kwargs)
