import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

fun getApiKey(property: String): String {
    return gradleLocalProperties(rootDir).getProperty(property)
}

android {
    namespace = "com.example.lifesharing"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lifesharing"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a")
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", getApiKey("kakao.key"))
        buildConfigField("String", "NAVER_CLIENT_ID", getApiKey("naver.id"))
        buildConfigField("String", "NAVER_SECRET_KEY", getApiKey("naver.secret"))
        buildConfigField("String", "BASE_URLS", getApiKey("base.urls"))
        buildConfigField("String", "IMAGE_BASE_URL", getApiKey("image.base.url"))
        buildConfigField("String", "STOMP_BASE_URL", getApiKey("stomp.base.url"))
        buildConfigField("String", "TOSS_CLIENT_KEY", getApiKey("toss.client.key"))
        buildConfigField("String", "TOSS_CUSTOMER_KEY", getApiKey("toss.customer.key"))
        resValue("string", "kakao_auth_host", getApiKey("kakao.oauth"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            versionNameSuffix = rootProject.extra["kotlin_version"] as String
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}
dependencies {

    implementation("com.github.tosspayments:payment-sdk-android:0.1.14")

    implementation("com.google.android.datatransport:transport-runtime:3.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-messaging:23.1.1")
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics:21.2.0")

    implementation("com.kakao.sdk:v2-user:2.19.0")

    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.gms:google-services:4.3.15")
    implementation("com.navercorp.nid:oauth-jdk8:5.9.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.preference:preference-ktx:1.2.1")

    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")

    // location service
    implementation("com.google.android.gms:play-services-location:21.1.0")

    // implementation("com.github.bishoybasily:stomp:2.0.5")
    implementation("com.github.NaikSoftware:StompProtocolAndroid:1.6.6")

    implementation("com.github.bumptech.glide:glide:4.16.0") //image를 연동하기 위해 사용 참고 : https://yunaaaas.tistory.com/43

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("androidx.recyclerview:recyclerview:1.3.2")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.9.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.core:core-ktx:1.10.1")

    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.compose.ui:ui:1.3.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.kakao.maps.open:android:2.6.0") //카카오맵 SDK

    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.activity:activity:1.7.2")
    implementation("androidx.activity:activity-ktx:1.7.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // google map api & location
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:20.0.0")

    implementation ("com.jakewharton.threetenabp:threetenabp:1.3.1")
}
