# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-keep class * implements java.io.Serializable { *; }
-keepattributes *Annotation
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

-keep class com.xuexiang.xupdate.entity.** { *; }

#注意，如果你使用的是自定义Api解析器解析，还需要给你自定义Api实体配上混淆，如下是本demo中配置的自定义Api实体混淆规则：
-keep class com.xuexiang.xupdatedemo.entity.** { *; }

-keep class org.xmlpull.v1.** { *;}
-dontwarn org.xmlpull.v1.**
-keep public class com.futureeducation.commonmodule.model.**{*;}

-keep public class com.futureeducation.commonmodule.websocket.bean.**{*;}


-keep class com.microsoft.signalr.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# for DexGuard only
-keep resourcexmlelements manifest/application/meta-data@value=GlideModule