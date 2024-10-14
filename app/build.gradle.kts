plugins {
    alias(libs.plugins.android.application)
    id("com.chaquo.python")
}

android {
    namespace = "methods.numericalmethods"
    compileSdk = 34

    defaultConfig {
        applicationId = "methods.numericalmethods"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "2.23"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("arm64-v8a", "x86_64")
        }

        flavorDimensions += "pyVersion"
        productFlavors {
            create("py38") { dimension = "pyVersion" }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        exclude("graphml.xsd")
        exclude("xlink.xsd")
        exclude("viz.xsd")
        exclude("META-INF/DEPENDENCIES")
        exclude("gexf.xsd")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

chaquopy {
    productFlavors {
        getByName("py38") { version = "3.8" }
    }

    defaultConfig {
        buildPython("C:/Users/Hp-pavilion/AppData/Local/Programs/Python/Python38/python.exe")
        pip {
            install ("sympy")
        }
    }

    sourceSets {
        getByName("main") {
            srcDir("src/main/python")
        }
    }
}
dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation ("com.github.frhnfrq:MathView:1.2")
    implementation ("net.objecthunter:exp4j:0.4.8")
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}