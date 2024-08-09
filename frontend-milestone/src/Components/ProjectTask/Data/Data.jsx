import axios from 'axios';

export const fetchTasks = async () => {
    try {
        const response = await axios.get('http://localhost:3307/task/api/v1/list');
        const taskData = response.data.map(task => ({
            start: new Date(task.startDate),
            end: new Date(task.endDate),
            name: task.name,
            id: task.id,
            progress: task.progress,
            type: task.type,
            project: task.project,
            dependencies: task.dependencies ? task.dependencies.split(',') : []
        }));
        return taskData;
    } catch (error) {
        console.error('Error fetching tasks:', error);
        return [];
    }
};

export const getStartEndDateForProject = (tasks, projectId) => {
    const projectTasks = tasks.filter((t) => t.project === projectId);
    let start = projectTasks[0].start;
    let end = projectTasks[0].end;

    for (let i = 0; i < projectTasks.length; i++) {
        const task = projectTasks[i];
        if (start.getTime() > task.start.getTime()) {
            start = task.start;
        }
        if (end.getTime() < task.end.getTime()) {
            end = task.end;
        }
    }
    return [start, end];
};

export const initTasks = () => {
    return [
        {
            start: new Date(),
            end: new Date(),
            name: "Initial Task",
            id: "Task 1",
            progress: 50,
            type: "task",
        },
    ];
};
