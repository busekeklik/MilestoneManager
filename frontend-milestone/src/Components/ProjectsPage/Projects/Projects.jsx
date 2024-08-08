import React from 'react';
import './projects.css';
import { Data } from './Data';

const Projects = () => {
    return (
        <div className='projects'>
            {Data.map((data, index) => (
                <div className="projects-item" key={index}>
                    <div className="projects-rectangle">
                        <div className='projects-wrapper'>
                            <div className="projects-project">{data.project}</div>
                            <div className="projects-team">{data.team}</div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default Projects;
