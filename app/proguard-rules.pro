# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/adt-bundle-mac-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses

-ignorewarnings
#-dontwarn net.sf.andpdf.**
#-dontwarn org.dom4j.**

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
#-dontwarn android.support.**


#-libraryjars  ./libs/android-support-v4.jar
#-libraryjars  ./libs/android-support-v7-appcompat.jar
#-libraryjars  ./libs/androidplot-core-0.6.1.jar
#-libraryjars  ./libs/commons-codec-1.9.jar
#-libraryjars  ./libs/commons-io-2.4.jar
#-libraryjars  ./libs/dom4j-2.0.0-ALPHA-2.jar
#-libraryjars  ./libs/HockeySDK-3.0.1.jar
#-libraryjars  ./libs/jaxen-1.1.6.jar
#-libraryjars  ./libs/PdfViewer.jar
#-libraryjars  ./libs/zxing-core-2.2.jar

-dontoptimize
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}

-keepclassmembers class * extends android.support.v4.app.Fragment {
    public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}


-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keep class !android.support.v7.internal.view.menu.**,android.support.** {*;}

-keep class org.** { *; }
-keep interface org.** { *; }

-printmapping out.map

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# (1)Annotations and signatures
-keepattributes *Annotation*
-keepattributes Signature


 #CA AXA –

-keep class com.ca.** { *; }
-dontwarn com.ca.**

-dontshrink