ACC.download = {

    _autoload: [
        ["downloadlink", $(".item__list--item").length == 1]
    ],

    downloadlink: function () {
        var listItem = $(".item__list--item");
        var ppvDownloadButton = "<button class='btn btn-primary btn-block' id='item__list--item--download'>Download</button>";
        listItem.append(ppvDownloadButton);
        listItem.on('click', '#item__list--item--download',
            function (event) {
                event.preventDefault();
                var downloadUrl = listItem.data("download-url") + $(location).attr('pathname').split('/').pop();
                $.ajax({
                    url: downloadUrl ,
                    type: 'GET',
                    dataType: 'json',
                    success: function(response){
                        window.open(response.access.pdf.url, '_tab');
                    },
                    error: function(jqXHR, textStatus, errorThrown){
                    },
                    complete: function(){
                    }
                });
            }
        );
    }
};