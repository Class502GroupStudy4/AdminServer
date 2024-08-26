document.addEventListener('DOMContentLoaded', function() {
    var stats = /*[[${stats}]]*/ [];

    // 데이터 준비
    const dates = stats.map(stat => stat.visitDate);
    const counts = stats.map(stat => stat.visitCount);

    // 그래프 생성
    const ctx = document.getElementById('visitorChart').getContext('2d');
    new Chart(ctx, {
        type: 'line', // 그래프의 종류 (선 그래프)
        data: {
            labels: dates, // X축 레이블 (날짜)
            datasets: [{
                label: 'Visitor Count',
                data: counts, // Y축 데이터 (방문자 수)
                borderColor: 'rgba(75, 192, 192, 1)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderWidth: 2,
                fill: true
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    beginAtZero: true
                },
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});
