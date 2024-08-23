/*
$(document).ready(function () {
    let currentPage = 1;
    let postsPerPage = $("#postForPage").val();

    function fetchPosts(page, sortOrder) {
        $.ajax({
            url: `/forum/api/posts?page=${page}&size=${postsPerPage}&sortOrder=${sortOrder}`,
            method: 'GET',
            success: function (data) {
                console.log(data)
                renderPosts(data.posts);
                renderPagination(data.totalPages, page);
            },
            error: function (error) {
                console.error("Error fetching posts", error);
            }
        });
    }

    function renderPosts(posts) {
        let postContainerBody = $('#postDisplay');
        postContainerBody.empty();
        posts.forEach(post => {
            let html = `<div type="button" class="row singlePost rounded shadow" data-bs-toggle="modal" data-bs-target="#myModal">
                              <div class="col-sm-10 modal_open" onclick="getData('${post.number}')" >
                               <h4 class="fw-bold text-left text-wrap col-12">${post.title}</h4>
                                 <div class="row">
                                  <div class="col-sm-8">
                                    <span class="badge bg-success mx-2">  View Count: ${post.viewCount}</span>
                                    <span class="badge bg-info mx-5"> React Count: ${post.reactCount}</span>
                                    <span class="h1 badge bg-danger bg-opacity-50 mx-5 p-2">Click Here to See the Post</span>
                                  </div>
                                  <div class="col-sm-4">
                                    <span class="badge rounded-pill bg-secondary">   ${post.createdBy}</span>
                                    <span class="badge rounded-pill bg-warning"> at ${post.createdAt}</span>
                                   </div>
                                 </div>
                               </div>
                              <div class="col-sm-1 p-2">
                                <img alt="profile image" class="rounded-pill shadow pro_image" src="${post.profileUrl}">
                              </div>
                              ${editableButton(post.editable, post.number)}
                            </div>`;
            postContainerBody.append(html)
        });
    }

    function editableButton(editable, number) {
        if (editable) {
            return `<div class="col-sm-1 pt-3">
              <a href="/forum/post/edit/${number}">
                <button class="btn btn-outline-primary"><i class="fas fa-edit"></i></button>
              </a>
            </div>`;
        }else {
            return ``;
        }
    }

    function renderPagination(totalPages, currentPage) {
        let paginationContainer = $('.pagination');
        paginationContainer.empty();

        for (let i = 1; i <= totalPages; i++) {
            paginationContainer.append(`
                    <li class="page-item ${i === currentPage ? 'active' : ''}">
                        <a class="page-link" href="#">${i}</a>
                    </li>
                `);
        }

        $('.page-link').click(function (e) {
            e.preventDefault();
            const page = parseInt($(this).text());
            fetchPosts(page, $('#sortOrder').val());
        });
    }

    $('#sortOrder').change(function () {
        fetchPosts(currentPage, $(this).val());
    });

    fetchPosts(currentPage, 'latest');
});
*/
let number;
$(document).ready(function () {
    getData('DoL338YSF9');
})

function getData(val) {
    let postTitle = $("#post_title");
    let postOwner = $("#post_owner");
    let postContent = $("#post_content");
    let postViewCount = $("#postViewCount");


    let postDetailUrl = $("#postDetailUrl").val();
    $.ajax({
        url: `${postDetailUrl}${val}`,
        method: 'GET',
        success: function (data) {
            postTitle.html(data.title);
            postOwner.html(data.postOwner);
            postViewCount.html(data.postViewCount);
            postContent.html(data.content);
            number = data.number;
            getReaction();
        },
        error: function (error) {
            console.error("Error fetching posts", error);
        }
    });
}

function getReaction() {
    let getPostReactionUrl = $("#getPostReactionUrl").val() + number;
    $.ajax({
        url: getPostReactionUrl,
        method: 'GET',
        success: function (data) {
            setReactionCount(data)
        },
        error: function (error) {
            console.error("Error fetching posts", error);
        }
    });
}

function submitReaction(reaction) {
    let persistPostReactionUrl = $("#persistPostReactionUrl").val() + number + "/" + reaction;
    $.ajax({
        url: persistPostReactionUrl,
        method: 'GET',
        success: function (data) {
            setReactionCount(data)
        },
        error: function (error) {
            console.error("Error fetching posts", error);
        }
    });
}

function setReactionCount(data) {
    $("#likeCount").html(data.LIKE);
    $("#dislikeCount").html(data.DIS_LIKE);
    $("#celebrateCount").html(data.CELEBRATE);
    $("#supportCount").html(data.SUPPORT);
    $("#loveCount").html(data.LOVE);
    $("#insightfulCount").html(data.INSIGHTFUL);
}

