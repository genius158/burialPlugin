# BurialPlugin
判断方法执行时间，插桩实现

## how to use 
in project mode
```
    classpath 'com.yan.burial:burial-plugin:1.0.2'
```
in app model
```
dependencies {
    ...
    implementation 'com.yan.burial.method.timer:burialtimer:0.0.8'
}

apply plugin: 'burial-plugin'

burialExt {
    logEnable = true
    // 插件工作环境 DEBUG, RELEASE, ALWAYS, NEVER
    runVariant = 'DEBUG'
    // 只插桩到当前配置类  如果不为空，ignoreList失效
    foreList = ['com.burial.test.TestView']
    // 插桩忽略名单
    // ignoreList = ['com.burial.test.MainActivity2']
    // 作用域 PROJECT,SUB_PROJECTS,EXTERNAL_LIBRARIES,TESTED_CODE,PROVIDED_ONLY
    scopes = ['PROJECT']
}

```
int code
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
