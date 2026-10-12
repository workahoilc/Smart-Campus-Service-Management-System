
package com.smartcampus.smart_campus_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcampus.smart_campus_backend.entity.ServiceRequest;

public interface ServiceRequestRepository
        extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByStudentEmailOrderByCreatedAtDesc(
            String studentEmail);
}
