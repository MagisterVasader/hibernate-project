package com.modsen.software.employee_service.repository;

import com.modsen.software.employee_service.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Employee, Long> {

    // TODO: Way 1
    // The problem is a double increase in the version parameter and two update statements.
    // With one thread, everything works correctly, but when using multiple threads, data is lost.
    //    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)

    // TODO: Way 2
    // Better than previous. With one thread, everything works correctly, but when using multiple threads, data is lost.
    //    @Lock(LockModeType.OPTIMISTIC)

    // TODO: Way 3
    // Better than previous. With one thread, everything works correctly, but when using multiple threads, data is lost, because of deadlock.
    // After the fix (adding the isolation to the transaction), the problem is not solved.
    //    @Lock(LockModeType.PESSIMISTIC_READ)

    // TODO: Way 4
    // The best way not to lose data. With an increase in the lock level, performance is lost.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Employee> findById(Long id);
}
