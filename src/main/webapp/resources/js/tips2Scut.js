/**
 * Created by Administrator on 13-12-20.
 */

(function(){
    $(function() {
        $('nav#menu').mmenu( {
            slidingSubmenus: false
        });
    });

    $('.btn-page').bind("click",function() {
        var page = this.id.replace('btn','page');
        $('.page').hide().each(
            function() {
                if(this.id == page) {
                    $(this).show();
                }
            }
        );
    });

    function changeImg() {
        var imgSrc = $("#register_img_checkCode");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", chgUrl(src));
    }

    function chgUrl(url) {
        var timestamp = (new Date()).valueOf();
        url = url.substring(0, 17);
        if ((url.indexOf("&") >= 0)) {
            url = url + "×tamp=" + timestamp;
        } else {
            url = url + "?timestamp=" + timestamp;
        }
        return url;
    }

    $('#register_img_checkCode').bind("click",function() {
        changeImg();
    });


}());