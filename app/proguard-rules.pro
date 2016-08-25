##-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-keepattributes *Annotation*

-optimizationpasses 5
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-keepattributes Signature
-keepattributes *Annotation*

-include annotations.pro


-dontwarn android.support.v4.**
-dontwarn org.apache.**
-dontwarn rx.**

#CollectionsQuery
-dontwarn com.sun.jna.**
-dontwarn java.lang.**
-dontwarn com.innahema.**

-dontwarn com.squareup.okhttp.*
-dontwarn com.google.appengine.api.urlfetch.*


#square

-dontwarn rx.**

-dontwarn okio.**

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontwarn retrofit.**
-dontwarn retrofit.appengine.UrlFetchClient
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}





# should be copied to application proguard rules config
-keep class com.android.vending.billing.**
-assumenosideeffects class org.solovyev.android.checkout.Billing {
public static void debug(...);
public static void warning(...);
public static void error(...);
}
-assumenosideeffects class org.solovyev.android.checkout.Check {
*;
}

-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-keep class org.jsoup.nodes.** { *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper

-keep public class * extends android.preference.Preference
-keep public class * extends android.preference.DialogPreference
-keep public class * extends android.preference.ListPreference

-keep class * extends android.preference.DialogPreference {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}


-keep class * extends android.preference.ListPreference {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keep class * extends android.preference.Preference {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}


-keep class org.apache.**
-keep interface org.apache.**


-keep class android.webkit.WebViewClient

-keep class * extends android.webkit.WebViewClient
-keep class * extends android.webkit.WebViewClient



-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}


-keepclassmembers class * extends android.webkit.WebViewClient {
    <methods>;
}

# This will avoid all the onClick listeners referenced from XML Layouts from being removed
-keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
}




-keep class greendroid.**
{
*;
}



-keep class com.j256.** {
     *;
}


-keep class android.webkit.WebViewClient
-keep class * extends android.webkit.WebViewClient

-keepclassmembers class * extends android.webkit.WebViewClient {
    <methods>;
}

-keepclassmembers class ** {
    public void onEvent*(**);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * {
  public <init>(android.content.Context);

}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}



# keep this class so that logging will show 'ACRA' and not a obfuscated name like 'a'.
# Note: if you are removing log messages elsewhere in this file then this isn't necessary
-keep class org.acra.ACRA {
        *;
}

# keep this around for some enums that ACRA needs
-keep class org.acra.ReportingInteractionMode {
   *;
}

# keep this otherwise it is removed by ProGuard
-keep public class org.acra.ErrorReporter
{
public void addCustomData(java.lang.String,java.lang.String);
}

# keep this otherwise it is removed by ProGuard
-keep public class org.acra.ErrorReporter
{
public org.acra.ErrorReporter$ReportsSenderWorker handleSilentException(java.lang.Throwable);
}


# Google Play Services


-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}


# Serializable

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


# Butterknife

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Guava

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe
-dontwarn org.joda.convert.**

# Retrolambda

-dontwarn java.lang.invoke.*

#EventBus

-keepclassmembers class ** {
    public void onEvent*(**);
}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
#    ThrowableFailureEvent(java.lang.Throwable);
#}

#Support libraries

-keep class android.support.v13.** { *; }
-keep interface android.support.v13.** { *; }

-keep class !android.support.v7.internal.view.menu.MenuBuilder, !android.support.v7.internal.view.menu.SubMenuBuilder, android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

-dontwarn **CompatHoneycomb
-dontwarn **CompatHoneycombMR2
-dontwarn **CompatCreatorHoneycombMR2

-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

#GUAVA

# Configuration for Guava
#
# disagrees with instructions provided by Guava project: https://code.google.com/p/guava-libraries/wiki/UsingProGuardWithGuava
#
# works if you add the following line to the Gradle dependencies
#
# provided 'javax.annotation:jsr250-api:1.0'
-keep class com.google.common.io.Resources {
public static <methods>;
}
-keep class com.google.common.collect.Lists {
public static ** reverse(**);
}
-keep class com.google.common.base.Charsets {
public static <fields>;
}
-keep class com.google.common.base.Joiner {
public static Joiner on(String);
public ** join(...);
}
-keep class com.google.common.collect.MapMakerInternalMap$ReferenceEntry
-keep class com.google.common.cache.LocalCache$ReferenceEntry

#RX

# RxJava 0.21
-keep class rx.schedulers.Schedulers {
public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
public <methods>;
}
-keep class rx.schedulers.TestScheduler {
public <methods>;
}
-keep class rx.schedulers.Schedulers {
public static ** test();
}