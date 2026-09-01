package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }
    //circuitBreaker
    @Retry(name = "employeeService", fallbackMethod = "defaultFallback")
    @CircuitBreaker(name = "employeeService", fallbackMethod = "defaultFallback")
    public List<Employee> getAll() {
        return repo.findAll();
    }

    public List<Employee> defaultFallback(Throwable t) {
        return Collections.emptyList();
    }

    @Retry(name = "employeeService", fallbackMethod = "defaultFallback")
    @CircuitBreaker(name = "employeeService", fallbackMethod = "defaultFallback")
    public ResponseEntity<Employee> getById(Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Single generic fallback for methods that take (Long id)
    public <T> ResponseEntity<T> defaultFallback(Long id, Throwable t) {
        return ResponseEntity.status(503).build();
    }

    @Retry(name = "employeeService", fallbackMethod = "defaultFallback")
    @CircuitBreaker(name = "employeeService", fallbackMethod = "defaultFallback")
    public ResponseEntity<Employee> create(Employee employee) {
        Employee saved = repo.save(employee);
        return ResponseEntity.created(java.net.URI.create("/api/employees/" + saved.getId())).body(saved);
    }

    public ResponseEntity<Employee> defaultFallback(Employee employee, Throwable t) {
        return ResponseEntity.status(503).build();
    }

    @Retry(name = "employeeService", fallbackMethod = "defaultFallback")
    @CircuitBreaker(name = "employeeService", fallbackMethod = "defaultFallback")
    public ResponseEntity<Employee> update(Long id, Employee payload) {
        return repo.findById(id).map(existing -> {
            existing.setFirstName(payload.getFirstName());
            existing.setLastName(payload.getLastName());
            existing.setEmail(payload.getEmail());
            existing.setEmail("ankit@gmail.com");
            Employee updated = repo.save(existing);
            return ResponseEntity.ok(updated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Employee> defaultFallback(Long id, Employee payload, Throwable t) {
        return ResponseEntity.status(503).build();
    }

    @Retry(name = "employeeService", fallbackMethod = "defaultFallback")
    @CircuitBreaker(name = "employeeService", fallbackMethod = "defaultFallback")
    public ResponseEntity<Void> delete(Long id) {
        return repo.findById(id).map(existing -> {
            repo.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // removed duplicate \`defaultFallback(Long, Throwable)\` — generic version above covers this case
}
