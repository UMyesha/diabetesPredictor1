document.addEventListener("DOMContentLoaded", () => {
  const data = JSON.parse(localStorage.getItem("surveyData")) || {};
  const result = JSON.parse(localStorage.getItem("resultData")) || {};
console.log(" Loaded result from localStorage:", result);

  const severityIndex = parseInt(result.severity); 
  const confidence = parseFloat(result.score);

  const severityLabels = ["Low Risk", "Moderate Risk", "High Risk", "Critical"];
  const severityColors = ["#2ecc71", "#f1c40f", "#e67e22", "#e74c3c"];

  // Update text
  const label = severityLabels[severityIndex] ?? "Unknown";
  document.getElementById("severityDisplay").textContent = `${label} (${(confidence * 100).toFixed(1)}%)`;

  // Move and color bar
  const marker = document.getElementById("severityMarker");
  const bar = document.getElementById("severityBar");

  marker.style.left = `${(severityIndex / 3) * 100}%`;
  bar.style.background = `linear-gradient(to right, ${severityColors[severityIndex]}, ${severityColors[severityIndex]})`;

  // Fill metric values
  document.getElementById("glucoseValue").textContent = data.bloodGlucoseLevel ?? "--";
  document.getElementById("bmiValue").textContent = data.bmi ?? "--";
  document.getElementById("hba1cValue").textContent = data.hbA1cLevel ?? "--";

  // Risk Factors
  const riskFactorsList = document.getElementById("riskFactorsList");
  riskFactorsList.innerHTML = ""; // Clear old content

  if (parseFloat(data.bloodGlucoseLevel) >= 140)
    riskFactorsList.innerHTML += `<div class="risk-factor">High Blood Glucose</div>`;
  if (parseFloat(data.bmi) >= 25)
    riskFactorsList.innerHTML += `<div class="risk-factor">Elevated BMI</div>`;
  if (parseFloat(data.hbA1cLevel) >= 5.7)
    riskFactorsList.innerHTML += `<div class="risk-factor">High HbA1c</div>`;

  // Recommendations
  const recommendationsList = document.getElementById("recommendationsList");
  recommendationsList.innerHTML = "";
  const tips = ["Maintain a balanced diet", "Exercise regularly", "Get regular checkups"];
  tips.forEach(tip => {
    const div = document.createElement("div");
    div.className = "recommendation-item";
    div.textContent = tip;
    recommendationsList.appendChild(div);
  });
});
