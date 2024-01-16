document.addEventListener('DOMContentLoaded', function() {
    const testId = getTestIdFromUrl();
    fetchQuestions(testId);
});

function fetchQuestions(testId) {
    fetch(`http://localhost:8081/questions/${testId}`)
        .then(response => response.json())
        .then(questions => {
            displayQuestions(questions);
        })
        .catch(error => console.error('Error:', error));
}

function displayQuestions(questions) {
    const questionsSection = document.getElementById('questions-section');

    questionsSection.innerHTML = '';

    questions.forEach(question => {
        const questionElement = document.createElement('div');
        questionElement.classList.add('question-card');

        const questionText = document.createElement('p');
        questionText.textContent = question.text;
        questionElement.appendChild(questionText);

        const answersList = document.createElement('ol');
        question.possibleAnswers.forEach(answer => {
            const answerItem = document.createElement('li');
            answerItem.textContent = answer;
            answersList.appendChild(answerItem);
        });
        questionElement.appendChild(answersList);

        const answerInput = document.createElement('input');
        answerInput.type = 'text';
        answerInput.placeholder = 'Enter your answer';
        answerInput.classList.add('answer-input');
        questionElement.appendChild(answerInput);

        questionsSection.appendChild(questionElement);
    });
}

function getTestIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('testId');
}

document.addEventListener('DOMContentLoaded', function () {
    const testId = getTestIdFromUrl();
    fetchQuestions(testId);

    const submitTestButton = document.getElementById('submit-test-btn');
    submitTestButton.addEventListener('click', submitTest);
});

function submitTest() {
    const answerInputs = document.querySelectorAll('.answer-input');
    const userAnswers = [];

    answerInputs.forEach(input => {
        const selectedAnswer = input.value.trim();
        userAnswers.push(selectedAnswer);
    });

    const testId = getTestIdFromUrl();

    const apiUrl = `http://localhost:8081/test/pass`;

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userAnswers),
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}