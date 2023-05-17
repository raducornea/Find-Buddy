from personal_implementation.knn_subscribers.observers.PreferencesObserver import PreferencesObserver
from personal_implementation.knn_subscribers.subjects.PreferencesSubject import PreferencesSubject

subject = PreferencesSubject()
observer = PreferencesObserver()
subject.register(observer)

for i in range(5):
    subject.shuffle_preferences()

observer.print_accuracies()
