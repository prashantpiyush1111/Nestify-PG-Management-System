package com.nestify.pg.controller;

import com.nestify.pg.entity.Complaint;
import com.nestify.pg.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMIN"));
    }

    // ADMIN sees every complaint; a TENANT only sees their own.
    @GetMapping
    public List<Complaint> getAllComplaints(Authentication auth) {
        if (isAdmin(auth)) {
            return complaintService.getAllComplaints();
        }
        return complaintService.getComplaintsByTenant(auth.getName());
    }

    // tenantName is never trusted from the request body — it's always
    // taken from the authenticated JWT, so a tenant cannot file a
    // complaint under someone else's name.
    @PostMapping
    public ResponseEntity<Complaint> addComplaint(@RequestBody Complaint complaint,
                                                    Authentication auth) {
        complaint.setTenantName(auth.getName());
        return ResponseEntity.ok(complaintService.addComplaint(complaint));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Complaint> updateStatus(@PathVariable Long id,
                                                   @RequestBody Map<String, String> body) {
        // Already ADMIN-only via SecurityConfig.
        return ResponseEntity.ok(complaintService.updateStatus(id, body.get("status")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long id) {
        // Already ADMIN-only via SecurityConfig.
        complaintService.deleteComplaint(id);
        return ResponseEntity.ok(Map.of("message", "Complaint deleted"));
    }
}