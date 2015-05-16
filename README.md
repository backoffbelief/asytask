#AsyncTask
AsyncTask是个人编写的一个Java异步调用框架，支持用户：<br/> 
1）自定义Task，并可设置Task的类型(Type), 子类型（subType），超时时间（TImeout），标识（Flag-可用来区分不同的Task），Task的输入参数（input）等。
2）可通过submitTask，提交 到框架中异步执行。
3）可自定义对应TaskExecutor，通过配置添加到框架中。
4）用户可使用TaskCollector通过TaskManager查询所有的Task。
5）支持持久化，用户提交的Task可以被存储在数据库中。中断的任务重启后自动恢复执行。
6）用户可通过ITaskReference可实时获取Task的状态（State）和进度Progress。
7）用户可定义Task的FinishedCallBack回调。
8）通过ITaskReference的waitForTask，支持用户以同步方式使用。 
9）用户可通过ITaskReference获取Task的执行结果或错误信息。