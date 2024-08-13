export const fetchTasks = async (projectId) => {  // projectId parametresi ekleniyor
    try {
        const response = await fetch(`http://localhost:3307/task/api/v1/list?projectId=${projectId}`); // projectId parametresi ile API çağrısı
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