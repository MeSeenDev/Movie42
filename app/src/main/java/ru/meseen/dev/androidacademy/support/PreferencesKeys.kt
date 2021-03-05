package ru.meseen.dev.androidacademy.support

enum class PreferencesKeys(val defaultKey: String, val key: String) {
    LANGUAGE_KEY(key = "language_preference", defaultKey = "EN"),
    REGION_KEY(key = "regions_preference", defaultKey = "US") //TODO поменять ключи на совсем дефолтные
}