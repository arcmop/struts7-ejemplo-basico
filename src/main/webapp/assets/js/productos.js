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

    const loginModalEl = document.getElementById("loginModal");
    if (loginModalEl) {
        let autoOpenConsumed = false;

        loginModalEl.addEventListener("shown.bs.modal", () => {
            const usernameInput = document.getElementById("loginUsername");
            if (usernameInput) {
                usernameInput.focus();
            }
        });

        loginModalEl.addEventListener("hidden.bs.modal", () => {
            if (!document.querySelector(".modal.show")) {
                document.body.classList.remove("modal-open");
                document.body.style.removeProperty("padding-right");
                document.querySelectorAll(".modal-backdrop").forEach((backdrop) => backdrop.remove());
            }
        });

        const openLoginModalIfNeeded = () => {
            if (autoOpenConsumed) {
                return;
            }

            const modalState = document.getElementById("loginModalState");
            if (!modalState) {
                return;
            }

            const shouldOpen = (modalState.dataset.autoOpen || "").trim().toLowerCase() === "true";
            if (shouldOpen) {
                autoOpenConsumed = true;
                modalState.dataset.autoOpen = "false";

                const tryShow = () => {
                    if (!window.bootstrap || !window.bootstrap.Modal) {
                        window.setTimeout(tryShow, 50);
                        return;
                    }
                    const modal = window.bootstrap.Modal.getOrCreateInstance(loginModalEl);
                    if (!loginModalEl.classList.contains("show")) {
                        modal.show();
                    }
                };
                tryShow();
            }
        };

        openLoginModalIfNeeded();
        window.addEventListener("load", openLoginModalIfNeeded);
        window.addEventListener("pageshow", openLoginModalIfNeeded);
    }
})();
