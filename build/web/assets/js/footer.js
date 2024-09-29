function submit() {

    document.getElementById("contact-form").addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent the default form submission

        // Get the email input value
        var email = document.getElementById("mc-email").value;

        // Create a URL with the Google Apps Script endpoint and query parameters
        var scriptURL = "https://script.google.com/macros/s/AKfycbzgkPTrXSyxMUFJEe07nB9W25yBOg8dLr9DRvIqZ0QiPsM1LM-jHcjHYod4_kdOn2cf/exec?Email=" + email;

        // Send a GET request to the Google Apps Script
        fetch(scriptURL, {method: "GET"})
                .then(response => {
                    // Handle the response here (if needed)
                    console.log("Request sent successfully.");
                })
                .catch(error => {
                    // Handle any errors here
                    console.error("Error sending the request:", error);
                });
    });
}
