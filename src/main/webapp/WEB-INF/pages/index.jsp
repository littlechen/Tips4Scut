<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <title>Tips 4 SCUT —— 提供华工信息服务</title>
    <link type="text/css" rel="stylesheet" href="${resroot}/css/base.css" />
    <link type="text/css" rel="stylesheet" href="${resroot}/css/signreg.css" />
    <link type="text/css" rel="stylesheet" href="${resroot}/css/card.css" />
    <link type="text/css" rel="stylesheet" href="${resroot}/css/lib/jquery.mmenu.css" />
    <link type="text/css" rel="stylesheet" href="${resroot}/css/lib/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="${resroot}/css/tips4scut.css" />

    <script type="text/javascript" src="${resroot}/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${resroot}/js/lib/jquery.mmenu.min.js"></script>
    <script type="text/javascript" src="${resroot}/js/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="${resroot}/js/lib/swfObject.js"></script>
</head>
<body>
    <div id="page">
        <div id="header">
            <a href="#menu"></a><p>Tips 4 SCUT</p>
        </div>
        <div id="content">
            <div class="container">
                <%--左则菜单--%>
                <nav id="menu">
                    <ul>
                        <li><a class="btn-page" id="btn_index">首页</a></li>
                        <li><a class="btn-page" id="btn_eCard">校园一卡通消费</a></li>
                        <li><a class="btn-page" id="btn_schedule">我的课程表</a></li>
                        <li><a class="btn-page" id="btn_library">我的图书馆</a></li>
                        <li><a class="btn-page" id="btn_searchBook">检索书籍</a></li>
                        <li><a class="btn-page" id="btn_transcript">Jw 2005 - 查成绩</a></li>
                        <li><a class="btn-page" id="btn_noticeInfo">教务/生活后勤公告</a></li>
                        <li><a class="btn-page" id="btn_secondMarket">华工二手市场</a></li>
                        <li><a class="btn-page" id="btn_employNotice">就业实习</a></li>
                        <li><a class="btn-page" id="btn_academic">学术讲座</a></li>
                        <li><a class="btn-page" id="btn_excellentCourse">精品课程</a></li>
                        <li><a class="btn-page" id="btn_lostFound">失物招领</a></li>
                    </ul>
                </nav>
                <%--主页--%>
                <div class="page" id="page_index">
                    <div class="jumbotron">
                        <p>Tips4scut，是一个校园信息化应用，为你提供想要的信息。</p>
                        <p>我们渴望你提出宝贵的意见，E-mail: 1278467842@qq.com</p>
                        <p><a class="btn btn-danger btn-lg" role="button">点赞(400)</a></p>
                        <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#index_register">注册</button>
                        <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#index_login">登录</button>
                        <%--登录--%>
                        <div class="modal fade" id="index_login" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">请登录</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-sign-container">
                                            <form class="form-signin" role="form">
                                                <input id="login_input_username" type="text" class="form-control" placeholder="用户名/邮箱"
                                                       required autofocus> </input>
                                                <input id="login_input_password" type="password" class="form-control" placeholder="密码" required> </input>
                                                <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
                                                <button class="btn btn-lg btn-warning btn-block" data-dismiss="modal">关闭</button>
                                            </form>
                                        </div>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div><!-- /.modal -->
                        <%--Register--%>
                        <div class="modal fade" id="index_register" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">注册</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-sign-container">
                                            <form class="form-signin" role="form">
                                                <input id="register_email" type="email" class="form-control" placeholder="邮箱"  required="" />
                                                <input id="register_password" type="password" class="form-control"
                                                       placeholder="密码,最小长度6位" ng-minlength="6" required="" />
                                                <input id="register_rePassword" type="password" class="form-control"
                                                       placeholder="再输入一次" required="" />
                                                <input id="register_checkCode" type="password" class="form-control" placeholder="验证码" required="" />
                                                <img id="register_img_checkCode" src="/ajax/checkCode" alt="error" class="img-rounded" />
                                                <div class="alert alert-danger" hidden="hidden"> <span>Hello</span></div>
                                                <button class="btn btn-lg btn-success btn-block" type="submit">注册新用户</button>
                                                <button class="btn btn-lg btn-warning btn-block" data-dismiss="modal">关闭</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page" id="page_schedule" hidden="hidden">
                    <div class="panel panel-primary">
                        <div class="panel-heading"><h3 class="panel-title">我的课程表</h3></div>
                        <div class="panel-body">
                            <form class="form-inline" role="form">
                                <%--student ID--%>
                                <div class="form-group">
                                    <label class="sr-only" for="schedule_input_studentId"></label>
                                    <input id="schedule_input_studentId" type="email" class="form-control" placeholder="学号"> </input>
                                </div>
                                <%--student password--%>
                                <div class="form-group">
                                    <label class="sr-only" for="schedule_input_password"></label>
                                    <input id="schedule_input_password" type="password" class="form-control" placeholder="密码"> </input>
                                </div>
                                <%--checkcode--%>
                                <img src="http://222.201.132.113/(nk4i3a45odkwoemfhduszre3)/CheckCode.aspx" alt="error" class="img-rounded" />
                                <div class="form-group">
                                    <label class="sr-only" for="schedule_input_checkCode"></label>
                                    <input id="schedule_input_checkCode" type="password" class="form-control" placeholder="验证码" />
                                </div>
                                <%--confirm--%>
                                <button id="schedule_btn_confirm" type="button" class="btn btn-primary">查看课程表</button>
                            </form>
                            <p class="help-block"></p>
                            <div id="schedule_classInfo" class="row-fluid span10">
                                <ul class="pager">
                                    <ul id="schedule" class="nav nav-tabs">
                                        <li><a href="#schedule_mon" data-toggle="tab">Mon</a></li>
                                        <li><a href="#schedule_tue" data-toggle="tab">Tue</a></li>
                                        <li><a href="#schedule_wed" data-toggle="tab">Wed</a></li>
                                        <li><a href="#schedule_thu" data-toggle="tab">Thu</a></li>
                                        <li><a href="#schedule_fri" data-toggle="tab">Fri</a></li>
                                        <li><a href="#schedule_sat" data-toggle="tab">Sat</a></li>
                                        <li><a href="#schedule_sun" data-toggle="tab">Sun</a></li>
                                    </ul>
                                    <br/>
                                    <div id="schedule_TabContent" class="tab-content">
                                        <div class="tab-pane fade in active" id="schedule_mon">
                                            <table class="table table-striped table-hover">
                                                <tbody><tr class="warning"><td>第1,2节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第5,6节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第7,8节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第10,11,12节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                            </table>
                                        </div>
                                        <div class="tab-pane fade" id="schedule_tue">
                                            <table class="table table-striped">
                                                <tbody><tr class="warning"><td>第1,2节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第5,6节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第7,8节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第10,11,12节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                            </table>
                                        </div>
                                        <div class="tab-pane fade" id="schedule_wed">
                                            <p class="help-block">今天没有课程安排</p>
                                            <table class="table table-striped"></table>
                                        </div>
                                        <div class="tab-pane fade" id="schedule_thu">
                                            <table class="table table-striped">
                                                <tbody><tr class="warning"><td>第1,2节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第5,6节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第7,8节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                                <tbody><tr><td>第10,11,12节</td><td>运筹学</td><td>340301</td><td>林健良</td></tr></tbody>
                                            </table>
                                        </div>
                                        <div class="tab-pane fade" id="schedule_fri">
                                            <p class="help-block">今天没有课程安排</p>
                                        </div>
                                        <div class="tab-pane fade" id="schedule_sat">
                                            <p class="help-block">今天没有课程安排</p>
                                        </div>
                                        <div class="tab-pane fade" id="schedule_sun">
                                            <p class="help-block">今天没有课程安排</p>
                                        </div>
                                    </div>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <%--一卡通--%>
                <div class="page" id="page_eCard" hidden="hidden">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">查询消费/余额情况</h3>
                        </div>
                        <div class="panel-body">

                            <form class="form-inline" role="form">
                                <%--student ID--%>
                                <div class="form-group">
                                    <label class="sr-only" for="eCard_input_studentId"></label>
                                    <input id="eCard_input_studentId" type="email" class="form-control" placeholder="学号" />
                                </div>
                                <%--password--%>
                                <div class="form-group">
                                    <label class="sr-only" for="eCard_input_password"></label>
                                    <input  id="eCard_input_password" type="password" class="form-control" maxlength="6" placeholder="校园卡密码" />
                                </div>
                                <%--start date--%>
                                <div class="form-group">
                                    <label class="sr-only" for="eCard_input_start">开始日期</label>
                                    <input class="form-control" id="eCard_input_start" placeholder="开始日期(eg.20131001)" />
                                </div>
                                <%--end data--%>
                                <div class="form-group">
                                    <label class="sr-only" for="eCard_input_end">结束日期</label>
                                    <input class="form-control" id="eCard_input_end" placeholder="结束日期(eg.20131031)" />
                                </div>
                                <%--confirm--%>
                                <button type="button" class="btn btn-primary" id="eCard_btn_consumer">查询消费</button>
                                <button type="button" class="btn btn-primary" id="eCard_btn_money">查询余额</button>
                                <p class="help-block">查询余额,不需要输入日期信息</p>
                            </form>
                            <div class="row-fluid span10" id="eCard_consumer"></div>
                        </div>
                    </div>
                </div>
                <%--图书馆借阅信息--%>
                <div class="page" id="page_library" hidden="hidden">

                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">我的借阅</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline" role="form">
                                <div class="form-group">
                                    <label class="sr-only" for="library_input_studentId"></label>
                                    <input type="email" class="form-control" id="library_input_studentId" placeholder="借阅号" />
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="library_input_password"></label>
                                    <input type="password" class="form-control" id="library_input_password" placeholder="密码" />
                                </div>
                                <button type="button" class="btn btn-primary" id="library_btn_Info">借阅情况</button>
                            </form>
                            <p class="help-block"></p>
                            <div class="row-fluid span10" id="library_studentInfo">
                                <h4>
                                    <span id="library_studentName" class="label label-primary">姓名: 陈海棋</span>
                                </h4>
                                <h4>
                                    <span id="library_money" class="label label-danger">欠费情况: 0.0</span>
                                </h4>
                                <table class="table table-striped table-hover">
                                    <tbody>
                                    <tr><th>书名</th><th>所在馆</th><th>借阅时间</th><th>到期时间</th><th>剩下借阅次数</th><th>过期</th><th>续借</th></tr>
                                    </tbody>

                                    <tbody>
                                    <tr>
                                        <td>深入理解Linux虚拟内存管理/(爱尔兰) Mel Gorman著</td><td>自然科学图书馆（北区1号楼）</td>
                                        <td>2013-12-30</td><td>2013-10-15</td><td>1/2</td><td>否</td><td><a href="#">续借</a></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <%--查图书--%>
                <div id="page_searchBook" class="page" hidden="hidden">
                    <h3><span class="label label-info">查询图书</span></h3>

                    <form class="form-inline" role="form">
                        <div class="form-group">
                            <label class="sr-only" for="searchBook_inputKeyword"></label>
                            <input id="searchBook_inputKeyword" class="form-control"  placeholder="输入所查书名"> </input>
                        </div>
                        <button id="searchBook_btnSearch" type="button" class="btn btn-primary">查询</button>
                    </form>

                    <p class="help-block"></p>
                    <div id="searchBook_bookInfo" class="row-fluid span10">
                        <table class="table table-striped table-hover" id="searchBook_tb_booksInfo">
                            <tbody>
                            <tr><th>书名</th><th>作者</th><th>出版社</th><th>索书号</th><th>出版年份</th><th>中文图书</th></tr>
                            </tbody>
                        </table>
                        <button id="searchBook_searchMore" class="loadMore" type="button">加载更多</button>
                    </div>

                    <div id="searchBook_dialog_bookDetail"
                         class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="false">&times;</button>
                                    <div class="modal-books"></div>
                                </div>
                                <div class="modal-body"></div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--<div class="panel panel-primary">--%>
                        <%--<div class="panel-heading">--%>
                            <%--<h3 class="panel-title">查询图书</h3>--%>
                        <%--</div>--%>
                        <%--<div class="panel-body">--%>
                            <%----%>
                        <%--</div>--%>
                    <%--</div>--%>
                </div>
                <%--jw2005查成绩--%>
                <div class="page" id="page_transcript" hidden="hidden">

                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">我的成绩表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline" role="form">
                                <div class="form-group">
                                    <label class="sr-only" for="transcript_input_studentId"></label>
                                    <input type="email" class="form-control" id="transcript_input_studentId" placeholder="学号" />
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="transcript_input_password"></label>
                                    <input type="password" class="form-control" id="transcript_input_password" placeholder="密码" />
                                </div>
                                <img src="http://222.201.132.113/(nk4i3a45odkwoemfhduszre3)/CheckCode.aspx" alt="XXX" class="img-rounded" />
                                <div class="form-group">
                                    <label class="sr-only" for="transcript_input_checkCode"></label>
                                    <input type="password" class="form-control" id="transcript_input_checkCode" placeholder="验证码" />
                                </div>
                                <button type="button" class="btn btn-primary" id="transcript_btn_byThisTerm">本学期</button>
                                <button type="button" class="btn btn-primary" id="transcript_btn_byThisYear">本学年</button>
                                <button type="button" class="btn btn-primary" id="transcript_btn_byAll">历年成绩</button>
                                <p class="help-block">按学年来查成绩</p>
                                <button type="button" class="btn btn-primary" id="transcript_btn_byYear1">大一</button>
                                <button type="button" class="btn btn-danger" id="transcript_btn_byYear2">大二</button>
                                <button type="button" class="btn btn-success" id="transcript_btn_byYear3">大三</button>
                                <button type="button" class="btn btn-warning" id="transcript_btn_byYear4">大四</button>
                            </form>
                            <p class="help-block"></p>
                            <div class="row-fluid span10" id="transcript_classInfo">
                                <div>
                                    <h4>
                                        <span id="transcript_studentName" class="label label-default">姓名: 陈海棋</span>
                                    </h4>
                                    <h4>
                                        <span id="transcript_student_id" class="label label-default">学号: 201030470102</span>
                                    </h4>
                                </div>
                                <table id="transcript_studentInfo" class="table table-striped table-hover">
                                    <tbody><tr class="success"><th>课程</th><th>分数</th><th>绩点</th><th>性质</th></tr></tbody>

                                    <tbody><tr class="warning"><td>概率论</td><td>96</td><td>4.0/4.0</td><td>必修课</td></tr></tbody>
                                    <tbody><tr class="warning"><td>概率论</td><td>96</td><td>4.0/4.0</td><td>必修课</td></tr></tbody>
                                    <tbody><tr class="warning"><td>概率论</td><td>96</td><td>4.0/4.0</td><td>必修课</td></tr></tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <%--通知新闻--%>
                <div class="page" id="page_noticeInfo" hidden="hidden">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">校园公告</h3>
                        </div>
                        <div class="panel-body">
                            <%--class="notice-box"--%>
                            <div>
                                <div id="noticeInfo_notices">
                                    <div class="notice-list">
                                        <div class="notice-avatar"><div class="notice-day">21</div><div class="notice-month">2013/11</div></div>
                                        <div class="notice-list-content">
                                            <p class="notice-title"><a href="#">关于华南理工大学与美国密苏里大学(哥伦比亚校区)“2+2”本科生联合培养项目的报名通知</a></p>
                                        </div>
                                    </div>

                                    <div class="notice-list">
                                        <div class="notice-avatar"><div class="notice-day">21</div><div class="notice-month">2013/11</div></div>
                                        <div class="notice-list-content">
                                            <p class="notice-title"><a href="#">关于华南理工大学与美国密苏里大学(哥伦比亚校区)“2+2”本科生联合培养项目的报名通知</a></p>
                                        </div>
                                    </div>
                                </div>
                                <button id="noticeInfo_btn_loadMore" class="btn loadMore">加载更多</button>
                            </div>
                            <div id="footer" class="mm-fixed-bottom">
                                <div>
                                    <div class="btn-group dropup">
                                        <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown">教务处通知</button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_academic_notice">教务通知</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_academic_new">教务新闻</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_academic_report">媒体报道</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_academic_college">学院通知</a></li>
                                        </ul>
                                    </div>
                                    <div class="btn-group dropup">
                                        <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">学习生活</button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_campusECard">校园一卡通公告</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_library">图书馆公告</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_campusNetwork">网络中心公告</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_youth">团委公告</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_logistics">后勤产业集团</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_hospital">校医院公告</a></li>
                                            <li><a class="noticeInfo_btn_info" id="noticeInfo_globalPartnerShips">中外合办办公室公告</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%--二手市场--%>
                <div class="page" id="page_secondMarket" hidden="hidden">

                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">二手市场</h3>
                        </div>
                        <div class="panel-body">
                            <h3>
                                <button id="secondMarket_electron" type="button" class="btn btn-primary secondMarket_btn">电子产品&nbsp;</button>
                                <button id="secondMarket_book" type="button" class="btn btn-info secondMarket_btn">二手书籍&nbsp;</button>
                                <button id="secondMarket_life" type="button" class="btn btn-warning secondMarket_btn">生活用品&nbsp;</button>
                                <button id="secondMarket_house" type="button" class="btn btn-success secondMarket_btn">出租房&nbsp;</button>
                            </h3>
                            <table id="secondMarket_tb_item" class="table table-striped table-hover">
                                <tbody><tr><td>转让全新二代kindle paperwhite</td><td>2013-12-17 20:20:37.0</td></tr></tbody>
                            </table>
                        </div>
                    </div>

                </div>
                <%--招聘信息--%>
                <div class="page" id="page_employNotice" hidden="hidden">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">招聘/实习</h3>
                        </div>
                        <div class="panel-body">
                            <%--<div id="employNotice_div_btn">--%>
                                <%--<h3>--%>
                                    <%--<button id="employNotice_btn_internship" type="button" class="btn btn-success employNotice_btn">实习信息&nbsp;</button>--%>
                                    <%--<button id="employNotice_btn_employ" type="button" class="btn btn-primary employNotice_btn">校园招聘&nbsp; </button>--%>
                                    <%--<br/>--%>
                                    <%--<button id="employNotice_btn_specialRecent" type="button" class="btn btn-warning employNotice_btn">最近招聘专场&nbsp;</button>--%>
                                    <%--<br/>--%>
                                    <%--<button id="employNotice_btn_specialExtra" type="button" class="btn btn-primary employNotice_btn">外校招聘专场&nbsp;</button>--%>
                                    <%--<br/>--%>
                                    <%--<button id="employNotice_btn_specialAll" type="button" class="btn btn-success employNotice_btn">所有招聘专场&nbsp;</button>--%>
                                <%--</h3>--%>
                            <%--</div>--%>
                            <%--<br/>--%>
                            <%--class="notice-box"--%>
                            <div>
                                <div id="employNotice_notices">
                                    <div class="notice-list">
                                        <div class="notice-avatar"><div class="notice-day">21</div><div class="notice-month">2013/11</div></div>
                                        <div class="notice-list-content">
                                            <p class="notice-title"><a href="#">广东省2014届高校毕业生供需见面会活动“外资、三资、国内知名企业专场招聘会</a></p>
                                        </div>
                                    </div>

                                    <div class="notice-list">
                                        <div class="notice-avatar"><div class="notice-day">21</div><div class="notice-month">2013/11</div></div>
                                        <div class="notice-list-content">
                                            <p class="notice-title"><a href="#">广东省2014届高校毕业生供需见面会活动“外资、三资、国内知名企业专场招聘会</a></p>
                                        </div>
                                    </div>
                                </div>
                                <button id="employNotice_btn_loadMore" class="btn loadMore">加载更多</button>
                            </div>
                            <div id="footer" class="mm-fixed-bottom">
                                <div>
                                    <div class="btn-group dropup">
                                        <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown">实习/招聘</button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a class="noticeInfo_btn_info employNotice_btn" id="employNotice_btn_internship">实习信息</a></li>
                                            <li><a class="noticeInfo_btn_info employNotice_btn" id="employNotice_btn_employ">校园招聘</a></li>
                                            <li><a class="noticeInfo_btn_info employNotice_btn" id="employNotice_btn_specialRecent">最近招聘专场</a></li>
                                            <li><a class="noticeInfo_btn_info employNotice_btn" id="employNotice_btn_specialExtra">外校招聘专场</a></li>
                                            <li><a class="noticeInfo_btn_info employNotice_btn" id="employNotice_btn_specialAll">所有招聘专场</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <%--学术讲座--%>
                <div class="page" id="page_academic" hidden="hidden">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">学术讲座</h3>
                        </div>
                        <div class="panel-body">
                            <%--<h3><span class="label label-info">学术讲座</span></h3>--%>
                            <table id="academic_tb_info" class="table table-striped table-hover">
                                <tbody><tr><td><a href="#">关于举行美国Arizona州立大学Jerry Lin教授学术报告的通知</a></td></tr></tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <%--精品课程--%>
                <div class="page" id="page_excellentCourse" hidden="hidden">
                    <div id="excellentCourse_swfPlayerDiv">
                        <script type="text/javascript">
                            var width = $(document).width();
                            var height = $(document).height();
                            if(width > height){
                                width = width / 2;
                                height = width * 9 / 16;
                            }else{
                                width = width / 8 * 7;
                                height = width;
                            }
                            var swfPlayer = {
                                play : function(courseUuid, swfWidth, swfHeight) {
                                    var swf = "http://www.icourses.cn:80/common/player/MPlayer.swf",
                                            ppiSWF = "http://www.icourses.cn:80/common/player/playerProductInstall.swf",
                                            swfContent = "MPlayerBox",
                                            params = {
                                                wmode: "Opaque",
                                                allowscriptaccess: "always",
                                                allowfullscreen: "true",
                                                bgcolor: "#222222"
                                            },
                                            attributes = {
                                                id: "MPlayer",
                                                name: "MPlayer"
                                            },
                                            flash = {
                                                IService: 'http://www.icourses.cn/getVideoResPlayInfo.action',
                                                uuid: courseUuid,
                                                other: ""
                                            };
                                    swfobject.embedSWF(swf, swfContent, swfWidth, swfHeight, "11.0.0", ppiSWF, flash, params, attributes);
                                }
                            };
                            swfPlayer.play("142af015-1385-1000-b7a6-9bd9a94f2948", width, height);
                        </script>
                        <div id="PlayerBox"><p id="MPlayerBox"></p></div>
                    </div>


                    <div id="excellentCourse_search">
                        <input id="excellentCourse_keyword" type="text" class="form-control" placeholder="关键字" required="" />
                        <input id="excellentCourse_teacher" type="text" class="form-control" placeholder="主讲" required="" />
                        <input id="excellentCourse_university" type="text" class="form-control" placeholder="学校" required="" />
                        <button  id = "excellentCourse_btn_search" class="btn btn-lg btn-success btn-block" type="button">搜索课程</button>

                        <div id="excellentCourse_courseItems" class="row-fluid span10">
                            <table class="table table-striped table-hover" id="excellentCourse_tb_courseInfo">
                                <tbody>
                                <tr><th>课程名</th><th>主讲</th><th>学校</th><th>播放次数</th><th>介绍</th></tr>
                                </tbody>
                            </table>
                            <button id="excellentCourse_searchMore" class="loadMore" type="button">加载更多</button>
                        </div>

                        <div id="excellentCourse_dialog_courseDetail"
                             class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="false">&times;</button>
                                        <div class="modal-books"></div>
                                    </div>
                                    <div class="modal-body">
                                        <p id="details">本课程介绍：xxx</p>
                                        <ul>
                                            <li></li>
                                            <li></li>
                                            <li></li>
                                            <li></li>
                                        </ul>
                                    </div>
                                    <div class="modal-footer">

                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <%--失物招领--%>
                <div class="page" id="page_lostFound" hidden="hidden">
                    <button id="lostFound_btn_lost" class="btn btn-primary" type="button">发布信息</button>
                    <button id="lostFound_btn_lost" class="btn btn-primary" type="button">寻物信息</button>
                    <button id="lostFound_btn_found" class="btn btn-primary" type="button">招领信息</button>
                    <%--<label class="label-danger">所有招领信息:</label>--%>

                    <div class="btn-group">
                        <button type="button" class="btn btn-warning btn-default dropdown-toggle" data-toggle="dropdown">
                            选择类型<span class="caret"></span>
                        </button>
                        <ul id="lostFound_btn_type" class="dropdown-menu" role="menu">
                            <li><a href="#">衣物饰品</a></li>
                            <li><a href="#">交通工具</a></li>
                            <li><a href="#">随身物品</a></li>
                            <li><a href="#">电子数码</a></li>
                            <li><a href="#">卡类证件</a></li>
                            <li><a href="#">其他物品</a></li>
                        </ul>
                    </div>
                    <br/>

                    <table id="lostFound_itemInfo" class="table table-striped table-hover">
                        <tbody><tr class="success"><th>类型</th><th>物品名称</th><th>地点</th><th>发布时间</th><th>详情描述</th><th></th></tr></tbody>
                        <tbody><tr class="warning"><td>卡类证件</td><td>饭卡</td><td>东九路上</td><td>2014/3/10</td><td>一张饭卡，上面只写了卡号=.=</td>
                            <td><a href="#">【联系方式】</a></td></tr></tbody>
                    </table>
                    <button id="lostFound_loadMore" class="loadMore" type="button">加载更多</button>

                    <div id="lostFound_dialog_itemInfo"
                         class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="false">&times;</button>
                                    <h5 class="modal-title">联系方式</h5>
                                    <%--<div class="modal-books"></div>--%>
                                </div>
                                <div class="modal-body">
                                    <span id="lostFound_contract"></span>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="lostFound_dialog_send"
                         class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="false">&times;</button>
                                    <h5 class="modal-title">发布失物招领</h5>
                                    <%--<div class="modal-books"></div>--%>
                                </div>
                                <div class="modal-body">
                                    <input type="radio" name="lostFound_input_state" value="lost"checked="true">寻物</input>
                                    <input type="radio" name="lostFound_input_state" value="found"/>招领</input>

                                    <select id="lostFound_itemType">
                                        <option value="选择类型">选择类型</option>
                                        <option value="书籍资料">书籍资料</option>
                                        <option value="衣物饰品">衣物饰品</option>
                                        <option value="交通工具" >交通工具</option>
                                        <option value="随身物品">随身物品</option>
                                        <option value="电子数码">电子数码</option>
                                        <option value="卡类证件">卡类证件</option>
                                        <option value="其他物品">其他物品</option>
                                     </select>

                                    <input id="lostFound_input_itemName" type="text" class="form-control" placeholder="物品名称" autofocus>
                                    </input>
                                    <input id="lostFound_input_itemPlace" type="text" class="form-control" placeholder="地点" autofocus>
                                    </input>
                                    <input id="lostFound_input_itemDetail" type="text" class="form-control" placeholder="详情描述" autofocus>
                                    </input>
                                    <button class="btn btn-lg btn-primary btn-block" type="button">发布</button>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${resroot}/js/tips2Scut.js"></script>
    <script type="text/javascript" src="${resroot}/js/searchBooks.js"></script>
</body>
</html>