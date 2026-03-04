let dropdown = document.getElementsByClassName("dropdown-btn");
let i;

for (i = 0; i < dropdown.length; i++) {
    dropdown[i].addEventListener("click", function () {
        this.classList.toggle("active");
        let dropdownContent = this.nextElementSibling;
        if (dropdownContent.style.display === "flex") {
            dropdownContent.style.display = "none";
        } else {
            dropdownContent.style.display = "flex";
        }
    });
}

function loadFilter(name, container) {
    const btn = document.createElement("button");
    btn.textContent = name;
    btn.classList.add("filter");
    btn.addEventListener("click", function () {
        this.classList.toggle("active");

    })
    container.appendChild(btn);
}


const fictionContainer = document.getElementById("fiction");
fictionGenres.forEach(g => loadFilter(g, fictionContainer));


const nonFictionContainer = document.getElementById("non-fiction");
nonFictionGenres.forEach(g => loadFilter(g, nonFictionContainer));