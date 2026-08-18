// ── Matches your UserController + DTOs exactly ───────────────────
const CONFIG = {
  baseURL: "",
  endpoints: {
    login: "/api/login",     // expects { email, password }
    signup: "/api/signUp",   // expects { name, email, userName, password }
  },
};
// ──────────────────────────────────────────────────────────────

document.getElementById("targetUrl").textContent = `→ ${CONFIG.baseURL}`;

const logEl = document.getElementById("log");
const sessionEl = document.getElementById("sessionState");
const logoutBtn = document.getElementById("logoutBtn");
const loginBanner = document.getElementById("loginBanner");
const signupBanner = document.getElementById("signupBanner");

function logLine(kind, text) {
  const line = document.createElement("div");
  line.className = `log-line ${kind}`;
  const time = new Date().toLocaleTimeString();
  line.innerHTML = `<span class="time">${time}</span> ${text}`;
  logEl.appendChild(line);
  logEl.scrollTop = logEl.scrollHeight;
}

function renderSession() {
  const email = sessionStorage.getItem("currentUserEmail");
  sessionEl.textContent = email ? `Signed in as ${email}` : "Not signed in";
  sessionEl.classList.toggle("ok", !!email);
  logoutBtn.style.display = email ? "inline" : "none";
}

function clearFieldErrors(form) {
  form.querySelectorAll(".field-error").forEach((el) => (el.textContent = ""));
}

function showFieldErrors(form, fieldErrors) {
  Object.entries(fieldErrors).forEach(([field, msg]) => {
    const el = form.querySelector(`.field-error[data-for="${field}"]`);
    if (el) el.textContent = msg;
  });
}

function showBanner(el, text, kind) {
  el.textContent = text;
  el.className = `banner ${kind}`;
}

function clearBanner(el) {
  el.textContent = "";
  el.className = "banner";
}

// Talks to the backend, logs the raw exchange, and never throws —
// caller inspects { ok, status, data, networkError }.
async function callApi(method, path, body) {
  const url = CONFIG.baseURL + path;
  logLine("req", `${method} ${path}`);

  try {
    const res = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });

    const raw = await res.text();
    let data = null;
    try { data = raw ? JSON.parse(raw) : null; } catch { data = raw; }

    const shown = typeof data === "string" ? data : JSON.stringify(data);
    logLine(res.ok ? "ok" : "err", `${res.status} ${res.statusText} — ${shown}`);

    return { ok: res.ok, status: res.status, data };
  } catch (err) {
    logLine("err", `Network/CORS error — is the backend running at ${CONFIG.baseURL}, and does WebConfig's allowedOrigins include ${location.origin}?`);
    return { ok: false, status: 0, data: null, networkError: true };
  }
}

// ── Tabs ──────────────────────────────────────────────────────
document.querySelectorAll(".tab").forEach((btn) => {
  btn.addEventListener("click", () => {
    document.querySelectorAll(".tab").forEach((b) => b.classList.remove("active"));
    document.querySelectorAll(".form").forEach((f) => f.classList.remove("active"));
    btn.classList.add("active");
    document.getElementById(`${btn.dataset.tab}Form`).classList.add("active");
  });
});

// ── Login → POST /api/login { email, password } ─────────────────
document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  clearFieldErrors(form);
  clearBanner(loginBanner);

  const fd = new FormData(form);
  const body = { email: fd.get("email"), password: fd.get("password") };

  const { ok, data, networkError } = await callApi("POST", CONFIG.endpoints.login, body);

  if (networkError) {
    showBanner(loginBanner, "Could not reach the server.", "err");
    return;
  }
  if (ok) {
    showBanner(loginBanner, data.message || "Logged in.", "ok");

    // Store logged-in user
    sessionStorage.setItem(
        "currentUserEmail",
        data.email || body.email
    );

    renderSession();

    // Redirect after a short delay
    setTimeout(() => {
      window.location.href = "/student/student-register.html";
    }, 700);
  } else if (data?.fieldErrors) {
    showFieldErrors(form, data.fieldErrors);
    showBanner(loginBanner, data.message || "Please fix the errors below.", "err");
  } else {
    showBanner(loginBanner, data?.message || "Login failed.", "err");
  }
});

// ── Signup → POST /api/signUp { name, email, userName, password } ─
document.getElementById("signupForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  clearFieldErrors(form);
  clearBanner(signupBanner);

  const fd = new FormData(form);
  const body = {
    name: fd.get("name"),
    email: fd.get("email"),
    userName: fd.get("userName"),
    password: fd.get("password"),
  };

  const { ok, data, networkError } = await callApi("POST", CONFIG.endpoints.signup, body);

  if (networkError) {
    showBanner(signupBanner, "Could not reach the server.", "err");
    return;
  }
  if (ok) {
    showBanner(signupBanner, `Account created for ${data.userName}.`, "ok");
    form.reset();
    setTimeout(() => document.querySelector('.tab[data-tab="login"]').click(), 900);
  } else if (data?.fieldErrors) {
    showFieldErrors(form, data.fieldErrors);
    showBanner(signupBanner, data.message || "Please fix the errors below.", "err");
  } else {
    showBanner(signupBanner, data?.message || "Signup failed.", "err");
  }
});

logoutBtn.addEventListener("click", () => {
  sessionStorage.removeItem("currentUserEmail");
  renderSession();
});

document.getElementById("clearLog").addEventListener("click", () => (logEl.innerHTML = ""));

renderSession();
