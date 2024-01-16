function getTestsForUser() {
    fetch('http://localhost:8081/test/all')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error in fetching data.');
            }
        })
        .then(data => {
            displayTests(data);
        })
        .catch(error => console.error('Error:', error));
}

function displayTests(tests) {
    const userSection = document.getElementById('student-section');
    if (userSection === null) {
        console.log("this user admin");
    } else {
    userSection.innerHTML = '';
    tests.forEach(test => {
        const testElement = document.createElement('div');
        testElement.classList.add('test-card');
        testElement.innerHTML = `
            <div class="test-header" onclick="toggleTestDetails(this)">
                <h3>${test.testName}</h3>
            </div>
            <div class="test-info" style="display: none;">
                <p>ID: ${test.id}</p>
                <p>Start Time: ${test.startTime}</p>
                <p>End Time: ${test.endTime}</p>
                <p>Duration: ${test.duration} minutes</p>
                <p>Created By: ${test.createdBy}</p>
            </div>
            <button class="start-test-btn" onclick="startTest(${test.id})">Start Test</button>
        `;

        userSection.appendChild(testElement);
    });
    }
}

function toggleTestDetails(testHeader) {
    const testInfo = testHeader.nextElementSibling;
    testInfo.style.display = testInfo.style.display === 'none' ? 'block' : 'none';
}

function startTest(testId) {
    window.location.href = `http://localhost:8081/questions?testId=${testId}`;
}
document.addEventListener('DOMContentLoaded', getTestsForUser);

function createGroup() {
    const groupName = document.getElementById('groupName').value;

    fetch('http://localhost:8081/group/create?groupName=' + encodeURIComponent(groupName), {
        method: 'POST',
        credentials: 'same-origin'
    })
        .then(response => response.json())
        .then(data => {
            const createGroupMessage = document.getElementById('createGroupMessage');
            createGroupMessage.textContent = data.message;
            console.log(data);
            if (data.success) {
                document.getElementById('groupName').value = '';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function navigateToCreateTest() {
    window.location.href = "/test";
}

function navigateToAllTests() {
    window.location.href = '/tests';
}

function navigateToAllGroups() {
    window.location.href = '/groups'
}

