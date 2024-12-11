function submitForm() {
    console.log("フォーム送信開始"); // デバッグ用
    document.forms['a_edit'].submit(); // フォーム送信
	document.forms['a_search'].submit(); // フォーム送信
}

document.addEventListener("DOMContentLoaded", function () {
    const currentDate = new Date(); // 現在の日時を取得
    const taskCards = document.querySelectorAll(".task-card"); // すべてのタスクカードを取得

    taskCards.forEach(card => {
        const endTime = card.getAttribute("data-endtime"); // End Time を取得
        const completionTime = card.getAttribute("data-completiontime"); // Completion Time を取得

        if (completionTime) {
            card.classList.add("completed"); // 完了済みタスクはグレー
        } else if (endTime) {
            const endDate = new Date(endTime); // End Time を日時としてパース

            if (endDate < currentDate) {
                card.classList.add("overdue"); // 過去のタスクは赤
            } else if (endDate.toDateString() === currentDate.toDateString()) {
                card.classList.add("due-today"); // 当日のタスクは青
            }
            // それ以外はスタイルを変更しない（デフォルトのスタイルを維持）
        }
    });
});
