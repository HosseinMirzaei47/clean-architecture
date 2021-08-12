package com.speaking.partner.data.database

import com.speaking.partner.data.daos.SampleDao

interface CoreDatabase {
    fun sampleDao(): SampleDao
}
