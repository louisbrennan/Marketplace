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

const fictionGenres = [
    "Action",
    "Adventure",
    "Chick Lit",
    "Contemporary Fiction",
    "Crime",
    "Dystopian",
    "Fantasy",
    "Gothic Fiction",
    "Historical Fiction",
    "Horror",
    "Magical Realism",
    "Mystery",
    "Paranormal",
    "Political Fiction",
    "Psychological Thriller",
    "Romance",
    "Science Fiction",
    "Thriller",
    "Urban Fantasy",
    "War Fiction",
    "Western",
    "Young Adult"
];

const fictionContainer = document.getElementById("fiction");
fictionGenres.forEach(g => loadFilter(g, fictionContainer));

const nonFictionGenres = [
    "Art & Photography",
    "Biography",
    "Business & Economics",
    "Cookbooks",
    "Crafts & Hobbies",
    "Education",
    "Essays",
    "Health & Fitness",
    "History",
    "Humor",
    "Journalism",
    "Memoir",
    "Philosophy",
    "Politics",
    "Psychology",
    "Religion & Spirituality",
    "Science",
    "Self-Help",
    "Sociology",
    "Sports",
    "Technology",
    "Travel",
    "True Crime",
    "Writing & Publishing"
];

const nonFictionContainer = document.getElementById("non-fiction");
nonFictionGenres.forEach(g => loadFilter(g, nonFictionContainer));