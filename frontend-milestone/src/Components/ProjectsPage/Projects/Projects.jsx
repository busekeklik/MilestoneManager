import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './projects.css';

const Projects = () => {
    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProjects = async () => {
            try {
                const response = await fetch('http://localhost:3307/project/api/v1/projects/list');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const projectsData = await response.json();

                const projectsWithTeams = await Promise.all(projectsData.map(async (project) => {
                    const teamResponse = await fetch(`http://localhost:3307/team_project/api/v1/project/${project.projectId}`);
                    if (!teamResponse.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const teamProjects = await teamResponse.json();
                    const teamName = teamProjects.length ? await getTeamName(teamProjects[0].teamID) : 'No Team';
                    return { ...project, teamName };
                }));

                setProjects(projectsWithTeams);
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        };

        fetchProjects();
    }, []);

    const getTeamName = async (teamID) => {
        const response = await fetch(`http://localhost:3307/team/api/v1/find/${teamID}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const team = await response.json();
        return team.teamName;
    };

    const handleProjectClick = (projectId, projectName) => {
        navigate(`/tasks`, { state: { projectId, projectName } });  // Pass projectName as well
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className='projects'>
            {projects.map((project, index) => (
                <div
                    className="projects-item"
                    key={index}
                    onClick={() => handleProjectClick(project.projectId, project.projectName)} // Pass projectName
                    style={{ cursor: 'pointer' }}
                >
                    <div className="projects-rectangle">
                        <div className='projects-wrapper'>
                            <div className="projects-project">{project.projectName}</div>
                            <div className="projects-team">{project.teamName}</div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default Projects;
