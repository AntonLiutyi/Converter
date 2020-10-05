package com.liutyi.converter.repos;

import com.liutyi.converter.models.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
