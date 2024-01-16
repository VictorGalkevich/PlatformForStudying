document.addEventListener('DOMContentLoaded', function () {
    const groupList = document.getElementById('groupList');
    const groupStudentsContainer = document.getElementById('groupStudents');
    const studentsContainer = document.getElementById('students');
    const showAllButton = document.getElementById('showAllButton');

    let selectedGroupId = null;
    function toggleStudents(groupId) {
        if (selectedGroupId === groupId) {
            studentsContainer.innerHTML = '';
            groupStudentsContainer.style.display = 'none';
            selectedGroupId = null;
        } else {
            fetch(`http://localhost:8081/group/${groupId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    studentsContainer.innerHTML = '';

                    if (Array.isArray(data.result.students)) {
                        data.result.students.forEach((student, index) => {
                            const studentItem = document.createElement('div');
                            studentItem.innerHTML = `<span>${index + 1}.</span>${student.firstName} ${student.lastName}`;
                            studentsContainer.appendChild(studentItem);
                        });
                    } else {
                        console.error('Invalid response format for students:', data);
                    }

                    groupStudentsContainer.style.display = 'block';
                    selectedGroupId = groupId;
                })
                .catch(error => console.error('Error fetching students:', error));
        }
    }

    function addStudentToGroup(groupId, studentId) {
        fetch(`http://localhost:8081/group/${groupId}/addStudent/${studentId}`, {
            method: 'POST',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Student added to group successfully:', data);
            })
            .catch(error => console.error('Error adding student to group:', error));
    }

    function showAllStudents() {
        fetch('http://localhost:8081/students/all')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                studentsContainer.innerHTML = '';
                data.result.forEach((student, index) => {
                    const studentItem = document.createElement('div');
                    studentItem.innerHTML = `<span>${index + 1}.</span>${student.firstName} ${student.lastName}`;

                    const addButton = document.createElement('button');
                    addButton.textContent = 'add in group';
                    addButton.addEventListener('click', () => addStudentToGroup(selectedGroupId, student.id));
                    studentItem.appendChild(addButton);

                    studentsContainer.appendChild(studentItem);
                });
            })
            .catch(error => console.error('Error fetching all students:', error));
    }

    showAllButton.addEventListener('click', showAllStudents);

    fetch('http://localhost:8081/group/all')
        .then(response => response.json())
        .then(data => {
            data.result.forEach(group => {
                const listItem = document.createElement('li');
                listItem.textContent = group.name;
                listItem.addEventListener('click', () => toggleStudents(group.id));
                groupList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching groups:', error));
});