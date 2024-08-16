import React, { useEffect, useState } from 'react';
import { addDays, format, startOfWeek, subDays, isWeekend, isSameDay, startOfDay } from 'date-fns';
import { tr } from 'date-fns/locale';
import { FaCheckCircle, FaTimesCircle } from 'react-icons/fa';
import Select from 'react-select';
import UserPopup from './UserPopup';
import './TeamPage.css';

const TeamPage = () => {
    const [startDate, setStartDate] = useState(startOfWeek(new Date(), { weekStartsOn: 1 }));
    const [teams, setTeams] = useState([]);
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [userToEdit, setUserToEdit] = useState(null);
    const [userOptions, setUserOptions] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);

    useEffect(() => {
        fetchData();
    }, [startDate]);

    const fetchData = async () => {
        try {
            const [teamResponse, userResponse, absenceResponse, taskResponse] = await Promise.all([
                fetch('http://localhost:3307/team/api/v1/list'),
                fetch('http://localhost:3307/user/api/v1/list'),
                fetch('http://localhost:3307/absence/api/v1/list'),
                fetch('http://localhost:3307/task/api/v1/list')
            ]);

            const teamsData = await teamResponse.json();
            const usersData = await userResponse.json();
            const absencesData = await absenceResponse.json();
            const tasksData = await taskResponse.json();

            const teamsWithTasksAndAbsences = teamsData.map(team => {
                const updatedMembers = team.members.map(member => {
                    const userTasks = tasksData.filter(task => task.softwareArchitectIds.includes(member.userID)).map(task => ({
                        taskName: task.taskName,
                        progress: task.progress
                    }));

                    const userAbsences = absencesData.filter(absence => absence.userId === member.userID)
                        .map(absence => ({
                            absenceID: absence.absenceID,
                            start: new Date(absence.startDate),
                            end: new Date(absence.endDate),
                            status: true,
                        }));

                    return { ...member, tasks: userTasks, activeRanges: userAbsences };
                });

                return { ...team, members: updatedMembers };
            });

            setTeams(teamsWithTasksAndAbsences);

            // Update user options for select menu
            const userOptions = usersData.map(user => ({
                value: user.userID,
                label: user.userName,
                user: user
            }));
            setUserOptions(userOptions);

        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const handlePrevWeek = () => {
        setStartDate(prevDate => subDays(prevDate, 7));
    };

    const handleNextWeek = () => {
        setStartDate(prevDate => addDays(prevDate, 7));
    };

    const handleSaveUser = async (user) => {
        try {
            user.team_id = 1;

            const requestOptions = {
                method: user.userID ? 'PUT' : 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(user),
            };

            const url = user.userID
                ? `http://localhost:3307/user/api/v1/update/${user.userID}`
                : 'http://localhost:3307/user/api/v1/create';

            await fetch(url, requestOptions);
            fetchData(); // Refresh data after saving
            closePopup(); // Close the popup
        } catch (error) {
            console.error('Error saving user:', error);
        }
    };

    const handleDeleteUser = async (userID) => {
        try {
            await fetch(`http://localhost:3307/user/api/v1/delete/${userID}`, {
                method: 'DELETE',
            });
            fetchData();
            closePopup();
        } catch (error) {
            console.error('Error deleting user:', error);
        }
    };

    const toggleAbsence = async (member, day) => {
        try {
            const existingAbsence = member.activeRanges.find(
                range => day >= range.start && day <= range.end
            );

            if (existingAbsence) {
                await fetch(`http://localhost:3307/absence/api/v1/delete/${existingAbsence.absenceID}`, {
                    method: 'DELETE',
                });
            } else {
                const newAbsence = {
                    startDate: format(day, 'yyyy-MM-dd'),
                    endDate: format(day, 'yyyy-MM-dd'),
                    type: 'Unpaid',
                    description: 'Auto-generated absence',
                    userId: member.userID,
                };

                const requestOptions = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(newAbsence),
                };

                await fetch('http://localhost:3307/absence/api/v1/create', requestOptions);
            }

            fetchData(); // Refresh data after updating
        } catch (error) {
            console.error('Error toggling absence:', error);
        }
    };

    const openPopup = (user = null) => {
        setUserToEdit(user);
        setIsPopupOpen(true);
    };

    const closePopup = () => {
        setIsPopupOpen(false);
        setUserToEdit(null);
    };

    const renderWeekDaysHeader = () => {
        const today = startOfDay(new Date());
        return [...Array(7)].map((_, i) => {
            const day = addDays(startDate, i);
            const isToday = isSameDay(day, today);
            const weekendClass = isWeekend(day) ? 'weekend' : '';
            const todayClass = isToday ? 'today' : '';

            return (
                <th key={i} className={`${weekendClass} ${todayClass}`}>
                    {format(day, 'EEE, dd MMM', { locale: tr })}
                </th>
            );
        });
    };

    const renderCustomRowCells = (member) => {
        const today = startOfDay(new Date());
        return [...Array(7)].map((_, i) => {
            const day = addDays(startDate, i);
            const isDayWeekend = isWeekend(day);
            const isDayInAbsenceRange = member.activeRanges && member.activeRanges.some(range => day >= range.start && day <= range.end);
            const isActive = !isDayWeekend && !isDayInAbsenceRange;
            const isToday = isSameDay(day, today);
            const todayClass = isToday ? 'today' : '';

            return (
                <td
                    key={i}
                    className={`${isDayWeekend ? 'weekend' : ''} ${todayClass}`}
                    onClick={() => toggleAbsence(member, day)}
                >
                    {isActive ? <FaCheckCircle className="status-icon active" /> : <FaTimesCircle className="status-icon inactive" />}
                </td>
            );
        });
    };

    return (
        <div className="team-page">
            <div className="team-page-header">
                <h1>EKİBİM</h1>
            </div>
            <div className="navigation-buttons">
                <button onClick={handlePrevWeek}>&#8592;</button>
                <button onClick={handleNextWeek}>&#8594;</button>
            </div>
            <div className="team-table-container">
                <table className="team-table">
                    <thead>
                    <tr>
                        <th>Ekip Üyeleri</th>
                        <th>Rolü</th>
                        <th>Task</th>
                        <th>Task İlerleyiş</th>
                        {renderWeekDaysHeader()}
                    </tr>
                    </thead>
                    <tbody>
                    {teams.length > 0 ? teams.map(team =>
                        team.members.map((member, index) => (
                            <tr key={index}>
                                <td>{member.userName}</td>
                                <td>{member.roles.join(', ')}</td>
                                <td>{member.tasks.map(task => task.taskName).join(', ')}</td>
                                <td>{member.tasks.map(task => `${task.progress}%`).join(', ')}</td>
                                {renderCustomRowCells(member)}
                            </tr>
                        ))
                    ) : <tr><td colSpan="11">Loading team data...</td></tr>}
                    </tbody>
                </table>
            </div>
            <div className="team-page-footer">
                <button onClick={() => openPopup()}>Add New User</button>
                <Select
                    className="user-select"
                    options={userOptions}
                    value={selectedUser}
                    onChange={setSelectedUser}
                    placeholder="Select User to Edit"
                    menuPortalTarget={document.body}  // Render the dropdown in the portal
                    styles={{
                        menuPortal: base => ({ ...base, zIndex: 9999 }),  // Ensure it stays on top
                        control: base => ({
                            ...base,
                            minWidth: 200,
                        }),
                        menu: base => ({
                            ...base,
                            maxHeight: '200px',
                            overflowY: 'auto',
                        }),
                    }}
                />
                <button
                    onClick={() => openPopup(selectedUser ? selectedUser.user : null)}
                    disabled={!selectedUser}
                >
                    Edit User
                </button>
            </div>
            <UserPopup
                isOpen={isPopupOpen}
                onClose={closePopup}
                userToEdit={userToEdit}
                onSave={handleSaveUser}
                onDelete={handleDeleteUser}
            />
        </div>
    );
};

export default TeamPage;
