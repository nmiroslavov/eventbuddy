const eventId = document.getElementById('eventId').value

const commentContainer = document.getElementById("commentContainer");
const allComments = []

const displayComments = (comments) => {
    commentContainer.innerHTML += comments.map((c) => {
        return asComment(c)
    }).join('')
}

function asComment(c) {

    let commentHtml = `<div class="row mt-2 mb-2">
                            <div class="col-md-6 offset-md-3 col-xl-4 offset-xl-4">
                                <div class="card shadow">
                                    <div class="card-body">
                                        <h5 class="card-title">${c.user}</h5>
                                        <p class="card-text">${c.textContent}</p>
                                    </div>
                                    <div class="card-footer text-muted">
                                    ${c.creationDateTime}
                                    </div>
                                </div>
                            </div>
                        </div>`

    return commentHtml
}


fetch(`http://localhost:8080/api/events/${eventId}/comments`)
    .then(response => response.json())
    .then(data => {
        for (let comment of data) {
            allComments.push(comment);
        }
        displayComments(allComments)
    })