console.log("Welcome to Jamal Person's COP 3060 assignment!");
console.log("GitHub Repository: https://github.com/JP397/index.html"); 

let myName = "Jamal";  
let myAge = 23; 
let isStudent = true; 
let favoriteGames = ["Zelda", "Fire Emblem", "Smash Bros", "Mario Kart", "Chrono Trigger"]; 
let userInfo = { city: "Port Saint Lucie", school: "FAMU" }; 
let futureGoal = null;


let sum = myAge + 6; 
let isEqual = myName === "Jamal"; 
let canGraduate = isStudent && myAge > 20; 

console.log("Sum:", sum, "| Equal:", isEqual, "| Graduate eligibility:", canGraduate);



const form = document.querySelector("#userInfoForm") || document.querySelector("form");
const statusEl = document.getElementById("password-status") || document.createElement("span"); 

if (!document.getElementById("password-status")) {
    statusEl.id = "password-status";
    statusEl.style.fontWeight = "bold";
    form.parentNode.insertBefore(statusEl, form.nextSibling); 
}



const loopContainer = document.getElementById("anime-list");
const dataContainer = document.getElementById("data-container") || document.createElement("div");
const fetchBtn = document.getElementById("fetch-button") || document.createElement("button");
const filterSelect = document.getElementById("sort-posts") || document.createElement("select");
const fetchDataStatus = document.getElementById("fetch-data-status");

if (!document.getElementById("fetch-button")) {
    fetchBtn.textContent = "Load Public API Data";
    dataContainer.parentNode.insertBefore(fetchBtn, dataContainer);
}

if (!document.getElementById("sort-posts")) {
    filterSelect.innerHTML = `
        <option value="all">Show All</option>
        <option value="a-z">Sort A–Z</option>
        <option value="z-a">Sort Z–A</option>
    `;
    dataContainer.parentNode.insertBefore(filterSelect, dataContainer);
}

function validateEmail(email) {
   
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function renderList(data) {
    dataContainer.innerHTML = ""; 

    if (data.length === 0) {
        dataContainer.textContent = "No results found.";
        return;
    }

    const ul = document.createElement("ul");
   
    data.forEach((item) => {
        const li = document.createElement("li");
        // Update DOM: Using name and email to represent an item
        li.textContent = `${item.name} (${item.email}) - City: ${item.address.city}`; 
        ul.appendChild(li);
    });
    dataContainer.appendChild(ul);
}

function handleError(err) {
    console.error("Fetch Error:", err);
    // f) Handle errors with user-visible messages
    fetchDataStatus.textContent = `🚨 Error loading data: ${err.message}.`;
    fetchDataStatus.style.color = "red";
    dataContainer.innerHTML = ""; 
}

if (loopContainer) {
    loopContainer.innerHTML = ''; // Clear content
    for (let i = 0; i < favoriteGames.length; i++) {
        let li = document.createElement("li");
        li.textContent = favoriteGames[i];
        loopContainer.appendChild(li);
    }
}

form.addEventListener("submit", (e) => {
    e.preventDefault();

    const emailInput = document.getElementById("email").value.trim();
    const nameInput = document.getElementById("fname").value.trim();

    if (validateEmail(emailInput) && nameInput.length >= 2) {
        statusEl.textContent = "✅ Validation passed! Data accepted.";
        statusEl.style.color = "green";
    } 
    else {
        statusEl.textContent = "Invalid input. Please check your name (min 2 chars) and email format.";
        statusEl.style.color = "red";
    }
});

let apiData = [];
async function handleFetch() {
    fetchDataStatus.textContent = "Loading data...";
    fetchDataStatus.style.color = "blue";
    dataContainer.innerHTML = "";

    try {
        const response = await fetch("https://jsonplaceholder.typicode.com/users");
        
        if (!response.ok) {
            throw new Error(`Network response was not ok, status: ${response.status}`);
        }
        
        const json = await response.json();
        apiData = json; // store array of objects

   
        if (apiData.length === 0) {
            fetchDataStatus.textContent = "Fetch successful, but no results found.";
            fetchDataStatus.style.color = "orange";
            return;
        }

        fetchDataStatus.textContent = "✅ Data loaded successfully!";
        fetchDataStatus.style.color = "green";
        renderList(apiData);
    } 
    catch (err) {
       
        handleError(err);
    }
}

function handleFilter() {
    
    if (apiData.length === 0) return;

    let processedData = [...apiData]; // Copy the original data

    
    switch (filterSelect.value) {
        case "a-z":
            processedData.sort((a, b) => a.name.localeCompare(b.name));
            break;
        case "z-a":
            processedData.sort((a, b) => b.name.localeCompare(a.name));
            break;
        case "all":
        default:
            processedData = apiData; 
            break;
    }

    renderList(processedData);
}
fetchBtn.addEventListener("click", handleFetch);
filterSelect.addEventListener("change", handleFilter);
