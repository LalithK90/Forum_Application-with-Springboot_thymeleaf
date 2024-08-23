SnapSelect('#demo', {
    liveSearch: true,
    placeholder: 'Select Tags for the post...',
    clearAllButton: true,
    selectOptgroups: false,
    selectAllOption: false,
    closeOnSelect: false,
    maxSelections: Infinity,
    allowEmpty: false,
});

$(document).ready(function () {
    $("#btnPostDelete").click(function () {
        let number = $("#number").val();
        let postDUrl = $("#postDUrl").val();
        swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover this post with all other related content !",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    $.ajax({
                        url: `${postDUrl}${number}`,
                        method: 'GET',
                        success: function (data) {
                            if (data.status) {
                                window.open(data.redirectUrl, "_self")
                            }
                        },
                        error: function (error) {
                            console.error("Error fetching posts", error);
                        }
                    });
                } else {
                    swal("Your post is safe!");
                }
            });
    });
})