package com.etiya.milestonemanager.business.services;

import java.util.List;

public interface ITaskServices<D, E> {
    // Method to convert Entity to DTO
    D entityToDto(E e);

    // Method to convert DTO to Entity
    E dtoToEntity(D d);

    // Delete all tasks
    void taskServiceDeleteAllData();

    // Create a new task
    D taskServiceCreate(D d);

    // List all tasks
    List<D> taskServiceList();

    // Find a task by its ID
    D taskServiceFindById(Long id);

    // Update a task by its ID
    D taskServiceUpdateById(Long id, D d);

    // Delete a task by its ID
    D taskServiceDeleteById(Long id);

    // New Method: List tasks by Project ID
    List<D> taskServiceListByProjectId(Long projectId);  // Add this method
}
