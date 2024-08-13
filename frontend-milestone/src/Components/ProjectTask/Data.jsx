// Data.js
export const fetchTasks = async (projectId) => {
    try {
        const response = await fetch(`http://localhost:3307/task/api/v1/list/project/${projectId}`);
        const data = await response.json();

        const formattedTasks = data.map(task => ({
            taskID: task.taskID,
            taskName: `${task.taskName} (${new Date(task.startDate).toLocaleDateString()} - ${new Date(task.endDate).toLocaleDateString()})`,
            startDate: new Date(task.startDate),
            endDate: new Date(task.endDate),
            progress: task.progress,
            severity: task.severity,  // Severity alanını ekliyoruz
            dependencies: task.dependencyIds.length ? task.dependencyIds.join(',') : null
        }));

        return formattedTasks;
    } catch (error) {
        console.error('Error fetching tasks:', error);
        return [];
    }
};

// Add this function to update task progress
export const updateTaskProgress = async (taskID, progress) => {
    try {
        const response = await fetch(`http://localhost:3307/task/api/v1/update/${taskID}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ progress }),  // Updating only the progress field
        });

        if (!response.ok) {
            throw new Error('Failed to update task progress');
        }

        return await response.json();
    } catch (error) {
        console.error('Error updating task progress:', error);
        throw error;
    }
};
