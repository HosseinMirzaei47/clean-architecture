package com.app.core

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SettingHelperEntryPoint {
    fun getSettingHelperEntryPoint(): SettingsHelper
}