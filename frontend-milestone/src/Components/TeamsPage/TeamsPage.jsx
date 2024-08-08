import './teamspage.css';
import Title from "./Title/Title";
import Myteam from "./Myteam/Myteam";
import Teams from "./Teams/Teams";

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
                <div className='desktop-teams'>
                    <Teams/>
                </div>
            </div>
        </div>
    );
}

export default TeamsPage;
