import ast
from knn.knn_strategies.KNN import get_best_x_percentage_best_predictions, map_preferences, get_preferences_as_numeric, \
    get_preferences_list, get_preferences_as_string
from knn.knn_strategies.KNNCosine import KNNCosine
from knn.knn_strategies.KNNEuclidian import KNNEuclidian
from knn.knn_strategies.KNNJaccard import KNNJaccard


# uncomment these 2 lines in case you want to parse a string to list and comment the other few lines to not get preferences from file; else vice-versa
# preferences_string = "[[microsoft, .net, c#, html, css, javascript, windows, sql], [sunglasses, emojis, html, css, typescript, ruby], [web, css, react, angular, javascript, html, cooking, dancing], [kotlin, python, javascript, html, css, react, spring, java, dancing, cooking], [sql, managing, databases, web, cloud, spring], [cooking], [dancing, javascript, sleeping], [html, stii tu, amogus], [javascript, ruby, java, html], [python, web development, machine learning, data analysis, artificial intelligence], [cloud computing, cybersecurity, programming languages, database management], [mobile app development, web design, user experience, agile methodology], [gaming, virtual reality, esports, game design, game engines], [digital marketing, social media, content creation, search engine optimization], [java, software development, artificial intelligence, database management], [data science, statistics, big data, data visualization, machine learning], [network administration, cybersecurity, cloud computing, virtualization], [software engineering, web development, devops, agile methodology], [c++, software architecture, project management, code quality, version control], [java, python, javascript, docker, kubernetes, cloud computing, devops, testing], [c++, python, sql, artificial intelligence, machine learning, data science, web development], [javascript, typescript, react, node.js, angular, vue.js, firebase, graphql], [python, r, machine learning, data analysis, big data, data visualization, tableau], [java, c++, python, algorithms, data structures, software architecture, testing], [swift, kotlin, flutter, react native, android, ios, mobile app development, ui/ux], [javascript, react, node.js, typescript, mongodb, postgresql, graphql, testing], [python, java, c++, artificial intelligence, machine learning, data science, devops], [javascript, react, node.js, vue.js, angular, firebase, typescript, graphql], [html, css, javascript, react, node.js, bootstrap, figma, adobe xd, ui/ux], [figma, adobe xd, sketch, photoshop, illustrator, html, css, javascript, react, ui/ux], [html, css, javascript, jquery, bootstrap, figma, adobe xd, web design, ui/ux], [figma, adobe xd, sketch, framer, html, css, javascript, react, ui/ux], [html, css, javascript, react, node.js, figma, adobe xd, web development, ui/ux], [html, css, javascript, react, vue.js, bootstrap, figma, adobe xd, web development, ui/ux], [figma, adobe xd, sketch, html, css, javascript, react, ui/ux], [html, css, javascript, react, angular, figma, adobe xd, web design, ui/ux], [figma, adobe xd, sketch, framer, html, css, javascript, react, ui/ux], [python, pandas, numpy, sklearn, machine learning], [ruby, rails, javascript, react, postgresql], [aws, azure, gcp, docker, kubernetes], [java, spring, hibernate, mysql, mongodb], [react, javascript, css, html, node.js], [python, django, postgresql, html, css], [data analysis, excel, tableau, statistics, sql], [c#, .net, asp.net, sql server, azure], [javascript, react, node.js, mongodb, express], [cybersecurity, network security, encryption, firewalls, security protocols], [swift, xcode, ios], [c++, opencv, qt], [php, mysql, html], [android, java, xml], [python, django, html], [java], [sql], [rust], [kotlin], [python, sql], [typescript, angular], [swift, ios], [scala, akka], [node.js, express, mongodb, graphql], [go, gin, mongodb, rabbitmq, grpc], [java, spring, hibernate, mysql, redis], [php, phalcon, postgresql, nginx], [python, flask, sqlalchemy, sqlite], [rust, actix-web, diesel, postgresql], [.net core, ef core, sql server, iis], [ruby, rails, postgresql, redis, rspec, nginx, aws], [mongodb, express, angular, node.js, bootstrap], [.net core, angular, sql server, typescript, aws], [python, flask, sqlalchemy, sqlite, react, npm], [java, spring, mysql, react, webpack], [vue.js, vuex, axios, express, mongodb], [meteor, react, mongodb, jest, enzyme, docker], [elixir, phoenix, postgresql, react, webpack], [php, laravel, mysql, react, webpack], [mongodb, express, react, node.js, npm, aws], [c, c++, python, html, css, javascript, react], [c, python, html, css, javascript, node.js, vue.js], [c, python, html, css, javascript, angular], [c++, python, html, css, javascript, react], [c, c++, python, html, css, javascript, node.js], [c, c++, html, css, javascript, vue.js], [c++, python, html, css, javascript, angular], [c, python, html, css, javascript, react], [c++, python, html, css, javascript, node.js], [c, c++, html, css, javascript, angular], [c++, java, python, algorithms], [ruby, perl, haskell], [java, c#, react], [javascript, typescript, assembly], [c, python, ruby, compilers], [c++, go, kotlin, compilers], [java, c++, rust, algorithms], [python, assembly, compilers], [javascript, python, compilers], [perl, python, javascript, ruby, compilers], [security, cryptography, pen testing], [network security, ethical hacking], [security, risk management], [encryption, vulnerability assessment], [security, access control], [secure coding, software security], [digital forensics, incident response], [network security, malware analysis], [risk assessment, compliance], [penetration testing, vulnerability management], [security, access control], [cryptography, security auditing], [web security, threat modeling], [security, risk management], [secure coding, secure architecture], [incident response, digital forensics], [security, compliance], [network security, security operations], [penetration testing, security research], [kali linux, python, metasploit, sql injection], [nmap, burp suite, wireshark, osint], [reverse engineering, gdb, assembly, shell scripting], [cryptography, hashcat, john the ripper, steganography], [network security, firewall configuration, tcpdump], [malware analysis, yara, volatility, cyber threat intel], [penetration testing, social engineering, phishing], [ddos, stress testing, botnets, sqlmap], [packet filtering, intrusion detection, ids/ips], [tcp/ip, ssl/tls, ipsec, openvpn], [metasploit, sqlmap, xss, csrf], [secure coding practices, encryption, owasp], [threat modeling, penetration testing, burp suite], [reverse engineering, malware analysis, radare2], [blockchain, smart contracts, ethereum, solidity], [python, tensorflow, pytorch, keras, scikit-learn], [python, r, pandas, numpy, matplotlib], [tensorflow, keras, python, jupyter, docker], [python, machine learning, scikit-learn], [python, pytorch, deep learning, computer vision], [python, r, machine learning, statistics], [python, tensorflow, keras, numpy, pandas], [machine learning, python, neural networks, tensorflow], [python, pytorch, computer vision], [python, pandas, numpy, machine learning], [python, machine learning, scikit-learn], [python, tensorflow, keras, pytorch], [deep learning, python, tensorflow], [python, scikit-learn, pandas, machine learning], [python, machine learning, pytorch], [python, tensorflow, keras], [python, machine learning, scikit-learn], [c++, java, r, tensorflow, keras, scikit-learn, aws, data analysis], [matlab, sql, excel, tableau, r], [java, scala, tensorflow, pytorch, kubernetes, docker], [c#, f#, kotlin, tensorflow, pytorch, jupyter, aws], [java, r, matlab, aws, jenkins, gitlab, data analysis, regression], [go, julia, pytorch, tensorflow, kubernetes, docker, aws], [python, docker, kubernetes, aws, jenkins, elasticsearch, logstash], [julia, tensorflow, matlab, kubernetes, data analysis, data science], [c++, kotlin, pytorch, tensorflow, aws, kubernetes, docker], [go, julia, tensorflow, keras, data analysis, aws, docker]]"
# preferences_string = get_preferences_list(preferences_string)
with open("stored_preferences.txt", "r+") as file:
    text = file.read()
    preferences_string = ast.literal_eval(text)
    print(preferences_string)

# in order to map the data easily, we use 2 dictionaries
dictionary_of_indices, dictionary_of_preferences = map_preferences(preferences_string)  # 0: '.net'; '.net': 0

new_individ_string1 = ["c", "c++", "java"]
new_individ_string2 = ["python"]
new_individ_string3 = ["react", "css"]
new_individ_numeric1 = get_preferences_as_numeric(new_individ_string1, dictionary_of_preferences)
new_individ_numeric2 = get_preferences_as_numeric(new_individ_string2, dictionary_of_preferences)
new_individ_numeric3 = get_preferences_as_numeric(new_individ_string3, dictionary_of_preferences)
preferences_numeric = [get_preferences_as_numeric(x, dictionary_of_preferences) for x in preferences_string]

# testing section
new_preferences = [new_individ_numeric1, new_individ_numeric2, new_individ_numeric3]

k = 5
voting_percentage = 0.7
knn_cosine = KNNCosine(k)
knn_euclidian = KNNEuclidian(k)
knn_jaccard = KNNJaccard(k)
knns = [knn_cosine, knn_euclidian, knn_jaccard]

for knn in knns:
    knn.fit(preferences_numeric)

for preference in new_preferences:
    print("*"*100)
    print(get_preferences_as_string(preference, dictionary_of_indices))

    for knn in knns:
        print(knn.name)
        predictions = knn.predict(preference)
        indices = list(map(lambda x: x[1], predictions))

        k_predicted_preferences = [preferences_numeric[x] for x in indices]

        best_x_percentage_predictions_numeric = get_best_x_percentage_best_predictions(preference, k_predicted_preferences, percentage=voting_percentage)
        best_x_percentage_predictions_string = [get_preferences_as_string(x, dictionary_of_indices) for x in best_x_percentage_predictions_numeric]
        print(best_x_percentage_predictions_string)

