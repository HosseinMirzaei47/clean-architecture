package com.app.core.data.datesources.sample

import com.app.core.data.daos.SampleDao
import javax.inject.Inject

class SampleLocalDataSourceImpl @Inject constructor(
    private val sampleDao: SampleDao,
) : SampleLocalDataSource