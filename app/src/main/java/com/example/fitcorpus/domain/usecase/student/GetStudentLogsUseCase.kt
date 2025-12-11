package com.example.fitcorpus.domain.usecase.student

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.repository.StudentLogs
import com.example.fitcorpus.domain.repository.StudentRepository
import java.time.LocalDate
import javax.inject.Inject

class GetStudentLogsUseCase @Inject constructor(
    private val studentRepository: StudentRepository
) {
    suspend operator fun invoke(
        athleteId: String,
        from: LocalDate? = null,
        to: LocalDate? = null
    ): Result<StudentLogs> {
        return studentRepository.getStudentLogs(athleteId, from, to)
    }
}