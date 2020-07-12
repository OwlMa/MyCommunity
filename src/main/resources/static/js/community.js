function post() {
    var articleId = $("#article_id").val();
    var content = $("#comment_content").val();
    if (!content) {
        alert("comment can not be empty");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": articleId,
            "content" : content,
            "type" : 1
        }),
        success: function (response) {
            if (response.code == 200) {
                // $("#comment_block").hide();
                window.location.reload();
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function showComments(e) {
     var id = e.getAttribute("data-d");
     var comments = $("#comment-" + id);
    // comments.addClass("show");
    comments.toggleClass("show");
}