package com.speaking.partner.data.repositories.sample

import com.speaking.partner.data.datesources.sample.SampleLocalDataSource
import com.speaking.partner.domain.repositories.sample.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val sampleLocalDataSource: SampleLocalDataSource,
) : TaskRepository