
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

function showText(textId, button) {
    var textDiv = document.getElementById(textId);
    var buttonRect = button.getBoundingClientRect();
    var textWidth = textDiv.offsetWidth;
    var startLeft = button.classList.contains("left") ? -textWidth : window.innerWidth;
    var endLeft = button.classList.contains("left") ? buttonRect.left - textWidth - 500 : buttonRect.right + 300;


    textDiv.style.left = startLeft + "px";
    textDiv.style.top = (buttonRect.top + button.offsetHeight - 250) + "px";
    textDiv.style.display = "block";

    var distance = Math.abs(startLeft - endLeft);
    var speed = 2;
    var duration = distance / speed;
    var startTime = null;

    function animate(timestamp) {
        if (!startTime) startTime = timestamp;
        var elapsed = timestamp - startTime;
        var progress = elapsed / duration;
        if (progress > 1) progress = 1;
        var currentLeft = startLeft + (endLeft - startLeft) * progress;
        textDiv.style.left = currentLeft + "px";
        if (progress < 1) {
            requestAnimationFrame(animate);
        }
    }

    requestAnimationFrame(animate);
}




function hideText(textId) {
    document.getElementById(textId).style.display = "none";
}
