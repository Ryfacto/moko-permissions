/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("dev.icerock.moko.gradle.publication")
    id("dev.icerock.moko.gradle.stub.javadoc")
    id("dev.icerock.moko.gradle.detekt")
}

dependencies {
    commonMainImplementation(libs.coroutines)

    androidMainImplementation(libs.appCompat)

    commonMainApi(projects.permissions)
}
