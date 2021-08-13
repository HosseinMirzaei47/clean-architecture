package com.app.core.data.database

import com.app.core.data.daos.SampleDao

interface CoreDatabase {
    fun sampleDao(): SampleDao
}
