(function ($) {
            var duration = 2500;
            var delay = 5000;

            var scrollTop = function ($target, ops) {
                var $list = $target.find('.box');
                return $list.animate({
                    'margin-top': '-=' + $target.height()
                }, {
                    duration: duration,
                    complete: function () {
                        console.log('complete');
                        var marginTop = parseInt($list.css('marginTop'));

                        if (marginTop == -ops.maxHeight) {
                            $list.find('ul').eq(0).appendTo($list);
                            $list.css({marginTop: 0});
                            console.log(123, marginTop);
                        }


                    }
                });
            };

            $.fn.scrollCakeMan = function (height) {
                var that = this;
                var $box = this.find('.box');
                var $list = this.find('ul');
                var listHeight = $list.height();
                var fullCount = Math.ceil(listHeight / height);
                var maxHeight = fullCount * height;
                var interval;
                var animate;

                console.log('maxHeight', maxHeight);
                $list.clone().appendTo($box);
                this.find('ul').css({height: maxHeight});

                interval = setInterval(function () {
                    animate = scrollTop(that, {maxHeight: maxHeight});
                }, delay);


                $('#prevPage').bind('click', function () {
                    var marginTop = parseInt($box.css('marginTop'));
                    var cur = Math.abs(marginTop) /height;

                    clearInterval(interval);
                    if(animate) {animate.stop(true, true)}


                    if(cur > 0) {
                        animate = $box.animate({
                            'margin-top': -(cur*height - height)
                        }, {
                            duration: duration,
                            complete: function() {
                                interval = setInterval(function () {
                                    animate = scrollTop(that, {maxHeight: maxHeight});
                                }, delay);
                            }
                        });
                    }

                });


                $('#nextPage').bind('click', function () {
                    var marginTop = parseInt($box.css('marginTop'));
                    var cur = Math.abs(marginTop) /height;

                    clearInterval(interval);
                    if(animate) {animate.stop(true, true)}


                    if(cur < fullCount-1) {
                        animate = $box.animate({
                            'margin-top': -(cur + 1)*height
                        }, {
                            duration: duration,
                            complete: function() {
                                interval = setInterval(function () {
                                    animate = scrollTop(that, {maxHeight: maxHeight});
                                }, delay);
                            }
                        });
                    }

                });


                return this;
            }
        })($);