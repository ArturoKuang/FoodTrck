object Versions {
    object SupportAndroidLibs {
        const val appcompat = "1.2.0"
        const val material = "1.2.1"
        const val navigation = "2.3.1"
        const val constraintLayout = "2.0.4"
        const val androidArcComponents = "2.2.0"
        const val font = "28.0.0"
    }

    object Kotlin {
        const val std = "1.4.2"
        const val core = "1.3.2"
        const val coroutines = "1.3.9"
    }

    object Libraries {
        const val glide = "4.11.0"
        const val room = "2.2.5"
        const val timber = "4.7.1"
        const val retrofit = "2.7.2"
        const val gson = "2.8.5"
        const val logging_interceptor = "4.9.0"
        const val easypermissions = "3.0.0"
    }

    object Google {
        const val hilt = "2.28.1-alpha"
        const val hilt_platform = "1.0.0-alpha02"
        const val maps = "17.0.0"
        const val location = "17.1.0"
    }
}

object Dependencies {
    val supportAndroidLibs = arrayOf(
        "androidx.appcompat:appcompat:${Versions.SupportAndroidLibs.appcompat}",
        "com.google.android.material:material:${Versions.SupportAndroidLibs.material}",
        "androidx.navigation:navigation-fragment:${Versions.SupportAndroidLibs.navigation}",
        "androidx.navigation:navigation-ui:${Versions.SupportAndroidLibs.navigation}",
        "androidx.navigation:navigation-fragment-ktx:${Versions.SupportAndroidLibs.navigation}",
        "androidx.navigation:navigation-ui-ktx:${Versions.SupportAndroidLibs.navigation}",
        "androidx.constraintlayout:constraintlayout:${Versions.SupportAndroidLibs.constraintLayout}",
        "com.android.support:support-compat:${Versions.SupportAndroidLibs.font}"
    )
    val androidArchComponents = arrayOf(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.SupportAndroidLibs.androidArcComponents}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.SupportAndroidLibs.androidArcComponents}",
        "androidx.lifecycle:lifecycle-common-java8:${Versions.SupportAndroidLibs.androidArcComponents}",
        "androidx.lifecycle:lifecycle-extensions:${Versions.SupportAndroidLibs.androidArcComponents}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.SupportAndroidLibs.androidArcComponents}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.SupportAndroidLibs.androidArcComponents}"
    )
    val kotlin = arrayOf(
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.std}",
        "androidx.core:core-ktx:${Versions.Kotlin.core}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
    )
    val google = arrayOf(
        "com.google.dagger:hilt-android:${Versions.Google.hilt}",
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Google.hilt_platform}",
        "com.google.android.gms:play-services-maps:${Versions.Google.maps}",
        "com.google.android.gms:play-services-location:${Versions.Google.location}"
    )
    val libraries = arrayOf(
        "com.github.bumptech.glide:glide:${Versions.Libraries.glide}",
        "androidx.room:room-runtime:${Versions.Libraries.room}",
        "androidx.room:room-ktx:${Versions.Libraries.room}",
        "com.jakewharton.timber:timber:${Versions.Libraries.timber}",
        "com.squareup.retrofit2:retrofit:${Versions.Libraries.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.Libraries.retrofit}",
        "com.google.code.gson:gson:${Versions.Libraries.gson}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Libraries.logging_interceptor}",
        "pub.devrel:easypermissions:${Versions.Libraries.easypermissions}"
        )

    val kotlinAnnotations = arrayOf(
        "com.google.dagger:hilt-android-compiler:${Versions.Google.hilt}",
        "androidx.hilt:hilt-compiler:${Versions.Google.hilt_platform}",
        "com.github.bumptech.glide:compiler:${Versions.Libraries.glide}",
        "androidx.room:room-compiler:${Versions.Libraries.room}"
    )
}
