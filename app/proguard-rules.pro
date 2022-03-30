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
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}





-keep class cn.wildfire.chat.app.login.model.** {*;}
-keepclassmembers class cn.wildfire.chat.app.login.model.** {
  *;
}

-keep class cn.wildfire.chat.kit.net.base.** {*;}
-keepclassmembers class cn.wildfire.chat.kit.net.base.** {
  *;
}



-keep class cn.wildfirechat.model.** {*;}
-keepclassmembers class cn.wildfirechat.model.** {
  *;
}

-keepclassmembers class cn.wildfirechat.** {
    <init>(...);
}

-keepclassmembers class cn.wildfire.** {
    <init>(...);
}

-keep class net.sourceforge.pinyin4j.** { *;}


-keep class com.microsoft.signalr.**{*;}

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**



-keep public class com.future_education.teacher.classgrade.model.**{*;}

-keep public class com.future_education.teacher.event.**{*;}

-keep public class com.future_education.teacher.person.model.**{*;}
-keep public class com.future_education.teacher.homework.event.**{*;}

-keep public class com.future_education.teacher.homework.modle.**{*;}
-keep class com.shuyu.gsyvideoplayer.video.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.**
-keep class com.shuyu.gsyvideoplayer.video.base.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.base.**
-keep class com.shuyu.gsyvideoplayer.utils.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.utils.**
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, java.lang.Boolean);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# For using annotation
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepclassmembers class ** {
    @com.threshold.rxbus3.annotation.RxSubscribe <methods>;
}
-keep enum com.threshold.rxbus3.util.EventThread { *; }

