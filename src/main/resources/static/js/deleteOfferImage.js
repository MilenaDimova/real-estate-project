document.querySelectorAll(".deleteImageBtn").forEach(btn =>  btn.addEventListener("click", function (e) {
    let requestOptions = {
        method: 'DELETE'
    };

    fetch(`http://localhost:8080/api/images/delete/` + e.target.getAttribute('data'), requestOptions).
    then(result => {
        if(result.ok){e.target.parentElement.remove()}
    }).
    catch(error => console.log('error', error))
}));