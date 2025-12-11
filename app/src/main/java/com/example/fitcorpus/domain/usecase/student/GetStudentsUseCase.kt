package com.example.fitcorpus.domain.usecase.student

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Student
import com.example.fitcorpus.domain.repository.StudentRepository
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(
    private val studentRepository: StudentRepository
) {
    suspend operator fun invoke(): Result<List<Student>> {
        return studentRepository.getStudents()
    }
}