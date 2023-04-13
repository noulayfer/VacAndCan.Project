const checkboxes = document.querySelectorAll('.my-table input[type="checkbox"]');
const button = document.querySelector('.button-group button');

function updateSelectedIds() {
    console.log('changing');
    const selectedIds = Array.from(checkboxes)
        .filter((cb) => cb.checked)
        .map((cb) => cb.closest('tr').querySelector('.d-none').textContent);
    document.getElementById('selectedIds1').value = selectedIds;
    document.getElementById('selectedIds2').value = selectedIds;
}


function updateButtonStyle() {
    const checkedCount = document.querySelectorAll('.my-table input[type="checkbox"]:checked').length;
    const buttons = document.getElementsByClassName('button');
    for (let i = 0; i < buttons.length; i++) {
        const button = buttons[i];
        if (checkedCount > 0) {
            button.classList.add('checked');
        } else {
            button.classList.remove('checked');
        }
    }
    updateSelectedIds();
}

checkboxes.forEach((checkbox) => {
    checkbox.addEventListener('change', () => {
        updateButtonStyle();
        console.log('changing');
    });
});

updateButtonStyle();




const table = document.querySelector('.my-table');
const headers = table.querySelectorAll('th');
let sortColumn = -1;
let sortOrder = 'asc';

headers.forEach((header, index) => {
    const arrow = document.createElement('span');
    arrow.classList.add('arrow');
    header.appendChild(arrow);

    header.addEventListener('click', () => {
        sortColumn = index;
        sortOrder = sortOrder === 'asc' ? 'desc' : 'asc';

        // Hide all other arrows
        headers.forEach((h, i) => {
            const hArrow = h.querySelector('.arrow');
            if (i !== sortColumn) {
                h.classList.remove('sorted');
                hArrow.innerHTML = '';
            } else {
                h.classList.add('sorted');
                hArrow.innerHTML = sortOrder === 'asc' ? '&uarr;' : '&darr;';
            }
        });

        const rows = Array.from(table.querySelectorAll('tbody tr'));

        rows.sort((a, b) => {
            const aVal = a.querySelectorAll('td')[sortColumn].innerText;
            const bVal = b.querySelectorAll('td')[sortColumn].innerText;

            return sortOrder === 'asc' ? aVal.localeCompare(bVal) : bVal.localeCompare(aVal);
        });

        table.querySelector('tbody').innerHTML = '';
        rows.forEach(row => table.querySelector('tbody').appendChild(row));
    });
});

// Add a click listener to the arrow to toggle sorting for this column
document.addEventListener('click', event => {
    const arrow = event.target.closest('.arrow');
    if (arrow) {
        event.stopPropagation();
        sortColumn = -1;
        sortOrder = 'asc';
        const header = arrow.parentNode;
        header.classList.remove('sorted');
        arrow.innerHTML = '';
    }
});

var header = document.getElementById("myHeader");

header.style.opacity = "0";
header.style.pointerEvents = "none";

document.addEventListener("mousemove", function(event) {
    if (event.clientY <= 50) {
        header.style.opacity = "1";
        header.style.pointerEvents = "auto";
    } else {
        header.style.opacity = "0";
        header.style.pointerEvents = "none";
    }
});


