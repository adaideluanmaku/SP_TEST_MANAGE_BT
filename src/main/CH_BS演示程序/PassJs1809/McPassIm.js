/*
* McPassIm.js
* Copyright 1997-2018 Medicom Inc. All rights reserved.
* Product: PASS IM
* Version: 1.0.2
* Date: 2017-09-13
*/
(function (window) {
    var _thePassim = window.$passim || new _passim();
    window.$passim = _thePassim;
    function _passim() {
        this.init = init;
        this.notice = notice;
        this.resetNotice = resetNotice;
        this.isInit = false;
        var mMsgCount = 0;
        var mUnreadCount = 0;
        var callBackCount = 0;

        var mUserMsgCount = { };

        this.proxy_callback = function (para) {
            function _callBack() {
                if (document.getElementById('mc_passim_proxy_iframe').contentWindow) {
                    document.getElementById('mc_passim_proxy_iframe').contentWindow.name = 'mc_passim_proxy_iframe';
                    document.getElementById('mc_passim_proxy_iframe').contentWindow.location.href = combineUrl(params.url, 'user/proxy?data=' + encodeURIComponent('url:' + params.proxyUrl));
                } else {
                    document.getElementById('mc_passim_proxy_iframe').name = 'mc_passim_proxy_iframe';
                    document.getElementById('mc_passim_proxy_iframe').location.href = combineUrl(params.url, 'user/proxy?data=' + encodeURIComponent('url:' + params.proxyUrl));
                }

                var currentMsgCount = parseInt(para);
                if (!isNaN(currentMsgCount)) {
                    var offsetCount = currentMsgCount - mMsgCount;
                    mMsgCount = currentMsgCount;
                    mUnreadCount += offsetCount;
                    if (currentStatus == statusOptions.close) {
                        notice(mUnreadCount);
                    }

                    var _arr = para.split('^');
                    if (_arr.length == 3) {
                        var _userId = _arr[1];
                        var _nickName = decodeURIComponent(_arr[2]);

                        if (mUserMsgCount[_userId] && mUserMsgCount[_userId].count) {
                            mUserMsgCount[_userId].count += 1;
                        } else {
                            mUserMsgCount[_userId] = {
                                userId: _userId,
                                nickName: _nickName,
                                count: 1
                            };
                        }
                    }
                    showNotice();
                }
            }


            if (callBackCount == 0) {
                setTimeout(_callBack, 1000);
            } else {
                _callBack();
            }

            callBackCount++;
        }

        this.talk = function (userName, message, silent) {
            if (!silent && currentStatus == statusOptions.close) {
                open();
            }
            if (!!!message) message = '';

            if (userName instanceof Array) {
                userName = userName.join('{_}');
            }

            cmd(userName.toLowerCase() + '{passim_fg}' + message + '{passim_fg}' + (silent ? 'true' : 'false'));
        }

        var noticeTimer = null;
        //显示消息提示
        function showNotice() {
            var noticeContainerId = 'passim_notice';
            var headImg = combineUrl(params.url, '/Content/passim/images/h1.gif');
            var noteImg0 = combineUrl(params.url, '/Content/passim/images/not0.gif');
            var noteImg1 = combineUrl(params.url, '/Content/passim/images/not1.gif');
            var noteImg2 = combineUrl(params.url, '/Content/passim/images/not2.gif');
            var itemHeight = 30;

            var noticeWidth = 150;
            var arr = [];
            arr.push('<div id="' + noticeContainerId + '" style="position:absolute;z-index:1000001;display:none;background-color:#fff;border:1px solid #ccc;width:' + noticeWidth + 'px;">');
            arr.push('</div>');

            var container = document.getElementById(noticeContainerId);
            if (!container) {
                getLastChildElement(document.body).insertAdjacentHTML('afterEnd', arr.join(''));
                container = document.getElementById(noticeContainerId);
            }

            arr.splice(0, arr.length);

            var msgCount = 0;
            for (var o in mUserMsgCount) {
                msgCount++;
                var showName = mUserMsgCount[o].nickName;
                var count = parseInt(mUserMsgCount[o].count);

                params.url
                arr.push('<div onclick="window.$passim.talk(\'' + mUserMsgCount[o].userId + '\',\'\',false);" style="cursor:pointer;height:' + itemHeight + 'px">');


                arr.push('<span style="display:block;float:left;margin-left:5px;background-image:url(' + headImg + ');background-repeat:no-repeat;background-position:left center;padding-left:30px;line-height:' + itemHeight + 'px;" >' + showName + '</span>');

                var _img = noteImg0;
                if (count > 9) {
                    _img = noteImg1;
                }
                if (count > 99) {
                    _img = noteImg2;
                }

                arr.push('<span style="display:block;float:right;margin-right:5px;text-align:center;color:#fff;font-size:10px;font-family:Verdana;width:30px;height:30px;background-image:url(' + _img + ');background-repeat:no-repeat;background-position:center;line-height:' + itemHeight + 'px;" >' + (count > 99 ? '99+' : count) + '</span>');
                arr.push('<span style="display:block;clear:both;" ></span>');
                arr.push('<div>');
            }

            container.innerHTML = arr.join('');

            var pos = getPos4()

            var noticeHeight = msgCount * itemHeight;

            var _pos = {
                left: pos.left - noticeWidth - 5,
                top: pos.top - noticeHeight - 35
            }
            container.style.left = _pos.left + 'px';
            container.style.top = _pos.top + 'px';
            container.style.display = 'block';


            if (noticeTimer != null) {
                clearTimeout(noticeTimer);
            }

            noticeTimer = setTimeout(function () {
                container.style.display = 'none';
            }, 10000);
        }


        //清空消息
        function clearUserMsgCount() {
            for (var i in mUserMsgCount) {
                delete mUserMsgCount[i];
            }
        }
        function cmd(data) {
            var iframe = document.createElement('iframe');
            var url = combineUrl(params.url, 'user/proxy?data=' + encodeURIComponent(data));
            iframe.src = url;
            //iframe.id = "mc_passim_iframe";
            document.body.appendChild(iframe);
            if (iframe.attachEvent) {
                iframe.attachEvent('onload', function () {
                    setTimeout(function () {
                        document.body.removeChild(iframe);
                    }, 100);
                });
            } else {
                iframe.onload = function () {
                    setTimeout(function () {
                        document.body.removeChild(iframe);
                    }, 100);
                };
            }
        }

        function combineUrl(url, attach) {
            var ret = url;

            if (!!!attach) attach = '';
            if (attach.indexOf('?') != -1) {
                attach += '&js_proxy_username=' + encodeURIComponent(params.userName);
            } else {
                attach += '?js_proxy_username=' + encodeURIComponent(params.userName);
            }

            if (url.lastIndexOf('/') == url.length - 1) {
                ret += attach;
            }
            else {
                ret += '/' + attach;
            }
            return ret;
        }

        var document = window.document;
        
        String.prototype.trim = function () {
            return this.replace(/(^\s*)|(\s*$)/g, "");
        }

        /***
        window status
        ***/
        var statusOptions = {
            close: 0,
            open: 1
        }
        /***
        window postion
        ***/
        var positionOptions = {
            leftTop: 0,
            rightTop: 1,
            leftBottom: 2,
            rightBottom: 3,
            center: 4
        }       
        var currentStatus = statusOptions.close;       
        var inEvent = {
            onClose: null,
            onOpen: null
        };

        /***
        params.url
        params.position
        params.userName
        **/
        var params = {
            url: mc_passim_url,
            position: positionOptions.center,
            userName: '',
            width: 800,
            height: 500,
            smallWidth: 150,
            smallheight: 30,
            tipWidth: 70,
            controlWidth: 20,
            prefixId: 'passim_',
            tipTitle: 'PASSIM',
            titleBackColor: '#4570E1',
            borderColor: '#ddd',
            bottomOffset: 0,
            proxyUrl: "about:blank",
            margin: 10
        }     
        var pos = {
            smallX: 0,
            smallY: 0,
            X: 0,
            Y: 0
        }      
        var currentMaxPos = {
            X: 0,
            Y: 0
        }      
        var currentMinPos = {
            X: 0,
            Y: 0
        }      
        params.getIframeUrl = function () {
            var iframeWidth = params.width;
            var iframeHeight = params.height - 30;
            return params.url + '?proxy_username=' + params.userName + '&w=' + iframeWidth + '&h=' + iframeHeight;;
        }
      
        params.getElementId = function (id) {
            return params.prefixId + id;
        }
      
        params.getIframeId = function () {
            return params.getElementId('iframe');
        }      
        params.getMainId = function () {
            return params.getElementId('main');
        }       
        params.getTipContainerId = function () {
            return params.getElementId('tipContainer');
        }       
        params.getTipId = function () {
            return params.getElementId('tip');
        }      
        params.getControlId = function () {
            return params.getElementId('control');
        }      
        params.getNoticeScriptId = function () {
            return params.getElementId('script');
        }     
        params.getMaskId = function () {
            return params.getElementId('mask');
        }       
        params.getCurrentWidth = function () {
            var w = params.width;
            if (currentStatus == statusOptions.close) {
                w = params.smallWidth;
            }
            return w;
        }
        params.getCurrentHeight = function () {
            var h = params.height;
            if (currentStatus == statusOptions.close) {
                h = params.smallheight;
            }
            return h;
        }       
        params.TipNeedShow = function () {
            return true;
        }      
        params.IframeNeedShow = function () {
            if (currentStatus == statusOptions.open) {
                return true;
            }
            return false;
        }
        /***       
        browser.isie 
        browser.iswebkit 
        browser.isopera 
        browser.ismozilla 
        ***/
        var browser = {
            regexs: {
                rwebkit: /(webkit)[ \/]([\w.]+)/i,
                ropera: /(opera)(?:.*version)?[ \/]([\w.]+)/i,
                rmsie: /(msie) ([\w.]+)/i,
                rmozilla: /(mozilla)(?:.*? rv:([\w.]+))?/i
            },
            userAgent: navigator.userAgent
        };

        browser.isie = browser.regexs.rmsie.test(browser.userAgent);
        browser.iswebkit = browser.regexs.rwebkit.test(browser.userAgent);
        browser.isopera = browser.regexs.ropera.test(browser.userAgent);
        browser.ismozilla = browser.regexs.rmozilla.test(browser.userAgent);
     
        function createHtml() {
            var arr = [];
            var tipShowStyle = params.TipNeedShow() ? '' : "display:none;";
            var iframeShowStyle = params.IframeNeedShow() ? '' : "display:none;";

            var iframeWidth = params.width;
            var iframeHeight = params.height - 30;

            arr.push('<div id="' + params.getMainId() + '" style="overflow:hidden;position:absolute;z-index:1000000;background-color:' + params.titleBackColor + ';color:#fff;width:' + params.getCurrentWidth() + 'px;height:' + params.getCurrentHeight() + 'px">');
            arr.push('<div id="' + params.getTipContainerId() + '" style="' + tipShowStyle + '-moz-user-select:none;-webkit-user-select:none; user-select:none;cursor:move;">');
            arr.push('<div id="' + params.getTipId() + '" style="float:left;line-height:30px;padding-left:5px;font-family:宋体;font-size:12px;color:white;">' + params.tipTitle + '</div>'); //－□
            arr.push('<div id="' + params.getControlId() + '" title="打开聊天窗口" style="float:right;margin-right:8px;line-height:30px;text-align:center;width:' + params.controlWidth + 'px;cursor:pointer;font-family:宋体;font-size:12px;color:white;">□</div>');
            arr.push('<div style="clear:both;"></div>');
            arr.push('</div>');
            arr.push('<div style="position:relative;">');
            arr.push('<div id="' + params.getMaskId() + '" style="z-index:1000001;position:absolute;left:0px;top:0px;width:' + params.width + 'px;height:' + (params.height - 30) + 'px;display:none;"></div><iframe id="' + params.getIframeId() + '" name="' + params.getIframeId() + '" framespacing="0" frameborder="0" src="' + params.getIframeUrl() + '" style="' + iframeShowStyle + 'position:absolute;left:0px;top:0px;width:' + iframeWidth + 'px;height:' + iframeHeight + 'px;border:0;margin:0;padding:0;overflow:hidden;"></iframe></div>');
            arr.push('</div>');
            arr.push('</div>');
            arr.push('<iframe style="display:none;" name="mc_passim_proxy_iframe" id="mc_passim_proxy_iframe" src="' + params.proxyUrl + '"></iframe>');
            return arr.join('');
        }

        function setTipTitle(title) {
            var obj = document.getElementById(params.getTipId());
            if (obj) {
                obj.innerHTML = title;
            }
        }
        function getImWindow() {
            return document.getElementById(params.getIframeId()).contentWindow;
        }


        var elemCaches = {};

        function pushCache(id, elem) {
            elemCaches[id] = elem;
        }

        function getElem(id) {
            return elemCaches[id];
        }

        function getBodyWidth() {
            return document.body.clientWidth;
        }
        function getBodyHeight() {
            var ret = document.documentElement.clientHeight || document.body.clientHeight;
            if (ret == document.documentElement.scrollHeight &&
                (document.body.clientHeight != document.body.scrollHeight)) {
                ret = document.body.clientHeight;
            }
            return ret;
        }
        function getBodyScrollTop() {
            return document.documentElement.scrollTop || document.body.scrollTop;
        }


        //左上角坐标
        function getPos1() {
            var x = 0 + params.margin;
            var y = getBodyScrollTop() + params.margin;
            return { left: x, top: y };
        }
        //右上角坐标
        function getPos2() {
            var x = getBodyWidth() - params.margin;
            var y = getBodyScrollTop() + params.margin;
            return { left: x, top: y }
        }
        //左下角坐标
        function getPos3() {
            var x = 0 + params.margin;
            var y = getBodyHeight() + getBodyScrollTop() - params.margin;
            return { left: x, top: y }
        }
        //右下角坐标
        function getPos4() {
            var x = getBodyWidth() - params.margin;
            var y = getBodyHeight() + getBodyScrollTop() + params.bottomOffset - params.margin;
            return { left: x, top: y }
        }
        //取得中心坐标
        function getPos5() {
            var x = Math.ceil(getBodyWidth() / 2);
            var y = Math.ceil(getBodyHeight() / 2) + getBodyScrollTop();
            return { left: x, top: y }
        }
        //根据定位类型取得坐标
        function getPos(position) {
            if (position == positionOptions.leftTop) {
                return getPos1();
            }

            if (position == positionOptions.rightTop) {
                return getPos2();
            }

            if (position == positionOptions.leftBottom) {
                return getPos3();
            }

            if (position == positionOptions.rightBottom) {
                return getPos4();
            }

            return getPos5();
        }

        //上次的ScrollTop
        var lastScrollTop = 0;
        //最大化下是否已经移动过
        var isMaxMoved = false;
        //最小化下是否已经移动过
        var isMinMoved = false;
        //最大化相对坐标
        var relativeMaxPos = { left: 0, top: 0 };
        //最小化相对坐标
        var relativeMinPos = { left: 0, top: 0 };
        //固定位置
        function fixPos() {
            var x = 0; y = 0;
            var w = params.getCurrentWidth();
            var h = params.getCurrentHeight();
            var hasScrollTop = arguments.length > 0;
            var scrollTop = hasScrollTop ? arguments[0] : getBodyScrollTop();

            if (currentStatus == statusOptions.close) {
                pos = getPos(params.position);
                if (hasScrollTop) {
                    pos.top = pos.top - getBodyScrollTop() + scrollTop;
                }

                if (isMinMoved) {

                    pos.top = relativeMinPos.top + scrollTop;
                    pos.left = relativeMinPos.left;

                    x = pos.left
                    y = pos.top;

                } else if (params.position == positionOptions.leftTop) {
                    x = pos.left;
                    y = pos.top;
                } else if (params.position == positionOptions.rightTop) {
                    x = pos.left - w;
                    y = pos.top;
                } else if (params.position == positionOptions.leftBottom) {
                    x = pos.left;
                    y = pos.top - h;
                } else if (params.position == positionOptions.rightBottom) {
                    x = pos.left - w;
                    y = pos.top - h;
                } else if (params.position == positionOptions.center) {
                    x = pos.left - Math.ceil(w / 2);
                    y = pos.top - Math.ceil(h / 2);
                }

                if (isNaN(x) || isNaN(y)) return;

                var cPos = correctPos(x, y);
                x = cPos.x;
                y = cPos.y;

                currentMinPos.X = x;
                currentMinPos.Y = y;

            } else if (currentStatus == statusOptions.open) {
                var pos = getPos(positionOptions.center);
                if (hasScrollTop) {
                    pos.top = pos.top - getBodyScrollTop() + scrollTop;
                }
                if (isMaxMoved) {
                    pos.top = relativeMaxPos.top + scrollTop;
                    pos.left = relativeMaxPos.left;
                    x = pos.left
                    y = pos.top;
                }
                else {
                    x = pos.left - Math.ceil(w / 2);
                    y = pos.top - Math.ceil(h / 2);
                }

                if (isNaN(x) || isNaN(y)) return;

                var cPos = correctPos(x, y);
                x = cPos.x;
                y = cPos.y;

                currentMaxPos.X = x;
                currentMaxPos.Y = y;
            }


            var elem = getElem(params.getMainId());
            elem.style.left = x + 'px';
            elem.style.top = y + 'px';
        }
        /***
        初始化
        ***/
        function init(username, url, startPos, width, height, bottomOffset, proxyUrl, margin) {
            if (_thePassim.isInit) return;
            _thePassim.isInit = true;

            if (!!width && width < 600) width = 600;
            if (!!height && height < 450) height = 450;

            if (username instanceof Array) {
                username = username.join('{_}');
            }

            if (!!bottomOffset) {
                params.bottomOffset = bottomOffset;
            }
            if (!!proxyUrl) {
                proxyUrl = proxyUrl.toLowerCase();
                if (proxyUrl.indexOf('http://') == -1) {
                    proxyUrl = 'http://' + window.location.host + proxyUrl;
                }
            }

            /*****动画帧支持*******/
            (function () {
                var lastTime = 0;
                var vendors = ['webkit', 'moz'];
                for (var x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
                    window.requestAnimationFrame = window[vendors[x] + 'RequestAnimationFrame'];
                    window.cancelAnimationFrame = window[vendors[x] + 'CancelAnimationFrame'] ||    // name has changed in Webkit
                                      window[vendors[x] + 'CancelRequestAnimationFrame'];
                }

                if (!window.requestAnimationFrame) {
                    window.requestAnimationFrame = function (callback, element) {
                        var currTime = new Date().getTime();
                        var timeToCall = Math.max(0, 16.7 - (currTime - lastTime));
                        var id = window.setTimeout(function () {
                            callback(currTime + timeToCall);
                        }, timeToCall);
                        lastTime = currTime + timeToCall;
                        return id;
                    };
                }
                if (!window.cancelAnimationFrame) {
                    window.cancelAnimationFrame = function (id) {
                        clearTimeout(id);
                    };
                }
            }());
            /*****动画帧支持end*******/

            addEvent(window, 'load', _load);

            //窗口加载检查，3秒一次，未加则加载，加载完后清除检查
            var _isLoad = false;
            var _checkTimer = setInterval(function () {
                if (_isLoad) {
                    clearInterval(_checkTimer);
                    return;
                }
                _load();
            }, 3000);

            //窗口加载
            function _load() {

                if (_isLoad) return;//确保不重复加载

                params.userName = encodeURIComponent(decodeURIComponent(username)).toLowerCase();
                params.position = !!startPos ? startPos : params.position;
                params.width = !!width ? width : params.width;
                params.height = !!height ? height : params.height;
                params.url = !!url ? url : params.url;
                params.proxyUrl = !!proxyUrl ? proxyUrl : params.proxyUrl;

                if (!!margin) params.margin == margin;


                var html = createHtml();

                var elem = getLastChildElement(document.body);
                elem.insertAdjacentHTML("afterEnd", html);

                pushCache(params.getControlId(), document.getElementById(params.getControlId()));
                pushCache(params.getIframeId(), document.getElementById(params.getIframeId()));
                pushCache(params.getMainId(), document.getElementById(params.getMainId()));
                pushCache(params.getTipContainerId(), document.getElementById(params.getTipContainerId()));
                pushCache(params.getTipId(), document.getElementById(params.getTipId()));
                pushCache(params.getMaskId(), document.getElementById(params.getMaskId()));

                addEvent(getElem(params.getControlId()), 'click', function () {
                    toggleOpen();
                });

                fixPos();

                initNotice();

                initDragEvent();

                var timer = null;

                addEvent(window, 'scroll', function () {
                    startScroll();
                });
                addEvent(window, 'resize', function () {
                    fixPos();
                });

                var lastScrollTop = getBodyScrollTop();
                var handle = null;

                function startScroll() {

                    if (timer != null) {
                        return;
                    }

                    function clearHandle() {
                        if (handle != null) {
                            window.cancelAnimationFrame(handle);
                            handle = null;
                        }
                    }
                    function clearTimer() {
                        if (timer != null) {
                            clearInterval(timer);
                            timer = null;
                        }
                    }

                    clearHandle();

                    var start = getBodyScrollTop();

                    timer = setInterval(function () {

                        var nowScrollTop = getBodyScrollTop();

                        function myscroll() {
                            if (Math.round(start) == Math.round(nowScrollTop)) return;

                            var offset = Math.abs((nowScrollTop - start) / 16.7);

                            if (start < nowScrollTop) {
                                start += offset;
                            } else {
                                start -= offset;
                            }
                            fixPos(start);
                            handle = window.requestAnimationFrame(myscroll);
                        }

                        if (nowScrollTop == lastScrollTop) {
                            clearTimer();
                            myscroll();
                            return;
                        }

                        lastScrollTop = nowScrollTop;

                    }, 16.7 * 5)
                }
                _isLoad = true;
            }
        }

        //窗口移动事件
        function initDragEvent() {
            var isMoving = false;

            var mX = 0;
            var mY = 0;

            var ctrlObj = getElem(params.getTipContainerId());
            addEvent(getElem(params.getTipContainerId()), 'mousedown', function (e) {
                isMoving = true;

                var e = e || event;
                mX = e.clientX;
                mY = e.clientY;

                if (browser.isie) {
                    getElem(params.getTipContainerId()).onselectstart = function () {
                        return false;
                    }
                }

                if (ctrlObj.setCapture) {
                    ctrlObj.setCapture();
                } else if (currentStatus == statusOptions.open) {
                    getElem(params.getMaskId()).style.display = 'block';
                }

                setNoSelect();
            });


            addEvent(document, 'mousemove', function (e) {
                if (!isMoving) {
                    return;
                }
                var e = e || event;
                var x = e.clientX;
                var y = e.clientY;

                var offsetX = x - mX;
                var offsetY = y - mY;

                mX = x;
                mY = y;

                var dx = 0;
                var dy = 0;

                if (currentStatus == statusOptions.open) {

                    isMaxMoved = true;

                    dx = currentMaxPos.X + offsetX;
                    dy = currentMaxPos.Y + offsetY;

                    pos.X = dx;
                    pos.Y = dy;

                    if (isNaN(dx) || isNaN(dy)) return;

                    currentMaxPos.X = dx;
                    currentMaxPos.Y = dy;

                } else {

                    isMinMoved = true;

                    dx = currentMinPos.X + offsetX;
                    dy = currentMinPos.Y + offsetY;

                    pos.smallX = dx;
                    pos.smallY = dy;

                    if (isNaN(dx) || isNaN(dy)) return;

                    currentMinPos.X = dx;
                    currentMinPos.Y = dy;
                }


                var cPos = correctPos(dx, dy);
                dx = cPos.x;
                dy = cPos.y;

                var elem = getElem(params.getMainId());
                elem.style.left = dx + 'px';
                elem.style.top = dy + 'px';
            }, false);


            function release(e) {
                isMoving = false;
                if (ctrlObj.releaseCapture) {
                    ctrlObj.releaseCapture();
                } else if (currentStatus == statusOptions.open) {
                    getElem(params.getMaskId()).style.display = 'none';
                }
                if (currentStatus == statusOptions.open) {
                    relativeMaxPos.left = currentMaxPos.X;
                    relativeMaxPos.top = currentMaxPos.Y - getBodyScrollTop();
                } else {
                    relativeMinPos.left = currentMinPos.X;
                    relativeMinPos.top = currentMinPos.Y - getBodyScrollTop();
                }
                setCanSelect();
            }
            addEvent(document, 'mouseup', release);
        }
        function correctPos(dx, dy) {
            if (params.getCurrentWidth() + dx > getBodyWidth()) {
                dx = getBodyWidth() - params.getCurrentWidth() - 2;
            }

            if (params.getCurrentHeight() + dy > getBodyHeight() + getBodyScrollTop() - 2) {
                dy = getBodyHeight() + getBodyScrollTop() - params.getCurrentHeight() - 2;
            }
            if (dy < getBodyScrollTop() + 2) {
                dy = getBodyScrollTop() + 2;
            }
            if (dx < 2) {
                dx = 2;
            }
            return { x: dx, y: dy };
        }
        /***
        取得元素的最后一个子元素
        ***/
        function getLastChildElement(element) {
            var i = element.children.length - 1;
            return element.children[i];
        }      
        function open() {
            currentStatus = statusOptions.open;
            resetParamsForUi();
            fixPos();
            if (!!inEvent.onOpen) {
                inEvent.onOpen();
            }
            clearUserMsgCount();
        }        
        function close() {
            currentStatus = statusOptions.close;
            resetParamsForUi();
            fixPos();
            if (!!inEvent.onClose) {
                inEvent.onClose();
            }
        }
        //切换窗口状态，关闭将开启，开启将关闭
        function toggleOpen() {
            if (currentStatus == statusOptions.open) {
                close();
                return;
            }
            open();
        }
        //重置UI参数
        function resetParamsForUi() {
            var mainElem = getElem(params.getMainId());
            mainElem.style.width = params.getCurrentWidth() + "px";
            mainElem.style.height = params.getCurrentHeight() + "px";
            mainElem.style.border = '1px solid ' + params.borderColor;

            var tipElem = getElem(params.getTipContainerId());
            if (params.TipNeedShow()) {
                tipElem.style.display = 'block';
                getElem(params.getTipId()).style.width = (params.getCurrentWidth() - 35) + 'px';
            } else {
                tipElem.style.display = 'none';
            }

            var iframeElem = getElem(params.getIframeId());
            if (params.IframeNeedShow()) {
                iframeElem.style.display = 'block';
            } else {
                iframeElem.style.display = 'none';
            }
           
            var ctrlElem = getElem(params.getControlId());
            if (currentStatus == statusOptions.open) {
                ctrlElem.innerHTML = '－';
                ctrlElem.title = '关闭聊天窗口';
                setTipTitle('');
            } else if (currentStatus == statusOptions.close) {
                ctrlElem.innerHTML = '□';
                ctrlElem.title = '打开聊天窗口';
                setTipTitle(params.tipTitle);
            }
        }
        //通知功能
        function initNotice() {
            inEvent.onOpen = function () {
                //loadResetNoticeScript();
                mUnreadCount = 0;
            }
            inEvent.onClose = function () {
                //loadNoticeScript(true);

            }
            if (currentStatus == statusOptions.close) {
                // loadNoticeScript(true);
            }
        }

        //加载通知JSONP
        function loadNoticeScript() {
            var clearParam = '';
            if (arguments.length > 0 && arguments[0]) {
                clearParam = '&clear=true';
            }
            var url = combineUrl(params.url, 'api/message/getunreadcount?func=window.$passim.notice' + clearParam + '&rnd=' + Math.random());
            var id = params.getNoticeScriptId();
            var script = document.getElementById(id);
            if (!script) {
                script = document.createElement("script");
                script.id = id;
                script.src = url;
                document.body.appendChild(script);
            }
            script.src = url;
        }
        //通知回调函数
        function notice(count) {
            if (currentStatus == statusOptions.open) { return; }

            var tipElem = getElem(params.getTipId());
            if (count == 0) {
                resetNotice();
            } else {
                setTipTitle('<div style="color:white;">有<span style="color:red">' + count + '</span>条新消息</div>');
            }         
        }

        //加载重置通知回调函数
        function loadResetNoticeScript() {
            var url = combineUrl(params.url, 'api/message/resetunreadcount?func=window.$passim.resetNotice&rnd=' + Math.random());
            var id = params.getNoticeScriptId();
            var script = document.getElementById(id);
            if (script) {
                document.body.removeChild(script);
                script = null;
            }
            script = document.createElement("script");
            script.id = id;
            script.src = url;
            document.body.appendChild(script);
        }

        //重置通知道回调函数
        function resetNotice() {
            if (currentStatus == statusOptions.open) {
                setTipTitle('');
            } else {
                setTipTitle(params.tipTitle);
            }
        }
        /***
        附加事件
        elem：要附加事件的元素
        eventType：要附加的事件类型，不加‘on’
        func：事件回调方法
        ***/
        function addEvent(elem, eventType, func) {
            if (browser.isie) {
                elem.attachEvent('on' + eventType, func);
            } else {
                var capture = false;
                if (arguments.length > 3) {
                    capture = arguments[3];
                }
                elem.addEventListener(eventType, func, capture);
            }
        }

        /***
        撤消事件
        elem：要撤消事件的元素
        eventType：要附加的事件类型，不加‘on’
        ***/
        function removeEvent(elem, func) {
            if (browser.isie) {
                elem.detachEvent('on' + eventType, func);
            } else {
                elem.removeEventListener(eventType, func, false);
            }
        }      
        var getStyleName = (function () {
            var prefixes = ['', '-ms-', '-moz-', '-webkit-', '-khtml-', '-o-'];
            var reg_cap = /-([a-z])/g;
            function getStyleName(css, el) {
                el = el || document.documentElement;
                var style = el.style, test;
                for (var i = 0, l = prefixes.length; i < l; i++) {
                    test = (prefixes[i] + css).replace(reg_cap, function ($0, $1) {
                        return $1.toUpperCase();
                    });

                    if (test in style) {
                        return test;
                    }
                }
                return null;
            }
            return getStyleName;
        })();

        var userSelect = getStyleName("user-select");        
        var getStyle = document.defaultView ? function (el, style) {
            return document.defaultView.getComputedStyle(el, null).getPropertyValue(style)
        } : function (el, style) {
            style = style.replace(/\-(\w)/g, function ($, $1) {
                return $1.toUpperCase();
            });
            return el.currentStyle[style];
        }

        function setNoSelect() {
            if (typeof userSelect === "string") {
                return document.documentElement.style[userSelect] = "none";
            }

            document.unselectable = "on";
            document.onselectstart = function () {
                return false;
            }
        }
        function setCanSelect() {
            if (typeof userSelect === "string") {
                return document.documentElement.style[userSelect] = "text";
            }
            document.unselectable = "off";
            document.onselectstart = null;
        }
    }
   

    window.Params_MC_PASSIM_In = function () {
    }
    window.Params_MC_PASSIM_In.prototype.Init = function () {
        var the = this;
        if (!!!the.hiscode) {
            $passim.init(the.doctor, mc_passim_url, mc_passim_position, mc_passim_width, mc_passim_height, mc_passim_offset_bottom, mc_passim_proxy_url);
        } else {
            $passim.init([the.hiscode, the.doctor], mc_passim_url, mc_passim_position, mc_passim_width, mc_passim_height, mc_passim_offset_bottom, mc_passim_proxy_url);
        }
    }

    window.McPASSTalk = function (hiscode, doctor, message, silent) {
        if (!!!hiscode) {
            $passim.talk(doctor, message, silent)
        } else {
            $passim.talk([hiscode, doctor], message, silent)
        }
    }

})(window);