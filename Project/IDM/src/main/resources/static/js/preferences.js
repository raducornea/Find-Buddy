function addItem(preference) {
    var ul = document.getElementById("list");
    var li = document.createElement("li");
    li.appendChild(document.createTextNode(preference));
    ul.appendChild(li);

    var hiddenInput = document.getElementById("hidden_preferences");
    hiddenInput.value = Array.from(ul.children).map(function(li) {
        return li.textContent;
    });
}

var input = document.getElementById("register_preferences");

input.addEventListener("keydown", function(e) {
    if (e.key === "Enter" && input.value !== "") {
        const value = input.value;

        if (value.includes(",")) {
            const elements = value.split(",");
            for (const element of elements) {
                if (element != "")
                    addItem(element);
            }
            input.value = "";
        } else {
            addItem(value);
            input.value = "";
        }
    }
});

input.addEventListener('input', () => {
    const value = input.value;

    if (value.includes(",")) {
        const elements = value.split(",");
        for (const element of elements) {
            if (element != "")
                addItem(element);
        }
        input.value = "";
    }
});