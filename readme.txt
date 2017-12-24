测试条件
1.需要翻墙，如不翻墙，可能造成图片加载不出来

实现介绍：
1.使用HttpURLConnection获取数据通过三方Gson库解析json数据
2.使用三方ImageLoader库进行图片加载

结构介绍
1.package com.hometest.wechatapp 该包下主要放了本应用的Application和Activity
2.package com.hometest.wechatapp.data Gson解析时用的类
3.package com.hometest.wechatapp.http 获取服务器数据
4.package com.hometest.wechatapp.view 自定义显示view
5.package com.hometest.wechatapp.adpter  自定义adapter，实现与view的数据传递
6.package com.hometest.wechatapp.util 工具类