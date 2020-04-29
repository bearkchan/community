function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    if (!content.trim()){
        alert("不能回复空内容～～")
        return;
    }
    $.ajax({
        url: "/comment",
        type: "POST",
        contentType:"application/json",
        data: JSON.stringify({
            parentId: questionId,
            content: content,
            type: 1
        }),
        dataType: "json",
        success: function (data) {
            if (data.code==200){
                $("#comment_section").hide()
                window.location.reload();
            }else {
                alert(data.message)
            }
        }
    })
}


function login() {
    window.open("https://github.com/login/oauth/authorize?client_id=2cc2f068391fc9357452&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
    window.localStorage.setItem("closable",true);
}