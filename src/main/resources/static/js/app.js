const selectElement =
  document.querySelectorAll(".gnt-color-picker"); /* Get all color pickers */

/* Change text color on load, and change :root css variables */
window.onload = function () {
  selectElement.forEach((element) => {
    element.style.color = getTextColor(element.value);

    document.documentElement.style.setProperty(
      element.dataset.color,
      hexToRgb(element.value)
    );
  });
};

/* Handle modal */
const displayButton = document.querySelector("#preview");
const exitModal = document.querySelector("#modal-bg");

displayButton.addEventListener("click", function () {
  document.querySelector("#modal").style.display = "block";
  document.querySelector("#modal-loader").style.display = "block";
  setTimeout(function () {
    document.querySelector("#modal-content").style.display = "block";
    document.querySelector("#modal-loader").style.display = "none";
  }, 300);
});

exitModal.addEventListener("click", function () {
  document.querySelector("#modal").style.display = "none";
  document.querySelector("#modal-content").style.display = "none";
});

/**
 * Event listener for the color picker.
 */
for (let i = 0; i < selectElement.length; i++) {
  selectElement[i].addEventListener("change", function () {
    changeColumnColor(selectElement[i]);
    selectElement[i].style.color = getTextColor(selectElement[i].value);
  });
}

/**
 * Event listener for the generate button.
 */
const generateButton = document.querySelector("#generate");
generateButton.addEventListener("click", function () {
  let primary = document
    .querySelector("[data-color='--primary-bg-color']")
    .value.replace("#", "");
  let secondary = document
    .querySelector("[data-color='--secondary-bg-color']")
    .value.replace("#", "");
  let action = document
    .querySelector("[data-color='--action-bg-color']")
    .value.replace("#", "");
  let action2 = document
    .querySelector("[data-color='--action2-bg-color']")
    .value.replace("#", "");

  const link = document.createElement("a");
  const uri =
    "https://generator.minstyle.io/download/" +
    primary +
    "/" +
    secondary +
    "/" +
    action +
    "/" +
    action2;

  console.log(uri);

  link.href = uri;
  link.download = "minstyle.io.css";
  link.target = "_blank";
  link.click();
});

/**
 * Change the color of the column after picking a color.
 * Also change the color in :root css variables for preview.
 * @param {*} inputColor
 */
function changeColumnColor(inputColor) {
  let divToChange = document.getElementById(inputColor.dataset.color);
  divToChange.style.backgroundColor = inputColor.value;
  document.documentElement.style.setProperty(
    inputColor.dataset.color,
    hexToRgb(inputColor.value)
  );
}

/* Function convert hex to rgb */
function hexToRgb(hex) {
  let c;
  c = hex.substring(1).split("");
  if (c.length == 3) {
    c = [c[0], c[0], c[1], c[1], c[2], c[2]];
  }
  c = "0x" + c.join("");
  return [(c >> 16) & 255, (c >> 8) & 255, c & 255].join(",");
}

/**
 * Function allowing to get text color from background color.
 * @param {*} color
 * @returns {string} text color.
 */
function getTextColor(color) {
  color = color.replace("#", "");
  let r = parseInt(color.substr(0, 2), 16);
  let g = parseInt(color.substr(2, 2), 16);
  let b = parseInt(color.substr(4, 2), 16);

  let brightness = Math.round((r * 299 + g * 587 + b * 114) / 1000);
  let textColour = brightness > 125 ? "#191919" : "#e6e6e6";

  return textColour;
}

/**
 * Color picker settings.
 */
Coloris({
  el: ".ms-color-field",
  wrap: false,
  theme: "large",
  themeMode: "auto",
  format: "hex",
  formatToggle: true,
});
