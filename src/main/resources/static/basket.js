function addToBasket(id) {
    fetch("/basket/add/${id}", { method: "PUT" });
}

function removeFromBasket(id) {
    fetch("/basket/remove/${id}", { method: "PUT" });
}

function deleteFromBasket(id) {
    fetch("basket/delete/${id}", { method: "DELETE" });
}