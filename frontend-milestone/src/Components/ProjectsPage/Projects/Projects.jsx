import React, { useState, useEffect } from 'react';
import './projects.css';

const Projects = () => {
    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProjects = async () => {
            try {
                const response = await fetch('http://localhost:3307/project/api/v1/projects/list');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const projectsData = await response.json();

                // Fetch team names for each project
                const projectsWithTeams = await Promise.all(projectsData.map(async (project) => {
                    const teamResponse = await fetch(`http://localhost:3307/team_project/api/v1/project/${project.projectId}`);
                    if (!teamResponse.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const teamProjects = await teamResponse.json();

                    // Assuming teamProjects array is not empty and you have a getTeamName function
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

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className='projects'>
            {projects.map((project, index) => (
                <div className="projects-item" key={index}>
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
