## 安卓工具类

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
[![](https://jitpack.io/v/senseless00/AndroidScaffold.svg)](https://jitpack.io/#senseless00/AndroidScaffold)
```groovy
dependencies {
        implementation 'com.github.senseless00:AndroidScaffold:Tag'
}
```