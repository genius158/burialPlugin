# BurialPlugin
判断方法执行耗时，插桩实现[最佳实践TODO](./TODO_README.md)

## say something（如何设计一个需要带到线上方法耗时统计插件，个人见解）
主要看三个方面，包体大小，效率，代码安全性
<br/>
做耗时统计，无非是方法结束时间减去开始时间。刚开始我是考虑的插入前后两个静态（start,end）
耗时cost也就是end执行的时间-start执行的时间，
<br/>
由于我们在所有方法中插入了统计的方法，即使我们只统计主线程的方法耗时，还是存在方法嵌套的情况
也就是说，start可能跑了很多个end确没执几次，这样我们就必须添加一个map，在方法执行前根据方法名参数
存入开始执行时间，end调用的时候，把当前时间减去从map取出来的开始时间。
<br/>
实际代码执行过程过，不一定到底存在多少个嵌套，也就是说map的添加数量不一定，也就可能会出现map扩容，这个执行本身是比较耗时的，
而且map的内存大小扩容之后并不会减回来，可能会有一点浪费，即使换别的数据类型，增删改查过程中，总有一环存在比较耗时、或空间浪费的情况
<br/>
<br/>
我看了一些耗时统计的实现，为了方便统计，在插入方法里我们往往会在参数里加上全类名，或者全方法名，参数描述，如果有人对我们的
的app做反编译，那很多方法类名的混淆就形同虚设，因为我们的耗时埋点直接把原本的类名、方法名写死在了代码里,
<br/>
这样做一个是代码安全性大大下降，另外全方法名、类名一般比较长，也不利于减小包体大小

### 个人插入的代码样式
```
    // 记录当前执行时间
    long start = System.currentTimeMillis();
    ...
    // 方法结束时回调
    // 1.第一个参数返回了类对象，为什么要返回这个参数，因为我们有些时候需要获取执行的类信息（父类、接口等）
    // 如果放回类名，处理前面说的问题，如果我们要获取类对象，我们的对应可能被混淆，直接Class.for还可能
    // 找不到
    // 2.(String)null 方法名，listenerWithMethodDetail为ture时放回，性能强于getStackTrace()
    // 不过存在方法名暴露问题
    // 3.(L;I)V这个是方法参数返回值类型，对象类型只会有L前缀（listenerWithMethodDetail为ture,放回L+参数类型短名称）
    //，弥补getStackTrace()没有对应的参数
    //
    //
    // 方法名查询使用了new Throwable().getStackTrace()，弥补执行方法没有传入的问题
    // new Throwable().getStackTrace()用一个7.0的老的手机循环大于十次执行时间才会超过1ms
    // 相比 Thread.currentThread().getStackTrace(),性能测试稍微好一点
    //
    BurialTimer.timer(Test.class,(String)null, "(L;I)V", start);
```

## how to use 
in project mode
```
    classpath 'com.yan.burial:burial-plugin:1.1.4'
```
 in app module
```
apply plugin: 'burial-plugin'

burialExt {
    logEnable = true
    // 是否在代码里插入方法名，提高性能，但是会暴露方法的原本信息
    listenerWithMethodDetail = true
    // 插件工作环境 DEBUG, RELEASE, ALWAYS, NEVER
    runVariant = 'DEBUG'
    // 只插桩到当前匹配的所有类 如果不为空，ignoreList失效 ，内部采用 contains(item)，来包涵匹配到的类
    foreList = ['com.burial.test.TestView']
    // 插桩忽略名单，不需要全类名，内部采用 contains(item)，来剔除配到的类
    // ignoreList = ['com.burial.test.MainActivity2']
    // 作用域 PROJECT,SUB_PROJECTS,EXTERNAL_LIBRARIES,TESTED_CODE,PROVIDED_ONLY
    scopes = ['PROJECT']
}

```
 int code 内部有默认实现[DefaultListener](https://github.com/genius158/burialPlugin/blob/master/burialTimer/src/main/java/com/yan/burial/method/timer/DefaultListener.java)
``` 
 BurialTimer.getTimer()
    .setListener { ignore: BurialTimer?, className: String, methodName: String, des: String, cost: Long ->
        Log.e("BurialTimer", "$cost  $className  $methodName  $des")
    }
```
## License

    Copyright 2020 genius158

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.# burialPlugin
