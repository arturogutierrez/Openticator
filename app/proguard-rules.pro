-verbose
-ignorewarnings
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,SourceFile,LineNumberTable,*Annotation*,Signature
-optimizationpasses 3

# Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# RxJava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
