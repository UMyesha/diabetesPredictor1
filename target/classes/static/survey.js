document.getElementById("healthForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const surveyData = {
    gender: document.getElementById("gender").value,
    age: parseFloat(document.getElementById("age").value),
    hypertension: parseInt(document.getElementById("hypertension").value),
    heartDisease: parseInt(document.getElementById("heartDisease").value),
    smokingHistory: document.getElementById("smoking").value,
    bmi: parseFloat(document.getElementById("bmi").value),
    hbA1cLevel: parseFloat(document.getElementById("hba1c").value),
    bloodGlucoseLevel: parseFloat(document.getElementById("glucose").value),
  };

  try {
    const response = await fetch("http://localhost:8082/api/predict", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(surveyData),
    });

    if (!response.ok) throw new Error("Server returned an error");

    const result = await response.json(); // Should be like { severity: 0.63 }

    localStorage.setItem("surveyData", JSON.stringify(surveyData));
    localStorage.setItem("resultData", JSON.stringify(result));

    window.location.href = "results.html";
  } catch (err) {
    console.error("Prediction failed:", err);
    alert("Prediction failed: " + err.message);
  }
  localStorage.removeItem("surveyData");
localStorage.removeItem("resultData");

});
