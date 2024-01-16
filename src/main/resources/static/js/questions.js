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
        questionElement.innerHTML = `
            <p>${question.text}</p>
            <input type="text" placeholder="Enter your answer" class="answer-input">
        `;

        questionsSection.appendChild(questionElement);
    });
}

function getTestIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('testId');
}