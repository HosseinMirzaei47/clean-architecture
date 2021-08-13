package com.app.core.data.repositories.sample

import com.app.core.data.datesources.sample.SampleLocalDataSource
import com.app.core.domain.repositories.sample.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val sampleLocalDataSource: SampleLocalDataSource,
) : TaskRepository