

document.addEventListener("DOMContentLoaded", function() {
    let geoLink = document.getElementById("geoLink");
    let geoPopup = document.getElementById("geoPopup");

    // Show the popup when hovering over the link
    geoLink.addEventListener("mouseenter", function() {
        geoPopup.style.display = "block";
    });

    // Keep it open when hovering over the popup
    geoPopup.addEventListener("mouseenter", function() {
        geoPopup.style.display = "block";
    });

    // Hide when mouse leaves both the link and popup
    geoLink.addEventListener("mouseleave", function() {
        setTimeout(() => {
            if (!geoPopup.matches(":hover")) {
                geoPopup.style.display = "none";
            }
        }, 200); // Delay to allow moving the cursor
    });

    geoPopup.addEventListener("mouseleave", function() {
        geoPopup.style.display = "none";
    });
});