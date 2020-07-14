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
                $.each(data.data, function (index,comment) {
                    var img = $("<img/>", {
                        "class": "mr-3 rounded-lg community-img",
                        "src": comment.user.avatarUrl
                    });
                    var media = $("<div/>", {
                        "class": "media community-media",
                    });
                    var div = $("<div/>", {
                        "class": "col-9"
                    }).append(
                        $("<h7/>", {
                            "class":"mt-0",
                            "text":comment.user.name
                        })
                    ).append($("<div/>", {
                        "class": "row",
                    }).append($("<span/>", {
                        "class": "col-9",
                        "text": comment.content
                    })).append($("<span/>", {
                        "class": "col-3",
                        "style": "float: right, padding: 0px",
                        "text": new Date(comment.gmtCreate).format("MM-dd-yyyy")
                    })));
                    media.append(img);
                    media.append(div);
                    var c = $("<div/>", {
                        "class": "col-lg-12 col-mid-12 col-sm-12 col-xs-12",
                    });
                    c.append(media);
                    c.append($("<hr/>"));
                    comments.prepend(c);
                });
            });
        }
    }
    else {
        e.classList.remove("active");
        comments.re
    }
}

/**
 * post the sub-comment to the sub-comment list
 * @param e
 */
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

Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}
