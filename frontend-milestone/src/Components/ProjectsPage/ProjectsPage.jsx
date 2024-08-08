import './projectsPage.css';
import Projects from "./Projects/Projects";
import Title from "./Title/Title";

function ProjectsPage() {
    return (
        <div className="App">
            <div className='desktop-title'>
                <Title />
            </div>
            <div className='panel'>
                <Projects />
            </div>
        </div>
    );
}

export default ProjectsPage;
