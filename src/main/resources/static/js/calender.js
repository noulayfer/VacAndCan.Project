const form = document.getElementById('vacancy-form');
const saveButton = document.getElementById('save-button');
const synchronizeCheckbox = document.getElementById('synchronize-checkbox');

form.addEventListener('submit', function(event) {
    if (synchronizeCheckbox.checked) {
        event.preventDefault();
        fetch('/save_vacancy_add_event', {
            method: 'POST',
            body: new FormData(form)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/';
                } else {
                    alert('Failed to save vacancy.');
                }
            })
            .catch(error => {
                alert('Failed to save vacancy.');
            });
    }
});