# Room Database models preservation
-keep class com.ai.kakatit.** { * }
-keepclassmembers class com.ai.kakatit.** {
    *;
}

# Keep Kotlin Coroutines and Serialization if used
-keep class kotlinx.coroutines.** { * }

# Retain generic signatures for Room and Gson/Moshi if added later
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
