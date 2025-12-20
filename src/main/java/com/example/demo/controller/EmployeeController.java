// src/main/java/com/example/demo/controller/EmployeeController.java
    package com.example.demo.controller;

    import com.example.demo.model.Employee;
    import com.example.demo.service.EmployeeService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/employees")
    public class EmployeeController {

        private final EmployeeService svc;

        public EmployeeController(EmployeeService svc) {
            this.svc = svc;
        }

        @GetMapping
        public List<Employee> getAll() {
            return svc.getAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Employee> getById(@PathVariable Long id) {
            return svc.getById(id);
        }

        @PostMapping
        public ResponseEntity<Employee> create(@RequestBody Employee employee) {
            return svc.create(employee);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee payload) {
            return svc.update(id, payload);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            return svc.delete(id);
        }
    }