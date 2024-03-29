async function getTests() {
    const response = await fetch('http://localhost:8081/test/allWithDetails');
    const tests = await response.json();

    const testList = document.getElementById('testList');
    testList.innerHTML = '';

    tests.forEach(test => {
        const listItem = document.createElement('li');

        const testLink = document.createElement('a');
        testLink.href = `javascript:void(0);`;
        testLink.textContent = test.testName;
        testLink.onclick = () => showTestContent(test);
        listItem.appendChild(testLink);

        const timeContainer = document.createElement('div');
        timeContainer.textContent = `Start Time: ${test.startTime || 'Not set'},
         End Time: ${test.endTime || 'Not set'},
         Duration: ${test.duration ? test.duration : 'Not Set'}`;
        listItem.appendChild(timeContainer);

        const setTimeButton = document.createElement('button');
        setTimeButton.textContent = 'Set Time';
        setTimeButton.addEventListener('click', function () {
            showTestTimeForm(test);
        });
        listItem.appendChild(setTimeButton);

        const testTimeFormContainer = document.createElement('div');
        testTimeFormContainer.style.display = 'none';
        testTimeFormContainer.id = `testTimeFormContainer-${test.id}`;

        listItem.appendChild(testTimeFormContainer);

        const startTimeInput = document.createElement('input');
        startTimeInput.type = 'datetime-local';
        startTimeInput.id = `startTime-${test.id}`;
        testTimeFormContainer.appendChild(startTimeInput);

        const endTimeInput = document.createElement('input');
        endTimeInput.type = 'datetime-local';
        endTimeInput.id = `endTime-${test.id}`;
        testTimeFormContainer.appendChild(endTimeInput);

        const durationInput = document.createElement('input');
        durationInput.type = 'number';
        durationInput.id = `duration-${test.id}`;
        testTimeFormContainer.appendChild(durationInput);

        const setTimeButtonInsideForm = document.createElement('button');
        setTimeButtonInsideForm.textContent = 'Set Time';
        setTimeButtonInsideForm.addEventListener('click', function () {
            setTestTime(test);
        });
        testTimeFormContainer.appendChild(setTimeButtonInsideForm);

        testList.appendChild(listItem);
    });

}

async function showTestContent(test) {
    const testContent = document.getElementById('testContent');
    const testTimeFormContainer = document.getElementById(`testTimeFormContainer-${test.id}`);

    if (testContent.style.display === 'block') {
        testContent.style.display = 'none';
        testTimeFormContainer.style.display = 'none';
    } else {
        const questionsList = document.getElementById('questionsList');
        questionsList.innerHTML = '';
        const assignTestButton = document.getElementById('assignTestButton');
        assignTestButton.style.display = 'block';
        assignTestButton.onclick = () => showGroupSelection(test);

        test.questions.forEach((question, index) => {
            const questionItem = document.createElement('div');
            questionItem.classList.add('question-container');

            questionItem.innerHTML = `
                <p class="question-text">${index + 1}. ${question.text}</p>
                <ol class="answer-list">
                    ${question.possibleAnswers.map((answer, answerIndex) => `<li>${answerIndex + 1}. ${answer}</li>`).join('')}
                </ol>
                <p class="right-answer">Right Answer: ${question.rightAnswer}</p>
                <hr class="divider">
            `;

            questionsList.appendChild(questionItem);
        });

        testContent.style.display = 'block';
        testTimeFormContainer.style.display = 'none';
    }
}
function showGroupSelection(test) {
    const groupSelect = document.getElementById('groupSelect');
    groupSelect.style.display = 'block';
    loadGroupsIntoSelect();

    const assignTestButton = document.getElementById('assignTestButton');
    assignTestButton.style.display = 'none';

    const selectedTestId = test.id;

    const handleGroupSelection = async () => {
        const selectedGroupId = groupSelect.value;
        assignTestButton.disabled = !selectedGroupId;

        if (selectedGroupId) {
            try {
                const response = await fetch(`http://localhost:8081/teacher/assign?idTest=${selectedTestId}&idGroup=${selectedGroupId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                const result = await response.json();

                if (!result.error) {
                    alert('Test assigned to group successfully!');
                } else {
                    alert('Error assigning test to group: ' + result.result);
                }
            } catch (error) {
                console.error('Error assigning test to group:', error);
            } finally {
                groupSelect.style.display = 'none';
                assignTestButton.style.display = 'block';
                groupSelect.removeEventListener('change', handleGroupSelection);
            }
        }
    };

    groupSelect.addEventListener('change', handleGroupSelection);
}

async function loadGroupsIntoSelect() {
    try {
        const response = await fetch('http://localhost:8081/group/all');
        const responseData = await response.json();

        if (Array.isArray(responseData.result)) {
            const groups = responseData.result;
            const groupSelect = document.getElementById('groupSelect');
            groupSelect.innerHTML = '';

            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Select Group';
            groupSelect.appendChild(defaultOption);

            groups.forEach(group => {
                const option = document.createElement('option');
                option.value = group.id;
                option.textContent = group.name;
                groupSelect.appendChild(option);
            });
        } else {
            console.error('Unexpected data format from the server:', responseData);
            alert('Error loading groups. Please try again.');
        }
    } catch (error) {
        console.error('Error loading groups:', error);
        alert('Error loading groups. Please try again.');
    }
}

function showTestTimeForm(test) {
    const testTimeFormContainer = document.getElementById(`testTimeFormContainer-${test.id}`);

    if (testTimeFormContainer.style.display === 'block') {
        testTimeFormContainer.style.display = 'none';
    } else {
        document.querySelectorAll('.test-time-form-container').forEach(formContainer => {
            formContainer.style.display = 'none';
        });

        testTimeFormContainer.style.display = 'block';
        testTimeFormContainer.setAttribute('data-test-id', test.id);
    }
}

async function setTestTime(test) {
    const startTime = document.getElementById(`startTime-${test.id}`).value;
    const endTime = document.getElementById(`endTime-${test.id}`).value;
    const duration = document.getElementById(`duration-${test.id}`).value;

    const testTimeRequest = {
        idTest: test.id,
        startTime: new Date(startTime).toISOString(),
        endTime: new Date(endTime).toISOString(),
        duration: duration
    };

    console.log('Test time request:', testTimeRequest);

    saveTestTime(testTimeRequest);
}

async function saveTestTime(testTimeRequest) {
    const response = await fetch('http://localhost:8081/test/time', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(testTimeRequest),
    });

    const result = await response.json();
    console.log('Test time saved:', result);
}

window.onload = getTests;