$(document).ready(function () {
    $('.commentForm').submit(function (event) {
        event.preventDefault();
        const commentData = {
            content: $(this).find('.commentContent').val(),
            postId: $(this).data('post-id') // Assuming there's a data attribute for post ID
        };

        $.ajax({
            type: 'POST',
            url: '/comments',
            data: JSON.stringify(commentData),
            contentType: 'application/json',
            success: function (response) {
                alert('Comment added successfully!');
                window.location.reload(); // Refresh to see the new comment
            },
            error: function (error) {
                alert('Error adding comment: ' + error.responseText);
            }
        });
    });
});
