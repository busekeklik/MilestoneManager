import { Gantt, ViewMode } from "gantt-task-react";
import "gantt-task-react/dist/index.css";

const GanttChart = () => {
    return <Gantt tasks={tasks} viewMode={ViewMode.Day} />;
};

export default GanttChart;
