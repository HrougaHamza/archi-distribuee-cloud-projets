package com.tp.studentsapi.controller;

import com.tp.studentsapi.model.Student;
import com.tp.studentsapi.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repo;

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Student> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Student one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found: " + id));
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student s) {
        s.setId(null);
        Student saved = repo.save(s);
        return ResponseEntity.created(URI.create("/students/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student s) {
        Student existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found: " + id));
        existing.setName(s.getName());
        existing.setEmail(s.getEmail());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
