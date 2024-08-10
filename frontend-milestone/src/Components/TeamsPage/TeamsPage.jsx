import React from 'react';
import './teamspage.css';
import Title from "./Title/Title";
import Myteam from "./Myteam/Myteam";

function TeamsPage() {
    return (
        <div className="App">
            <div className='desktop-title'>
                <Title/>
            </div>
            <div className='panel'>
                <div className='desktop-myteam'>
                    <Myteam/>
                </div>
                {/* Remove or comment out the Teams component */}
                {/* <div className='desktop-teams'>
                    <Teams/>
                </div> */}
            </div>
        </div>
    );
}

export default TeamsPage;
