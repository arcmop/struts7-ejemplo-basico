(() => {
    const clockEl = document.getElementById("liveClock");
    if (!clockEl) {
        return;
    }

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
})();
