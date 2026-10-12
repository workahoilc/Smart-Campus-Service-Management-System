
package com.smartcampus.smart_campus_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartcampus.smart_campus_backend.entity.ServiceRequest;
import com.smartcampus.smart_campus_backend.repository.ServiceRequestRepository;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository repository;

    public ServiceRequestService(ServiceRequestRepository repository) {
        this.repository = repository;
    }

    public ServiceRequest createRequest(ServiceRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()
                || request.getDescription() == null
                || request.getDescription().isBlank()
                || request.getCategory() == null
                || request.getCategory().isBlank()
                || request.getStudentEmail() == null
                || request.getStudentEmail().isBlank()) {
            throw new IllegalArgumentException(
                "Title, description, category and student email are required");
        }

        request.setStudentEmail(
            request.getStudentEmail().trim().toLowerCase());
        request.setStatus("PENDING");
        request.setPriority("MEDIUM");
        request.setCreatedAt(LocalDateTime.now());

        return repository.save(request);
    }

    public List<ServiceRequest> getStudentRequests(String email) {
        return repository.findByStudentEmailOrderByCreatedAtDesc(
            email.trim().toLowerCase());
    }

    public List<ServiceRequest> getAllRequests() {
        return repository.findAll();
    }

    public ServiceRequest updateStatus(Long id, String status) {
        if (status == null || !List.of(
                "PENDING", "IN_PROGRESS", "RESOLVED"
        ).contains(status.toUpperCase())) {
            throw new IllegalArgumentException("Invalid request status");
        }

        ServiceRequest request = repository.findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException("Request not found"));

        request.setStatus(status.toUpperCase());
        return repository.save(request);
    }
}
