import { useEffect, useState } from 'react';

export const fetchTasks = async () => {
    try {
        const response = await fetch('http://localhost:3307/task/api/v1/list');
        const data = await response.json();

        const formattedTasks = data.map(task => ({
            taskID: task.taskID,
            taskName: `${task.taskName} (${new Date(task.startDate).toLocaleDateString()} - ${new Date(task.endDate).toLocaleDateString()})`,
            startDate: new Date(task.startDate),
            endDate: new Date(task.endDate),
            progress: task.progress
        }));

        return formattedTasks;
    } catch (error) {
        console.error('Veri çekme hatası:', error);
        return [];
    }
};

export const getStartEndDateForProject = (tasks, projectId) => {
    const projectTasks = tasks.filter((t) => t.projectId === projectId);
    let start = projectTasks[0]?.startDate;
    let end = projectTasks[0]?.endDate;

    for (let i = 1; i < projectTasks.length; i++) {
        const task = projectTasks[i];
        if (start.getTime() > task.startDate.getTime()) {
            start = task.startDate;
        }
        if (end.getTime() < task.endDate.getTime()) {
            end = task.endDate;
        }
    }
    return [start, end];
};