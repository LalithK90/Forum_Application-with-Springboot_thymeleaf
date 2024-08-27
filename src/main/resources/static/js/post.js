$(document).ready(function () {
    let currentPage = 1;
    let postsPerPage = $("#postForPage").val();

    function fetchPosts(page, sortOrder) {
        $.ajax({
            url: `/forum/api/posts?page=${page}&size=${postsPerPage}&sortOrder=${sortOrder}`,
            method: 'GET',
            success: function (data) {
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
        } else {
            return ``;
        }
    }

    function renderPagination(totalPages, currentPage) {
        let paginationContainer = $('.pagination');
        paginationContainer.empty();

        for (let i = 1; i <= totalPages; i++) {
            paginationContainer.append(`
                    <li class="page-item ${i === currentPage ? 'active' : ''}">
                        <a class="page-link post-link" href="#">${i}</a>
                    </li>
                `);
        }

        $('.post-link').click(function (e) {
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


let number;
let page;

function getData(val) {
    page = 0;
    let modal_lod = $("#modal_load");
    modal_lod.show();
    hideCommentSection()
    let postTitle = $("#post_title");
    let postOwner = $("#post_owner");
    let postContent = $("#post_content");
    let postViewCount = $("#postViewCount");

    let postDetailUrl = $("#postDetailUrl").val();
    $.ajax({
        url: `${postDetailUrl}${val}`, method: 'GET', success: function (data) {
            modal_lod.hide();
            postTitle.html(data.title);
            postOwner.html(data.postOwner);
            postViewCount.html(data.postViewCount);
            postContent.html(data.content);
            number = data.number;
            getReaction();
            loadComments();
        }, error: function (error) {
            console.error("Error fetching posts", error);
        }
    });
}

// get data when modal scroll
// $('#myModal .modal-body').on('scroll', function () {
//     let scrollHeight = $(this).prop('scrollHeight');
//     let scrollTop = $(this).scrollTop();
//     let clientHeight = $(this).outerHeight();
//     if (scrollTop + clientHeight >= scrollHeight) {
//         loadComments();
//     }
// });

function getReaction() {
    let getPostReactionUrl = $("#getPostReactionUrl").val() + number;
    $.ajax({
        url: getPostReactionUrl, method: 'GET', success: function (data) {
            setReactionCount(data)
        }, error: function (error) {
            console.error("Error fetching posts", error);
        }
    });
}

function submitReaction(reaction) {
    let persistPostReactionUrl = $("#persistPostReactionUrl").val() + number + "/" + reaction;
    $.ajax({
        url: persistPostReactionUrl, method: 'GET', success: function (data) {
            setReactionCount(data)
        }, error: function (error) {
            console.error("Error fetching posts", error);
        }
    });
}

function setReactionCount(data) {
    $("#likeCount").html(data.LIKE);
    $("#dislikeCount").html(data.DIS_LIKE);
    $("#celebrateCount").html(data.CELEBRATE);
    $("#supportCount").html(data.SUPPORTIVE);
    $("#loveCount").html(data.LOVE);
    $("#insightfulCount").html(data.INSIGHTFUL);
}

let paginationData;

function loadComments() {
    let postId = number;
    const commentSection = $('#commentSection');
    let url = $("#getCommentUrl").val().replace("?page=&size=", "");
    let cleanedUrl = url.replace("?page=&size=", "");

    $.ajax({
        url: `${cleanedUrl}${postId}`,
        data: {page: page, size: 5},
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            commentSection.empty()
            let dataArray = data.content.sort((a, b) => b.id - a.id);
            const uniqueObjects = filterUniqueById(dataArray)

            appendComments(uniqueObjects);
            console.log(data.page)
            paginationData = data.page;
            renderPagination(data.page)
        },
        error: function (xhr, status, error) {
            console.error('Error loading comments:', error);
        }
    });

    function appendComments(commentsArray, indentLevel = 0) {
        commentsArray.forEach(comment => {
            let html = createCommentHtml(comment, indentLevel);
            commentSection.append(html);
            if (comment.replies && comment.replies.length > 0) {
                appendComments(comment.replies, indentLevel + 1);
            }
        });
    }

    function createCommentHtml(comment, indentLevel) {
        let indent = `<div class="row p-2"><div class="col-sm-${indentLevel}"></div><div class="col-sm-${12 - indentLevel}">`;
        return indent + makeCommentView(comment) + `</div></div>`;
    }

    function renderPagination(pagination) {
        const {size, number, totalElements, totalPages} = pagination;
        const $paginationElement = $('.pagination-comment');
        $paginationElement.empty(); // Clear any existing pagination

        // Previous button
        const prevDisabled = number === 0 ? 'disabled' : '';
        $paginationElement.append(`
                <li class="page-item ${prevDisabled}">
                    <a class="page-link comment-link" href="#" aria-label="Previous" onclick="changePageNumber(${number - 1})">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            `);

        // Page numbers
        for (let i = 0; i < totalPages; i++) {
            const activeClass = i === number ? 'active' : '';
            $paginationElement.append(`
                    <li class="page-item ${activeClass}">
                        <a class="page-link comment-link" href="#" onclick="changePageNumber(${i})">${i + 1}</a>
                    </li>
                `);
        }

        // Next button
        const nextDisabled = number === totalPages - 1 ? 'disabled' : '';
        $paginationElement.append(`
                <li class="page-item ${nextDisabled}">
                    <a class="page-link comment-link" href="#" aria-label="Next" onclick="changePageNumber(${number + 1})">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            `);
    }


    const filterUniqueById = (array) => {
        const seen = new Set();
        return array.filter(obj => {
            const duplicate = seen.has(obj.id);
            seen.add(obj.id);
            return !duplicate;
        });
    };

    function makeCommentView(comment) {
        return `<div class="card" id="comment_card_${comment.id}">
              <div class="card-body" id="comment_body_${comment.id}">${comment.content}</div>
              <div class="row">
                <div class="d-flex justify-content-end">
                  <div class="btn-group" role="group" aria-label="Reaction button group">
                  ${comment.editable ? `<button class="btn btn-sm rounded-pill btn-outline-danger mx-2" onclick="deleteComment(${comment.id})"><i class="fas fa-trash"></i></button>` : ''}
                  <button class="btn btn-sm rounded-pill btn-outline-primary mx-1" onclick="commentForComment(${comment.id})"><i class="fas fa-comment"></i></button>
                  ${comment.editable ? `<button class="btn btn-sm rounded-pill btn-outline-info mx-1" onclick="editComment(${comment.id})"><i class="fas fa-edit"></i></button>` : ''}
                  <span class="card-subtitle text-muted text-right mx-1"> ${comment.createdBy}  at ${comment.createdAt}</span>
                    <button type="button" class="btn btn-sm mx-1 rounded-pill btn-outline-primary" onclick="submitCommentReaction(${comment.id},'LIKE')">
                      <i class="fas fa-thumbs-up"></i> <span id="LIKE_${comment.id}">${comment.commentReactions.LIKE}</span>
                    </button>
                    <button type="button" class="btn btn-sm mx-1 rounded-pill btn-outline-secondary" onclick="submitCommentReaction(${comment.id},'DIS_LIKE')">
                      <i class="fas fa-thumbs-down"></i> <span id="DIS_LIKE_${comment.id}">${comment.commentReactions.DIS_LIKE}</span>
                    </button>
                    <button type="button" class="btn btn-sm mx-1 rounded-pill btn-outline-success" onclick="submitCommentReaction(${comment.id},'CELEBRATE')">
                      <i class="fas fa-star"></i> <span id="CELEBRATE_${comment.id}">${comment.commentReactions.CELEBRATE}</span>
                    </button>
                    <button type="button" class="btn btn-sm mx-1 rounded-pill btn-outline-warning" onclick="submitCommentReaction(${comment.id},'SUPPORTIVE')">
                      <i class="fas fa-hands-helping"></i> <span id="SUPPORTIVE_${comment.id}">${comment.commentReactions.SUPPORTIVE}</span>
                    </button>
                    <button type="button" class="btn btn-sm mx-1 rounded-pill btn-outline-danger" onclick="submitCommentReaction(${comment.id},'LOVE')">
                      <i class="fas fa-heart"></i> <span id="LOVE_${comment.id}">${comment.commentReactions.LOVE}</span>
                    </button>
                    <button type="button" class="btn btn-sm mx-1 rounded-pill btn-outline-info" onclick="submitCommentReaction(${comment.id},'INSIGHTFUL')">
                      <i class="fas fa-lightbulb"></i> <span id="INSIGHTFUL_${comment.id}">${comment.commentReactions.INSIGHTFUL}</span>
                    </button>
                  </div>
                </div>
              </div>
           </div>`;
    }

}

function changePageNumber(number) {
    const pageNumber = parseInt(number, 10);
    if (!isNaN(pageNumber) && pageNumber >= 0 && pageNumber < paginationData.totalPages) {
        page = pageNumber;
        loadComments();
    }
}

function deleteComment(commentId) {
    const deleteUrl = $("#deleteCommentUrl").val();
    $.ajax({
        url: `${deleteUrl}${commentId}`,
        method: 'GET',
        success: function (data) {
            loadComments()
        },
        error: function (xhr, status, error) {
            console.error('Error loading comments:', error);
        }
    });
}

function submitCommentReaction(commentId, reaction) {
    let persistPostReactionUrl = $("#persistCommentReactionUrl").val() + commentId + "/" + reaction;
    $.ajax({
        url: persistPostReactionUrl, method: 'GET', success: function (data) {
            setReactionToCommentCount(commentId, data)
        }, error: function (error) {
            console.error("Error fetching posts", error);
        }
    });
}

function setReactionToCommentCount(id, data) {
    $(`#LIKE_${id}`).html(data.LIKE);
    $(`#DIS_LIKE_${id}`).html(data.DIS_LIKE);
    $(`#CELEBRATE_${id}`).html(data.CELEBRATE);
    $(`#SUPPORTIVE_${id}`).html(data.SUPPORTIVE);
    $(`#LOVE_${id}`).html(data.LOVE);
    $(`#INSIGHTFUL_${id}`).html(data.INSIGHTFUL);
}

function commentForComment(commentId) {
    hideCommentSection();
    let commentAfter = $(`#comment_card_${commentId}`);
    commentAfter.after(commentSection(commentId, '', false))
    trumbowygCall();
}

function editComment(commentId) {
    hideCommentSection();
    let textComment = $(`#comment_card_${commentId}`);
    let commentAfter = $(`#comment_body_${commentId}`);
    textComment.after(commentSection(commentId, commentAfter.html(), true))
    commentAfter.empty();
    trumbowygCall();
}

$("#btnComment").click(function () {
    hideCommentSection();
    const blogPost = $("#blogPost");
    blogPost.after(commentSection('', '', false))
    trumbowygCall();
})

function commentSection(id, text, editStatus) {
    return `<div class="card p-3" id="commentNewSection">
              <form id="commentForm">
               <input type="hidden" value="${id}" id="commentId_${id}"/>
               <input type="hidden" value="${editStatus}" id="commentIdEdit_${id}"/>
                 <div class="mb-3">
                   <label for="content" class="form-label">Comment</label>
                     <textarea class="form-control" id="comment_content_${id}" rows="3">${text}</textarea>
                 </div>
                 <div class="mb-3">
                   <button type="button" class="btn btn-sm btn-outline-info" onclick="saveComment(${id})">Save Comment</button>
                 </div>
               </form>
            </div>`;
}

function hideCommentSection() {
    $("#commentNewSection").remove();
}

function saveComment(id) {
    let number_post = '';
    let content;
    if (!id) {
        number_post = number;
        content = $(`#comment_content_`).val()
    } else {
        content = $(`#comment_content_${id}`).val();
    }
    let commentDto = {
        commentId: $(`#commentId_${id}`).val(),
        number: number_post,
        edit: $(`#commentIdEdit_${id}`).val(),
        content: content
    }
    let url = $("#persistCommentUrl").val();
    $.ajax({
        url: `${url}`,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(commentDto),
        success: function (data) {
            // console.log(data)
            // if (!data.parentCommentId) {
            //     comments.push(data)
            // }
            hideCommentSection()
            loadComments()
        }, error: function (error) {
            console.error("Error fetching posts", error);
        }
    });
}

// need to manage scroll function in side the modal
// need to create react save and get react for comment reaction
// need to edit comment in side