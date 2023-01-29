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
[![](https://jitpack.io/v/senselesscn/AndroidScaffold.svg)](https://jitpack.io/#senselesscn/AndroidScaffold)
```groovy
dependencies {
        implementation 'com.github.senselesscn:AndroidScaffold:Tag'
}
```