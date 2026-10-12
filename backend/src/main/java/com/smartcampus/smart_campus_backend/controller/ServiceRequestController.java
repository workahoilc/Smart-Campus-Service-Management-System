
package com.smartcampus.smart_campus_backend.controller;

import com.smartcampus.smart_campus_backend.entity.ServiceRequest;
import com.smartcampus.smart_campus_backend.service.ServiceRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class ServiceRequestController {

    private final ServiceRequestService service;

    public ServiceRequestController(ServiceRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createRequest(
            @RequestBody ServiceRequest request) {
        try {
            ServiceRequest saved = service.createRequest(request);
            return ResponseEntity.ok(Map.of(
                "message", "Service request created successfully",
                "id", saved.getId(),
                "title", saved.getTitle(),
                "category", saved.getCategory(),
                "status", saved.getStatus()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/student/{email}")
    public List<ServiceRequest> getStudentRequests(
            @PathVariable String email) {
        return service.getStudentRequests(email);
    }

    @GetMapping
    public List<ServiceRequest> getAllRequests() {
        return service.getAllRequests();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            ServiceRequest updated = service.updateStatus(
                id, body.get("status"));

            return ResponseEntity.ok(Map.of(
                "message", "Status updated successfully",
                "id", updated.getId(),
                "status", updated.getStatus()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", e.getMessage()));
        }
    }
}
