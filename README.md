## 简单实现澎湃客户端置顶新闻文字滚动效果
### 效果图:



### 此demo需求的是这样:
+ 仿照澎湃新闻客户端的置顶新闻实现滚动效果；

+ 每行文字滚动到最后停止，不再继续滚动；

+ 第一条新闻是固定的，下面两条是循环的，比如共4条新闻，第一次：1、2、3，第二次：1、4、2，第三次：1、3、4 .....以此类推；

+ 点击item跳转详情

### 实现思路:
+ HorizontalScrollView实现滚动（TextView控件自带跑马灯效果不能达到需求的效果）；

+ 基于RxJava实现轮询定时器和任务执行器；



#### 代码逻辑通俗易懂！有需要的同学可以根据源码自定义需求，欢迎star~

demo地址：[TextSinglelinesScrolling](https://github.com/cying112115/TextSinglelinesScrolling/)
