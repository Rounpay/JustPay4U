# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/cys/Public/androidADT/sdk/tools/proguard/proguard-android.txt
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
-ignorewarnings
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}
-keepclassmembers class com.paytm.pgsdk.paytmWebView$PaytmJavaScriptInterface {
   public *;
}

-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-dontwarn org.codehaus.**
-dontwarn retrofit2.Platform$Java8
-dontwarn org.bouncycastle.**
-dontwarn de.wirecard.**
-dontwarn com.fasterxml.**
-dontwarn java.io.**
-dontwarn org.apache.http.annotation.**

-dontwarn com.pnsol.sdk.**
-dontwarn com.newland.**
-dontwarn com.dspread.**

-keep class com.pnsol.sdk.newland.listener.** { *; }
-keep class com.pnsol.sdk.auth.** {*;}
-keep class com.pnsol.sdk.payment.PaymentInitialization {*;}
-keep class com.pnsol.sdk.payment.** {*;}
-keep class com.pnsol.sdk.qpos.listener.QPOSListener {*;}
-keep class com.pnsol.sdk.interfaces.** {*;}
-keep class com.pnsol.sdk.enums.** {*;}
-keep class com.pnsol.sdk.usb.USBClass{*;}
-keep class com.dspread.xpos.BLE.** { *; }
-keep class com.pnsol.sdk.util.** { *; }
-keep class com.newland.mpos.payswiff.**{*;}
-keep class org.codehaus.jackson.** { *; }
-keep class android.a.a.** { *; }
-keep class android.misc.** { *; }
-keep class android.newland.** { *; }
-keep class android.psam.** { *; }
-keep class com.a.a.** { *; }
-keep class com.newland.** { *; }
-keep class com.nlscan.android.scan.** { *; }
-keep class com.pnsol.sdk{ *; }

-keep public class your.class.* {
public void set_(_);
public ** get*();
}
-keep class com.yourapp.class1 { *; }

-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**

-keep class android.support.v7.widget.SearchView { *; }

-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *; }

# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keeppackagenames org.jsoup.nodes

-keepattributes Annotation
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

 -keep class cn.pedant.SweetAlert.Rotate3dAnimation {
    public <init>(...);
}

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}