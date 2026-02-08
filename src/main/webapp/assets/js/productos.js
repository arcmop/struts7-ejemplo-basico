(() => {
    const clockEl = document.getElementById("liveClock");
    if (clockEl) {
        const formatTime = (date) => date.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit"
        });

        const updateClock = () => {
            clockEl.textContent = formatTime(new Date());
        };

        updateClock();
        setInterval(updateClock, 1000);
    }

    const confirmForms = document.querySelectorAll("[data-confirm]");
    confirmForms.forEach((form) => {
        form.addEventListener("submit", (event) => {
            const message = form.getAttribute("data-confirm");
            if (message && !window.confirm(message)) {
                event.preventDefault();
            }
        });
    });
})();
