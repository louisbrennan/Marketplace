let dropdown = document.getElementsByClassName("dropdown-btn");
let i;
document.getElementById("dropdownSymbol").innerText = "+";

for (i = 0; i < dropdown.length; i++) {
    dropdown[i].addEventListener("click", function () {
        this.classList.toggle("active");
        let dropdownContent = this.nextElementSibling;
        if (dropdownContent.style.display === "flex") {
            document.getElementById("dropdownSymbol").innerText = "＋";
            dropdownContent.style.display = "none";
        } else {
            document.getElementById("dropdownSymbol").innerText = "–";
            dropdownContent.style.display = "flex";
        }
    });
}

let filters = document.getElementsByClassName("filter");

for (i =0; i < filters.length; i++) {
    filters[i].addEventListener("click", function () {
        this.classList.toggle("active");
    })
}
