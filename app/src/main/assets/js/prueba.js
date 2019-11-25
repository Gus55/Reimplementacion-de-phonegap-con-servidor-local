function myFunction(imgs) {
  var expandImg = document.getElementById("expandedImg");
  expandImg.src = imgs.src;
  expandImg.innerHTML = imgs.alt;
  expandImg.parentElement.style.display = "block";
}
