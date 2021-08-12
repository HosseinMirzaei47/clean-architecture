package com.speaking.partner.data.datesources.sample

import com.speaking.partner.data.daos.SampleDao
import javax.inject.Inject

class SampleLocalDataSourceImpl @Inject constructor(
    private val sampleDao: SampleDao,
) : SampleLocalDataSource