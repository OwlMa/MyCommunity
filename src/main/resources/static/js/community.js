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

/**
 * collapse and show the secondary comment
 * @param e
 */
function showComments(e) {
     var id = e.getAttribute("data-d");
     var comments = $("#comment-" + id);
    // comments.addClass("show");
    comments.toggleClass("show");
    if (comments.hasClass("show")) {
        e.classList.add("active");
        if (comments.children().length == 1) {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                var items = [];
                $.each(data.data, function (index,comment) {
                    var c = $("<div/>", {
                        "class": "col-lg-9 col-mid-12 col-sm-12 col-xs-12",
                        html:comment.content
                    });
                    comments.prepend(c);
                })
            });
        }
    }
    else {
        e.classList.remove("active");
        comments.re
    }
}

function postSubComment(e) {
    var commentId = e.getAttribute("data-id");
    var subCommentsId = $("#sub-comment" + commentId);
    var content = $(subCommentsId).val();
    if (!content) {
        alert("comment can not be empty");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": commentId,
            "content" : content,
            "type" : 2
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