ACC.download = {

    _autoload: [
        "downloadlink"
    ],

    downloadlink: function () {
        var download = "<button class='btn btn-primary btn-block' id='item__list--item--download'>Download</button>";
        $('.item__list--item').append(download);
        $(".item__list--item").on('click', '#item__list--item--download',
            function (event) {
                event.preventDefault();
                $.ajax({
                    url: '/springernaturestorefront/downloadlink/' + $(location).attr('pathname').split('/').pop(),
                    type: 'GET',
                    dataType: 'json',


                    success: function(response){
                        window.open(response.access.pdf.url, '_blank');
                    },


                    error: function(jqXHR, textStatus, errorThrown){
                        alert("Server error!");
                    },


                    complete: function(){

                    }
                });
            }
        );
    }
};