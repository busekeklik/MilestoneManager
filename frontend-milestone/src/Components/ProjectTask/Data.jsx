// Data.js

export const fetchTasks = async (projectId) => {
    try {
        const response = await fetch(`http://localhost:3307/task/api/v1/list/project/${projectId}`);
        const data = await response.json();

        const formattedTasks = data.map(task => ({
            taskID: task.taskID,
            taskIDstr: task.taskIDstr,
            taskName: task.taskName,
            startDate: new Date(task.startDate),
            endDate: new Date(task.endDate),
            progress: task.progress,
            severity: task.severity,
            analystIds: task.analystIds,
            solutionArchitectIds: task.solutionArchitectIds,
            softwareArchitectIds: task.softwareArchitectIds,
            dependencies: task.dependencyIds.length ? task.dependencyIds.join(',') : null
        }));

        return formattedTasks;
    } catch (error) {
        console.error('Error fetching tasks:', error);
        return [];
    }
};

export const updateTask = async (taskID, updatedTask) => {
    try {
        const response = await fetch(`http://localhost:3307/task/api/v1/update/${taskID}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedTask),
        });

        if (!response.ok) {
            throw new Error('Failed to update task');
        }

        return await response.json();
    } catch (error) {
        console.error('Error updating task:', error);
        throw error;
    }
};

export const fetchUsers = async () => {
    try {
        const response = await fetch(`http://localhost:3307/user/api/v1/list`);
        const data = await response.json();

        return data.map(user => ({
            userID: user.userID,
            userName: user.userName
        }));
    } catch (error) {
        console.error('Error fetching users:', error);
        return [];
    }
};
