### 
> 对webmagic项目做了一些修改，增加了任务结束回调、以及额外参数传递


### 使用
#### crawlook 核心依赖
```xml
<dependency>
    <groupId>io.github.longxiaoyun</groupId>
    <artifactId>crawlook-core</artifactId>
    <version>1.0.5</version>
</dependency>
```

#### crawlook 扩展模块
```xml
<dependency>
    <groupId>io.github.longxiaoyun</groupId>
    <artifactId>crawlook-extension</artifactId>
    <version>1.0.5</version>
</dependency>
```



### 打包
```shell
# 父模块
maven clean package deploy -N

# 指定子模块
maven clean package deploy -pl crawlook-core
```

