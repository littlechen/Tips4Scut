/**
 * Created by Administrator on 13-12-27.
 */

jQuery(function() {
    searchBooks.ready();
});

var searchBooks = {
    tableTitle: '<tbody><tr><th>书名</th><th>作者</th><th>出版社</th><th>索书号</th><th>出版年份</th><th>中文图书</th></tr></tbody>',
    bookItemTpl:
        '<tbody><tr>' +
        '<td id="title"><a id="{{id}}" class="searchBook_bookId" >{{title}}</a></td>' +
        '<td id="author">{{author}}</td>' +
        '<td id="publish">{{publish}}</td>' +
        '<td id="callNum">{{callNum}}</td>' +
        '<td id="year">{{year}}</td>' +
        '<td id="type">{{type}}</td>' +
        '</tr></tbody>',

    bookDetailsTpl:
        '<h5>' +
        '<p id="author">{{author}}</p>' +
        '<p id="publish">{{publish}}</p>' +
        '<p id="callNumber">{{callNumber}}</p>' +
        '<p id="remainCount">{{remainCount}}</p>' +
        '<p id="status">{{status}}</p>' +
        '<p id="more">{{more}}</p>' +
        '</h5>',

    keyword: '',
    page: 1,

    toHTML: function(tpl, o) {
        var key;
        for (key in o) {
            if (o.hasOwnProperty(key)) {
                tpl = tpl.replace('{{' + key + '}}', o[key]);
            }
        }
        return tpl;
    },

    search: function() {
        var input = $('#searchBook_inputKeyword').val().trim();
        if (searchBooks.keyword != input) {
            searchBooks.keyword = input;
            searchBooks.page = 1;
            $('#searchBook_tb_booksInfo').empty().append(searchBooks.tableTitle);
        }

        $.get('/library/searchBook', { title: searchBooks.keyword, page: searchBooks.page },
            function(data) {
                searchBooks.page = searchBooks.page + 1;
                var html = '';
                $.each(data,
                    function(index, item) {
                        var o = {
                            id: item.id,
                            title: item.title,
                            author: item.author,
                            publish: item.publish,
                            isbn: item.isbn,
                            callNum: item.callNum,
                            year: item.year,
                            type: item.type
                        };
                        if ((o.author).length > 12) {
                            o.author = (o.author).substr(0, 15) + '...';
                        }
                        if ((o.title).length > 12) {
                            o.title = (o.title).substr(0, 15) + '...';
                        }
                        html += searchBooks.toHTML(searchBooks.bookItemTpl, o);
                    }
                );

                $('#searchBook_tb_booksInfo').append(html);
                $('#searchBook_searchMore').show();

                $('a.searchBook_bookId').bind('click',
                    function() {
                        var id = this.id;
                        $.get('/library/bookInfo', { id: id },
                            function(item) {
                                var o = {
                                    title: item.title,
                                    author: item.author,
                                    publish: item.publish,
                                    type: item.type,
                                    isbn: item.isbn,
                                    carrier: item.carrier,
                                    callNumber: item.callNumber,
                                    more: item.more,
                                    remainCount: item.remainCount,
                                    status: (item.status)
                                };
                                var re = new RegExp("\\$", 'g');
                                item.status = (item.status).replace(re, ' ');
                                re = new RegExp("\\|", 'g');
                                o.status = (item.status).replace(re, '<br/>');

                                var bookDetail = searchBooks.bookDetailsTpl;
                                bookDetail = searchBooks.toHTML(bookDetail, o);

                                $('.modal-books').empty();
                                $('.modal-body').empty();
                                $('.modal-books').append('<h5 class="modal-title" id="title">' + o.title + '</h5>');
                                $('.modal-body').append(bookDetail);
                                $('#searchBook_dialog_bookDetail').modal("show");
                            }
                        );
                    }
                );
            },'json'
        );
    },

    ready: function() {
        $('#searchBook_searchMore').hide();
        $("#searchBook_btnSearch").bind("click",
            function() {
                $("#searchBook_tb_booksInfo").empty();
                searchBooks.search();
            }
        );
        $("#searchBook_searchMore").bind("click",
            function() {
                searchBooks.search();
            }
        );
    }
};