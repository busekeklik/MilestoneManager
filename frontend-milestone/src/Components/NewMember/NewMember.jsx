import React, { useState } from 'react';
import Select from 'react-select';
import './NewMember.css';

const NewMember = () => {
    const [name, setName] = useState('');
    const [team, setTeam] = useState([]);
    const [role, setRole] = useState([]);
    const [email, setEmail] = useState('');

    const handleSave = () => {
        console.log('Member saved:', { name, team, role, email });
    };

    const teamOptions = [
        { value: 'development', label: 'Pulsar' },
        { value: 'marketing', label: 'Pulsar' },
        { value: 'design', label: 'Pulsar' }
    ];

    const roleOptions = [
        { value: 'team-leader', label: 'Takım Lideri' },
        { value: 'team-member', label: 'Back-End Dev' },
        { value: 'intern', label: 'Intern' }
    ];

    return (
        <div className="new-member-container">
            <div className="new-member-form-header">
                <h1>Yeni Üye Ekleme</h1>
            </div>
            <div className="new-member-form">
                <form>
                    <div className="form-group">
                        <label>İsim -Soyisim</label>
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                        <label>Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />

                        <label>Ekibi</label>
                        <Select
                            options={teamOptions}
                            value={team}
                            onChange={setTeam}
                            styles={{
                                control: (base) => ({
                                    ...base,
                                    borderRadius: '20px',
                                    borderColor: '#ccc',
                                    padding: '5px'
                                })
                            }}
                        />
                        <label>Rolü</label>
                        <Select
                            isMulti
                            options={roleOptions}
                            value={role}
                            onChange={setRole}
                            styles={{
                                control: (base) => ({
                                    ...base,
                                    borderRadius: '20px',
                                    borderColor: '#ccc',
                                    padding: '5px'
                                })
                            }}
                        />
                    </div>

                </form>

                <button type="button" className="save-button" onClick={handleSave}>Kaydet</button>
            </div>
        </div>
    );
};

export default NewMember;
