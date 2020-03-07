package com.brain.dreamnote.ui

import androidx.lifecycle.ViewModel
import com.brain.dreamnote.repository.TestRepository
import javax.inject.Inject

class TestViewModel @Inject constructor(testRepository: TestRepository) : ViewModel() {
}